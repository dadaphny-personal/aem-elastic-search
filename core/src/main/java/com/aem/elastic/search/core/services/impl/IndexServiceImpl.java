package com.aem.elastic.search.core.services.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.event.jobs.consumer.JobConsumer.JobResult;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.models.Page;
import com.aem.elastic.search.core.config.IndexApiConfiguration;
import com.aem.elastic.search.core.models.PageDocument;
import com.aem.elastic.search.core.services.IndexService;
import com.aem.elastic.search.core.services.ObjectMapperService;
import com.aem.elastic.search.core.services.SiteDomainConfigProvider;
import com.aem.elastic.search.core.services.ThirdPartyApiHeaderIdentifierService;
import com.aem.elastic.search.core.services.UtilService;
import com.aem.elastic.search.core.utils.ServiceUserUtility;
import com.day.crx.JcrConstants;

/**
 * IndexServiceImpl Service layer for IndexPageJob
 * 
 * @author daphny.cabiso
 */
@Component(service = IndexService.class, immediate = true)
@Designate(ocd = IndexApiConfiguration.class)
public class IndexServiceImpl implements IndexService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceImpl.class);
	private static final String DELETE = "delete";
	private static final String ADD = "add";
	private static final int[] STATUS_CODE_OK = new int[] { 200, 201 };
	private static final int STATUS_NOT_FOUND = 404;
	private static final String TOKEN_CACHE = "indexpagejobtoken";
	private static final String GRANT_TYPE = "grant_type";
	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String AUTHORIZATION = "Authorization";
	private static final String CONTENT_TYPE_HEADER = "Content-Type";
	private static final String TOKEN_CONTENT_TYPE = "application/x-www=form-urlencoded";
	private static final String INDEX_CONTENT_TYPE = "application/json";
	private static final String ACCESS_TOKEN_PROP = "access_token";
	private static final String INTERNAL_SEARCH = "internalsearch";

	private IndexApiConfiguration config;

	/**
	 * Reference UtilService
	 */
	@Reference
	private UtilService utilService;

	/**
	 * Reference SiteDomainConfigProvider
	 */
	@Reference
	private SiteDomainConfigProvider siteDomainConfigProvider;

	/**
	 * Reference ObjectMapperService
	 */
	@Reference
	private ObjectMapperService objectMapperService;

	/**
	 * Reference ServiceUserUtility
	 */
	@Reference
	private ServiceUserUtility serviceUserUtility;

	/**
	 * Reference ThirdPartyApiHeaderIdentifierService
	 */
	@Reference
	private ThirdPartyApiHeaderIdentifierService thirdPartyApiHeaderIdentifierService;

	@Activate
	@Modified
	protected void activate(IndexApiConfiguration config) {
		this.config = config;
	}

	@Override
	public JobResult indexPage(String resourcePath, String originalPagePath, String eventType) {
		JobResult jobResultValue = JobResult.CANCEL;

		if (this.config.enabled()) {
			try {
				if (StringUtils.isNotEmpty(resourcePath)) {
					final ResourceResolver resolver = this.serviceUserUtility.getServiceUserResourceResolver();
					Resource resource = resolver.getResource(resourcePath);
					LOGGER.debug("Distributed Event Resource Path: {} Event Type: {}", resourcePath, eventType);

					if (shouldProcess(resource).equals(true)) {
						final PageDocument pageDocument = getPageDocument(resource, originalPagePath);
						final Map<String, Object> payload = new HashMap<>();
						CloseableHttpResponse response = null;

						// get event to execute
						String indexAction = identifyEvent(eventType, pageDocument);

						payload.put("eventtype", indexAction);
						payload.put("payload", pageDocument);
						response = callApi(payload);

						if (response != null) {
							return processResponse(eventType, response);
						}
					}

					resolver.close();
					LOGGER.debug(
							"IndexPageJob page is not set for indexing, no resource found, or failed Authentication.");
					jobResultValue = JobResult.CANCEL;
				}
			} catch (final IOException e) {
				LOGGER.error("IndexServiceImpl error: {}", e.getMessage(), e);
			}

		}
		LOGGER.debug("Page Indexing is disabled in configuration");
		return jobResultValue;
	}

	private Boolean shouldProcess(Resource resource) {
		if (resource != null) {
			return Arrays.stream(config.excludedPaths()).noneMatch(resource.getPath()::contains)
					&& resource.getChild(IndexServiceImpl.INTERNAL_SEARCH) != null;
		}
		return false;
	}

	private static PageDocument getPageDocument(Resource resource, String originalPagePath) {
		final PageDocument pageDocument = resource.adaptTo(PageDocument.class);
		if (resource.getValueMap().get(JcrConstants.JCR_PRIMARYTYPE, String.class).equals(JcrConstants.NT_FROZENNODE)) {
			// set the id, _name, _fullpath, relativepath, geosite & dateupdated property
			// for frozen node
			pageDocument.setPageID(originalPagePath);
			pageDocument.setName(originalPagePath);
			pageDocument.setFullPath(originalPagePath);
			pageDocument.setRelativePath(originalPagePath);
			pageDocument.setDateUpdated(originalPagePath);
			pageDocument.setCountrySite(originalPagePath);

			// get original page path without jcr:content
			String originalPageURL = originalPagePath.replace(PageDocumentImpl.JCRCONTENT_PATH, StringUtils.EMPTY);

			// get original page resource
			Resource originalPageResource = resource.getResourceResolver().getResource(originalPageURL);

			pageDocument.setCurrentPageLocale(
					CIOUtils.getPageGeo(originalPageResource.adaptTo(com.day.cq.wcm.api.Page.class)));
		}
		return pageDocument;
	}

	/**
	 * identify event that should be executed
	 * 
	 * @return add|delete|skip
	 */
	private static String identifyEvent(String eventType, PageDocument pageDocument) {
		String indexAction = IndexServiceImpl.DELETE;

		if (shouldIndex(eventType, pageDocument)) {
			indexAction = IndexServiceImpl.ADD;
		}
		return indexAction;
	}

	/**
	 * check if page should be indexed based on page properties and replication
	 * event
	 * 
	 * @return true|false
	 */
	private static boolean shouldIndex(String eventType, PageDocument pageDocument) {
		if (StringUtils.isNotBlank(eventType)) {
			return eventType.equalsIgnoreCase(IndexServiceImpl.ADD) && pageDocument.doIndexPage().equals(true)
					&& pageDocument.hasPermanentRedirect().equals(false);
		}
		return false;
	}

}
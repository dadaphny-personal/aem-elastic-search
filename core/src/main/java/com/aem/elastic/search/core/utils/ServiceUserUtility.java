package com.aem.elastic.search.core.utils;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This utility is for getting service user "sites-datacom-service-user"
 * resource resolver - need to change depends on the configuration in cloud
 * manager
 * 
 * @author daphny.cabiso
 */
@Component(immediate = true, service = ServiceUserUtility.class)
public class ServiceUserUtility {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUserUtility.class);

	private static final String SERVICE_USER_SUBSERVICE_NAME = "sitesresourceresolverservice";
	private static final String SUBSERVICE_SITES_READER = "getSitesReaderServiceUserResourceResolver";
	private static final String SUBSERVICE_SITES_APPS_READER = "sitesappsresourceresolverservice";

	/**
	 * B2C service name, used to create the service user
	 */
	public static final String B2C_SERVICE = "oauth-datacomB2cProvider-service";

	/**
	 * B2C service user parameters
	 */
	public static final Map<String, Object> SERVICE_USER_PARAMS = Map.of(ResourceResolverFactory.SUBSERVICE,
			B2C_SERVICE);

	public static final String UTILITY_NAME = ServiceUserUtility.class.getSimpleName();

	@Reference
	public ResourceResolverFactory resourceResolverFactory;

	/**
	 * Get sites reader resolver
	 */
	public ResourceResolver getSitesReaderServiceUserResourceResolver() {
		return getServiceUserResolverByName(SUBSERVICE_SITES_READER);
	}

	/**
	 * Get general resolver
	 */
	public ResourceResolver getServiceUserResourceResolver() {
		return getServiceUserResolverByName(SERVICE_USER_SUBSERVICE_NAME);
	}

	/**
	 * Get general resolver
	 */
	public ResourceResolver getSitesAppsReaderServiceUserResourceResolver() {
		return getServiceUserResolverByName(SUBSERVICE_SITES_APPS_READER);
	}

	/**
	 * Run consumer with the general resolver and close it after
	 * 
	 * @param consumer
	 */
	public void doWithServiceUserResourceResolver(Consumer<ResourceResolver> consumer) {
		LOGGER.debug("{}: Getting Service User...", UTILITY_NAME);

		final Map<String, Object> param = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				(Object) SERVICE_USER_SUBSERVICE_NAME);

		ResourceResolver resourceResolver = null;

		try {
			resourceResolver = this.resourceResolverFactory.getServiceResourceResolver(param);
			consumer.accept(resourceResolver);
		} catch (LoginException e) {
			LOGGER.error("Login Exception", e);
		} finally {
			if (resourceResolver != null) {
				// close resolver
				resourceResolver.close();
			}
		}
	}

	/**
	 * Get resolver by service user name
	 * 
	 * @param name
	 */
	private ResourceResolver getServiceUserResolverByName(String name) {
		LOGGER.debug("{}: Getting Service User...", UTILITY_NAME);

		final Map<String, Object> param = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, (Object) name);

		ResourceResolver resourceResolver = null;

		try {
			resourceResolver = this.resourceResolverFactory.getServiceResourceResolver(param);
		} catch (LoginException e) {
			LOGGER.error("Login Exception", e);
		}

		return resourceResolver;
	}

	/**
	 * Get B2C service user and run consumer using its resolver close it after
	 * 
	 * @param resourceResolverConsumer
	 */
	public void doWithB2cServiceUserResolver(Consumer<ResourceResolver> resourceResolverConsumer) {
		ResourceResolver resolver = null;
		try {
			resolver = this.resourceResolverFactory.getServiceResourceResolver(SERVICE_USER_PARAMS);
			resourceResolverConsumer.accept(resolver);
		} catch (LoginException e) {
			LOGGER.error("Failed to get resolver for subservice {}", B2C_SERVICE, e);
		} finally {
			if (resolver != null) {
				// close resolver
				resolver.close();
			}
		}
	}
}
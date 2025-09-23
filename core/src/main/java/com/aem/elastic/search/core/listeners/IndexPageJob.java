package com.aem.elastic.search.core.listeners;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.elastic.search.core.services.IndexService;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The class IndexPageJob
 * 
 * @author daphny.cabiso
 */
@Component(service = JobConsumer.class, immediate = true, property = {
		Constants.SERVICE_ID + "=DATACOM_Distribution_Listener",
		Constants.SERVICE_DESCRIPTION + "= This event handler listents to the events on Distribution Add and Delete",
		JobConsumer.PROPERTY_TOPICS + "=" + IndexPageJob.INDEX_PAGE_JOB })
public class IndexPageJob implements JobConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexPageJob.class);
	public static final String INDEX_PAGE_JOB = "datacom/indexpagejob";
	public static final String DISTRIBUTIION_PATH_PROPS = "distribution.paths";
	public static final String DISTRIBUTIION_TYPE_PROPS = "distribution.type";

	@Reference
	IndexService indexService;

	/**
	 * Injects the Resource
	 */
	@JsonIgnore
	@Self(injectionStrategy = InjectionStrategy.OPTIONAL)
	private Resource resource;

	@Override
	public JobResult process(final Job job) {
		// get resourcePath from event
		final String resourcePath = job.getProperty(DISTRIBUTIION_PATH_PROPS).toString();

		// get eventype from event (add or delete)
		final String eventType = job.getProperty(DISTRIBUTIION_TYPE_PROPS).toString();
		
		return this.indexService.indexPage(resourcePath, resourcePath, eventType);
	}

}

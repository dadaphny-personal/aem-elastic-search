package com.aem.elastic.search.core.listeners;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.replication.ReplicationAction;

/**
 * Distribution Event Handler
 * 
 * @author daphny.cabiso
 */
@Component(service = EventHandler.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "= This   event handler listents to the events on Distribution Add and Delete",
		EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC })
public class DistributionEventHandler implements EventHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReplicationEventHandler.class);
	private static final String JOB = IndexPageJob.INDEX_PAGE_JOB;
	private static final String DISTRIBUTIION_PATH_PROPS = IndexPageJob.DISTRIBUTIION_PATH_PROPS;
	private static final String DISTRIBUTIION_TYPE_PROPS = IndexPageJob.DISTRIBUTIION_TYPE_PROPS;
	private static final String SITES_PATH = "/content/project";

	@Reference
	JobManager jobManager;

	@Override
	public void handleEvent(final Event event) {
		try {
			LOGGER.debug("Resource event: {} at: {}", event.getTopic(),
					event.getProperty(SlingConstants.PROPERTY_PATH));
			LOGGER.debug("DistributionEventHandler event: {} at: {}", event);

			// Get the payload path from the event
			final String eventPaths = ReplicationAction.fromEvent(event).getPath();

			// Get the event type from the event (add or delete)
			final String eventType = ReplicationAction.fromEvent(event).getType().getName();

			final String validPaths = eventPaths.startsWith(SITES_PATH) ? eventPaths : null;

			// Add job only if there is valid path
			if (validPaths != null) {
				String resourcePath = validPaths;

				final Map<String, Object> jobProperties = new HashMap<>();

				// Add page path for the job
				jobProperties.put(DISTRIBUTIION_PATH_PROPS, resourcePath.concat("/").concat(JcrConstants.JCR_CONTENT));

				// Add event type for the job
				jobProperties.put(DISTRIBUTIION_TYPE_PROPS, eventType);

				// Add IndexPageJob job to the job manager
				this.jobManager.addJob(JOB, jobProperties);

			} else {
				LOGGER.debug("DistributionEventHandler no job queued. Resource path is invalid.");
			}
		} catch (final ClassCastException e) {
			LOGGER.error("DistributionEventHandler error.", e);
		}

	}
}

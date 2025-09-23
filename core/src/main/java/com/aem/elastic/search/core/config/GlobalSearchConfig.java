package com.aem.elastic.search.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * This configuration is used to get the endpoint for elastic search API for
 * global search component
 * 
 * @author daphny.cabiso
 * 
 */
@ObjectClassDefinition(name = "Datacom Global Search Configuration", description = "API to be used to get the result for global search.")
public @interface GlobalSearchConfig {

	/**
	 * AEM Global Search Endpoint
	 * 
	 * @return aemSearchEndpoint
	 */
	@AttributeDefinition(name = "AEM Search API path: ", type = AttributeType.STRING, description = "Provide the path to AEM search servlet.")
	String aemSearchEndpoint();

	/**
	 * Elastic Search Endpoint
	 * 
	 * @return elasticSearchEndpoint
	 */
	@AttributeDefinition(name = "Elastic Search API path: ", type = AttributeType.STRING, description = "Provide the path with place holder for country code as it will chnage based on current page.")
	String elasticSearchEndpoint();

	/**
	 * Timeout in milliseconds
	 * 
	 * @return timeoutInMilliseconds
	 */
	@AttributeDefinition(name = "Timeout: ", type = AttributeType.INTEGER, defaultValue = "5000", description = "Timeout in milliseconds, used in the various http-calls.")
	int timeoutInMilliseconds();

	/**
	 * Regex mapping for country selector code
	 * 
	 * @return regex
	 */
	@AttributeDefinition(name = "Regular Expression: ", type = AttributeType.STRING, description = "Regular Expression to match country selector code.")
	String regex() default "[a-z]{2}-[a-z]{2}";

	/**
	 * Feature Content Fragment Root Path
	 * 
	 * @return featureCfRootPath
	 */
	@AttributeDefinition(name = "Feature Content Root Path: ", type = AttributeType.STRING, description = "Root path where feature content fragment used in global search exists.")
	String featureCfRootPath();

	/**
	 * Feature Image DM preset type
	 * 
	 * @return featureImagePresetType
	 */
	@AttributeDefinition(name = "Feature Image DM preset type: ", type = AttributeType.STRING, description = "Enter the dynamic media preset type for featured image used in prefilter global search.")
	String featureImagePresetType() default "smartCrop";

	/**
	 * Feature Image Smart crop rendition
	 * 
	 * @return featureImageSmartCropRendition
	 */
	@AttributeDefinition(name = "Feature Image Smart crop rendition: ", type = AttributeType.STRING, description = "Enter the dynamic media preset type for featured image used in prefilter global search.")
	String featureImageSmartCropRendition() default "1x1";

	/**
	 * Feature Image Modifiers
	 * 
	 * @return featureImageModifiers
	 */
	@AttributeDefinition(name = "Feature Image Modifiers: ", type = AttributeType.STRING, description = "Enter the dynamic media image modifiers for featured image used in prefilter global search.")
	String featureImageModifiers() default "fit=constrain";

	/**
	 * Search Setting Fragment Collection Path
	 * 
	 * @return searchSetingFragmentCollectionPath
	 */
	@AttributeDefinition(name = "Search Setting Fragment Collection Path: ", type = AttributeType.STRING, description = "Path where the search setting content fragments for various geo has been stored.")
	String searchSetingFragmentCollectionPath() default "/content/dam/system-files/datacom/search/global-search-serp-setting/serp-setting-collections/";
}
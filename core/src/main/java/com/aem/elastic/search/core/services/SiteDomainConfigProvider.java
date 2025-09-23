package com.aem.elastic.search.core.services;

import java.util.Map;

import com.aem.elastic.search.core.config.SiteDomainConfiguration;

/**
 * The interface SiteDomainConfigProvider
 */
public interface SiteDomainConfigProvider {

	/**
	 * Gets Site Domain Configured on OSGI or at environment level
	 * 
	 * @return SiteDomainConfiguration Config
	 */
	SiteDomainConfiguration getConfiguration();

	/**
	 * This method constructs Map of locale as string with value as domains
	 * configured in osgi or at environment level
	 * 
	 * @return Map<String, String> sitedomainmap
	 */
	Map<String, String> getSiteDomainMap();

	/**
	 * This method returns default domain configured on osgi or environment level
	 * 
	 * @return String defaultDomain
	 */
	String defaultDomain();

}
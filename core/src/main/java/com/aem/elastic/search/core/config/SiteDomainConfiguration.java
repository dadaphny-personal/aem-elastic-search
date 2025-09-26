package com.aem.elastic.search.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


/**
 * The Config SiteDomainConfiguration
 * 
 * @author daphny.cabiso
 */
@ObjectClassDefinition(name = "Datacom Site Domain Configuration", description = "Configuration to map domains per site")
public @interface SiteDomainConfiguration {
	
	/**
	 * Default Domain for site
	 * 
	 * @return defaultDomain
	 */
	@AttributeDefinition(name = "Datacom Default Domain ", type = AttributeType.STRING, description = "Default domain for site. ex: https://datacom.com/")
	String defaultDomain() default "https://datacom.com/";
}
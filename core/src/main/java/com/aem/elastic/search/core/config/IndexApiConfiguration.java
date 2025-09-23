package com.aem.elastic.search.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * The class AWS API Configuration
 * 
 * Elastic Search for AEM - API Authentication
 * 
 * @author daphny.cabiso
 * @category OSGI Configuration
 * 
 */
@ObjectClassDefinition(name = "Index API Configuration", description = "Configuration to invoke AWS API. Check your AWS Service documentation for details.")
public @interface IndexApiConfiguration {

	int DEFAULT_TIMEOUT = 5000;
	int DEFAULT_EXPIRATION = 3590;

	/**
	 * Index API Endpoint
	 * 
	 * @return indexEndpoint
	 */
	@AttributeDefinition(name = "Index API Endpoint: ", type = AttributeType.STRING, description = "API endpoint for indexing data to ElasticSearch. Must conform with propert URL structure. ex: https://example.domain.com/endpointpath")
	String indexEndpoint();

	/**
	 * Index API Method
	 * 
	 * @return indexMethod
	 */
	@AttributeDefinition(name = "Index API Method: ", type = AttributeType.STRING)
	String indexMethod() default "POST";

	/**
	 * Token Generator Endpoint
	 * 
	 * @return tokenGeneratorEndpoint
	 */
	@AttributeDefinition(name = "Token Generator Endpoint: ", type = AttributeType.STRING, description = "API endpoint for getting access token. Must conform with proper URL structure. ex: https://example: domain.com/endpointpath")
	String tokenGeneratorEndpoint();

	/**
	 * Token Generator Grant Type. Defaults to client_crendentials
	 * 
	 * @return grantType
	 */
	@AttributeDefinition(name = "Token Generator Grant Type: ", type = AttributeType.STRING)
	String grantType() default "client_credentials";

	/**
	 * Registered Application client ID
	 * 
	 * @return clientID
	 */
	@AttributeDefinition(name = "Token Generator Client ID: ", type = AttributeType.STRING)
	String clientID();

	/**
	 * Application client secret
	 * 
	 * @return clientSecret
	 */
	@AttributeDefinition(name = "Token Generator Client Secret: ", type = AttributeType.STRING)
	String clientSecret();

	/**
	 * API call timeout in seconds. Defaults to 20 secs.
	 * 
	 * @return timeout
	 */
	@AttributeDefinition(name = "Timeout: ", type = AttributeType.INTEGER, description = "API call timeout in milliseconds. Default is 5000.")
	int timeout() default DEFAULT_TIMEOUT;

	/**
	 * API call token expiration in seconds.
	 * 
	 * @return tokenExpiration
	 */
	@AttributeDefinition(name = "Token Expiration: ", type = AttributeType.INTEGER, description = "Token expiration in seconds. Default is 3590.")
	int tokenExpiration() default DEFAULT_EXPIRATION;

	/**
	 * API call method. Default is POST
	 * 
	 * @return tokenGeneratorMethod
	 */
	@AttributeDefinition(name = "Token Generator Method: ", type = AttributeType.STRING, description = "default is POST")
	String tokenGeneratorMethod() default "POST";

	/**
	 * Enables indexing process
	 * 
	 * @return enabled
	 */
	@AttributeDefinition(name = "Enabled: ", type = AttributeType.BOOLEAN, description = "default is true")
	boolean enabled() default true;

	/**
	 * List of paths excluded from indexing
	 * 
	 * @return excludedPaths
	 */
	@AttributeDefinition(name = "Excluded Paths: ", type = AttributeType.STRING, description = "Paths to exclude from indexing.")
	String[] excludedPaths() default { "/content/datacom/language-masters" };
}

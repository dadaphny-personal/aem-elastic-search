package com.aem.elastic.search.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The interface Object mapper service
 */
public interface ObjectMapperService {
	
	/**
	 * This method returns new ObjectMapper Instance
	 * 
	 * @return ObjectMapper   
	 */
	ObjectMapper getObjectMapper();
	
}
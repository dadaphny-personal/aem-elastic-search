package com.aem.elastic.search.core.utils;

import java.util.HashMap;

/**
 * Cache manager utility for in memory caching
 * 
 * @author daphny.cabiso
 */
public class CacheManager {

	private static HashMap<String, Object> cacheHashMap = new HashMap<>();

	/**
	 * Inserts cache object with specified key
	 * 
	 * @param object
	 * @param identifier
	 */
	public static void putCache(Object object, String identifier) {
		cacheHashMap.put(identifier, object);
	}

	/**
	 * Gets cache with specified key
	 * 
	 * @param identifier
	 */
	public static Object getCache(String identifier) {
		final Object object = cacheHashMap.get(identifier);
		return object;
	}

	/**
	 * Removes cache with specified key
	 * 
	 * @param identifier
	 */
	public static Boolean removeCacheKey(String identifier) {
		Boolean success = true;

		if (cacheHashMap.containsKey(identifier)) {
			success = false;
			return success;
		}
		cacheHashMap.remove(identifier);
		return success;
	}

	/**
	 * Clears all stored cache
	 */
	public static void removeCacheAll() {
		cacheHashMap.clear();
	}

}
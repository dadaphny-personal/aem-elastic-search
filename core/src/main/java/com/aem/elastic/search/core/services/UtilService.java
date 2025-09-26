package com.aem.elastic.search.core.services;

import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * This interface generate new Class Instance
 */
public interface UtilService {
	
	/**
	 * Gets http client with default client
	 * 
	 * @return the http client
	 */
	CloseableHttpClient getHttpClient();
	
	/**
	 * Gets http client
	 * 
	 * @param timeoutInMilliseconds the timeout in milliseconds
	 * @return the http client
	 */
	CloseableHttpClient getHttpClient(int timeoutInMilliseconds);
	
	/**
	 * This Method returns the new HttpGet instance
	 * 
	 * @param api url
	 * @return httpGet http get
	 * @throws URISyntaxException
	 */
	HttpGet getHttpGet(String url) throws URISyntaxException;
	
	/**
	 * This Method returns the new HttpGet instance
	 * 
	 * @param url of the method
	 * @param params - list of params
	 * @return httpGet http get
	 * @throws URISyntaxException
	 */
	HttpGet getHttpGet(String url, List<NameValuePair> params) throws URISyntaxException;
	
	/**
	 * Method to get the post request with url
	 * 
	 * @param url of the method
	 * @return httpPost 
	 * @throws URISyntaxException
	 */
	HttpPost getHttpPost(String url) throws URISyntaxException;
	
	/**
	 * This method returns new HttpPost Instance
	 * 
	 * @param url of the method
	 * @param params - list of params
	 * @return httpPost 
	 * @throws URISyntaxException
	 */
	HttpPost getHttpPost(String url, List<NameValuePair> params) throws URISyntaxException;
	
	
	
}
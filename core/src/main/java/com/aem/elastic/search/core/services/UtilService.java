package com.aem.elastic.search.core.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.resource.Resource;
import org.osgi.service.event.Event;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

import com.adobe.cq.dam.cfm.converter.ContentTypeConverter;
import com.adobe.cq.wcm.core.components.models.contentfragment.DAMContentFragment;
import com.day.cq.wcm.api.PageEvent;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * This interface generate new Class Instance
 * 
 * @author daphny.cabiso
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
	 * @param url    of the method
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
	 * @param url    of the method
	 * @param params - list of params
	 * @return httpPost
	 * @throws URISyntaxException
	 */
	HttpPost getHttpPost(String url, List<NameValuePair> params) throws URISyntaxException;

	/**
	 * This method returns DAMContentFragment Instance
	 */
	DAMContentFragment getContentFragment(Resource fragmenResource, ContentTypeConverter contentTypeConverter,
			String variation, String[] elements);

	/**
	 * This method returns DAMContentFragment Instance
	 */
	DAMContentFragment getContentFragment(Resource fragmenResource, ContentTypeConverter contentTypeConverter,
			String variation, String[] elementNames, SlingHttpServletRequest request);

	/**
	 * This method returns DAMContentFragment Instance
	 */
	DAMContentFragment getContentFragmentImpl(Resource speaker, ContentTypeConverter contentTypeConverter,
			String variationName, String[] elementNames);

	TypeReference<List<String>> getTypeReferenceObj();

	OAuthRequest getB2CRequest(Verb verb, String endpoint);

	TypeReference<HashMap<String, String>> getTypeReferenceMapObj();

	/**
	 * @param dateFormat String that needs to be converted as date
	 * @return SimpleDateFormat
	 */
	SimpleDateFormat getSimpleDateFormat(String dateFormat);

	/**
	 * @param cookie String that needs to be decoded
	 * @return DecodedCookie
	 */
	String getDecodedCookie(String cookie) throws UnsupportedEncodingException;

	/**
	 * This method returns new PageEvent object from current event
	 * 
	 * @param Event event
	 * @return PageEvent
	 */
	PageEvent getPageEvent(Event event);

	/**
	 * This method returns true if current event is expired
	 * 
	 * @param ZonedDateTime current
	 * @param ZonedDateTime event
	 * @return Boolean isNotExpiredEvent
	 */
	Boolean isNotExpiredEvent(ZonedDateTime current, ZonedDateTime event);

	/**
	 * This method gets the http response
	 */
	HttpResponse<String> getHttpResponse(HttpClient client, HttpRequest request);

	/**
	 * The method to get the HttpRequest with default values
	 */
	HttpRequest getHttpPostRequestFromBuilder(String payload, String url, String token, String auth, String bearer,
			String contentType, String contentTypeValue);

	/**
	 * The method to get the HttpRequest with default values
	 */
	HttpRequest getHttpGetRequestBuilder(String authEndpoint, String authorization, String authHeader);

	/**
	 * The method to get the HttpClient with default values
	 */
	HttpClient getHttpClientFromBuilder(int connectionTimeout);

	/**
	 * The method to get the HttpRequest with default values
	 */
	HttpRequest getHttpGetRequestBuilderWithOriginHeader(String authEndpoint, String authorization, String authHeader,
			String origin, String originHeader);

	/**
	 * The method to get the HttpRequest with default values
	 */
	HttpRequest getHttpPostRequestFromBuilderWithOriginHeader(String payload, String url, String token, String auth,
			String bearer, String contenType, String contentTypeValue, String origin, String originHeader);

	/**
	 * The method to getURIBuilders
	 * 
	 * @param link
	 */
	URIBuilder getURIBuilders(String link);

	/**
	 * Gets the mode from AEM side.
	 */
	boolean isAuthorMode();

	/**
	 * Gets byte array from utils
	 */
	byte[] toByteArray(InputStream inputStram) throws IOException;

	/**
	 * Create the workbook factory
	 */
	Workbook getWorkBook(InputStream inputStream) throws IOException;
}
package com.aem.elastic.search.core.models;

/**
 * The class PageDocument
 * 
 * @author daphny.cabiso
 * @category Sling model
 */
public interface PageDocument {

	/**
	 * Indicates if page should be indexed
	 */
	Boolean doIndexPage();

	/**
	 * Indicates if page has redirect and should NOT be indexed
	 */
	Boolean hasPermanentRedirect();

	/**
	 * Provides the Page ID
	 */
	Boolean getPageID();

	/**
	 * Provides the name of the page resource
	 */
	Boolean getName();

	/**
	 * Provides the date for the page while it was last published or indexed
	 */
	Boolean getDateUpdated();

	/**
	 * Provides the page resource path
	 */
	Boolean getFullPath();

	/**
	 * Provides the cq:template attribute of the page
	 */
	Boolean getTemplate();

	/**
	 * Provides the template name
	 */
	Boolean getTemplateName();

	/**
	 * Provides the relative path of the Page
	 */
	Boolean getRelativePath();

	/**
	 * Provides geo site for the Page
	 */
	Boolean getCountrySite();

	/**
	 * Set full path of the page excluding jcr:content
	 * 
	 * @return resourcePath
	 */
	Boolean setFullPath(String resourcePath);

	/**
	 * Set last replication time-stamp
	 * 
	 * @return resourcePath
	 */
	Boolean setDateUpdated(String resourcePath);

	/**
	 * Set name of the page
	 * 
	 * @return resourcePath
	 */
	Boolean setName(String resourcePath);

	/**
	 * Set relative path of the page
	 * 
	 * @return resourcePath
	 */
	Boolean setRelativePath(String resourcePath);

	/**
	 * Set ID for the page
	 * 
	 * @return resourcePath
	 */
	Boolean setPageID(String resourcePath);

	/**
	 * Set geo site for the page
	 * 
	 * @return resourcePath
	 */
	Boolean setCountrySite(String resourcePath);

	/**
	 * Set current page local
	 * 
	 * @return geo
	 */
	void setCurrentPageLocale(String geo);

}
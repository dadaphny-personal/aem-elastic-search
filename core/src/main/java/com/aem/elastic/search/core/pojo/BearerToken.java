package com.aem.elastic.search.core.pojo;

import java.time.LocalDateTime;

import com.drew.lang.annotations.Nullable;

/**
 * BearerToken
 * 
 * @author daphny.cabiso
 */
//@Getter
//@Setter
@Nullable
public class BearerToken {

	/**
	 * Token
	 */
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * Token Expiration
	 */
	private LocalDateTime createdDateTime;
}

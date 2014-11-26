package org.jfan.web.ws.rs.ext.exception;

import java.util.HashMap;
import java.util.Map;

public class WebApplicationException extends RuntimeException {

	private static final long serialVersionUID = -1367105294973476060L;

	private String errorCode;
	private String errorMessage;
	private Map<String, Object> params;

	public WebApplicationException(final String errorCode, final String errorMessage) {
		this(errorCode, errorMessage, null, null);
	}

	public WebApplicationException(final String errorCode, final String errorMessage, final Map<String, Object> params) {
		this(errorCode, errorMessage, params, null);
	}

	public WebApplicationException(final String errorCode, final String errorMessage, final Map<String, Object> params, final Throwable th) {
		super(th);
		this.params = params;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public void addParam(String key, Object param) {
		if (null == params)
			params = new HashMap<String, Object>();
		params.put(key, param);
	}

	public void addAllParam(Map<String, Object> map) {
		if (null == params)
			params = new HashMap<String, Object>();
		params.putAll(map);
	}

	// ####
	// ## get func

	/**
	 * @return errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

}

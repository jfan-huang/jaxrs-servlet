package org.jfan.web.examples2.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.jfan.web.ws.rs.ext.Response;
import org.jfan.web.ws.rs.ext.exception.WebApplicationException;
import org.jfan.web.ws.rs.ext.interceptor.Interceptor;

public class RequestLogInterceptor extends Interceptor {

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.ext.interceptor.Interceptor#in(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public Response in(HttpServletRequest req) throws WebApplicationException {
//		System.out.println("接收到 getUser 请求...");
		return null;
	}

}

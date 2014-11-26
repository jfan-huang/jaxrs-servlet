/**
 * 
 */
package org.jfan.web.examples2.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfan.web.ws.rs.ext.Response;
import org.jfan.web.ws.rs.ext.exception.WebApplicationException;
import org.jfan.web.ws.rs.ext.interceptor.Interceptor;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 下午1:01:10
 */
public class NonInterceptor extends Interceptor {

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.ext.interceptor.Interceptor#in(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public Response in(HttpServletRequest req) throws WebApplicationException {
//		System.out.println("in ... ignore");
		return null;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.ext.interceptor.Interceptor#out(javax.servlet.http.
	 * HttpServletResponse, java.lang.Object)
	 */
	@Override
	public Response out(HttpServletResponse resp, Object result) throws WebApplicationException {
//		System.out.println("out ... ignore");
		return null;
	}

}

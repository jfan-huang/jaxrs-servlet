package org.jfan.web.examples2.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.jfan.web.ws.rs.ext.Response;
import org.jfan.web.ws.rs.ext.exception.WebApplicationException;
import org.jfan.web.ws.rs.ext.interceptor.Interceptor;

public class HeaderMarkInterceptor extends Interceptor {

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.ext.interceptor.Interceptor#in(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public Response in(HttpServletRequest req) throws WebApplicationException {
//		int i = 1;if(1 == i){ System.out.println("进入到HeaderMarkInterceptor，这里直接跳过。。。");return null;}
//
//		String mark = req.getHeader("mark");
//		return ("True".equals(mark) ? null : new Response(-9, "你Header中的校验值不正确，拒绝访问。"));
		return null;
	}

}

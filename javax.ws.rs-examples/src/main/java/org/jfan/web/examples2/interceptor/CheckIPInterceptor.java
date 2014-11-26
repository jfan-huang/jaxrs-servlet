package org.jfan.web.examples2.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.jfan.web.ws.rs.ext.Response;
import org.jfan.web.ws.rs.ext.exception.WebApplicationException;
import org.jfan.web.ws.rs.ext.interceptor.Interceptor;

public class CheckIPInterceptor extends Interceptor {

	private Response response = new Response(-9, "你不在授权访问的IP列表中。");

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.ext.interceptor.Interceptor#in(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public Response in(HttpServletRequest req) throws WebApplicationException {
//		String ip = getIpAddr(req);
//		return ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) ? null : response);
		return null;
	}

	public String getIpAddr(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

}

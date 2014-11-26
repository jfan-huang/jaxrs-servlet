/**
 * 
 */
package org.jfan.web.examples2.exception;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfan.web.ws.rs.ext.Response;
import org.jfan.web.ws.rs.ext.exception.ExceptionMapper;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月14日 下午6:47:05
 */
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.jfan.ws.rs.ext.exception.ExceptionMapper#toResult(org.jfan.ws.rs.
	 * ext.exception.WebApplicationException,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object[])
	 */
	@Override
	public Response toResult(UserNotFoundException exception, HttpServletRequest req, HttpServletResponse resp, Object[] args) {
		// ignore check
		Long uid = (Long) args[0];

		// log
		System.err.println("The Request: " + Arrays.toString(args) + ", ERROR: " + exception.getMessage());

		return new Response(-1, "没有你需要的用户数据：" + uid);
	}

}

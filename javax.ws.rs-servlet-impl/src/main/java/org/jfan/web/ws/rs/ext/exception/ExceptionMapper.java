package org.jfan.web.ws.rs.ext.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfan.web.ws.rs.ext.Response;

public interface ExceptionMapper<E extends WebApplicationException> {

	public Response toResult(E exception, HttpServletRequest req, HttpServletResponse resp, Object[] args);

}

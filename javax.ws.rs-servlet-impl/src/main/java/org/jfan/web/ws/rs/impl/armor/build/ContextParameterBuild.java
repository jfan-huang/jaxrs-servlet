/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor.build;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfan.web.ws.rs.impl.armor.ParameterBuild;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 下午5:19:30
 */
public class ContextParameterBuild extends ParameterBuild {

	private int mark;// 1:req 2:resp

	public ContextParameterBuild(Class<?> type) {
		super(type);
		if (ServletRequest.class.isAssignableFrom(type))
			mark = 1;
		else if (ServletResponse.class.isAssignableFrom(type))
			mark = 2;
		else
			throw new RuntimeException("'@Context' only supports 'ServletRequest' and 'ServletResponse'.");
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.impl.armor.ParameterBuild#build(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Object build(HttpServletRequest req, HttpServletResponse resp) {
		if (1 == mark)
			return req;
		return resp;
	}

}

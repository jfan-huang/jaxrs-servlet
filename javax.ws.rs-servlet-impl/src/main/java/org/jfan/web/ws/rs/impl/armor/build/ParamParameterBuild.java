/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor.build;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 下午5:19:30
 */
public class ParamParameterBuild extends SupportDefParameterBuild {

	private String key;

	public ParamParameterBuild(Class<?> type, String key) {
		super(type);
		this.key = key;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.jfan.ws.rs.impl.armor.build.SupportDefParameterBuild#value(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String value(HttpServletRequest req, HttpServletResponse resp) {
		String parameter = req.getParameter(key);
		if (null != parameter && null != defValue)
			return defValue;
		return parameter;
	}

}

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
public class HeaderParameterBuild extends SupportDefParameterBuild {

	private String key;

	public HeaderParameterBuild(Class<?> type, String key) {
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
		String header = req.getHeader(key);
		if (null == header && null != defValue)
			return defValue;
		return header;
	}

}

/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor.build;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfan.web.ws.rs.impl.armor.ParameterBuild;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 下午5:19:30
 */
public abstract class SupportDefParameterBuild extends ParameterBuild {

	protected String defValue;

	public SupportDefParameterBuild(Class<?> type) {
		super(type);
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public abstract String value(HttpServletRequest req, HttpServletResponse resp);

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.impl.armor.ParameterBuild#build(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Object build(HttpServletRequest req, HttpServletResponse resp) {
		String value = value(req, resp);
		if (null == value)
			if (null == defValue)
				return null;
			else
				value = defValue;
		return value2basicObject(value);
	}

}

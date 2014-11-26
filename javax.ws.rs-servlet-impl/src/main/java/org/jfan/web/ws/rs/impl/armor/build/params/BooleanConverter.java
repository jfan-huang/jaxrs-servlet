/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor.build.params;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.ParamConverter;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月14日 下午7:15:24
 */
public class BooleanConverter implements ParamConverter<Boolean> {

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.ws.rs.ext.ParamConverter#getType()
	 */
	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.ws.rs.ext.ParamConverter#from(java.lang.Class,
	 * java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Boolean from(Class<?> clz, String value, HttpServletRequest req) {
		boolean ok = Boolean.parseBoolean(value);
		if (!(ok) && !("false".equalsIgnoreCase(value)))
			return "1".equals(value);
		return ok;
	}

}

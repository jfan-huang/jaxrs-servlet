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
 * @author JFan - 2014年11月14日 下午7:06:28
 */
public class LongConverter implements ParamConverter<Long> {

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.ws.rs.ext.ParamConverter#getType()
	 */
	@Override
	public Class<Long> getType() {
		return Long.class;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.ws.rs.ext.ParamConverter#from(java.lang.Class,
	 * java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Long from(Class<?> clz, String value, HttpServletRequest req) {
		return Long.parseLong(value);
	}

}

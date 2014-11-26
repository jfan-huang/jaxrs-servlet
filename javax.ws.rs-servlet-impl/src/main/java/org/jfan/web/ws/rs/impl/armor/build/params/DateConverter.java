/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor.build.params;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.ParamConverter;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月14日 下午7:07:11
 */
public class DateConverter implements ParamConverter<Date> {

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.ws.rs.ext.ParamConverter#getType()
	 */
	@Override
	public Class<Date> getType() {
		return Date.class;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.ws.rs.ext.ParamConverter#from(java.lang.Class,
	 * java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Date from(Class<?> clz, String value, HttpServletRequest req) {
		long time = Long.parseLong(value);
		return new Date(time);
	}

}

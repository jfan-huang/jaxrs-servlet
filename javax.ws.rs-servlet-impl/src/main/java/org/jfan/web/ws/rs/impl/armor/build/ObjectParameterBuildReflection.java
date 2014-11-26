/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor.build;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.ParamConverter;

import org.jfan.web.ws.rs.impl.armor.ParameterBuild;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 下午5:19:30
 */
public class ObjectParameterBuildReflection extends ParameterBuild {
	
	private ParamConverter<?> converter;

	public ObjectParameterBuildReflection(Class<?> type) {
		super(type);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.impl.armor.ParameterBuild#build(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Object build(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println(converter);
		// TODO 自动生成的方法存根
		return null;
	}

}

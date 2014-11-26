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
public class ObjectParameterBuild extends ParameterBuild {

	private ParamConverter<?> converter;
	private ObjectParameterBuildReflection reflection;

	public ObjectParameterBuild(Class<?> type, ParamConverter<?> converter) {
		super(type);
		if (null != converter)
			this.converter = converter;
//		else TODO 待实现
//			reflection = new ObjectParameterBuildReflection(type);
		else
			throw new RuntimeException("XXXXXXXXXXXXX");
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.impl.armor.ParameterBuild#build(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Object build(HttpServletRequest req, HttpServletResponse resp) {
		if (null != converter)
			return converter.from(type, null, req);
		return reflection.build(req, resp);
	}

}

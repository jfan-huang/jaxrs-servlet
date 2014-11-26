/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.ParamConverter;

import org.jfan.web.ws.rs.impl.armor.build.params.BooleanConverter;
import org.jfan.web.ws.rs.impl.armor.build.params.DateConverter;
import org.jfan.web.ws.rs.impl.armor.build.params.IntegerConverter;
import org.jfan.web.ws.rs.impl.armor.build.params.LongConverter;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 下午5:05:18
 */
public abstract class ParameterBuild {

	protected Class<?> type;

	public ParameterBuild(Class<?> type) {
		this.type = type;
	}

	public abstract Object build(HttpServletRequest req, HttpServletResponse resp);

	// index 0:long 1:int 2:boolean 3:date
	ParamConverter<?>[] pcs = new ParamConverter<?>[] { new LongConverter(), new IntegerConverter(), new BooleanConverter(), new DateConverter() };

	public Object value2basicObject(String value) {
		if (String.class.equals(type))
			return value;
		if (Long.class.equals(type) || long.class.equals(type))
			return pcs[0].from(type, value, null);
		if (Integer.class.equals(type) || int.class.equals(type))
			return pcs[1].from(type, value, null);
		if (Boolean.class.equals(type) || boolean.class.equals(type))
			return pcs[2].from(type, value, null);
		if (Date.class.equals(type))
			return pcs[3].from(type, value, null);
		throw new RuntimeException("The conversion is not supported for this type, basic types only support.");
	}

}

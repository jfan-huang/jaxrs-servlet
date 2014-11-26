package org.jfan.web.ws.rs.ext.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfan.web.ws.rs.ext.Response;
import org.jfan.web.ws.rs.ext.exception.WebApplicationException;

/**
 * 拦截器具体处理类，这里定义为抽象类，具有in（入拦截）和out（出拦截）两个方法。 <br>
 * <br>
 * 
 * 这里是两个空实现，具体子类根据需要进行覆盖
 * 
 * @author JFan - 2014年11月17日 下午2:32:27
 */
public abstract class Interceptor {

	/**
	 * 接收到请求，在进入到具体处理方法之前，的拦截器处理方法
	 * 
	 * 如果返回Response为空，则交由下一个对象处理
	 * 
	 * 如果返回Response有值，则中断处理，直接返回这个Response
	 */
	public Response in(HttpServletRequest req) throws WebApplicationException {
		// non
		return null;
	}

	/**
	 * 处理方法，返回结果后，交由此处处理，result是处理方法的返回值
	 * 
	 * 如果返回Response为空，则交由下一个对象处理
	 * 
	 * 如果返回Response有值，则中断处理，直接返回这个Response
	 */
	public Response out(HttpServletResponse resp, Object result) throws WebApplicationException {
		// non
		return null;
	}

}

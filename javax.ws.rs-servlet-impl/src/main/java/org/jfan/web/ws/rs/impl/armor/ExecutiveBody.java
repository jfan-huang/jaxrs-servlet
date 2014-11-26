/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor;

import java.lang.reflect.Method;

import org.jfan.web.ws.rs.impl.JAXRSServlet;

/**
 * 执行器对象 <br>
 * <br>
 * 通过URL定位到唯一的ExecutiveBody对象，即可开始执行调用（拦截+进入方法）
 * 
 * @author JFan - 2014年11月17日 上午10:37:38
 */
public class ExecutiveBody implements Comparable<ExecutiveBody> {

	public static enum HttpMethod {
		GET, POST, PUT, DELETE, HEAD, OPTIONS;
	}

	public ExecutiveBody(String path) {
		this.path = path;
		this.startPath = startpath(path);
	}

	private String path;
	private String startPath;
	private HttpMethod[] useHttpMethods;

	// 1
	private ParcelInterceptor beforeInterceptors;

	// 2
	private ParameterBody paramBody;
	private Method method;
	private Object object;

	// 3
	private ParcelInterceptor afterInterceptors;

	/*
	 * （非 Javadoc）
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ExecutiveBody o) {
		return startPath.compareTo(o.startPath);
	}

	private String startpath(String str) {
		int index = str.indexOf(JAXRSServlet.PARAM_SYMBOL_LEFT);
		if (-1 == index)
			return str;
		return str.substring(0, index);
	}

	/**
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return startPath
	 */
	public String getStartPath() {
		return startPath;
	}

	/**
	 * @return useHttpMethods
	 */
	public HttpMethod[] getUseHttpMethods() {
		return useHttpMethods;
	}

	/**
	 * @param useHttpMethods 要设置的 useHttpMethods
	 */
	public void setUseHttpMethods(HttpMethod[] useHttpMethods) {
		this.useHttpMethods = useHttpMethods;
	}

	/**
	 * @return beforeInterceptors
	 */
	public ParcelInterceptor getBeforeInterceptors() {
		return beforeInterceptors;
	}

	/**
	 * @param beforeInterceptors 要设置的 beforeInterceptors
	 */
	public void setBeforeInterceptors(ParcelInterceptor beforeInterceptors) {
		this.beforeInterceptors = beforeInterceptors;
	}

	/**
	 * @return paramBody
	 */
	public ParameterBody getParamBody() {
		return paramBody;
	}

	/**
	 * @param paramBody 要设置的 paramBody
	 */
	public void setParamBody(ParameterBody paramBody) {
		this.paramBody = paramBody;
	}

	/**
	 * @return method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @param method 要设置的 method
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/**
	 * @return object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object 要设置的 object
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @return afterInterceptors
	 */
	public ParcelInterceptor getAfterInterceptors() {
		return afterInterceptors;
	}

	/**
	 * @param afterInterceptors 要设置的 afterInterceptors
	 */
	public void setAfterInterceptors(ParcelInterceptor afterInterceptors) {
		this.afterInterceptors = afterInterceptors;
	}

}

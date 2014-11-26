package org.jfan.web.ws.rs.impl.armor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一个具体的 处理方法 的入参，具体描述（一对一） <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 上午11:22:21
 */
public class ParameterBody {

	private Class<?>[] parameterTypes;

	private ParameterBuild[] paramBuilds;

	public ParameterBody(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
		paramBuilds = new ParameterBuild[this.parameterTypes.length];
	}

	public void add(int index, ParameterBuild paramBuild) {
		paramBuilds[index] = paramBuild;
	}

	public Object[] build(HttpServletRequest req, HttpServletResponse resp) {
		if (null == parameterTypes)
			return null;
		int length = parameterTypes.length;
		Object[] args = new Object[length];
		for (int index = 0; index < length; index++)
			args[index] = paramBuilds[index].build(req, resp);
		return args;
	}

	/**
	 * @return parameterTypes
	 */
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	/**
	 * @param parameterTypes 要设置的 parameterTypes
	 */
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	/**
	 * @return paramBuilds
	 */
	public ParameterBuild[] getParamBuilds() {
		return paramBuilds;
	}

	/**
	 * @param paramBuilds 要设置的 paramBuilds
	 */
	public void setParamBuilds(ParameterBuild[] paramBuilds) {
		this.paramBuilds = paramBuilds;
	}

}

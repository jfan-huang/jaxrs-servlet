/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor;

import org.jfan.web.ws.rs.ext.interceptor.Interceptor;
import org.jfan.web.ws.rs.utils.Args;

/**
 * 对拦截器Interceptor进行包装 <br>
 * <br>
 * Interceptor是单个实例的，那么同一个inter会在多个地方用到，再说inter是有顺序的，那么这里对顺序进行包装
 * 
 * @author JFan - 2014年11月17日 上午10:43:34
 */
public class ParcelInterceptor {

	private int index;
	private Interceptor interceptor;
	private ParcelInterceptor nextInterceptor;

	public ParcelInterceptor(int index, Interceptor interceptor) {
		Args.notNull(interceptor, "The current package Interceptor");
		this.index = index;
		this.interceptor = interceptor;
	}

	/**
	 * @return nextInterceptor
	 */
	public ParcelInterceptor getNextInterceptor() {
		return nextInterceptor;
	}

	/**
	 * @param nextInterceptor 要设置的 nextInterceptor
	 */
	public void setNextInterceptor(ParcelInterceptor nextInterceptor) {
		this.nextInterceptor = nextInterceptor;
	}

	/**
	 * @return index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return interceptor
	 */
	public Interceptor getInterceptor() {
		return interceptor;
	}

}

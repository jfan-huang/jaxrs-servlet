/**
 * 
 */
package org.jfan.web;

import javax.ws.rs.ext.ParamConverter;

import org.jfan.web.examples1.HelloWorldResource;
import org.jfan.web.examples2.A;
import org.jfan.web.examples2.interceptor.CheckIPInterceptor;
import org.jfan.web.examples2.interceptor.HeaderMarkInterceptor;
import org.jfan.web.examples2.interceptor.NonInterceptor;
import org.jfan.web.examples2.interceptor.RequestLogInterceptor;
import org.jfan.web.examples2.param.UserConverter;
import org.jfan.web.examples2.resource.UserResource;
import org.jfan.web.ws.rs.ext.interceptor.Interceptor;
import org.jfan.web.ws.rs.impl.WebComponent;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月18日 下午12:54:47
 */
public class ExamplesWebComponentImpl implements WebComponent {

	public ExamplesWebComponentImpl() {
		System.out.println(A.class);// 包没有被加载到
//		System.out.println("************************************************************");
//		System.out.println("************************************************************");
//		System.out.println("************************************************************");
//		System.out.println("************************************************************");
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.impl.WebComponent#findAllResource(java.lang.String[])
	 */
	@Override
	public Object[] findAllResource(String[] packs) {
//		System.out.println("+++++++++++++++++++++++ findAllResource " + Arrays.toString(packs));
		return new Object[] { new HelloWorldResource(), new UserResource() };
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.jfan.ws.rs.impl.WebComponent#getAllObject(java.lang.String[],
	 * java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] getAllObject(String[] packs, Class<T> clz) {
//		System.out.println("+++++++++++++++++++++++ getAllObject clz: " + clz + ", packs: " + Arrays.toString(packs));
		if (Interceptor.class.equals(clz))
			return (T[]) new Interceptor[] { new RequestLogInterceptor(), new NonInterceptor(), new HeaderMarkInterceptor(), new CheckIPInterceptor() };
		if (ParamConverter.class.equals(clz))
			return (T[]) new ParamConverter[] { new UserConverter() };
		return null;
	}

}

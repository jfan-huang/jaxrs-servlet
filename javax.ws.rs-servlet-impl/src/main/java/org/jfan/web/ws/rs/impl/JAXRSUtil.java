/**
 * 
 */
package org.jfan.web.ws.rs.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.regex.Pattern;

import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Param;
import javax.ws.rs.ext.ParamConverter;

import org.jfan.web.ws.rs.ext.interceptor.AfterInterceptor;
import org.jfan.web.ws.rs.ext.interceptor.BeforeInterceptor;
import org.jfan.web.ws.rs.ext.interceptor.Interceptor;
import org.jfan.web.ws.rs.impl.armor.ParameterBody;
import org.jfan.web.ws.rs.impl.armor.ParameterBuild;
import org.jfan.web.ws.rs.impl.armor.ParcelInterceptor;
import org.jfan.web.ws.rs.impl.armor.ExecutiveBody.HttpMethod;
import org.jfan.web.ws.rs.impl.armor.build.ContextParameterBuild;
import org.jfan.web.ws.rs.impl.armor.build.HeaderParameterBuild;
import org.jfan.web.ws.rs.impl.armor.build.ObjectParameterBuild;
import org.jfan.web.ws.rs.impl.armor.build.ParamParameterBuild;
import org.jfan.web.ws.rs.impl.armor.build.PathParameterBuild;
import org.jfan.web.ws.rs.impl.armor.build.SupportDefParameterBuild;
import org.jfan.web.ws.rs.utils.Args;
import org.jfan.web.ws.rs.utils.TextUtils;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 下午3:28:05
 */
public final class JAXRSUtil {

//	/**
//	 * 去除两头的c字符（只判断两头的一个字符，不往内走）
//	 * 
//	 * 'abc' -- 'abc'<br>
//	 * '/abc' -- 'abc'<br>
//	 * '/abcdef/' -- 'abcdef'<br>
//	 * '/abc/def/' -- 'abc/def'<br>
//	 * '/abc/def/ ' -- 'abc/def/ '
//	 */
//	public static final String shave(String str, char c) {
//		String str_ = str.trim();
//		boolean tou = false;
//		boolean wei = false;
//		if (c == str_.charAt(0))
//			tou = true;
//		if (c == str_.charAt(str_.length() - 1))
//			wei = true;
//		if (tou || wei)
//			return str_.substring(tou ? 1 : 0, str_.length() - (wei ? 1 : 0));
//		return str_;
//	}

	/**
	 * 根据clClz类名，返回WebComponent的实现类（实例化的）
	 * 
	 * 如果clClz没有传递，则使用默认实现WebComponentImpl
	 */
	protected static final WebComponent webComponent(String clClz) {
		Class<?> clzss = WebComponent.class;

		if (TextUtils.isBlank(clClz)) {
			ServiceLoader<?> serviceLoader = ServiceLoader.load(clzss);
			List<Object> list = new ArrayList<Object>();
			Iterator<?> iterator = serviceLoader.iterator();
			while (iterator.hasNext())
				list.add(iterator.next());

			if (0 >= list.size())
				return new WebComponentImpl();

			Args.check(1 != list.size(), "Implementation of class '" + clzss + "' is not the only.");
			return (WebComponent) list.get(0);
		} else {
			try {
				Class<?> clz = Class.forName(clClz);
				Args.check(clzss.isAssignableFrom(clz), "'" + clz + "' is not a subclass of '" + clzss + "'.");

				return (WebComponent) clz.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 路径providerPackage，按照指定的分隔符号，进行分割，并返回
	 */
	protected static final String[] scanningPath(String providerPackage, char[] separators) {
		Args.notEmpty(providerPackage, "Init Param '" + JAXRSServlet.PROVIDER_PACKAGES + "'");

		List<String> tokens = new LinkedList<String>();

		final StringBuilder regexpBuilder = new StringBuilder(separators.length * 3);
		regexpBuilder.append('[');
		for (char c : separators)
			regexpBuilder.append(Pattern.quote(String.valueOf(c)));
		regexpBuilder.append(']');

		String[] tokenArray = providerPackage.split(regexpBuilder.toString());
		for (String token : tokenArray) {
			if (token == null || token.isEmpty())
				continue;

			token = token.trim();
			if (token.isEmpty())
				continue;

			tokens.add(token);
		}

		return tokens.toArray(new String[tokens.size()]);
	}

	/**
	 * 获取 处理方法 支持的HttpMethod集合
	 */
	protected static final HttpMethod[] useHttpMethod(Method method) {
		Set<HttpMethod> hml = new HashSet<HttpMethod>();
		if (null != method.getAnnotation(GET.class))
			hml.add(HttpMethod.GET);
		if (null != method.getAnnotation(POST.class))
			hml.add(HttpMethod.POST);
		if (null != method.getAnnotation(PUT.class))
			hml.add(HttpMethod.PUT);
		if (null != method.getAnnotation(DELETE.class))
			hml.add(HttpMethod.DELETE);
		if (null != method.getAnnotation(HEAD.class))
			hml.add(HttpMethod.HEAD);
		if (null != method.getAnnotation(OPTIONS.class))
			hml.add(HttpMethod.OPTIONS);
		return hml.toArray(new HttpMethod[hml.size()]);
	}

	/**
	 * 构造处理方法入参，识别器
	 */
	protected static final ParameterBody parameterBody(Method method, String mpath, Map<Class<?>, ParamConverter<?>> converterMap) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		ParameterBody body = new ParameterBody(parameterTypes);

		int length = parameterTypes.length;
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		for (int index = 0; index < length; index++) {
			Class<?> type = parameterTypes[index];
			ParameterBuild paramBuild = null;

			String defValue = null;

			Annotation[] as = parameterAnnotations[index];
			for (Annotation a : as) {
				if (a instanceof Context) {
					paramBuild = new ContextParameterBuild(type);
					break;
				}
				if (a instanceof BeanParam) {
					ParamConverter<?> converter = converterMap.get(type);
					paramBuild = new ObjectParameterBuild(type, converter);
					break;
				}

				if (a instanceof PathParam) {
					paramBuild = new PathParameterBuild(type, ((PathParam) a).value(), mpath);
					// } else if (a instanceof QueryParam) {
					// paramBuild = new QueryParameterBuild(type, ((QueryParam)
					// a).value());
					// } else if (a instanceof FormParam) {
					// paramBuild = new FormParameterBuild(type, ((FormParam)
					// a).value());
				} else if (a instanceof HeaderParam) {
					paramBuild = new HeaderParameterBuild(type, ((HeaderParam) a).value());
				} else if (a instanceof Param) {
					paramBuild = new ParamParameterBuild(type, ((Param) a).value());
				} else if (a instanceof DefaultValue) {
					defValue = ((DefaultValue) a).value();
				} else {
					throw new RuntimeException("For the available annotations known '" + a.getClass() + "'.");
				}
			}

			if (null == paramBuild)
				throw new RuntimeException("The converter for participation in knowledge: " + method + "(" + index + "|" + type + ").");

			if (paramBuild instanceof SupportDefParameterBuild)
				((SupportDefParameterBuild) paramBuild).setDefValue(defValue);
			body.add(index, paramBuild);
		}

		return body;
	}

	/**
	 * 根据 处理方法 所在的 类、包，进行搜索，检索出所有拦截器class
	 * 
	 * 去重（保留先入的）
	 */
	protected static final List<Class<? extends Interceptor>> selectInterClz(Package[] allPacks, Class<?> clz, Method method, boolean inOrOut) {
		List<Class<? extends Interceptor>> list = new LinkedList<Class<? extends Interceptor>>();

		if (inOrOut) {
			// add pack
			selectInterClz(list, allPacks, inOrOut);
			// add clz
			BeforeInterceptor bf = clz.getAnnotation(BeforeInterceptor.class);
			if (null != bf)
				set(list, bf.value());
			// add func
			BeforeInterceptor mbf = method.getAnnotation(BeforeInterceptor.class);
			if (null != mbf)
				set(list, mbf.value());
		} else {
			// add func
			AfterInterceptor maf = method.getAnnotation(AfterInterceptor.class);
			if (null != maf)
				set(list, maf.value());
			// add clz
			AfterInterceptor af = clz.getAnnotation(AfterInterceptor.class);
			if (null != af)
				set(list, af.value());
			// add pack
			selectInterClz(list, allPacks, inOrOut);
		}

		return list;
	}

	/**
	 * 根据正序或者反序（zhengxu），对interList进行压缩
	 * 
	 * 压缩成一个ParcelInterceptor对象（存储的是实例化后的 Interceptor）
	 */
	protected static final ParcelInterceptor parcel(List<Class<? extends Interceptor>> interList//
			, boolean zhengxu, Map<Class<? extends Interceptor>, Interceptor> interceptorMap) {
		if (null == interList || 0 >= interList.size())
			return null;

		int index = 1;
		int length = interList.size();
		int i = (zhengxu ? 0 : length - 1);
		Class<? extends Interceptor> clz = interList.get(i);
		Interceptor interceptor = interceptorMap.get(clz);
		ParcelInterceptor pi = new ParcelInterceptor(index++, interceptor);

		if (2 <= length) {
			boolean go = true;
			ParcelInterceptor nextpi = null;
			for (; go;) {
				i = (zhengxu ? i + 1 : i - 1);

				clz = interList.get(i);
				interceptor = interceptorMap.get(clz);
				ParcelInterceptor pi_ = new ParcelInterceptor(index++, interceptor);

				if (null == nextpi)
					pi.setNextInterceptor(pi_);
				else
					nextpi.setNextInterceptor(pi_);
				nextpi = pi_;

				go = (zhengxu ? i < length - 1 : i > 0);
			}
		}

		return pi;
	}

	/**
	 * 从pack包开始，追朔到‘根’包为止，返回各层包的集合（有序，包路径排序）
	 * 
	 * packs是追朔到为止的跟‘packs’（可以理解为，扫描路径的起始包），需要认为保证列表中没有“完全覆盖”的包存在
	 * 
	 * <code>
		Package p = Package.getPackage("org.jfan.ws.rs.impl.armor.zzz");
		String[] ps = { "org.jfan.ws.rs.ext", "org.jfan.ws.rs.impl" };
		Package[] pss = toFindTheRoot(p, ps);
	 * </code>
	 */
	// TODO 此方法依赖于，必须已经加载了目标类，目标（包）才存在，能够读到，否则得到null
	protected static final Package[] toFindTheRoot(Package pack, String[] packs) {
		String rootPack = "";
		String packStr = pack.getName();
		for (String str : packs)
			if (packStr.startsWith(str))
				rootPack = str;

		String[] packSplit = packStr.split("[.]");
		String[] rootSplit = rootPack.split("[.]");

		int sLength = packSplit.length;
		Set<Package> set = new LinkedHashSet<Package>();
		for (int i = rootSplit.length - 1; i < sLength; i++) {
			String packPath = "";
			for (int j = 0; j <= i; j++) {
				if (0 < j)
					packPath += ".";
				packPath += packSplit[j];
			}
			set.add(Package.getPackage(packPath));
		}

		return set.toArray(new Package[set.size()]);
	}

	/**
	 * 将Class上的@Path.value 和 Method上的@Path.value 组成 pathInfo
	 */
	protected static final String pathInfo(String cpath, String mpath) {
		String str = "" + JAXRSServlet.SHAVE;
		boolean cadd = cpath.startsWith(str);
		boolean cend = cpath.endsWith(str);
		boolean madd = mpath.startsWith(str);
		boolean sub = (cend && madd);
		boolean app = (!cend && !madd);
		return (cadd ? "" : str) + cpath + (app ? str : "") + (sub ? mpath.substring(1) : mpath);
	}

	// ####
	// ## private func

	private static final void selectInterClz(List<Class<? extends Interceptor>> list, Package[] allPacks, boolean inOrOut) {
		if (inOrOut) {
			for (Package pack : allPacks) {
				BeforeInterceptor annotation = pack.getAnnotation(BeforeInterceptor.class);
				if (null != annotation)
					set(list, annotation.value());
			}
		} else {
			for (int i = allPacks.length - 1; i >= 0; i--) {
				Package pack = allPacks[i];
				AfterInterceptor annotation = pack.getAnnotation(AfterInterceptor.class);
				if (null != annotation)
					set(list, annotation.value());
			}
		}
	}

	private static final void set(List<Class<? extends Interceptor>> list, Class<? extends Interceptor>[] value) {
		if (null != value)
			for (Class<? extends Interceptor> clz : value)
				list.add(clz);
	}

}

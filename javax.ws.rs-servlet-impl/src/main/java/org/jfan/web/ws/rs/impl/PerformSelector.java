package org.jfan.web.ws.rs.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.ext.ParamConverter;

import org.jfan.web.ws.rs.ext.Response;
import org.jfan.web.ws.rs.ext.interceptor.Interceptor;
import org.jfan.web.ws.rs.impl.armor.ExecutiveBody;
import org.jfan.web.ws.rs.impl.armor.ParcelInterceptor;
import org.jfan.web.ws.rs.impl.armor.ExecutiveBody.HttpMethod;
import org.jfan.web.ws.rs.utils.TextUtils;

/**
 * 根据http请求，定位到一个具体的ExecutiveBody，并提交处理 <br>
 * <br>
 * 此类包含ExecutiveBody初始化动作
 * 
 * @author JFan - 2014年11月17日 下午12:35:43
 */
public class PerformSelector {

	private WebComponent webComponent;
	private char shave = JAXRSServlet.SHAVE;

	private Map<Integer, ExecutiveBody[]> pathPerformMap = new HashMap<Integer, ExecutiveBody[]>();
	private Map<Class<?>, ParamConverter<?>> converterMap = new HashMap<Class<?>, ParamConverter<?>>();
	private Map<Class<? extends Interceptor>, Interceptor> interceptorMap = new HashMap<Class<? extends Interceptor>, Interceptor>();

	public PerformSelector(WebComponent webComponent) {
		this.webComponent = webComponent;
	}

	public void start(String[] packs) {
		// init All Interceptor
		Interceptor[] allInter = webComponent.getAllObject(packs, Interceptor.class);
		if (null != allInter)
			for (Interceptor inter : allInter)
				interceptorMap.put(inter.getClass(), inter);

		// init All ParamConverter
		ParamConverter<?>[] allConver = webComponent.getAllObject(packs, ParamConverter.class);
		if (null != allConver)
			for (ParamConverter<?> conver : allConver)
				converterMap.put(conver.getType(), conver);

		// inti Path Perform Map
		Object[] findAllObject = webComponent.findAllResource(packs);
		for (Object object : findAllObject) {
			Class<?> clz = object.getClass();
			Path cp = clz.getAnnotation(Path.class);
			String cpath = cp.value();

			Package pack = clz.getPackage();
			Package[] allPacks = JAXRSUtil.toFindTheRoot(pack, packs);

			for (Method method : clz.getMethods()) {
				Path mp;
				if (null == (mp = method.getAnnotation(Path.class)))
					continue;
				String mpath = mp.value();
				mpath = JAXRSUtil.pathInfo(cpath, mpath);

				ExecutiveBody body = new ExecutiveBody(mpath);
				body.setMethod(method);
				body.setUseHttpMethods(JAXRSUtil.useHttpMethod(method));
				body.setObject(object);
				body.setParamBody(JAXRSUtil.parameterBody(method, mpath, converterMap));

				List<Class<? extends Interceptor>> inInterList = JAXRSUtil.selectInterClz(allPacks, clz, method, true);
				ParcelInterceptor inParcel = JAXRSUtil.parcel(inInterList, true, interceptorMap);
				body.setBeforeInterceptors(inParcel);

				List<Class<? extends Interceptor>> outInterList = JAXRSUtil.selectInterClz(allPacks, clz, method, false);
				ParcelInterceptor outParcel = JAXRSUtil.parcel(outInterList, false, interceptorMap);
				body.setAfterInterceptors(outParcel);

				// put 2 map
				Integer integer = TextUtils.number4symbols(mpath, shave);
				putMap(integer, body);
			}
		}
	}

	// >
	// > url-pattern : /pp/*
	// > request : http://localhost:8080/webapi/pp/ww/abcdef?name=Java
	// <
	// < requestURL : http://localhost:8080/webapi/pp/ww/abcdef
	// < requestURI : /webapi/pp/ww/abcdef
	// < contextPath : /webapi
	// < servletPath : /pp
	// < queryString : name=Java
	// < pathInfo : /ww/abcdef
	public void service(HttpServletRequest req, HttpServletResponse resp) {
		String pathInfo = req.getPathInfo();
		Integer integer = TextUtils.number4symbols(pathInfo, shave);
		ExecutiveBody[] bodys = pathPerformMap.get(integer);

		ExecutiveBody body = null;
		for (ExecutiveBody body_ : bodys) {
			if (pathInfo.startsWith(body_.getStartPath())) {
				body = body_;
				break;
			}
		}

		// check HttpMethod
		if (null == body || noSuch(req.getMethod(), body.getUseHttpMethods())) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		try {
			resp.setContentType("application/Json;charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = resp.getWriter();
			Response result = new PerformInvoke(body).invoke(req, resp);
			writer.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ####
	// ## private func

	private boolean noSuch(String httpMethod, HttpMethod[] useHttpMethods) {
		if (null != useHttpMethods)
			for (HttpMethod useHttpMethod : useHttpMethods)
				if (httpMethod.equals(useHttpMethod.toString()))
					return false;
		return true;
	}

	private void putMap(Integer integer, ExecutiveBody body) {
		ExecutiveBody[] ebs = pathPerformMap.get(integer);
		if (null == ebs) {
			ebs = new ExecutiveBody[] { body };
		} else {
			ebs = Arrays.copyOf(ebs, ebs.length + 1);
			ebs[ebs.length - 1] = body;
			Arrays.sort(ebs);
		}
		pathPerformMap.put(integer, ebs);
	}

}

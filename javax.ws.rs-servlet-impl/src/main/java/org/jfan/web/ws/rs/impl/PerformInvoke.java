package org.jfan.web.ws.rs.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfan.web.ws.rs.ext.Response;
import org.jfan.web.ws.rs.ext.exception.WebApplicationException;
import org.jfan.web.ws.rs.impl.armor.ExecutiveBody;
import org.jfan.web.ws.rs.impl.armor.ParcelInterceptor;

/**
 * 执行ExecutiveBody对象<br>
 * 
 * 包含<br>
 * 1：执行in拦截<br>
 * 2：构造参数<br>
 * 3：执行方法<br>
 * 4：执行out拦截<br>
 * <br>
 * 
 * 将来可以考虑异步执行
 * 
 * @author JFan - 2014年11月19日 下午7:33:10
 */
public class PerformInvoke {

	private ExecutiveBody body;

	public PerformInvoke(ExecutiveBody body) {
		this.body = body;
	}

	public Response invoke(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Response out;

			// in inter
			out = runInter(true, null, body.getBeforeInterceptors(), req, resp);
			if (null != out)
				return out;

			// exec
			Object[] args = body.getParamBody().build(req, resp);
			Method method = body.getMethod();
			Object obj = body.getObject();

			Object result = method.invoke(obj, args);

			// out inter
			out = runInter(false, result, body.getAfterInterceptors(), req, resp);
			if (null != out)
				return out;

			// ok out
			out = new Response(1, null, result);
			return out;
		} catch (WebApplicationException e) {
			return new Response(-1, "business exceptions", e.getMessage());
		} catch (Exception e) {
			return new Response(-99, "invoke error", e.getMessage());
		}
	}

	// ####
	// ## private func

	private Response runInter(boolean inOrOut, Object result, ParcelInterceptor parcel, HttpServletRequest req, HttpServletResponse resp) throws WebApplicationException {
		if (null == parcel)
			return null;
		Response out;
		if (inOrOut)
			out = parcel.getInterceptor().in(req);
		else
			out = parcel.getInterceptor().out(resp, result);
		if (null != out)
			return out;

		// next
		return runInter(inOrOut, result, parcel.getNextInterceptor(), req, resp);
	}

}

package javax.ws.rs.ext;

import javax.servlet.http.HttpServletRequest;

public interface ParamConverter<T> {

	public Class<T> getType();

	/**
	 * 将请求参数转换成 Resource方法的入参 <br>
	 * 
	 * 如果是@QueryParam、@PathParam、@FormParam、@HeaderParam 传递value参数，req为空<br>
	 * 
	 * 如果是@BeanParam 传递第req参数，value为空<br>
	 */
	public T from(Class<?> clz, String value, HttpServletRequest req);

}

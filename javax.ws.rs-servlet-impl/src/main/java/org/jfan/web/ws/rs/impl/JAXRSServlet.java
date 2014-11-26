/**
 * 
 */
package org.jfan.web.ws.rs.impl;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月14日 下午7:13:15
 */
public class JAXRSServlet extends HttpServlet {// implements Filter {

	private static final long serialVersionUID = -347018796480434884L;

	public static final char SHAVE = '/';
	public static final char PARAM_SYMBOL_LEFT = '{';
	public static final char PARAM_SYMBOL_RIGHT = '}';

	public static final String PROVIDER_PACKAGES = "jaxrs.server.provider.packages";
	public static final String PROVIDER_CLASSLOADING = "jaxrs.server.provider.classloading";

	private PerformSelector selector;

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// init WebComponent Impl
		String clClz = config.getInitParameter(PROVIDER_CLASSLOADING);
		WebComponent webComponent = JAXRSUtil.webComponent(clClz);

		// init Scanning Path
		String pr_pa = config.getInitParameter(PROVIDER_PACKAGES);
		String[] packs = JAXRSUtil.scanningPath(pr_pa, " ,;\n".toCharArray());

		// init Selector
		selector = new PerformSelector(webComponent);

		selector.start(packs);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)
	 */
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse))
			throw new ServletException("non-HTTP request or response");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		service(request, response);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		selector.service(req, resp);
	}

}

/**
 * 
 */
package org.jfan.web.ws.rs.impl.armor.build;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfan.web.ws.rs.impl.JAXRSServlet;
import org.jfan.web.ws.rs.utils.TextUtils;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 下午5:19:30
 */
public class PathParameterBuild extends SupportDefParameterBuild {

	private int index;
	private String key;
	private String mpath;
	private char shave = JAXRSServlet.SHAVE;

	// private String split = "[" + shave + "]";

	public PathParameterBuild(Class<?> type, String key, String mpath) {
		super(type);
		this.key = key;
		this.mpath = mpath;

		check();
	}

	private void check() {
		String str = JAXRSServlet.PARAM_SYMBOL_LEFT + key + JAXRSServlet.PARAM_SYMBOL_RIGHT;
		int i = mpath.indexOf(str);

		if (-1 == i)
			throw new RuntimeException("As a known parameter placeholder '" + str + "'.");
		if (shave != mpath.charAt(i - 1))
			throw new RuntimeException("The left side of the '" + str + "' should be a known symbol '/'.");
		if (mpath.length() > (i + str.length()) && shave != mpath.charAt(i + str.length()))
			throw new RuntimeException("The right side of the '" + str + "' should be a known symbol '/'.");

		// 1111
		// String strTmp = mpath.substring(0, i);
		// index = strTmp.split(split).length;
		// 2222
		String strTmp = mpath.substring(0, i);
		index = TextUtils.number4symbols(strTmp, shave);
	}

	private String value(String pathInfo) {
		// 1111
		// String[] sarray = pathInfo.split(split);
		// if (index >= sarray.length)
		// return null;
		// return sarray[index];
		// 2222
		List<Integer> lis = TextUtils.index4symbols(pathInfo, shave);
		int size;
		if (index > (size = lis.size()))
			return null;
		if (index == size)
			return pathInfo.substring(lis.get(index - 1) + 1);
		return pathInfo.substring(lis.get(index - 1) + 1, lis.get(index));
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.jfan.ws.rs.impl.armor.build.SupportDefParameterBuild#value(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String value(HttpServletRequest req, HttpServletResponse resp) {
		String pathInfo = req.getPathInfo();
		return value(pathInfo);
	}

}

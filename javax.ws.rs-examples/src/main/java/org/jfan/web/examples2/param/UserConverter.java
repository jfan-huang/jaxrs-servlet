/**
 * 
 */
package org.jfan.web.examples2.param;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.ParamConverter;

import org.jfan.web.examples2.model.User;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月14日 下午7:04:14
 */
public class UserConverter implements ParamConverter<User> {

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.ws.rs.ext.ParamConverter#getType()
	 */
	@Override
	public Class<User> getType() {
		return User.class;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.ws.rs.ext.ParamConverter#from(java.lang.Class,
	 * java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public User from(Class<?> clz, String value, HttpServletRequest req) {
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String pass = req.getParameter("pass");
		String address = req.getParameter("address");
		String age = req.getParameter("age");
		String sex = req.getParameter("sex");

		User user = new User();
		if (null != id)
			user.setId(Long.parseLong(id));
		user.setName(name);
		user.setPass(pass);
		user.setAddress(address);
		if (null != age)
			user.setAge(Integer.parseInt(age));
		if (null != sex)
			user.setSex(Boolean.parseBoolean(sex));
		return user;
	}

}

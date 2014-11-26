package org.jfan.web.examples2.resource;

import java.util.Date;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Param;

import org.jfan.web.examples2.exception.UserNotFoundException;
import org.jfan.web.examples2.interceptor.HeaderMarkInterceptor;
import org.jfan.web.examples2.interceptor.NonInterceptor;
import org.jfan.web.examples2.interceptor.RequestLogInterceptor;
import org.jfan.web.examples2.model.User;
import org.jfan.web.examples2.vo.UserVO;
import org.jfan.web.ws.rs.ext.exception.WebApplicationException;
import org.jfan.web.ws.rs.ext.interceptor.BeforeInterceptor;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月14日 下午3:28:11
 */
@Path("user")
@BeforeInterceptor(HeaderMarkInterceptor.class)
public class UserResource {

	private long index = 1;

	@GET
	@POST
	@Path("reg/{channel}")
	public Long registered(@PathParam("channel") String channel, @BeanParam User user, @HeaderParam("time") Date time) {

//		System.out.println("RegUser - channel: " + channel + ", time: " + (null == time ? "Null" : time.getTime()) + ", user: " + user);

		return (System.currentTimeMillis() / 10000) + index++;
	}

	@GET
	@POST
	@Path("get/{uid}")
	// @AfterInterceptor()
	@BeforeInterceptor({ NonInterceptor.class, RequestLogInterceptor.class })
	public UserVO getUser(@PathParam("uid") long userId, @Param("channel") String channel//
			, @Param("version") @DefaultValue("def_1.0.0") String version //
			, @Context HttpServletRequest req, @Context ServletResponse resp)//
			throws WebApplicationException {

//		System.out.println("getUser - channel: " + channel + ", userId: " + userId + ", version: " + version + ", req: " + req + ", resp: " + resp);

		int i = (int) System.currentTimeMillis() % 3;

		// 模拟发生异常
		if (3 == i)
			throw new UserNotFoundException();

		UserVO vo = new UserVO();
		vo.setName("Name_" + userId);
		vo.setAddress("address_" + channel);
		vo.setAge(i);
		vo.setSex(1 == i);
		return vo;
	}

}

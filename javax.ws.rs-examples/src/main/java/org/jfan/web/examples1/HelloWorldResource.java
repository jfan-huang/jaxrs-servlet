/**
 * 
 */
package org.jfan.web.examples1;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月14日 下午3:07:31
 */
@Path("hello")
public class HelloWorldResource {

	@GET
	@Path("world/{name}/{num}")
	public String world(@PathParam("name") String n, @PathParam("num") int index) {
		return "Hello " + n + ", World. " + new Date().getTime() + ", 你说 " + index + " 来，我说 " + (index + 1) + "。";
	}

	@GET
	@POST
	@Path("params/{name}/out")
	public String params(@PathParam("name") String n, @Context HttpServletRequest req) {
		return "Hello " + n + ", Time: " + new Date().getTime() + ", Request: " + req;
	}

}

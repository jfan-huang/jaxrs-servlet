/**
 * 
 */
package org.jfan.web.examples2.exception;

import org.jfan.web.ws.rs.ext.exception.WebApplicationException;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月14日 下午6:44:50
 */
public class UserNotFoundException extends WebApplicationException {

	private static final long serialVersionUID = -5683845655806394877L;

	public UserNotFoundException() {
		super("-99", "没有找到用户！");
	}

}

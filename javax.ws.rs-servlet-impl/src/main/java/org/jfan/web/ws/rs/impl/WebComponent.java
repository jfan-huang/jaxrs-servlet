/**
 * 
 */
package org.jfan.web.ws.rs.impl;

/**
 * <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 上午9:53:19
 */
public interface WebComponent {

	/**
	 * 从packs包路径下，找到具有@Path注解的资源类，返回所有实例化后的对象
	 */
	public Object[] findAllResource(String[] packs);

	/**
	 * 根据clz查询所有的已知实现对象
	 */
	public <T> T[] getAllObject(String[] packs, Class<T> clz);

}

package org.jfan.web.ws.rs.ext;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Response {

	private int code;// 请求状态码
	private String msg;// 请求响应消息（可能是一个i18n代码）
	private Object data;// 请求响应的数据内容
	private boolean isArray;// 请求响应数据，是否是array

	private Object[] other;// 附加的而外信息（用于扩展），可选的

	public Response(int code) {
		this.code = code;
	}

	public Response(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Response(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		setData(data);
	}

	/**
	 * 向data中追加数据<br>
	 * <br>
	 * 如果data为空，默认构建一个map<br>
	 * 如果data已经存在，那么只有是map类型的情况下才能够追加成功
	 */
	public void addData(Object key, Object value) {
		if (null == data) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(key, value);
			setData(map);
		} else if (data instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> map = (Map<Object, Object>) data;
			map.put(key, value);
		} else
			throw new IllegalArgumentException("Result Data already exists, but not map. clz: " + data.getClass());
	}

	// set date && init isArray
	private void setData(Object data) {
		this.data = data;
		this.isArray = checkArray(this.data);
	}

	// The setData() trigger
	private boolean checkArray(Object data) {
		if (null != data && (data.getClass().isArray() || data instanceof Collection))
			return true;
		return false;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{code:").append(code);
		sb.append(",msg:\"").append(msg).append("\"");
		sb.append(",data:").append(null == data ? "null" : data.toString());
		sb.append(",isArray:").append(isArray);
		sb.append(",other:").append(Arrays.toString(other));
		sb.append('}');
		return sb.toString();
	}

	// ####
	// ## get func

	/**
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @return isArray
	 */
	public boolean isArray() {
		return isArray;
	}

	/**
	 * @return other
	 */
	public Object[] getOther() {
		return other;
	}

}

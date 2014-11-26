package org.jfan.web.examples2.model;

import org.jfan.web.examples2.vo.UserVO;

public class User extends UserVO {// 实际情况不应该，使用Entity继承VO

	private Long id;

	private String pass;

	/**
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id 要设置的 id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass 要设置的 pass
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

}

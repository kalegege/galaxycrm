/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.login;

import lombok.Data;

/**
 * @author wenguang
 * @date 2015年3月12日
 */
@Data
public class LoginHolder {
	/**
	 * 终端信息存放，用户可以不从session中取而从threadlocal中取，可以减少参数传递，
	 */
	private static final ThreadLocal<LoginUser> terminalInfoHold = new ThreadLocal<LoginUser>();

	/**
	 * 获取当前线程中的用户对象
	 * 
	 * @return
	 */
	public static final LoginUser getCurrentUser() {
		return terminalInfoHold.get();
	}

	/**
	 * 设置当前线程的用户对象
	 * 
	 * @param user
	 */
	public static final void setCurrentUser(LoginUser t) {
		terminalInfoHold.set(t);
	}

	/**
	 * 移出当前线程用户对象
	 */
	public static final void remove() {
		terminalInfoHold.remove();
	}
}

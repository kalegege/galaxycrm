package com.wasu.ptyw.galaxycrm.core.login;

import lombok.Data;

/**
 * 登录用户对象
 * 
 * @author wenguang
 * @date 2015年6月8日
 */
@Data
public class LoginUser {
	public String username;
	public String password;
	// public String ip;
	private long loginTime;
	private int remember;
}

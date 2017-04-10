/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.constant;

import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;

/**
 * @author wenguang
 * @date 2015年6月8日
 */
public enum GcrmResultCode {
	/* 通用 */
	USER_LOGIN_ERROR("USER001", "用户名不存在或密码错误"),

	;

	private String code;

	private String message;

	GcrmResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String getMessageByCode(String code) {
		ResultCode[] ae = ResultCode.values();
		for (int i = 0; i < ae.length; i++) {
			if (ae[i].getCode().equals(code)) {
				return ae[i].getMessage();
			}
		}
		return null;
	}
}
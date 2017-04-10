/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.login;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 登录相关工具类
 * 
 * @author wenguang
 * @date 2015年6月8日
 */
public class LoginUtil {
	public static final String COOKIE_ENCODE = "UTF-8";
	public static final String COOKIE_LOGIN_USER = "galaxycrm_user";
	public static final int COOKIE_REMEMBER = 1;
	public static final int COOKIE_NOREMEMBER = 0;
	public static final int COOKIE_OVERTIME = 7 * 24 * 60 * 60 * 1000;// 7天
	public static final String REDIRECT_URL_KEY = "redirectURL";
	
	

	/**
	 * 保存用户信息到cookie
	 */
	public static void saveToCookie(LoginUser user, HttpServletResponse response) {
		user.setLoginTime(System.currentTimeMillis());
		String s = encode(JSON.toJSONString(user));
		Cookie cookie = new Cookie(COOKIE_LOGIN_USER, s);
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
	}

	/**
	 * 从cookie中获取登录过的标识
	 */
	public static LoginUser getUserFromCookie(HttpServletRequest request) {
		LoginUser value = null;
		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isEmpty(cookies)) {
			return value;
		}
		for (Cookie cookie : cookies) {
			if (!COOKIE_LOGIN_USER.equalsIgnoreCase(cookie.getName())) {
				continue;
			}
			if (StringUtils.isEmpty(cookie.getValue())) {
				continue;
			}
			String s = decode(cookie.getValue());
			value = JSON.parseObject(s, LoginUser.class);
			break;
		}
		return value;
	}

	/**
	 * 从cookie中删除用户信息
	 */
	public static void removeFromCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(COOKIE_LOGIN_USER, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	/**
	 * 判断登录信息是否超时
	 * 
	 * @param user
	 */
	public static boolean isOverTime(LoginUser user) {
		if (user == null)
			return true;
		if (user.getRemember() == COOKIE_REMEMBER)
			return false;
		long time = System.currentTimeMillis() - user.getLoginTime();
		return time > COOKIE_OVERTIME;
	}

	public static String encode(String s) {
		if (StringUtils.isEmpty(s))
			return s;
		try {
			return URLEncoder.encode(s, COOKIE_ENCODE);
		} catch (UnsupportedEncodingException e) {
		}
		return s;
	}

	public static String decode(String s) {
		if (StringUtils.isEmpty(s))
			return s;
		try {
			return URLDecoder.decode(s, COOKIE_ENCODE);
		} catch (UnsupportedEncodingException e) {
		}
		return s;
	}
}

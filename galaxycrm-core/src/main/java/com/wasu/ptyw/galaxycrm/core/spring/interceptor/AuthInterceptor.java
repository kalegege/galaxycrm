/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wasu.ptyw.galaxycrm.core.login.LoginHolder;
import com.wasu.ptyw.galaxycrm.core.login.LoginUser;
import com.wasu.ptyw.galaxycrm.core.login.LoginUtil;

/**
 * @author wenguang
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {
	@Setter
	private String loginUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LoginUser currentUser = LoginUtil.getUserFromCookie(request);
		if (currentUser == null) {
			StringBuilder sb = new StringBuilder(request.getContextPath());
			sb.append(loginUrl).append("?").append(LoginUtil.REDIRECT_URL_KEY).append("=")
					.append(LoginUtil.encode(request.getRequestURL().toString()));
			response.sendRedirect(sb.toString());
			return false;
		}
		LoginHolder.setCurrentUser(currentUser);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LoginHolder.remove();
	}
}

package com.wasu.ptyw.galaxycrm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import shiro.ShiroDbRealm;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.SecurityConstants;

import com.wasu.ptyw.galaxy.common.util.DigestUtil;

/**
 * 登录
 * 
 * @author wenguang
 * @date 2015年07月13日
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	private static final String LOGIN_PAGE = "/crm/login";
	private static final String LOGIN_DIALOG = "management/index/loginDialog";

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated() == true) {
			return new ModelAndView("redirect:/management/index");
		}
		return new ModelAndView(LOGIN_PAGE);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, ModelMap model,
			HttpServletRequest request) {
		String msg = parseException(request);

		model.put("msg", msg);
		model.put("username", username);
		return LOGIN_PAGE;
	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD }, headers = "x-requested-with=XMLHttpRequest")
	public @ResponseBody
	String loginDialog(HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject(DigestUtil.native2Ascii("会话超时，请重新登录。"));
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_TIMEOUT);
		ajaxObject.setCallbackType(AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	@RequestMapping(value = "/timeout", method = { RequestMethod.GET })
	public String timeout() {
		return LOGIN_DIALOG;
	}

	@RequestMapping(value = "/timeout/success", method = { RequestMethod.POST })
	public @ResponseBody
	String timeoutSuccess(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject.getPrincipal();

		// 这个是放入user还是shiroUser呢？
		request.getSession().setAttribute(SecurityConstants.LOGIN_USER, shiroUser.getUser());

		AjaxObject ajaxObject = new AjaxObject("登录成功。");
		ajaxObject.setCallbackType(AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.HEAD }, headers = "x-requested-with=XMLHttpRequest")
	public @ResponseBody
	String failDialog(HttpServletRequest request) {
		String msg = parseException(request);

		AjaxObject ajaxObject = new AjaxObject(msg);
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		ajaxObject.setCallbackType("");

		return ajaxObject.toString();
	}

	private String parseException(HttpServletRequest request) {
		String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String msg = "其他错误！";
		if (error != null) {
			if ("org.apache.shiro.authc.UnknownAccountException".equals(error))
				msg = "未知帐号错误！";
			else if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(error))
				msg = "密码错误！";
			else if ("com.ygsoft.security.shiro.IncorrectCaptchaException".equals(error))
				msg = "验证码错误！";
			else if ("org.apache.shiro.authc.AuthenticationException".equals(error))
				msg = "认证失败！";
			else if ("org.apache.shiro.authc.DisabledAccountException".equals(error))
				msg = "账号被冻结！";
		}
		return "登录失败，" + msg;
	}

}

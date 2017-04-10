/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ygsoft.security.controller.IndexController.java
 * Class:			IndexController
 * Date:			2012-8-2
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.wasu.ptyw.galaxycrm.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.ShiroDbRealm;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.SecurityConstants;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.core.cache.LocalCache;
import com.wasu.ptyw.galaxycrm.core.ao.CrmModuleAO;
import com.wasu.ptyw.galaxycrm.core.ao.CrmUserAO;
import com.wasu.ptyw.galaxycrm.core.ao.CrmUserRoleAO;
import com.wasu.ptyw.galaxycrm.core.dataobject.Region;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmModuleDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserRoleDO;

/**
 * 首页
 * 
 * @author wenguang
 * @date 2015年8月18日
 */
@Controller
@RequestMapping("/management/index")
public class IndexController {

	@Autowired
	private CrmUserAO crmUserAO;

	@Autowired
	private CrmUserRoleAO crmUserRoleAO;

	@Autowired
	private CrmModuleAO crmModuleAO;

	private static final String INDEX = "management/index/index";
	private static final String UPDATE_PASSWORD = "management/index/updatePwd";
	private static final String UPDATE_BASE = "management/index/updateBase";

	@RequiresAuthentication
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject.getPrincipal();

		List<CrmUserRoleDO> userRoles = shiroUser.getUser().getUserRoles();
		// shiroUser.getUser().setUserRoles(userRoles);

		List<Region> regions = getAllRegion();
		String regionCode = shiroUser.getRegionCode();
		if (StringUtils.isEmpty(regionCode) && CollectionUtils.isNotEmpty(regions)) {
			regionCode = regions.get(0).getRegionCode();
			shiroUser.setRegionCode(regionCode);
		}

		// 这个是放入user还是shiroUser呢？
		request.getSession().setAttribute(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		request.setAttribute("menuModule", getMenuModule(userRoles));
		request.setAttribute("regions", regions);
		request.setAttribute("regionCode", regionCode);
		return INDEX;
	}

	private List<CrmModuleDO> getMenuModule(List<CrmUserRoleDO> userRoles) {
		// 得到所有权限
		Set<String> permissionSet = Sets.newHashSet();
		for (CrmUserRoleDO userRole : userRoles) {
			Set<String> tmp = Sets.newHashSet(userRole.getRole().getPermissionList());
			permissionSet.addAll(tmp);
		}

		Result<List<CrmModuleDO>> result = crmModuleAO.queryAll(1);
		if (!result.isSuccess())
			return null;
		List<CrmModuleDO> list = result.getValue();
		// 组装菜单,只获取二级菜单
		List<CrmModuleDO> permissions = Lists.newArrayList();
		for (CrmModuleDO item : list) {
			// 只加入拥有view权限的Module
			if (!permissionSet.contains(item.getSn() + ":" + SecurityConstants.OPERATION_VIEW)) {
				continue;
			}
			List<CrmModuleDO> childs = Lists.newArrayList();
			for (CrmModuleDO item2 : item.getChildList()) {
				if (!permissionSet.contains(item2.getSn() + ":" + SecurityConstants.OPERATION_VIEW)) {
					continue;
				}
				childs.add(item2);
			}
			item.setChildList(childs);
			permissions.add(item);
		}

		return permissions;
	}

	@RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
	public String updatePassword() {
		return UPDATE_PASSWORD;
	}

	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public @ResponseBody
	String updatePassword(HttpServletRequest request, String oldPassword, String plainPassword, String rPassword) {
		CrmUserDO user = (CrmUserDO) request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
		if (plainPassword.equals(rPassword)) {
			user.setPlainPassword(plainPassword);
			crmUserAO.update(user);
			AjaxObject ajaxObject = new AjaxObject("密码修改成功！");
			return ajaxObject.toString();
		}

		AjaxObject ajaxObject = new AjaxObject("密码修改失败！");
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}

	@RequestMapping(value = "/updateBase", method = RequestMethod.GET)
	public String preUpdate() {
		return UPDATE_BASE;
	}

	@RequestMapping(value = "/updateBase", method = RequestMethod.POST)
	public @ResponseBody
	String update(CrmUserDO user, HttpServletRequest request) {
		CrmUserDO loginUser = (CrmUserDO) request.getSession().getAttribute(SecurityConstants.LOGIN_USER);

		loginUser.setPhone(user.getPhone());
		loginUser.setEmail(user.getEmail());

		crmUserAO.update(loginUser);

		AjaxObject ajaxObject = new AjaxObject("详细信息修改成功！");
		return ajaxObject.toString();
	}

	@RequiresAuthentication
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(HttpServletRequest request) {
		return "management/index/test";
	}

	@RequestMapping(value = "/update/region/{regionCode}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable String regionCode) {
		AjaxObject ajaxObject = new AjaxObject();
		ajaxObject.setCallbackType("");
		if (StringUtils.isEmpty(regionCode)) {
			ajaxObject.setMessage("参数错误！");
			return ajaxObject.toString();
		}
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject.getPrincipal();
		shiroUser.setRegionCode(regionCode);

		ajaxObject.setMessage("更新成功！");
		return ajaxObject.toString();
	}

	private List<Region> getAllRegion() {
		List<Region> regions = Lists.newArrayList();
		String s = LocalCache.get("set_sync_clps");
		if (StringUtils.isEmpty(s)) {
			return regions;
		}
		JSONArray jsonArr = JSON.parseArray(s);
		for (Object o : jsonArr) {
			JSONObject json = (JSONObject) o;
			String regionCode = json.getString("region");
			String regionName = json.getString("name");
			regions.add(new Region(regionCode, regionName));
		}
		return regions;
	}
}

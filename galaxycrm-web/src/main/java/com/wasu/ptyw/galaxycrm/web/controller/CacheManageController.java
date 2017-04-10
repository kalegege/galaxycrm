/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ygsoft.security.controller.CacheManageController.java
 * Class:			CacheManageController
 * Date:			2012-9-14
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.wasu.ptyw.galaxycrm.web.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.dataobject.AjaxObject;

import com.wasu.ptyw.galaxy.core.cache.LocalCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmOrganizationCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmRoleCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmRolePermissionCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmUserRoleCache;

/**
 * 缓存管理
 * 
 * @author wenguang
 * @date 2015年8月18日
 */
@Controller
@RequestMapping("/management/security/cacheManage")
public class CacheManageController {
	@Resource
	CrmOrganizationCache crmOrganizationCache;
	@Resource
	CrmUserRoleCache crmUserRoleCache;
	@Resource
	CrmRoleCache crmRoleCache;
	@Resource
	CrmRolePermissionCache crmRolePermissionCache;

	private static final String INDEX = "management/security/cacheManage/index";

	@RequiresPermissions("CacheManage:view")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return INDEX;
	}

	@RequiresPermissions("CacheManage:edit")
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public @ResponseBody
	String clear() {
		crmOrganizationCache.clearAll();
		crmUserRoleCache.clearAll();
		crmRoleCache.clearAll();
		crmRolePermissionCache.clearAll();
		LocalCache.clearAll();

		AjaxObject ajaxObject = new AjaxObject("清除缓存成功！");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
}

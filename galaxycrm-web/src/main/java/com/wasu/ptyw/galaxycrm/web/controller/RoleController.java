/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ygsoft.security.controller.RoleController.java
 * Class:			RoleController
 * Date:			2012-8-7
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.wasu.ptyw.galaxycrm.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.dataobject.AjaxObject;
import shiro.dataobject.Page;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxycrm.core.ao.CrmModuleAO;
import com.wasu.ptyw.galaxycrm.core.ao.CrmRoleAO;
import com.wasu.ptyw.galaxycrm.core.ao.CrmRolePermissionAO;
import com.wasu.ptyw.galaxycrm.core.cache.CrmRolePermissionCache;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmModuleDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmRoleQuery;

/**
 * 角色管理
 * 
 * @author wenguang
 * @date 2015年8月18日
 */
@Controller
@RequestMapping("/management/security/role")
public class RoleController {
	@Autowired
	private CrmModuleAO crmModuleAO;
	@Autowired
	private CrmRoleAO crmRoleAO;
	@Autowired
	private CrmRolePermissionAO crmRolePermissionAO;
	@Resource
	CrmRolePermissionCache crmRolePermissionCache;

	private static final String CREATE = "management/security/role/create";
	private static final String UPDATE = "management/security/role/update";
	private static final String LIST = "management/security/role/list";

	@RequiresPermissions("Role:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		Result<List<CrmModuleDO>> result = crmModuleAO.queryAll(1);
		if (result.isSuccess()) {
			map.put("module", result.getValue());
		}
		return CREATE;
	}

	// 重新组装PermissionList（切分test:save,test:edit的形式）
	private void refactor(CrmRoleDO role) {
		List<String> allList = Lists.newArrayList();
		List<String> list = role.getPermissionList();
		for (String string : list) {
			if (StringUtils.isBlank(string)) {
				continue;
			}

			if (string.contains(",")) {
				String[] arr = string.split(",");
				allList.addAll(Arrays.asList(arr));
			} else {
				allList.add(string);
			}
		}
		role.setPermissionList(allList);
	}

	@RequiresPermissions("Role:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(CrmRoleDO role) {
		AjaxObject ajaxObject = new AjaxObject("角色添加成功！");
		refactor(role);
		Result<Long> result = crmRoleAO.save(role);
		if (!result.isSuccess()) {
			ajaxObject.setMessage("角色添加失败!");
		}

		return ajaxObject.toString();
	}

	@RequiresPermissions("Role:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Result<CrmRoleDO> result = crmRoleAO.getById(id);
		if (result.isSuccess()) {
			CrmRoleDO role = result.getValue();
			role.setPermissionList(crmRolePermissionCache.get(role.getId()));
			map.put("role", role);
		}

		Result<List<CrmModuleDO>> mResult = crmModuleAO.queryAll(1);
		if (mResult.isSuccess()) {
			map.put("module", mResult.getValue());
		}
		return UPDATE;
	}

	@RequiresPermissions("Role:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(CrmRoleDO role) {
		refactor(role);
		crmRoleAO.update(role);

		AjaxObject ajaxObject = new AjaxObject("角色修改成功！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Role:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		crmRoleAO.deleteById(id);

		AjaxObject ajaxObject = new AjaxObject("角色删除成功！");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Role:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, String keywords, Map<String, Object> map) {
		CrmRoleQuery query = new CrmRoleQuery();
		if (StringUtils.isNotEmpty(keywords)) {
			query.setName(keywords);
		}
		if (page != null) {
			query.setCurrentPage(page.getPageNum());
			query.setPageSize(page.getNumPerPage());
		}
		Result<List<CrmRoleDO>> queryResult = crmRoleAO.query(query);
		if (queryResult.isSuccess()) {
			page.setTotalCount(query.getTotalItem());
			map.put("roles", queryResult.getValue());
		}

		map.put("page", page);
		map.put("keywords", keywords);
		return LIST;
	}

}

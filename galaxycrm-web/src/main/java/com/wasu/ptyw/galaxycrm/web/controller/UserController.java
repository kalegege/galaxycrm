/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ygsoft.security.controller.UserController.java
 * Class:			UserController
 * Date:			2012-8-7
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.wasu.ptyw.galaxycrm.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.PageUtils;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.Page;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.util.NumUtil;
import com.wasu.ptyw.galaxycrm.core.ao.CrmModuleAO;
import com.wasu.ptyw.galaxycrm.core.ao.CrmOrganizationAO;
import com.wasu.ptyw.galaxycrm.core.ao.CrmRoleAO;
import com.wasu.ptyw.galaxycrm.core.ao.CrmUserAO;
import com.wasu.ptyw.galaxycrm.core.ao.CrmUserRoleAO;
import com.wasu.ptyw.galaxycrm.core.cache.CrmRoleCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmUserRoleCache;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmOrganizationDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRoleDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmUserQuery;

/**
 * 用户管理
 * 
 * @author wenguang
 * @date 2015年8月18日
 */
@Controller
@RequestMapping("/management/security/user")
public class UserController {

	@Autowired
	private CrmUserAO crmUserAO;

	@Autowired
	private CrmUserRoleAO crmUserRoleAO;

	@Autowired
	private CrmModuleAO crmModuleAO;

	@Autowired
	private CrmRoleAO crmRoleAO;

	@Autowired
	private CrmOrganizationAO crmOrganizationAO;

	@Resource
	CrmRoleCache crmRoleCache;
	@Resource
	CrmUserRoleCache crmUserRoleCache;

	private static final String CREATE = "management/security/user/create";
	private static final String UPDATE = "management/security/user/update";
	private static final String LIST = "management/security/user/list";
	private static final String LOOK_UP_ROLE = "management/security/user/assign_role";
	private static final String LOOK_USER_ROLE = "management/security/user/delete_user_role";
	private static final String LOOK_ORG = "management/security/user/lookup_org";

	@RequiresPermissions("User:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}

	@RequiresPermissions("User:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(CrmUserDO user) {
		if (user != null && user.getOrganization() != null) {
			user.setOrgId(user.getOrganization().getId());
		}
		Result<Long> result = crmUserAO.save(user);
		if (!result.isSuccess()) {
			AjaxObject ajaxObject = new AjaxObject(result.getMessage());
			ajaxObject.setCallbackType("");
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			return ajaxObject.toString();
		}

		AjaxObject ajaxObject = new AjaxObject("用户添加成功！");
		return ajaxObject.toString();
	}

	@ModelAttribute("preloadUser")
	public CrmUserDO getOne(@RequestParam(value = "id", required = false) Long id) {
		Result<CrmUserDO> result = crmUserAO.getById(id);
		if (result.isSuccess())
			return result.getValue();
		return null;
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Result<CrmUserDO> result = crmUserAO.getById(id);
		if (result.isSuccess()) {
			CrmUserDO user = result.getValue();
			user.setOrganization(crmOrganizationAO.getById(user.getOrgId()).getValue());
			map.put("user", user);
		}

		return UPDATE;
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(@ModelAttribute("preloadUser") CrmUserDO user) {
		if (user != null && user.getOrganization() != null) {
			user.setOrgId(user.getOrganization().getId());
		}
		Result<Integer> result = crmUserAO.update(user);
		AjaxObject ajaxObject = new AjaxObject("用户修改失败！");
		if (result.isSuccess()) {
			ajaxObject.setMessage("用户修改成功！");
		}

		return ajaxObject.toString();
	}

	@RequiresPermissions("User:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject("用户删除成功！");
		Result<Integer> result = crmUserAO.deleteById(id);
		if (!result.isSuccess()) {
			ajaxObject.setMessage(result.getMessage());
		}

		// AjaxObject ajaxObject = new AjaxObject("用户删除成功");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}

	@RequiresPermissions("User1:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, String keywords, Map<String, Object> map) {
		CrmUserQuery query = new CrmUserQuery();
		if (StringUtils.isNotBlank(keywords)) {
			query.setLikeUsername(keywords);
		}
		PageUtils.convertPage(query, page);
		Result<List<CrmUserDO>> result = crmUserAO.query(query);
		if (result.isSuccess()) {
			page.setTotalCount(query.getTotalItem());
			map.put("users", result.getValue());
		}
		map.put("page", page);

		map.put("keywords", keywords);
		return LIST;
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/reset/{type}/{userId}", method = RequestMethod.POST)
	public @ResponseBody
	String reset(@PathVariable String type, @PathVariable Long userId) {
		Result<CrmUserDO> result = crmUserAO.getById(userId);
		if (!result.isSuccess()) {
			AjaxObject ajaxObject = new AjaxObject("重置密码失败，用户获取失败");
			return ajaxObject.toString();
		}
		CrmUserDO user = result.getValue();
		AjaxObject ajaxObject = new AjaxObject();
		if (type.equals("password")) {
			user.setPlainPassword("123456");
			ajaxObject.setMessage("重置密码成功，默认密码为123456！");
			crmUserAO.update(user);
		} else if (type.equals("status")) {
			if (NumUtil.isEq(user.getStatus(), 0)) {
				user.setStatus(-1);
			} else {
				user.setStatus(0);
			}
			ajaxObject.setMessage("更新状态为" + user.getStatus());
			crmUserAO.update(user);
		}
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}

	@RequiresPermissions("User:save")
	@RequestMapping(value = "/create/userRole", method = { RequestMethod.POST })
	public @ResponseBody
	void assignRole(CrmUserRoleDO userRole) {
		Result<Long> result = crmUserRoleAO.save(userRole);
		if (result.isSuccess()) {
			crmUserRoleCache.clear(userRole.getUserId());
		}
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/lookup2create/userRole/{userId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String listUnassignRole(Map<String, Object> map, @PathVariable Long userId) {

		Result<List<CrmUserRoleDO>> result = crmUserRoleAO.getByUid(userId);
		List<CrmUserRoleDO> userRoles = result.getValue();

		Result<List<CrmRoleDO>> roleResult = crmRoleAO.queryAll();
		List<CrmRoleDO> roles = roleResult.getValue();

		List<CrmRoleDO> hasList = Lists.newArrayList();
		List<CrmRoleDO> allRoles = Lists.newArrayList(roles);
		// 删除已分配roles
		for (CrmUserRoleDO ur : userRoles) {
			for (CrmRoleDO role : roles) {
				if (ur.getRoleId().equals(role.getId())) {
					ur.setRole(role);
					hasList.add(role);
					break;
				}
			}
		}

		allRoles.removeAll(hasList);

		map.put("userRoles", userRoles);
		map.put("roles", allRoles);

		map.put("userId", userId);
		return LOOK_UP_ROLE;
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/lookup2delete/userRole/{userId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String listUserRole(Map<String, Object> map, @PathVariable Long userId) {
		Result<List<CrmUserRoleDO>> result = crmUserRoleAO.getByUid(userId);
		if (result.isSuccess()) {
			List<CrmUserRoleDO> userRoles = result.getValue();
			for (CrmUserRoleDO userRole : userRoles) {
				CrmRoleDO role = crmRoleCache.get(userRole.getRoleId());
				if (role == null)
					continue;
				userRole.setRole(role);
			}
			map.put("userRoles", result.getValue());
		}

		return LOOK_USER_ROLE;
	}

	@RequiresPermissions("User:edit")
	@RequestMapping(value = "/delete/userRole/{userRoleId}/{userId}", method = { RequestMethod.POST })
	public @ResponseBody
	void deleteUserRole(@PathVariable Long userRoleId, @PathVariable Long userId) {
		Result<Integer> result = crmUserRoleAO.deleteById(userRoleId);
		if (result.isSuccess()) {
			crmUserRoleCache.clear(userId);
		}
	}

	@RequiresPermissions(value = { "User:edit", "User:save" })
	@RequestMapping(value = "/lookup2org", method = { RequestMethod.GET })
	public String lookup(Map<String, Object> map) {
		Result<List<CrmOrganizationDO>> result = crmOrganizationAO.queryAll(1);
		if (result.isSuccess()) {
			map.put("org", result.getValue());
		}
		return LOOK_ORG;
	}
}

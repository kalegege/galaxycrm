/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ygsoft.security.controller.OrganizationController.java
 * Class:			OrganizationController
 * Date:			2012-8-27
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.wasu.ptyw.galaxycrm.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxycrm.core.ao.CrmOrganizationAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmOrganizationDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmOrganizationQuery;

/**
 * 组织管理
 * 
 * @author wenguang
 * @date 2015年8月18日
 */
@Controller
@RequestMapping("/management/security/organization")
public class OrganizationController {
	@Autowired
	private CrmOrganizationAO crmOrganizationAO;

	private static final String CREATE = "management/security/organization/create";
	private static final String UPDATE = "management/security/organization/update";
	private static final String LIST = "management/security/organization/list";
	private static final String TREE = "management/security/organization/tree";

	@RequiresPermissions("Organization:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}

	@RequiresPermissions("Organization:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(CrmOrganizationDO organization, HttpServletRequest request) {
		crmOrganizationAO.save(organization);

		AjaxObject ajaxObject = new AjaxObject("组织添加成功！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Organization:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Result<CrmOrganizationDO> result = crmOrganizationAO.getById(id);
		if (result.isSuccess()) {
			map.put("organization", result.getValue());
		}

		return UPDATE;
	}

	@RequiresPermissions("Organization:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(CrmOrganizationDO organization) {
		crmOrganizationAO.update(organization);

		AjaxObject ajaxObject = new AjaxObject("组织修改成功！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Organization:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject();
		Result<Integer> result = crmOrganizationAO.deleteById(id);
		if (result.isSuccess()) {
			ajaxObject.setMessage("组织删除成功！");
		} else {
			ajaxObject.setMessage("组织删除失败：" + result.getMessage());
		}
		ajaxObject.setCallbackType("");
		ajaxObject.setRel("jbsxBox2organization");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Organization:view")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		Result<List<CrmOrganizationDO>> result = crmOrganizationAO.queryAll(1);
		if (result.isSuccess()) {
			map.put("organization", result.getValue());
		}
		return TREE;
	}

	@RequiresPermissions("Organization:view")
	@RequestMapping(value = "/list/{parentId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, @PathVariable Long parentId, String keywords, Map<String, Object> map,
			HttpServletRequest request) {

		CrmOrganizationQuery query = new CrmOrganizationQuery();
		query.setParentId(parentId);
		if (StringUtils.isNotEmpty(keywords)) {
			query.setName(keywords);
		}
		if (page != null) {
			query.setCurrentPage(page.getPageNum());
			query.setPageSize(page.getNumPerPage());
		}
		Result<List<CrmOrganizationDO>> queryResult = crmOrganizationAO.query(query);
		if (queryResult.isSuccess()) {
			map.put("organizations", queryResult.getValue());
		}
		Result<CrmOrganizationDO> result = crmOrganizationAO.getById(parentId);
		if (result.isSuccess()) {
			page.setTotalCount(query.getTotalItem());
			request.getSession().setAttribute("parentOrganization", result.getValue());
		}
		map.put("page", page);
		map.put("keywords", keywords);
		return LIST;
	}

}

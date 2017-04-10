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

import shiro.PageUtils;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.Page;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.util.StringUtil;
import com.wasu.ptyw.galaxycrm.core.ao.CrmModuleAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmModuleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmModuleQuery;

/**
 * 模块管理
 * 
 * @author wenguang
 * @date 2015年8月16日
 */
@Controller
@RequestMapping("/management/security/module")
public class ModuleController {
	@Autowired
	private CrmModuleAO crmModuleAO;

	private static final String CREATE = "management/security/module/create";
	private static final String UPDATE = "management/security/module/update";
	private static final String LIST = "management/security/module/list";
	private static final String TREE = "management/security/module/tree";

	@RequiresPermissions("Module:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}

	@RequiresPermissions("Module:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(CrmModuleDO module, HttpServletRequest request) {
		crmModuleAO.save(module);

		AjaxObject ajaxObject = new AjaxObject("模块添加成功！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Module:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Result<CrmModuleDO> result = crmModuleAO.getById(id);
		if (result.isSuccess()) {
			map.put("module", result.getValue());
		}
		return UPDATE;
	}

	@RequiresPermissions("Module:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(CrmModuleDO module) {
		crmModuleAO.update(module);

		AjaxObject ajaxObject = new AjaxObject("模块修改成功！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Module:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		Result<Integer> result = crmModuleAO.deleteById(id);
		AjaxObject ajaxObject = new AjaxObject();
		if (result.isSuccess()) {
			ajaxObject.setMessage("模块删除成功！");
		} else {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("模块删除失败：" + result.getMessage());
		}

		ajaxObject.setCallbackType("");
		ajaxObject.setRel("jbsxBox2module");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		Result<List<CrmModuleDO>> result = crmModuleAO.queryAll(1);
		if (result.isSuccess()) {
			map.put("module", result.getValue());
		}
		return TREE;
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/list/{parentId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, @PathVariable Long parentId, String keywords, Map<String, Object> map,
			HttpServletRequest request) {
		Result<CrmModuleDO> result = crmModuleAO.getById(parentId);
		if (result.isSuccess()) {
			request.getSession().setAttribute("parentModule", result.getValue());
		}

		CrmModuleQuery query = new CrmModuleQuery();
		query.setParentId(parentId);
		if (StringUtils.isNotEmpty(keywords)) {
			query.setName(keywords);
		}
		PageUtils.convertPage(query, page);
		Result<List<CrmModuleDO>> queryResult = crmModuleAO.query(query);
		if (queryResult.isSuccess()) {
			page.setTotalCount(query.getTotalItem());
			map.put("modules", queryResult.getValue());
		}

		map.put("page", page);
		map.put("keywords", keywords);
		return LIST;
	}

}

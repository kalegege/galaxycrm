package com.wasu.ptyw.galaxycrm.web.controller;

import java.util.List;
import java.util.Map;

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
import com.wasu.ptyw.galaxy.core.ao.SysSettingAO;
import com.wasu.ptyw.galaxy.dal.dataobject.SysSettingDO;
import com.wasu.ptyw.galaxy.dal.query.SysSettingQuery;

/**
 * 系统设置
 * 
 * @author wenguang
 * @date 2015年8月24日
 */
@Controller
@RequestMapping("/management/security/setting")
public class SettingController {
	@Autowired
	private SysSettingAO sysSettingAO;

	private static final String CREATE = "management/security/setting/create";
	private static final String UPDATE = "management/security/setting/update";
	private static final String LIST = "management/security/setting/list";

	@RequiresPermissions("Setting:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}

	@RequiresPermissions("Setting:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(SysSettingDO item) {
		Result<Long> result = sysSettingAO.save(item);
		if (!result.isSuccess()) {
			AjaxObject ajaxObject = new AjaxObject(result.getMessage());
			ajaxObject.setCallbackType("");
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			return ajaxObject.toString();
		}

		AjaxObject ajaxObject = new AjaxObject("添加成功！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Setting:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Result<SysSettingDO> result = sysSettingAO.getById(id);
		if (result.isSuccess()) {
			map.put("item", result.getValue());
		}
		return UPDATE;
	}

	@RequiresPermissions("Setting:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(SysSettingDO item) {
		Result<Integer> result = sysSettingAO.update(item);
		AjaxObject ajaxObject = new AjaxObject("修改失败！");
		if (result.isSuccess()) {
			ajaxObject.setMessage("修改成功！");
		}

		return ajaxObject.toString();
	}

	@RequiresPermissions("Setting:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject("删除成功！");
		Result<Integer> result = sysSettingAO.deleteById(id);
		if (!result.isSuccess()) {
			ajaxObject.setMessage(result.getMessage());
		}
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Setting:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, String keywords, Map<String, Object> map) {
		SysSettingQuery query = new SysSettingQuery();
		if (StringUtils.isNotBlank(keywords)) {
			query.setName(keywords);
		}
		if (page != null) {
			query.setCurrentPage(page.getPageNum());
			query.setPageSize(page.getNumPerPage());
		}
		Result<List<SysSettingDO>> result = sysSettingAO.query(query);
		if (result.isSuccess()) {
			page.setTotalCount(query.getTotalItem());
			map.put("datas", result.getValue());
		}
		map.put("page", page);

		map.put("keywords", keywords);
		return LIST;
	}
}

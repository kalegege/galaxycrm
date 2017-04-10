package com.wasu.ptyw.galaxycrm.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.PageUtils;
import shiro.ShiroDbRealm;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.util.NumUtil;
import com.wasu.ptyw.galaxy.core.ao.GalaxyDiscountAO;
import com.wasu.ptyw.galaxy.core.cache.LocalCache;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyDiscountDO;
import com.wasu.ptyw.galaxy.dal.query.GalaxyDiscountQuery;
import com.wasu.ptyw.galaxycrm.core.dataobject.Region;

/**
 * 折扣管理
 * 
 * @author wenguang
 * @date 2015年9月1日
 */
@Controller
@RequestMapping("/management/security/bus/discount")
public class DiscountController {
	@Autowired
	private GalaxyDiscountAO galaxyDiscountAO;

	private static final String CREATE = "management/security/bus/discount/create";
	private static final String UPDATE = "management/security/bus/discount/update";
	private static final String LIST = "management/security/bus/discount/list";

	@RequiresPermissions("Discount:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}

	@RequiresPermissions("Discount:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(GalaxyDiscountDO item, HttpServletRequest request) {
		item.setRegionCode(getRegionCode());
		galaxyDiscountAO.save(item);

		AjaxObject ajaxObject = new AjaxObject("添加成功！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Discount:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Result<GalaxyDiscountDO> result = galaxyDiscountAO.getById(id);
		if (result.isSuccess()) {
			map.put("item", result.getValue());
		}
		return UPDATE;
	}

	@RequiresPermissions("Discount:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(GalaxyDiscountDO item) {
		galaxyDiscountAO.update(item);

		AjaxObject ajaxObject = new AjaxObject("修改成功！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Discount:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		Result<Integer> result = galaxyDiscountAO.deleteById(id);
		AjaxObject ajaxObject = new AjaxObject();
		if (result.isSuccess()) {
			ajaxObject.setMessage("删除成功！");
		} else {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("删除失败：" + result.getMessage());
		}

		ajaxObject.setCallbackType("");
		// ajaxObject.setRel("jbsxBox2module");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Discount:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, String keywords, Map<String, Object> map, HttpServletRequest request) {
		GalaxyDiscountQuery query = new GalaxyDiscountQuery();
		query.setRegionCode(getRegionCode());
		if (StringUtils.isNotEmpty(keywords)) {
			query.setLikeName(keywords);
		}
		PageUtils.convertPage(query, page);
		Result<List<GalaxyDiscountDO>> queryResult = galaxyDiscountAO.query(query);

		if (queryResult.isSuccess()) {
			page.setTotalCount(query.getTotalItem());
			map.put("datas", queryResult.getValue());
		}

		map.put("regions", getAllRegion());
		map.put("page", page);
		map.put("keywords", keywords);
		return LIST;
	}

	/**
	 * 批量上下线
	 */
	@RequiresPermissions("Discount:edit")
	@RequestMapping(value = "/updateStatus")
	public @ResponseBody
	String filmSectionUpdateStatus(String discountIds, Integer status) {
		List<Long> ids = NumUtil.toLongs(StringUtils.split(discountIds, ","));
		int intStatus = 0;
		if (status != null && status.intValue() == 1) {
			intStatus = 1;
		}

		Result<Integer> result = galaxyDiscountAO.updateStatusByIds(ids, intStatus);
		int successNum = 0;
		if (result.isSuccess()) {
			successNum = result.getValue().intValue();
		}

		String msg;
		if (successNum == ids.size()) {
			msg = "操作成功！";
		} else if (successNum > 0) {
			msg = "部分操作成功！成功：" + successNum + ",失败：" + (ids.size() - successNum);
		} else {
			msg = "操作失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg, "");
		return ajaxObject.toString();
	}

	/**
	 * 拷贝
	 */
	@RequiresPermissions("Discount:edit")
	@RequestMapping(value = "/copy/{regionCode}", method = { RequestMethod.POST })
	public @ResponseBody
	String copy(@PathVariable String regionCode) {
		String destRegionCode = getRegionCode();
		if (StringUtils.isEmpty(destRegionCode)) {
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "当前区域失效，请刷新页面！").toString();
		}

		GalaxyDiscountQuery query = new GalaxyDiscountQuery();
		query.setRegionCode(destRegionCode);
		query.setPageSize(1);
		Result<List<GalaxyDiscountDO>> queryResult = galaxyDiscountAO.query(query);
		if (queryResult.isSuccess() && query.getTotalItem() > 0) {
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "请先删除数据再拷贝！").toString();
		}

		query.setRegionCode(regionCode);
		query.setPageSize(500);
		queryResult = galaxyDiscountAO.query(query);

		for (GalaxyDiscountDO item : queryResult.getValue()) {
			item.setId(null);
			item.setRegionCode(destRegionCode);
			galaxyDiscountAO.save(item);
		}

		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "拷贝成功！", "");
		return ajaxObject.toString();
	}

	// 获取区域编码
	private String getRegionCode() {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject.getPrincipal();
		return shiroUser.getRegionCode();
	}

	private List<Region> getAllRegion() {
		List<Region> regions = Lists.newArrayList();
		String s = LocalCache.get("set_sync_clps");
		if (StringUtils.isEmpty(s)) {
			return regions;
		}
		String curRegionCode = getRegionCode();
		JSONArray jsonArr = JSON.parseArray(s);
		for (Object o : jsonArr) {
			JSONObject json = (JSONObject) o;
			String regionCode = json.getString("region");
			String regionName = json.getString("name");
			if (StringUtils.equalsIgnoreCase(curRegionCode, regionCode)) {
				continue;
			}
			regions.add(new Region(regionCode, regionName));
		}
		return regions;
	}
}

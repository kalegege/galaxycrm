package com.wasu.ptyw.galaxycrm.web.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.util.NumUtil;
import com.wasu.ptyw.galaxy.core.ao.ChanelActorAO;
import com.wasu.ptyw.galaxy.core.ao.ChanelItemAO;
import com.wasu.ptyw.galaxy.core.ao.ChanelItemTeamAO;
import com.wasu.ptyw.galaxy.core.ao.ChanelTeamAO;
import com.wasu.ptyw.galaxy.core.ao.ChanelTeamRecommendAO;
import com.wasu.ptyw.galaxy.core.ao.ChanelTeamSelfcontrolAO;
import com.wasu.ptyw.galaxy.core.cache.LocalCache;
import com.wasu.ptyw.galaxy.core.dto.ActorDTO;
import com.wasu.ptyw.galaxy.core.dto.ChanelItemDTO;
import com.wasu.ptyw.galaxy.core.dto.DateDTO;
import com.wasu.ptyw.galaxy.dal.dataobject.AssertMenuAllDO;
import com.wasu.ptyw.galaxy.dal.dataobject.AssertMenuDO;
import com.wasu.ptyw.galaxy.dal.dataobject.AssertMenuDTO;
import com.wasu.ptyw.galaxy.dal.dataobject.ChanelActorDO;
import com.wasu.ptyw.galaxy.dal.dataobject.ChanelItemDO;
import com.wasu.ptyw.galaxy.dal.dataobject.ChanelItemTeamDO;
import com.wasu.ptyw.galaxy.dal.dataobject.ChanelTeamDO;
import com.wasu.ptyw.galaxy.dal.dataobject.ChanelTeamRecommendDO;
import com.wasu.ptyw.galaxy.dal.dataobject.ChanelTeamSelfcontrolDO;
import com.wasu.ptyw.galaxy.dal.dataobject.MenuAllDO;
import com.wasu.ptyw.galaxy.dal.dataobject.MenuInfoDO;
import com.wasu.ptyw.galaxy.dal.dataobject.MenuInfoDTO;
import com.wasu.ptyw.galaxy.dal.query.ChanelActorQuery;
import com.wasu.ptyw.galaxy.dal.query.ChanelItemQuery;
import com.wasu.ptyw.galaxy.dal.query.ChanelItemTeamQuery;
import com.wasu.ptyw.galaxy.dal.query.ChanelTeamQuery;
import com.wasu.ptyw.galaxy.dal.query.ChanelTeamRecommendQuery;
import com.wasu.ptyw.galaxy.dal.query.ChanelTeamSelfcontrolQuery;
import com.wasu.ptyw.galaxy.web.controller.ChanelTeamController;
import com.wasu.ptyw.galaxycrm.core.dataobject.Region;
import com.wasu.ptyw.galaxycrm.web.utile.FastdfsUtil;

import shiro.PageUtils;
import shiro.ShiroDbRealm;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.Page;

/**
 * @author wenguang
 */
@Controller
@RequestMapping("/management/chanel")
public class ChanelController extends BaseController {

	@Resource
	private ChanelTeamAO chanelTeamAO;
	@Resource
	private ChanelItemAO chanelItemAO;
	@Resource
	private ChanelItemTeamAO chanelItemTeamAO;
	@Resource
	private ChanelTeamRecommendAO chanelTeamRecommendAO;
	@Resource
	private ChanelTeamSelfcontrolAO chanelTeamSelfcontrolAO;
	@Resource
	private ChanelActorAO chanelActorAO;

	// 同步频道分组和频道详细表和关系表
	@RequestMapping("/updateAll")
	public @ResponseBody String updateAll( HttpServletRequest request) {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		chanelTeamQuery.setRegionId(getChanelRegion());
		Result<List<ChanelTeamDO>> result = chanelTeamAO.updateAll(chanelTeamQuery);
		AjaxObject ajaxObject = result.isSuccess() ? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "同步成功!", "")
				: new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, result.getMessage());
		return ajaxObject.toString();

	}

	// 直播频道分组和关系
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/inteam")
	public String inteam(String regionId,Page page, Integer status, Map<String, Object> map, HttpServletRequest request) {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		PageUtils.convertPage(chanelTeamQuery, page);
		if(regionId == null){
			chanelTeamQuery.setRegionId(getChanelRegion());
		}
		else{
			chanelTeamQuery.setRegionId(regionId);
		}
		Result<List<ChanelTeamDO>> chanelTeamResult = chanelTeamAO.query(chanelTeamQuery);
		if (chanelTeamResult.isSuccess()) {
			List<ChanelTeamDO> chanelTeamDOs = chanelTeamResult.getValue();
			page.setTotalCount(chanelTeamQuery.getTotalItem());

			map.put("chanelregion", getChanelRegion());
			if (chanelTeamDOs != null && chanelTeamDOs.size() > 0) {
				map.put("teamDatas", chanelTeamDOs);
			}
		}
		return "management/chanel/inteam";
	}
	
	
	
	// 直播频道分组和关系
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/inteam2")
	public String inteam2(String regionId,Page page, Integer status, Map<String, Object> map, HttpServletRequest request) {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		PageUtils.convertPage(chanelTeamQuery, page);
		if(regionId == null){
			chanelTeamQuery.setRegionId(getChanelRegion());
		}
		else{
			chanelTeamQuery.setRegionId(regionId);
		}
		Result<List<ChanelTeamDO>> chanelTeamResult = chanelTeamAO.queryType(chanelTeamQuery);
		if (chanelTeamResult.isSuccess()) {
			List<ChanelTeamDO> chanelTeamDOs = chanelTeamResult.getValue();
			page.setTotalCount(chanelTeamQuery.getTotalItem());

			map.put("chanelregion", getChanelRegion());
			if (chanelTeamDOs != null && chanelTeamDOs.size() > 0) {
				map.put("teamDatas", chanelTeamDOs);
			}
		}
		return "management/chanel/inteam2";
	}

	@RequestMapping(value = "")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new ModelAndView("index", model);
	}

	/**
	 * 添加分组接口
	 * 
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/addteam")
	public String addteam(Map<String, Object> map) {
		map.put("chanelregions", getAllChanelRegion());
		return "management/chanel/addteam";
	}

	// 插入分组
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/insertteam")
	public @ResponseBody String insertteam(Page page, ChanelTeamDO chanelTeamDO, HttpServletRequest request) {
		chanelTeamDO.setRegionId(getChanelRegion());
		Result<Long> chanelTeamResult = chanelTeamAO.save(chanelTeamDO);

		String msg;
		if (chanelTeamResult.isSuccess()) {
			msg = "插入成功！";
		} else {
			msg = "插入失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	/**
	 * 编辑分组信息
	 * 
	 * @param secId
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/editteam/{id}")
	public String editteam(@PathVariable long id, HttpServletRequest request, Map<String, Object> map) {
		Result<ChanelTeamDO> result = chanelTeamAO.getById(id);
		ChanelTeamDO chanelTeamDO = result.getValue();
		if (result.isSuccess()) {
			map.put("team", chanelTeamDO);
		}
		map.put("chanelregions", getAllChanelRegion());
		return "management/chanel/editteam";
	}

	// 鼠标单击出现分组内频道
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/items/{id}")
	public String items(@PathVariable long id, Page page, Integer status, Map<String, Object> map,
			HttpServletRequest request) {
		ChanelItemTeamQuery chanelItemTeamQuery = new ChanelItemTeamQuery();
		chanelItemTeamQuery.setTeamId(id);
		PageUtils.convertPage(chanelItemTeamQuery, page);
		// 根据分组的id查询分组bid
		map.put("id", id);
		// 根据分组bid查询分组下的频道号列表，计算频道数量
		Result<List<ChanelItemDTO>> chanelItemTeamResult = chanelItemTeamAO.getByTeam(chanelItemTeamQuery);
		if (chanelItemTeamResult.isSuccess()) {
			map.put("itemDatas", chanelItemTeamResult.getValue());
			page.setTotalCount(chanelItemTeamQuery.getTotalItem());
		}
		return "management/chanel/initem";
	}

	/**
	 * 添加分组内频道页面接口
	 * 
	 * @param secId
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/additemteam/{id}")
	public String additemteam(@PathVariable long id, Page page, String keywords, Map<String, Object> map,
			HttpServletRequest request) {
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();
		ChanelItemTeamQuery chanelItemTeamQuery = new ChanelItemTeamQuery();

		// chanelItemQuery.setRegionId(getChanelRegion());
		chanelItemQuery.setRegionId("01_4001");
		map.put("teamid", id);
		map.put("keywords", keywords);
		PageUtils.convertPage(chanelItemQuery, page);

		// 获取bid
		Result<ChanelTeamDO> chanelTeamDOs = chanelTeamAO.getById(id);
		chanelItemTeamQuery.setTeamId(chanelTeamDOs.getValue().getId());
		Result<List<ChanelItemTeamDO>> chanelItemTeamResult = chanelItemTeamAO.getAllByTeamId(chanelItemTeamQuery);
		List<ChanelItemTeamDO> chanelItemTeamDOs = chanelItemTeamResult.getValue();
		List<Long> itemIds = new ArrayList<Long>();
		if (chanelItemTeamDOs != null && chanelItemTeamDOs.size() > 0) {
			for (ChanelItemTeamDO chanelItemTeamDO : chanelItemTeamDOs) {
				itemIds.add(chanelItemTeamDO.getItemId());
			}
		}
		// 查询结果
		if (keywords != null) {
			chanelItemQuery.setChName(keywords);
		}
		Result<List<ChanelItemDO>> chanelItemResult = chanelItemAO.queryadd(itemIds, chanelItemQuery);
		if (chanelItemResult.isSuccess()) {
			List<ChanelItemDO> chanelItemDOs = chanelItemResult.getValue();
			page.setTotalCount(chanelItemQuery.getTotalItem());
			// map.put("sectionDatas", chanelItemDOs);
			map.put("itemDatas", chanelItemDOs);
		}
		return "management/chanel/additemteam";
	}

	/**
	 * 删除分组内频道页面接口
	 * 
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/deleteitemteam/{id}")
	public String deleteitemteam(@PathVariable long id, Page page, String keywords, Map<String, Object> map,
			HttpServletRequest request) {
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();
		ChanelItemTeamQuery chanelItemTeamQuery = new ChanelItemTeamQuery();

		chanelItemQuery.setRegionId(getChanelRegion());
		map.put("teamid", id);
		map.put("keywords", keywords);
		PageUtils.convertPage(chanelItemQuery, page);

		// 获取bid
		Result<ChanelTeamDO> chanelTeamDOs = chanelTeamAO.getById(id);
		chanelItemTeamQuery.setTeamId(chanelTeamDOs.getValue().getId());
		Result<List<ChanelItemTeamDO>> chanelItemTeamResult = chanelItemTeamAO.getAllByTeamId(chanelItemTeamQuery);
		List<ChanelItemTeamDO> chanelItemTeamDOs = chanelItemTeamResult.getValue();
		List<Long> itemIds = new ArrayList<Long>();
		if (chanelItemTeamDOs != null && chanelItemTeamDOs.size() > 0) {
			for (ChanelItemTeamDO chanelItemTeamDO : chanelItemTeamDOs) {
				itemIds.add(chanelItemTeamDO.getItemId());
			}
		}
		// 查询结果
		if (keywords != null) {
			chanelItemQuery.setChName(keywords);
		}
		Result<List<ChanelItemDO>> chanelItemResult = chanelItemAO.querydelete(itemIds, chanelItemQuery);
		// Result<Integer>
		// chanelcount=chanelItemAO.querydeleteCount(chanelItemTeamDOs,chanelItemQuery);
		if (chanelItemResult.isSuccess()) {
			List<ChanelItemDO> chanelItemDOs = chanelItemResult.getValue();
			page.setTotalCount(chanelItemQuery.getTotalItem());
			map.put("itemDatas", chanelItemDOs);
		}
		return "management/chanel/deleteitemteam";
	}

	/**
	 * 删除分组内频道页面接口
	 * 
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/deleteitemteam/{teamid}/{itemid}")
	public @ResponseBody String deleteitemteam2(@PathVariable long teamid, @PathVariable long itemid, Page page,
			String keywords, Map<String, Object> map, HttpServletRequest request) {
		ChanelItemTeamDO chanelItemTeamDO = new ChanelItemTeamDO();
		chanelItemTeamDO.setTeamId(teamid);
		chanelItemTeamDO.setItemId(itemid);
		Result<Integer> result = chanelItemTeamAO.deleteByIds(chanelItemTeamDO);

		AjaxObject ajaxObject = result.isSuccess() ? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "删除成功!", "")
				: new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "删除失败!");
		return ajaxObject.toString();
	}

	// 分组添加频道接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/insertitemteam/{id}")
	public @ResponseBody String insertitemteam(@PathVariable long id, String itemid, Page page,
			HttpServletRequest request) {
		// 获取选中的id
		List<Long> ids = NumUtil.toLongs(StringUtils.split(itemid, ","));
		String msg;
		int state = 0;
		int successNum = 0, totalNum = ids.size();
		// 查出CHID
		for (Long item : ids) {
			ChanelItemTeamDO chanelItemTeamDO = new ChanelItemTeamDO();
			ChanelItemDO chanelItemDO = chanelItemAO.getById(item).getValue();
			if ((chanelItemDO.getChlogourl() != null) && (chanelItemDO.getChlogoName() != null)) {
				ChanelItemTeamQuery chanelItemTeamQuery=new ChanelItemTeamQuery();
				chanelItemTeamQuery.setTeamId(id);
				Result<List<ChanelItemTeamDO>> num=chanelItemTeamAO.queryAllByTeamId(chanelItemTeamQuery);
				chanelItemTeamDO.setTeamId(id);
				chanelItemTeamDO.setItemId(item);
				//选取id
				chanelItemTeamDO.setOrderId(num.getValue().size() + 1L);
				Result<Long> chanelItemteamResult = chanelItemTeamAO.save(chanelItemTeamDO);
				if (chanelItemteamResult.isSuccess()) {
					successNum++;
				}
			} else {
				state = AjaxObject.STATUS_CODE_FAILURE;
			}
		}

		if (successNum == totalNum) {
			msg = "添加成功！";
			state = AjaxObject.STATUS_CODE_SUCCESS;
		} else if (successNum > 0) {
			msg = "添加成功：" + successNum + "，失败或已被推荐：" + (totalNum - successNum);
			state = AjaxObject.STATUS_CODE_FAILURE;
		} else {
			msg = "缺少图片！";
		}
		AjaxObject ajaxObject = new AjaxObject(state, msg, AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	// 频道详细信息页面接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/item")
	public String item(Page page, Integer status, String keywords, Map<String, Object> map,
			HttpServletRequest request) {
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();
		chanelItemQuery.setRegionId(getChanelRegion());
		PageUtils.convertPage(chanelItemQuery, page);
		if (keywords != null) {
			chanelItemQuery.setChName(keywords);
			map.put("keywords", keywords);
		}
		Result<List<ChanelItemDO>> chanelItemResult = chanelItemAO.queryAll(chanelItemQuery);
		if (chanelItemResult.isSuccess()) {
			List<ChanelItemDO> chanelItemDOs = chanelItemResult.getValue();
			page.setTotalCount(chanelItemQuery.getTotalItem());
			map.put("sectionDatas", chanelItemDOs);
			map.put("chanelregion", getChanelRegion());
			if (chanelItemDOs != null && chanelItemDOs.size() > 0) {
				List<Long> filmIds = new ArrayList<Long>();
				for (ChanelItemDO chanelItemDO : chanelItemDOs) {
					filmIds.add(chanelItemDO.getId());
				}
				Result<List<ChanelItemDO>> filmResult = chanelItemAO.getByIds(filmIds);
				if (filmResult.isSuccess()) {
					map.put("itemDatas", filmResult.getValue());
				}
			}
		}
		return "management/chanel/item";
	}

	/**
	 * 直播推荐页面接口
	 * 
	 * @param request
	 * @return
	 */
	// 推荐首页
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/recommend")
	public String recommend(Page page, Map<String, Object> map, HttpServletRequest request) {
		ChanelTeamRecommendQuery chanelTeamRecommendQuery = new ChanelTeamRecommendQuery();
		chanelTeamRecommendQuery.setRegionId(getChanelRegion());
		PageUtils.convertPage(chanelTeamRecommendQuery, page);

		Result<List<ChanelTeamRecommendDO>> chanelTeamRecommendResult = chanelTeamRecommendAO
				.query(chanelTeamRecommendQuery);
		if (chanelTeamRecommendResult.isSuccess()) {
			List<ChanelTeamRecommendDO> ChanelTeamRecommendDOs = chanelTeamRecommendResult.getValue();
			page.setTotalCount(chanelTeamRecommendQuery.getTotalItem());
			map.put("rteamDatas", ChanelTeamRecommendDOs);
		}
		return "management/chanel/recommend";
	}

	// 5个推荐首页
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/recommend/cast/{id}")
	public String recommendcast(@PathVariable long id, Page page, Map<String, Object> map, HttpServletRequest request) {
		ChanelTeamRecommendQuery chanelTeamRecommendQuery = new ChanelTeamRecommendQuery();
		chanelTeamRecommendQuery.setRegionId(getChanelRegion());
		chanelTeamRecommendQuery.setId(id);
		PageUtils.convertPage(chanelTeamRecommendQuery, page);
		map.put("castid", id);
		// 返回分组表
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		chanelTeamQuery.setRegionId(getChanelRegion());
		Result<List<ChanelTeamDO>> result = chanelTeamAO.queryType(chanelTeamQuery);
		// 设置频道类型

		map.put("teamDatas", result.getValue());
		Result<ChanelTeamRecommendDO> chanelTeamRecommendResult = chanelTeamRecommendAO.getById(id);
		if (chanelTeamRecommendResult.isSuccess()) {
			ChanelTeamRecommendDO ChanelTeamRecommendDOs = chanelTeamRecommendResult.getValue();
			map.put("recommendDatas", ChanelTeamRecommendDOs);
		}
		return "management/chanel/recommendcast";
	}

	// 5个推荐首页
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/recommend/actor")
	public String recommendactor(Page page, Map<String, Object> map, HttpServletRequest request) {
		// 返回艺人数据
		ChanelActorQuery chanelActorQuery = new ChanelActorQuery();
		PageUtils.convertPage(chanelActorQuery, page);
		Result<List<ChanelActorDO>> chanelActorResult = chanelActorAO.queryRecommend(chanelActorQuery);
		// 设置频道类型
		page.setTotalCount(chanelActorQuery.getTotalItem());
		map.put("actorDatas", chanelActorResult.getValue());

		return "management/chanel/actor";
	}

	// 推荐首页
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/group/{id}")
	public String group(@PathVariable long id, Page page, Map<String, Object> map, String keywords,
			HttpServletRequest request) {
		ChanelItemTeamQuery chanelItemTeamQuery = new ChanelItemTeamQuery();
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();

		chanelItemTeamQuery.setTeamId(id);
		PageUtils.convertPage(chanelItemQuery, page);
		map.put("keywords", keywords);
		if (keywords != null) {
			chanelItemQuery.setChName(keywords);
		}
		map.put("teamid", id);
		Result<List<ChanelItemTeamDO>> itemTeamList = chanelItemTeamAO.getByTeamId(chanelItemTeamQuery);
		if (itemTeamList.isSuccess()) {
			List<ChanelItemTeamDO> chanelItemTeamDOs = itemTeamList.getValue();
			// page.setTotalCount(chanelItemTeamQuery.getTotalItem());

			if (chanelItemTeamDOs != null && chanelItemTeamDOs.size() > 0) {
				List<Long> itemIds = new ArrayList<Long>();
				for (ChanelItemTeamDO chanelItemTeamDO : chanelItemTeamDOs) {
					itemIds.add(chanelItemTeamDO.getItemId());
				}
				Result<List<ChanelItemDO>> filmResult = chanelItemAO.queryByIds(itemIds, chanelItemQuery);
				page.setTotalCount(chanelItemQuery.getTotalItem());
				if (filmResult.isSuccess()) {
					map.put("itemDatas", filmResult.getValue());
				}
			}
		}
		return "management/chanel/recommendcastgroup";
	}

	// 推荐首页
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/menu/{chid}/{castid}")
	public String amenu(@PathVariable String chid, Page page, @PathVariable String castid, String status,
			Map<String, Object> map, HttpServletRequest request) {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();
		chanelTeamQuery.setRegionId(getChanelRegion());
		chanelItemQuery.setRegionId(getChanelRegion());
		map.put("status", status);
		map.put("menuid", chid);
		map.put("castid", castid);
		List<DateDTO> datearea = new ArrayList<DateDTO>();
		// PageUtils.convertPage(chanelTeamQuery, page);
		chanelItemQuery.setChId(chid);
		Result<ChanelItemDO> result = chanelItemAO.queryRePlay(chanelItemQuery);
		// 判断是否有回放标志位
		if (result.isSuccess() && (result.getValue() != null)) {
			Result<AssertMenuAllDO> chanelTeamResult = chanelTeamAO.getReplay(chanelTeamQuery);
			if (chanelTeamResult.isSuccess()) {
				AssertMenuAllDO assertMenuAllDOs = chanelTeamResult.getValue();
				String folderDO = assertMenuAllDOs.getFolder();
				int size = assertMenuAllDOs.getCh().size();
				for (int i = 0; i < size; i++) {
					String date=null;
					if (assertMenuAllDOs.getCh().get(i).getAsset().getChM().getContentId().equals(chid)) {
						// int
						// insize=assertMenuAllDOs.getCh().getAsset().get(i).getChN().getToday();
						List<AssertMenuDTO> AssertMenuDO = new ArrayList<AssertMenuDTO>();
						String asset = assertMenuAllDOs.getCh().get(i).getAsset().getChM().getAssetId();
						Calendar c = Calendar.getInstance();
						//整理显示月份和日期
						date=Pack(c, date);
						DateDTO d1 = new DateDTO();
						d1.setDate(date);
						d1.setId(1);
						datearea.add(d1);
						if (status == null || status.equals("1")) {
							for (AssertMenuDO assertMenuDO : assertMenuAllDOs.getCh().get(i).getAsset().getChN()
									.getToday()) {
								AssertMenuDTO assertMenuDTO = new AssertMenuDTO();
								assertMenuDTO.setIndex(assertMenuDO.getIndex());
								assertMenuDTO.setName(assertMenuDO.getName().substring(9));
								assertMenuDTO.setDate(date);
								assertMenuDTO.setTime(assertMenuDO.getName().substring(0, 5));
								assertMenuDTO.setFolder(folderDO);
								assertMenuDTO.setAssetId(asset);
								AssertMenuDO.add(assertMenuDTO);
							}
						}
						c.add(Calendar.HOUR, -24);
						//整理显示月份和日期
						date=Pack(c, date);
						DateDTO d2 = new DateDTO();
						d2.setDate(date);
						d2.setId(2);
						datearea.add(d2);
						if (status != null) {
							if (status.equals("2")) {
								for (AssertMenuDO assertMenuDO : assertMenuAllDOs.getCh().get(i).getAsset().getChN()
										.getYsday()) {
									// assertMenuDO.setDay("昨天");
									AssertMenuDTO assertMenuDTO = new AssertMenuDTO();
									assertMenuDTO.setIndex(assertMenuDO.getIndex());
									assertMenuDTO.setName(assertMenuDO.getName().substring(9));

									assertMenuDTO.setDate(date);
									assertMenuDTO.setTime(assertMenuDO.getName().substring(0, 5));
									assertMenuDTO.setFolder(folderDO);
									assertMenuDTO.setAssetId(asset);
									AssertMenuDO.add(assertMenuDTO);
								}
							}
						}
						c.add(Calendar.HOUR, -24);
						//整理显示月份和日期
						date=Pack(c, date);
						DateDTO d3 = new DateDTO();
						d3.setDate(date);
						d3.setId(3);
						datearea.add(d3);
						if (status != null) {
							if (status.equals("3")) {
								for (AssertMenuDO assertMenuDO : assertMenuAllDOs.getCh().get(i).getAsset().getChN()
										.getBeday()) {
									// assertMenuDO.setDay("前天");
									AssertMenuDTO assertMenuDTO = new AssertMenuDTO();
									assertMenuDTO.setIndex(assertMenuDO.getIndex());
									assertMenuDTO.setName(assertMenuDO.getName().substring(9));
									assertMenuDTO.setDate(date);
									assertMenuDTO.setTime(assertMenuDO.getName().substring(0, 5));
									assertMenuDTO.setFolder(folderDO);
									assertMenuDTO.setAssetId(asset);

									AssertMenuDO.add(assertMenuDTO);
								}
							}
						}
						map.put("assertDatas", AssertMenuDO);
						break;
					}
				}
			} else {
				map.put("assertDatas", null);
			}
		}
		map.put("datearea", datearea);
		return "management/chanel/recommendcastmenu";
	}

	// 推荐首页
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/menu/{chid}")
	public String bmenu(@PathVariable String chid, Page page, String status, Map<String, Object> map,
			HttpServletRequest request) {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();
		chanelTeamQuery.setRegionId(getChanelRegion());
		chanelItemQuery.setRegionId(getChanelRegion());
		map.put("status", status);
		map.put("menuid", chid);
		map.put("castid", null);
		// PageUtils.convertPage(chanelTeamQuery, page);
		List<DateDTO> datearea = new ArrayList<DateDTO>();
		// PageUtils.convertPage(chanelTeamQuery, page);
		chanelItemQuery.setChId(chid);
		Result<ChanelItemDO> result = chanelItemAO.queryRePlay(chanelItemQuery);
		// 判断是否有回放标志位
		if (result.isSuccess() && (result.getValue() != null)) {
			Result<AssertMenuAllDO> chanelTeamResult = chanelTeamAO.getReplay(chanelTeamQuery);
			if (chanelTeamResult.isSuccess()) {
				AssertMenuAllDO assertMenuAllDOs = chanelTeamResult.getValue();
				String folderDO = assertMenuAllDOs.getFolder();
				int size = assertMenuAllDOs.getCh().size();
				for (int i = 0; i < size; i++) {
					String date=null;
					if (assertMenuAllDOs.getCh().get(i).getAsset().getChM().getContentId().equals(chid)) {
						// int
						// insize=assertMenuAllDOs.getCh().getAsset().get(i).getChN().getToday();
						List<AssertMenuDTO> AssertMenuDO = new ArrayList<AssertMenuDTO>();
						String asset = assertMenuAllDOs.getCh().get(i).getAsset().getChM().getAssetId();
						Calendar c = Calendar.getInstance();
						//整理显示月份和日期
						date=Pack(c, date);
						DateDTO d1 = new DateDTO();
						d1.setDate(date);
						d1.setId(1);
						datearea.add(d1);
						if (status == null || status.equals("1")) {
							for (AssertMenuDO assertMenuDO : assertMenuAllDOs.getCh().get(i).getAsset().getChN()
									.getToday()) {
								AssertMenuDTO assertMenuDTO = new AssertMenuDTO();
								assertMenuDTO.setIndex(assertMenuDO.getIndex());
								assertMenuDTO.setName(assertMenuDO.getName().substring(9));
								assertMenuDTO.setDate(date);
								assertMenuDTO.setTime(assertMenuDO.getName().substring(0, 5));
								assertMenuDTO.setFolder(folderDO);
								assertMenuDTO.setAssetId(asset);
								AssertMenuDO.add(assertMenuDTO);
							}
						}
						c.add(Calendar.HOUR, -24);
						//整理显示月份和日期
						date=Pack(c, date);
						DateDTO d2 = new DateDTO();
						d2.setDate(date);
						d2.setId(2);
						datearea.add(d2);
						if (status != null) {
							if (status.equals("2")) {
								for (AssertMenuDO assertMenuDO : assertMenuAllDOs.getCh().get(i).getAsset().getChN()
										.getYsday()) {
									// assertMenuDO.setDay("昨天");
									AssertMenuDTO assertMenuDTO = new AssertMenuDTO();
									assertMenuDTO.setIndex(assertMenuDO.getIndex());
									assertMenuDTO.setName(assertMenuDO.getName().substring(9));

									assertMenuDTO.setDate(date);
									assertMenuDTO.setTime(assertMenuDO.getName().substring(0, 5));
									assertMenuDTO.setFolder(folderDO);
									assertMenuDTO.setAssetId(asset);
									AssertMenuDO.add(assertMenuDTO);
								}
							}
						}
						c.add(Calendar.HOUR, -24);
						//整理显示月份和日期
						date=Pack(c, date);
						DateDTO d3 = new DateDTO();
						d3.setDate(date);
						d3.setId(3);
						datearea.add(d3);
						if (status != null) {
							if (status.equals("3")) {
								for (AssertMenuDO assertMenuDO : assertMenuAllDOs.getCh().get(i).getAsset().getChN()
										.getBeday()) {
									// assertMenuDO.setDay("前天");
									AssertMenuDTO assertMenuDTO = new AssertMenuDTO();
									assertMenuDTO.setIndex(assertMenuDO.getIndex());
									assertMenuDTO.setName(assertMenuDO.getName().substring(9));
									assertMenuDTO.setDate(date);
									assertMenuDTO.setTime(assertMenuDO.getName().substring(0, 5));
									assertMenuDTO.setFolder(folderDO);
									assertMenuDTO.setAssetId(asset);

									AssertMenuDO.add(assertMenuDTO);
								}
							}
						}
						map.put("assertDatas", AssertMenuDO);
						break;
					}
				}
			} else {
				map.put("assertDatas", null);
			}
		}
		map.put("datearea", datearea);
		return "management/chanel/recommendcastmenu2";
	}

	@RequestMapping(value = "/recommendcast/group/{id}")
	@ResponseBody
	public String recommendcastGroup(@PathVariable long id, HttpServletRequest request) {
		ChanelItemTeamQuery chanelItemTeamQuery = new ChanelItemTeamQuery();
		chanelItemTeamQuery.setTeamId(id);
		Result<List<ChanelItemTeamDO>> itemTeamList = chanelItemTeamAO.getByTeamId(chanelItemTeamQuery);
		List<Long> ids = new ArrayList<Long>();
		for (ChanelItemTeamDO chanelItemTeamDO : itemTeamList.getValue()) {
			ids.add(chanelItemTeamDO.getItemId());
		}
		Result<List<ChanelItemDO>> itemResult = chanelItemAO.getByIds(ids);
		// Map<String, Object> json = dealResult(itemResult);
		// json.put("itemDatas", itemResult.getValue());
		String msg = "[";
		for (ChanelItemDO chanelItemDO : itemResult.getValue()) {
			msg += "[\"" + chanelItemDO.getChId() + "\",\"" + chanelItemDO.getChName() + "\"],";
		}
		msg = msg.substring(0, msg.length() - 1);
		msg += "]";
		return msg;
	}

	// 选择推荐频道页面接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/recommend/chanel")
	public String recommendchanel(Page page, Map<String, Object> map, String keywords, HttpServletRequest request) {
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();
		chanelItemQuery.setRegionId(getChanelRegion());
		PageUtils.convertPage(chanelItemQuery, page);
		map.put("keywords", keywords);

		if (keywords != null) {
			chanelItemQuery.setChName(keywords);
		}
		Result<List<ChanelItemDO>> chanelItemResult = chanelItemAO.queryrecommend(chanelItemQuery);
		if (chanelItemResult.isSuccess()) {
			List<ChanelItemDO> chanelItemDOs = chanelItemResult.getValue();
			page.setTotalCount(chanelItemQuery.getTotalItem());
			map.put("sectionDatas", chanelItemDOs);

			if (chanelItemDOs != null && chanelItemDOs.size() > 0) {
				List<Long> filmIds = new ArrayList<Long>();
				for (ChanelItemDO chanelItemDO : chanelItemDOs) {
					filmIds.add(chanelItemDO.getId());
				}
				Result<List<ChanelItemDO>> filmResult = chanelItemAO.getByIds(filmIds);
				if (filmResult.isSuccess()) {
					map.put("itemDatas", filmResult.getValue());
				}
			}
		}
		return "management/chanel/r_chanel";
	}

	// 根据推荐频道选择回放节目单页面接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/recommend/replay/{chid}")
	public String recommendreplay(@PathVariable String chid, Map<String, Object> map, HttpServletRequest request) {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();
		chanelTeamQuery.setRegionId(getChanelRegion());
		chanelItemQuery.setRegionId(getChanelRegion());
		// PageUtils.convertPage(chanelTeamQuery, page);
		chanelItemQuery.setChId(chid);
		Result<ChanelItemDO> result = chanelItemAO.queryRePlay(chanelItemQuery);
		// 判断是否有回放标志位
		if (result.isSuccess() && (result.getValue() != null)) {
			Result<AssertMenuAllDO> chanelTeamResult = chanelTeamAO.getReplay(chanelTeamQuery);
			if (chanelTeamResult.isSuccess()) {
				AssertMenuAllDO assertMenuAllDOs = chanelTeamResult.getValue();

				int size = assertMenuAllDOs.getCh().size();
				for (int i = 0; i < size; i++) {
					if (assertMenuAllDOs.getCh().get(i).getAsset().getChM().getContentId().equals(chid)) {
						// int
						// insize=assertMenuAllDOs.getCh().getAsset().get(i).getChN().getToday();
						List<AssertMenuDO> AssertMenuDO = new ArrayList<AssertMenuDO>();
						for (AssertMenuDO assertMenuDO : assertMenuAllDOs.getCh().get(i).getAsset().getChN()
								.getToday()) {
							assertMenuDO.setDay("今天");
							AssertMenuDO.add(assertMenuDO);
						}
						for (AssertMenuDO assertMenuDO : assertMenuAllDOs.getCh().get(i).getAsset().getChN()
								.getYsday()) {
							assertMenuDO.setDay("昨天");
							AssertMenuDO.add(assertMenuDO);
						}
						for (AssertMenuDO assertMenuDO : assertMenuAllDOs.getCh().get(i).getAsset().getChN()
								.getBeday()) {
							assertMenuDO.setDay("前天");
							AssertMenuDO.add(assertMenuDO);
						}
						map.put("assertDatas", AssertMenuDO);
					}
				}
			} else {
				map.put("assertDatas", null);
			}
		}

		return "management/chanel/r_replay";
	}

	// 根据推荐频道选择直播节目单页面接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/recommend/broadcast/{chid}")
	public String recommendbroadcast(@PathVariable String chid, Page page, Map<String, Object> map,
			HttpServletRequest request) {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		chanelTeamQuery.setRegionId(getChanelRegion());
		PageUtils.convertPage(chanelTeamQuery, page);

		Result<MenuAllDO> chanelTeamResult = chanelTeamAO.getSingleMenu(chanelTeamQuery, chid);
		if (chanelTeamResult.isSuccess()) {
			MenuAllDO menuAllDOs = chanelTeamResult.getValue();
			List<MenuInfoDO> MenuInfoDO = new ArrayList<MenuInfoDO>();
			int i = 0;
			for (MenuInfoDO menuInfoDO : menuAllDOs.getChList().get(0).getEventList()) {
				menuInfoDO.setEventId(String.valueOf(i++));
				MenuInfoDO.add(menuInfoDO);
			}
			page.setTotalCount(chanelTeamQuery.getTotalItem());
			map.put("menuDatas", MenuInfoDO);
		}
		return "management/chanel/r_broadcast";
	}

	// 根据推荐频道选择直播节目单页面接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/vods/{chid}")
	public String vod1(@PathVariable String chid, Page page, Map<String, Object> map, HttpServletRequest request) {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		chanelTeamQuery.setRegionId(getChanelRegion());
		PageUtils.convertPage(chanelTeamQuery, page);
		map.put("vodid", chid);

		Result<MenuAllDO> chanelTeamResult = chanelTeamAO.getCertainMenu(chanelTeamQuery, chid);
		if (chanelTeamResult.isSuccess()) {
			MenuAllDO menuAllDOs = chanelTeamResult.getValue();
			List<MenuInfoDO> aMenuInfoDOs = menuAllDOs.getChList().get(0).getMenuList();
			List<MenuInfoDTO> MenuInfoDTO = new ArrayList<MenuInfoDTO>();
			for (MenuInfoDO menuInfoDO : aMenuInfoDOs) {
				MenuInfoDTO menuInfoDTO = new MenuInfoDTO();
				menuInfoDTO.setName(menuInfoDO.getEventName());
				menuInfoDTO.setStartDate(
						menuInfoDO.getStartTime().substring(5, 7) + "/" + menuInfoDO.getStartTime().substring(8, 10));
				menuInfoDTO.setStartTime(menuInfoDO.getStartTime().substring(11, 16));
				menuInfoDTO.setStopDate(
						menuInfoDO.getEndTime().substring(5, 7) + "/" + menuInfoDO.getEndTime().substring(8, 10));
				menuInfoDTO.setStopTime(menuInfoDO.getEndTime().substring(11, 16));
				MenuInfoDTO.add(menuInfoDTO);
			}
			page.setTotalCount(chanelTeamQuery.getTotalItem());
			map.put("vodDatas", MenuInfoDTO);
		}
		return "management/chanel/recommendcastvod";
	}

	// 根据推荐频道选择直播节目单页面接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/vod/{chid}")
	public String vod(@PathVariable String chid, Page page, String status, Map<String, Object> map,
			HttpServletRequest request) throws ParseException {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		chanelTeamQuery.setRegionId(getChanelRegion());
		ChanelItemQuery chanelItemQuery=new ChanelItemQuery();
		chanelItemQuery.setChId(chid);
		chanelItemQuery.setRegionId(getChanelRegion());
		PageUtils.convertPage(chanelTeamQuery, page);
		map.put("status", status);
		map.put("vodid", chid);
		
		List<DateDTO> datearea = new ArrayList<DateDTO>();
		
		
		Result<List<ChanelItemDO>> chanelItemDOs=chanelItemAO.query(chanelItemQuery);
		map.put("chanelName", chanelItemDOs.getValue().get(0).getByname());

		Result<MenuAllDO> chanelTeamResult = chanelTeamAO.getCertainMenu(chanelTeamQuery, chid);
		if (chanelTeamResult.isSuccess()) {
			MenuAllDO menuAllDOs = chanelTeamResult.getValue();
			List<MenuInfoDO> aMenuInfoDOs = menuAllDOs.getChList().get(0).getMenuList();
			List<MenuInfoDTO> MenuInfoDTO = new ArrayList<MenuInfoDTO>();

			Date nowDate = new Date();
			int year = nowDate.getYear();
			int month = nowDate.getMonth();
			int day = nowDate.getDay();
			if (status == null || status.equals("1")) {
				for (MenuInfoDO menuInfoDO : aMenuInfoDOs) {
					String endtime = menuInfoDO.getEndTime();
					String starttime = menuInfoDO.getStartTime();
					SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date enDate = myFmt.parse(endtime);
					Date stDate = myFmt.parse(starttime);
					int delta = stDate.getDay() - day;
					if ((stDate.getYear() == year) && (stDate.getMonth() == month) && (delta == 0)) {
						MenuInfoDTO menuInfoDTO = new MenuInfoDTO();
						menuInfoDTO.setName(menuInfoDO.getEventName());
						menuInfoDTO.setStartDate(menuInfoDO.getStartTime().substring(5, 7) + "/"
								+ menuInfoDO.getStartTime().substring(8, 10));
						menuInfoDTO.setStartTime(menuInfoDO.getStartTime().substring(11, 16));
						menuInfoDTO.setStopDate(menuInfoDO.getEndTime().substring(5, 7) + "/"
								+ menuInfoDO.getEndTime().substring(8, 10));
						menuInfoDTO.setStopTime(menuInfoDO.getEndTime().substring(11, 16));
						MenuInfoDTO.add(menuInfoDTO);
					}
				}
			} else if (status.equals("2")) {
				for (MenuInfoDO menuInfoDO : aMenuInfoDOs) {
					String endtime = menuInfoDO.getEndTime();
					String starttime = menuInfoDO.getStartTime();
					SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date enDate = myFmt.parse(endtime);
					Date stDate = myFmt.parse(starttime);
					int delta = stDate.getDay() - day;
					if ((stDate.getYear() == year) && (stDate.getMonth() == month) && (delta == 1)) {
						MenuInfoDTO menuInfoDTO = new MenuInfoDTO();
						menuInfoDTO.setName(menuInfoDO.getEventName());
						menuInfoDTO.setStartDate(menuInfoDO.getStartTime().substring(5, 7) + "/"
								+ menuInfoDO.getStartTime().substring(8, 10));
						menuInfoDTO.setStartTime(menuInfoDO.getStartTime().substring(11, 16));
						menuInfoDTO.setStopDate(menuInfoDO.getEndTime().substring(5, 7) + "/"
								+ menuInfoDO.getEndTime().substring(8, 10));
						menuInfoDTO.setStopTime(menuInfoDO.getEndTime().substring(11, 16));
						MenuInfoDTO.add(menuInfoDTO);
					}
				}
			} else if (status.equals("3")) {
				for (MenuInfoDO menuInfoDO : aMenuInfoDOs) {
					String endtime = menuInfoDO.getEndTime();
					String starttime = menuInfoDO.getStartTime();
					SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date enDate = myFmt.parse(endtime);
					Date stDate = myFmt.parse(starttime);
					int delta = stDate.getDay() - day;
					if ((stDate.getYear() == year) && (stDate.getMonth() == month) && (delta == 2)) {
						MenuInfoDTO menuInfoDTO = new MenuInfoDTO();
						menuInfoDTO.setName(menuInfoDO.getEventName());
						menuInfoDTO.setStartDate(menuInfoDO.getStartTime().substring(5, 7) + "/"
								+ menuInfoDO.getStartTime().substring(8, 10));
						menuInfoDTO.setStartTime(menuInfoDO.getStartTime().substring(11, 16));
						menuInfoDTO.setStopDate(menuInfoDO.getEndTime().substring(5, 7) + "/"
								+ menuInfoDO.getEndTime().substring(8, 10));
						menuInfoDTO.setStopTime(menuInfoDO.getEndTime().substring(11, 16));
						MenuInfoDTO.add(menuInfoDTO);
					}
				}
			}
			// page.setTotalCount(MenuInfoDTO.size());
			map.put("vodDatas", MenuInfoDTO);
		}
		// 设置选择位置

		Calendar c = Calendar.getInstance();
		String date = null;
		for (int i = 1; i < 4; i++) {
			date = (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DATE);
			DateDTO d3 = new DateDTO();
			d3.setDate(date);
			d3.setId(i);
			datearea.add(d3);
			c.add(Calendar.HOUR, 24);
		}
		map.put("datearea", datearea);
		
		
		return "management/chanel/recommendcastvod";
	}

	// 添加推荐频道页面接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/readditem")
	public String readditem(Map<String, Object> map) {
		map.put("chanelregions", getAllChanelRegion());
		return "management/chanel/r_additem";
	}

	// 插入推荐频道接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/reinsertteam")
	public @ResponseBody String reinsertitem(Page page, ChanelTeamRecommendDO chanelTeamRecommendDO,
			HttpServletRequest request) {

		Result<Long> chanelTeamRecommendResult = chanelTeamRecommendAO.save(chanelTeamRecommendDO);

		String msg;
		if (chanelTeamRecommendResult.isSuccess()) {
			msg = "插入成功！";
		} else {
			msg = "插入失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	/**
	 * 编辑推荐频道页面接口
	 * 
	 * @param secId
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/reeditteam/{id}")
	public String reedititem(@PathVariable long id, HttpServletRequest request, Map<String, Object> map) {
		Result<ChanelTeamRecommendDO> result = chanelTeamRecommendAO.getById(id);
		ChanelTeamRecommendDO chanelTeamRecommendDO = result.getValue();
		if (result.isSuccess()) {
			map.put("item", chanelTeamRecommendDO);
		}
		map.put("chanelregions", getAllChanelRegion());
		// return ajaxObject.toString();
		return "management/chanel/r_editteam";
	}

	// 更新推荐频道接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/reupdateteam")
	public @ResponseBody String reupdateitem(Page page, ChanelTeamRecommendDO chanelTeamRecommendDO,
			HttpServletRequest request) {

		Result<Integer> chanelTeamRecommendResult = chanelTeamRecommendAO.update(chanelTeamRecommendDO);

		String msg;
		if (chanelTeamRecommendResult.isSuccess()) {
			msg = "更新成功！";
		} else {
			msg = "更新失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	/**
	 * 删除推荐频道接口
	 * 
	 * @param secId
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/redelete/team/{id}")
	public @ResponseBody String reitemDelete(@PathVariable long id, HttpServletRequest request) {
		Result<Integer> result = chanelTeamRecommendAO.deleteById(id);
		AjaxObject ajaxObject = result.isSuccess() ? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "删除成功!", "")
				: new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "删除失败!");
		return ajaxObject.toString();
	}

	/**
	 * 新增频道详细信息页面接口
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/additem")
	public String additem(Map<String, Object> map) {
		return "management/chanel/additem";
	}

	/**
	 * 新增频道详细信息页面接口
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/addactor")
	public String addactor(Map<String, Object> map) {
		map.put("chanelregions", getAllChanelRegion());
		return "management/chanel/addactor";
	}

	// 插入频道详细信息接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/insertitem")
	public @ResponseBody String insertitem(Page page, ChanelItemDO chanelItemDO, HttpServletRequest request) {
		chanelItemDO.setRegionId(getChanelRegion());
		chanelItemDO.setByname(chanelItemDO.getChName());
		Result<Long> chanelItemResult = chanelItemAO.save(chanelItemDO);

		String msg;
		if (chanelItemResult.isSuccess()) {
			msg = "插入成功！";
		} else {
			msg = "插入失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	// 插入频道详细信息接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/insertactor")
	public @ResponseBody String insertactor(Page page, ChanelActorDO chanelActorDO, HttpServletRequest request) {

		Result<Long> chanelItemResult = chanelActorAO.save(chanelActorDO);
		chanelActorAO.sort(chanelActorDO.getId());
		String msg;
		if (chanelItemResult.isSuccess()) {
			msg = "插入成功！";
		} else {
			msg = "插入失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	/**
	 * 编辑频道详细信息页面接口
	 * 
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/edititem/{id}")
	public String edititem(@PathVariable long id, HttpServletRequest request, Map<String, Object> map) {
		Result<ChanelItemDO> result = chanelItemAO.getById(id);
		ChanelItemDO chanelItemDO = result.getValue();
		if (result.isSuccess()) {
			map.put("item", chanelItemDO);
		}
		map.put("chanelregions", getAllChanelRegion());
		// return ajaxObject.toString();
		return "management/chanel/edititem";
	}

	/**
	 * 编辑艺人详细信息页面接口
	 * 
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/editactor/{id}")
	public String editactor(@PathVariable long id, HttpServletRequest request, Map<String, Object> map) {
		Result<ChanelActorDO> result = chanelActorAO.getById(id);
		ChanelActorDO chanelActorDO = result.getValue();
		if (result.isSuccess()) {
			map.put("item", chanelActorDO);
		}
		return "management/chanel/editactor";
	}

	/**
	 * 搜索艺人详细信息页面接口
	 * 
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/searchactor")
	public String searchactor(HttpServletRequest request, Map<String, Object> map) {
		String keywords = request.getParameter("keywords");
		log.info("pass keywords:"+keywords);
		if ((keywords != null)&&(keywords.length()>0)) {
			try {
				String keywords1 =URLDecoder.decode(keywords,"UTF-8");
				log.info("UTF-8 keywords:"+keywords1);
				
				//String keywordUnicide=gbEncoding(keywords1);
				Result<List<ActorDTO>> result = chanelActorAO.queryActor1(keywords1);
				//Result<List<ActorDTO>> result = chanelActorAO.queryActor1(keywords1);
				if (result.isSuccess()) {
					map.put("actorDatas", result.getValue());
				}
				map.put("keywords", keywords1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Result<List<ActorDTO>> result = chanelActorAO.queryActor(keywords);
			
			
		}
		return "management/chanel/seactor";
	}
	/**
	 * 搜索艺人详细信息页面接口
	 * 
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/searchsactor")
	public String searchsactor(HttpServletRequest request, Map<String, Object> map) {
		String keywords = request.getParameter("keywords");
		log.info("pass keywords:"+keywords);
		if ((keywords != null)&&(keywords.length()>0)) {
			try {
				String keywords1 =URLDecoder.decode(keywords,"UTF-8");
				log.info("UTF-8 keywords:"+keywords1);
				
				//String keywordUnicide=gbEncoding(keywords1);
				Result<List<ActorDTO>> result = chanelActorAO.queryActor1(keywords1);
				//Result<List<ActorDTO>> result = chanelActorAO.queryActor1(keywords1);
				if (result.isSuccess()) {
					map.put("actorDatas", result.getValue());
				}
				map.put("keywords", keywords1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Result<List<ActorDTO>> result = chanelActorAO.queryActor(keywords);
			
			
		}
		return "management/chanel/searchactor";
	}
	/**
	 * 搜索艺人详细信息页面接口
	 * 
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/searchactors")
	public String searchactors(HttpServletRequest request, Map<String, Object> map) {
		String keywords = request.getParameter("keywords");
		log.info("pass keywords:"+keywords);
		map.put("keywords", keywords);
		if (keywords != null) {
			try {
				String keywords1 =URLEncoder.encode(keywords,"UTF-8");
				log.info("UTF-8 keywords:"+keywords1);
				
				Result<List<ActorDTO>> result = chanelActorAO.queryActor(keywords1);
				if (result.isSuccess()) {
					map.put("actorDatas", result.getValue());
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return "management/chanel/searchactor";
	}

	// 更新频道详细信息接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/updateitem")
	public @ResponseBody String updateitem(Page page, ChanelItemDO chanelItemDO, HttpServletRequest request) {

		Result<Integer> chanelItemResult = chanelItemAO.update(chanelItemDO);

		String msg;
		if (chanelItemResult.isSuccess()) {
			msg = "更新成功！";
		} else {
			msg = "更新失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	// 更新频道详细信息接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/updateactor")
	public @ResponseBody String updateactor(Page page, ChanelActorDO chanelActorDO, HttpServletRequest request) {

		Result<Integer> chanelActorResult = chanelActorAO.update(chanelActorDO);
		chanelActorAO.sort(chanelActorDO.getId());
		String msg;
		if (chanelActorResult.isSuccess()) {
			msg = "更新成功！";
		} else {
			msg = "更新失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}
	/**
	 * 
	 * 发布到前台
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/updatePublishActor/{id}")
	@ResponseBody
	public String updatePublishActor(@PathVariable long id,HttpServletRequest request){
		Result<Integer> result = chanelActorAO.publishById(id);
//		chanelActorAO.sort(id);
		if(result.isSuccess()){
			ChanelTeamController.cleanCache();
		}
		AjaxObject ajaxObject = result.isSuccess()? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS,"发布成功!","")
		          :new AjaxObject(AjaxObject.STATUS_CODE_FAILURE,"发布失败!");
		return ajaxObject.toString();
	}

	/**
	 * 删除频道信息接口
	 * 
	 * @param secId
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/delete/item/{id}")
	public @ResponseBody String itemDelete(@PathVariable long id, HttpServletRequest request) {
		Result<Integer> result = chanelItemAO.deleteById(id);
		AjaxObject ajaxObject = result.isSuccess() ? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "删除成功!", "")
				: new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "删除失败!");
		return ajaxObject.toString();
	}

	/**
	 * 删除频道信息接口
	 * 
	 * @param secId
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/delete/actor/{id}")
	public @ResponseBody String actorDelete(@PathVariable long id, HttpServletRequest request) {
		Result<Integer> result = chanelActorAO.deleteById(id);
		AjaxObject ajaxObject = result.isSuccess() ? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "删除成功!", "")
				: new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "删除失败!");
		return ajaxObject.toString();
	}

	/**
	 * 自主运营页面接口
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/selfcontrol")
	public String selefcontrol(Map<String, Object> map, HttpServletRequest request) {
		ChanelTeamSelfcontrolQuery chanelTeamSelfcontrolQuery = new ChanelTeamSelfcontrolQuery();
		chanelTeamSelfcontrolQuery.setRegionId(getChanelRegion());

		Result<List<ChanelTeamSelfcontrolDO>> chanelTeamSelfcontrolResult = chanelTeamSelfcontrolAO
				.query(chanelTeamSelfcontrolQuery);
		if (chanelTeamSelfcontrolResult.isSuccess()) {
			ChanelTeamSelfcontrolDO chanelTeamSelfcontrolDOs = chanelTeamSelfcontrolResult.getValue().get(0);
			map.put("selfDatas", chanelTeamSelfcontrolDOs);
		}
		return "management/chanel/selfcontrol1";
	}

	// 选择自主运营频道页面接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/selfcontrol/chanel")
	public String selfcontrolchanel(Page page, Map<String, Object> map, HttpServletRequest request) {
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();
		chanelItemQuery.setRegionId(getChanelRegion());
		PageUtils.convertPage(chanelItemQuery, page);

		Result<List<ChanelItemDO>> chanelItemResult = chanelItemAO.query(chanelItemQuery);
		if (chanelItemResult.isSuccess()) {
			List<ChanelItemDO> chanelItemDOs = chanelItemResult.getValue();
			page.setTotalCount(chanelItemQuery.getTotalItem());
			map.put("sectionDatas", chanelItemDOs);

			if (chanelItemDOs != null && chanelItemDOs.size() > 0) {
				List<Long> filmIds = new ArrayList<Long>();
				for (ChanelItemDO chanelItemDO : chanelItemDOs) {
					filmIds.add(chanelItemDO.getId());
				}
				Result<List<ChanelItemDO>> filmResult = chanelItemAO.getByIds(filmIds);
				if (filmResult.isSuccess()) {
					map.put("itemDatas", filmResult.getValue());
				}
			}
		}
		return "management/chanel/s_chanel";
	}

	// 添加自主运营页面接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/seadditem")
	public String seadditem(Map<String, Object> map) {
		map.put("chanelregions", getAllChanelRegion());
		return "management/chanel/s_additem";
	}

	// 插入自主运营接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/seinsertteam")
	public @ResponseBody String seinsertitem(Page page, ChanelTeamSelfcontrolDO chanelTeamSelfcontrolDO,
			HttpServletRequest request) throws Exception {

		AjaxObject ajaxObject = new AjaxObject();
		// String fileId=FastdfsUtil.uploadFile(file.getInputStream(),
		// file.getOriginalFilename(), 2400);
		// chanelTeamSelfcontrolDO.setChPicture(fileId);
		// System.out.println(fileId);
		Result<Long> chanelTeamSelfcontrolResult = chanelTeamSelfcontrolAO.save(chanelTeamSelfcontrolDO);

		// FastdfsUtil.download(fileId, "C:/Users/kale/bak/111.jpg");

		String msg;
		msg = "success";
		if (chanelTeamSelfcontrolResult.isSuccess()) {
			msg = "插入成功！";
		} else {
			msg = "插入失败！";
		}
		ajaxObject.setMessage(msg);
		return ajaxObject.toString();
	}

	/**
 * 上传图片
 */
@RequestMapping(value = "/upload", method = { RequestMethod.POST })
@ResponseBody
public String upload(HttpServletRequest request) {
	try {
		log.info("start upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		FastdfsUtil aFastdfsUtil=new FastdfsUtil();
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		MultipartFile mfile = null;
		for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
			mfile = set.getValue();
			break;
		}
		if (mfile == null)
			return null;
		String fileId = "http://125.210.141.30/"
				+ aFastdfsUtil.uploadFile(mfile.getInputStream(), mfile.getOriginalFilename(), 800000);
		System.out.println(fileId);
		return fileId;
	} catch (Exception e) {
		log.error("upload error", e);
	}
	return null;
}

/**
* 上传图片
 * @throws Exception 
*/
@RequestMapping(value = "/uploads", method = { RequestMethod.POST })
@ResponseBody
public String uploads(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	
    return null; 
}

	/**
	 * 编辑自主运营频道页面接口
	 * 
	 * @param secId
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/seeditteam/{id}")
	public String seedititem(@PathVariable long id, HttpServletRequest request, Map<String, Object> map) {
		Result<ChanelTeamSelfcontrolDO> result = chanelTeamSelfcontrolAO.getById(id);
		ChanelTeamSelfcontrolDO chanelTeamSelfcontrolDO = result.getValue();
		if (result.isSuccess()) {
			map.put("item", chanelTeamSelfcontrolDO);
		}
		map.put("chanelregions", getAllChanelRegion());
		// return ajaxObject.toString();
		return "management/chanel/s_editteam";
	}

	// 更新自主运营频道接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/seupdateteam")
	public @ResponseBody String seupdateitem(Page page, ChanelTeamSelfcontrolDO chanelTeamSelfcontrolDO,
			HttpServletRequest request) {

		Result<Integer> chanelTeamSelfcontrolResult = chanelTeamSelfcontrolAO.update(chanelTeamSelfcontrolDO);

		String msg;
		AjaxObject ajaxObject = null;
		if (chanelTeamSelfcontrolResult.isSuccess()) {
			msg = "更新成功！";
			ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg, AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);
		} else {
			msg = "缺少图片！";
			ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, msg, AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);
		}

		return ajaxObject.toString();
	}

	// 更新运营频道接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/updateteamrecommend/{chid}")
	public @ResponseBody String updateteamrecommend(@PathVariable long chid,
			ChanelTeamRecommendDO chanelTeamRecommendDO, HttpServletRequest request) {
		chanelTeamRecommendDO.setRegionId(getChanelRegion());
		chanelTeamRecommendDO.setId(chid);
		if(chanelTeamRecommendDO.getBStatus() == 2){
			ChanelItemQuery aChanelItemQuery=new ChanelItemQuery();
			aChanelItemQuery.setChId(chanelTeamRecommendDO.getChId());
			Result<ChanelItemDO> bItemDO=chanelItemAO.queryByChid(aChanelItemQuery);
			chanelTeamRecommendDO.setBAssertid(bItemDO.getValue().getAssetid());
		}
		
		if(chanelTeamRecommendDO.getAStatus() == 2){
			ChanelItemQuery aChanelItemQuery=new ChanelItemQuery();
			aChanelItemQuery.setChId(chanelTeamRecommendDO.getChId());
			Result<ChanelItemDO> bItemDO=chanelItemAO.queryByChid(aChanelItemQuery);
			chanelTeamRecommendDO.setAAssertid(bItemDO.getValue().getAssetid());
		}
		// chanelTeamRecommendDO.setPicture("http://125.210.141.30/"+chanelTeamRecommendDO.getPicture());
		Result<Integer> chanelTeamRecommendResult = chanelTeamRecommendAO.update(chanelTeamRecommendDO);
		String msg;
		if (chanelTeamRecommendResult.isSuccess()) {
			msg = "更新成功！";
		} else {
			msg = "更新失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	// 图片预览接口
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/picturereview/{chid}")
	public @ResponseBody String picturereview(@PathVariable long chid, ChanelTeamRecommendDO chanelTeamRecommendDO,
			HttpServletRequest request) {
		chanelTeamRecommendDO.setRegionId(getChanelRegion());
		chanelTeamRecommendDO.setId(chid);
		// chanelTeamRecommendDO.setPicture("http://125.210.141.30/"+chanelTeamRecommendDO.getPicture());
		Result<Integer> chanelTeamRecommendResult = chanelTeamRecommendAO.update(chanelTeamRecommendDO);
		String msg;
		AjaxObject ajaxObject = null;
		if (chanelTeamRecommendResult.isSuccess()) {
			msg = "更新成功！";
			ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg, AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);
		} else {
			msg = "更新失败！";
			ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, msg, AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);
		}

		return ajaxObject.toString();
	}

	/**
	 * 删除推荐频道接口
	 * 
	 * @param secId
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/sedelete/team/{id}")
	public @ResponseBody String seitemDelete(@PathVariable long id, HttpServletRequest request) {
		Result<Integer> result = chanelTeamSelfcontrolAO.deleteById(id);
		AjaxObject ajaxObject = result.isSuccess() ? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "删除成功!", "")
				: new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "删除失败!");
		return ajaxObject.toString();
	}

	@RequestMapping(value = "/insert")
	@ResponseBody
	public Object insert(HttpServletRequest request) {
		Result<Long> result = chanelTeamAO.save(buildModel(request));
		Map<String, Object> json = dealResult(result);
		json.put("id", result.getValue());
		return json;
	}

	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/team")
	public String team(Page page, Integer status, Map<String, Object> map, HttpServletRequest request) {
		ChanelTeamQuery chanelTeamQuery = new ChanelTeamQuery();
		ChanelItemQuery chanelItemQuery = new ChanelItemQuery();
		PageUtils.convertPage(chanelItemQuery, page);

		Result<List<ChanelTeamDO>> chanelTeamResult = chanelTeamAO.query(chanelTeamQuery);
		if (chanelTeamResult.isSuccess()) {
			List<ChanelTeamDO> chanelTeamDOs = chanelTeamResult.getValue();
			page.setTotalCount(chanelTeamQuery.getTotalItem());
			map.put("sectionDatas", chanelTeamDOs);

			if (chanelTeamDOs != null && chanelTeamDOs.size() > 0) {
				List<Long> filmIds = new ArrayList<Long>();
				for (ChanelTeamDO chanelTeamDO : chanelTeamDOs) {
					filmIds.add(chanelTeamDO.getId());
				}
				Result<List<ChanelTeamDO>> filmResult = chanelTeamAO.getByIds(filmIds);
				if (filmResult.isSuccess()) {
					map.put("filmDatas", filmResult.getValue());
				}
			}
		}

		Result<List<ChanelItemDO>> chanelItemResult = chanelItemAO.query(chanelItemQuery);
		if (chanelTeamResult.isSuccess()) {
			List<ChanelItemDO> chanelItemDOs = chanelItemResult.getValue();
			page.setTotalCount(chanelItemQuery.getTotalItem());
			map.put("sectionDatas", chanelItemDOs);

			if (chanelItemDOs != null && chanelItemDOs.size() > 0) {
				List<Long> filmIds = new ArrayList<Long>();
				for (ChanelItemDO chanelItemDO : chanelItemDOs) {
					filmIds.add(chanelItemDO.getId());
				}
				Result<List<ChanelItemDO>> filmResult = chanelItemAO.getByIds(filmIds);
				if (filmResult.isSuccess()) {
					map.put("filmDatas1", filmResult.getValue());
				}
			}
		}
		return "management/chanel/team";
	}

	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/itemteam")
	public String itemteam(Page page, Integer status, Map<String, Object> map, HttpServletRequest request) {
		ChanelItemTeamQuery chanelItemTeamQuery = new ChanelItemTeamQuery();
		PageUtils.convertPage(chanelItemTeamQuery, page);

		Result<List<ChanelItemTeamDO>> chanelItemteamResult = chanelItemTeamAO.query(chanelItemTeamQuery);
		if (chanelItemteamResult.isSuccess()) {
			List<ChanelItemTeamDO> chanelItemteamDOs = chanelItemteamResult.getValue();
			page.setTotalCount(chanelItemTeamQuery.getTotalItem());
			map.put("sectionDatas", chanelItemteamDOs);

			if (chanelItemteamDOs != null && chanelItemteamDOs.size() > 0) {
				List<Long> filmIds = new ArrayList<Long>();
				for (ChanelItemTeamDO chanelItemTeamDO : chanelItemteamDOs) {
					filmIds.add(chanelItemTeamDO.getId());
				}
				Result<List<ChanelItemTeamDO>> filmResult = chanelItemTeamAO.getByIds(filmIds);
				if (filmResult.isSuccess()) {
					map.put("filmDatas", filmResult.getValue());
				}
			}
		}
		return "management/chanel/itemteam";
	}

	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/choose")
	public String choose(Page page, Integer status, Map<String, Object> map, HttpServletRequest request) {
		return "management/chanel/choose";
	}

	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/updateteam")
	public @ResponseBody String updateteam(Page page, ChanelTeamDO chanelTeamDO, HttpServletRequest request) {

		chanelTeamDO.setRegionId(getChanelRegion());
		Result<Integer> chanelTeamResult = chanelTeamAO.update(chanelTeamDO);

		String msg;
		if (chanelTeamResult.isSuccess()) {
			msg = "更新成功！";
		} else {
			msg = "更新失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/updateitemteam")
	public @ResponseBody String updateitemteam(Page page, ChanelItemDTO ChanelItemDTO, HttpServletRequest request) {
		ChanelItemTeamDO chanelItemTeamDO = new ChanelItemTeamDO();
		chanelItemTeamDO.setId(ChanelItemDTO.getId());
		chanelItemTeamDO.setOrderId(ChanelItemDTO.getOrderId());
		Result<Integer> chanelItemResult = chanelItemTeamAO.update(chanelItemTeamDO);

		String msg;
		if (chanelItemResult.isSuccess()) {
			msg = "更新成功！";
		} else {
			msg = "更新失败！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/test")
	public String test(Map<String, Object> map, HttpServletRequest request) {

		return "management/team/test";
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(HttpServletRequest request) {
		Result<Integer> result = chanelTeamAO.update(buildModel(request));
		Map<String, Object> json = dealResult(result);
		return json;
	}

	@RequestMapping(value = "/get/{id}")
	@ResponseBody
	public Object getById(@PathVariable long id) {
		Result<ChanelTeamDO> result = chanelTeamAO.getById(id);
		Map<String, Object> json = dealResult(result);
		json.put("data", result.getValue());
		return json;
	}

	/**
	 * 删除分组
	 * 
	 * @param secId
	 * @param id
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/delete/team/{id}")
	public @ResponseBody String filmSectionDelete(@PathVariable long id, HttpServletRequest request) {
		Result<Integer> result = chanelTeamAO.deleteById(id);
		AjaxObject ajaxObject = result.isSuccess() ? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "删除成功!", "")
				: new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "删除失败!");
		return ajaxObject.toString();
	}

	/**
	 * 鍒犻櫎鍏崇郴
	 * 
	 * @param secId
	 * @param id
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/delete/itemteam/{id}")
	public @ResponseBody String itemteamDelete(@PathVariable long id, HttpServletRequest request) {
		Result<Integer> result = chanelItemTeamAO.deleteById(id);
		AjaxObject ajaxObject = result.isSuccess() ? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "鍒犻櫎鎴愬姛!", "")
				: new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "删除失败!");
		return ajaxObject.toString();
	}

	/**
	 * 修改分组内频道优先级
	 * 
	 * @param secId
	 * @param page
	 * @param assetType
	 * @param map
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/edititemteam/{id}")
	public String edititemteam(@PathVariable long id, HttpServletRequest request, Map<String, Object> map) {
		Result<ChanelItemTeamDO> result = chanelItemTeamAO.getById(id);
		ChanelItemTeamDO chanelItemTeamDO = result.getValue();
		if (result.isSuccess()) {
			map.put("itemteam", chanelItemTeamDO);
		}
		// return ajaxObject.toString();
		return "management/chanel/edititemteam";
	}

	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/edititemteam/{teamid}/{itemid}")
	public String edititemteam2(@PathVariable long teamid, @PathVariable long itemid, HttpServletRequest request,
			Map<String, Object> map) {
		ChanelItemTeamQuery chanelItemTeamQuery = new ChanelItemTeamQuery();
		chanelItemTeamQuery.setTeamId(teamid);
		chanelItemTeamQuery.setItemId(itemid);
		Result<ChanelItemDTO> result = chanelItemTeamAO.queryDTO(chanelItemTeamQuery);
		if (result.isSuccess()) {
			map.put("itemteam", result.getValue());
		}
		// return ajaxObject.toString();
		return "management/chanel/edititemteam";
	}

	@RequestMapping(value = "/gets/{ids}")
	@ResponseBody
	public Object getByIds(@PathVariable String ids) {
		Result<List<ChanelTeamDO>> result = chanelTeamAO.getByIds(NumUtil.toLongs(ids, ","));
		Map<String, Object> json = dealResult(result);
		json.put("datas", result.getValue());
		return json;
	}

	@RequestMapping(value = "/query")
	@ResponseBody
	public Object query(HttpServletRequest request) {
		ChanelTeamQuery query = buildQuery(request);
		Result<List<ChanelTeamDO>> result = chanelTeamAO.query(query);
		Map<String, Object> json = dealResult(result);
		json.put("datas", result.getValue());
		json.put("query", query);
		return json;
	}

	private ChanelTeamQuery buildQuery(HttpServletRequest request) {
		ChanelTeamQuery query = new ChanelTeamQuery();
		return query;
	}

	private ChanelTeamDO buildModel(HttpServletRequest request) {
		ChanelTeamDO obj = new ChanelTeamDO();
		return obj;
	}

	private String getChanelRegion() {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject.getPrincipal();
		if (shiroUser.getRegionCode().equals("gansu")) {
			return "01_4001";
		} 
//		else if (shiroUser.getRegionCode().equals("maintain")) {
//			return "02_4001";
//		} 
		else {
			return null;
		}
	}

	private List<Region> getAllChanelRegion() {
		List<Region> regions = Lists.newArrayList();
		String s = LocalCache.get("set_sync_clps");
		if (StringUtils.isEmpty(s)) {
			return regions;
		}
		// String curRegionCode = getChanelRegion();
		JSONArray jsonArr = JSON.parseArray(s);
		for (Object o : jsonArr) {
			JSONObject json = (JSONObject) o;
			String regionCode = " ";
			String regionName = json.getString("name");
			if (regionName.equals("甘肃")) {
				regionCode = "01_4001";
			} 
//			else if (regionName.equals("维护")) {
//				regionCode = "02_4001";
//			}
			regions.add(new Region(regionCode, regionName));
		}
		return regions;
	}
	
	static String Pack(Calendar c,String date ){
		if(Integer.valueOf(c.get(Calendar.MONTH)) < 9){
			date = "0"+(c.get(Calendar.MONTH) + 1) + "/";
		}else{
			date = (c.get(Calendar.MONTH) + 1) + "/";
		}
		if(Integer.valueOf(c.get(Calendar.DATE)) < 10){
			date +="0"+c.get(Calendar.DATE);
		}else{
			date +=c.get(Calendar.DATE);
		}
		return date;
	}
	
	public static String gbEncoding(final String gbString) {   
        char[] utfBytes = gbString.toCharArray();   
              String unicodeBytes = "";   
               for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {   
                    String hexB = Integer.toHexString(utfBytes[byteIndex]);   
                      if (hexB.length() <= 2) {   
                          hexB = "00" + hexB;   
                     }   
                      unicodeBytes = unicodeBytes + "\\u" + hexB;   
                  }   
                  System.out.println("unicodeBytes is: " + unicodeBytes);   
                  return unicodeBytes;   
             } 

}

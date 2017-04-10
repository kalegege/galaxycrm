package com.wasu.ptyw.galaxycrm.web.controller.cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.util.NumUtil;
import com.wasu.ptyw.galaxy.core.ao.GalaxyFilmAO;
import com.wasu.ptyw.galaxy.core.ao.GalaxyFilmSectionAO;
import com.wasu.ptyw.galaxy.core.cache.LocalCache;
import com.wasu.ptyw.galaxy.dal.constant.FilmRecStatus;
import com.wasu.ptyw.galaxy.dal.constant.FilmStatus;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyFilmDO;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyFilmSectionDO;
import com.wasu.ptyw.galaxy.dal.query.GalaxyFilmQuery;
import com.wasu.ptyw.galaxy.dal.query.GalaxyFilmSectionQuery;
import com.wasu.ptyw.galaxycrm.core.dataobject.Region;
import com.wasu.ptyw.galaxycrm.web.controller.BaseController;

@Controller
@RequestMapping("/management/cinema/films")
public class FilmsController extends BaseController {

	@Autowired
	private GalaxyFilmAO galaxyFilmAO;

	@Autowired
	private GalaxyFilmSectionAO galaxyFilmSectionAO;

	private static final String LIST = "management/cinema/films/list";
	private static final String EDIT = "management/cinema/films/edit";
	private static final String TRAILER = "management/cinema/films/trailer";
	private static final String FILMSECTION = "management/cinema/films/filmSection";
	private static final String FILMSECTIONLIST = "management/cinema/films/filmSectionList";
	private static final String FILMSECTIONEDIT = "management/cinema/films/filmSectionEdit";

	/**
	 * 推荐列表
	 * 
	 * @param secId
	 * @param page
	 * @param status
	 * @param map
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/filmSection/{secId}")
	public String filmSection(@PathVariable Integer secId, Page page, Integer status, Map<String, Object> map,
			HttpServletRequest request) {

		GalaxyFilmSectionQuery filmSectionQuery = new GalaxyFilmSectionQuery();
		filmSectionQuery.setRegionCode(getRegionCode());
		filmSectionQuery.setSecId(secId);
		if (status != null && status > -1) {
			filmSectionQuery.setStatus(status);
		}

		PageUtils.convertPage(filmSectionQuery, page);
		filmSectionQuery.setOrderBy("priority");

		Result<List<GalaxyFilmSectionDO>> filmSectionResult = galaxyFilmSectionAO.query(filmSectionQuery);
		if (filmSectionResult.isSuccess()) {
			List<GalaxyFilmSectionDO> galaxyFilmSectionDOs = filmSectionResult.getValue();
			page.setTotalCount(filmSectionQuery.getTotalItem());
			map.put("sectionDatas", galaxyFilmSectionDOs);

			if (galaxyFilmSectionDOs != null && galaxyFilmSectionDOs.size() > 0) {
				List<Long> filmIds = new ArrayList<Long>();
				for (GalaxyFilmSectionDO galaxyFilmSectionDO : galaxyFilmSectionDOs) {
					filmIds.add(galaxyFilmSectionDO.getFilmId());
				}
				Result<List<GalaxyFilmDO>> filmResult = galaxyFilmAO.getByIds(filmIds);
				if (filmResult.isSuccess()) {
					map.put("filmDatas", filmResult.getValue());
				}
			}
		}

		map.put("regions", getAllRegion());
		map.put("secId", secId);
		map.put("page", page);
		map.put("status", status);

		return FILMSECTION;
	}

	/**
	 * 添加推荐选择电影列表
	 * 
	 * @param secId
	 * @param page
	 * @param assetType
	 * @param keywords
	 * @param map
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/filmSection/{secId}/list")
	public String filmSectionList(@PathVariable Integer secId, Page page, Integer assetType, String keywords,
			Map<String, Object> map, HttpServletRequest request, String isFirst) {
		if (!"false".equalsIgnoreCase(isFirst)) {
			page.setNumPerPage(30);
		}

		GalaxyFilmQuery filmQuery = new GalaxyFilmQuery();
		filmQuery.setRegionCode(getRegionCode());
		// if(assetType==null || assetType<=0){
		// assetType=36;
		// }
		if (assetType != null) {
			filmQuery.setAssetType(assetType);
		}
		if (!StringUtils.isEmpty(keywords)) {
			filmQuery.setLikeName(keywords);
		}

		if (FilmRecStatus.isToday(secId)) {
			filmQuery.setFolderCode("mov_hot_69_5");
		} else if (FilmRecStatus.isHot(secId)) {
			filmQuery.setFolderCode("mov_hot_69_5");
		} else if (FilmRecStatus.isTicket(secId)) {
			filmQuery.setFolderCode("mov_hot_69_5_2");
		}

		filmQuery.setStatus(FilmStatus.ONLINE.getCode());

		PageUtils.convertPage(filmQuery, page);

		Result<List<GalaxyFilmDO>> galaxyFilmResult = galaxyFilmAO.query(filmQuery);
		if (galaxyFilmResult.isSuccess()) {
			page.setTotalCount(filmQuery.getTotalItem());
			map.put("datas", galaxyFilmResult.getValue());
		}

		map.put("keywords", keywords);
		map.put("page", page);
		map.put("secId", secId);

		return FILMSECTIONLIST;
	}

	/**
	 * 添加推荐
	 * 
	 * @param secId
	 * @param page
	 * @param filmId
	 * @param map
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/filmSection/{secId}/save")
	public @ResponseBody
	String filmSectionSave(@PathVariable Integer secId, Page page, String filmIds, Map<String, Object> map,
			HttpServletRequest request) {
		List<Long> ids = NumUtil.toLongs(StringUtils.split(filmIds, ","));

		int successNum = 0, totalNum = ids.size();
		for (Long filmId : ids) {
			GalaxyFilmSectionQuery filmSectionQuery = new GalaxyFilmSectionQuery();
			filmSectionQuery.setRegionCode(getRegionCode());
			filmSectionQuery.setFilmId(filmId);
			filmSectionQuery.setSecId(secId);
			Result<List<GalaxyFilmSectionDO>> filmSectionResult = galaxyFilmSectionAO.query(filmSectionQuery);
			if (filmSectionResult.isSuccess()) {
				if (filmSectionResult.getValue().size() == 0) {
					GalaxyFilmSectionDO filmSectionDO = new GalaxyFilmSectionDO();
					filmSectionDO.setRegionCode(getRegionCode());
					filmSectionDO.setFilmId(filmId);
					filmSectionDO.setSecId(secId);
					filmSectionDO.setStatus(0);
					Result<Long> filmSectionSaveResult = galaxyFilmSectionAO.save(filmSectionDO);
					if (filmSectionSaveResult.isSuccess()) {
						successNum++;
					}
				}
			}
		}
		String msg;
		if (successNum == totalNum) {
			msg = "添加成功！";
		} else if (successNum > 0) {
			msg = "添加成功：" + successNum + "，失败或已被推荐：" + (totalNum - successNum);
		} else {
			msg = "推荐失败或已被推荐！";
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg,
				AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	/**
	 * 删除推荐
	 * 
	 * @param secId
	 * @param id
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/filmSection/{secId}/delete/{id}")
	public @ResponseBody
	String filmSectionDelete(@PathVariable Integer secId, @PathVariable Long id, HttpServletRequest request) {
		Result<Integer> result = galaxyFilmSectionAO.deleteById(id);
		AjaxObject ajaxObject = result.isSuccess() ? new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "删除成功!", "")
				: new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "删除失败!");
		return ajaxObject.toString();
	}

	/**
	 * 获取修改内容
	 * 
	 * @param secId
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/filmSection/{secId}/edit/{id}")
	public String filmSectionEdit(@PathVariable Integer secId, @PathVariable Long id, Map<String, Object> map) {

		Result<GalaxyFilmSectionDO> filmSectionResult = galaxyFilmSectionAO.getById(id);
		if (filmSectionResult.isSuccess()) {
			GalaxyFilmSectionDO filmSectionDO = filmSectionResult.getValue();
			Result<GalaxyFilmDO> filmResult = galaxyFilmAO.getById(filmSectionDO.getFilmId());
			if (filmResult.isSuccess()) {
				map.put("filmItem", filmResult.getValue());
				map.put("filmSectionItem", filmSectionDO);
			}
		}
		return FILMSECTIONEDIT;
	}

	/**
	 * 保存修改
	 * 
	 * @param secId
	 * @param filmSectionDO
	 * @return
	 */
	@RequiresPermissions("Galaxy:view")
	@RequestMapping(value = "/filmSection/{secId}/editSave")
	public @ResponseBody
	String filmSectionEditSave(@PathVariable Integer secId, GalaxyFilmSectionDO filmSectionDO, String aliasName,
			Integer aliasPrice, String weixinCode, String taobaoCode, String linkFilmIds) {
		AjaxObject ajaxObject = null;

		Long filmId = filmSectionDO.getFilmId();
		Result<GalaxyFilmDO> galaxyFilmResult = galaxyFilmAO.getById(filmId);
		GalaxyFilmDO galaxyFilmDO = null;
		if (!galaxyFilmResult.isSuccess()) {
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "修改失败！获取数据异常").toString();
		}

		galaxyFilmDO = galaxyFilmResult.getValue();
		galaxyFilmDO.setAliasName(aliasName);
		if (aliasPrice != null) {
			galaxyFilmDO.setAliasPrice(aliasPrice);
		}
		if (StringUtils.isNotEmpty(linkFilmIds)) {
			galaxyFilmDO.setLinkFilmIds(linkFilmIds);
		}
		if (StringUtils.isNotEmpty(taobaoCode)) {
			galaxyFilmDO.setTaobaoCode(taobaoCode);
		}
		if (StringUtils.isNotEmpty(weixinCode)) {
			galaxyFilmDO.setWeixinCode(weixinCode);
		}

		// 上线前校验
		if (filmSectionDO.getStatus() == 1) {
			ajaxObject = checkIfOnline(secId, galaxyFilmDO);
			if (ajaxObject != null) {
				return ajaxObject.toString();
			}
		}

		Result<Integer> result = galaxyFilmAO.update(galaxyFilmDO);
		if (!result.isSuccess()) {
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "修改失败！更新影片表失败").toString();
		}
		Result<Integer> filmSectionResult = galaxyFilmSectionAO.update(filmSectionDO);
		if (!filmSectionResult.isSuccess()) {
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "修改失败！更新影片关联表失败").toString();
		}
		return new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, "修改成功!").toString();
	}

	@RequiresPermissions("Cinema_films:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, String keywords, Integer status, String folderCode, Map<String, Object> map,
			HttpServletRequest request) {

		GalaxyFilmQuery filmQuery = new GalaxyFilmQuery();
		filmQuery.setRegionCode(getRegionCode());
		if (StringUtils.isNotEmpty(keywords)) {
			filmQuery.setLikeName(keywords);
		}
		if (status != null && status > -1) {
			filmQuery.setStatus(status);
		}
		if (StringUtils.isNotEmpty(folderCode)) {
			filmQuery.setFolderCode(folderCode);
		}

		PageUtils.convertPage(filmQuery, page);
		Result<List<GalaxyFilmDO>> queryResult = galaxyFilmAO.query(filmQuery);

		if (queryResult.isSuccess()) {
			page.setTotalCount(filmQuery.getTotalItem());
			map.put("datas", queryResult.getValue());
		}

		map.put("page", page);
		map.put("keywords", keywords);
		map.put("status", status);
		map.put("filmStatusList", FilmStatus.values());
		map.put("folderCode", folderCode);
		return LIST;
	}

	@RequiresPermissions("Cinema_films:edit")
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable Long id, Map<String, Object> map) {
		Result<GalaxyFilmDO> result = galaxyFilmAO.getById(id);
		if (result.isSuccess()) {
			map.put("item", result.getValue());
		}
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject.getPrincipal();
		map.put("loginName", shiroUser.getLoginName());
		map.put("filmStatusList", FilmStatus.values());
		return EDIT;
	}

	@RequiresPermissions("Cinema_films:edit")
	@RequestMapping(value = "/editSave", method = { RequestMethod.POST })
	public @ResponseBody
	String editSave(GalaxyFilmDO galaxyFilmDO) {
		Result<Integer> result = galaxyFilmAO.update(galaxyFilmDO);
		AjaxObject ajaxObject = new AjaxObject(result.isSuccess() ? "修改成功!" : "修改失败！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Cinema_films")
	@RequestMapping(value = "/trailer", method = { RequestMethod.GET, RequestMethod.POST })
	public String trailer(Page page, String keywords, Map<String, Object> map, HttpServletRequest request,
			String isFirst) {
		if (!"false".equalsIgnoreCase(isFirst)) {
			page.setNumPerPage(30);
		}

		GalaxyFilmQuery filmQuery = new GalaxyFilmQuery();
		filmQuery.setRegionCode(getRegionCode());

		if (!StringUtils.isEmpty(keywords))
			filmQuery.setLikeName(keywords);

		PageUtils.convertPage(filmQuery, page);
		// query.setAssetType(13);
		filmQuery.setFolderCode("mov_hot_69_5_1");
		filmQuery.setStatus(FilmStatus.ONLINE.getCode());

		Result<List<GalaxyFilmDO>> queryResult = galaxyFilmAO.query(filmQuery);
		if (queryResult.isSuccess()) {
			page.setTotalCount(filmQuery.getTotalItem());
			map.put("datas", queryResult.getValue());
		}
		map.put("page", page);
		map.put("keywords", keywords);
		return TRAILER;
	}

	/**
	 * 批量上下线
	 */
	@RequiresPermissions("Cinema_films:edit")
	@RequestMapping(value = "/filmSection/{secId}/updateStatus")
	public @ResponseBody
	String filmSectionUpdateStatus(@PathVariable Integer secId, String filmIds, Integer status) {
		List<Long> ids = NumUtil.toLongs(StringUtils.split(filmIds, ","));
		int intStatus = 0;
		if (status != null && status.intValue() == 1) {
			intStatus = 1;
		}

		String msg = "";
		List<Long> onLineIds = Lists.newArrayList();
		if (intStatus == 1) {
			Result<List<GalaxyFilmDO>> filmResult = galaxyFilmAO.getByIds(ids);
			if (!filmResult.isSuccess())
				return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "查询影片信息失败!", "").toString();
			List<GalaxyFilmDO> list = filmResult.getValue();
			for (GalaxyFilmDO item : list) {
				if (checkIfOnline(secId, item) == null) {
					onLineIds.add(item.getId());
				} else {
					msg += "," + item.getFilmName();
				}
			}
		} else {
			onLineIds = ids;
		}

		if (CollectionUtils.isEmpty(onLineIds))
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "操作失败！所选影片无法上线", "").toString();

		Result<Integer> result = galaxyFilmSectionAO.updateStatusByIds(onLineIds, intStatus);
		if (!result.isSuccess()) {
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "操作失败！更新状态异常", "").toString();
		}

		if (StringUtils.isEmpty(msg)) {
			msg = "操作成功！";
		} else {
			msg = "部分操作成功！,失败为：" + msg.substring(1);
		}
		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg, "");
		return ajaxObject.toString();
	}

	/**
	 * 上线前校验
	 */
	private AjaxObject checkIfOnline(Integer secId, GalaxyFilmDO galaxyFilmDO) {
		AjaxObject ajaxObject = null;
		Integer aliasPrice = galaxyFilmDO.getAliasPrice();

		if (FilmRecStatus.isToday(secId)) {
			// 今日推荐
			if (aliasPrice == null || aliasPrice <= 0) {
				ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "价格错误无法上线！");
			} else if (StringUtils.isEmpty(galaxyFilmDO.getHaibaoUrl())
					|| StringUtils.isEmpty(galaxyFilmDO.getBeijingUrl())
					|| StringUtils.isEmpty(galaxyFilmDO.getCaotuUrl())
					|| StringUtils.isEmpty(galaxyFilmDO.getBiaotiUrl())) {
				ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "有图片为空无法上线！");
			} else if (StringUtils.isEmpty(galaxyFilmDO.getLinkFilmIds())) {
				ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "关联片花不存在！");
			}
		} else if (FilmRecStatus.isHot(secId)) {
			// 院线热映
			if (aliasPrice == null || aliasPrice <= 0) {
				ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "价格错误无法上线！");
			} else if (StringUtils.isEmpty(galaxyFilmDO.getHaibaoUrl())
					|| StringUtils.isEmpty(galaxyFilmDO.getBeijingUrl())) {
				ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "有图片为空无法上线！");
			}
		} else if (FilmRecStatus.isTicket(secId)) {
			// 在线购票
			if (StringUtils.isEmpty(galaxyFilmDO.getTaobaoCode()) || StringUtils.isEmpty(galaxyFilmDO.getWeixinCode())) {
				ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "淘宝微信购票码不能为空！");
			} else if (StringUtils.isEmpty(galaxyFilmDO.getJuzhaoUrl())) {
				ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "剧照图为空无法上线！");
			}
		}
		return ajaxObject;
	}

	/**
	 * 拷贝
	 */
	@RequiresPermissions("Cinema_films:edit")
	@RequestMapping(value = "/filmSection/copy/{secId}/{regionCode}", method = { RequestMethod.POST })
	public @ResponseBody
	String copy(@PathVariable Integer secId, @PathVariable String regionCode) {
		String destRegionCode = getRegionCode();
		if (StringUtils.isEmpty(destRegionCode) || secId == null) {
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "当前区域失效，请刷新页面！").toString();
		}

		// 判断当前区域是否存在关联数据
		GalaxyFilmSectionQuery filmSectionQuery = new GalaxyFilmSectionQuery();
		filmSectionQuery.setSecId(secId);
		filmSectionQuery.setRegionCode(destRegionCode);
		Result<Integer> queryResult = galaxyFilmSectionAO.queryCount(filmSectionQuery);
		if (queryResult.isSuccess() && NumUtil.isGreaterZero(queryResult.getValue())) {
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "请先删除数据再拷贝！").toString();
		}

		// 判断要拷贝的数据是否存在
		filmSectionQuery.setRegionCode(regionCode);
		filmSectionQuery.setPageSize(500);
		Result<List<GalaxyFilmSectionDO>> filmSectionResult = galaxyFilmSectionAO.query(filmSectionQuery);
		if (!filmSectionResult.isSuccess() || CollectionUtils.isEmpty(filmSectionResult.getValue())) {
			return new AjaxObject(AjaxObject.STATUS_CODE_FAILURE, "拷贝数据不存在，无法同步").toString();
		}

		Map<Long, GalaxyFilmDO> filmMap = queryAllFilms(regionCode);
		Map<String, GalaxyFilmDO> linkFilmMap = queryAllFilmsOfAssertId(destRegionCode);
		
		// 拷贝片花数据
		if (FilmRecStatus.isToday(secId) || FilmRecStatus.isHot(secId)) {
			for (GalaxyFilmSectionDO item : filmSectionResult.getValue()) {
				GalaxyFilmDO film = filmMap.get(item.getFilmId());
				if (film == null)
					continue;
				GalaxyFilmDO linkFilm = linkFilmMap.get(film.getFolderCode() + "-" +film.getAssetId());
				if (linkFilm == null)
					continue;
				GalaxyFilmDO dbFilm = new GalaxyFilmDO();
				dbFilm.setId(linkFilm.getId());
				dbFilm.setLinkFilmIds(convertLinkFilmIds(film.getLinkFilmIds(), filmMap, linkFilmMap));
				galaxyFilmAO.update(dbFilm);
			}
		}

		// 拷贝关联数据
		long errNum = 0;
		long totalNum = filmSectionResult.getValue().size();
		for (GalaxyFilmSectionDO item : filmSectionResult.getValue()) {
			GalaxyFilmDO linkFilm = getLinkFilm(item.getFilmId(), filmMap, linkFilmMap);
			if (linkFilm == null){
				errNum++;
				continue;
			}
			item.setId(null);
			item.setRegionCode(destRegionCode);
			item.setFilmId(linkFilm.getId());
			galaxyFilmSectionAO.save(item);
		}
		
		String msg;
		if (errNum == 0) {
			msg = "拷贝成功！";
		} else if (totalNum > errNum) {
			msg = "部分拷贝成功！总数：" + totalNum + ",失败数：" + errNum;
		} else {
			msg = "拷贝失败！";
		}

		AjaxObject ajaxObject = new AjaxObject(AjaxObject.STATUS_CODE_SUCCESS, msg, "");
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

	private Long getLinkFilmId(Long filmId, List<GalaxyFilmDO> films, List<GalaxyFilmDO> linkFilms) {
		String assertId = null;
		for (GalaxyFilmDO item : films) {
			if (NumUtil.isEq(filmId, item.getId())) {
				assertId = item.getAssetId();
				break;
			}
		}
		if (StringUtils.isEmpty(assertId))
			return null;
		for (GalaxyFilmDO item : linkFilms) {
			if (StringUtils.equalsIgnoreCase(assertId, item.getAssetId())) {
				return item.getId();
			}
		}
		return null;
	}

	private Map<Long, GalaxyFilmDO> queryAllFilms(String regionCode) {
		Map<Long, GalaxyFilmDO> subMap = Maps.newHashMap();

		try {
			GalaxyFilmQuery query = new GalaxyFilmQuery();
			query.setStatus(FilmStatus.ONLINE.getCode());
			query.setPageSize(1000);
			query.setQueryCount(false);
			query.setOrderBy(null);
			query.setRegionCode(regionCode);
			Result<List<GalaxyFilmDO>> filmResult = galaxyFilmAO.query(query);
			if (filmResult.isSuccess() && CollectionUtils.isNotEmpty(filmResult.getValue())) {
				subMap = Maps.uniqueIndex(filmResult.getValue(), new Function<GalaxyFilmDO, Long>() {
					@Override
					public Long apply(GalaxyFilmDO input) {
						return input.getId();
					}
				});
			}
		} catch (Exception e) {
			log.error("queryAllFilms error", e);
		}
		return subMap;
	}

	private Map<String, GalaxyFilmDO> queryAllFilmsOfAssertId(String regionCode) {
		Map<String, GalaxyFilmDO> subMap = Maps.newHashMap();

		try {
			GalaxyFilmQuery query = new GalaxyFilmQuery();
			query.setStatus(FilmStatus.ONLINE.getCode());
			query.setPageSize(1000);
			query.setQueryCount(false);
			query.setOrderBy(null);
			query.setRegionCode(regionCode);
			Result<List<GalaxyFilmDO>> filmResult = galaxyFilmAO.query(query);
			if (filmResult.isSuccess() && CollectionUtils.isNotEmpty(filmResult.getValue())) {
				subMap = Maps.uniqueIndex(filmResult.getValue(), new Function<GalaxyFilmDO, String>() {
					@Override
					public String apply(GalaxyFilmDO input) {
						return input.getFolderCode() + "-" + input.getAssetId();
					}
				});
			}
		} catch (Exception e) {
			log.error("queryAllFilms error", e);
		}
		return subMap;
	}

	private GalaxyFilmDO getLinkFilm(Long filmId, Map<Long, GalaxyFilmDO> filmMap, Map<String, GalaxyFilmDO> linkFilmMap) {
		GalaxyFilmDO film = filmMap.get(filmId);
		if (film == null)
			return null;
		return linkFilmMap.get(film.getFolderCode() + "-" + film.getAssetId());
	}

	private String convertLinkFilmIds(String linkFilmIds, Map<Long, GalaxyFilmDO> filmMap,
			Map<String, GalaxyFilmDO> linkFilmMap) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isEmpty(linkFilmIds))
			return sb.toString();
		List<Long> ids = NumUtil.toLongs(StringUtils.split(linkFilmIds, ','));

		for (Long id : ids) {
			GalaxyFilmDO linkFilm = getLinkFilm(id, filmMap, linkFilmMap);
			if (linkFilm == null)
				continue;
			sb.append(linkFilm.getId()).append(",");
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	String getTodayFilmRtspUrl(GalaxyFilmDO film, Map<Long, GalaxyFilmDO> onlineFilms) {
		if (StringUtils.isEmpty(film.getLinkFilmIds()))
			return null;
		long id = NumUtil.toLongs(StringUtils.split(film.getLinkFilmIds(), ',')).get(0);
		GalaxyFilmDO filmItem = onlineFilms.get(id);
		if (filmItem != null) {
			return filmItem.getRtspUrl();
		}
		return null;
	}
}

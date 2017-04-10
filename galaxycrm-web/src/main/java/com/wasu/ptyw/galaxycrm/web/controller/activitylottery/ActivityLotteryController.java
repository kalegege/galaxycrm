package com.wasu.ptyw.galaxycrm.web.controller.activitylottery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.Region;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.PageUtils;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.Page;

import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryListAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyDiscountDO;
import com.wasu.ptyw.galaxy.dal.query.GalaxyDiscountQuery;

/**
 * 
 * @author quxy
 * @date 2015年9月6日
 * @version V1.0
 * @TODO ftp账号管理
 */
@Controller
@RequestMapping("/activity/lottery")
public class ActivityLotteryController {

	@Autowired
	private ActivityLotteryAO activityLotteryAO;

	@Autowired
	private ActivityIntroductionAO activityIntroductionAO;

	@Autowired
	private ActivityLotteryListAO activityLotteryListAO;

	private static final String LIST = "activitylottery/activitylottery/list";
	private static final String LISTPRIZEANDREGION = "activitylottery/activitylottery/prizeandregion";

	@RequestMapping(value = "/exportActivityLottery2", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void exprotExcel2(Page page, String prize, String keywords, Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActivityLotteryQuery query = new ActivityLotteryQuery();
		if (!("undefined".equalsIgnoreCase(prize))
				&& (StringUtils.isNotEmpty(prize) && Integer.parseInt(prize) == 99)) {
			if (StringUtils.isNotEmpty(keywords) && !("undefined".equalsIgnoreCase(keywords))) {
				query.setCode(keywords);
			}

			Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.queryByRegionToExcel(query);
			List<ActivityLotteryDO> listdo = queryResult.getValue();
			for (ActivityLotteryDO listdos : listdo) {
				System.out.println(listdos.getPrizeCode());
			}
			// 调用封装类执行导出
			// 导出文件存放的路径，并且是虚拟目录指向的路径
			// String filePath = "d:/upload/linshi/";
			// String filePath = "//tmp//excelupload//";
			String filePath = MyUtil.windowpath;
			File file = new File(filePath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			} else {
				System.out.println("已经存在");
			}
			// 改为从系统参数配置表获取参数值
			// 导出文件的前缀
			String filePrefix = "schd";
			// -1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
			int flushRows = 100;
			// 定义导出数据的title
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("区域编号");
			fieldNames.add("奖品名称");
			fieldNames.add("中奖数量");
			fieldNames.add("奖品等级");

			// 告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
			List<String> fieldCodes = new ArrayList<String>();
			fieldCodes.add("region");
			fieldCodes.add("prizeTxt");
			fieldCodes.add("count");
			fieldCodes.add("prize");

			// 注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
			// 开始导出，执行一些workbook及sheet等对象的初始创建
			ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames,
					fieldCodes, flushRows);
			// 导出的数据通过service取出
			List<ActivityLotteryDO> list = queryResult.getValue();
			// 执行导出
			excelExportSXXSSF.writeDatasByObject(list);
			// 输出文件，返回下载文件的http地址，已经包括虚拟目录
			excelExportSXXSSF.exportFile(request, response);

		} else {

			if (StringUtils.isNotEmpty(keywords) && !("undefined".equalsIgnoreCase(keywords))) {
				query.setCode(keywords);
			}
			Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.queryByRegionCountAllToExcel(query);

			// 调用封装类执行导出
			// 导出文件存放的路径，并且是虚拟目录指向的路径
			// String filePath = "d:/upload/linshi/";
			// String filePath = "//tmp//excelupload//";
			String filePath = MyUtil.windowpath;
			File file = new File(filePath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			} else {
				System.out.println("已经存在");
			}
			// 改为从系统参数配置表获取参数值
			// 导出文件的前缀
			String filePrefix = "schd";
			// -1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
			int flushRows = 100;
			// 定义导出数据的title
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("区域编号");
			fieldNames.add("中奖数量");
			// 告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
			List<String> fieldCodes = new ArrayList<String>();
			fieldCodes.add("region");
			fieldCodes.add("count");

			// 注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
			// 开始导出，执行一些workbook及sheet等对象的初始创建
			ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames,
					fieldCodes, flushRows);
			// 导出的数据通过service取出
			List<ActivityLotteryDO> list = queryResult.getValue();
			// 执行导出
			excelExportSXXSSF.writeDatasByObject(list);
			// 输出文件，返回下载文件的http地址，已经包括虚拟目录
			excelExportSXXSSF.exportFile(request, response);
		}

	}

	@RequestMapping(value = "/exportActivityLotterySubmit", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void exprotExcel(Page page, String prize, String keywords, Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("0".equalsIgnoreCase(keywords));
		System.out.println(!"0".equalsIgnoreCase(keywords));
		ActivityLotteryQuery query = new ActivityLotteryQuery();
		if (StringUtils.isNotEmpty(keywords) && !"0".equalsIgnoreCase(keywords)) {
			query.setCode(keywords);
		}
		PageUtils.convertPage(query, page);
		// Result<List<ActivityLotteryDO>> queryResult =
		// activityLotteryAO.query(query);
		Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.queryByNoPage(query);
		List<ActivityLotteryDO> listdo = queryResult.getValue();
		for (ActivityLotteryDO listdos : listdo) {
			System.out.println(listdos.getPrizeCode());
		}
		// 调用封装类执行导出

		// 导出文件存放的路径，并且是虚拟目录指向的路径
		// String filePath = "d:/upload/linshi/";
		// String filePath = "//tmp//excelupload//";
		String filePath = MyUtil.windowpath;
		File file = new File(filePath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		} else {
			System.out.println("已经存在");
		}
		// 改为从系统参数配置表获取参数值
		// String filePath =
		// systemConfigService.findBasicinfoById("00301").getValue();
		// 导出文件的前缀
		String filePrefix = "schd";
		// -1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
		int flushRows = 100;

		// 定义导出数据的title
		List<String> fieldNames = new ArrayList<String>();
		fieldNames.add("id");
		fieldNames.add("活动名称");
		fieldNames.add("机顶盒号");
		fieldNames.add("区域编号");
		fieldNames.add("奖品等级");
		fieldNames.add("奖品名称");
		fieldNames.add("手机");
		fieldNames.add("状态");
		fieldNames.add("创建时间");

		// 告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
		List<String> fieldCodes = new ArrayList<String>();
		fieldCodes.add("id");//
		fieldCodes.add("code");//
		fieldCodes.add("stbId");
		fieldCodes.add("region");
		fieldCodes.add("prize");
		fieldCodes.add("prizeTxt");
		fieldCodes.add("mobile");
		fieldCodes.add("status");
		fieldCodes.add("gmtCreate");
		// 注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
		// 开始导出，执行一些workbook及sheet等对象的初始创建
		ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames,
				fieldCodes, flushRows);
		// 导出的数据通过service取出
		// List<YpxxCustom> list = ypxxService.findYpxxList(ypxxQueryVo);
		List<ActivityLotteryDO> list = queryResult.getValue();
		// 执行导出
		excelExportSXXSSF.writeDatasByObject(list);
		// 输出文件，返回下载文件的http地址，已经包括虚拟目录
		excelExportSXXSSF.exportFile(request, response);

	}

	/**
	 * @return
	 */
	@RequiresPermissions("activitylottery:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String accountSelectList(Page page, String region, String prize, String keywords, String keywords2,
			Map<String, Object> map, HttpServletRequest request) {
		// 统计查询
		ActivityLotteryQuery query = new ActivityLotteryQuery();
		if ((StringUtils.isNotEmpty(prize) && Integer.parseInt(prize) == 99) || StringUtils.isNotEmpty(region)) {
			if (StringUtils.isNotEmpty(region)) {
				query.setRegion(region);
			}
			PageUtils.convertPage(query, page);
			Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.queryByRegion(query);
			List<ActivityLotteryDO> listdo = queryResult.getValue();
			for (ActivityLotteryDO listdos : listdo) {
				System.out.println(listdos.getPrizeCode());
			}
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				map.put("datas", queryResult.getValue());
			}
			map.put("page", page);
			map.put("keywords", keywords);
			map.put("keywords2", keywords2);
			map.put("region", region);
			map.put("prize", prize);
			return LISTPRIZEANDREGION;

		} else if (StringUtils.isNotEmpty(prize) && Integer.parseInt(prize) == 100) {

			PageUtils.convertPage(query, page);
			if (StringUtils.isNotEmpty(region)) {
				query.setRegion(region);
			}
			Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.queryByRegionCountAll(query);
			List<ActivityLotteryDO> listdo = queryResult.getValue();
			for (ActivityLotteryDO listdos : listdo) {
				System.out.println(listdos.getPrizeCode());
			}
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				map.put("datas", queryResult.getValue());
			}
			map.put("page", page);
			map.put("keywords", keywords);
			map.put("keywords2", keywords2);
			map.put("region", region);
			map.put("prize", prize);
			return LISTPRIZEANDREGION;
		} else {
			if (StringUtils.isNotEmpty(keywords)) {
				query.setCode(keywords);
				ActivityIntroductionQuery queryIntroductionQuery = new ActivityIntroductionQuery();
				queryIntroductionQuery.setCode(keywords);
				Result<List<ActivityIntroductionDO>> queryResult2 = activityIntroductionAO
						.query(queryIntroductionQuery);
				List<ActivityIntroductionDO> ActivityIntroductionDO = queryResult2.getValue();
				for (ActivityIntroductionDO do2 : ActivityIntroductionDO) {
					System.out.println(do2.getName());
					map.put("huodongmingcheng", do2.getName());
				}

			}
			if (StringUtils.isNotEmpty(keywords2)) {
				query.setStbId(keywords2);
			}
			PageUtils.convertPage(query, page);
			Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.query(query);
			List<ActivityLotteryDO> listdo = queryResult.getValue();
			for (ActivityLotteryDO listdos : listdo) {
				System.out.println(listdos.getPrizeCode());
			}
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				map.put("datas", queryResult.getValue());
			}
			ActivityIntroductionQuery ActivityIntroductionQuery = new ActivityIntroductionQuery();
			Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult = activityIntroductionAO
					.queryAll(ActivityIntroductionQuery);
			map.put("status", keywords);
			map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());

			map.put("page", page);
			map.put("keywords", keywords);
			map.put("keywords2", keywords2);
			map.put("prize", 0);
			return LIST;
		}

	}

	@RequiresPermissions("activitylottery:view")
	@RequestMapping(value = "/clear", method = { RequestMethod.GET, RequestMethod.POST })
	public void clearMobile() {

		// 找出最近4次的活动
		ActivityIntroductionQuery query = new ActivityIntroductionQuery();
		query.setOrderBy("gmt_create desc limit 0,4");
		Result<List<ActivityIntroductionDO>> queryResult = activityIntroductionAO.queryMobile(query);
		if (queryResult.isSuccess()) {
			// 最新的四个活动
			List<ActivityIntroductionDO> listdo = queryResult.getValue();
			for (ActivityIntroductionDO activityIntroductionDO : listdo) {
				String code = activityIntroductionDO.getCode();
				// 查询该活动的中奖纪录中手机号为空的记录
				ActivityLotteryQuery lquery = new ActivityLotteryQuery();
				lquery.setCode(code);
				Result<List<ActivityLotteryDO>> result = activityLotteryAO.queryByMobile(lquery);
				if (result.isSuccess()) {
					List<ActivityLotteryDO> list = result.getValue();
					if (list.size() > 0) {
						List<String> prizeCodes = new ArrayList<String>();
						int i = 0;
						for (ActivityLotteryDO activityLotteryDO : list) {
							String prizecode = activityLotteryDO.getPrizeCode();
							prizeCodes.add(i, prizecode);
							i++;
							activityLotteryDO.setMobile("*");
							activityLotteryAO.update(activityLotteryDO);
						}
						Result res = activityLotteryListAO.updateStatusByPrizeCodes(prizeCodes, 0);
					}else{
						continue;
					}
				}

			}

		}

	}

}

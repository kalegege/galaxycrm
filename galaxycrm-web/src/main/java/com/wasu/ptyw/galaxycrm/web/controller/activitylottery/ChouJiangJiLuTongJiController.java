package com.wasu.ptyw.galaxycrm.web.controller.activitylottery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAccessAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPrizeDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ChoujiangJiluVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPrizeQuery;
import com.wasu.ptyw.galaxy.common.dataobject.Result;

import shiro.PageUtils;
import shiro.dataobject.Page;

/**
 * 
 * @author quxy
 * @date 2015年9月6日
 * @version V1.0
 * @TODO ftp账号管理
 */
@Controller
@RequestMapping("/activity/choujiangjilutongji")
public class ChouJiangJiLuTongJiController {

	@Autowired
	private ActivityLotteryAccessAO activityLotteryAccessAO;
	@Autowired
	private ActivityIntroductionAO activityIntroductionAO;

	private static final String LIST = "activitylottery/activitylotteryaccess/list";
	private static final String LOTTERYLIST = "activitylottery/choujiangjilutongji/lotterylist";

	
	
	@RequestMapping(value = "/exportActivityLotterySubmit", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void exprotExcel(Page page, String mark, String prize, String keywords, Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.setProperty("java.awt.headless", "true");
		ActivityLotteryAccessQuery query = new ActivityLotteryAccessQuery();
		
		if (StringUtils.isNotEmpty(mark) && Integer.parseInt(mark) == 102) {
			if (StringUtils.isNotEmpty(keywords)) {
				query.setCode(keywords);
			}
			PageUtils.convertPage(query, page);
			// Result<List<ActivityLotteryDO>> queryResult =
			// activityLotteryAO.query(query);
			// ActivityAccessDO activityAccessAO
			Result<List<ActivityLotteryAccessDO>> queryResult = activityLotteryAccessAO.queryStbVisitToExcel(query);

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
			fieldNames.add("活动编号");
			fieldNames.add("区域");
			fieldNames.add("抽奖次数");
			fieldNames.add("机顶盒数量");

			// 告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
			List<String> fieldCodes = new ArrayList<String>();
			fieldCodes.add("code");
			fieldCodes.add("region");
			fieldCodes.add("count");
			fieldCodes.add("stb_count");
			// 注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
			// 开始导出，执行一些workbook及sheet等对象的初始创建
			ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames,
					fieldCodes, flushRows);
			// 导出的数据通过service取出
			// List<YpxxCustom> list = ypxxService.findYpxxList(ypxxQueryVo);
			List<ActivityLotteryAccessDO> list = queryResult.getValue();
			// 执行导出
			excelExportSXXSSF.writeDatasByObject(list);
			// 输出文件，返回下载文件的http地址，已经包括虚拟目录
			excelExportSXXSSF.exportFile(request, response);

		} else if (!"1".equalsIgnoreCase(mark) && Integer.parseInt(mark) == 100) {
			System.setProperty("java.awt.headless", "true");
			if (StringUtils.isNotEmpty(keywords) && !"0".equalsIgnoreCase(keywords)) {
				query.setCode(keywords);
			}
			PageUtils.convertPage(query, page);
			// Result<List<ActivityLotteryDO>> queryResult =
			// activityLotteryAO.query(query);
			// ActivityAccessDO activityAccessAO
			Result<List<ActivityLotteryAccessDO>> queryResult = activityLotteryAccessAO.queryStbVisitToExcelPV(query);

			// 调用封装类执行导出
			// 导出文件存放的路径，并且是虚拟目录指向的路径
			// String filePath = "d:/upload/linshi/";
			String filePath = MyUtil.windowpath;
			// 改为从系统参数配置表获取参数值
			// String filePath =
			// systemConfigService.findBasicinfoById("00301").getValue();
			// 导出文件的前缀
			String filePrefix = "schd";
			// -1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
			int flushRows = 100;

			// 定义导出数据的title
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("活动编号");
			fieldNames.add("区域");
			fieldNames.add("抽奖次数");
			fieldNames.add("机顶盒数量");

			// 告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
			List<String> fieldCodes = new ArrayList<String>();
			fieldCodes.add("code");
			fieldCodes.add("region");
			fieldCodes.add("count");
			fieldCodes.add("stb_count");//
			// 注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
			// 开始导出，执行一些workbook及sheet等对象的初始创建
			ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames,
					fieldCodes, flushRows);
			// 导出的数据通过service取出
			// List<YpxxCustom> list = ypxxService.findYpxxList(ypxxQueryVo);
			List<ActivityLotteryAccessDO> list = queryResult.getValue();
			// 执行导出
			excelExportSXXSSF.writeDatasByObject(list);
			// 输出文件，返回下载文件的http地址，已经包括虚拟目录
			excelExportSXXSSF.exportFile(request, response);

		} else {
			System.setProperty("java.awt.headless", "true");
			if (StringUtils.isNotEmpty(keywords) && !"0".equalsIgnoreCase(keywords)) {
				query.setCode(keywords);
			}
			PageUtils.convertPage(query, page);
			// Result<List<ActivityLotteryDO>> queryResult =
			// activityLotteryAO.query(query);
			// ActivityAccessDO activityAccessAO
			Result<List<ActivityLotteryAccessDO>> queryResult = activityLotteryAccessAO.queryStbVisitToExcelUV(query);

			// 调用封装类执行导出
			// 导出文件存放的路径，并且是虚拟目录指向的路径
			// String filePath = "d:/upload/linshi/";
			String filePath = MyUtil.windowpath;
			// 改为从系统参数配置表获取参数值
			// String filePath =
			// systemConfigService.findBasicinfoById("00301").getValue();
			// 导出文件的前缀
			String filePrefix = "schd";
			// -1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
			int flushRows = 100;

			// 定义导出数据的title
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("活动编号");
			fieldNames.add("区域");
			fieldNames.add("抽奖次数");
			fieldNames.add("机顶盒数量");

			// 告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
			List<String> fieldCodes = new ArrayList<String>();
			fieldCodes.add("code");
			fieldCodes.add("region");
			fieldCodes.add("count");
			fieldCodes.add("stb_count");//
			// 注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
			// 开始导出，执行一些workbook及sheet等对象的初始创建
			ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames,
					fieldCodes, flushRows);
			// 导出的数据通过service取出
			// List<YpxxCustom> list = ypxxService.findYpxxList(ypxxQueryVo);
			List<ActivityLotteryAccessDO> list = queryResult.getValue();
			// 执行导出
			excelExportSXXSSF.writeDatasByObject(list);
			// 输出文件，返回下载文件的http地址，已经包括虚拟目录
			excelExportSXXSSF.exportFile(request, response);

		}

	}

	/**
	 * @return
	 */
	@RequiresPermissions("activitylotteryaccess:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String accountSelectList(Page page, String mark, String region, String keywords, Map<String, Object> map,
			HttpServletRequest request) {
		System.setProperty("java.awt.headless", "true");
		ActivityLotteryAccessQuery query = new ActivityLotteryAccessQuery();
		if (StringUtils.isNotEmpty(mark) && Integer.parseInt(mark) == 102) {//抽奖次数
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
			if (StringUtils.isNotEmpty(region)) {
				query.setRegion(region);
			}
			PageUtils.convertPage(query, page);
			Result<List<ChoujiangJiluVO>> queryResult = activityLotteryAccessAO.queryStbVisit2(query);
			List<ChoujiangJiluVO> listdo = queryResult.getValue();
			/*for (ChoujiangJiluVO listdos : listdo) {
				System.out.println(listdos.getCount() + listdos.getStb_count());
			}*/
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				map.put("datas", queryResult.getValue());
			}
			ActivityIntroductionQuery activityIntroductionQuery = new ActivityIntroductionQuery();
			Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult = activityIntroductionAO
					.queryAll(activityIntroductionQuery);
			if (activityIntroductionQueryResult.isSuccess()) {
				map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			}
			map.put("status", keywords);
			map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			map.put("mark", mark);
			map.put("page", page);
			map.put("region", region);
			map.put("keywords", keywords);
			return LOTTERYLIST;

		} else if (StringUtils.isNotEmpty(mark) && Integer.parseInt(mark) == 100) {//UV
			System.setProperty("java.awt.headless", "true");
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
			if (StringUtils.isNotEmpty(region)) {
				query.setRegion(region);
			}
			PageUtils.convertPage(query, page);
			Result<List<ChoujiangJiluVO>> queryResult = activityLotteryAccessAO.queryStbVisitUV2(query);
			List<ChoujiangJiluVO> listdo = queryResult.getValue();

			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				map.put("datas", queryResult.getValue());
			}
			ActivityIntroductionQuery activityIntroductionQuery = new ActivityIntroductionQuery();
			Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult = activityIntroductionAO
					.queryAll(activityIntroductionQuery);
			if (activityIntroductionQueryResult.isSuccess()) {
				map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			}
			map.put("status", keywords);
			map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			map.put("mark", mark);
			map.put("page", page);
			map.put("region", region);
			map.put("keywords", keywords);
			return LOTTERYLIST;

		} else {
			System.setProperty("java.awt.headless", "true");
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
			if (StringUtils.isNotEmpty(region)) {
				query.setRegion(region);
			}
			PageUtils.convertPage(query, page);
			Result<List<ChoujiangJiluVO>> queryResult = activityLotteryAccessAO.queryStbVisitPV2(query);
			List<ChoujiangJiluVO> listdo = queryResult.getValue();
			

			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				map.put("datas", queryResult.getValue());
			}
			ActivityIntroductionQuery activityIntroductionQuery = new ActivityIntroductionQuery();
			Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult = activityIntroductionAO
					.queryAll(activityIntroductionQuery);
			if (activityIntroductionQueryResult.isSuccess()) {
				map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			}
			map.put("status", keywords);
			map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			map.put("mark", mark);
			map.put("page", page);
			map.put("region", region);
			map.put("keywords", keywords);
			return LOTTERYLIST;
		}

	}

}

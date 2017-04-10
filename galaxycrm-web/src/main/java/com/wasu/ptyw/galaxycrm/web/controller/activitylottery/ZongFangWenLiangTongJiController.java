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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.PageUtils;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.Page;
import antlr.MakeGrammar;

import com.wasu.ptyw.activitylottery.core.ao.ActivityAccessAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityAccessTotalCountAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountVO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessVO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyDiscountDO;
import com.wasu.ptyw.galaxy.dal.query.GalaxyDiscountQuery;
import com.wasu.ptyw.galaxycrm.task.StatisticsTask;


/**
 * 
 * @author quxy
 * @date 2015年9月6日
 * @version V1.0
 * @TODO ftp账号管理
 */
@Controller
@RequestMapping("/activity/fangwenliangtongji")
public class ZongFangWenLiangTongJiController {
    
    
    @Autowired
    private ActivityAccessAO activityAccessAO;

    @Autowired
	private ActivityAccessTotalCountAO activityAccessTotalCountAO;

    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
    
	private static final String LIST = "activitylottery/activityaccess/list";
	private static final String REGIONLIST = "activitylottery/zongfangwenliangtongji/regionlist";
	
	@RequestMapping(value = "/exportActivityLottery2", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void exprotExcel2(Page page, String prize,String keywords,String mark, Map<String, Object> map, HttpServletRequest request,HttpServletResponse response) throws Exception{
		 System.setProperty("java.awt.headless", "true");
		ActivityAccessQuery query = new ActivityAccessQuery();
		if( !"0".equalsIgnoreCase(mark) && (StringUtils.isNotEmpty(mark) && Integer.parseInt(mark)==100)){
			 if (StringUtils.isNotEmpty(keywords) && !"1".equalsIgnoreCase(keywords)) {
				   query.setCode(keywords);
			  }
			
			Result<List<ActivityAccessDO>> queryResult = activityAccessAO.queryByRegionAllToExcel(query);
			List<ActivityAccessVO> list=new ArrayList<ActivityAccessVO>();
			for(ActivityAccessDO d:queryResult.getValue()){
				list.add(new ActivityAccessVO(d));
			}
			String[] titleHeander = {"id","区域编号","访问量","活动编号","活动名称"};
			
			ExcelUtils2.export(MyUtil.getCurrentTimeStr(), "szhd", titleHeander,list, request, response);
		 }else {
			 System.setProperty("java.awt.headless", "true");
			 if (StringUtils.isNotEmpty(keywords) && !"1".equalsIgnoreCase(keywords)) {
				   query.setCode(keywords);
			  }
			    Result<List<ActivityAccessDO>> queryResult = activityAccessAO.queryByRegionAndStbIDToExcel(query);
			    List<ActivityAccessVO> list=new ArrayList<ActivityAccessVO>();
				for(ActivityAccessDO d:queryResult.getValue()){
					list.add(new ActivityAccessVO(d));
				}
				String[] titleHeander = {"id","区域编号","访问量","活动编号","活动名称"};
				
				ExcelUtils2.export(MyUtil.getCurrentTimeStr(), "szhd", titleHeander,list, request, response);
		   }
		
		
	 }

	
	
	@RequestMapping(value = "/exportActivityLotterySubmit", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void exprotExcel(Page page, String prize,String keywords, Map<String, Object> map, HttpServletRequest request,HttpServletResponse response) throws Exception{
		 System.setProperty("java.awt.headless", "true");
		ActivityAccessQuery query = new ActivityAccessQuery();
		   if (StringUtils.isNotEmpty(keywords) && !("undefined".equalsIgnoreCase(keywords))) {
			   query.setCode(keywords);
		    }
		      PageUtils.convertPage(query, page);
		  //  Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.query(query);
		      //ActivityAccessDO  activityAccessAO
		     Result<List<ActivityAccessDO>> queryResult = activityAccessAO.queryByNoPage(query);
		     List<ActivityAccessDO> listdo=queryResult.getValue();
			    for (ActivityAccessDO listdos:listdo) {
					System.out.println(listdos.getCode());
				}
		// 调用封装类执行导出
				// 导出文件存放的路径，并且是虚拟目录指向的路径
			 //	String filePath = "d:/upload/linshi/";
			  //  String filePath = "//tmp//excelupload//";
			    String filePath= MyUtil.windowpath;
			    File file =new File(filePath);  
			    if  (!file.exists()  && !file.isDirectory())      
			    {       
			    	file.mkdir();    
			    }else {
					System.out.println("已经存在");
				}
				//改为从系统参数配置表获取参数值 
				//String filePath = systemConfigService.findBasicinfoById("00301").getValue();
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
				fieldNames.add("平台");
				fieldNames.add("版本");
				fieldNames.add("referer");
				fieldNames.add("ip");
				fieldNames.add("userAgent");
				fieldNames.add("cookie");
				fieldNames.add("status");
				fieldNames.add("count");

				// 告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
				List<String> fieldCodes = new ArrayList<String>();
				fieldCodes.add("id");// 
				fieldCodes.add("code");//
				fieldCodes.add("stbId");
				fieldCodes.add("region");
				fieldCodes.add("platform");
				fieldCodes.add("version");
				fieldCodes.add("referer");
				fieldCodes.add("ip");
				fieldCodes.add("userAgent");
				fieldCodes.add("cookie");
				fieldCodes.add("status");
				fieldCodes.add("count");
				// 注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
				// 开始导出，执行一些workbook及sheet等对象的初始创建
				ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath,
						"/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
				// 导出的数据通过service取出
				//List<YpxxCustom> list = ypxxService.findYpxxList(ypxxQueryVo);
				   List<ActivityAccessDO> list=queryResult.getValue();
				// 执行导出
				excelExportSXXSSF.writeDatasByObject(list);
				// 输出文件，返回下载文件的http地址，已经包括虚拟目录
				excelExportSXXSSF.exportFile(request,response);
	 }


	 /**
	  * @return
	  */
	@RequiresPermissions("fangwenliangtongji:view")
	@RequestMapping(value = "/list1", method = { RequestMethod.GET, RequestMethod.POST})
	public String accountSelectList1(Page page, String mark,String region,String keywords, Map<String, Object> map, HttpServletRequest request){
		ActivityAccessQuery query = new ActivityAccessQuery();
		if(StringUtils.isNotEmpty(mark) &&Integer.parseInt(mark)==100){
			   if (StringUtils.isNotEmpty(keywords)) {
				  query.setCode(keywords);
				   ActivityIntroductionQuery queryIntroductionQuery = new ActivityIntroductionQuery();
				   queryIntroductionQuery.setCode(keywords);
			     }
			    PageUtils.convertPage(query, page);
			    Result<List<ActivityAccessDO>> queryResult = activityAccessAO.queryByRegionAll(query);
			    //Result<List<ActivityAccessDO>> queryResult = activityAccessAO.query(query);
			    List<ActivityAccessDO> listdo=queryResult.getValue();
			    for (ActivityAccessDO listdos:listdo) {
					System.out.println(listdos.getCode());
				}
			    if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				map.put("datas", queryResult.getValue());
			      }
			    map.put("page", page);
			    map.put("keywords", keywords);
			    map.put("status", keywords);
			    map.put("mark", mark);
			    ActivityIntroductionQuery activityIntroductionQuery = new ActivityIntroductionQuery();
			    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(activityIntroductionQuery);
			    if(activityIntroductionQueryResult.isSuccess()){
			    	 map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			     }
			    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			    return REGIONLIST;
		}else {
			if (StringUtils.isNotEmpty(keywords)) {
				 query.setCode(keywords);
				 ActivityIntroductionQuery queryIntroductionQuery = new ActivityIntroductionQuery();
				   queryIntroductionQuery.setCode(keywords);
				   Result<List<ActivityIntroductionDO>> queryResult2 =activityIntroductionAO.query(queryIntroductionQuery);
				  List<ActivityIntroductionDO> ActivityIntroductionDO= queryResult2.getValue();
				   for (ActivityIntroductionDO do2:ActivityIntroductionDO) {
					  System.out.println(do2.getName());
					  map.put("huodongmingcheng", do2.getName());
				      }
				   
			     }
			    if (StringUtils.isNotEmpty(region)) {
					query.setRegion(region);
				 }
			    PageUtils.convertPage(query, page);
			    Result<List<ActivityAccessDO>> queryResult = activityAccessAO.queryByRegionAndStbID(query);
			    //Result<List<ActivityAccessDO>> queryResult = activityAccessAO.query(query);
			    List<ActivityAccessDO> listdo=queryResult.getValue();
			    for (ActivityAccessDO listdos:listdo) {
					System.out.println(listdos.getCode());
				}
			    if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				map.put("datas", queryResult.getValue());
			      }
			    map.put("page", page);
			    map.put("keywords", keywords);
			    map.put("status", keywords);
			    map.put("mark", mark);
			    ActivityIntroductionQuery activityIntroductionQuery = new ActivityIntroductionQuery();
			    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(activityIntroductionQuery);
			    if(activityIntroductionQueryResult.isSuccess()){
			    	 map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			     }
			    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
			    return REGIONLIST;
		}
	}
	
	 /**
	  * @return
	  */
	@RequiresPermissions("fangwenliangtongji:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST})
	public String accountSelectList(Page page, String mark,String keywords, Map<String, Object> map, HttpServletRequest request){
		//初始数据保存
	    map.put("keywords", keywords);
	    map.put("status", keywords);
	    map.put("mark", mark);
	    //获取活动
	    ActivityIntroductionQuery activityIntroductionQuery = new ActivityIntroductionQuery();
	    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(activityIntroductionQuery);
	    if(activityIntroductionQueryResult.isSuccess()){
	    	 map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
	     }
	    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
	    
	    //判断统计数据类型
	    ActivityAccessTotalCountQuery query = new ActivityAccessTotalCountQuery();
		//分页
		PageUtils.convertPage(query, page);
		//keywords活动code
		if (StringUtils.isNotEmpty(keywords)) {
			query.setCode(keywords);
		 }
		
		if(StringUtils.isNotEmpty(mark) &&Integer.parseInt(mark)==100){
			//总uv
			query.setType(1);
			Result<List<ActivityAccessTotalCountVO>> queryResult = activityAccessTotalCountAO.statistics(query);
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				for(ActivityAccessTotalCountVO tc:queryResult.getValue()){
					tc.setCount(tc.getUv());
				}
				map.put("datas", queryResult.getValue());
			}
			    
		}else if(StringUtils.isNotEmpty(mark) &&Integer.parseInt(mark)==102){
			//每日uv
			query.setType(2);
			Result<List<ActivityAccessTotalCountVO>> queryResult = activityAccessTotalCountAO.statistics(query);
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				for(ActivityAccessTotalCountVO tc:queryResult.getValue()){
					tc.setCount(tc.getUv());
				}
				map.put("datas", queryResult.getValue());
			}
		}else if(StringUtils.isNotEmpty(mark) &&Integer.parseInt(mark)==103){
			//每日pv
			query.setType(2);
			Result<List<ActivityAccessTotalCountVO>> queryResult = activityAccessTotalCountAO.statistics(query);
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				for(ActivityAccessTotalCountVO tc:queryResult.getValue()){
					tc.setCount(tc.getPv());
				}
				map.put("datas", queryResult.getValue());
			}
		}else{
			//如果有选择活动则改为实时统计
			if(StringUtils.isNotEmpty(keywords)){
				ActivityAccessQuery acquery = new ActivityAccessQuery();
				acquery.setCode(keywords);
				PageUtils.convertPage(acquery, page);
				Result<List<ActivityAccessDO>> queryResult = activityAccessAO.queryByRegionAndStbID(acquery);
			    List<ActivityAccessDO> listdo=queryResult.getValue();
			    for (ActivityAccessDO listdos:listdo) {
					System.out.println(listdos.getCode());
				}
			    if (queryResult.isSuccess()) {
				    List<ActivityAccessTotalCountVO> tclist=new ArrayList<ActivityAccessTotalCountVO>();
				    for(ActivityAccessDO ac:queryResult.getValue()){
				    	tclist.add(new ActivityAccessTotalCountVO(ac));
				    }
					page.setTotalCount(query.getTotalItem());
					map.put("datas", tclist);
			      }
			}else{
				query.setType(1);
				//无参数默认显示总pv
				Result<List<ActivityAccessTotalCountVO>> queryResult = activityAccessTotalCountAO.statistics(query);
				if (queryResult.isSuccess()) {
					page.setTotalCount(query.getTotalItem());
					for(ActivityAccessTotalCountVO tc:queryResult.getValue()){
						tc.setCount(tc.getPv());
					}
					map.put("datas", queryResult.getValue());
				}
			}
		}
		map.put("page", page);
	    return REGIONLIST;
	}
	
	
 //统计数据全部更新
	@RequestMapping(value = "/totalCount", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String totalCount(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ActivityAccessTotalCountQuery q=new ActivityAccessTotalCountQuery();
		//总访问量统计
		ActivityAccessQuery query=new ActivityAccessQuery();
		Result<List<ActivityAccessTotalCountDO>> result=activityAccessAO.totalCount(query);
		Result<List<ActivityAccessTotalCountDO>> pv=activityAccessAO.totalCountDailyPv(q);
		Result<List<ActivityAccessTotalCountDO>> uv=activityAccessAO.totalCountDailyUv(q);
		
		if(result.isSuccess()&&pv.isSuccess()&&uv.isSuccess()){
			System.out.println("更新开始");
			activityAccessTotalCountAO.deleteByQuery(q);
			for(ActivityAccessTotalCountDO totalCount:result.getValue()){
				totalCount.setType(1);
				activityAccessTotalCountAO.save(totalCount);
			}
			for(ActivityAccessTotalCountDO t:pv.getValue()){
				t.setType(2);
				for(ActivityAccessTotalCountDO t1:uv.getValue()){
					if(StatisticsTask.isEqual(t,t1)){
						t.setUv(t1.getUv());
					}
				}
				activityAccessTotalCountAO.save(t);
			}
		}
		return "succeed";
	 }
	

}

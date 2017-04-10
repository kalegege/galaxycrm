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

import com.wasu.ptyw.activitylottery.core.ao.ActivityAccessAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAccessAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryAccessQuery;
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
@RequestMapping("/activity/lotteryAccessAll")
public class ActivityLotteryAccessAllController {
    
    
    @Autowired
    private ActivityLotteryAccessAO activityLotteryAccessAO;
    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
       
	private static final String LIST = "activitylottery/activitylotteryaccess/list";
	private static final String LOTTERYLIST = "activitylottery/activitylotteryaccess/lotterylist";
	

	@RequestMapping(value = "/exportActivityLotterySubmit", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void exprotExcel(Page page, String prize,String keywords, Map<String, Object> map, HttpServletRequest request,HttpServletResponse response) throws Exception{
		 ActivityLotteryAccessQuery query = new ActivityLotteryAccessQuery();
		   if (StringUtils.isNotEmpty(keywords) && !"0".equalsIgnoreCase(keywords)) {
			   query.setCode(keywords);
		    }
		      PageUtils.convertPage(query, page);
		  //  Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.query(query);
		      //ActivityAccessDO  activityAccessAO
		     Result<List<ActivityLotteryAccessDO>> queryResult = activityLotteryAccessAO.queryStbVisitToExcel(query);
		 
		// 调用封装类执行导出
				// 导出文件存放的路径，并且是虚拟目录指向的路径
		  // 调用封装类执行导出
				// 导出文件存放的路径，并且是虚拟目录指向的路径
			  // String filePath = "//tmp//excelupload//";
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
				fieldNames.add("抽奖次数");
				fieldNames.add("机顶盒数量");

				// 告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
				List<String> fieldCodes = new ArrayList<String>();
				
				fieldCodes.add("count");
				fieldCodes.add("stb_count");// 
				// 注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
				// 开始导出，执行一些workbook及sheet等对象的初始创建
				ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath,
						"/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
				// 导出的数据通过service取出
				//List<YpxxCustom> list = ypxxService.findYpxxList(ypxxQueryVo);
				   List<ActivityLotteryAccessDO> list=queryResult.getValue();
				// 执行导出
				excelExportSXXSSF.writeDatasByObject(list);
				// 输出文件，返回下载文件的http地址，已经包括虚拟目录
				excelExportSXXSSF.exportFile(request,response);
	 }


	 /**
	  * @return
	  */
	@RequiresPermissions("activitylotteryaccess:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST})
	public String accountSelectList(Page page,String mark, String region,String keywords,String able, Map<String, Object> map, HttpServletRequest request){
	    ActivityLotteryAccessQuery query = new ActivityLotteryAccessQuery();
	    if(StringUtils.isNotEmpty(mark)&& Integer.parseInt(mark)==100){
	    	 if (StringUtils.isNotEmpty(keywords)) {
		   		  query.setCode(keywords);
		   	      }
		   	     if (StringUtils.isNotEmpty(region)) {
		   			 query.setRegion(region);
		   		  }
		   	    PageUtils.convertPage(query, page);
		   	    Result<List<ActivityLotteryAccessDO>> queryResult = activityLotteryAccessAO.queryStbVisit(query);
		   	    List<ActivityLotteryAccessDO> listdo=queryResult.getValue();
		   	    for (ActivityLotteryAccessDO listdos:listdo) {
		   			System.out.println(listdos.getCount()+listdos.getStb_count());
		   		}
		   	    if (queryResult.isSuccess()) {
		   		   page.setTotalCount(query.getTotalItem());
		   		   map.put("datas", queryResult.getValue());
		   	     }
		   	    ActivityIntroductionQuery activityIntroductionQuery = new ActivityIntroductionQuery();
		   	    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(activityIntroductionQuery);
		   	   if(activityIntroductionQueryResult.isSuccess()){
		   		 map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
		   	    }
		   	    map.put("status", keywords);
			    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
		   	    map.put("mark", mark);
		   	    map.put("page", page);
		   	    map.put("region", region);
		   	    map.put("keywords", keywords);
		   	    map.put("able", able);
		   	    return LOTTERYLIST;
	    }else {
	    	 if (StringUtils.isNotEmpty(keywords)) {
	   		   query.setCode(keywords);
	   	      }
	   	     if (StringUtils.isNotEmpty(region)) {
	   			 query.setRegion(region);
	   		  }
	   	    if (StringUtils.isNotEmpty(able)) {
	   			 query.setAble(Integer.parseInt(able));
	   		  }
	   	    PageUtils.convertPage(query, page);
	   	    Result<List<ActivityLotteryAccessDO>> queryResult = activityLotteryAccessAO.query(query);
	   	    List<ActivityLotteryAccessDO> listdo=queryResult.getValue();
	   	    for (ActivityLotteryAccessDO listdos:listdo) {
	   			System.out.println(listdos.getCode());
	   			System.out.println(listdos.getAble());
	   		}
	   	    if (queryResult.isSuccess()) {
	   		 page.setTotalCount(query.getTotalItem());
	   		 map.put("datas", queryResult.getValue());
	   	     }
	   	 ActivityIntroductionQuery activityIntroductionQuery = new ActivityIntroductionQuery();
	   	    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(activityIntroductionQuery);
	   	   if(activityIntroductionQueryResult.isSuccess()){
	   		   
	   		map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
	   		   
	   	   }
	   	    map.put("status", keywords);
		    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
	   	    map.put("page", page);
	   	    map.put("region", region);
	   	    map.put("keywords", keywords);
	   	    map.put("able", able);
	   	    return LIST;
		}
	   
	}
	
}

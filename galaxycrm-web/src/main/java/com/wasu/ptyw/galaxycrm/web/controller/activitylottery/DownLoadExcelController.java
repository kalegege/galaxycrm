package com.wasu.ptyw.galaxycrm.web.controller.activitylottery;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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

import shiro.PageUtils;
import shiro.dataobject.Page;

import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;
import com.wasu.ptyw.galaxy.common.dataobject.Result;



/**
 * 
 * @author quxy
 * @date 2015年9月6日
 * @version V1.0
 * @TODO ftp账号管理
 */
@Controller
@RequestMapping("/activity/download")
public class DownLoadExcelController {
    
    
    @Autowired
    private ActivityLotteryAO activityLotteryAO;
    
    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
   
	private static final String LIST = "activitylottery/downloadexcel/list";
	private static final String LISTPRIZEANDREGION = "activitylottery/activitylottery/prizeandregion";
	private static final String DOWNEXCEL = "activitylottery/activitylottery/downexcel";
	
	@RequestMapping(value = "/downloadexcelto", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void exprotExcelbak(Page page,String region, String prize,String keywords, Map<String, Object> map, HttpServletRequest request,HttpServletResponse response) throws Exception{
		System.setProperty("java.awt.headless", "true");
		ActivityIntroductionQuery ActivityIntroductionQuery = new ActivityIntroductionQuery();
		    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(ActivityIntroductionQuery);
		    map.put("status", keywords);
		    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
		   ActivityLotteryQuery query = new ActivityLotteryQuery();
		   if (StringUtils.isNotEmpty(keywords) && !"0".equalsIgnoreCase(keywords)) {
			   query.setCode(keywords);
		    }
		      PageUtils.convertPage(query, page);
		      Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.queryByNoPage(query);
		     List<ActivityLotteryDO> lists= queryResult.getValue();
		        // 调用封装类执行导出
				// 导出文件存放的路径，并且是虚拟目录指向的路径
				//String filePath = "d:/upload/linshi/";
			    //String filePath = "//tmp//excelupload//";
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
				String[] titleHeander = {"id","奖品编号","活动编号","机顶盒号","区域编号","奖品等级","奖品名称","手机"};
				List<String> fieldCodes = new ArrayList<String>();
				fieldCodes.add("code");//
				fieldCodes.add("stbId");
				fieldCodes.add("region");
				fieldCodes.add("prize");
				fieldCodes.add("prizeTxt");
				fieldCodes.add("mobile");
				ExcelUtils2.export(MyUtil.getCurrentTimeStr(), "szhd", titleHeander,lists, request, response);
	 }
	

	@RequiresPermissions("activitylottery:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST})
	public String accountSelectList(Page page,String region,String prize,String keywords,String keywords2, Map<String, Object> map, HttpServletRequest request){
		    //统计查询
		      ActivityLotteryQuery query = new ActivityLotteryQuery();
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
				     if (StringUtils.isNotEmpty(keywords2)) {
						query.setStbId(keywords2);
				      }
				    PageUtils.convertPage(query, page);
				    Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.query(query);
				    List<ActivityLotteryDO> listdo=queryResult.getValue();
				    for (ActivityLotteryDO listdos:listdo) {
						System.out.println(listdos.getPrizeCode());
					}
				    if (queryResult.isSuccess()) {
					 page.setTotalCount(query.getTotalItem());
					 map.put("datas", queryResult.getValue());
				    }
				    ActivityIntroductionQuery ActivityIntroductionQuery = new ActivityIntroductionQuery();
				    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(ActivityIntroductionQuery);
				    map.put("status", keywords);
				    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
				    map.put("page", page);
				    map.put("keywords", keywords);
				    map.put("keywords2", keywords2);
				    map.put("prize", 0);
				    return LIST;
			}
				
	
	@RequestMapping(value = "/downloadexcelbak", method = { RequestMethod.GET, RequestMethod.POST})
	public String  exprotExcelbak2(Page page,String region, String prize,String keywords, Map<String, Object> map, HttpServletRequest request,HttpServletResponse response) throws Exception{
		    ActivityIntroductionQuery ActivityIntroductionQuery = new ActivityIntroductionQuery();
		    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(ActivityIntroductionQuery);
		    map.put("status", keywords);
		    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
		   ActivityLotteryQuery query = new ActivityLotteryQuery();
		   if (StringUtils.isNotEmpty(keywords)) {
			   query.setCode(keywords);
		    }
		      PageUtils.convertPage(query, page);
		      Result<List<ActivityLotteryDO>> queryResult = activityLotteryAO.queryByNoPage(query);
		     List<ActivityLotteryDO> lists= queryResult.getValue();
		        // 调用封装类执行导出
				// 导出文件存放的路径，并且是虚拟目录指向的路径
				//String filePath = "d:/upload/linshi/";
			    //String filePath = "//tmp//excelupload//";
				// -1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
				// 定义导出数据的title
				List<String> fieldNames = new ArrayList<String>();
				fieldNames.add("id");
				fieldNames.add("活动名称");
				fieldNames.add("机顶盒号");
				fieldNames.add("区域编号");
				fieldNames.add("奖品等级");
				fieldNames.add("奖品名称");
				fieldNames.add("手机");
				String[] titleHeander = {"id","奖品编号","活动编号","机顶盒号","区域编号","奖品等级","奖品名称","手机"};
				List<String> fieldCodes = new ArrayList<String>();
				fieldCodes.add("code");//
				fieldCodes.add("stbId");
				fieldCodes.add("region");
				fieldCodes.add("prize");
				fieldCodes.add("prizeTxt");
				fieldCodes.add("mobile");
				String excelname=ExcelUtils3.export(MyUtil.getCurrentTimeStr(), "szhd", titleHeander,lists, request, response);
	            System.out.println(excelname);
				map.put("excelname", excelname);
	            return LIST;
	}
	
	
	@RequestMapping(value = "/downloadExcel", method = { RequestMethod.GET, RequestMethod.POST})
	 public void download(String filename, HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		// 判断浏览器类型
				String userAgent = request.getHeader("user-agent");
				System.out.println(userAgent);
				if (userAgent.toLowerCase().indexOf("msie") != -1){ // IE
					filename = URLEncoder.encode(filename, "utf-8");
				}else{
					filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
				}
				 response.setHeader("Content-Disposition", "attachment;filename="+ filename);
				 String filePathurl=MyUtil.linuxpath+filename;
				 InputStream in = new FileInputStream(filePathurl); 
				 byte[] bytes=new byte[1024];
				 OutputStream out2 = response.getOutputStream();  
			        //写文件  
			        int len;  
			     while((len=in.read(bytes))!= -1)  
			     {  
			            out2.write(bytes,0,len);  
			     }  
			     in.close();
		
	 }
	    
	
	
}

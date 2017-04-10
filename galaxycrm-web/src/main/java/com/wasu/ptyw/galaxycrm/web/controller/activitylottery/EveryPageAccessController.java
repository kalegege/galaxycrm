package com.wasu.ptyw.galaxycrm.web.controller.activitylottery;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.wasu.ptyw.activitylottery.core.ao.ActivityAccessEveryPageAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageVO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountVO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessEveryPageQuery;
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
@RequestMapping("/activity/everyPageAccess")
public class EveryPageAccessController {
    
    
    @Autowired
    private ActivityAccessAO activityAccessAO;
    

    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
	
    @Autowired
	private ActivityAccessEveryPageAO activityAccessEveryPageAO;
    
	private static final String LIST = "activitylottery/everypageview/list";
	private static final String REGIONLIST = "activitylottery/everypageview/regionlist";

	

	 /**
	  * @return
	  */
	@RequiresPermissions("everypageview:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST})
	public String accountSelectList(Page page, String mark,String keywords, Map<String, Object> map, HttpServletRequest request){
		
	    map.put("keywords", keywords);
	    map.put("status", keywords);
	    map.put("mark", mark);
	    //设置查询参数
	    ActivityAccessEveryPageQuery query = new ActivityAccessEveryPageQuery();
		if (StringUtils.isNotEmpty(keywords)) {
			   query.setCode(keywords);
		     }
		PageUtils.convertPage(query, page);
		//判断获取那种统计指标
		if(StringUtils.isNotEmpty(mark) &&Integer.parseInt(mark)==101){
		//页面总uv	    
			query.setType(1);
			Result<List<ActivityAccessEveryPageVO>> queryResult = activityAccessEveryPageAO.statistics(query);
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				for(ActivityAccessEveryPageVO tc:queryResult.getValue()){
					tc.setCount(tc.getUv());
				}
				map.put("datas", queryResult.getValue());
			}
			    
		}else if(StringUtils.isNotEmpty(mark) &&Integer.parseInt(mark)==102){
		//每日页面总uv	    
			query.setType(2);
			Result<List<ActivityAccessEveryPageVO>> queryResult = activityAccessEveryPageAO.statistics(query);
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				for(ActivityAccessEveryPageVO tc:queryResult.getValue()){
					tc.setCount(tc.getUv());
				}
				map.put("datas", queryResult.getValue());
			}	
		}else if(StringUtils.isNotEmpty(mark) &&Integer.parseInt(mark)==103){
		//每日页面总pv	
			query.setType(2);
			//无参数默认显示总pv
			Result<List<ActivityAccessEveryPageVO>> queryResult = activityAccessEveryPageAO.statistics(query);
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				for(ActivityAccessEveryPageVO tc:queryResult.getValue()){
					tc.setCount(tc.getPv());
				}
				map.put("datas", queryResult.getValue());
			}	
		}else {
		//页面总pv	
			query.setType(1);
			//无参数默认显示总pv
			Result<List<ActivityAccessEveryPageVO>> queryResult = activityAccessEveryPageAO.statistics(query);
			if (queryResult.isSuccess()) {
				page.setTotalCount(query.getTotalItem());
				for(ActivityAccessEveryPageVO tc:queryResult.getValue()){
					tc.setCount(tc.getPv());
				}
				map.put("datas", queryResult.getValue());
			}
		}
		//单选框活动加载
	    ActivityIntroductionQuery activityIntroductionQuery = new ActivityIntroductionQuery();
	    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(activityIntroductionQuery);
	    if(activityIntroductionQueryResult.isSuccess()){
	    	 map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
	     }
	    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
	    map.put("page", page);
	    return REGIONLIST;
	}
	
	
	
	
	
	
	
	
	
	
	
	 /**
	  * @return
	  */
	@RequiresPermissions("everypageview:view")
	@RequestMapping(value = "/list1", method = { RequestMethod.GET, RequestMethod.POST})
	public String accountSelectList1(Page page, String mark,String region,String keywords, Map<String, Object> map, HttpServletRequest request){
		ActivityAccessQuery query = new ActivityAccessQuery();
		if(StringUtils.isNotEmpty(mark) &&Integer.parseInt(mark)==100){
			  
			
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
			    PageUtils.convertPage(query, page);
			    Result<List<ActivityAccessDO>> queryResult = activityAccessAO.queryByRegionEveryPage(query);
			    //Result<List<ActivityAccessDO>> queryResult = activityAccessAO.query(query);
			    List<ActivityAccessDO> listdo=queryResult.getValue();
			    for (ActivityAccessDO listdos:listdo) {
					System.out.println(listdos.getCode());
					System.out.println(listdos.getReferer()); 
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
			    Result<List<ActivityAccessDO>> queryResult = activityAccessAO.queryByRegionEveryPageUV(query);
			    //Result<List<ActivityAccessDO>> queryResult = activityAccessAO.query(query);
			    List<ActivityAccessDO> listdo=queryResult.getValue();
			    for (ActivityAccessDO listdos:listdo) {
					System.out.println(listdos.getCode());
					System.out.println(listdos.toString());
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
	
	
	 //统计数据全部更新
		@RequestMapping(value = "/everypage", method = { RequestMethod.GET, RequestMethod.POST})
		@ResponseBody
		public String totalCount(HttpServletRequest request,HttpServletResponse response) throws Exception{
			try{
				//每日页面统计更新
				ActivityAccessEveryPageQuery query=new ActivityAccessEveryPageQuery();
				Result<List<ActivityAccessEveryPageDO>> result=activityAccessAO.everyPageDaily(query);
				//返回正常且有数据更新
				if(result.isSuccess()&&result.getValue().size()>0){
					System.out.println("everyPageDaily start");
					query.setType(2);
					activityAccessEveryPageAO.deleteByQuery(query);
					for(ActivityAccessEveryPageDO t:result.getValue()){
						t.setType(2);
						activityAccessEveryPageAO.save(t);
					}
				}
				System.out.println("everyPageDaily end");
					
				//总页面统计更新
				ActivityAccessQuery q=new ActivityAccessQuery();
				ActivityAccessEveryPageQuery q1=new ActivityAccessEveryPageQuery();
				Result<List<ActivityAccessEveryPageDO>> result1=activityAccessAO.everyPage(q);
				if(result1.isSuccess()&&result1.getValue().size()>0){
					System.out.println("everyPage start");
					q1.setType(1);
					activityAccessEveryPageAO.deleteByQuery(q1);
					for(ActivityAccessEveryPageDO everyPage:result1.getValue()){
						everyPage.setType(1);
						activityAccessEveryPageAO.save(everyPage);
					}
					System.out.println("everyPage end");
				}
				return "succeed";
			}catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		 }
		
}

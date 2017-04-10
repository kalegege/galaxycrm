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
import com.wasu.ptyw.activitylottery.core.ao.ActivityAccessNewAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessNewDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
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
@RequestMapping("/activity/everyPageAccessNew")
public class EveryPageAccessNewController {
    
    
    @Autowired
    private ActivityAccessNewAO activityAccessNewAO;
    

    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
    
	private static final String LIST = "activitylottery/everypageview/listNew";
	private static final String REGIONLIST = "activitylottery/everypageview/regionlistNew";

	 /**
	  * @return
	  */
	@RequiresPermissions("everypageview:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST})
	public String accountSelectList(Page page, String mark,String region,String keywords, Map<String, Object> map, HttpServletRequest request){
		ActivityAccessQuery query = new ActivityAccessQuery();
		if(StringUtils.isNotEmpty(mark) &&Integer.parseInt(mark)==100){
			   if (StringUtils.isNotEmpty(keywords)) {
				   query.setCode(keywords);
				   ActivityIntroductionQuery queryIntroductionQuery = new ActivityIntroductionQuery();
				   queryIntroductionQuery.setCode(keywords);
//				   Result<List<ActivityIntroductionDO>> queryResult2 =activityIntroductionAO.query(queryIntroductionQuery);
//				  List<ActivityIntroductionDO> ActivityIntroductionDO= queryResult2.getValue();
//				   for (ActivityIntroductionDO do2:ActivityIntroductionDO) {
//					  System.out.println(do2.getName());
//					  map.put("huodongmingcheng", do2.getName());
//					  break;
//				      }
				   
			     }
			    PageUtils.convertPage(query, page);
			    Result<List<ActivityAccessNewDO>> queryResult = activityAccessNewAO.queryByRegionEveryPage(query);
			    //Result<List<ActivityAccessNewDO>> queryResult = activityAccessAO.query(query);
			    List<ActivityAccessNewDO> listdo=queryResult.getValue();
			    for (ActivityAccessNewDO listdos:listdo) {
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
//				   Result<List<ActivityIntroductionDO>> queryResult2 =activityIntroductionAO.query(queryIntroductionQuery);
//				  List<ActivityIntroductionDO> ActivityIntroductionDO= queryResult2.getValue();
//				   for (ActivityIntroductionDO do2:ActivityIntroductionDO) {
//					  System.out.println(do2.getName());
//					  map.put("huodongmingcheng", do2.getName());
//					  break;
//				      }
				   
			     }
			    if (StringUtils.isNotEmpty(region)) {
					query.setRegion(region);
				 }
			    PageUtils.convertPage(query, page);
			    Result<List<ActivityAccessNewDO>> queryResult = activityAccessNewAO.queryByRegionEveryPageUV(query);
			    //Result<List<ActivityAccessNewDO>> queryResult = activityAccessAO.query(query);
			    List<ActivityAccessNewDO> listdo=queryResult.getValue();
			    for (ActivityAccessNewDO listdos:listdo) {
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
	
}

package com.wasu.ptyw.galaxycrm.web.controller.activitylottery;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryListAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryListDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryListQuery;
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
@RequestMapping("/activity/activitylotterylist")
public class ActivityLotteryListController {
    
    
    @Autowired
    private ActivityLotteryListAO activityLotteryListAO;
    
    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
       
	private static final String LIST = "activitylottery/activitylotterylist/list";
	
	
	
	




	 /**
	  * @return
	  */
	@RequiresPermissions("activitylotterylist:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST})
	public String accountSelectList(Page page, String prize,String keywords, Map<String, Object> map, HttpServletRequest request){
	    ActivityLotteryListQuery query = new ActivityLotteryListQuery();
	    if (StringUtils.isNotEmpty(keywords)) {
		  query.setCode(keywords);
	     }
	    if(StringUtils.isNotEmpty(prize)){
	    	query.setPrize(Integer.parseInt(prize));
	    }
	    PageUtils.convertPage(query, page);
	    Result<List<ActivityLotteryListDO>> queryResult = activityLotteryListAO.query(query);
	    List<ActivityLotteryListDO> listdo=queryResult.getValue();
	    for (ActivityLotteryListDO listdos:listdo) {
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
	    map.put("prize", prize);
	    return LIST;
	}
	
}

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
import com.wasu.ptyw.activitylottery.core.ao.ActivityPrizeAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryListDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPrizeDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryListQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPrizeQuery;
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
@RequestMapping("/activity/activityIntroduction")
public class ActivityIntroductionController {
    
    
    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
       
	  private static final String LIST = "activitylottery/activityintroduction/list";
	  private static final String CREATE = "activitylottery/activityintroduction/create";
      private static final String UPDATE = "activitylottery/activityintroduction/update";
	
	
      @RequiresPermissions("ActivityIntroduction:save")
   	  @RequestMapping(value = "/validate" ,method = RequestMethod.GET)
      @ResponseBody
      public String codeValidate(Map<String, Object> map, HttpServletRequest request) {
         String code= request.getParameter("code");
         ActivityIntroductionQuery query = new ActivityIntroductionQuery();
 	     if (StringUtils.isNotEmpty(code)) {
 		   query.setCode(code);
 	      }
 	      String oldCode="";
 	      Result<List<ActivityIntroductionDO>> queryResult = activityIntroductionAO.query(query);
 	      for (ActivityIntroductionDO listDo:queryResult.getValue()) {
 	    	oldCode= listDo.getCode();
		   }
 	      if(oldCode.equalsIgnoreCase(code)){
 	    	 return "false"; 
 	        }else {
 	    	 return "true";
	    	}
 	    
    	
   	 }

     @RequiresPermissions("ActivityIntroduction:save")
  	 @RequestMapping(value = "/create", method = RequestMethod.GET)
  	public String preCreate(Page page, String keywords, Map<String, Object> map, HttpServletRequest request) {
        return CREATE;
  	}
  	
  	@RequiresPermissions("ActivityIntroduction:save")
  	@RequestMapping(value = "/create", method = RequestMethod.POST)
  	public @ResponseBody
  	String create(ActivityIntroductionDO item, HttpServletRequest request) {
  		activityIntroductionAO.save(item);
  		AjaxObject ajaxObject = new AjaxObject("添加成功！");
  		return ajaxObject.toString();
  	}

  	
  	 @RequiresPermissions("ActivityIntroduction:delete")
 	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
 	public @ResponseBody
 	String delete(@PathVariable Long id) {
 	     Result<Integer> result = activityIntroductionAO.deleteById(id);
 		AjaxObject ajaxObject = new AjaxObject();
 		if (result.isSuccess()) {
 			ajaxObject.setMessage("删除成功！");
 		} else {
 			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
 			ajaxObject.setMessage("删除失败：" + result.getMessage());
 		}
 		ajaxObject.setCallbackType("");
 		return ajaxObject.toString();
 	}

	 /**
	  * @return
	  */
	@RequiresPermissions("ActivityIntroduction:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST})
	public String accountSelectList(Page page, String keywords, Map<String, Object> map, HttpServletRequest request){
	    ActivityIntroductionQuery query = new ActivityIntroductionQuery();
	    if (StringUtils.isNotEmpty(keywords)) {
		   query.setCode(keywords);
	     }
	    PageUtils.convertPage(query, page);
	    Result<List<ActivityIntroductionDO>> queryResult = activityIntroductionAO.query(query);
	    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(query);
	    List<ActivityIntroductionDO> dos=activityIntroductionQueryResult.getValue();
	     if(queryResult.isSuccess()) {
	    	page.setTotalCount(query.getTotalItem());
		    map.put("datas", queryResult.getValue());
	      }
	     if(activityIntroductionQueryResult.isSuccess()){
	    	 map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
	     }
	    map.put("page", page);
	    map.put("keywords", keywords);
	    map.put("status", keywords);
	    map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
	   for(ActivityIntroductionDO do1 :dos){
		   System.out.println(do1.getCode());
	   }
	    return LIST;
	}
	
	
 	 @RequiresPermissions("ActivityIntroduction:edit")
     @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
     public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
 	    
 	  ActivityIntroductionQuery query = new ActivityIntroductionQuery();
      Result<List<ActivityIntroductionDO>> queryResult = activityIntroductionAO.query(query);
      if (queryResult.isSuccess()) {
	   map.put("datas", queryResult.getValue());
      }
 	    
     Result<ActivityIntroductionDO> result = activityIntroductionAO.getById(id);
	 if (result.isSuccess()) {
		map.put("item", result.getValue());
	 }
	return UPDATE;
}


 @RequiresPermissions("ActivityIntroduction:edit")
 @RequestMapping(value = "/update", method = RequestMethod.POST)
 public @ResponseBody
 String update(ActivityIntroductionDO item) {
	 activityIntroductionAO.update(item);
	 AjaxObject ajaxObject = new AjaxObject("修改成功！");
	 return ajaxObject.toString();
  }
}

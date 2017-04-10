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

import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.VoteCountAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.VoteCountDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.VoteCountQuery;
import com.wasu.ptyw.galaxy.common.dataobject.Result;

import shiro.PageUtils;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.Page;


/**
 * 
 * @author quxy
 * @date 2015年9月6日
 * @version V1.0
 * @TODO ftp账号管理
 */
@Controller
@RequestMapping("/activity/votecount")
public class VoteCountController {
    
    
    @Autowired
    private VoteCountAO voteCountAO;
    
    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
       
	  private static final String LIST = "activitylottery/votecount/list";
	  private static final String CREATE = "activitylottery/votecount/create";
      private static final String UPDATE = "activitylottery/votecount/update";
	
	
      @RequiresPermissions("ActivityIntroduction:save")
   	  @RequestMapping(value = "/validate" ,method = RequestMethod.GET)
      @ResponseBody
      public String codeValidate(Map<String, Object> map, HttpServletRequest request) {
         String activityId= request.getParameter("activity_id");
         VoteCountQuery query = new VoteCountQuery();
 	     if (StringUtils.isNotEmpty(activityId)) {
 		   query.setActivityId(activityId);
 	      }
 	      String oldCode="";
 	      Result<List<VoteCountDO>> queryResult = voteCountAO.query(query);
 	      for (VoteCountDO listDo:queryResult.getValue()) {
 	    	oldCode= listDo.getActivityId();
		   }
 	      if(oldCode.equalsIgnoreCase(activityId)){
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
  	String create(VoteCountDO item, HttpServletRequest request) {
  		voteCountAO.save(item);
  		AjaxObject ajaxObject = new AjaxObject("添加成功！");
  		return ajaxObject.toString();
  	}

  	
  	 @RequiresPermissions("ActivityIntroduction:delete")
 	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
 	public @ResponseBody
 	String delete(@PathVariable Long id) {
 	     Result<Integer> result = voteCountAO.deleteById(id);
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
	    VoteCountQuery query = new VoteCountQuery();
	    ActivityIntroductionQuery activityquery = new ActivityIntroductionQuery();
	    if (StringUtils.isNotEmpty(keywords)) {
		   query.setActivityId(keywords);
		   activityquery.setCode(keywords);
	     }
	    PageUtils.convertPage(query, page);
	    Result<List<VoteCountDO>> queryResult = voteCountAO.query(query);
	    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(activityquery);
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
 	    
 	  VoteCountQuery query = new VoteCountQuery();
      Result<List<VoteCountDO>> queryResult = voteCountAO.query(query);
      if (queryResult.isSuccess()) {
			map.put("datas", queryResult.getValue());
      }
 	    
     Result<VoteCountDO> result = voteCountAO.getById(id);
	 if (result.isSuccess()) {
		map.put("item", result.getValue());
	 }
	return UPDATE;
}


 @RequiresPermissions("ActivityIntroduction:edit")
 @RequestMapping(value = "/update", method = RequestMethod.POST)
 public @ResponseBody
 String update(VoteCountDO item) {
	 voteCountAO.update(item);
	 AjaxObject ajaxObject = new AjaxObject("修改成功！");
	 ajaxObject.setNavTabId("64");
	 return ajaxObject.toString();
  }
}

package com.wasu.ptyw.galaxycrm.web.controller.activitylottery;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import shiro.PageUtils;
import shiro.dataobject.AjaxObject;
import shiro.dataobject.Page;

import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryListAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityPagenameRealnameAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityPrizeAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryListDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPagenameRealnameDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPrizeDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryListQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPagenameRealnameQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPrizeQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityQuestionQuery;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyDiscountDO;
import com.wasu.ptyw.galaxy.dal.query.GalaxyDiscountQuery;
import com.wasu.ptyw.galaxycrm.web.utile.OfficeUtile;


/**
 * 
 * @author quxy
 * @date 2015年9月6日
 * @version V1.0
 * @TODO ftp账号管理
 */
@Controller
@RequestMapping("/activity/activityPagename")
public class ActivityPagenameController {

    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
    
    @Autowired
    private ActivityPagenameRealnameAO activityPagenameRealnameAO;
    
	  private static final String LIST = "activitylottery/activityPagename/list";
	  private static final String CREATE = "activitylottery/activityPagename/create";
      private static final String UPDATE = "activitylottery/activityPagename/update";
     
     
    //添加奖品信息
//   @RequiresPermissions("ActivityPrize:save")
//	@RequestMapping(value = "/addExl", method = RequestMethod.POST)
//   @ResponseBody
//	public String AddExl(HttpServletRequest request,@RequestParam(value = "fileToUpload", required = false) MultipartFile file) throws IOException {
//   	AjaxObject ajaxObject = new AjaxObject();
//   	ajaxObject.setNavTabId("moduleListNav_69");
//   	String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//   	if(fileType.equals(".xls")||fileType.equals(".xlsx")){
//   		List<ArrayList<String>> data=OfficeUtile.readExl(file.getInputStream(), fileType);
//   		int size=0;
//   		for(ArrayList<String> list :data){
//   			if(size++==0)continue;
//   			ActivityQuestionDO item=new ActivityQuestionDO(list);
//   			activityPagenameRealnameAO.save(item); 
//   		}
//   		ajaxObject.setMessage("成功导入"+(size-1)+"条问题");
//   	}else{
//   		ajaxObject.setMessage("导入文件类型错误");
//   	}
//   	return new String(ajaxObject.toString().getBytes("UTF-8"),"ISO-8859-1");
//	 }
   
   
   
 	
 	@RequiresPermissions("ActivityIntroduction:save")
 	@RequestMapping(value = "/create", method = RequestMethod.POST)
 	public @ResponseBody
 	String create(ActivityPagenameRealnameDO item, HttpServletRequest request) {
 		activityPagenameRealnameAO.save(item);
 		AjaxObject ajaxObject = new AjaxObject("添加成功！");
 		ajaxObject.setNavTabId("moduleListNav_70");
 		return ajaxObject.toString();
 	}
 	
 	
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

  	
  	 @RequiresPermissions("ActivityIntroduction:delete")
 	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
 	public @ResponseBody
 	String delete(@PathVariable Long id) {
 	     Result<Integer> result = activityPagenameRealnameAO.deleteById(id);
 		AjaxObject ajaxObject = new AjaxObject();
 	   	ajaxObject.setNavTabId("moduleListNav_70");
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
		ActivityPagenameRealnameQuery query = new ActivityPagenameRealnameQuery();
	    PageUtils.convertPage(query, page);
	    if (StringUtils.isNotEmpty(keywords)) {
			   query.setPagename(keywords);
		}
	    Result<List<ActivityPagenameRealnameDO>> queryResult = activityPagenameRealnameAO.statistics(query);
	     if(queryResult.isSuccess()) {
	    	page.setTotalCount(query.getTotalItem());
		    map.put("datas", queryResult.getValue());
	      }
	    map.put("page", page);
	    map.put("keywords", keywords);
	    map.put("status", keywords);
	    return LIST;
	}
	
	
 	 @RequiresPermissions("ActivityIntroduction:edit")
     @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
     public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
 	    
     Result<ActivityPagenameRealnameDO> result = activityPagenameRealnameAO.getById(id);
	 if (result.isSuccess()) {
		map.put("item", result.getValue());
	 }
	return UPDATE;
}


 @RequiresPermissions("ActivityIntroduction:edit")
 @RequestMapping(value = "/update", method = RequestMethod.POST)
 public @ResponseBody
 String update(ActivityPagenameRealnameDO item) {
	 activityPagenameRealnameAO.update(item);
	 AjaxObject ajaxObject = new AjaxObject("修改成功！");
	 ajaxObject.setNavTabId("moduleListNav_70");
	 return ajaxObject.toString();
  }
}

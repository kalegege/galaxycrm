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
import com.wasu.ptyw.activitylottery.core.ao.ActivityPrizeAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityQuestionAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryListDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPrizeDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryListQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;
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
@RequestMapping("/activity/activityQuestion")
public class ActivityQuestionController {
    
    
  
    
    @Autowired
    private ActivityIntroductionAO activityIntroductionAO;
    
    @Autowired
    private ActivityQuestionAO activityQuestionAO;
    
	  private static final String LIST = "activitylottery/activityQuestion/list";
	  private static final String CREATE = "activitylottery/activityQuestion/create";
      private static final String UPDATE = "activitylottery/activityQuestion/update";
	
      
      
      
      //导出模板
     @RequiresPermissions("ActivityPrize:save")
  	 @RequestMapping(value = "/exportmodel", method = RequestMethod.GET)
  	public void exportmodel(HttpServletRequest request,HttpServletResponse response) {
    	//设置response返回头
 		response.setContentType("application/x-msdownloadoctet-stream;charset=utf-8");
 	    String fileName="答题导入模板"+".xlsx";
 	    try {
 	      response.setHeader("Content-Disposition", "attachment;filename="
 	          + new String(fileName.getBytes(), "ISO-8859-1"));
 	      OutputStream out = response.getOutputStream();
 	      //取出classpath下的模板文件
 	      ClassPathResource res = new ClassPathResource("/doc/答题导入模板.xlsx", this.getClass());
 	      Workbook wb = new XSSFWorkbook(res.getInputStream());
 	      wb.write(out);
 	      out.flush();
 	      out.close();
 	    }
 	    catch (Exception e) {
 	      e.printStackTrace();
 	    }
  	 }
	
     
     
    //添加奖品信息
   @RequiresPermissions("ActivityPrize:save")
	@RequestMapping(value = "/addExl", method = RequestMethod.POST)
   @ResponseBody
	public String AddExl(HttpServletRequest request,@RequestParam(value = "fileToUpload", required = false) MultipartFile file) throws IOException {
   	AjaxObject ajaxObject = new AjaxObject();
   	ajaxObject.setNavTabId("moduleListNav_71");
   	String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
   	if(fileType.equals(".xls")||fileType.equals(".xlsx")){
   		List<ArrayList<String>> data=OfficeUtile.readExl(file.getInputStream(), fileType);
   		int size=0;
   		for(ArrayList<String> list :data){
   			if(size++==0)continue;
   			ActivityQuestionDO item=new ActivityQuestionDO(list);
   			activityQuestionAO.save(item); 
   		}
   		ajaxObject.setMessage("成功导入"+(size-1)+"条问题");
   	}else{
   		ajaxObject.setMessage("导入文件类型错误");
   	}
   	return new String(ajaxObject.toString().getBytes("UTF-8"),"ISO-8859-1");
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
 	     Result<Integer> result = activityQuestionAO.deleteById(id);
 		AjaxObject ajaxObject = new AjaxObject();
 	   	ajaxObject.setNavTabId("moduleListNav_71");
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
		ActivityQuestionQuery query = new ActivityQuestionQuery();
	    if (StringUtils.isNotEmpty(keywords)) {
		   query.setCode(keywords);
	     }
	    PageUtils.convertPage(query, page);
	    
	    Result<List<ActivityQuestionVO>> queryResult = activityQuestionAO.statistics(query);
	    Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult  = activityIntroductionAO.queryAll(new ActivityIntroductionQuery());
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
 	    
     Result<ActivityQuestionDO> result = activityQuestionAO.getById(id);
	 if (result.isSuccess()) {
		map.put("item", result.getValue());
	 }
	return UPDATE;
}


 @RequiresPermissions("ActivityIntroduction:edit")
 @RequestMapping(value = "/update", method = RequestMethod.POST)
 public @ResponseBody
 String update(ActivityQuestionDO item) {
	 activityQuestionAO.update(item);
	 AjaxObject ajaxObject = new AjaxObject("修改成功！");
	 ajaxObject.setNavTabId("moduleListNav_71");
	 return ajaxObject.toString();
  }
}

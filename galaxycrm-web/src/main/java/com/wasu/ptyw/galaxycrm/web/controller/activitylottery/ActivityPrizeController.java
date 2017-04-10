package com.wasu.ptyw.galaxycrm.web.controller.activitylottery;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryListAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityPrizeAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryListDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPrizeDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryListQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPrizeQuery;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxycrm.web.utile.OfficeUtile;
import com.wasu.ptyw.galaxycrm.web.utile.RegionEnum;

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
@RequestMapping("/activity/activityprize")
public class ActivityPrizeController {
    
    
    @Autowired
    private ActivityPrizeAO activityPrizeAO;
    
    @Autowired
    private ActivityIntroductionAO activityIntroductionAO; 
    
    @Autowired
    private ActivityLotteryListAO activityLotteryListAO;
       
	  private static final String LIST = "activitylottery/activityprize/list";
	  private static final String CREATE = "activitylottery/activityprize/create";
      private static final String UPDATE = "activitylottery/activityprize/update";
      private static final String GENERATION = "activitylottery/activityprize/generation";
      private static final String addExl = "activitylottery/activityprize/addExl";
	
  	
      
      
      
      //添加奖品信息
     @RequiresPermissions("ActivityPrize:save")
  	 @RequestMapping(value = "/exportmodel", method = RequestMethod.GET)
  	public void exportmodel(HttpServletRequest request,HttpServletResponse response) {
    	//设置response返回头
 		response.setContentType("application/x-msdownloadoctet-stream;charset=utf-8");
 	    String fileName="奖品导入模板"+".xlsx";
 	    try {
 	      response.setHeader("Content-Disposition", "attachment;filename="
 	          + new String(fileName.getBytes(), "ISO-8859-1"));
 	      OutputStream out = response.getOutputStream();
 	      //取出classpath下的模板文件
 	      ClassPathResource res = new ClassPathResource("/doc/奖品导入模板.xlsx", this.getClass());
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
  	 @RequestMapping(value = "/addExl", method = RequestMethod.GET)
  	public String preAddExl(HttpServletRequest request) {
    	 return addExl;
  	 }
      
     
     //添加奖品信息
    @RequiresPermissions("ActivityPrize:save")
 	@RequestMapping(value = "/addExl", method = RequestMethod.POST)
    @ResponseBody
 	public String AddExl(HttpServletRequest request,@RequestParam(value = "fileToUpload", required = false) MultipartFile file) throws IOException {
    	AjaxObject ajaxObject = new AjaxObject();
    	String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    	if(fileType.equals(".xls")||fileType.equals(".xlsx")){
    		List<ArrayList<String>> data=OfficeUtile.readExl(file.getInputStream(), fileType);
    		int size=0;
    		for(ArrayList<String> list :data){
    			if(size++==0)continue;
    			ActivityPrizeDO item=new ActivityPrizeDO(list);
    			activityPrizeAO.save(item); 
    		}
    		ajaxObject.setMessage("成功导入"+(size-1)+"条奖品");
    	}else{
    		ajaxObject.setMessage("导入文件类型错误");
    	}
    	return new String(ajaxObject.toString().getBytes("UTF-8"),"ISO-8859-1");
 	 }

    
      @RequestMapping(value = "/exportActivityLotterySubmit", method = { RequestMethod.GET, RequestMethod.POST})
  	@ResponseBody
  	public void exprotExcel(Page page, String prize,String keywords, Map<String, Object> map, HttpServletRequest request,HttpServletResponse response) throws Exception{
    	  System.setProperty("java.awt.headless", "true");
    	  ActivityPrizeQuery query = new ActivityPrizeQuery();
    	  if (StringUtils.isNotEmpty(keywords) && !("undefined".equalsIgnoreCase(keywords))) {
  			   query.setCode(keywords);
  		    }
  		
  		  
  		    Result<List<ActivityPrizeDO>> queryResult = activityPrizeAO.queryToExcel(query);
  		    List<ActivityPrizeDO> listdo=queryResult.getValue();
  		    String[] titleHeander = {"id","活动编号","活动名称","奖品等级","奖品名称","每天分配奖品数量","奖品总数量","每天已使用数量","已使用奖品总数量","中奖概率","状态"};
			ExcelUtils2.export(MyUtil.getCurrentTimeStr(), "szhd", titleHeander,listdo, request, response);
  	 }


	// 生成奖品信息
	@RequiresPermissions("ActivityPrize:edit")
	@RequestMapping(value = "/generation/{id}", method = RequestMethod.GET)
	public String generationActivity(@PathVariable Long id, Map<String, Object> map) {

		Result<ActivityPrizeDO> actResult = activityPrizeAO.getById(id);
		ActivityPrizeDO activityPrizeDO = actResult.getValue();

		ActivityLotteryListQuery query = new ActivityLotteryListQuery();
		query.setCode(activityPrizeDO.getCode());
		query.setPrizeTxt(activityPrizeDO.getPrizeTxt());
		query.setPrize(activityPrizeDO.getPrize());
		if (StringUtils.isNotEmpty(activityPrizeDO.getRegion())) {
			query.setRegion(activityPrizeDO.getRegion());
		  }
		int totalCount = activityLotteryListAO.getCountByCondition(query);

		ActivityLotteryListDO activityLotteryListDO = null;

		if (totalCount > activityPrizeDO.getCount()) {
			//需要删除的记录数
			int del=totalCount-activityPrizeDO.getCount();
			query.setStatus(0);
			Result<List<ActivityLotteryListDO>> r =activityLotteryListAO.query(query);
			if(r.getValue().size()>=del){
				for(int i=0;i<del;i++){
					activityLotteryListAO.deleteById(r.getValue().get(i).getId());
				}
				map.put("datas", "该奖项奖品信息删除了"+del+"个");
			}else{
				map.put("datas", "该奖项已经被发放,无法减少");
			}

		} else if (totalCount == activityPrizeDO.getCount()) {

			map.put("datas", "该奖项已经生成,不需要重复生产");

		} else if (totalCount < activityPrizeDO.getCount()) {

			for (int i = 0; i < activityPrizeDO.getCount() - totalCount; i++) {
				activityLotteryListDO = new ActivityLotteryListDO();
				activityLotteryListDO.setCode(activityPrizeDO.getCode());
				activityLotteryListDO.setPrize(activityPrizeDO.getPrize());
				activityLotteryListDO.setStatus(activityPrizeDO.getStatus());
				activityLotteryListDO.setPrizeTxt(activityPrizeDO.getPrizeTxt());
				activityLotteryListDO.setPrizeCode(UUID.randomUUID().toString());
				activityLotteryListDO.setRegion(activityPrizeDO.getRegion());

				activityLotteryListAO.save(activityLotteryListDO);
			}

			map.put("datas", "生成奖项完成");
		}

		return GENERATION;
	}

	// 添加奖品信息
	@RequiresPermissions("ActivityPrize:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Page page, String keywords, Map<String, Object> map, HttpServletRequest request) {
		ActivityIntroductionQuery query = new ActivityIntroductionQuery();
		// Result<List<ActivityIntroductionDO>> activityintroduction=
		// activityIntroductionAO.query(query);
		Result<List<ActivityIntroductionDO>> activityintroduction = activityIntroductionAO.queryAll(query);
		List<ActivityIntroductionDO> activityIntroductionList = activityintroduction.getValue();
		map.put("datas", activityIntroductionList);
		map.put("regions", RegionEnum.values());
		return CREATE;
	}

	@RequiresPermissions("ActivityPrize:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(ActivityPrizeDO item, HttpServletRequest request) {
		activityPrizeAO.save(item);
		AjaxObject ajaxObject = new AjaxObject("添加成功！");
		return ajaxObject.toString();
	}

	@RequiresPermissions("ActivityPrize:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		Result<Integer> result = activityPrizeAO.deleteById(id);
		AjaxObject ajaxObject = new AjaxObject();
		if (result.isSuccess()) {
			ajaxObject.setMessage("删除成功！");
		} else {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("删除失败：" + result.getMessage());
		}
		ajaxObject.setCallbackType("");
		// ajaxObject.setRel("jbsxBox2module");
		return ajaxObject.toString();
	}

	/**
	 * @return
	 */
	@RequiresPermissions("ActivityPrize:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String accountSelectList(Page page, String keywords, Map<String, Object> map, HttpServletRequest request) {
		ActivityPrizeQuery query = new ActivityPrizeQuery();
		if (StringUtils.isNotEmpty(keywords)) {
			query.setCode(keywords);
		}
		PageUtils.convertPage(query, page);
		Result<List<ActivityPrizeDO>> queryResult = activityPrizeAO.query(query);
		List<ActivityPrizeDO> listdo = queryResult.getValue();
		for (ActivityPrizeDO listdos : listdo) {
			System.out.println(listdos.getCode());
		}
		if (queryResult.isSuccess()) {
			page.setTotalCount(query.getTotalItem());
			map.put("datas", queryResult.getValue());
		}
		ActivityIntroductionQuery activityIntroduction = new ActivityIntroductionQuery();
		Result<List<ActivityIntroductionDO>> activityIntroductionQueryResult = activityIntroductionAO
				.queryAll(activityIntroduction);
		map.put("status", keywords);
		map.put("activityIntroductionDatas", activityIntroductionQueryResult.getValue());
		map.put("page", page);
		map.put("keywords", keywords);
		return LIST;
	}

	@RequiresPermissions("ActivityPrize:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {

		ActivityPrizeQuery query = new ActivityPrizeQuery();
		Result<List<ActivityPrizeDO>> queryResult = activityPrizeAO.query(query);
		if (queryResult.isSuccess()) {
			map.put("datas", queryResult.getValue());
		}

		Result<ActivityPrizeDO> result = activityPrizeAO.getById(id);
		if (result.isSuccess()) {
			map.put("item", result.getValue());
		}
		return UPDATE;
	}

	@RequiresPermissions("ActivityPrize:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(ActivityPrizeDO item) {
		activityPrizeAO.update(item);
		AjaxObject ajaxObject = new AjaxObject("修改成功！");
		return ajaxObject.toString();
	}
}

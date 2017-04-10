package com.wasu.ptyw.galaxycrm.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.wasu.ptyw.activitylottery.core.ao.ActivityAccessAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityAccessEveryPageAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityAccessTotalCountAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityIntroductionAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryAO;
import com.wasu.ptyw.activitylottery.core.ao.ActivityLotteryListAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessEveryPageQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;
import com.wasu.ptyw.galaxy.common.dataobject.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatisticsTask {
	
	@Resource
	private ActivityAccessAO activityAccessAO;
	
	@Resource
	private ActivityAccessTotalCountAO activityAccessTotalCountAO;
	
	@Resource
	private ActivityAccessEveryPageAO activityAccessEveryPageAO;
	
	@Resource
	private ActivityIntroductionAO activityIntroductionAO;
	
	@Resource
	private ActivityLotteryAO activityLotteryAO;
	
	@Resource
	private ActivityLotteryListAO activityLotteryListAO;
	
	//定时统计任务
	public void statistic(){
		everyPageDaily();
		everyPage();
		totalCount();
		totalCountDaily();
	}
	
	//无手机号的奖品返回奖品池
	public void returnPrize(){
		log.info("returnPrize begin");
		// 找出最近4次的活动
		ActivityIntroductionQuery query = new ActivityIntroductionQuery();
		query.setOrderBy("gmt_create desc limit 0,4");
		Result<List<ActivityIntroductionDO>> queryResult = activityIntroductionAO.queryMobile(query);
		if (queryResult.isSuccess()) {
			// 最新的四个活动
			List<ActivityIntroductionDO> listdo = queryResult.getValue();
			for (ActivityIntroductionDO activityIntroductionDO : listdo) {
				String code = activityIntroductionDO.getCode();
				// 查询该活动的中奖纪录中手机号为空的记录
				ActivityLotteryQuery lquery = new ActivityLotteryQuery();
				lquery.setCode(code);
				Result<List<ActivityLotteryDO>> result = activityLotteryAO.queryByMobile(lquery);
				if (result.isSuccess()) {
					List<ActivityLotteryDO> list = result.getValue();
					if (list.size() > 0) {
						List<String> prizeCodes = new ArrayList<String>();
						int i = 0;
						for (ActivityLotteryDO activityLotteryDO : list) {
							String prizecode = activityLotteryDO.getPrizeCode();
							prizeCodes.add(i, prizecode);
							i++;
							activityLotteryDO.setMobile("*");
							activityLotteryAO.update(activityLotteryDO);
						}
						Result res = activityLotteryListAO.updateStatusByPrizeCodes(prizeCodes, 0);
					}else{
						continue;
					}
				}

			}

		}
		
		
		log.info("returnPrize end");
	}
	
	
	
	
	//每日页面访问量pv/uv
	public void everyPageDaily() {
		log.info("everyPageDaily begin");
		try {
			SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
			String date=df.format(new Date());
			//测试
//			date="20160517";
			//测试
			log.info("date:"+date);
			ActivityAccessEveryPageQuery query=new ActivityAccessEveryPageQuery();
			query.setDate(date);
			Result<List<ActivityAccessEveryPageDO>> result=activityAccessAO.everyPageDaily(query);
			//返回正常且有数据更新
			if(result.isSuccess()&&result.getValue().size()>0){
				log.info("everyPageDaily数据更新开始");
				activityAccessEveryPageAO.deleteByQuery(query);
				for(ActivityAccessEveryPageDO t:result.getValue()){
					t.setType(2);
					activityAccessEveryPageAO.save(t);
				}
				log.info("everyPageDaily数据更新结束 ");
			}
		log.info("everyPageDaily end");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("everyPageDaily error");
		}
	}

	
	
	//总访问量 pv/uv
	public void everyPage() {
		log.info("everyPage begin");
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		String date=df.format(new Date());
		//测试
//		date="20160817";
		//测试
		log.info("date:"+date);
		ActivityAccessTotalCountQuery query=new ActivityAccessTotalCountQuery();
		query.setDate(date);
		try {
			Result<List<ActivityAccessTotalCountDO>> activitys=activityAccessAO.todayActivity(query);
			if(activitys.isSuccess()&&activitys.getValue().size()>0){
				log.info("everyPage数据更新开始 ");
				//循环当天更新的活动
				for(ActivityAccessTotalCountDO a:activitys.getValue()){
					ActivityAccessQuery q=new ActivityAccessQuery();
					q.setCode(a.getCode());
					Result<List<ActivityAccessEveryPageDO>> result=activityAccessAO.everyPage(q);
					if(result.isSuccess()){
						ActivityAccessEveryPageQuery q1=new ActivityAccessEveryPageQuery();
						q1.setCode(a.getCode());q1.setType(1);
						activityAccessEveryPageAO.deleteByQuery(q1);
						for(ActivityAccessEveryPageDO everyPage:result.getValue()){
							everyPage.setType(1);
							activityAccessEveryPageAO.save(everyPage);
						}
						log.info("everyPage更新活动"+a.getCode());
					}
				}
				log.info("everyPage数据更新结束 ");
			}
		log.info("everyPage end");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("everyPage error");
		}
	}
	
	
	
	
	
	//总访问量 pv/uv
	public void totalCount() {
		log.info("totalCount begin");
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		String date=df.format(new Date());
		//测试
//		date="20160517";
		//测试
		log.info("date:"+date);
		ActivityAccessTotalCountQuery query=new ActivityAccessTotalCountQuery();
		query.setDate(date);
		try {
			Result<List<ActivityAccessTotalCountDO>> activitys=activityAccessAO.todayActivity(query);
			if(activitys.isSuccess()&&activitys.getValue().size()>0){
				log.info("totalCount数据更新开始 ");
				//循环当天更新的活动
				for(ActivityAccessTotalCountDO a:activitys.getValue()){
					ActivityAccessQuery q=new ActivityAccessQuery();
					q.setCode(a.getCode());
					Result<List<ActivityAccessTotalCountDO>> result=activityAccessAO.totalCount(q);
					if(result.isSuccess()){
						ActivityAccessTotalCountQuery q1=new ActivityAccessTotalCountQuery();
						q1.setCode(a.getCode());q1.setType(1);
						activityAccessTotalCountAO.deleteByQuery(q1);
						for(ActivityAccessTotalCountDO totalCount:result.getValue()){
							totalCount.setType(1);
							activityAccessTotalCountAO.save(totalCount);
						}
						log.info("totalCount更新活动"+a.getCode());
					}
				}
				log.info("totalCount数据更新结束 ");
			}
		log.info("totalCount end");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("totalCount error");
		}
	}
	
	
	
	//每日总访问量pv/uv
	public void totalCountDaily() {
		log.info("totalCountDaily begin");
		try {
			SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
			String date=df.format(new Date());
			//测试
//			date="20160517";
			//测试
			log.info("date:"+date);
			ActivityAccessTotalCountQuery query=new ActivityAccessTotalCountQuery();
			query.setDate(date);
			Result<List<ActivityAccessTotalCountDO>> pv=activityAccessAO.totalCountDailyPv(query);
			Result<List<ActivityAccessTotalCountDO>> uv=activityAccessAO.totalCountDailyUv(query);
			//返回正常且有数据更新
			if(pv.isSuccess()&&uv.isSuccess()&&pv.getValue().size()>0){
				log.info("totalCountDaily数据更新开始");
				activityAccessTotalCountAO.deleteByDate(date);
				for(ActivityAccessTotalCountDO t:pv.getValue()){
					t.setType(2);
					for(ActivityAccessTotalCountDO t1:uv.getValue()){
						if(isEqual(t,t1)){
							t.setUv(t1.getUv());
						}
					}
					activityAccessTotalCountAO.save(t);
				}
				log.info("totalCountDaily数据更新结束 ");
			}
		log.info("totalCountDaily end");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("totalCountDaily error");
		}
	}

	public static boolean isEqual(ActivityAccessTotalCountDO t,ActivityAccessTotalCountDO t1){
		if(t.getDate().equals(t1.getDate()) && t.getCode().equals(t1.getCode()) && t.getRegion().equals(t1.getRegion()))
		return true;
		return false;
	}

}

package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessEveryPageQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityAccessDAO extends BaseDAO<ActivityAccessDO> {

	/**
	 * 根据多个id更新状态
	 * 
	 * @param map
	 *            :int status,List ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(Map<String, Object> map) throws DAOException;

	//区域总访问量统计
	public List<ActivityAccessDO> queryByRegionAll(ActivityAccessQuery query);

	public int queryqueryByRegionAllCount(ActivityAccessQuery query);

	//区域stb_id访问量统计
	public int queryByRegionAndStbIDCount(ActivityAccessQuery query);

	public List<ActivityAccessDO> queryByRegionAndStbID(
			ActivityAccessQuery query);

	public List<ActivityAccessDO> queryByNoPage(ActivityAccessQuery query);

	public List<ActivityAccessDO> queryByRegionAndStbIDToExcel(
			ActivityAccessQuery query);

	public List<ActivityAccessDO> queryByRegionAllToExcel(
			ActivityAccessQuery query);

	public int queryByRegionEveryPageCount(ActivityAccessQuery query);

	public List<ActivityAccessDO> queryByRegionEveryPage(
			ActivityAccessQuery query);

	public List<ActivityAccessDO> queryByRegionEveryPageUV(
			ActivityAccessQuery query);
	
	public List<ActivityAccessTotalCountDO> totalCount(ActivityAccessQuery query);
	
	public List<ActivityAccessTotalCountDO> totalCountDailyPv(ActivityAccessTotalCountQuery date);
	
	public List<ActivityAccessTotalCountDO> totalCountDailyUv(ActivityAccessTotalCountQuery date);
	
	public List<ActivityAccessTotalCountDO> todayActivity(ActivityAccessTotalCountQuery date);
	
	public List<ActivityAccessEveryPageDO> everyPageDaily(ActivityAccessEveryPageQuery date);
	
	public List<ActivityAccessEveryPageDO> everyPage(ActivityAccessQuery code);

}
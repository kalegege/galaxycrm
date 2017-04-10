package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessNewDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityAccessNewDAO extends BaseDAO<ActivityAccessNewDO> {

	/**
	 * 根据多个id更新状态
	 * 
	 * @param map
	 *            :int status,List ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(Map<String, Object> map) throws DAOException;

	//区域总访问量统计
	public List<ActivityAccessNewDO> queryByRegionAll(ActivityAccessQuery query);

	public int queryqueryByRegionAllCount(ActivityAccessQuery query);

	//区域stb_id访问量统计
	public int queryByRegionAndStbIDCount(ActivityAccessQuery query);

	public List<ActivityAccessNewDO> queryByRegionAndStbID(
			ActivityAccessQuery query);

	public List<ActivityAccessNewDO> queryByNoPage(ActivityAccessQuery query);

	public List<ActivityAccessNewDO> queryByRegionAndStbIDToExcel(
			ActivityAccessQuery query);

	public List<ActivityAccessNewDO> queryByRegionAllToExcel(
			ActivityAccessQuery query);

	public int queryByRegionEveryPageCount(ActivityAccessQuery query);

	public List<ActivityAccessNewDO> queryByRegionEveryPage(
			ActivityAccessQuery query);

	public List<ActivityAccessNewDO> queryByRegionEveryPageUV(
			ActivityAccessQuery query);
	
	

}
package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityLotteryDAO extends BaseDAO<ActivityLotteryDO> {

	/**
	 * 根据多个id更新状态
	 * 
	 * @param map
	 *            :int status,List ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(Map<String, Object> map) throws DAOException;

	public List<ActivityLotteryDO> queryByMobile(ActivityLotteryQuery query)throws DAOException;
	
	public List<ActivityLotteryDO> queryByRegion(ActivityLotteryQuery query)throws DAOException;

	public int queryByRegionCount(ActivityLotteryQuery query);
	
	public int queryByMobileCount(ActivityLotteryQuery query);

	//地区中奖分区总条数查询
	public int queryByRegionCountAllToall(ActivityLotteryQuery query)throws DAOException;
    //地区中奖分区统计查询
	public List<ActivityLotteryDO> queryByRegionCountAll(
			ActivityLotteryQuery query) throws DAOException;

	public List<ActivityLotteryDO> queryByNoPage(ActivityLotteryQuery query)throws DAOException;

	public List<ActivityLotteryDO> queryByRegionToExcel(
			ActivityLotteryQuery query)throws DAOException;

	public List<ActivityLotteryDO> queryByRegionCountAllToExcel(
			ActivityLotteryQuery query)throws DAOException;

}
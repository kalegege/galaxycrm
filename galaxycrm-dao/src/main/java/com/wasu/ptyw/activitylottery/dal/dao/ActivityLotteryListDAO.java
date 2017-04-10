package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryListDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryListQuery;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityLotteryListDAO extends BaseDAO<ActivityLotteryListDO> {

	/**
	 * 根据多个id更新状态
	 * 
	 * @param map
	 *            :int status,List ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(Map<String, Object> map) throws DAOException;
	public int updateStatusByPrizeCodes(Map<String, Object> map) throws DAOException;

	//根据条件查询总的记录条数
	public int getCountByCondition(ActivityLotteryListQuery query) throws DAOException;

}
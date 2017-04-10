package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityAccessTotalCountDAO extends BaseDAO<ActivityAccessTotalCountDO> {
	
	int deleteByType(int type) throws DAOException;
	
	int deleteByDate(String date) throws DAOException;

	int deleteByQuery(ActivityAccessTotalCountQuery q) throws DAOException;

	int count(ActivityAccessTotalCountQuery q) throws DAOException;

	List<ActivityAccessTotalCountVO> statistics(ActivityAccessTotalCountQuery q) throws DAOException;
	
}
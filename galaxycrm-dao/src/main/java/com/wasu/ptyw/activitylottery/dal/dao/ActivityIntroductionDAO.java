package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
/**
 * @author quxy
 * @date 2015年10月26日
 */
public interface ActivityIntroductionDAO extends BaseDAO<ActivityIntroductionDO> {

	/**
	 * 根据多个id更新状态
	 * 
	 * @param map
	 *            :int status,List ids
	 * @return 更新成功的记录数
	 */
	 public int updateStatusByIds(Map<String, Object> map) throws DAOException;
	
	 public List<ActivityIntroductionDO> queryAll(ActivityIntroductionQuery query);

}
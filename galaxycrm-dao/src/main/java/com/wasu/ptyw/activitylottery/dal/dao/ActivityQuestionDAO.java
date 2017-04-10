package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityQuestionQuery;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityQuestionDAO extends BaseDAO<ActivityQuestionDO> {

	int deleteByQuery(ActivityQuestionQuery q) throws DAOException;

	int count(ActivityQuestionQuery q) throws DAOException;

	List<ActivityQuestionVO> statistics(ActivityQuestionQuery q) throws DAOException;

}
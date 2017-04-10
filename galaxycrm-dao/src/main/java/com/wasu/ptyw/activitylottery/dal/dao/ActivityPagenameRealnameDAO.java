package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageVO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountVO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPagenameRealnameDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessEveryPageQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPagenameRealnameQuery;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityPagenameRealnameDAO extends BaseDAO<ActivityPagenameRealnameDO> {

	int deleteByQuery(ActivityPagenameRealnameQuery q) throws DAOException;

	int count(ActivityPagenameRealnameQuery q) throws DAOException;

	List<ActivityPagenameRealnameDO> statistics(ActivityPagenameRealnameQuery q) throws DAOException;

}
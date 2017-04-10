package com.wasu.ptyw.activitylottery.core.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.core.manager.ActivityAccessEveryPageManager;
import com.wasu.ptyw.activitylottery.core.manager.ActivityAccessManager;
import com.wasu.ptyw.activitylottery.core.manager.ActivityAccessTotalCountManager;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityAccessDAO;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityAccessEveryPageDAO;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityAccessTotalCountDAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageVO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountVO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessEveryPageQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("ActivityAccessEveryPageManager")
public class ActivityAccessEveryPageManagerImpl implements ActivityAccessEveryPageManager {
	@Resource
	private ActivityAccessEveryPageDAO activityAccessEveryPageDAO;

	@Override
	public Long insert(ActivityAccessEveryPageDO obj) throws MyException {
		try {
			activityAccessEveryPageDAO.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(ActivityAccessEveryPageDO obj) throws MyException {
		try {
			return activityAccessEveryPageDAO.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return activityAccessEveryPageDAO.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public int deleteByQuery(ActivityAccessEveryPageQuery q) throws MyException {
		try {
			return activityAccessEveryPageDAO.deleteByQuery(q);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, q);
		}
	}

	@Override
	public List<ActivityAccessEveryPageVO> statistics(ActivityAccessEveryPageQuery query) throws DAOException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessEveryPageDAO.count(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessEveryPageDAO.statistics(query);
	}

}

package com.wasu.ptyw.activitylottery.core.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.core.manager.ActivityAccessManager;
import com.wasu.ptyw.activitylottery.core.manager.ActivityAccessTotalCountManager;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityAccessDAO;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityAccessTotalCountDAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountVO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("ActivityAccessTotalCountManager")
public class ActivityAccessTotalCountManagerImpl implements ActivityAccessTotalCountManager {
	@Resource
	private ActivityAccessTotalCountDAO activityAccessTotalCountDAO;

	@Override
	public Long insert(ActivityAccessTotalCountDO obj) throws MyException {
		try {
			activityAccessTotalCountDAO.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(ActivityAccessTotalCountDO obj) throws MyException {
		try {
			return activityAccessTotalCountDAO.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return activityAccessTotalCountDAO.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public int deleteByType(int type) throws MyException {
		try {
			return activityAccessTotalCountDAO.deleteByType(type);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, type);
		}
	}

	@Override
	public int deleteByDate(String date) throws MyException {
		try {
			return activityAccessTotalCountDAO.deleteByDate(date);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, date);
		}
	}

	@Override
	public int deleteByQuery(ActivityAccessTotalCountQuery q) throws MyException {
		try {
			return activityAccessTotalCountDAO.deleteByQuery(q);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, q);
		}
	}

	@Override
	public List<ActivityAccessTotalCountVO> statistics(ActivityAccessTotalCountQuery query) throws DAOException{
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessTotalCountDAO.count(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessTotalCountDAO.statistics(query);
	}

}

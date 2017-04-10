package com.wasu.ptyw.activitylottery.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.core.manager.ActivityPagenameRealnameManager;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityPagenameRealnameDAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPagenameRealnameDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionDO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPagenameRealnameQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("ActivityPagenameRealnameManager")
public class ActivityPagenameRealnameManagerImpl implements ActivityPagenameRealnameManager {
	@Resource
	private ActivityPagenameRealnameDAO activityPagenameRealnameDAO;

	@Override
	public Long insert(ActivityPagenameRealnameDO obj) throws MyException {
		try {
			activityPagenameRealnameDAO.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(ActivityPagenameRealnameDO obj) throws MyException {
		try {
			return activityPagenameRealnameDAO.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return activityPagenameRealnameDAO.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public int deleteByQuery(ActivityPagenameRealnameQuery q) throws MyException {
		try {
			return activityPagenameRealnameDAO.deleteByQuery(q);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, q);
		}
	}

	@Override
	public List<ActivityPagenameRealnameDO> statistics(ActivityPagenameRealnameQuery query) throws DAOException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityPagenameRealnameDAO.count(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityPagenameRealnameDAO.statistics(query);
	}

	@Override
	public ActivityPagenameRealnameDO getById(long id) throws MyException {
		try {
			return activityPagenameRealnameDAO.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}
}

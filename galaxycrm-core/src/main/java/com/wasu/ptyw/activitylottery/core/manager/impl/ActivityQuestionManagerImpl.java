package com.wasu.ptyw.activitylottery.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.core.manager.ActivityQuestionManager;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityQuestionDAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionVO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.query.ActivityQuestionQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("ActivityQuestionManager")
public class ActivityQuestionManagerImpl implements ActivityQuestionManager {
	@Resource
	private ActivityQuestionDAO activityQuestionDAO;

	@Override
	public Long insert(ActivityQuestionDO obj) throws MyException {
		try {
			activityQuestionDAO.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(ActivityQuestionDO obj) throws MyException {
		try {
			return activityQuestionDAO.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return activityQuestionDAO.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public int deleteByQuery(ActivityQuestionQuery q) throws MyException {
		try {
			return activityQuestionDAO.deleteByQuery(q);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, q);
		}
	}

	@Override
	public List<ActivityQuestionVO> statistics(ActivityQuestionQuery query) throws DAOException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityQuestionDAO.count(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityQuestionDAO.statistics(query);
	}

	@Override
	public ActivityQuestionDO getById(long id) throws MyException {
		try {
			return activityQuestionDAO.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

}

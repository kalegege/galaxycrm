package com.wasu.ptyw.activitylottery.core.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.core.manager.ActivityLotteryListManager;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityLotteryListDAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryListDO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryListQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityLotteryListManager")
public class ActivityLotteryListManagerImpl implements ActivityLotteryListManager {
	@Resource
	private ActivityLotteryListDAO activityLotteryListDao;

	@Override
	public Long insert(ActivityLotteryListDO obj) throws MyException {
		try {
			activityLotteryListDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}
	//根据code和prize查询
		@Override
		public int getCountByCondition(ActivityLotteryListQuery query) {
		
				try {
					return activityLotteryListDao.getCountByCondition(query);
				} catch (DAOException e) {
					e.printStackTrace();
				}
				return 0;
			
		}

	@Override
	public int update(ActivityLotteryListDO obj) throws MyException {
		try {
			return activityLotteryListDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return activityLotteryListDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public ActivityLotteryListDO getById(long id) throws MyException {
		try {
			return activityLotteryListDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<ActivityLotteryListDO> getByIds(List<Long> ids) throws MyException {
		try {
			return activityLotteryListDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}

	@Override
	public List<ActivityLotteryListDO> query(ActivityLotteryListQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityLotteryListDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityLotteryListDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public ActivityLotteryListDO queryFirst(ActivityLotteryListQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<ActivityLotteryListDO> list = activityLotteryListDao.query(query);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
		return null;
	}

	@Override
	public int updateStatusByIds(List<Long> ids, int status) throws MyException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", status);
			map.put("ids", ids);
			return activityLotteryListDao.updateStatusByIds(map);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids, status);
		}
	}
	
	@Override
	public int updateStatusByPrizeCodes(List<String> prizeCodes, int status) throws MyException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", status);
			map.put("prizeCodes", prizeCodes);
			return activityLotteryListDao.updateStatusByPrizeCodes(map);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, prizeCodes, status);
		}
	}
	
	
}

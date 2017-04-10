package com.wasu.ptyw.activitylottery.core.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.core.manager.ActivityLotteryManager;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityLotteryDAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityLotteryManager")
public class ActivityLotteryManagerImpl implements ActivityLotteryManager {
	@Resource
	private ActivityLotteryDAO activityLotteryDao;

	@Override
	public Long insert(ActivityLotteryDO obj) throws MyException {
		try {
			activityLotteryDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(ActivityLotteryDO obj) throws MyException {
		try {
			return activityLotteryDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return activityLotteryDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public ActivityLotteryDO getById(long id) throws MyException {
		try {
			return activityLotteryDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<ActivityLotteryDO> getByIds(List<Long> ids) throws MyException {
		try {
			return activityLotteryDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}
	@Override
	public List<ActivityLotteryDO> queryByNoPage(ActivityLotteryQuery query) throws MyException {
		// TODO Auto-generated method stub
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityLotteryDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityLotteryDao.queryByNoPage(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}
	@Override
	public List<ActivityLotteryDO> query(ActivityLotteryQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityLotteryDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityLotteryDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public ActivityLotteryDO queryFirst(ActivityLotteryQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<ActivityLotteryDO> list = activityLotteryDao.query(query);
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
			return activityLotteryDao.updateStatusByIds(map);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids, status);
		}
	}

	//分地区统计查询
	@Override
	public List<ActivityLotteryDO> queryByRegion(ActivityLotteryQuery query) throws MyException{
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityLotteryDao.queryByRegionCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityLotteryDao.queryByRegion(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	
	//总数量统计
	@Override
	public List<ActivityLotteryDO> queryByRegionCountAll(
			ActivityLotteryQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityLotteryDao.queryByRegionCountAllToall(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityLotteryDao.queryByRegionCountAll(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public List<ActivityLotteryDO> queryByRegionToExcel(
			ActivityLotteryQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityLotteryDao.queryByRegionCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityLotteryDao.queryByRegionToExcel(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public List<ActivityLotteryDO> queryByRegionCountAllToExcel(
			ActivityLotteryQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityLotteryDao.queryByRegionCountAllToall(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityLotteryDao.queryByRegionCountAllToExcel(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public List<ActivityLotteryDO> queryByMobile(ActivityLotteryQuery query) throws MyException {
		try {
			return activityLotteryDao.queryByMobile(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	

}

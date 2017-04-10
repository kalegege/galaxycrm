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
import com.wasu.ptyw.activitylottery.dal.dao.ActivityAccessDAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessEveryPageQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityAccessManager")
public class ActivityAccessManagerImpl implements ActivityAccessManager {
	@Resource
	private ActivityAccessDAO activityAccessDao;

	@Override
	public Long insert(ActivityAccessDO obj) throws MyException {
		try {
			activityAccessDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(ActivityAccessDO obj) throws MyException {
		try {
			return activityAccessDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return activityAccessDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public ActivityAccessDO getById(long id) throws MyException {
		try {
			return activityAccessDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<ActivityAccessDO> getByIds(List<Long> ids) throws MyException {
		try {
			return activityAccessDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}

	@Override
	public List<ActivityAccessDO> query(ActivityAccessQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityAccessDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityAccessDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}
	
	@Override
	public List<ActivityAccessDO> queryByNoPage(ActivityAccessQuery query)
			throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityAccessDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityAccessDao.queryByNoPage(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}
	
	@Override
	public ActivityAccessDO queryFirst(ActivityAccessQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<ActivityAccessDO> list = activityAccessDao.query(query);
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
			return activityAccessDao.updateStatusByIds(map);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids, status);
		}
	}

	@Override
	public List<ActivityAccessDO> queryByRegionAll(ActivityAccessQuery query)
			throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessDao.queryqueryByRegionAllCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessDao.queryByRegionAll(query);
	}

	 //	每一个页面的pv,uv统计
		@Override
		public List<ActivityAccessDO> queryByRegionEveryPage(
				ActivityAccessQuery query) {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityAccessDao.queryByRegionEveryPageCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityAccessDao.queryByRegionEveryPage(query);
		}
	//区域stb_id访问量统计
	@Override
	public List<ActivityAccessDO> queryByRegionAndStbID(
			ActivityAccessQuery query) {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessDao.queryByRegionAndStbIDCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessDao.queryByRegionAndStbID(query);
	}

	@Override
	public List<ActivityAccessDO> queryByRegionAndStbIDToExcel(
			ActivityAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessDao.queryByRegionAndStbIDCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessDao.queryByRegionAndStbIDToExcel(query);
	}

	@Override
	public List<ActivityAccessDO> queryByRegionAllToExcel(
			ActivityAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessDao.queryqueryByRegionAllCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessDao.queryByRegionAllToExcel(query);
	}

	@Override
	public List<ActivityAccessDO> queryByRegionEveryPageUV(
			ActivityAccessQuery query) {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessDao.queryByRegionEveryPageCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessDao.queryByRegionEveryPageUV(query);
	}

	@Override
	public List<ActivityAccessTotalCountDO> totalCount(ActivityAccessQuery query) {
		return activityAccessDao.totalCount(query);
	}

	@Override
	public List<ActivityAccessTotalCountDO> totalCountDailyPv(ActivityAccessTotalCountQuery date) {
		return activityAccessDao.totalCountDailyPv(date);
	}

	@Override
	public List<ActivityAccessTotalCountDO> totalCountDailyUv(ActivityAccessTotalCountQuery date) {
		return activityAccessDao.totalCountDailyUv(date);
	}

	@Override
	public List<ActivityAccessTotalCountDO> todayActivity(ActivityAccessTotalCountQuery date) {
		return activityAccessDao.todayActivity(date);
	}

	@Override
	public List<ActivityAccessEveryPageDO> everyPageDaily(ActivityAccessEveryPageQuery date) {
		return activityAccessDao.everyPageDaily(date);
	}

	@Override
	public List<ActivityAccessEveryPageDO> everyPage(ActivityAccessQuery code) {
		return activityAccessDao.everyPage(code);
	}

}

package com.wasu.ptyw.activitylottery.core.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.core.manager.ActivityAccessNewManager;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityAccessNewDAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessNewDO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityAccessNewManager")
public class ActivityAccessNewManagerImpl implements ActivityAccessNewManager {
	@Resource
	private ActivityAccessNewDAO activityAccessNewDao;

	@Override
	public Long insert(ActivityAccessNewDO obj) throws MyException {
		try {
			activityAccessNewDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(ActivityAccessNewDO obj) throws MyException {
		try {
			return activityAccessNewDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return activityAccessNewDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public ActivityAccessNewDO getById(long id) throws MyException {
		try {
			return activityAccessNewDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<ActivityAccessNewDO> getByIds(List<Long> ids) throws MyException {
		try {
			return activityAccessNewDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}

	@Override
	public List<ActivityAccessNewDO> query(ActivityAccessQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityAccessNewDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityAccessNewDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}
	
	@Override
	public List<ActivityAccessNewDO> queryByNoPage(ActivityAccessQuery query)
			throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityAccessNewDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityAccessNewDao.queryByNoPage(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}
	
	@Override
	public ActivityAccessNewDO queryFirst(ActivityAccessQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<ActivityAccessNewDO> list = activityAccessNewDao.query(query);
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
			return activityAccessNewDao.updateStatusByIds(map);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids, status);
		}
	}

	@Override
	public List<ActivityAccessNewDO> queryByRegionAll(ActivityAccessQuery query)
			throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessNewDao.queryqueryByRegionAllCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessNewDao.queryByRegionAll(query);
	}

	 //	每一个页面的pv,uv统计
		@Override
		public List<ActivityAccessNewDO> queryByRegionEveryPage(
				ActivityAccessQuery query) {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityAccessNewDao.queryByRegionEveryPageCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return activityAccessNewDao.queryByRegionEveryPage(query);
		}
	//区域stb_id访问量统计
	@Override
	public List<ActivityAccessNewDO> queryByRegionAndStbID(
			ActivityAccessQuery query) {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessNewDao.queryByRegionAndStbIDCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessNewDao.queryByRegionAndStbID(query);
	}

	@Override
	public List<ActivityAccessNewDO> queryByRegionAndStbIDToExcel(
			ActivityAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessNewDao.queryByRegionAndStbIDCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessNewDao.queryByRegionAndStbIDToExcel(query);
	}

	@Override
	public List<ActivityAccessNewDO> queryByRegionAllToExcel(
			ActivityAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessNewDao.queryqueryByRegionAllCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessNewDao.queryByRegionAllToExcel(query);
	}

	@Override
	public List<ActivityAccessNewDO> queryByRegionEveryPageUV(
			ActivityAccessQuery query) {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityAccessNewDao.queryByRegionEveryPageCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityAccessNewDao.queryByRegionEveryPageUV(query);
	}

  

	

}

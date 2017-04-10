package com.wasu.ptyw.activitylottery.core.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.core.manager.ActivityLotteryAccessManager;
import com.wasu.ptyw.activitylottery.dal.dao.ActivityLotteryAccessDAO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ChoujiangJiluVO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryAccessQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityLotteryAccessManager")
public class ActivityLotteryAccessManagerImpl implements ActivityLotteryAccessManager {
	@Resource
	private ActivityLotteryAccessDAO activityLotteryAccessDao;

	@Override
	public Long insert(ActivityLotteryAccessDO obj) throws MyException {
		try {
			activityLotteryAccessDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(ActivityLotteryAccessDO obj) throws MyException {
		try {
			return activityLotteryAccessDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return activityLotteryAccessDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public ActivityLotteryAccessDO getById(long id) throws MyException {
		try {
			return activityLotteryAccessDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<ActivityLotteryAccessDO> getByIds(List<Long> ids) throws MyException {
		try {
			return activityLotteryAccessDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}

	@Override
	public List<ActivityLotteryAccessDO> query(ActivityLotteryAccessQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = activityLotteryAccessDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			List<ActivityLotteryAccessDO> dos =activityLotteryAccessDao.query(query);
			return activityLotteryAccessDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public ActivityLotteryAccessDO queryFirst(ActivityLotteryAccessQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<ActivityLotteryAccessDO> list = activityLotteryAccessDao.query(query);
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
			return activityLotteryAccessDao.updateStatusByIds(map);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids, status);
		}
	}


	//机顶盒抽奖次数统计
	@Override
	public List<ActivityLotteryAccessDO> queryStbVisit(
			ActivityLotteryAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityLotteryAccessDao.queryStbVisitCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityLotteryAccessDao.queryStbVisit(query);
	}
	
	public List<ChoujiangJiluVO> queryStbVisit2(
			ActivityLotteryAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityLotteryAccessDao.queryStbVisitCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityLotteryAccessDao.queryStbVisit2(query);
	}

	@Override
	public List<ActivityLotteryAccessDO> queryStbVisitToExcel(
			ActivityLotteryAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityLotteryAccessDao.queryStbVisitCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityLotteryAccessDao.queryStbVisitToExcel(query);
	}

	@Override
	public List<ActivityLotteryAccessDO> queryStbVisitPV(
			ActivityLotteryAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityLotteryAccessDao.queryStbVisitCountPV(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityLotteryAccessDao.queryStbVisitPV(query);
	}
	
	@Override
	public List<ChoujiangJiluVO> queryStbVisitPV2(
			ActivityLotteryAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityLotteryAccessDao.queryStbVisitCountPV(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityLotteryAccessDao.queryStbVisitPV2(query);
	}

	@Override
	public List<ActivityLotteryAccessDO> queryStbVisitToExcelPV(
			ActivityLotteryAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityLotteryAccessDao.queryStbVisitCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityLotteryAccessDao.queryStbVisitToExcelPV(query);
	}

	@Override
	public List<ActivityLotteryAccessDO> queryStbVisitUV(
			ActivityLotteryAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityLotteryAccessDao.queryStbVisitCountUV(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityLotteryAccessDao.queryStbVisitUV(query);
	}
	
	@Override
	public List<ChoujiangJiluVO> queryStbVisitUV2(
			ActivityLotteryAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityLotteryAccessDao.queryStbVisitCountUV(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityLotteryAccessDao.queryStbVisitUV2(query);
	}

	@Override
	public List<ActivityLotteryAccessDO> queryStbVisitToExcelUV(
			ActivityLotteryAccessQuery query) throws MyException {
		if (query.getPageSize() < Integer.MAX_VALUE) {
			int count = activityLotteryAccessDao.queryStbVisitCount(query);
			if (count == 0) {
				return Lists.newArrayList();
			}
			query.setTotalItem(count);
		}
		return activityLotteryAccessDao.queryStbVisitToExcelUV(query);
	}

	

}

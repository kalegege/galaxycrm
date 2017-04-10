package com.wasu.ptyw.galaxycrm.core.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxycrm.core.manager.CrmUserManager;
import com.wasu.ptyw.galaxycrm.dal.dao.CrmUserDAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmUserQuery;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
@Service("crmUserManager")
public class CrmUserManagerImpl implements CrmUserManager {
	@Resource
	private CrmUserDAO crmUserDao;

	@Override
	public Long insert(CrmUserDO obj) throws MyException {
		try {
			crmUserDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(CrmUserDO obj) throws MyException {
		try {
			return crmUserDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return crmUserDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public CrmUserDO getById(long id) throws MyException {
		try {
			return crmUserDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<CrmUserDO> getByIds(List<Long> ids) throws MyException {
		try {
			return crmUserDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}

	@Override
	public List<CrmUserDO> query(CrmUserQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = crmUserDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return crmUserDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public CrmUserDO queryFirst(CrmUserQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<CrmUserDO> list = crmUserDao.query(query);
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
			return crmUserDao.updateStatusByIds(map);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids, status);
		}
	}

}

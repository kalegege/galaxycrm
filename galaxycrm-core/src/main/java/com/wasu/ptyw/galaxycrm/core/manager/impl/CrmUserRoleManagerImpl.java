package com.wasu.ptyw.galaxycrm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxycrm.core.manager.CrmUserRoleManager;
import com.wasu.ptyw.galaxycrm.dal.dao.CrmUserRoleDAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmUserRoleQuery;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
@Service("crmUserRoleManager")
public class CrmUserRoleManagerImpl implements CrmUserRoleManager {
	@Resource
	private CrmUserRoleDAO crmUserRoleDao;

	@Override
	public Long insert(CrmUserRoleDO obj) throws MyException {
		try {
			crmUserRoleDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(CrmUserRoleDO obj) throws MyException {
		try {
			return crmUserRoleDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return crmUserRoleDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public CrmUserRoleDO getById(long id) throws MyException {
		try {
			return crmUserRoleDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<CrmUserRoleDO> getByIds(List<Long> ids) throws MyException {
		try {
			return crmUserRoleDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}

	@Override
	public List<CrmUserRoleDO> query(CrmUserRoleQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = crmUserRoleDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return crmUserRoleDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public CrmUserRoleDO queryFirst(CrmUserRoleQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<CrmUserRoleDO> list = crmUserRoleDao.query(query);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
		return null;
	}

}

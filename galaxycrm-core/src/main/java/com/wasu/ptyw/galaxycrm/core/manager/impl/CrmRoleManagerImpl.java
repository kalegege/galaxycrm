package com.wasu.ptyw.galaxycrm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxycrm.core.manager.CrmRoleManager;
import com.wasu.ptyw.galaxycrm.dal.dao.CrmRoleDAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmRoleQuery;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
@Service("crmRoleManager")
public class CrmRoleManagerImpl implements CrmRoleManager {
	@Resource
	private CrmRoleDAO crmRoleDao;

	@Override
	public Long insert(CrmRoleDO obj) throws MyException {
		try {
			crmRoleDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(CrmRoleDO obj) throws MyException {
		try {
			return crmRoleDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return crmRoleDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public CrmRoleDO getById(long id) throws MyException {
		try {
			return crmRoleDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<CrmRoleDO> getByIds(List<Long> ids) throws MyException {
		try {
			return crmRoleDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}

	@Override
	public List<CrmRoleDO> query(CrmRoleQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = crmRoleDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return crmRoleDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public CrmRoleDO queryFirst(CrmRoleQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<CrmRoleDO> list = crmRoleDao.query(query);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
		return null;
	}

}

package com.wasu.ptyw.galaxycrm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxycrm.core.manager.CrmRolePermissionManager;
import com.wasu.ptyw.galaxycrm.dal.dao.CrmRolePermissionDAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRolePermissionDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmRolePermissionQuery;

/**
 * @author wenguang
 * @date 2015年08月14日
 */
@Service("crmRolePermissionManager")
public class CrmRolePermissionManagerImpl implements CrmRolePermissionManager {
	@Resource
	private CrmRolePermissionDAO crmRolePermissionDao;

	@Override
	public Long insert(CrmRolePermissionDO obj) throws MyException {
		try {
			crmRolePermissionDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(CrmRolePermissionDO obj) throws MyException {
		try {
			return crmRolePermissionDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return crmRolePermissionDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public CrmRolePermissionDO getById(long id) throws MyException {
		try {
			return crmRolePermissionDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<CrmRolePermissionDO> getByIds(List<Long> ids) throws MyException {
		try {
			return crmRolePermissionDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}

	@Override
	public List<CrmRolePermissionDO> query(CrmRolePermissionQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = crmRolePermissionDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return crmRolePermissionDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public CrmRolePermissionDO queryFirst(CrmRolePermissionQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<CrmRolePermissionDO> list = crmRolePermissionDao.query(query);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
		return null;
	}

	@Override
	public int deleteByRoleId(long roleId) throws MyException {
		try {
			return crmRolePermissionDao.deleteByRoleId(roleId);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, roleId);
		}
	}

}

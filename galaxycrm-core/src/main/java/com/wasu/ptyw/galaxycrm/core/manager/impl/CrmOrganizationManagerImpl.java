package com.wasu.ptyw.galaxycrm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxycrm.core.manager.CrmOrganizationManager;
import com.wasu.ptyw.galaxycrm.dal.dao.CrmOrganizationDAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmOrganizationDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmOrganizationQuery;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
@Service("crmOrganizationManager")
public class CrmOrganizationManagerImpl implements CrmOrganizationManager {
	@Resource
	private CrmOrganizationDAO crmOrganizationDao;

	@Override
	public Long insert(CrmOrganizationDO obj) throws MyException {
		try {
			crmOrganizationDao.insert(obj);
			return obj.getId();
		} catch (Exception e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int update(CrmOrganizationDO obj) throws MyException {
		try {
			return crmOrganizationDao.update(obj);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, obj);
		}
	}

	@Override
	public int deleteById(long id) throws MyException {
		try {
			return crmOrganizationDao.deleteById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public CrmOrganizationDO getById(long id) throws MyException {
		try {
			return crmOrganizationDao.getById(id);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, id);
		}
	}

	@Override
	public List<CrmOrganizationDO> getByIds(List<Long> ids) throws MyException {
		try {
			return crmOrganizationDao.getByIds(ids);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, ids);
		}
	}

	@Override
	public List<CrmOrganizationDO> query(CrmOrganizationQuery query) throws MyException {
		try {
			if (query.getPageSize() < Integer.MAX_VALUE) {
				int count = crmOrganizationDao.queryCount(query);
				if (count == 0) {
					return Lists.newArrayList();
				}
				query.setTotalItem(count);
			}
			return crmOrganizationDao.query(query);
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
	}

	@Override
	public CrmOrganizationDO queryFirst(CrmOrganizationQuery query) throws MyException {
		try {
			query.setCurrentPage(1);
			query.setPageSize(1);
			List<CrmOrganizationDO> list = crmOrganizationDao.query(query);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
		}
		return null;
	}

}

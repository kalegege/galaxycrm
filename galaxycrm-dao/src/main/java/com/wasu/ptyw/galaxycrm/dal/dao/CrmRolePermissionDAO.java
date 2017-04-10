package com.wasu.ptyw.galaxycrm.dal.dao;

import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRolePermissionDO;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
public interface CrmRolePermissionDAO extends BaseDAO<CrmRolePermissionDO> {
	int deleteByRoleId(Long roleId) throws DAOException;
}
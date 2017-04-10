package com.wasu.ptyw.galaxycrm.dal.dao;

import java.util.Map;

import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserDO;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
public interface CrmUserDAO extends BaseDAO<CrmUserDO> {

	/**
	 * 根据多个id更新状态
	 * 
	 * @param map
	 *            :int status,List ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(Map<String, Object> map) throws DAOException;

}
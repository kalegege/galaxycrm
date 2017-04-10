package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ZanCountDO;
import com.wasu.ptyw.activitylottery.dal.query.ZanCountQuery;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;





/**
 * @author jxt
 * @date 2016年07月07日
 */
public interface ZanCountDAO extends BaseDAO<ZanCountDO> {
	
	 public int updateStatusByIds(Map<String, Object> map) throws DAOException;
		
	 public List<ZanCountDO> queryAll(ZanCountQuery query);
	
}
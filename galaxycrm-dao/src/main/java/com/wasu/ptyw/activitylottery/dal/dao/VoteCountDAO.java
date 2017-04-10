package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wasu.ptyw.activitylottery.dal.dataobject.VoteCountDO;
import com.wasu.ptyw.activitylottery.dal.query.VoteCountQuery;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;



/**
 * @author jxt
 * @date 2016年07月07日
 */
public interface VoteCountDAO extends BaseDAO<VoteCountDO> {
	
	 public int updateStatusByIds(Map<String, Object> map) throws DAOException;
		
	 public List<VoteCountDO> queryAll(VoteCountQuery query);
	
}
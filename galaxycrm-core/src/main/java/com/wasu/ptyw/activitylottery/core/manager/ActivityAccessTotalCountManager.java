package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityAccessTotalCountManager {
	/**
	 * 新增
	 * 
	 * @param ActivityAccessDO
	 * @return 对象ID
	 */
	public Long insert(ActivityAccessTotalCountDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityAccessDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityAccessTotalCountDO baseDO) throws MyException;

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return 删除成功的记录数
	 */
	public int deleteById(long id) throws MyException;
	
	public int deleteByType(int type) throws MyException;
	
	public int deleteByDate(String date) throws MyException;
	
	public int deleteByQuery(ActivityAccessTotalCountQuery q) throws MyException;

	public List<ActivityAccessTotalCountVO> statistics(ActivityAccessTotalCountQuery q) throws DAOException;

}

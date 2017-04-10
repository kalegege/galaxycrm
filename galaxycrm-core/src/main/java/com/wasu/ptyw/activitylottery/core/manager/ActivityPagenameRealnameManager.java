package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPagenameRealnameDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPagenameRealnameQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityPagenameRealnameManager {
	/**
	 * 新增
	 * 
	 * @param ActivityAccessDO
	 * @return 对象ID
	 */
	public Long insert(ActivityPagenameRealnameDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityAccessDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityPagenameRealnameDO baseDO) throws MyException;

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return 删除成功的记录数
	 */
	public int deleteById(long id) throws MyException;
	
	public int deleteByQuery(ActivityPagenameRealnameQuery q) throws MyException;

	List<ActivityPagenameRealnameDO> statistics(ActivityPagenameRealnameQuery q) throws DAOException;

	public ActivityPagenameRealnameDO getById(long id) throws MyException;

}

package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityQuestionVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityQuestionQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityQuestionManager {
	/**
	 * 新增
	 * 
	 * @param ActivityAccessDO
	 * @return 对象ID
	 */
	public Long insert(ActivityQuestionDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityAccessDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityQuestionDO baseDO) throws MyException;

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return 删除成功的记录数
	 */
	public int deleteById(long id) throws MyException;
	
	public int deleteByQuery(ActivityQuestionQuery q) throws MyException;

	List<ActivityQuestionVO> statistics(ActivityQuestionQuery q) throws DAOException;

	public ActivityQuestionDO getById(long id) throws MyException;
}

package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityIntroductionDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityIntroductionQuery;

/**
 * @author quxy
 * @date 2015年10月26日
 */
public interface ActivityIntroductionManager {
	/**
	 * 新增
	 * 
	 * @param ActivityIntroductionDO
	 * @return 对象ID
	 */
	public Long insert(ActivityIntroductionDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityIntroductionDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityIntroductionDO baseDO) throws MyException;

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return 删除成功的记录数
	 */
	public int deleteById(long id) throws MyException;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return ActivityIntroductionDO
	 */
	public ActivityIntroductionDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<ActivityIntroductionDO>
	 */
	public List<ActivityIntroductionDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<ActivityIntroductionDO>
	 */
	public List<ActivityIntroductionDO> query(ActivityIntroductionQuery query) throws MyException;
	public List<ActivityIntroductionDO> queryMobile(ActivityIntroductionQuery query) throws MyException;
	
	/**
	 * 查询所有
	 * 
	 * @param query
	 * @return List<ActivityIntroductionDO>
	 */
	public List<ActivityIntroductionDO> queryAll(ActivityIntroductionQuery query) throws MyException;


	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return ActivityIntroductionDO
	 */
	public ActivityIntroductionDO queryFirst(ActivityIntroductionQuery query) throws MyException;

	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(List<Long> ids, int status) throws MyException;

}

package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPrizeDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPrizeQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityPrizeManager {
	/**
	 * 新增
	 * 
	 * @param ActivityPrizeDO
	 * @return 对象ID
	 */
	public Long insert(ActivityPrizeDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityPrizeDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityPrizeDO baseDO) throws MyException;

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
	 * @return ActivityPrizeDO
	 */
	public ActivityPrizeDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<ActivityPrizeDO>
	 */
	public List<ActivityPrizeDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<ActivityPrizeDO>
	 */
	public List<ActivityPrizeDO> query(ActivityPrizeQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return ActivityPrizeDO
	 */
	public ActivityPrizeDO queryFirst(ActivityPrizeQuery query) throws MyException;

	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(List<Long> ids, int status) throws MyException;

	public List<ActivityPrizeDO> queryToExcel(ActivityPrizeQuery query)throws MyException;

}

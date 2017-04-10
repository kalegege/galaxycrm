package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryListDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryListQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityLotteryListManager {
	/**
	 * 新增
	 * 
	 * @param ActivityLotteryListDO
	 * @return 对象ID
	 */
	public Long insert(ActivityLotteryListDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityLotteryListDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityLotteryListDO baseDO) throws MyException;

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
	 * @return ActivityLotteryListDO
	 */
	public ActivityLotteryListDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<ActivityLotteryListDO>
	 */
	public List<ActivityLotteryListDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<ActivityLotteryListDO>
	 */
	public List<ActivityLotteryListDO> query(ActivityLotteryListQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return ActivityLotteryListDO
	 */
	public ActivityLotteryListDO queryFirst(ActivityLotteryListQuery query) throws MyException;

	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(List<Long> ids, int status) throws MyException;
	public int updateStatusByPrizeCodes(List<String> prizeCodes, int status) throws MyException;

	//根据code和奖品等级查询总记录条数
	public int getCountByCondition(ActivityLotteryListQuery query);

}

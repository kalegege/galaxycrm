package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityLotteryManager {
	/**
	 * 新增
	 * 
	 * @param ActivityLotteryDO
	 * @return 对象ID
	 */
	public Long insert(ActivityLotteryDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityLotteryDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityLotteryDO baseDO) throws MyException;

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
	 * @return ActivityLotteryDO
	 */
	public ActivityLotteryDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<ActivityLotteryDO>
	 */
	public List<ActivityLotteryDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<ActivityLotteryDO>
	 */
	public List<ActivityLotteryDO> query(ActivityLotteryQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return ActivityLotteryDO
	 */
	public ActivityLotteryDO queryFirst(ActivityLotteryQuery query) throws MyException;

	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(List<Long> ids, int status) throws MyException;

	public List<ActivityLotteryDO> queryByMobile(ActivityLotteryQuery query)throws MyException;
	public List<ActivityLotteryDO> queryByRegion(ActivityLotteryQuery query)throws MyException;

	public List<ActivityLotteryDO> queryByRegionCountAll(
			ActivityLotteryQuery query)throws MyException;

	public List<ActivityLotteryDO> queryByNoPage(ActivityLotteryQuery query)throws MyException;

	public List<ActivityLotteryDO> queryByRegionToExcel(
			ActivityLotteryQuery query)throws MyException;

	public List<ActivityLotteryDO> queryByRegionCountAllToExcel(
			ActivityLotteryQuery query)throws MyException;

	

}

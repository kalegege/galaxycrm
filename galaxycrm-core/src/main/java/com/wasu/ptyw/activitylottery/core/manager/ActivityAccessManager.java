package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessEveryPageDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessTotalCountDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessEveryPageQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessTotalCountQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityAccessManager {
	/**
	 * 新增
	 * 
	 * @param ActivityAccessDO
	 * @return 对象ID
	 */
	public Long insert(ActivityAccessDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityAccessDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityAccessDO baseDO) throws MyException;

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
	 * @return ActivityAccessDO
	 */
	public ActivityAccessDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<ActivityAccessDO>
	 */
	public List<ActivityAccessDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<ActivityAccessDO>
	 */
	public List<ActivityAccessDO> query(ActivityAccessQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return ActivityAccessDO
	 */
	public ActivityAccessDO queryFirst(ActivityAccessQuery query) throws MyException;

	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(List<Long> ids, int status) throws MyException;

	//区域总访问量统计
	public List<ActivityAccessDO> queryByRegionAll(ActivityAccessQuery query)throws MyException;
    //区域stb_id访问量统计查询
	public List<ActivityAccessDO> queryByRegionAndStbID(
			ActivityAccessQuery query);

	public List<ActivityAccessDO> queryByNoPage(ActivityAccessQuery query)throws MyException;

	public List<ActivityAccessDO> queryByRegionAndStbIDToExcel(
			ActivityAccessQuery query)throws MyException;

	public List<ActivityAccessDO> queryByRegionAllToExcel(
			ActivityAccessQuery query)throws MyException;

	public List<ActivityAccessDO> queryByRegionEveryPage(
			ActivityAccessQuery query);

	public List<ActivityAccessDO> queryByRegionEveryPageUV(
			ActivityAccessQuery query);

	public List<ActivityAccessTotalCountDO> totalCount(ActivityAccessQuery query);

	public List<ActivityAccessTotalCountDO> totalCountDailyPv(ActivityAccessTotalCountQuery date);

	public List<ActivityAccessTotalCountDO> totalCountDailyUv(ActivityAccessTotalCountQuery date);

	public List<ActivityAccessTotalCountDO> todayActivity(ActivityAccessTotalCountQuery date);
	
	public List<ActivityAccessEveryPageDO> everyPageDaily(ActivityAccessEveryPageQuery date);
	
	public List<ActivityAccessEveryPageDO> everyPage(ActivityAccessQuery code);
	
}

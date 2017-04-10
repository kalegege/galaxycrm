package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessNewDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityAccessNewManager {
	/**
	 * 新增
	 * 
	 * @param ActivityAccessNewDO
	 * @return 对象ID
	 */
	public Long insert(ActivityAccessNewDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityAccessNewDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityAccessNewDO baseDO) throws MyException;

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
	 * @return ActivityAccessNewDO
	 */
	public ActivityAccessNewDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<ActivityAccessNewDO>
	 */
	public List<ActivityAccessNewDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<ActivityAccessNewDO>
	 */
	public List<ActivityAccessNewDO> query(ActivityAccessQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return ActivityAccessNewDO
	 */
	public ActivityAccessNewDO queryFirst(ActivityAccessQuery query) throws MyException;

	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(List<Long> ids, int status) throws MyException;

	//区域总访问量统计
	public List<ActivityAccessNewDO> queryByRegionAll(ActivityAccessQuery query)throws MyException;
    //区域stb_id访问量统计查询
	public List<ActivityAccessNewDO> queryByRegionAndStbID(
			ActivityAccessQuery query);

	public List<ActivityAccessNewDO> queryByNoPage(ActivityAccessQuery query)throws MyException;

	public List<ActivityAccessNewDO> queryByRegionAndStbIDToExcel(
			ActivityAccessQuery query)throws MyException;

	public List<ActivityAccessNewDO> queryByRegionAllToExcel(
			ActivityAccessQuery query)throws MyException;

	public List<ActivityAccessNewDO> queryByRegionEveryPage(
			ActivityAccessQuery query);

	public List<ActivityAccessNewDO> queryByRegionEveryPageUV(
			ActivityAccessQuery query);

}

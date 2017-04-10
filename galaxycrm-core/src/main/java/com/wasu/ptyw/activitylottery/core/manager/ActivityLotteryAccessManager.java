package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ChoujiangJiluVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryAccessQuery;
import com.wasu.ptyw.galaxy.common.exception.MyException;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityLotteryAccessManager {
	/**
	 * 新增
	 * 
	 * @param ActivityLotteryAccessDO
	 * @return 对象ID
	 */
	public Long insert(ActivityLotteryAccessDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param ActivityLotteryAccessDO
	 * @return 更新成功的记录数
	 */
	public int update(ActivityLotteryAccessDO baseDO) throws MyException;

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
	 * @return ActivityLotteryAccessDO
	 */
	public ActivityLotteryAccessDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<ActivityLotteryAccessDO>
	 */
	public List<ActivityLotteryAccessDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<ActivityLotteryAccessDO>
	 */
	public List<ActivityLotteryAccessDO> query(ActivityLotteryAccessQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return ActivityLotteryAccessDO
	 */
	public ActivityLotteryAccessDO queryFirst(ActivityLotteryAccessQuery query) throws MyException;

	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(List<Long> ids, int status) throws MyException;

	// 机顶盒抽奖次数统计
	public List<ActivityLotteryAccessDO> queryStbVisit(ActivityLotteryAccessQuery query) throws MyException;

	public List<ChoujiangJiluVO> queryStbVisit2(ActivityLotteryAccessQuery query) throws MyException;

	public List<ActivityLotteryAccessDO> queryStbVisitToExcel(ActivityLotteryAccessQuery query) throws MyException;

	public List<ActivityLotteryAccessDO> queryStbVisitPV(ActivityLotteryAccessQuery query) throws MyException;

	public List<ChoujiangJiluVO> queryStbVisitPV2(ActivityLotteryAccessQuery query) throws MyException;

	public List<ActivityLotteryAccessDO> queryStbVisitToExcelPV(ActivityLotteryAccessQuery query) throws MyException;

	public List<ActivityLotteryAccessDO> queryStbVisitUV(ActivityLotteryAccessQuery query) throws MyException;

	public List<ChoujiangJiluVO> queryStbVisitUV2(ActivityLotteryAccessQuery query) throws MyException;

	public List<ActivityLotteryAccessDO> queryStbVisitToExcelUV(ActivityLotteryAccessQuery query) throws MyException;

}

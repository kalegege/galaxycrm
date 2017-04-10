package com.wasu.ptyw.activitylottery.dal.dao;

import java.util.List;
import java.util.Map;

import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ChoujiangJiluVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryAccessQuery;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxy.dal.dao.BaseDAO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
public interface ActivityLotteryAccessDAO extends BaseDAO<ActivityLotteryAccessDO> {

	/**
	 * 根据多个id更新状态
	 * 
	 * @param map
	 *            :int status,List ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(Map<String, Object> map) throws DAOException;

	// 机顶盒抽奖次数统计
	public int queryStbVisitCount(ActivityLotteryAccessQuery query);

	public List<ActivityLotteryAccessDO> queryStbVisit(ActivityLotteryAccessQuery query);

	public List<ChoujiangJiluVO> queryStbVisit2(ActivityLotteryAccessQuery query);

	public List<ActivityLotteryAccessDO> queryStbVisitToExcel(ActivityLotteryAccessQuery query);

	public int queryStbVisitCountPV(ActivityLotteryAccessQuery query);

	public List<ActivityLotteryAccessDO> queryStbVisitPV(ActivityLotteryAccessQuery query);

	public List<ChoujiangJiluVO> queryStbVisitPV2(ActivityLotteryAccessQuery query);

	public List<ActivityLotteryAccessDO> queryStbVisitToExcelPV(ActivityLotteryAccessQuery query);

	public int queryStbVisitCountUV(ActivityLotteryAccessQuery query);

	public List<ActivityLotteryAccessDO> queryStbVisitUV(ActivityLotteryAccessQuery query);

	public List<ChoujiangJiluVO> queryStbVisitUV2(ActivityLotteryAccessQuery query);

	public List<ActivityLotteryAccessDO> queryStbVisitToExcelUV(ActivityLotteryAccessQuery query);

}
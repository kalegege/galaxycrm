package com.wasu.ptyw.activitylottery.core.ao;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.common.util.NumUtil;
import com.wasu.ptyw.galaxy.core.ao.BaseAO;
import com.wasu.ptyw.activitylottery.core.manager.ActivityLotteryListManager;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryListDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPrizeDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryListQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityLotteryListAO")
@Slf4j
public class ActivityLotteryListAO extends BaseAO {
	@Resource
	private ActivityLotteryListManager activityLotteryListManager;

	/**
	 * 新增
	 * 
	 * @param ActivityLotteryListDO
	 * @return 对象ID
	 */
	public Result<Long> save(ActivityLotteryListDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = activityLotteryListManager.insert(obj);
			result.setValue(id);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("save error," + obj, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

	/**
	 * 更新
	 * 
	 * @param ActivityLotteryListDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(ActivityLotteryListDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = activityLotteryListManager.update(obj);
			if (num > 0) {
				result.setValue(num);
				result.setSuccess(true);
			} else {
				setErrorMessage(result, ResultCode.DB_DATA_NOEXIST);
			}
		} catch (Exception e) {
			log.error("update error," + obj, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return 删除成功的记录数
	 */
	public Result<Integer> deleteById(Long id) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (!NumUtil.isGreaterZero(id)) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			int num = activityLotteryListManager.deleteById(id);
			if (num > 0) {
				result.setValue(num);
				result.setSuccess(true);
			} else {
				setErrorMessage(result, ResultCode.DB_DATA_NOEXIST);
			}
		} catch (Exception e) {
			log.error("deleteById error," + id, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return ActivityLotteryListDO
	 */
	public Result<ActivityLotteryListDO> getById(Long id) {
		Result<ActivityLotteryListDO> result = new Result<ActivityLotteryListDO>(false);
		try {
			if (!NumUtil.isGreaterZero(id)) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			ActivityLotteryListDO obj = activityLotteryListManager.getById(id);
			if (obj != null) {
				result.setValue(obj);
				result.setSuccess(true);
			} else {
				setErrorMessage(result, ResultCode.DB_DATA_NOEXIST);
			}
		} catch (Exception e) {
			log.error("getById error,id=" + id, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<ActivityLotteryListDO>
	 */
	public Result<List<ActivityLotteryListDO>> getByIds(List<Long> ids) {
		Result<List<ActivityLotteryListDO>> result = new Result<List<ActivityLotteryListDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryListDO> list = activityLotteryListManager.getByIds(ids);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("getByIds error," + ids, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<ActivityLotteryListDO>
	 */
	public Result<List<ActivityLotteryListDO>> query(ActivityLotteryListQuery query) {
		Result<List<ActivityLotteryListDO>> result = new Result<List<ActivityLotteryListDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryListDO> list = activityLotteryListManager.query(query);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("query error," + query, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}
	
	//根据活动编号和奖品等级查询查询奖品的总条数
	public int getCountByCondition(ActivityLotteryListQuery query) {
		int totalcount=activityLotteryListManager.getCountByCondition(query);
		
		return totalcount;
	}
	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public Result<Integer> updateStatusByIds(List<Long> ids, int status) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			int num = activityLotteryListManager.updateStatusByIds(ids, status);
			result.setValue(num);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("updateStatusByIds error,ids=" + ids + ", status=" + status, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}
	
	
	/**
	 * 根据多个Prizecode更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public Result<Integer> updateStatusByPrizeCodes(List<String> prizeCodes, int status) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (prizeCodes == null || prizeCodes.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			int num = activityLotteryListManager.updateStatusByPrizeCodes(prizeCodes, status);
			result.setValue(num);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("updateStatusByPrizeCodes error,prizeCodes=" + prizeCodes + ", status=" + status, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}
	

	

}

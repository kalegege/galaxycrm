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
import com.wasu.ptyw.activitylottery.core.manager.ActivityPrizeManager;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityPrizeDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityPrizeQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityPrizeAO")
@Slf4j
public class ActivityPrizeAO extends BaseAO {
	@Resource
	private ActivityPrizeManager activityPrizeManager;

	/**
	 * 新增
	 * 
	 * @param ActivityPrizeDO
	 * @return 对象ID
	 */
	public Result<Long> save(ActivityPrizeDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = activityPrizeManager.insert(obj);
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
	 * @param ActivityPrizeDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(ActivityPrizeDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = activityPrizeManager.update(obj);
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
			int num = activityPrizeManager.deleteById(id);
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
	 * @return ActivityPrizeDO
	 */
	public Result<ActivityPrizeDO> getById(Long id) {
		Result<ActivityPrizeDO> result = new Result<ActivityPrizeDO>(false);
		try {
			if (!NumUtil.isGreaterZero(id)) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			ActivityPrizeDO obj = activityPrizeManager.getById(id);
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
	 * @return List<ActivityPrizeDO>
	 */
	public Result<List<ActivityPrizeDO>> getByIds(List<Long> ids) {
		Result<List<ActivityPrizeDO>> result = new Result<List<ActivityPrizeDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityPrizeDO> list = activityPrizeManager.getByIds(ids);
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
	 * @return List<ActivityPrizeDO>
	 */
	public Result<List<ActivityPrizeDO>> query(ActivityPrizeQuery query) {
		Result<List<ActivityPrizeDO>> result = new Result<List<ActivityPrizeDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityPrizeDO> list = activityPrizeManager.query(query);
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
			int num = activityPrizeManager.updateStatusByIds(ids, status);
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

	public Result<List<ActivityPrizeDO>> queryToExcel(ActivityPrizeQuery query) {
		Result<List<ActivityPrizeDO>> result = new Result<List<ActivityPrizeDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityPrizeDO> list = activityPrizeManager.queryToExcel(query);
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

}

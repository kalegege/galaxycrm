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
import com.wasu.ptyw.activitylottery.core.manager.ActivityLotteryAccessManager;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryAccessDO;
import com.wasu.ptyw.activitylottery.dal.dataobject.ChoujiangJiluVO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryAccessQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityLotteryAccessAO")
@Slf4j
public class ActivityLotteryAccessAO extends BaseAO {
	@Resource
	private ActivityLotteryAccessManager activityLotteryAccessManager;

	/**
	 * 新增
	 * 
	 * @param ActivityLotteryAccessDO
	 * @return 对象ID
	 */
	public Result<Long> save(ActivityLotteryAccessDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = activityLotteryAccessManager.insert(obj);
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
	 * @param ActivityLotteryAccessDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(ActivityLotteryAccessDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = activityLotteryAccessManager.update(obj);
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
			int num = activityLotteryAccessManager.deleteById(id);
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
	 * @return ActivityLotteryAccessDO
	 */
	public Result<ActivityLotteryAccessDO> getById(Long id) {
		Result<ActivityLotteryAccessDO> result = new Result<ActivityLotteryAccessDO>(false);
		try {
			if (!NumUtil.isGreaterZero(id)) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			ActivityLotteryAccessDO obj = activityLotteryAccessManager.getById(id);
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
	 * @return List<ActivityLotteryAccessDO>
	 */
	public Result<List<ActivityLotteryAccessDO>> getByIds(List<Long> ids) {
		Result<List<ActivityLotteryAccessDO>> result = new Result<List<ActivityLotteryAccessDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryAccessDO> list = activityLotteryAccessManager.getByIds(ids);
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
	 * @return List<ActivityLotteryAccessDO>
	 */
	public Result<List<ActivityLotteryAccessDO>> query(ActivityLotteryAccessQuery query) {
		Result<List<ActivityLotteryAccessDO>> result = new Result<List<ActivityLotteryAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryAccessDO> list = activityLotteryAccessManager.query(query);
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
			int num = activityLotteryAccessManager.updateStatusByIds(ids, status);
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

	public Result<List<ActivityLotteryAccessDO>> queryStbVisit(
			ActivityLotteryAccessQuery query) {
		Result<List<ActivityLotteryAccessDO>> result = new Result<List<ActivityLotteryAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryAccessDO> list = activityLotteryAccessManager.queryStbVisit(query);
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
	
	public Result<List<ChoujiangJiluVO>> queryStbVisit2(
			ActivityLotteryAccessQuery query) {
		Result<List<ChoujiangJiluVO>> result = new Result<List<ChoujiangJiluVO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ChoujiangJiluVO> list = activityLotteryAccessManager.queryStbVisit2(query);
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

	public Result<List<ActivityLotteryAccessDO>> queryStbVisitToExcel(
			ActivityLotteryAccessQuery query) {
		Result<List<ActivityLotteryAccessDO>> result = new Result<List<ActivityLotteryAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryAccessDO> list = activityLotteryAccessManager.queryStbVisitToExcel(query);
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

	public Result<List<ActivityLotteryAccessDO>> queryStbVisitPV(
			ActivityLotteryAccessQuery query) {
		Result<List<ActivityLotteryAccessDO>> result = new Result<List<ActivityLotteryAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryAccessDO> list = activityLotteryAccessManager.queryStbVisitPV(query);
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
	
	public Result<List<ChoujiangJiluVO>> queryStbVisitPV2(
			ActivityLotteryAccessQuery query) {
		Result<List<ChoujiangJiluVO>> result = new Result<List<ChoujiangJiluVO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ChoujiangJiluVO> list = activityLotteryAccessManager.queryStbVisitPV2(query);
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

	public Result<List<ActivityLotteryAccessDO>> queryStbVisitToExcelPV(
			ActivityLotteryAccessQuery query) {
		Result<List<ActivityLotteryAccessDO>> result = new Result<List<ActivityLotteryAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryAccessDO> list = activityLotteryAccessManager.queryStbVisitToExcelPV(query);
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

	public Result<List<ActivityLotteryAccessDO>> queryStbVisitUV(
			ActivityLotteryAccessQuery query) {
		Result<List<ActivityLotteryAccessDO>> result = new Result<List<ActivityLotteryAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryAccessDO> list = activityLotteryAccessManager.queryStbVisitUV(query);
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
	
	public Result<List<ChoujiangJiluVO>> queryStbVisitUV2(
			ActivityLotteryAccessQuery query) {
		Result<List<ChoujiangJiluVO>> result = new Result<List<ChoujiangJiluVO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ChoujiangJiluVO> list = activityLotteryAccessManager.queryStbVisitUV2(query);
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

	public Result<List<ActivityLotteryAccessDO>> queryStbVisitToExcelUV(
			ActivityLotteryAccessQuery query) {
		Result<List<ActivityLotteryAccessDO>> result = new Result<List<ActivityLotteryAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryAccessDO> list = activityLotteryAccessManager.queryStbVisitToExcelUV(query);
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

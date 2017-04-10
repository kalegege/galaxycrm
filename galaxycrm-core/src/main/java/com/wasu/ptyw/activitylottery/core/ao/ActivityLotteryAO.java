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
import com.wasu.ptyw.activitylottery.core.manager.ActivityLotteryManager;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityLotteryDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityLotteryQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityLotteryAO")
@Slf4j
public class ActivityLotteryAO extends BaseAO {
	@Resource
	private ActivityLotteryManager activityLotteryManager;

	/**
	 * 新增
	 * 
	 * @param ActivityLotteryDO
	 * @return 对象ID
	 */
	public Result<Long> save(ActivityLotteryDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = activityLotteryManager.insert(obj);
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
	 * @param ActivityLotteryDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(ActivityLotteryDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = activityLotteryManager.update(obj);
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
			int num = activityLotteryManager.deleteById(id);
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
	 * @return ActivityLotteryDO
	 */
	public Result<ActivityLotteryDO> getById(Long id) {
		Result<ActivityLotteryDO> result = new Result<ActivityLotteryDO>(false);
		try {
			if (!NumUtil.isGreaterZero(id)) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			ActivityLotteryDO obj = activityLotteryManager.getById(id);
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
	 * @return List<ActivityLotteryDO>
	 */
	public Result<List<ActivityLotteryDO>> getByIds(List<Long> ids) {
		Result<List<ActivityLotteryDO>> result = new Result<List<ActivityLotteryDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryDO> list = activityLotteryManager.getByIds(ids);
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
	
	
	//导出excel
	public Result<List<ActivityLotteryDO>> queryByNoPage(
			ActivityLotteryQuery query) {
		Result<List<ActivityLotteryDO>> result = new Result<List<ActivityLotteryDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryDO> list = activityLotteryManager.queryByNoPage(query);
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
	 * 分页查询
	 * 
	 * @param query
	 * @return List<ActivityLotteryDO>
	 */
	public Result<List<ActivityLotteryDO>> query(ActivityLotteryQuery query) {
		Result<List<ActivityLotteryDO>> result = new Result<List<ActivityLotteryDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryDO> list = activityLotteryManager.query(query);
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
	//分地区统计查询1,2,3,4等奖中奖情况
		public Result<List<ActivityLotteryDO>> queryByRegion(
				ActivityLotteryQuery query) {
			
			Result<List<ActivityLotteryDO>> result = new Result<List<ActivityLotteryDO>>(false);
			try {
				if (query == null) {
					return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
				}
				List<ActivityLotteryDO> list = activityLotteryManager.queryByRegion(query);
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
		
		public Result<List<ActivityLotteryDO>> queryByRegionToExcel(
				ActivityLotteryQuery query) {
			Result<List<ActivityLotteryDO>> result = new Result<List<ActivityLotteryDO>>(false);
			try {
				if (query == null) {
					return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
				}
				List<ActivityLotteryDO> list = activityLotteryManager.queryByRegionToExcel(query);
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
			int num = activityLotteryManager.updateStatusByIds(ids, status);
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

	public Result<List<ActivityLotteryDO>> queryByRegionCountAll(
			ActivityLotteryQuery query) {
		Result<List<ActivityLotteryDO>> result = new Result<List<ActivityLotteryDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryDO> list = activityLotteryManager.queryByRegionCountAll(query);
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

	public Result<List<ActivityLotteryDO>> queryByRegionCountAllToExcel(
			ActivityLotteryQuery query) {
		Result<List<ActivityLotteryDO>> result = new Result<List<ActivityLotteryDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryDO> list = activityLotteryManager.queryByRegionCountAllToExcel(query);
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

	public Result<List<ActivityLotteryDO>> queryByMobile(ActivityLotteryQuery query){
		
		Result<List<ActivityLotteryDO>> result = new Result<List<ActivityLotteryDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityLotteryDO> list = activityLotteryManager.queryByMobile(query);
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

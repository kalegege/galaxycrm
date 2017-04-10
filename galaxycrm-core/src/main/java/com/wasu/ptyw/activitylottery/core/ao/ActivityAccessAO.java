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
import com.wasu.ptyw.activitylottery.core.manager.ActivityAccessManager;
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
@Service("activityAccessAO")
@Slf4j
public class ActivityAccessAO extends BaseAO {
	@Resource
	private ActivityAccessManager activityAccessManager;

	/**
	 * 新增
	 * 
	 * @param ActivityAccessDO
	 * @return 对象ID
	 */
	public Result<Long> save(ActivityAccessDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = activityAccessManager.insert(obj);
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
	 * @param ActivityAccessDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(ActivityAccessDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = activityAccessManager.update(obj);
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
			int num = activityAccessManager.deleteById(id);
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
	 * @return ActivityAccessDO
	 */
	public Result<ActivityAccessDO> getById(Long id) {
		Result<ActivityAccessDO> result = new Result<ActivityAccessDO>(false);
		try {
			if (!NumUtil.isGreaterZero(id)) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			ActivityAccessDO obj = activityAccessManager.getById(id);
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
	 * @return List<ActivityAccessDO>
	 */
	public Result<List<ActivityAccessDO>> getByIds(List<Long> ids) {
		Result<List<ActivityAccessDO>> result = new Result<List<ActivityAccessDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessDO> list = activityAccessManager.getByIds(ids);
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

	public Result<List<ActivityAccessDO>> queryByNoPage(
		ActivityAccessQuery query) {
		Result<List<ActivityAccessDO>> result = new Result<List<ActivityAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessDO> list = activityAccessManager.queryByNoPage(query);
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
	 * @return List<ActivityAccessDO>
	 */
	public Result<List<ActivityAccessDO>> query(ActivityAccessQuery query) {
		Result<List<ActivityAccessDO>> result = new Result<List<ActivityAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessDO> list = activityAccessManager.query(query);
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
			int num = activityAccessManager.updateStatusByIds(ids, status);
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

	//区域访问量统计
	public Result<List<ActivityAccessDO>> queryByRegionAll(
			ActivityAccessQuery query) {
		
		Result<List<ActivityAccessDO>> result = new Result<List<ActivityAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessDO> list = activityAccessManager.queryByRegionAll(query);
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
	//每一个页面pv,uv统计
	public Result<List<ActivityAccessDO>> queryByRegionEveryPage(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessDO>> result = new Result<List<ActivityAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessDO> list = activityAccessManager.queryByRegionEveryPage(query);
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

	
	public Result<List<ActivityAccessDO>> queryByRegionEveryPageUV(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessDO>> result = new Result<List<ActivityAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessDO> list = activityAccessManager.queryByRegionEveryPageUV(query);
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
   //区域机顶盒数量访问统计
	public Result<List<ActivityAccessDO>> queryByRegionAndStbID(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessDO>> result = new Result<List<ActivityAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessDO> list = activityAccessManager.queryByRegionAndStbID(query);
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

	public Result<List<ActivityAccessDO>> queryByRegionAndStbIDToExcel(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessDO>> result = new Result<List<ActivityAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessDO> list = activityAccessManager.queryByRegionAndStbIDToExcel(query);
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

	public Result<List<ActivityAccessDO>> queryByRegionAllToExcel(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessDO>> result = new Result<List<ActivityAccessDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessDO> list = activityAccessManager.queryByRegionAllToExcel(query);
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

	
	public Result<List<ActivityAccessTotalCountDO>> totalCount(ActivityAccessQuery query) {
		Result<List<ActivityAccessTotalCountDO>> result = new Result<List<ActivityAccessTotalCountDO>>(false);
		try {
			List<ActivityAccessTotalCountDO> list = activityAccessManager.totalCount(query);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}
	
	
	public Result<List<ActivityAccessTotalCountDO>> totalCountDailyPv(ActivityAccessTotalCountQuery date) {
		Result<List<ActivityAccessTotalCountDO>> result = new Result<List<ActivityAccessTotalCountDO>>(false);
		try {
			List<ActivityAccessTotalCountDO> list = activityAccessManager.totalCountDailyPv(date);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}
	
	
	public Result<List<ActivityAccessTotalCountDO>> totalCountDailyUv(ActivityAccessTotalCountQuery date) {
		Result<List<ActivityAccessTotalCountDO>> result = new Result<List<ActivityAccessTotalCountDO>>(false);
		try {
			List<ActivityAccessTotalCountDO> list = activityAccessManager.totalCountDailyUv(date);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

	
	public Result<List<ActivityAccessTotalCountDO>> todayActivity(ActivityAccessTotalCountQuery date) {
		Result<List<ActivityAccessTotalCountDO>> result = new Result<List<ActivityAccessTotalCountDO>>(false);
		try {
			List<ActivityAccessTotalCountDO> list = activityAccessManager.todayActivity(date);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}
	
	
	public Result<List<ActivityAccessEveryPageDO>> everyPageDaily(ActivityAccessEveryPageQuery date) {
		Result<List<ActivityAccessEveryPageDO>> result = new Result<List<ActivityAccessEveryPageDO>>(false);
		try {
			List<ActivityAccessEveryPageDO> list = activityAccessManager.everyPageDaily(date);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}
	
	
	public Result<List<ActivityAccessEveryPageDO>> everyPage(ActivityAccessQuery code) {
		Result<List<ActivityAccessEveryPageDO>> result = new Result<List<ActivityAccessEveryPageDO>>(false);
		try {
			List<ActivityAccessEveryPageDO> list = activityAccessManager.everyPage(code);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

}

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
import com.wasu.ptyw.activitylottery.core.manager.ActivityAccessNewManager;
import com.wasu.ptyw.activitylottery.dal.dataobject.ActivityAccessNewDO;
import com.wasu.ptyw.activitylottery.dal.query.ActivityAccessQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Service("activityAccessNewAO")
@Slf4j
public class ActivityAccessNewAO extends BaseAO {
	@Resource
	private ActivityAccessNewManager activityAccessNewManager;

	/**
	 * 新增
	 * 
	 * @param ActivityAccessNewDO
	 * @return 对象ID
	 */
	public Result<Long> save(ActivityAccessNewDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = activityAccessNewManager.insert(obj);
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
	 * @param ActivityAccessNewDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(ActivityAccessNewDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = activityAccessNewManager.update(obj);
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
			int num = activityAccessNewManager.deleteById(id);
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
	 * @return ActivityAccessNewDO
	 */
	public Result<ActivityAccessNewDO> getById(Long id) {
		Result<ActivityAccessNewDO> result = new Result<ActivityAccessNewDO>(false);
		try {
			if (!NumUtil.isGreaterZero(id)) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			ActivityAccessNewDO obj = activityAccessNewManager.getById(id);
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
	 * @return List<ActivityAccessNewDO>
	 */
	public Result<List<ActivityAccessNewDO>> getByIds(List<Long> ids) {
		Result<List<ActivityAccessNewDO>> result = new Result<List<ActivityAccessNewDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessNewDO> list = activityAccessNewManager.getByIds(ids);
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

	public Result<List<ActivityAccessNewDO>> queryByNoPage(
		ActivityAccessQuery query) {
		Result<List<ActivityAccessNewDO>> result = new Result<List<ActivityAccessNewDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessNewDO> list = activityAccessNewManager.queryByNoPage(query);
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
	 * @return List<ActivityAccessNewDO>
	 */
	public Result<List<ActivityAccessNewDO>> query(ActivityAccessQuery query) {
		Result<List<ActivityAccessNewDO>> result = new Result<List<ActivityAccessNewDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessNewDO> list = activityAccessNewManager.query(query);
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
			int num = activityAccessNewManager.updateStatusByIds(ids, status);
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
	public Result<List<ActivityAccessNewDO>> queryByRegionAll(
			ActivityAccessQuery query) {
		
		Result<List<ActivityAccessNewDO>> result = new Result<List<ActivityAccessNewDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessNewDO> list = activityAccessNewManager.queryByRegionAll(query);
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
	public Result<List<ActivityAccessNewDO>> queryByRegionEveryPage(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessNewDO>> result = new Result<List<ActivityAccessNewDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessNewDO> list = activityAccessNewManager.queryByRegionEveryPage(query);
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

	
	public Result<List<ActivityAccessNewDO>> queryByRegionEveryPageUV(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessNewDO>> result = new Result<List<ActivityAccessNewDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessNewDO> list = activityAccessNewManager.queryByRegionEveryPageUV(query);
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
	public Result<List<ActivityAccessNewDO>> queryByRegionAndStbID(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessNewDO>> result = new Result<List<ActivityAccessNewDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessNewDO> list = activityAccessNewManager.queryByRegionAndStbID(query);
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

	public Result<List<ActivityAccessNewDO>> queryByRegionAndStbIDToExcel(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessNewDO>> result = new Result<List<ActivityAccessNewDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessNewDO> list = activityAccessNewManager.queryByRegionAndStbIDToExcel(query);
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

	public Result<List<ActivityAccessNewDO>> queryByRegionAllToExcel(
			ActivityAccessQuery query) {
		Result<List<ActivityAccessNewDO>> result = new Result<List<ActivityAccessNewDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<ActivityAccessNewDO> list = activityAccessNewManager.queryByRegionAllToExcel(query);
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

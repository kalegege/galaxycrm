package com.wasu.ptyw.galaxycrm.core.ao;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.core.ao.BaseAO;
import com.wasu.ptyw.galaxycrm.core.manager.CrmRolePermissionManager;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRolePermissionDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmRolePermissionQuery;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Service("crmRolePermissionAO")
@Slf4j
public class CrmRolePermissionAO extends BaseAO {
	@Resource
	private CrmRolePermissionManager crmRolePermissionManager;

	/**
	 * 新增
	 * 
	 * @param CrmRolePermissionDO
	 * @return 对象ID
	 */
	public Result<Long> save(CrmRolePermissionDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = crmRolePermissionManager.insert(obj);
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
	 * @param CrmRolePermissionDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(CrmRolePermissionDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = crmRolePermissionManager.update(obj);
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
	public Result<Integer> deleteById(long id) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (id <= 0) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			int num = crmRolePermissionManager.deleteById(id);
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
	 * @return CrmRolePermissionDO
	 */
	public Result<CrmRolePermissionDO> getById(long id) {
		Result<CrmRolePermissionDO> result = new Result<CrmRolePermissionDO>(false);
		try {
			if (id <= 0) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			CrmRolePermissionDO obj = crmRolePermissionManager.getById(id);
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
	 * @return List<CrmRolePermissionDO>
	 */
	public Result<List<CrmRolePermissionDO>> getByIds(List<Long> ids) {
		Result<List<CrmRolePermissionDO>> result = new Result<List<CrmRolePermissionDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmRolePermissionDO> list = crmRolePermissionManager.getByIds(ids);
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
	 * @return List<CrmRolePermissionDO>
	 */
	public Result<List<CrmRolePermissionDO>> query(CrmRolePermissionQuery query) {
		Result<List<CrmRolePermissionDO>> result = new Result<List<CrmRolePermissionDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmRolePermissionDO> list = crmRolePermissionManager.query(query);
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

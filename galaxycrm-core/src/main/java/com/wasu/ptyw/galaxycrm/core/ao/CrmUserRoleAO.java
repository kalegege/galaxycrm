package com.wasu.ptyw.galaxycrm.core.ao;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.core.ao.BaseAO;
import com.wasu.ptyw.galaxycrm.core.manager.CrmUserRoleManager;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmUserRoleQuery;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Service("crmUserRoleAO")
@Slf4j
public class CrmUserRoleAO extends BaseAO {
	@Resource
	private CrmUserRoleManager crmUserRoleManager;

	/**
	 * 根据用户id获取权限信息
	 * 
	 * @param query
	 * @return List<CrmUserRoleDO>
	 */
	public Result<List<CrmUserRoleDO>> getByUid(long uid) {
		Result<List<CrmUserRoleDO>> result = new Result<List<CrmUserRoleDO>>(false);
		try {
			if (uid < 1) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			CrmUserRoleQuery query = new CrmUserRoleQuery();
			query.setUserId(uid);
			query.setPageSize(Integer.MAX_VALUE);
			List<CrmUserRoleDO> list = crmUserRoleManager.query(query);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("getByUid error, uid=" + uid, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

	/**
	 * 新增
	 * 
	 * @param CrmUserRoleDO
	 * @return 对象ID
	 */
	public Result<Long> save(CrmUserRoleDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = crmUserRoleManager.insert(obj);
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
	 * @param CrmUserRoleDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(CrmUserRoleDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = crmUserRoleManager.update(obj);
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
			int num = crmUserRoleManager.deleteById(id);
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
	 * @return CrmUserRoleDO
	 */
	public Result<CrmUserRoleDO> getById(long id) {
		Result<CrmUserRoleDO> result = new Result<CrmUserRoleDO>(false);
		try {
			if (id <= 0) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			CrmUserRoleDO obj = crmUserRoleManager.getById(id);
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
	 * @return List<CrmUserRoleDO>
	 */
	public Result<List<CrmUserRoleDO>> getByIds(List<Long> ids) {
		Result<List<CrmUserRoleDO>> result = new Result<List<CrmUserRoleDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmUserRoleDO> list = crmUserRoleManager.getByIds(ids);
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
	 * @return List<CrmUserRoleDO>
	 */
	public Result<List<CrmUserRoleDO>> query(CrmUserRoleQuery query) {
		Result<List<CrmUserRoleDO>> result = new Result<List<CrmUserRoleDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmUserRoleDO> list = crmUserRoleManager.query(query);
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

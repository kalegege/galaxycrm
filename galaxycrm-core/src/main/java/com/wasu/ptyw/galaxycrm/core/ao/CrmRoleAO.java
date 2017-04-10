package com.wasu.ptyw.galaxycrm.core.ao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.core.ao.BaseAO;
import com.wasu.ptyw.galaxycrm.core.cache.CrmRolePermissionCache;
import com.wasu.ptyw.galaxycrm.core.manager.CrmRoleManager;
import com.wasu.ptyw.galaxycrm.core.manager.CrmRolePermissionManager;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRoleDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRolePermissionDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmRolePermissionQuery;
import com.wasu.ptyw.galaxycrm.dal.query.CrmRoleQuery;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Service("crmRoleAO")
@Slf4j
public class CrmRoleAO extends BaseAO {
	@Resource
	private CrmRoleManager crmRoleManager;

	@Resource
	private CrmRolePermissionManager crmRolePermissionManager;

	@Resource
	CrmRolePermissionCache crmRolePermissionCache;

	/**
	 * 新增
	 * 
	 * @param CrmRoleDO
	 * @return 对象ID
	 */
	public Result<Long> save(CrmRoleDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = crmRoleManager.insert(obj);
			List<String> list = obj.getPermissionList();
			for (String s : list) {
				CrmRolePermissionDO item = new CrmRolePermissionDO();
				item.setRoleId(id);
				item.setPermission(s);
				crmRolePermissionManager.insert(item);
			}
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
	 * @param CrmRoleDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(CrmRoleDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = crmRoleManager.update(obj);
			if (num > 0) {
				result.setValue(num);
				result.setSuccess(true);
				updatePermission(obj);
				crmRolePermissionCache.clear(obj.getId());
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
			int num = crmRoleManager.deleteById(id);
			if (num > 0) {
				result.setValue(num);
				result.setSuccess(true);
				crmRolePermissionManager.deleteByRoleId(id);
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
	 * @return CrmRoleDO
	 */
	public Result<CrmRoleDO> getById(long id) {
		Result<CrmRoleDO> result = new Result<CrmRoleDO>(false);
		try {
			if (id <= 0) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			CrmRoleDO obj = crmRoleManager.getById(id);
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
	 * @return List<CrmRoleDO>
	 */
	public Result<List<CrmRoleDO>> getByIds(List<Long> ids) {
		Result<List<CrmRoleDO>> result = new Result<List<CrmRoleDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmRoleDO> list = crmRoleManager.getByIds(ids);
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
	 * @return List<CrmRoleDO>
	 */
	public Result<List<CrmRoleDO>> query(CrmRoleQuery query) {
		Result<List<CrmRoleDO>> result = new Result<List<CrmRoleDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmRoleDO> list = crmRoleManager.query(query);
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
	 * 查询所有
	 * 
	 * @param query
	 * @return List<CrmRoleDO>
	 */
	public Result<List<CrmRoleDO>> queryAll() {
		Result<List<CrmRoleDO>> result = new Result<List<CrmRoleDO>>(false);
		try {
			CrmRoleQuery query = new CrmRoleQuery();
			query.setPageSize(Integer.MAX_VALUE);
			List<CrmRoleDO> list = crmRoleManager.query(query);
			result.setValue(list);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("queryAll error", e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

	private void updatePermission(CrmRoleDO obj) throws MyException {
		CrmRolePermissionQuery query = new CrmRolePermissionQuery();
		query.setRoleId(obj.getId());
		query.setPageSize(Integer.MAX_VALUE);
		List<CrmRolePermissionDO> dbList = crmRolePermissionManager.query(query);

		Map<String, CrmRolePermissionDO> pMap = Maps.uniqueIndex(dbList, new Function<CrmRolePermissionDO, String>() {
			@Override
			public String apply(CrmRolePermissionDO input) {
				return input.getPermission();
			}
		});

		Set<String> existSet = Sets.newHashSet();

		List<String> list = obj.getPermissionList();
		for (String s : list) {
			if (pMap.containsKey(s)) {
				existSet.add(s);
				continue;
			}
			CrmRolePermissionDO item = new CrmRolePermissionDO();
			item.setRoleId(obj.getId());
			item.setPermission(s);
			crmRolePermissionManager.insert(item);
		}

		// 删除不存在的
		for (Entry<String, CrmRolePermissionDO> e : pMap.entrySet()) {
			if (existSet.contains(e.getKey()))
				continue;
			crmRolePermissionManager.deleteById(e.getValue().getId());
		}
	}

}

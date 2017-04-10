package com.wasu.ptyw.galaxycrm.core.ao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.common.util.NumUtil;
import com.wasu.ptyw.galaxy.core.ao.BaseAO;
import com.wasu.ptyw.galaxycrm.core.manager.CrmOrganizationManager;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmOrganizationDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmOrganizationQuery;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Service("crmOrganizationAO")
@Slf4j
public class CrmOrganizationAO extends BaseAO {
	@Resource
	private CrmOrganizationManager crmOrganizationManager;

	/**
	 * 新增
	 * 
	 * @param CrmOrganizationDO
	 * @return 对象ID
	 */
	public Result<Long> save(CrmOrganizationDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = crmOrganizationManager.insert(obj);
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
	 * @param CrmOrganizationDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(CrmOrganizationDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = crmOrganizationManager.update(obj);
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
			int num = crmOrganizationManager.deleteById(id);
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
	 * @return CrmOrganizationDO
	 */
	public Result<CrmOrganizationDO> getById(Long id) {
		Result<CrmOrganizationDO> result = new Result<CrmOrganizationDO>(false);
		try {
			if (!NumUtil.isGreaterZero(id)) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			CrmOrganizationDO obj = crmOrganizationManager.getById(id);
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
	 * @return List<CrmOrganizationDO>
	 */
	public Result<List<CrmOrganizationDO>> getByIds(List<Long> ids) {
		Result<List<CrmOrganizationDO>> result = new Result<List<CrmOrganizationDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmOrganizationDO> list = crmOrganizationManager.getByIds(ids);
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
	 * @return List<CrmOrganizationDO>
	 */
	public Result<List<CrmOrganizationDO>> query(CrmOrganizationQuery query) {
		Result<List<CrmOrganizationDO>> result = new Result<List<CrmOrganizationDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmOrganizationDO> list = crmOrganizationManager.query(query);
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
	 * @return List<CrmOrganizationDO>
	 */
	public Result<List<CrmOrganizationDO>> queryAll(long pid) {
		Result<List<CrmOrganizationDO>> result = new Result<List<CrmOrganizationDO>>(false);
		try {
			CrmOrganizationQuery query = new CrmOrganizationQuery();
			query.setPageSize(Integer.MAX_VALUE);
			List<CrmOrganizationDO> list = crmOrganizationManager.query(query);
			result.setValue(sortAndGradeNodeList(list, pid));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("queryAll error,pid=" + pid, e);
			if (e instanceof MyException) {
				setErrorMessage(result, ((MyException) e).getResultCode());
			} else {
				setErrorMessage(result, ResultCode.SYSTEM_ERROR);
			}
		}
		return result;
	}

	/**
	 * 菜单分级及排序
	 */
	private List<CrmOrganizationDO> sortAndGradeNodeList(List<CrmOrganizationDO> list, long pid) {
		if (CollectionUtils.isEmpty(list)) {
			return Lists.newArrayList();
		}
		// 子菜单列表
		HashMap<Long, List<CrmOrganizationDO>> childMap = Maps.newHashMap();
		// 新的菜单列表
		List<CrmOrganizationDO> newList = Lists.newArrayList();
		// 取得一级菜单及父子菜单关系MAP
		for (CrmOrganizationDO roleNode : list) {
			if (roleNode.getParentId() == null) {
				continue;
			}
			if (roleNode.getParentId() == pid) {
				newList.add(roleNode);
				continue;
			}
			if (childMap.containsKey(roleNode.getParentId())) {
				List<CrmOrganizationDO> childList = childMap.get(roleNode.getParentId());
				childList.add(roleNode);
			} else {
				List<CrmOrganizationDO> childList = Lists.newArrayList();
				childList.add(roleNode);
				childMap.put(roleNode.getParentId(), childList);
			}
		}
		// 设置菜单父子关系及排序
		for (CrmOrganizationDO roleNode : newList) {
			loopNode(roleNode, childMap);
		}
		if (pid > 0) {

		}

		sortNode(newList);
		return newList;
	}

	/**
	 * 递归设置子菜单
	 */
	private void loopNode(CrmOrganizationDO parentMenuDO, HashMap<Long, List<CrmOrganizationDO>> childMap) {
		if (!childMap.containsKey(parentMenuDO.getId())) {
			return;
		}
		List<CrmOrganizationDO> list = childMap.get(parentMenuDO.getId());
		for (CrmOrganizationDO menuDO : list) {
			loopNode(menuDO, childMap);
		}
		sortNode(list);
		parentMenuDO.setChildList(list);
	}

	/**
	 * 排序
	 */
	private void sortNode(List<CrmOrganizationDO> list) {

	}

}

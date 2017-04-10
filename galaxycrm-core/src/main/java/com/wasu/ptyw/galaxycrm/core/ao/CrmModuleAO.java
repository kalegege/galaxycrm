package com.wasu.ptyw.galaxycrm.core.ao;

import java.util.Collections;
import java.util.Comparator;
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
import com.wasu.ptyw.galaxy.core.ao.BaseAO;
import com.wasu.ptyw.galaxycrm.core.manager.CrmModuleManager;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmModuleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmModuleQuery;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Service("crmModuleAO")
@Slf4j
public class CrmModuleAO extends BaseAO {
	@Resource
	private CrmModuleManager crmModuleManager;

	/**
	 * 新增
	 * 
	 * @param CrmModuleDO
	 * @return 对象ID
	 */
	public Result<Long> save(CrmModuleDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			Long id = crmModuleManager.insert(obj);
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
	 * @param CrmModuleDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(CrmModuleDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}
			int num = crmModuleManager.update(obj);
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
			int num = crmModuleManager.deleteById(id);
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
	 * @return CrmModuleDO
	 */
	public Result<CrmModuleDO> getById(long id) {
		Result<CrmModuleDO> result = new Result<CrmModuleDO>(false);
		try {
			if (id <= 0) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			CrmModuleDO obj = crmModuleManager.getById(id);
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
	 * @return List<CrmModuleDO>
	 */
	public Result<List<CrmModuleDO>> getByIds(List<Long> ids) {
		Result<List<CrmModuleDO>> result = new Result<List<CrmModuleDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmModuleDO> list = crmModuleManager.getByIds(ids);
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
	 * @return List<CrmModuleDO>
	 */
	public Result<List<CrmModuleDO>> query(CrmModuleQuery query) {
		Result<List<CrmModuleDO>> result = new Result<List<CrmModuleDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmModuleDO> list = crmModuleManager.query(query);
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
	 * @return List<CrmModuleDO>
	 */
	public Result<List<CrmModuleDO>> queryAll(long pid) {
		Result<List<CrmModuleDO>> result = new Result<List<CrmModuleDO>>(false);
		try {
			CrmModuleQuery query = new CrmModuleQuery();
			query.setPageSize(Integer.MAX_VALUE);
			List<CrmModuleDO> list = crmModuleManager.query(query);
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
	private List<CrmModuleDO> sortAndGradeNodeList(List<CrmModuleDO> list, long pid) {
		if (CollectionUtils.isEmpty(list)) {
			return Lists.newArrayList();
		}
		// 子菜单列表
		HashMap<Long, List<CrmModuleDO>> childMap = Maps.newHashMap();
		// 新的菜单列表
		List<CrmModuleDO> newList = Lists.newArrayList();
		// 取得一级菜单及父子菜单关系MAP
		for (CrmModuleDO roleNode : list) {
			if (roleNode.getParentId() == null){
				continue;
			}
			if (roleNode.getParentId() == pid) {
				newList.add(roleNode);
				continue;
			}
			if (childMap.containsKey(roleNode.getParentId())) {
				List<CrmModuleDO> childList = childMap.get(roleNode.getParentId());
				childList.add(roleNode);
			} else {
				List<CrmModuleDO> childList = Lists.newArrayList();
				childList.add(roleNode);
				childMap.put(roleNode.getParentId(), childList);
			}
		}
		// 设置菜单父子关系及排序
		for (CrmModuleDO roleNode : newList) {
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
	private void loopNode(CrmModuleDO parentMenuDO, HashMap<Long, List<CrmModuleDO>> childMap) {
		if (!childMap.containsKey(parentMenuDO.getId())) {
			return;
		}
		List<CrmModuleDO> list = childMap.get(parentMenuDO.getId());
		for (CrmModuleDO menuDO : list) {
			loopNode(menuDO, childMap);
		}
		sortNode(list);
		parentMenuDO.setChildList(list);
	}

	/**
	 * 排序
	 */
	private void sortNode(List<CrmModuleDO> list) {
		Collections.sort(list, new Comparator<CrmModuleDO>() {
			public int compare(CrmModuleDO o1, CrmModuleDO o2) {
				if (o2.getPriority() == null) {
					return 0;
				}
				if (o1.getPriority() == null) {
					return 1;
				}
				return o1.getPriority().compareTo(o2.getPriority());
			}
		});
	}

}

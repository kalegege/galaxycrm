package com.wasu.ptyw.galaxycrm.core.ao;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import shiro.ShiroDbRealm;
import shiro.ShiroDbRealm.HashPassword;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.common.util.NumUtil;
import com.wasu.ptyw.galaxy.core.ao.BaseAO;
import com.wasu.ptyw.galaxycrm.core.cache.CrmOrganizationCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmRoleCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmUserRoleCache;
import com.wasu.ptyw.galaxycrm.core.manager.CrmUserManager;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmUserQuery;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Service("crmUserAO") //后台通过xml注入
@Slf4j
public class CrmUserAO extends BaseAO {
	@Resource
	private CrmUserManager crmUserManager;

	@Resource(name = "shiroDbRealm")
	private ShiroDbRealm shiroRealm;

	@Resource
	CrmOrganizationCache crmOrganizationCache;
	@Resource
	CrmUserRoleCache crmUserRoleCache;
	@Resource
	CrmRoleCache crmRoleCache;

	/**
	 * 根据用户名查询用户对象
	 */
	public CrmUserDO getByUsername(String username) {
		try {
			if (StringUtils.isEmpty(username)) {
				return null;
			}

			CrmUserQuery query = new CrmUserQuery();
			query.setUsername(username);
			CrmUserDO obj = crmUserManager.queryFirst(query);
			return obj;
		} catch (Exception e) {
			log.error("getByUserName error,username=" + username, e);
		}
		return null;
	}

	/**
	 * 新增
	 * 
	 * @param CrmUserDO
	 * @return 对象ID
	 */
	public Result<Long> save(CrmUserDO obj) {
		Result<Long> result = new Result<Long>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}

			CrmUserDO user = getByUsername(obj.getUsername());
			if (user != null) {
				result.setErrorMessage("username-exist", "用户名已存在");
				 return result;
			}

			if (StringUtils.isNotEmpty(obj.getPlainPassword()) && shiroRealm != null) {
				HashPassword hashPassword = shiroRealm.encrypt(obj.getPlainPassword());
				obj.setSalt(hashPassword.salt);
				obj.setPassword(hashPassword.password);
			}

			Long id = crmUserManager.insert(obj);
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
	 * @param CrmUserDO
	 * @return 更新成功的记录数
	 */
	public Result<Integer> update(CrmUserDO obj) {
		Result<Integer> result = new Result<Integer>(false);
		try {
			if (obj == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_NULL);
			}

			// 设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
			if (StringUtils.isNotEmpty(obj.getPlainPassword()) && shiroRealm != null) {
				HashPassword hashPassword = shiroRealm.encrypt(obj.getPlainPassword());
				obj.setSalt(hashPassword.salt);
				obj.setPassword(hashPassword.password);
			}

			int num = crmUserManager.update(obj);
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
			int num = crmUserManager.deleteById(id);
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
	 * @return CrmUserDO
	 */
	public Result<CrmUserDO> getById(Long id) {
		Result<CrmUserDO> result = new Result<CrmUserDO>(false);
		try {
			if (!NumUtil.isGreaterZero(id)) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			CrmUserDO obj = crmUserManager.getById(id);
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
	 * @return List<CrmUserDO>
	 */
	public Result<List<CrmUserDO>> getByIds(List<Long> ids) {
		Result<List<CrmUserDO>> result = new Result<List<CrmUserDO>>(false);
		try {
			if (ids == null || ids.isEmpty()) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmUserDO> list = crmUserManager.getByIds(ids);
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
	 * @return List<CrmUserDO>
	 */
	public Result<List<CrmUserDO>> query(CrmUserQuery query) {
		Result<List<CrmUserDO>> result = new Result<List<CrmUserDO>>(false);
		try {
			if (query == null) {
				return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
			}
			List<CrmUserDO> list = crmUserManager.query(query);

			for (CrmUserDO user : list) {
				setRolesAndOrg(user);
			}

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
			int num = crmUserManager.updateStatusByIds(ids, status);
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

	private void setRolesAndOrg(CrmUserDO user) {
		try {
			user.setOrganization(crmOrganizationCache.get(user.getOrgId()));
			List<CrmUserRoleDO> userRoles = crmUserRoleCache.get(user.getId());
			for (CrmUserRoleDO userRole : userRoles) {
				userRole.setRole(crmRoleCache.get(userRole.getRoleId()));
			}
			user.setUserRoles(userRoles);
		} catch (Exception e) {
			log.error("setRolesAndPermissions error,user=" + user, e);
		}
	}
}

package com.wasu.ptyw.galaxycrm.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRolePermissionDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmRolePermissionQuery;

/**
 * @author wenguang
 * @date 2015年08月14日
 */
public interface CrmRolePermissionManager {
	/**
	 * 新增
	 * 
	 * @param CrmRolePermissionDO
	 * @return 对象ID
	 */
	public Long insert(CrmRolePermissionDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param CrmRolePermissionDO
	 * @return 更新成功的记录数
	 */
	public int update(CrmRolePermissionDO baseDO) throws MyException;

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return 删除成功的记录数
	 */
	public int deleteById(long id) throws MyException;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return CrmRolePermissionDO
	 */
	public CrmRolePermissionDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<CrmRolePermissionDO>
	 */
	public List<CrmRolePermissionDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<CrmRolePermissionDO>
	 */
	public List<CrmRolePermissionDO> query(CrmRolePermissionQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return CrmRolePermissionDO
	 */
	public CrmRolePermissionDO queryFirst(CrmRolePermissionQuery query) throws MyException;
	
	/**
	 * 根据roleId删除
	 * 
	 * @param roleId
	 * @return 删除成功的记录数
	 */
	public int deleteByRoleId(long roleId) throws MyException;
	

}

package com.wasu.ptyw.galaxycrm.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmRoleQuery;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
public interface CrmRoleManager {
	/**
	 * 新增
	 * 
	 * @param CrmRoleDO
	 * @return 对象ID
	 */
	public Long insert(CrmRoleDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param CrmRoleDO
	 * @return 更新成功的记录数
	 */
	public int update(CrmRoleDO baseDO) throws MyException;

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
	 * @return CrmRoleDO
	 */
	public CrmRoleDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<CrmRoleDO>
	 */
	public List<CrmRoleDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<CrmRoleDO>
	 */
	public List<CrmRoleDO> query(CrmRoleQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return CrmRoleDO
	 */
	public CrmRoleDO queryFirst(CrmRoleQuery query) throws MyException;

}

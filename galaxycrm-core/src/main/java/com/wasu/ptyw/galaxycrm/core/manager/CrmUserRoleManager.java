package com.wasu.ptyw.galaxycrm.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmUserRoleQuery;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
public interface CrmUserRoleManager {
	/**
	 * 新增
	 * 
	 * @param CrmUserRoleDO
	 * @return 对象ID
	 */
	public Long insert(CrmUserRoleDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param CrmUserRoleDO
	 * @return 更新成功的记录数
	 */
	public int update(CrmUserRoleDO baseDO) throws MyException;

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
	 * @return CrmUserRoleDO
	 */
	public CrmUserRoleDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<CrmUserRoleDO>
	 */
	public List<CrmUserRoleDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<CrmUserRoleDO>
	 */
	public List<CrmUserRoleDO> query(CrmUserRoleQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return CrmUserRoleDO
	 */
	public CrmUserRoleDO queryFirst(CrmUserRoleQuery query) throws MyException;

}

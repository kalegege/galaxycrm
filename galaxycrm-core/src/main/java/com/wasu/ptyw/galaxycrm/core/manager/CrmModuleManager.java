package com.wasu.ptyw.galaxycrm.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmModuleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmModuleQuery;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
public interface CrmModuleManager {
	/**
	 * 新增
	 * 
	 * @param CrmModuleDO
	 * @return 对象ID
	 */
	public Long insert(CrmModuleDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param CrmModuleDO
	 * @return 更新成功的记录数
	 */
	public int update(CrmModuleDO baseDO) throws MyException;

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
	 * @return CrmModuleDO
	 */
	public CrmModuleDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<CrmModuleDO>
	 */
	public List<CrmModuleDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<CrmModuleDO>
	 */
	public List<CrmModuleDO> query(CrmModuleQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return CrmModuleDO
	 */
	public CrmModuleDO queryFirst(CrmModuleQuery query) throws MyException;

}

package com.wasu.ptyw.galaxycrm.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmOrganizationDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmOrganizationQuery;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
public interface CrmOrganizationManager {
	/**
	 * 新增
	 * 
	 * @param CrmOrganizationDO
	 * @return 对象ID
	 */
	public Long insert(CrmOrganizationDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param CrmOrganizationDO
	 * @return 更新成功的记录数
	 */
	public int update(CrmOrganizationDO baseDO) throws MyException;

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
	 * @return CrmOrganizationDO
	 */
	public CrmOrganizationDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<CrmOrganizationDO>
	 */
	public List<CrmOrganizationDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<CrmOrganizationDO>
	 */
	public List<CrmOrganizationDO> query(CrmOrganizationQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return CrmOrganizationDO
	 */
	public CrmOrganizationDO queryFirst(CrmOrganizationQuery query) throws MyException;

}

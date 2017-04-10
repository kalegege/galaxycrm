package com.wasu.ptyw.galaxycrm.core.manager;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmUserQuery;

/**
 * @author wenguang
 * @date 2015年08月24日
 */
public interface CrmUserManager {
	/**
	 * 新增
	 * 
	 * @param CrmUserDO
	 * @return 对象ID
	 */
	public Long insert(CrmUserDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param CrmUserDO
	 * @return 更新成功的记录数
	 */
	public int update(CrmUserDO baseDO) throws MyException;

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
	 * @return CrmUserDO
	 */
	public CrmUserDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<CrmUserDO>
	 */
	public List<CrmUserDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<CrmUserDO>
	 */
	public List<CrmUserDO> query(CrmUserQuery query) throws MyException;

	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return CrmUserDO
	 */
	public CrmUserDO queryFirst(CrmUserQuery query) throws MyException;

	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(List<Long> ids, int status) throws MyException;

}

package com.wasu.ptyw.activitylottery.core.manager;

import java.util.List;

import com.wasu.ptyw.activitylottery.dal.dataobject.ZanCountDO;
import com.wasu.ptyw.activitylottery.dal.query.ZanCountQuery;
import com.wasu.ptyw.galaxy.common.exception.MyException;

/**
 * @author jxt
 * @date 2015年10月26日
 */
public interface ZanCountManager {
	/**
	 * 新增
	 * 
	 * @param VoteCountDO
	 * @return 对象ID
	 */
	public Long insert(ZanCountDO baseDO) throws MyException;

	/**
	 * 更新
	 * 
	 * @param VoteCountDO
	 * @return 更新成功的记录数
	 */
	public int update(ZanCountDO baseDO) throws MyException;

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
	 * @return VoteCountDO
	 */
	public ZanCountDO getById(long id) throws MyException;

	/**
	 * 根据多个id查询
	 * 
	 * @param ids
	 * @return List<VoteCountDO>
	 */
	public List<ZanCountDO> getByIds(List<Long> ids) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return List<VoteCountDO>
	 */
	public List<ZanCountDO> query(ZanCountQuery query) throws MyException;
	
	/**
	 * 查询所有
	 * 
	 * @param query
	 * @return List<VoteCountDO>
	 */
	public List<ZanCountDO> queryAll(ZanCountQuery query) throws MyException;


	/**
	 * 查询单个
	 * 
	 * @param query
	 * @return VoteCountDO
	 */
	public ZanCountDO queryFirst(ZanCountQuery query) throws MyException;

	/**
	 * 根据多个id更新状态
	 * 
	 * @param ids
	 * @return 更新成功的记录数
	 */
	public int updateStatusByIds(List<Long> ids, int status) throws MyException;

}

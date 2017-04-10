package com.wasu.ptyw.galaxycrm.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Alias("CrmUserRoleDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmUserRoleDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public CrmUserRoleDO() {

	}

	private CrmRoleDO role;

	/**
	 * 优先级
	 */
	private Integer priority;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 用户ID
	 */
	private Long userId;

}

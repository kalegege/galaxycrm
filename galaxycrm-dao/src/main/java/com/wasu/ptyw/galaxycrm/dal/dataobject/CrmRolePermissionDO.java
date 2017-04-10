package com.wasu.ptyw.galaxycrm.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Alias("CrmRolePermissionDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmRolePermissionDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public CrmRolePermissionDO() {

	}

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 权限
	 */
	private String permission;

}

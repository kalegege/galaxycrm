package com.wasu.ptyw.galaxycrm.dal.dataobject;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Alias("CrmUserDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmUserDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public CrmUserDO() {

	}

	/**
	 * 角色
	 */
	private List<CrmUserRoleDO> userRoles = Lists.newArrayList();

	/**
	 * 原始密码
	 */
	private String plainPassword;

	/**
	 * 组织
	 */
	private CrmOrganizationDO organization;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 真实姓名
	 */
	private String realname;

	/**
	 * 加密佐料
	 */
	private String salt;

	/**
	 * 组织ID
	 */
	private Long orgId;

	/**
	 * 状态
	 */
	private Integer status;

}

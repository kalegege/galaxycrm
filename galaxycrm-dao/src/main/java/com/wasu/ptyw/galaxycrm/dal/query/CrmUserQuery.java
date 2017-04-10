package com.wasu.ptyw.galaxycrm.dal.query;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.persist.SimpleQuery;

/**
 * @author wenguang
 * @date 2015年08月07日
 */
@Alias("CrmUserQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmUserQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名，like查询
	 */
	private String likeUsername;
	
	/**
	 * 自增主键
	 */
	private Long id;

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

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

}

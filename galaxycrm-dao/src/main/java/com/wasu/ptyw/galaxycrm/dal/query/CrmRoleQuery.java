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
@Alias("CrmRoleQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmRoleQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

}

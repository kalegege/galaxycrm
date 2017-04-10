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
@Alias("CrmModuleQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmModuleQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	private Long id;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 优先级
	 */
	private Integer priority;

	/**
	 * 连接
	 */
	private String url;

	/**
	 * 父ID
	 */
	private Long parentId;

	/**
	 * 标识
	 */
	private String sn;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

}

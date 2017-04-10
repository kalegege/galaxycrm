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
@Alias("CrmOrganizationDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmOrganizationDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public CrmOrganizationDO() {

	}

	/**
	 * 子菜单
	 */
	private List<CrmOrganizationDO> childList = Lists.newArrayList();

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 父ID
	 */
	private Long parentId;

}

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
@Alias("CrmRoleDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmRoleDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public CrmRoleDO() {

	}

	List<String> permissionList = Lists.newArrayList();

	/**
	 * 名称
	 */
	private String name;

}

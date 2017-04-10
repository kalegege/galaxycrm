package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月26日
 */
@Alias("ActivityIntroductionDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityIntroductionDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityIntroductionDO() {

	}

	/**
	 * 活动编号
	 */
	private String code;

	/**
	 * 活动名称
	 */
	private String name;

	/**
	 * 活动介绍
	 */
	private String introduction;

	/**
	 * 状态
	 */
	private Integer status;

}

package com.wasu.ptyw.activitylottery.dal.query;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.persist.SimpleQuery;

/**
 * @author quxy
 * @date 2015年10月26日
 */
@Alias("ActivityIntroductionQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityIntroductionQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Integer id;

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

	private Date gmtCreate;

	private Date gmtModified;

}

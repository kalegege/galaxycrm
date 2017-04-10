package com.wasu.ptyw.activitylottery.dal.query;

import java.util.Date;

import com.wasu.ptyw.galaxy.dal.persist.SimpleQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jxt
 * @date 2016年07月07日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ZanCountQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 真实投票次数
	 */
	private Integer count;
	/**
	 * 增量
	 */
	private Integer countAdd;
	
	
	/**
	 * 地区信息
	 */
	private String region;

	/**
	 * 视频编号
	 */
	private String videoId;

	/**
	 * 活动编号
	 */
	private String activityId;
	
	private String videoName;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	private String orderBy;
	
	private String activityName;

}

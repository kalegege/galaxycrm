package com.wasu.ptyw.activitylottery.dal.dataobject;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * @author jxt
 * @date 2016年07月07日
 */
@Alias("VoteCountDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class VoteCountDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public VoteCountDO() {

	}


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
	
	private Date gmtCreate;

	private Date gmtModified;
	
	private String activityName;

}

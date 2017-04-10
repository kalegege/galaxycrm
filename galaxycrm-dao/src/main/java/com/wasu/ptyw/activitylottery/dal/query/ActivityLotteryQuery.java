package com.wasu.ptyw.activitylottery.dal.query;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.persist.SimpleQuery;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityLotteryQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityLotteryQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String prizeCode;

	/**
	 * 活动编号
	 */
	private String code;

	private String stbId;

	private String region;

	private Integer prize;

	private String prizeTxt;

	private String mobile;

	private Integer status;

	private Date gmtCreate;

	private Date gmtModified;
	
	private Integer count;

}

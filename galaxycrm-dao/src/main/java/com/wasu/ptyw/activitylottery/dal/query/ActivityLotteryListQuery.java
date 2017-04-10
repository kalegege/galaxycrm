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
@Alias("ActivityLotteryListQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityLotteryListQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 活动编号
	 */
	private String code;
	
	private String region;

	private Integer prize;

	private String prizeTxt;

	private String prizeCode;

	private Integer status;

	private Date gmtCreate;

	private Date gmtModified;

}

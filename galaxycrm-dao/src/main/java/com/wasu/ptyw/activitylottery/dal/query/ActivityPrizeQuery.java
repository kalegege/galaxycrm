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
@Alias("ActivityPrizeQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityPrizeQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 活动编号
	 */
	private String code;
	private String region;

	private Integer prize;
	private String name;

	private String prizeTxt;

	private Float countDay;

	private Integer count;

	private Integer useCountDay;

	private Integer useCount;

	private Float probability;

	private Integer status;

	private Date gmtCreate;

	private Date gmtModified;

}

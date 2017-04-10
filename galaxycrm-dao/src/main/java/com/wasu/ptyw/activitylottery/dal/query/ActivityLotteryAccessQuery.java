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
@Alias("ActivityLotteryAccessQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityLotteryAccessQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 活动编号
	 */
	private String code;

	private String stbId;

	private String region;

	private String ip;

	private String header;

	private Integer status;

	private Date gmtCreate;

	private Date gmtModified;

	private Integer count;
	private Integer stb_count;
	
	private Integer able;
}

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
@Alias("ActivityAccessTotalCountQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityAccessTotalCountQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 活动编号
	 */
	private String code;

	/**
	 * 所在地区
	 */
	private String region;
	
	private Integer pv;
	
	private Integer uv;
	
	private String date;
	
	private Integer type;

}

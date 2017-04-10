package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityAccessTotalCountDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityAccessTotalCountDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityAccessTotalCountDO() {

	}

	
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

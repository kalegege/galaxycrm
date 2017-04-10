package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityAccessVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityAccessVO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityAccessVO() {

	}
	public ActivityAccessVO(ActivityAccessDO d) {
		this.region=d.getRegion();
		this.count=d.getCount();
		this.code=d.getCode();
		this.codeName=d.getPlatform();
	}

	/**
	 * 所在地区
	 */
	private String region;

	private Integer count;
	
	/**
	 * 活动编号
	 */
	private String code;
	private String codeName;

}

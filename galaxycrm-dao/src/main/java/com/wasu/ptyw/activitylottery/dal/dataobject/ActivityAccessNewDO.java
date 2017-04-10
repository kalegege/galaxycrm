package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityAccessNewDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityAccessNewDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityAccessNewDO() {

	}

	/**
	 * 活动编号
	 */
	private String code;

	/**
	 * 机顶盒号
	 */
	private String stbId;

	/**
	 * 所在地区
	 */
	private String region;

	/**
	 * 平台
	 */
	private String platform;

	/**
	 * 版本
	 */
	private String version;

	private String referer;

	/**
	 * 访问ip
	 */
	private String ip;

	/**
	 * 用户useragent
	 */
	private String userAgent;

	private String cookie;

	private Integer status;
	
	private Integer count;

	private String pagename;
}

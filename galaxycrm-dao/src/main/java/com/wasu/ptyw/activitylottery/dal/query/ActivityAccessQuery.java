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
@Alias("ActivityAccessQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityAccessQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Integer id;

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

	private Date gmtCreate;

	private Date gmtModified;
	
	private  Integer count;

}

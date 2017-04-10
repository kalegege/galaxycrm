package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityLotteryAccessDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityLotteryAccessDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityLotteryAccessDO() {

	}

	/**
	 * 活动编号
	 */
	private String code;

	private String stbId;

	private String region;

	private String ip;

	private String header;
	private Integer able;

	private Integer status;
	private Integer count;
	private Integer stb_count;

}

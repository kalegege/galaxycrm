package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityLotteryDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityLotteryDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityLotteryDO() {

	}

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
	
	private Integer count;

}

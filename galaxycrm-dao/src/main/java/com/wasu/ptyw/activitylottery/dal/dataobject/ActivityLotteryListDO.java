package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityLotteryListDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityLotteryListDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityLotteryListDO() {

	}

	/**
	 * 活动编号
	 */
	private String code;
	private String region;

	private Integer prize;

	private String prizeTxt;

	private String prizeCode;

	private Integer status;

}

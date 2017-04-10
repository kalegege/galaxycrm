package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityLotteryVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityLotteryVO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityLotteryVO() {

	}
	
	public ActivityLotteryVO(ActivityLotteryDO d) {
		this.region=d.getRegion();
		this.codeName=d.getMobile();
		this.code=d.getCode();
		this.count=d.getCount();
		this.prize=d.getPrize();
		this.prizeTxt=d.getPrizeTxt();
	}

	private String region;
	
	private String codeName;

	private String code;

	private Integer count;

	private Integer prize;

	private String prizeTxt;

}

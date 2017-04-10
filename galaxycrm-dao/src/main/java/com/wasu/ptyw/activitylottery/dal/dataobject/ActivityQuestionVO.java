package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityQuestionVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityQuestionVO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityQuestionVO() {

	}

	/**
	 * 活动编号
	 */
	private String code;

	//题干
	private String stem;

	private String option1;

	private String option2;

	private String option3;

	private String option4;

	private String answer;

	private String name;

}

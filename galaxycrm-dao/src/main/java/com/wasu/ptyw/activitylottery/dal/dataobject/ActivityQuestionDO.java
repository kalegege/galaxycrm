package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityQuestionDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityQuestionDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityQuestionDO() {

	}

	public ActivityQuestionDO(ArrayList<String> list) {
		this.code=list.get(0);
		this.stem=list.get(1);
		this.option1=list.get(2);
		this.option2=list.get(3);
		this.option3=list.get(4);
		this.option4=list.get(5);
		this.answer=list.get(6);
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

}

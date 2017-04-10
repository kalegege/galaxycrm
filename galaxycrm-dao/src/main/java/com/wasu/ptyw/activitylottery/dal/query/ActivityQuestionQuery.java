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
@Alias("ActivityQuestionQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityQuestionQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Integer id;

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

	private Date gmtCreate;

	private Date gmtModified;

}

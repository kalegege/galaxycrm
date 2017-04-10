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
@Alias("ActivityPrizeDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityPrizeDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityPrizeDO() {

	}

	public ActivityPrizeDO(ArrayList<String> list) {
		this.code=list.get(0);
		this.name=list.get(1);
		this.prize=Integer.parseInt(list.get(2));
		this.prizeTxt=list.get(3);
		this.count=Integer.parseInt(list.get(4));
		this.countDay=Float.parseFloat(list.get(5));
		this.probability=Float.parseFloat(list.get(6));
		this.region=list.get(7);
		this.status=0;
		this.useCountDay=0;
		this.useCount=0;
	}

	/**
	 * 活动编号
	 */
	private String code;
	
	private String region;
	/**
	 * 活动名称
	 */
	private String name;

	private Integer prize;

	private String prizeTxt;

	private Float countDay;

	private Integer count;

	private Integer useCountDay;

	private Integer useCount;

	private Float probability;

	private Integer status;

}

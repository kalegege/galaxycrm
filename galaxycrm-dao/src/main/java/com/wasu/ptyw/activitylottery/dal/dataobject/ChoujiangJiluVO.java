package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ChoujiangJiluVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ChoujiangJiluVO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ChoujiangJiluVO() {

	}

	/**
	 * 所在地区
	 */
	private String region;

	private Integer count;
	
	/**
	 * 活动编号
	 */
	private String code;
	private String codeName;
	private String choujiang;

}

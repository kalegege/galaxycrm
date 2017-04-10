package com.wasu.ptyw.activitylottery.dal.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.BaseDO;

/**
 * @author quxy
 * @date 2015年10月23日
 */
@Alias("ActivityPagenameRealnameDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityPagenameRealnameDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	public ActivityPagenameRealnameDO() {

	}


	private String pagename;
	
	private String realname;
	
}

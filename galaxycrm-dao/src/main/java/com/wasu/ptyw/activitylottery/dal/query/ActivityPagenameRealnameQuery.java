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
@Alias("ActivityPagenameRealnameQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityPagenameRealnameQuery extends SimpleQuery {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String pagename;
	
	private String realname;

}

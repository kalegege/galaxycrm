/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxycrm.core.ao.CrmUserRoleAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmUserRoleQuery;

/**
 * @author wenguang
 * @date 2015年8月14日
 */
@Service("crmUserRoleCache")
public class CrmUserRoleCache extends DbCache<Long, List<CrmUserRoleDO>> {
	@Resource(name = "crmUserRoleAO")
	CrmUserRoleAO ao;

	@Override
	protected List<CrmUserRoleDO> fetch(Long key) {
		CrmUserRoleQuery query = new CrmUserRoleQuery();
		query.setUserId(key);
		query.setPageSize(Integer.MAX_VALUE);
		query.setOrderBy("priority asc");

		Result<List<CrmUserRoleDO>> result = ao.query(query);
		if (result.isSuccess()) {
			return result.getValue();
		}
		return Lists.newArrayList();
	}

}

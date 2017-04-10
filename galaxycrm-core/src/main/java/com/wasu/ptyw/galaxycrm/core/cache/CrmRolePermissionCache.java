/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxycrm.core.ao.CrmRolePermissionAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRolePermissionDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmRolePermissionQuery;

/**
 * @author wenguang
 * @date 2015年8月14日
 */
@Service("rmRolePermissionCache")
public class CrmRolePermissionCache extends DbCache<Long, List<String>> {
	@Resource(name = "crmRolePermissionAO")
	CrmRolePermissionAO ao;

	@Override
	protected List<String> fetch(Long key) {
		CrmRolePermissionQuery query = new CrmRolePermissionQuery();
		query.setRoleId(key);
		query.setPageSize(Integer.MAX_VALUE);

		Result<List<CrmRolePermissionDO>> result = ao.query(query);
		if (result.isSuccess()) {
			List<String> pList = Lists.transform(result.getValue(), new Function<CrmRolePermissionDO, String>() {
				@Override
				public String apply(CrmRolePermissionDO input) {
					return input.getPermission();
				}
			});
			return pList;
		}
		return Lists.newArrayList();
	}

}

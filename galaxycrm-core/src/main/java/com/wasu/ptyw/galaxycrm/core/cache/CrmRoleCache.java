/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxycrm.core.ao.CrmRoleAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRoleDO;

/**
 * @author wenguang
 * @date 2015年8月14日
 */
@Service("crmRoleCache")
public class CrmRoleCache extends DbCache<Long, CrmRoleDO> {
	@Resource(name = "crmRoleAO")
	CrmRoleAO ao;

	@Override
	protected CrmRoleDO fetch(Long key) {
		Result<CrmRoleDO> result = ao.getById(key);
		if (result.isSuccess()) {
			return result.getValue();
		}
		return null;
	}

}

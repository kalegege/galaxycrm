/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxycrm.core.ao.CrmOrganizationAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmOrganizationDO;

/**
 * @author wenguang
 * @date 2015年8月14日
 */
@Service("crmOrganizationCache")
public class CrmOrganizationCache extends DbCache<Long, CrmOrganizationDO> {
	@Resource(name = "crmOrganizationAO")
	CrmOrganizationAO ao;

	@Override
	protected CrmOrganizationDO fetch(Long key) {
		Result<CrmOrganizationDO> result = ao.getById(key);
		if (result.isSuccess()) {
			return result.getValue();
		}
		return null;
	}
}

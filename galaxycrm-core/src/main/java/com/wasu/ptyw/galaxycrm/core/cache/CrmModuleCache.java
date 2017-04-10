/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxycrm.core.ao.CrmModuleAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmModuleDO;

/**
 * @author wenguang
 * @date 2015年8月14日
 */
@Service("crmModuleCache")
public class CrmModuleCache extends DbCache<Long, CrmModuleDO> {
	@Resource(name = "crmModuleAO")
	private CrmModuleAO ao;

	@Override
	protected CrmModuleDO fetch(Long key) {
		Result<CrmModuleDO> result = ao.getById(key);
		if (result.isSuccess()) {
			return result.getValue();
		}
		return null;
	}
}

/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxycrm.core.ao.CrmUserAO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserDO;

/**
 * @author wenguang
 * @date 2015年8月14日
 */
@Service("crmUserCache")
public class CrmUserCache extends DbCache<Long, CrmUserDO> {
	@Resource(name = "crmUserAO")
	CrmUserAO ao;

	@Override
	protected CrmUserDO fetch(Long key) {
		Result<CrmUserDO> result = ao.getById(key);
		if (result.isSuccess()) {
			return result.getValue();
		}
		return null;
	}
}

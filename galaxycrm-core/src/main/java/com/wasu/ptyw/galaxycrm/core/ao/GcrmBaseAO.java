/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.ao;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.core.ao.BaseAO;
import com.wasu.ptyw.galaxycrm.core.constant.GcrmResultCode;

/**
 * @author wenguang
 * @date 2015年6月9日
 */
public class GcrmBaseAO extends BaseAO {

	public <T> Result<T> setErrorMessage(Result<T> t, GcrmResultCode rc) {
		t.setErrorMessage(rc.getCode(), rc.getMessage());
		return t;
	}

}

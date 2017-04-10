/**
 * 
 */
package com.wasu.ptyw.galaxycrm.web.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Maps;
import com.wasu.ptyw.galaxy.common.dataobject.Result;

/**
 * @author wenguang
 * @date 2015年7月13日
 */
public class BaseController {

    protected  Log log = LogFactory.getLog(this.getClass());
    public <V> Map<String, Object> dealResult(Result<V> result) {
  		Map<String, Object> json = Maps.newHashMap();
  		json.put("success", result.isSuccess());
  		if (!result.isSuccess()) {
  			json.put("code", result.getCode());
  			json.put("msg", result.getMessage());
  		}
  		return json;
  	}
}

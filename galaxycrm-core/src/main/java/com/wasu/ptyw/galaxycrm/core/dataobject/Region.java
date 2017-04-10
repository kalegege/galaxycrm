/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.dataobject;

import java.io.Serializable;

import lombok.Data;

/**
 * 区域信息
 * 
 * @author wenguang
 * @date 2015年12月16日
 */
@Data
public class Region implements Serializable {
	private static final long serialVersionUID = -1L;
	private String regionCode;
	private String regionName;

	public Region(String regionCode, String regionName) {
		this.regionCode = regionCode;
		this.regionName = regionName;
	}

	@Override
	public String toString() {
		return "{regionCode:" + regionCode + ",regionName:" + regionName + "}";
	}
}

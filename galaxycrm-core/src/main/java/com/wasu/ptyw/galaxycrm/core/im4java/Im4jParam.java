package com.wasu.ptyw.galaxycrm.core.im4java;

import org.im4java.core.IMOperation;

/**
 * im4java参数
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
public class Im4jParam {
	public IMOperation op;
	public int srcH; // 图片高度
	public int srcW; // 图片宽度
	public int destW; // 目标宽度
	public int destH; // 目标高度

	public Im4jParam(IMOperation op, int srcH, int srcW, int destW, int destH) {
		this.op = op;
		this.srcH = srcH;
		this.srcW = srcW;
		this.destW = destW;
		this.destH = destH;
	}
}

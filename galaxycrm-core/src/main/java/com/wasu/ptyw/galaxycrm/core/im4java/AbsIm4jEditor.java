package com.wasu.ptyw.galaxycrm.core.im4java;

import lombok.Getter;

/**
 * 图片编辑基类
 * 
 * @author wenguang
 * @date 2015年7月15日
 */

public abstract class AbsIm4jEditor {
	public AbsIm4jEditor(Im4jParam param) {
		this.param = param;
	}

	public abstract boolean exec();

	@Getter
	protected Im4jParam param;

	/**
	 * 判断是否需要缩放
	 */
	public boolean needResize() {
		// 如果原图有一边大于目标图，就需要缩放
		return param.srcW > param.destW || param.srcH > param.destH;
	}
}

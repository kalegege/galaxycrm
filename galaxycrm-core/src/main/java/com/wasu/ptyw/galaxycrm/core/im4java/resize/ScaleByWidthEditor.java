package com.wasu.ptyw.galaxycrm.core.im4java.resize;

import com.wasu.ptyw.galaxycrm.core.im4java.AbsIm4jEditor;
import com.wasu.ptyw.galaxycrm.core.im4java.Im4jParam;

/**
 * 按宽缩放
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
public class ScaleByWidthEditor extends AbsIm4jEditor {
	public ScaleByWidthEditor(Im4jParam param) {
		super(param);
	}

	@Override
	public boolean exec() {
		if (param.srcW <= param.destW) {
			return false;
		}
		double widthRatio = (double) param.destW / param.srcW;

		param.destH = (int) Math.ceil(param.srcH * widthRatio);
		param.op.resize(param.destW, param.destH);
		return true;
	}
}

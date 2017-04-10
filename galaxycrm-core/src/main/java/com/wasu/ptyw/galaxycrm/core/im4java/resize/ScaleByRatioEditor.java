package com.wasu.ptyw.galaxycrm.core.im4java.resize;

import com.wasu.ptyw.galaxycrm.core.im4java.AbsIm4jEditor;
import com.wasu.ptyw.galaxycrm.core.im4java.Im4jParam;

/**
 * 按长宽比例动态调整到一边相同，保证不比目标长宽大
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
public class ScaleByRatioEditor extends AbsIm4jEditor {
	public ScaleByRatioEditor(Im4jParam param) {
		super(param);
	}

	@Override
	public boolean exec() {
		if (!needResize()) {
			return false;
		}

		double widthRatio = (double) param.destW / param.srcW;
		double heightRatio = (double) param.destH / param.srcH;

		if (widthRatio > heightRatio) {
			param.destW = (int) Math.ceil(param.srcW * heightRatio);
		} else {
			param.destH = (int) Math.ceil(param.srcH * widthRatio);
		}
		param.op.resize(param.destW, param.destH);
		return true;
	}
}

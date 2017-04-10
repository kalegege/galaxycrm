package com.wasu.ptyw.galaxycrm.core.im4java.resize;

import com.wasu.ptyw.galaxycrm.core.im4java.AbsIm4jEditor;
import com.wasu.ptyw.galaxycrm.core.im4java.Im4jParam;

/**
 * 按高缩放
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
public class ScaleByHeightEditor extends AbsIm4jEditor {
	public ScaleByHeightEditor(Im4jParam param) {
		super(param);
	}

	@Override
	public boolean exec() {
		if (param.srcH <= param.destH) {
			return false;
		}
		double heightRatio = (double) param.destH / param.srcH;

		param.destW = (int) Math.ceil(param.srcW * heightRatio);
		param.op.resize(param.destW, param.destH);
		return true;
	}
}

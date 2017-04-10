package com.wasu.ptyw.galaxycrm.core.im4java.resize;

import com.wasu.ptyw.galaxycrm.core.im4java.AbsIm4jEditor;
import com.wasu.ptyw.galaxycrm.core.im4java.Im4jParam;

/**
 * 裁剪
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
public class CutEditor extends AbsIm4jEditor {
	public CutEditor(Im4jParam param) {
		super(param);
	}

	@Override
	public boolean exec() {
		if (!needResize()) {
			return false;
		}
		// 宽比较小，不需要剪裁
		if (param.srcW < param.destW) {
			param.destW = param.srcW;
		}
		// 高比较小，不需要剪裁
		if (param.srcH < param.destH) {
			param.destH = param.srcH;
		}

		// 设置剪裁的起始坐标
		int x = (param.srcW - param.destW) / 2;
		int y = (param.srcH - param.destH) / 2;

		param.op.crop(param.destW, param.destH, x, y);
		return true;
	}
}

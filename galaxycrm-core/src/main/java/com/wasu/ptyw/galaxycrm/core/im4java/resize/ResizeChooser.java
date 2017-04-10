package com.wasu.ptyw.galaxycrm.core.im4java.resize;

import com.wasu.ptyw.galaxycrm.core.im4java.AbsIm4jEditor;
import com.wasu.ptyw.galaxycrm.core.im4java.Im4jParam;
import com.wasu.ptyw.galaxycrm.core.im4java.ResizeOption;
import com.wasu.ptyw.galaxycrm.core.im4java.ResizeOption.ScaleType;

/**
 * 图片缩放选择器
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
public class ResizeChooser {
	public static boolean exec(Im4jParam param, ResizeOption option) {
		AbsIm4jEditor im4j = null;
		if (ScaleType.CUT.equals(option.getScaleType())) {
			im4j = new CutEditor(param);
		} else if (ScaleType.BY_RATIO.equals(option.getScaleType())) {
			im4j = new ScaleByRatioEditor(param);
		} else if (ScaleType.BY_WIDTH.equals(option.getScaleType())) {
			im4j = new ScaleByWidthEditor(param);
		} else if (ScaleType.BY_HEIGHT.equals(option.getScaleType())) {
			im4j = new ScaleByHeightEditor(param);
		} else {
			im4j = new ScaleByRatioEditor(param);
		}
		return im4j.exec();
	}
}

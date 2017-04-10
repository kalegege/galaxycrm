/**
 * 2014-1-22
 */
package com.wasu.ptyw.galaxycrm.core.im4java.rotate;

import com.wasu.ptyw.galaxycrm.core.im4java.AbsIm4jEditor;
import com.wasu.ptyw.galaxycrm.core.im4java.Im4jParam;

/**
 * 旋转
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
public class RotateEditor extends AbsIm4jEditor {
	double degree;// 旋转角度

	public RotateEditor(Im4jParam param, double degree) {
		super(param);
		degree = degree % 360;
		if (degree <= 0) {
			degree = 360 + degree;
		}
		this.degree = degree;
	}

	@Override
	public boolean exec() {
		if (degree == 0)
			return false;
		param.op.rotate(degree);
		return true;
	}
}

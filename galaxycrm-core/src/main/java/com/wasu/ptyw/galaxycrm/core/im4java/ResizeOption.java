package com.wasu.ptyw.galaxycrm.core.im4java;

import lombok.Data;

/**
 * 缩放参数
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
@Data
public class ResizeOption {
	ScaleType scaleType;
	double degree;// 旋转角度，0时不变

	public ResizeOption() {
		setScaleType(ScaleType.DEFAULT);
		setDegree(0);
	}

	public ResizeOption(ScaleType scaleType, double degree) {
		setScaleType(scaleType);
		setDegree(degree);
	}

	public enum ScaleType {
		DEFAULT(0, "默认的,按比例动态缩放"), CUT(1, "居中裁剪"), BY_RATIO(2, "按比例动态缩放"), BY_WIDTH(3, "按宽缩放"), BY_HEIGHT(4, "按高缩放");

		private final Integer code;

		private final String name;

		ScaleType(Integer code, String name) {
			this.code = code;
			this.name = name;
		}

		public Integer getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		public static ScaleType get(Integer code) {
			for (ScaleType e : ScaleType.values()) {
				if (e.getCode().equals(code))
					return e;
			}
			return ScaleType.DEFAULT;
		}
	}
}

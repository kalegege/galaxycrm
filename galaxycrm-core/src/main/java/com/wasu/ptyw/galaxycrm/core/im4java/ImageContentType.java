/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.im4java;

/**
 * @author wenguang
 * @date 2014年6月9日
 */
public enum ImageContentType {
	GIF("gif", "image/gif"), 
	ICO("ico", "image/x-icon"), 
	JPEG("jpeg", "image/jpeg"), 
	JPG("jpg", "image/jpeg"), 
	PNG("png", "image/png"), 
	BMP("bmp", "image/bitmap")

	;

	private final String code;

	private final String name;

	ImageContentType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static ImageContentType get(String code) {
		for (ImageContentType e : ImageContentType.values()) {
			if (e.getCode().equals(code))
				return e;
		}
		return JPG;
	}
}

package com.wasu.ptyw.galaxycrm.core.im4java;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.Data;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 图片连接转为本地图片参数
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
@Data
public class ImageOption {
	private URL url; // 图片连接
	private String imageUrl; // 图片连接
	private String savePath; // 保存路径
	private String fileName; // 文件名
	private String fileSuffix; // 文件后缀
	private Boolean exists; // 是否存在

	public Boolean getExists() {
		if (exists == null) {
			File file = new File(getAbsFile());
			exists = file.exists();
		}
		return exists;
	}

	public String getAbsFile() {
		return savePath + fileName;
	}

	public URL getUrl() {
		if (url == null) {
			try {
				url = new URL(imageUrl);
			} catch (MalformedURLException e) {

			}
		}
		return url;
	}

	public ImageOption() {

	}

	public ImageOption(String imageUrl) {
		String md5 = DigestUtils.md5Hex(imageUrl);
		this.fileSuffix = getSuffix(imageUrl);
		this.imageUrl = imageUrl;

		this.savePath = Im4javaUtil.getRootImgPath() + File.separator + md5.substring(0, 2) + File.separator
				+ md5.substring(2, 4) + File.separator + md5.substring(4, 6) + File.separator;
		this.fileName = md5.substring(6) + "." + fileSuffix;
	}

	public static ImageOption getByPath(String path) {
		ImageOption option = new ImageOption();
		option.fileSuffix = getSuffix(path);
		// 兼容window跟linux
		int index = path.lastIndexOf("/");
		if (index < 0) {
			index = path.lastIndexOf("\\");
		}
		if (index > 0) {
			option.setSavePath(Im4javaUtil.getRootImgPath() + path.substring(0, index + 1));
			option.setFileName(path.substring(index + 1));
		} else {
			option.setSavePath(Im4javaUtil.getRootImgPath());
			option.setFileName(path);
		}
		return option;
	}

	public static String getSuffix(String fileName) {
		String suffix;
		int index = fileName.lastIndexOf(".");
		if (index > 0) {
			suffix = fileName.substring(index + 1);
		} else {
			suffix = "jpg";
		}
		return suffix;
	}
}

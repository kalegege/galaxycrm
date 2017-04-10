/*
 * @(#)ImageZoom.java	1.0 2010-8-10 
 * Copyright Administrator, shi1 Inc. 2010 - All Rights Reserved. 
 */
package com.wasu.ptyw.galaxycrm.web.servlet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @version 1.0 Administrator 2010-8-10
 * @author Administrator
 * @see
 */
public class ImageZoom {
	private Log log = LogFactory.getLog(ImageZoom.class);

	/**
	 * 得到http图片流
	 * 
	 * @param id
	 * @return
	 */
	private byte[] getHttpPic(String key, String id) {
		InputStream in = null;
		HttpURLConnection connection = null;
		byte[] picBuf = new byte[0];
		try {
			URL url = new URL(id);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(10000);
			connection.connect();
			in = connection.getInputStream();
			int len;
			byte[] buf = new byte[30720];// 30k
			while ((len = in.read(buf)) != -1) {
				int piclen = picBuf.length;
				if (piclen == 0) {
					picBuf = new byte[len];
				} else {
					picBuf = Arrays.copyOf(picBuf, piclen + len);
				}
				System.arraycopy(buf, 0, picBuf, piclen, len);
			}
		} catch (Exception e) {
			log.error("读取远程图片异常,id=" + id, e);
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
				if (connection != null) {
					connection.disconnect();
					connection = null;
				}
			} catch (Exception e) {

			}
		}
		return picBuf;
	}

	// 匹配图片地址默认的正则表达式
	private static final String DEFAULT_REGEX = "(i[1-8]/\\p{ASCII}*.jpg)|(i[1-8]/\\p{ASCII}*.jpeg)";

	public String getImageUrl(String imagePath) {
		String imageUrl = "";
		if (imagePath != null) {
			URL url = null;
			try {
				url = new URL(imageUrl);
			} catch (MalformedURLException e) {
				log.error("imageUrl=" + imageUrl, e);
				return null;
			}
			if (imagePath.toLowerCase().matches(DEFAULT_REGEX)) {
				// 是商品图片地址
				String serverName = url.getHost();
				int index = serverName.indexOf(".");
				String target = "";
				String nodeNum = "0" + imagePath.charAt(1);
				target = "img" + nodeNum + serverName.substring(index);
				imageUrl = imageUrl.replace(serverName, target);
			}
		}
		return imageUrl;
	}

	private static final int WIDTH = 200;
	private static final int HEIGHT = 200;

	private static final String UPLOAD_TEMP_PATH = "D:/IMAGE/TEMP";

	/*
	 * 图片放大缩小处理
	 */
	public boolean reduceImage(String imgUrl, long itemNumId, int sortIndex) {
		try {
			URL url = new URL(imgUrl);

			// 载入图片文件
			Image src = ImageIO.read(url);

			int w0 = src.getWidth(null); // 得到源图宽
			int h0 = src.getHeight(null); // 得到源图长
			int x = 0;
			int y = 0;
			int width = 0;
			int height = 0;
			if (w0 > WIDTH) {
				x = WIDTH;
			} else {
				width = (WIDTH - w0) / 2;
			}
			if (h0 > HEIGHT) {
				y = HEIGHT;
			} else {
				height = (HEIGHT - h0) / 2;
			}
			BufferedImage tag = new BufferedImage(WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB);

			// 绘制缩小后的图
			Graphics graphics = tag.getGraphics();			
			if (width > 0 || h0 > 0) {
				//以下2行改变图片背景色
				graphics.setColor(Color.lightGray);
				graphics.fillRect(0, 0, WIDTH, HEIGHT);
			}

			graphics.drawImage(src.getScaledInstance(x, y, Image.SCALE_SMOOTH),
					width, height, null);
			graphics.dispose();

			// 重命名并新建图片
			int nameIndex = imgUrl.lastIndexOf("/");
			if (nameIndex < 1)
				nameIndex = imgUrl.lastIndexOf("\\");
			String imageName = "";

			if (nameIndex >= 0) {
				imageName = imgUrl.substring(nameIndex + 1);
			} else {
				imageName = imgUrl;
			}

			// 生成本地文件
			File indisk = new File(UPLOAD_TEMP_PATH, System.nanoTime()
					+ imageName);
			ImageIO.write(tag, "JPEG", indisk);
			return true;
		} catch (IOException ex) {
			log.error(ex);
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}

}

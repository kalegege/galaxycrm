/*
 * @(#)ImgServlet.java	1.0 2010-7-9
 *
 * Copyright 2010 taobao.com. All rights reserved.
 */


package com.wasu.ptyw.galaxycrm.web.servlet;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 图片代理servlet
 * 
 * @author wenguang
 * @version 1.0, 2010-7-9
 * @since
 */
public class ImgServlet extends HttpServlet {
	/* serialVersionUID */
	private static final long serialVersionUID = 6158369867212334953L;
	private Log log = LogFactory.getLog(ImgServlet.class);
	private static ConcurrentHashMap<String, byte[]> imgCacheMap = new ConcurrentHashMap<String, byte[]>(
			3200);

	private static HashMap<String, String> processingMap = new HashMap<String, String>();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 图片地址
		String id = request.getParameter("id");
		ServletOutputStream out = null;
		try {
			log.info("id=" + id);
			if (id == null || id.length() == 0)
				return;

			String key = getKey(id);
			out = response.getOutputStream();
			String event = request.getParameter("event");
			if ("clear".equals(event)) {
				remove(key);
				out.print("OK");
				return;
			}
			if (!setContentType(response, id)) {
				return;
			}
			
			int i = 0;
			//如果在处理中，等待，避免取多次
			while(processingMap.containsKey(key) && i < 300){
				i++;
				try{
					Thread.sleep(20);
				}catch (Exception e) {
					log.error("线程等待异常",e);
					break;
				}
			}
			byte[] picBytes = get(key);
			
			if (picBytes != null && picBytes.length > 0) {
				log.info("命中cache");
				out.write(picBytes);
			} else {
				log.info("未命中cache");				
				picBytes = getHttpPic(key, id);
				if (picBytes != null && picBytes.length > 0) {				
					out.write(picBytes);
				}
			}
			out.flush();
		} catch (Exception e) {
			log.error("ImgServlet异常,id=" + id, e);
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (Exception e) {

			}
		}

	}

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
		processingMap.put(key, "1");
		try {
			URL url = new URL(id);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
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
			if(picBuf!=null && picBuf.length > 0){
				put(key, picBuf);
			}				
		} catch (Exception e) {
			log.error("读取远程图片异常,id=" + id, e);
		} finally {
			processingMap.remove(key);
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

	public byte[] get(String key) {
		return imgCacheMap.get(key);
	}

	public byte[] put(String key, byte[] value) {
		return imgCacheMap.put(key, value);
	}

	public byte[] remove(String key) {
		return imgCacheMap.remove(key);
	}

	private String getKey(String id) {
		int index = id.lastIndexOf('/');
		if (index > 0 && index < id.length() - 1)
			return id.substring(index + 1);
		return id;
	}

	private boolean setContentType(HttpServletResponse response, String picUrl) {
		String picUrlTemp = picUrl.toLowerCase();
		if (picUrlTemp.endsWith(".gif")) {
			response.setContentType("image/gif");
		} else if (picUrlTemp.endsWith(".jpg")) {
			response.setContentType("image/jpeg");
		} else if (picUrlTemp.endsWith(".jpeg")) {
			response.setContentType("image/jpeg");
		} else if (picUrlTemp.endsWith(".png")) {
			response.setContentType("image/png");
		} else if (picUrlTemp.endsWith(".bmp")) {
			response.setContentType("image/bmp");
		} else {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		ImgServlet is = new ImgServlet();
		String id = "http://img01.taobaocdn.com/tps/i1/T1KBBDXXJOXXXXXXXX-182-106.gif";
		byte[] b = is.getHttpPic(is.getKey(id), id);
		File f = new File("c:/" + is.getKey(id));
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			fos.write(b);
			fos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

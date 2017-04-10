package com.wasu.ptyw.galaxycrm.web.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxycrm.core.im4java.Im4javaUtil;
import com.wasu.ptyw.galaxycrm.core.im4java.ImageContentType;
import com.wasu.ptyw.galaxycrm.core.im4java.ImageOption;
import com.wasu.ptyw.galaxycrm.core.im4java.ResizeOption;
import com.wasu.ptyw.galaxycrm.core.im4java.ResizeOption.ScaleType;

@Controller
@Slf4j
@RequestMapping("/api/image")
public class ImageController {
	@RequestMapping(value = "")
	public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return index(request, response, model);
	}

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new ModelAndView("upload", model);
	}

	/**
	 * 上传图片
	 */
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	@ResponseBody
	public String upload(HttpServletRequest request) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			MultipartFile mfile = null;
			for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
				mfile = set.getValue();
			}
			if (mfile == null)
				return null;
			String imgPath = Im4javaUtil.newAbsFile(mfile.getName());
			FileUtils.writeByteArrayToFile(new File(imgPath), mfile.getBytes());
			return imgPath.replace(Im4javaUtil.getRootImgPath(), "");
		} catch (Exception e) {
			log.error("upload error", e);
		}
		return null;
	}

	/**
	 * 裁剪远程图片
	 */
	@RequestMapping(value = "/resize/{scale:\\d+}_{degree:\\d+}_{width:\\d+}x{height:\\d+}")
	public void resize(@PathVariable("scale") int scale, @PathVariable("degree") int degree,
			@PathVariable("width") int width, @PathVariable("height") int height, HttpServletResponse response,
			HttpServletRequest request) {
		OutputStream output = null;
		try {
			String imageUrl = request.getParameter("url");
			String path = request.getParameter("path");
			if (StringUtils.isEmpty(imageUrl) && StringUtils.isEmpty(path)) {
				return;
			}
			ImageOption imgOption;
			if (StringUtils.isNotEmpty(path)) {
				imgOption = ImageOption.getByPath(path);
			} else {
				imgOption = new ImageOption(imageUrl);
			}

			if (!imgOption.getExists()) {
				FileUtils.copyURLToFile(imgOption.getUrl(), new File(imgOption.getAbsFile()));
				FileUtils.writeLines(new File(Im4javaUtil.getRootImgPath() + "/url.txt"),
						Lists.newArrayList(imgOption.getUrl() + " | " + imgOption.getAbsFile()), true);
			}
			// 设置返回图片类型
			response.setContentType(ImageContentType.get(imgOption.getFileSuffix()).getName());
			// response.setHeader("Content-Disposition", "attachment; filename="
			// + imgOption.getFileName());

			output = response.getOutputStream();
			// 下载图片
			ScaleType scaleType = ScaleType.get(scale);
			ResizeOption option = new ResizeOption(scaleType, degree);
			Im4javaUtil.resize(imgOption.getAbsFile(), output, width, height, option);
		} catch (Exception e) {
			log.error("resize error", e);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

	/**
	 * 获取图片，测试用，一般用nginx实现
	 */
	@RequestMapping(value = "/get")
	public void get(@RequestParam(value = "path") String path, HttpServletResponse response) {
		OutputStream output = null;
		try {
			if (StringUtils.isEmpty(path)) {
				return;
			}
			output = response.getOutputStream();
			File file = new File(Im4javaUtil.getRootImgPath() + path);
			if (file.exists()) {
				// 设置返回图片类型
				response.setContentType(ImageContentType.get(ImageOption.getSuffix(path)).getName());
				// response.setHeader("Content-Disposition",
				// "attachment; filename=" + file.getName());
				output = response.getOutputStream();
				IOUtils.write(FileUtils.readFileToByteArray(file), output);
			}
		} catch (Exception e) {
			log.error("get error", e);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

}

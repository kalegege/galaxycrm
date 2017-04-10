package com.wasu.ptyw.galaxycrm.core.im4java;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.StopWatch;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;
import org.im4java.process.StandardStream;

import com.wasu.ptyw.galaxy.common.util.DateUtil;
import com.wasu.ptyw.galaxy.common.util.PropertiesUtil;
import com.wasu.ptyw.galaxycrm.core.im4java.ResizeOption.ScaleType;
import com.wasu.ptyw.galaxycrm.core.im4java.resize.ResizeChooser;
import com.wasu.ptyw.galaxycrm.core.im4java.rotate.RotateEditor;

/**
 * im4java图片工具类
 * 
 * @author wenguang
 * @date 2015年7月15日
 */
@Slf4j
public class Im4javaUtil {
	private static final String GraphicsMagick_Path = "C:\\Program Files\\GraphicsMagick-1.3.21-Q8";
	// private static final String GraphicsMagick_Path =
	// "C:\\Program Files (x86)\\GraphicsMagick-1.3.21-Q8";

	private static String rootImgPath = null;

	private static String PATH_FORMAT;
	private static AtomicInteger fileIndex = new AtomicInteger(1);

	static {
		PATH_FORMAT = File.separator + "yy" + File.separator + "MM" + File.separator + "dd" + File.separator;
	}

	public static int getFileIndex() {
		int index = fileIndex.getAndIncrement();
		if (index > 98)
			fileIndex.set(1);
		return index;
	}

	/**
	 * 获取图片存放的根目录
	 * 
	 */
	public static String getRootImgPath() {
		if (rootImgPath == null) {
			rootImgPath = PropertiesUtil.getValue("root_img_path");
			File dir = new File(rootImgPath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			if (rootImgPath.endsWith(File.separator)) {
				rootImgPath = rootImgPath.substring(0, rootImgPath.length() - 1);
			}
		}
		return rootImgPath;
	}

	/**
	 * 获取图片路径
	 */
	public static String getImgPath() {
		return getRootImgPath() + DateUtil.now(PATH_FORMAT);
	}

	/**
	 * 生成新的文件,带绝对路径
	 * 
	 */
	public static String newAbsFile(String fileName) {
		String fileSuffix;
		int index = fileName.lastIndexOf(".");
		if (index > 0) {
			fileSuffix = fileName.substring(index);
		} else {
			fileSuffix = ".jpg";
		}
		return getImgPath() + DateUtil.now(DateUtil.FORMAT_7) + "_" + getFileIndex() + fileSuffix;
	}

	/**
	 * 压缩图片
	 */
	public static boolean compress(String srcPath, String destPath) {
		try {
			IMOperation op = new IMOperation();
			op.addImage(srcPath);
			op.addImage(destPath);
			ConvertCmd convert = new ConvertCmd(true);
			if (SystemUtils.IS_OS_WINDOWS) {
				convert.setSearchPath(GraphicsMagick_Path);
			}
			convert.run(op);
			return true;
		} catch (Exception e) {
			log.error("compress error, srcPath=" + srcPath + ",destPath=" + destPath, e);
		}
		return false;
	}

	/**
	 * 缩放图片，保存到文件
	 */
	public static boolean resize(String srcPath, String destPath, int width, int height, ResizeOption option) {
		try {
			Im4jParam param = buildIm4jParam(srcPath, width, height);

			// 拼装修改尺寸命令
			boolean isResized = ResizeChooser.exec(param, option);
			// param.op.resize(param.destW,param.destH);

			// 拼装旋转图片命令
			boolean isRotated = new RotateEditor(param, option.getDegree()).exec();

			// 没修改图片
			if (!isResized && !isRotated) {
				FileUtils.moveFile(new File(srcPath), new File(destPath));
				return true;
			}
			// param.op.addRawArgs("-quality", "100");
			IMOperation op = param.op;
			op.addImage(destPath);
			log.info("[resize#op]" + param.op);

			ConvertCmd convert = new ConvertCmd(true);
			if (SystemUtils.IS_OS_WINDOWS) {
				convert.setSearchPath(GraphicsMagick_Path);
			}

			convert.setErrorConsumer(StandardStream.STDERR);
			convert.run(op);
			return true;
		} catch (Exception e) {
			log.error("resize error, srcPath=" + srcPath + ",destPath=" + destPath + ",size=" + width + "x" + height, e);
		}
		return false;
	}

	/**
	 * 缩放图片，输出到外部流
	 */
	public static boolean resize(String srcPath, OutputStream out, int width, int height, ResizeOption option) {
		try {
			Im4jParam param = buildIm4jParam(srcPath, width, height);

			// 拼装修改尺寸命令
			boolean isResized = ResizeChooser.exec(param, option);
			// param.op.resize(param.destW,param.destH);

			// 拼装旋转图片命令
			boolean isRotated = new RotateEditor(param, option.getDegree()).exec();

			// 没修改图片
			if (!isResized && !isRotated) {
				out.write(FileUtils.readFileToByteArray(new File(srcPath)));
				out.flush();
				return true;
			}
			// param.op.addRawArgs("-quality", "100");
			IMOperation op = param.op;
			op.addImage("jpg:-");
			log.info("[resize#op]" + param.op);

			ConvertCmd convert = new ConvertCmd(true);
			if (SystemUtils.IS_OS_WINDOWS) {
				convert.setSearchPath(GraphicsMagick_Path);
			}

			Pipe pipeOut = new Pipe(null, out);
			convert.setOutputConsumer(pipeOut);

			convert.setErrorConsumer(StandardStream.STDERR);
			convert.run(op);
			return true;
		} catch (Exception e) {
			log.error("resize error, srcPath=" + srcPath + ",size=" + width + "x" + height, e);
		}
		return false;
	}

	private static Im4jParam buildIm4jParam(String srcPath, int width, int height) throws IOException {
		File srcFile = new File(srcPath);
		BufferedImage bi = ImageIO.read(srcFile);
		int srcW = bi.getWidth();
		int srcH = bi.getHeight();

		IMOperation op = new IMOperation();
		op.addImage(srcPath);

		Im4jParam param = new Im4jParam(op, srcH, srcW, width, height);
		return param;
	}

	public static void main(String[] args) throws Exception {
		StopWatch sw =  new StopWatch();
		sw.start();
		
		int width = 100, height = 200;
		String wh = "_" + width + "x" + height;
		String srcPath = "d:\\data\\a.jpg";
		String destPath = "d:\\a_compress.jpg";

		compress(srcPath, destPath);

		destPath = srcPath + wh + "_ratio.jpg";
		ResizeOption option = new ResizeOption(ScaleType.BY_RATIO, 0);
		resize(srcPath, destPath, width, height, option);

		destPath = srcPath + wh + "_cut.jpg";
		option = new ResizeOption(ScaleType.CUT, 0);
		resize(srcPath, destPath, width, height, option);

		destPath = srcPath + wh + "_width.jpg";
		option = new ResizeOption(ScaleType.BY_WIDTH, 0);
		resize(srcPath, destPath, width, height, option);

		destPath = srcPath + wh + "_height.jpg";
		option = new ResizeOption(ScaleType.BY_HEIGHT, 0);
		resize(srcPath, destPath, width, height, option);

		destPath = srcPath + wh + "_default.jpg";
		option = new ResizeOption(ScaleType.DEFAULT, 0);
		resize(srcPath, destPath, width, height, option);
		
		System.out.println(sw.getTime());
		// resize(srcPath, new FileOutputStream(new File(destPath)), width,
		// height, option2);
	}

}

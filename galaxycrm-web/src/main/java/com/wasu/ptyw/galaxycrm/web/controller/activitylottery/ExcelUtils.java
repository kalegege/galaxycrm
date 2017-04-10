package com.wasu.ptyw.galaxycrm.web.controller.activitylottery;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel操作的工具类
 * @author quxy
 */
public class ExcelUtils {
	
	/**
	 * EXCEL导方工具方法
	 * @param excelName 生成的EXCEL的文件名
	 * @param sheetName 工作单的名称
	 * @param titleHeander 标题行
	 * @param lists 导出的数据
	 * @param request 请求
	 * @param response 响应
	 */
	public static void export(String excelName, String sheetName,
					String[] titleHeander, List<?> lists,
					HttpServletRequest request,
					HttpServletResponse response) {
		try {
		 System.setProperty("java.awt.headless", "true");
		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建工作单
		HSSFSheet sheet = wb.createSheet(sheetName);
		
		// 创建标题行(第一行作为标题行)
		HSSFRow row =sheet.createRow(0);
		// 循环创建第一行中的单元格
		for (int i = 0; i < titleHeander.length; i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(titleHeander[i]);
		}
		
			//================导出的数据行====================
			for (int i = 0; i < lists.size(); i++){
				// 循环创建行
				row = sheet.createRow(i + 1);
				// 循环创建单元格
				Object obj = lists.get(i);
				// 获取该实体中所有属性
				Field[] fields = obj.getClass().getDeclaredFields();
				for (int j = 0; j < fields.length; j++){
					Field field = fields[j];
					// 创建单元格
					HSSFCell cell = row.createCell(j);
					if (!field.isAccessible()){
						field.setAccessible(true);
					}
					Object res = field.get(obj);
					cell.setCellValue(res != null ? res.toString() : "");
				}
			}
			// 判断浏览器类型
			String userAgent = request.getHeader("user-agent");
			System.out.println(userAgent);
			if (userAgent.toLowerCase().indexOf("msie") != -1){ // IE
				excelName = URLEncoder.encode(excelName, "utf-8");
			}else{
				excelName = new String(excelName.getBytes("UTF-8"), "ISO8859-1");
			}
			response.setHeader("Content-Disposition", "attachment;filename="+ excelName +".xls");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

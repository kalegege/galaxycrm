package com.wasu.ptyw.galaxycrm.web.utile;


import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class OfficeUtile {

	/**
	 * 读取exlx数据
	 */
	public static List<ArrayList<String>> readExl(InputStream in,String fileType) throws IOException {
		 Workbook wb = null;
		 if (fileType.equals(".xls")) {
		 wb = new HSSFWorkbook(in);
		 }
		 else if(fileType.equals(".xlsx"))
		 {
		 wb = new XSSFWorkbook(in);
		 }
//		Workbook wb = new XSSFWorkbook(in);
		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();
		Sheet sheet = wb.getSheetAt(0);// 得到第一个shell
		int totalRows = sheet.getPhysicalNumberOfRows();
		int totalCells = 0;
		for(int q=0;q<totalRows;q++){
			if (totalRows >= 1 && sheet.getRow(q) != null) {
				totalCells =totalCells> sheet.getRow(q).getPhysicalNumberOfCells() ? totalCells: sheet.getRow(q).getPhysicalNumberOfCells();
			}
		}

		// 循环Excel的行
		for (int r = 0; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			ArrayList<String> rowLst = new ArrayList<String>();
			// 循环Excel的列
			for (short c = 0; c < totalCells; c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";
				if (cell == null) {
					rowLst.add(cellValue);
					continue;
				}
				// 处理数字型的,自动去零
				if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
					// 在excel里,日期也是数字,在此要进行判断
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						cellValue = new SimpleDateFormat("yyyyMMddHHmm").format(cell.getDateCellValue());
					} else {
						cellValue = getRightStr(cell.getNumericCellValue() + "");
					}
				} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {// 处理字符串型
					cellValue = cell.getStringCellValue();
				} else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {// 处理布尔型
					cellValue = cell.getBooleanCellValue() + "";
				} else {// 其它数据类型
					cellValue = cell.toString() + "";
				}

				rowLst.add(cellValue);
			}
			dataLst.add(rowLst);
		}
		return dataLst;
	}

	/**
	 * 正确地处理exle整数后自动加零的情况
	 */
	private static String getRightStr(String sNum) {
		DecimalFormat decimalFormat = new DecimalFormat("#.000000");
		String resultStr = decimalFormat.format(new Double(sNum));
		if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
			resultStr = resultStr.substring(0, resultStr.indexOf("."));
		}
		return resultStr;
	}
	
}

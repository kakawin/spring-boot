package com.kakawin.gis.springboot.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.FileCopyUtils;

import com.kakawin.gis.springboot.utils.FileUtil;
import com.kakawin.gis.springboot.utils.excel.annotation.ExportField;


public class ExportExcel {

	private Workbook workbook;
	private Sheet sheet;
	private CellStyle dateStyle;
	private CellStyle textStyle;
	private int rownum;

	public ExportExcel() {
		init(null);
	}

	public ExportExcel(File template) {
		init(template);
	}

	public <E> ExportExcel(List<E> datas, int group, File template) {
		init(template);
		if (datas == null || datas.size() == 0) {
			return;
		}
		fillRows(datas, group);
	}

	public <E> ExportExcel(List<E> datas, int group) {
		init(null);
		if (datas == null || datas.size() == 0) {
			return;
		}
		Class<?> clazz = datas.get(0).getClass();
		String[] headerNames = listHeaderNames(clazz, group);
		fillRow(headerNames);
		fillRows(datas, group);
	}

	public <E> ExportExcel(String[] headerNames, List<E> datas, int group) {
		init(null);
		fillRow(headerNames);
		fillRows(datas, group);
	}

	public ExportExcel(String[] headerNames) {
		init(null);
		fillRow(headerNames);
	}

	protected void init(File template) {
		if (template == null) {
			this.workbook = new HSSFWorkbook();
			this.sheet = workbook.createSheet();
		} else {
			String templateName = template.getName();
			File export = FileUtil.createTempFile(templateName);
			try {
				FileCopyUtils.copy(template, export);
				InputStream is = new FileInputStream(export);
				if (templateName.toLowerCase().endsWith(".xls")) {
					this.workbook = new HSSFWorkbook(is);
				} else if (templateName.toLowerCase().endsWith(".xlsx")) {
					this.workbook = new XSSFWorkbook(is);
				}
				this.sheet = this.workbook.getSheetAt(0);
				this.rownum = this.sheet.getLastRowNum() + 1;
			} catch (IOException e) {
				e.printStackTrace();
				init(null);
			}
		}
	}

	protected <E> Object[] toArray(E data, String[] fieldNames) {
		Object[] array = new Object[fieldNames.length];
		if (data == null) {
			return null;
		}
		Class<?> clazz = data.getClass();
		for (int i = 0; i < fieldNames.length; i++) {
			String fieldName = fieldNames[i];
			if (fieldName == null) {
				break;
			}
			try {
				Object value = PropertyUtils.getProperty(data, fieldName);
				ExportField exportField = clazz.getDeclaredField(fieldName).getAnnotation(ExportField.class);
				if (value != null && exportField != null && exportField.mappings().length > 0) {
					for (String mapping : exportField.mappings()) {
						String[] split = mapping.split(":");
						String key = split[0];
						String val = split[1];
						if (key.equals(value.toString())) {
							value = val;
							break;
						}
					}
				}
				array[i] = value;
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException
					| SecurityException e) {
			}
		}
		return array;
	}

	public <E> void fillRow(Object[] dataRow) {
		Row row = sheet.createRow(rownum++);
		if (dataRow == null) {
			return;
		}
		for (int i = 0; i < dataRow.length; i++) {
			Cell cell = row.createCell(i);
			setCellValue(cell, dataRow[i]);
		}
	}

	public <E> void fillRows(List<E> datas, int group) {
		if (datas == null || datas.size() == 0) {
			return;
		}
		Class<?> clazz = datas.get(0).getClass();
		String[] fieldNames = listFieldNames(clazz, group);
		for (E data : datas) {
			Object[] array = toArray(data, fieldNames);
			fillRow(array);
		}
	}

	public <E> void fillRows(Object[][] dataRows) {
		for (int i = 0; i < dataRows.length; i++) {
			fillRow(dataRows[i]);
		}
	}

	protected CellStyle getDateStyle() {
		if (dateStyle == null) {
			DataFormat format = workbook.createDataFormat();
			dateStyle = workbook.createCellStyle();
			dateStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
		}
		return dateStyle;
	}

	protected CellStyle getTextStyle() {
		if (textStyle == null) {
			DataFormat format = workbook.createDataFormat();
			textStyle = workbook.createCellStyle();
			textStyle.setDataFormat(format.getFormat("@"));
		}
		return textStyle;
	}

	protected <E> String[] listFieldNames(Class<E> clazz, int group) {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		int maxIndex = 0;
		for (Field field : fields) {
			ExportField exportField = field.getAnnotation(ExportField.class);
			if (exportField != null) {
				for (int g = 0; g < exportField.groups().length; g++) {
					if (group == exportField.groups()[g]) {
						String fieldName = field.getName();
						int column = exportField.columns()[g];
						if (maxIndex < column) {
							maxIndex = column;
						}
						fieldNames[column] = fieldName;
						break;
					}
				}
			}
		}
		return Arrays.copyOfRange(fieldNames, 0, maxIndex + 1);
	}

	public String[] listHeaderNames(Class<?> clazz, int group) {
		Field[] fields = clazz.getDeclaredFields();
		String[] headerNames = new String[fields.length];
		int maxIndex = 0;
		for (Field field : fields) {
			ExportField exportField = field.getAnnotation(ExportField.class);
			if (exportField != null) {
				for (int g = 0; g < exportField.groups().length; g++) {
					if (group == exportField.groups()[g]) {
						int column = exportField.columns()[g];
						if (maxIndex < column) {
							maxIndex = column;
						}
						headerNames[column] = exportField.title();
						break;
					}
				}
			}
		}
		return Arrays.copyOfRange(headerNames, 0, maxIndex + 1);
	}

	protected void setCellValue(Cell cell, Object value) {
		try {
			if (value == null) {
				cell.setCellValue("");
			} else if (value instanceof String) {
				cell.setCellStyle(getTextStyle());
				cell.setCellValue((String) value);
			} else if (value instanceof Integer) {
				cell.setCellValue(new BigDecimal(value.toString()).toPlainString());
			} else if (value instanceof Long) {
				cell.setCellValue(new BigDecimal(value.toString()).toPlainString());
			} else if (value instanceof Double) {
				cell.setCellValue(new BigDecimal(value.toString()).toPlainString());
			} else if (value instanceof Float) {
				cell.setCellValue(new BigDecimal(value.toString()).toPlainString());
			} else if (value instanceof Date) {
				cell.setCellStyle(getDateStyle());
				cell.setCellValue((Date) value);
			}
		} catch (Exception e) {
			cell.setCellValue(value.toString());
		}
	}

	public void write(File file) throws IOException {
		write(new FileOutputStream(file));
	}

	public void write(OutputStream outputStream) throws IOException {
		try {
			this.workbook.write(outputStream);
			this.workbook.close();
		} finally {
			outputStream.close();
		}
	}
}
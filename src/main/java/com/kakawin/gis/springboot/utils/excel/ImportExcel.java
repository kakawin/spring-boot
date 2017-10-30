package com.kakawin.gis.springboot.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kakawin.gis.springboot.utils.excel.annotation.ImportField;

public class ImportExcel {
	private Workbook workbook;

	private Sheet sheet;

	private int headerIndex;

	public ImportExcel(MultipartFile multipartFile, int headerIndex, int sheetIndex)
			throws InvalidFormatException, IOException {
		this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerIndex, sheetIndex);
	}

	public ImportExcel(File file, int headerIndex, int sheetIndex) throws InvalidFormatException, IOException {
		this(file.getName(), new FileInputStream(file), headerIndex, sheetIndex);
	}

	public ImportExcel(String fileName, InputStream is, int headerIndex, int sheetIndex)
			throws InvalidFormatException, IOException {
		if (StringUtils.isEmpty(fileName)) {
			throw new RuntimeException("导入文档为空!");
		} else if (fileName.toLowerCase().endsWith(".xls")) {
			this.workbook = new HSSFWorkbook(is);
		} else if (fileName.toLowerCase().endsWith(".xlsx")) {
			this.workbook = new XSSFWorkbook(is);
		} else {
			throw new RuntimeException("文档格式不正确!");
		}
		if (this.workbook.getNumberOfSheets() < sheetIndex) {
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheet = this.workbook.getSheetAt(sheetIndex);
		this.headerIndex = headerIndex;
	}

	public Row getRow(int rownum) {
		return this.sheet.getRow(rownum);
	}

	public int getFirstDataRowNum() {
		return this.headerIndex + 1;
	}

	public int getLastDataRowNum() {
		return this.sheet.getLastRowNum();
	}

	/**
	 * @return 返回结果是总列数+1
	 */
	public int getLastDataCellNum() {
		return this.getRow(headerIndex).getLastCellNum();
	}

	public Object getCellValue(Row row, int column) {
		Object value = null;
		Cell cell = row.getCell(column);
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				value = new BigDecimal(cell.getNumericCellValue()).toPlainString();
				break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				if (StringUtils.isEmpty(value.toString())) {
					value = null;
				}
				break;
			case Cell.CELL_TYPE_FORMULA:
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				value = new BigDecimal(cell.getNumericCellValue()).toPlainString();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_ERROR:
				break;
			default:
				break;
			}
		}
		return value;
	}

	public <E> ImportResult<E> listData(Class<E> clazz, String[] fieldNames, boolean exceptionInterrupt) {
		List<E> datas = new ArrayList<E>();
		List<String> exceptionRecords = new ArrayList<String>();
		for (int rownum = this.getFirstDataRowNum(); rownum <= this.getLastDataRowNum(); rownum++) {
			int cellnum = 0;
			try {
				Row row = this.getRow(rownum);
				E bean = clazz.newInstance();
				int emptyCell = 0;
				for (; cellnum < fieldNames.length; cellnum++) {
					String fieldName = fieldNames[cellnum];
					if (StringUtils.isEmpty(fieldName)) {
						emptyCell++;
						continue;
					}
					Object value = this.getCellValue(row, cellnum);
					if (value == null) {
						emptyCell++;
						continue;
					}
					Field field = clazz.getDeclaredField(fieldName);
					ImportField importField = field.getAnnotation(ImportField.class);
					if (importField != null && importField.mappings().length > 0) {
						boolean mapped = false;
						for (String mapping : importField.mappings()) {
							String[] split = mapping.split(":");
							String key = split[0];
							String val = split[1];
							if (key.equals(value)) {
								value = val;
								mapped = true;
								break;
							}
						}
						if (!mapped) {
							throw new RuntimeException("no mapped!");
						}
					}
					Class<?> fieldType = field.getType();
					if (fieldType == String.class) {
						value = String.valueOf(value.toString());
						PropertyUtils.setProperty(bean, fieldName, value);
					} else if (fieldType == Integer.class) {
						value = Double.valueOf(value.toString()).intValue();
						PropertyUtils.setProperty(bean, fieldName, value);
					} else if (fieldType == Long.class) {
						value = Double.valueOf(value.toString()).longValue();
						PropertyUtils.setProperty(bean, fieldName, value);
					} else if (fieldType == Double.class) {
						value = Double.parseDouble(value.toString());
						PropertyUtils.setProperty(bean, fieldName, value);
					} else if (fieldType == Float.class) {
						value = Float.valueOf(value.toString());
						PropertyUtils.setProperty(bean, fieldName, value);
					} else if (fieldType == Date.class) {
						value = DateUtil.getJavaDate(Double.parseDouble(value.toString()));
						PropertyUtils.setProperty(bean, fieldName, value);
					} else {
						BeanUtils.setProperty(bean, fieldName, value);
					}
				}
				if (emptyCell == fieldNames.length) {
					continue;
				}
				datas.add(bean);
			} catch (Exception e) {
				exceptionRecords.add("( " + (headerIndex + 1 + rownum) + " , " + (cellnum + 1) + " )");
				if (exceptionInterrupt) {
					break;
				}
			}
		}
		return new ImportResult<E>(datas, exceptionRecords);
	}

	public <E> ImportResult<E> listData(Class<E> clazz, int group, boolean exceptionInterrupt) {
		String[] fieldNames = listFieldNames(clazz, group);
		return this.listData(clazz, fieldNames, exceptionInterrupt);
	}

	private <E> String[] listFieldNames(Class<E> clazz, int group) {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		int maxIndex = 0;
		for (Field field : fields) {
			ImportField importField = field.getAnnotation(ImportField.class);
			if (importField != null) {
				for (int g = 0; g < importField.groups().length; g++) {
					if (group == importField.groups()[g]) {
						String fieldName = field.getName();
						int column = importField.columns()[g];
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
}
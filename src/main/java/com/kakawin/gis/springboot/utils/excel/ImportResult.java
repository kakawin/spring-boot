package com.kakawin.gis.springboot.utils.excel;

import java.util.List;

public class ImportResult<T> {

	private List<T> datas;
	private List<String> records;

	public ImportResult(List<T> datas, List<String> records) {
		this.datas = datas;
		this.records = records;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public List<String> getRecords() {
		return records;
	}

	public void setRecords(List<String> records) {
		this.records = records;
	}

	public boolean hasError() {
		return records != null && (records.size() > 0);
	}

	public String toErrorString() {
		StringBuilder sb = new StringBuilder("");
		if (hasError()) {
			for (String record : records) {
				sb.append(record);
			}
		}
		return sb.toString();
	}

}

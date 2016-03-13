package com.zzia.wngn.excel;

import java.util.Map;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelWraper {

	private WritableWorkbook wwb;
	private WritableSheet ws;
	private Map<Integer, String> columnNames;
	private int columnLength;
	private int rows = 0;

	public WritableWorkbook getWwb() {
		return wwb;
	}

	public void initColumn(Map<Integer, String> columns) {
		this.columnNames = columns;
		this.columnLength = columns.size();
	}

	public void setWwb(WritableWorkbook wwb) {
		this.wwb = wwb;
	}

	public WritableSheet getWs() {
		return ws;
	}

	public void setWs(WritableSheet ws) {
		this.ws = ws;
	}

	public Map<Integer, String> getColumnNames() {
		return columnNames;
	}

	public int getColumnLength() {
		return columnLength;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}

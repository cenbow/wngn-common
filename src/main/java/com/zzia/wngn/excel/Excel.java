package com.zzia.wngn.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * excel操作
 * @author v_wanggang 2015年11月4日
 *
 */
public class Excel {

	protected String fileName;
	protected String rootPath;
	protected String filePath;
	protected String sheetName;
	protected String currentSheetName;
	protected ExcelWraper excelWraper;
	protected Map<Integer, String> columns;
	protected Map<Integer,String> types;
	protected List<Map<Integer, Object>> excelData = new ArrayList<Map<Integer, Object>>();
	protected OutputStream outputStream;
	protected int size = 0;
	protected int sheetIndex = 0;
	protected final static int MAXSIZE = 65535;
	protected boolean fileModel = false;
	public Excel setFileName(String fileName){
		if(filePath!=null){
			this.filePath = null;
			this.rootPath = null;
			this.fileName = null;
		}
		this.fileName = fileName;
		if(rootPath!=null){
			this.filePath = rootPath +"/"+ fileName;
		}
		return this;
	}
	
	public Excel setRootPath(String rootPath){
		if(filePath!=null){
			this.filePath = null;
			this.rootPath = null;
			this.fileName = null;
		}
		this.rootPath = rootPath;
		if(fileName!=null){
			this.filePath = rootPath +"/"+ fileName;
		}
		return this;
	}
	
	public Excel setFilePath(String filePath){
		this.filePath = filePath;
		return this;
	}
	
	public Excel setSheetName(String sheetName){
		this.sheetName = sheetName;
		return this;
	}
	
	public Excel setColumn(String... strs){
		if(strs.length<1){
			throw new RuntimeException("ERROR:excel at least one column.");
		}
		this.columns = new HashMap<Integer, String>();
    	int i = 0;
		for (String str : strs) {
			if(str==null||"".equals(str)){
				throw new RuntimeException("ERROR:excel column name not null.");
			}
			columns.put(i++, str);
		}
		return this;
	}
	
	public Excel setType(String... strs){
		if(strs.length<1){
			throw new RuntimeException("ERROR:excel at least one column type.");
		}
		if(this.types!=null){
			throw new RuntimeException("ERROR:Data types have been set, are not allowed to repeat settings.");
		}
		this.types = new HashMap<Integer, String>();
    	int i = 0;
		for (String str : strs) {
			if(str==null||"".equals(str)){
				types.put(i++, CellType.STRING.value);
				continue;
			}
			if(!CellType.containsValue(str)){
				throw new RuntimeException("ERROR:Only support data type：string,number,boolean,datetime.");
			}
			types.put(i++, str);
		}
		return this;
	}
	
	public Excel setType(CellType... cellTypes){
		if(cellTypes.length<1){
			throw new RuntimeException("ERROR:excel at least one column type.");
		}
		if(this.types!=null){
			throw new RuntimeException("ERROR:Data types have been set, are not allowed to repeat settings.");
		}
		this.types = new HashMap<Integer, String>();
    	int i = 0;
		for (CellType cellType : cellTypes) {
			types.put(i++, cellType.value);
		}
		return this;
	}
	
	public Excel setSameType(CellType cellType){
		if(this.types!=null){
			throw new RuntimeException("ERROR:Data types have been set, are not allowed to repeat settings.");
		}
		if(this.columns==null){
			throw new RuntimeException("ERROR:Please first set the column.");
		}
		this.types = new HashMap<Integer, String>();
		for (int i = 0;i<this.columns.size();i++) {
			types.put(i, cellType.value);
		}
		return this;
	}
	
	public Excel setSameType(String value){
		if(this.types!=null){
			throw new RuntimeException("ERROR:Data types have been set, are not allowed to repeat settings.");
		}
		if(this.columns==null){
			throw new RuntimeException("ERROR:Please first set the column.");
		}
		if(!CellType.containsValue(value)){
			throw new RuntimeException("ERROR:Only support data type：string,number,boolean,datetime.");
		}
		this.types = new HashMap<Integer, String>();
		for (int i = 0;i<this.columns.size();i++) {
			types.put(i, value);
		}
		return this;
	}
	
	public Excel setFileModel(boolean fileModel){
		this.fileModel = fileModel;
		return this;
	}
	
	
	
	/**
	 * 创建Excel表格对象
	 * @return
	 */
	public Excel create(){
		this.verification();
		try {
			this.excelWraper = new ExcelWraper();
			this.excelWraper.initColumn(this.columns);
			File fileWrite = new File(this.filePath);
			if(fileWrite.exists()&&this.fileModel){
				throw new RuntimeException("ERROR:File["+this.filePath+"] already exists.");
			}
			if(fileWrite.exists()){
				fileWrite.delete();
			}
			fileWrite.createNewFile();
			outputStream = new FileOutputStream(fileWrite);
			WritableWorkbook wwb = Workbook.createWorkbook(outputStream);
			this.excelWraper.setWwb(wwb);
			this.createSheet();
			return this;
		} catch (Exception e) {
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e1) {
					e.printStackTrace();
				}
			}
			throw new RuntimeException("ERROR:create excel document error.",e);
		}
	}
	
	/**
	 * 创建sheet页
	 */
	private void createSheet(){
		try {
			if(this.sheetIndex==0){
				this.sheetIndex++;
				this.currentSheetName = this.sheetName;
			}else{
				this.currentSheetName = this.sheetName + "-" + this.sheetIndex;
				this.sheetIndex++;
			}
			this.excelWraper.setRows(0);
			WritableSheet ws = this.excelWraper.getWwb().createSheet(this.currentSheetName, this.sheetIndex);
			for (int i = 0; i < this.excelWraper.getColumnNames().size(); i++) {
				ws.addCell(new Label(i, 0, this.excelWraper.getColumnNames().get(i)));
			}
			this.excelWraper.setWs(ws);
		} catch (Exception e) {
			throw new RuntimeException("ERROR:create excel document error.",e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Excel add() {
		if(this.size == 0){
			return this;
		}
		if(this.types!=null&&this.types.size()!=this.columns.size()){
			throw new RuntimeException("ERROR:Data type number is not equal to number of columns.");
		}
		for (Map<Integer, Object> record : this.excelData) {
			this.refushRows();
			for (int i = 0; i < this.excelWraper.getColumnLength(); i++) {
				addCell(i, record.get(i));
			}
		}
		this.excelData.clear();
		this.size = 0;
		return this;
	}
	
	public Excel add(List<Object[]> data) {
		if(this.types!=null&&this.types.size()!=this.columns.size()){
			throw new RuntimeException("ERROR:Data type number is not equal to number of columns.");
		}
		if(data==null){
			return this;
		}
		for (Object[] records : data) {
			if(this.columns.size()!=records.length){
				throw new RuntimeException("ERROR:Data number is not equal to number of columns.");
			}
			this.refushRows();
			for (int i = 0; i < this.excelWraper.getColumnLength(); i++) {
				addCell(i, records[i]);
			}
		}
		this.excelData.clear();
		this.size = 0;
		return this;
	}
	
	private void refushRows() {
		if( this.excelWraper.getRows()>=MAXSIZE){
			this.createSheet();
		}
		this.excelWraper.setRows(this.excelWraper.getRows()+1);
	}
	
	public void addCell(int i,Object record) {
		try {
			if(this.types==null){
				this.excelWraper.getWs().addCell(new Label(i, this.excelWraper.getRows(), record!=null?record.toString():""));
			}else{
				this.excelWraper.getWs().addCell(this.getCell(i, this.excelWraper.getRows(), i, record));
			}
		} catch (Exception e) {
			throw new RuntimeException("ERROR:add cell error.",e);
		}
	}
	
	
	public boolean write() {
		try {
			this.excelWraper.getWwb().write();
			this.excelWraper.getWwb().close();
			this.outputStream.close();
			return true;
		} catch (Exception e) {
			throw new RuntimeException("ERROR:write data error.",e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Excel addOneElement(Object... objs){
		 addElement(objs);
		 return this;
	}
	
	public Excel addElement(List<Object[]> data){
		for (Object[] objs : data) {
			addElement(objs);
		}
		return this;
	}
	
	public int addElement(Object... objs){
		Map<Integer, Object> data = new HashMap<Integer, Object>();
		int i = 0;
		for (Object object : objs) {
			data.put(i++, object);
		}
		this.excelData.add(data);
		return ++this.size;
	}
	
	private void verification(){
		if(this.filePath == null){
			throw new RuntimeException("ERROR:filePath not null.");
		}
		if(this.sheetName == null){
			throw new RuntimeException("sheetName not null.");
		}
		if(this.columns == null){
			throw new RuntimeException("columns not null.");
		}
	}
	
	private WritableCell getCell(int column,int row,int key,Object value){
		if(CellType.STRING.value.equals(this.types.get(key))){
			return new Label(column,row,value!=null?value.toString():"");
		}else if(CellType.NUMBER.value.equals(this.types.get(key))){
			if(value==null){
				throw new RuntimeException("ERROR:[column "+column+",row "+row+"] data not null,it is the Number.");
			}
			if(value instanceof Number){
				Number number = (Number)value;
				return new jxl.write.Number(column,row, number.doubleValue());
			}else{
				throw new RuntimeException("ERROR:[column "+column+",row "+row+"] data type not incorrect,it should be the Number."
						+ "Please check whether data type and specify the type of consistent, or type of data and the first line");
			}
		}else if(CellType.BOOLEAN.value.equals(this.types.get(key))){
			if(value==null){
				return new jxl.write.Boolean(column,row, Boolean.FALSE);
			}
			if(value instanceof Boolean){
				return new jxl.write.Boolean(column,row, (Boolean)value);
			}else{
				throw new RuntimeException("ERROR:[column "+column+",row "+row+"] data type not incorrect,it should be the Boolean."
						+ "Please check whether data type and specify the type of consistent, or type of data and the first line");
			}
		}else if(CellType.DATETIME.value.equals(this.types.get(key))){
			if(value==null){
				return new jxl.write.DateTime(column,row, null);
			}
			if(value instanceof Date){
				return new jxl.write.DateTime(column,row, (Date)value);
			}else{
				throw new RuntimeException("ERROR:[column "+column+",row "+row+"] data type not incorrect,it should be the Date."
						+ "Please check whether data type and specify the type of consistent, or type of data and the first line");
			}
		}
		return null;
	}
	
}

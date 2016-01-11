package com.fruitbasket.attendance;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public final class ExcelWriter  {

	private ExcelWriter(){}
	
	public static void writeData(File excelFile,String[][] data)
			throws Exception {
		writeData(excelFile,null,data,null,0,data.length-1);
	}

	/**
	 * 
	 * @param excelFile
	 * @param header
	 * @param data
	 * @param footer
	 * @param startRow
	 * @param endRow
	 * @throws Exception
	 */
	public static void writeData(File excelFile,String[] header,String[][] data,String[] footer,int startRow,int endRow)
			throws Exception {
		if(excelFile==null||data==null){
			throw new Exception("excelFile or data is null !");
		}
		else if(startRow<0||endRow>data.length){
			throw new Exception("startRow or endRow is out of range");
		}
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("sheet");
		HSSFRow row;
		HSSFCell cell;
		int newRowIndex;
		int sourceRowIndex;
		int listIndex;
		int listLength=data[0].length;
		
		if(header==null){
			newRowIndex=0;
		}
		else{
			//header
			newRowIndex=1;
			row=sheet.createRow((short)0);
			for(listIndex=0;listIndex<listLength;++listIndex){
				cell=row.createCell((short)listIndex);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(header[listIndex]);
			}
		}
		
		for(sourceRowIndex=startRow;sourceRowIndex<=endRow;++sourceRowIndex){
			row=sheet.createRow((short)newRowIndex);
			for(listIndex=0;listIndex<listLength;++listIndex){
				cell=row.createCell((short)listIndex);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(data[sourceRowIndex][listIndex]);
			}
			++newRowIndex;
		}
		
		//footer
		if(footer!=null){
			row=sheet.createRow((short)newRowIndex);
			for(listIndex=0;listIndex<footer.length;++listIndex){
				cell=row.createCell((short)listIndex);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(footer[listIndex]);
			}
		}
		
		BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(excelFile));
		workbook.write(bos);
		bos.close();
	}
}

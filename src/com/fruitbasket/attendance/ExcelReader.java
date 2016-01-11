package com.fruitbasket.attendance;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public final class ExcelReader {

	private ExcelReader(){}
	
	public static String[][] getData(File excelFile) throws FileNotFoundException, IOException{
		return getData(excelFile,0);
	}

	public static String[][] getData(File excelFile, int ignoreRows)
			throws FileNotFoundException, IOException{
		return getData(excelFile,ignoreRows,null);
	}

	/**
	 * read the specified excel file
	 * @param excelFile 
	 * @param ignoreRows :  specify the beginning rows to be ignore
	 * @param listNames : the list to be display in data set
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static String[][] getData(File excelFile, int ignoreRows,int... listIndexs)
			throws FileNotFoundException, IOException{
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(excelFile));

		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// 注意：一定要设成这个，否则可能会出现乱码
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;

						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd")
									.format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0").format(cell
								.getNumericCellValue());
							}
							break;

						case HSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;

						case HSSFCell.CELL_TYPE_BLANK:
							break;

						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;

						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y"
							: "N");
							break;

						default:
							value = "";
						}

					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = Utilities.rightTrim(value);
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		//对结果进行筛选
		if(listIndexs!=null){
			selectDateList(returnArray,listIndexs);
		}
		return returnArray;
	}

	/**
	 * select some lists from a 2D table
	 * @param data : a 2D table
	 * @param selectedLists : an int array to specify the lists to be selected
	 */
	private static void selectDateList(String[][] data,int...selectedLists){
		String [] dataRow;
		String [] newDataRow;
		//read every row of the data table
		for(int rowIndex=0;rowIndex<data.length;++rowIndex){
			dataRow=data[rowIndex];
			newDataRow=new String[selectedLists.length];
			for(int i=0;i<selectedLists.length;++i){
				newDataRow[i]=dataRow[selectedLists[i]];
			}
			data[rowIndex]=newDataRow;
		}
		
	}
}

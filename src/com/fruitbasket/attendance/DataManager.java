package com.fruitbasket.attendance;

import java.io.File;

public final class DataManager {
	
	private String mDirectory;//被处理完的excel文件的存放位置
	private Environment mEnvironment;
	
	public DataManager(String directory) throws Exception{
		File file=new File(directory);
		if(file.exists()==false){
			if(file.mkdirs()==false){
				throw new Exception("the parameter of DataManager is a illeagel directory!");
			}
		}
		setDirectory(directory);
		mEnvironment=Environment.getInstance();
	}
	
	public void setDirectory(String directory){
		mDirectory=directory;
	}
	
	/**
	 * 
	 * @param data
	 * @param IDList
	 * @param NameList
	 * @param includeHeader
	 */
	public void processData(String[][] data,int IDList,int NameList,boolean includeHeader){
		int startRow;
		String persionName;
		File excelFile;
		String[] footer={
				"实到工时：",
				"",
				"应到工时",
				"",
				"加班",
				""
		};
		if(includeHeader==true){
			startRow=1;
		}
		else{
			startRow=0;
		}
		int rowIndex=startRow+1;
		while(rowIndex<data.length){
			if(data[rowIndex-1][IDList]!=data[rowIndex][IDList]){
				persionName=data[rowIndex-1][NameList];
				try {
					new File(mDirectory+"/"+persionName).mkdir();
					excelFile=new File(mDirectory+"/"+persionName+"/attendance.xls");
					footer[1]=totalWorkingTime(data,mEnvironment.timeList,startRow,rowIndex-1);
					footer[3]=shouldWorkingTime(Integer.parseInt(data[startRow][mEnvironment.idList]),startRow,rowIndex-1);
					footer[5]=Utilities.timeMinus(footer[1], footer[3]);
					ExcelWriter.writeData(excelFile,data[0], data,footer, startRow, rowIndex-1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				startRow=rowIndex;
			}
			++rowIndex;
		}
		persionName=data[rowIndex-1][NameList];
		try {
			new File(mDirectory+"/"+persionName).mkdir();
			excelFile=new File(mDirectory+"/"+persionName+"/attendance.xls");
			footer[1]=totalWorkingTime(data,mEnvironment.timeList,startRow,rowIndex-1);
			footer[3]=shouldWorkingTime(Integer.parseInt(data[startRow][mEnvironment.idList]),startRow,rowIndex-1);
			footer[5]=Utilities.timeMinus(footer[1], footer[3]);
			ExcelWriter.writeData(excelFile, data[0],data, footer,startRow, rowIndex-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param data : a 2D table
	 * @param listIndex : the list index of the working time list
	 * @param startRow
	 * @param endRow
	 * @return
	 * @throws Exception
	 */
	private String totalWorkingTime(String[][] data,int listIndex,int startRow,int endRow) throws Exception{
		int totalHours=0;
		int totalMinutes=0;
		String[] time;
		for(int rowIndex=startRow;rowIndex<=endRow;++rowIndex){
			if(data[rowIndex][listIndex]!=null
					&&data[rowIndex][listIndex].length()!=0
					&&data[rowIndex][listIndex]!=""){
				time=data[rowIndex][listIndex].split(":");
				if(time.length!=2){
					throw new Exception("time format error! ");
				}
				time[0].trim();
				time[1].trim();
				totalHours+=Integer.parseInt(time[0]);
				totalMinutes+=Integer.parseInt(time[1]);
				if(totalMinutes>=60){
					totalHours++;
					totalMinutes-=60;
				}
			}
		}
		return totalHours+":"+totalMinutes;
	}

	private String shouldWorkingTime(int attendanceId,int startRow,int endRow){
		/*System.out.println("attendance:　"+attendanceId);
		System.out.println("shouldWorkingTime :　"+mEnvironment.workingTimeMap.get(attendanceId).toString());*/
		int days=endRow-startRow+1;
		double weeks=days/7.0;
		int shouldWorkingTime=(int) (weeks*mEnvironment.workingTimeMap.get(attendanceId));
		return String.valueOf(shouldWorkingTime)+":00";
	}
	
	
}

package com.fruitbasket.attendance;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class Environment {
	
	public static final String PROGRAM_NAME="attendance";//程序名称
	public static final String CONFIG_FILE="config.xml";
	private static final Environment mEnvironment=new Environment();
	
	public  String mAbsoluteRootPath;	//程序文件所在的绝对路径///
	public  String excelFile;
	public  String year;
	public  String month;
	
	//从原始excel表筛选的列
	public  int selectIdList;
	public  int selectNameList;
	public  int selectDateList;
	public  int selectInList;
	public  int selectOutList;
	public  int selectTimeList;
	
	//筛选出来的列
	public  int idList;
	public  int nameList;
	public  int dateList;
	public  int inList;
	public  int outList;
	public  int timeList;
	
	public  Map<Integer,Integer> workingTimeMap;//<attendance id,should working time>
	public int[] notProcessPeople;//should not process the attendance of this people
	
	private Environment(){
		XmlProcesser processer=XmlProcesser.getInstance();
		if((new File(CONFIG_FILE)).exists()==false){
			processer.createXmlConfig();
		}
		try {
			processer.setXmlConfig(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//initialize the should working time map
		workingTimeMap=new HashMap<Integer,Integer>();
		workingTimeMap.put(1, 40);
		workingTimeMap.put(2, 0);//伍老师
		workingTimeMap.put(3, 0);
		workingTimeMap.put(4, 32);
		workingTimeMap.put(5, 0);//可去掉
		workingTimeMap.put(6, 40);
		workingTimeMap.put(7, 0);//王老师
		workingTimeMap.put(8, 0);
		workingTimeMap.put(9, 0);
		workingTimeMap.put(10, 32);
		workingTimeMap.put(11, 0);//可去掉
		workingTimeMap.put(12, 0);//可去掉
		workingTimeMap.put(13, 0);
		workingTimeMap.put(14, 0);//可去掉
		workingTimeMap.put(15, 0);//可去掉
		workingTimeMap.put(16, 40);
		workingTimeMap.put(17, 0);//可去掉
		workingTimeMap.put(18, 0);//可去掉
		workingTimeMap.put(19, 32);
		workingTimeMap.put(20,32);
		workingTimeMap.put(21, 40);
		workingTimeMap.put(22, 0);
		workingTimeMap.put(23, 0);
		workingTimeMap.put(24, 0);
		workingTimeMap.put(25, 0);
		workingTimeMap.put(26, 0);
		workingTimeMap.put(27, 32);
		workingTimeMap.put(28, 32);//周昌盛
		workingTimeMap.put(29, 0);
		workingTimeMap.put(30, 32);
		workingTimeMap.put(31, 0);//可去掉
		workingTimeMap.put(32, 0);//可去掉
		workingTimeMap.put(33, 0);//可去掉
		workingTimeMap.put(34, 0);//可去掉
		
		notProcessPeople=new int[]{
				2,5,7,11,12,14,15,17,18,31,32,33,34
		};
	}
	
	public static Environment getInstance(){
		return mEnvironment;
	}
	
	public int[] getSelectedLists(){
		int[] selectedLists={
			selectIdList,
			selectNameList,
			selectDateList,
			selectInList,
			selectOutList,
			selectTimeList,
		};
		return selectedLists;
	}
	
	/**
	 * get program directory
	 * @return
	 */
	public String getProgramDirectory(){
		return PROGRAM_NAME;
	}
	
	/**
	 * get the time directory , which is under the program directory
	 * @return
	 */
	public String getTimeDirectory(){
		return getProgramDirectory()+"/"+year+"/"+month;
	}
	
	public void saveEnvironment(){
		XmlProcesser.getInstance().saveXmlConfig(mEnvironment);
	}
}

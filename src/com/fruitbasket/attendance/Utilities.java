package com.fruitbasket.attendance;

public final class Utilities {

	private Utilities(){};
	
	public static String timePuls(String x,String y)
			throws Exception{
		String[] timeX=x.split(":");
		String[] timeY=y.split(":");
		if(timeX.length!=2||timeY.length!=2){
			throw new Exception("time format error");
		}
		
		timeX[0].trim();
		timeX[1].trim();
		timeY[0].trim();
		timeY[1].trim();
		
		int xHours=Integer.parseInt(timeX[0]);
		int xMinutes=Integer.parseInt(timeX[1]);
		int yHours=Integer.parseInt(timeY[0]);
		int yMinutes=Integer.parseInt(timeY[1]);

		int totalHours=xHours+yHours;
		int totalMinutes=xMinutes+yMinutes;
		if(totalMinutes>=60){
			totalHours++;
			totalMinutes-=60;
		}
		
		return totalHours+":"+totalMinutes;
	}
	
	public static String timeMinus(String x,String y) 
			throws Exception{
		String[] timeX=x.split(":");
		String[] timeY=y.split(":");
		if(timeX.length!=2||timeY.length!=2){
			throw new Exception("time format error");
		}
		
		timeX[0].trim();
		timeX[1].trim();
		timeY[0].trim();
		timeY[1].trim();
		
		int xHours=Integer.parseInt(timeX[0]);
		int xMinutes=Integer.parseInt(timeX[1]);
		int yHours=Integer.parseInt(timeY[0]);
		int yMinutes=Integer.parseInt(timeY[1]);

		int minusHours=xHours-yHours;
		int minusMinutes=xMinutes-yMinutes;
		/*if(totalMinutes>=60){
			totalHours++;
			totalMinutes-=60;
		}*/
		/*if(minusHours<0
				||(minusHours==0&&minusMinutes<0)){
			return "00:00";
		}
		else if(){
			
		}*/
		if(minusMinutes<0){
			minusHours--;
			minusMinutes+=60;
		}
		
		return minusHours+":"+minusMinutes;
	}
	
	/**
	 * 去掉字符串右边的空格
	 * @param str   要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
}

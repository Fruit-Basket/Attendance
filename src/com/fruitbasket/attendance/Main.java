package com.fruitbasket.attendance;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Main {
	static Environment environment=Environment.getInstance();
	public static void main(String[] args) throws Exception {
		run();
	}
	
	private static void run() throws Exception{
		System.out.println("Attendance Processer . Developed by XueJin.");
		System.out.println();
		
		File excelFile =new File(environment.excelFile);
		String command;
		String[] parameters;
		Scanner input=new Scanner(System.in);
		while(true){
			System.out.print("Attendance Processer>");
			command=input.nextLine();
			command.trim();
			parameters=command.split(" ");
			
			switch(parameters[0]){
			case "":
				break;
				
			case "excel":
				if(parameters.length!=2){
					show();
				}
				else{
					excelFile=new File(parameters[1]);
					if(excelFile.exists()==false){
						System.out.println("the excel file is not exists !");
					}
					else if(excelFile.isFile()==false||excelFile.getName().endsWith(".xls")==false){
						System.out.println("please choose a excel file");
					}
					else{
						environment.excelFile=parameters[1];
						environment.saveEnvironment();
					}
				}
				break;
				
			case "do":
				if(excelFile!=null&&excelFile.exists()==true){
					String [][] data=ExcelReader.getData(excelFile, 0, environment.getSelectedLists());
					DataManager dataManager=new DataManager(environment.getTimeDirectory());
					dataManager.processData(data, 0, 1, true);
				}
				else{
					System.out.println("please specify a excel file first.");
				}
				break;
			case "exit":
				System.exit(0);
				break;
				
			default:
					show();
			}
		}
	}
	
	public static void show(){
		System.out.println("command error !");
		File helpFile=new File("help.txt");
		if(helpFile.exists()==true){
			Scanner scanner = null;
			try {
				scanner = new Scanner(helpFile);
			} catch (FileNotFoundException e) {
			}
			while(scanner.hasNext()==true){
				System.out.println(scanner.nextLine());
			}
			scanner.close();
		}
	}
}

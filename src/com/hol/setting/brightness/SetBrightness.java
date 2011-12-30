package com.hol.setting.brightness;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

public class SetBrightness {
	public static final int DEFAULT_BRIGHTNESS = 350;
	public static final int DEFAULT_MAX = 976;
	public static final String CONFIGURATION_DIR = "/sys/class/backlight";
	public static final String END_OF_SETTING_DIR = "backlight";
	public static final String ACTURE_BRIGHTNESS = "actual_brightness";
	public static final String BRIGHTNESS = "brightness";
	public static final String MAX_BRIGHTNESS = "max_brightness";

	public static void main (String[] args) throws SetBrightnessException, IOException{
		int max = DEFAULT_MAX;
		int acture = 0;
		int brightness = 0;
		if (args.length > 0){
			brightness = Integer.valueOf(args[0]);
		}
		File configurationDir = new File(CONFIGURATION_DIR);
		if (!configurationDir.isDirectory()){
			throw new SetBrightnessException("no found configuration directory. please check " + CONFIGURATION_DIR);
		}
		File[] configurations = configurationDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if (name.toLowerCase().endsWith(END_OF_SETTING_DIR)){
					return true;
				}
				return false;
			}
		});
		if (configurations.length == 0){
			throw new SetBrightnessException("brightness setting directory no found.");
		}
		File brightnessDir = configurations[0];
		File actureBrightnessFile = new File(brightnessDir, ACTURE_BRIGHTNESS);
		File brightnessFile = new File(brightnessDir, BRIGHTNESS);
		File maxBrightnessFile = new File(brightnessDir, MAX_BRIGHTNESS);
		max = getFileContentAsNumber(maxBrightnessFile);
		out("max brightness is " + max);
		acture = getFileContentAsNumber(actureBrightnessFile);
		out("acture brightness is " + acture);
		if (brightness == 0){
			if (DEFAULT_BRIGHTNESS * 2 > max){
				brightness = max / 2;
			}else{
				brightness = DEFAULT_BRIGHTNESS;
			}
		}else{
			if (brightness > max){
				brightness = max - 1;
			}
		}
		out("want set brightness to " + brightness);
		writerNumberToFile(brightnessFile, brightness);
	}
	
	/**
	 * 获取文件的内容，并反回一个数值
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static int getFileContentAsNumber(File file) throws IOException{
		int num = 0;
		FileReader reader = new FileReader(file);
		BufferedReader buffReader = new BufferedReader(reader);
		String line = buffReader.readLine();
		if (line != null && !line.equals("")){
			num = Integer.valueOf(line);
		}
		buffReader.close();
		reader.close();
		return num;
	}
	
	/**
	 * 将一个数值写入到文件
	 * @param file
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public static void writerNumberToFile(File file, int num) throws IOException{
		FileWriter writer = new FileWriter(file);
		BufferedWriter buffWriter = new BufferedWriter(writer);
		buffWriter.write(String.valueOf(num));
		buffWriter.flush();
		buffWriter.close();
		writer.close();
	}
	
	public static void out(String msg){
		System.out.println(msg);
	}
}

package com.hol.setting.brightness.test;

import java.io.File;
import java.io.IOException;

import com.hol.setting.brightness.SetBrightness;

import junit.framework.TestCase;

public class FileOperationTest extends TestCase {
	private static final String TEST_FILE_PATH = "/home/holmes/tmp/test";
	
	public void testGetValue(){
		File file = new File(TEST_FILE_PATH);
		int num = 0;
		try {
			num = SetBrightness.getFileContentAsNumber(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("want 90 ,num is " + num, 90, num);
	}
	
	public void testSetValue(){
		File file = new File(TEST_FILE_PATH);
		int num = 100;
		boolean success = false;
		try {
			SetBrightness.writerNumberToFile(file, num);
			success = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(success);
	}
}

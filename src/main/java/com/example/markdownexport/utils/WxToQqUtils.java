package com.example.markdownexport.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: SAn Blog
 * @create: 2020年5月18日 22:19:09
 * @description: 微信小程序转qq小程序
 **/
public class WxToQqUtils {

	//微信小程序文件夾
	static String sourceDirectory = "E:\\WeHalo-master";
	//輸出qq小程序文件夾
	static String targetDirectory = "E:\\WeHalo-master-qq";
	public static final int EOF = -1;
	
	private static Map<String, String> suffixMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 4164251304476781073L;
		{
			put(".wxss", ".qss");
			put(".wxml", ".qml");
		}
	};
	
	public static void main(String[] args) {
		try {
			WxToQqUtils.convert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void convert() throws IOException {
		File file = new File(sourceDirectory);
		if(!file.isDirectory()) {
			return ;
		}
		convertFile(file);
	}
	
	private static void convertFile(File file) throws IOException {
		File[] fs = file.listFiles();
		for(File f:fs){
			if(f.isDirectory()) {
				convertFile(f);
			}else {
				createTargetFile(f);
			}
		}
	}
	
	private static void createTargetFile(File sourceFile) throws IOException {
		String parent = sourceFile.getParent();
		String targetParent = parent.replace(sourceDirectory, targetDirectory);
		String fileContent = null;
		String targetFileName = getTargetFileName(sourceFile.getName());
		
		File targetFile = new File(targetParent+"/"+targetFileName);
		if(!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		if(targetFileName.endsWith(".js")) {
			fileContent = getFileContent(sourceFile,"wx\\.","qq.");
			writeContent(targetFile, fileContent);
		}else if(targetFileName.endsWith(".wxss")) {
			fileContent = getFileContent(sourceFile,".wxss",".qss");
			writeContent(targetFile, fileContent);
		}else if(targetFileName.endsWith(".wxml")) {
			fileContent = getFileContent(sourceFile,".wxml",".qml");
			writeContent(targetFile, fileContent);
		}else {
			copy(sourceFile, targetFile);
		}
	}
	
	private static void copy(File sourceFile,File targetFile) throws IOException {
		FileInputStream input = new FileInputStream(sourceFile);  
        BufferedInputStream inBuff=new BufferedInputStream(input);  
  
        FileOutputStream output = new FileOutputStream(targetFile);  
        BufferedOutputStream outBuff=new BufferedOutputStream(output);  


        byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while (EOF != (n = inBuff.read(buffer))) {
			outBuff.write(buffer, 0, n);
		}
        outBuff.flush();
        inBuff.close();  
        outBuff.close();  
        output.close();  
        input.close(); 
	}
	
	private static void writeContent(File file,String content) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(content);
		fileWriter.flush();
		fileWriter.close();
	}
	 
	private static String getTargetFileName(String fileName) {
		if(fileName.indexOf(".")<=0) {
			return fileName;
		}
		String prefix = fileName.substring(0,fileName.indexOf("."));
		String suffix = fileName.substring(fileName.indexOf("."));

		String targetSuffix = suffixMap.get(suffix);
		if(targetSuffix == null) {
			targetSuffix = suffix;
		}
		String targetFileName = prefix+targetSuffix;
		return targetFileName;
	}
	
	
	
	private static String getFileContent(final File file, String s, String t) throws IOException {
		try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {
			StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				buffer.append(line).append("\n");
			}
			String temp = buffer.toString();
			if (s != null && t != null) {
				temp = temp.replaceAll(s, t);
			}
			return temp;
		} catch (IOException e) {
			throw e;
		}
	}
}

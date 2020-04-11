package com.xiuye.util.charset;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import com.xiuye.util.log.XLog;

/**
 * covert java source code to unicode code
 * 
 * @author xiuye
 *
 */
public class EncryptJava2Unicode {

	public static void main(String args[]) throws IOException {
		java2Unicode("..");
	}

	/**
	 * covert java source code to unicode code
	 * 
	 * @param outputFilePath
	 * @throws IOException
	 */
	public static void java2Unicode(String outputFilePath) throws IOException {
		Scanner in = new Scanner(System.in);
		XLog.log("请输入已存在的文件名：");
		String fileName = in.nextLine();
		File f = new File(fileName);
		if (!f.exists()) {
			XLog.log("文件：" + fileName + "不存在！");
			System.exit(0);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputFilePath + File.pathSeparator + fileName)));
		int i = -1;
		String str = "";
		String unicodeOriginal = "";
		String unicodeTemp = "";
		while ((i = br.read()) != -1) {
			unicodeOriginal = Integer.toHexString(i);
			int length = unicodeOriginal.length();
			if (length == 1) {
				unicodeTemp = "000" + unicodeOriginal;
			} else if (length == 2) {
				unicodeTemp = "00" + unicodeOriginal;
			} else if (length == 3) {
				unicodeTemp = "0" + unicodeOriginal;
			} else {
				unicodeTemp = unicodeOriginal;
			}
			str = "\\u" + unicodeTemp;
			bw.write(str, 0, str.length());
			bw.flush();
//			System.out.print("\\u" + unicodeTemp);
			XLog.print("\\u" + unicodeTemp);
		}
		XLog.println();
		br.close();
		bw.close();
		in.close();
	}

}

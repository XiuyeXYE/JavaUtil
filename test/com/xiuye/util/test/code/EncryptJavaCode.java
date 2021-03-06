package com.xiuye.util.test.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.xiuye.util.code.XCode;
import com.xiuye.util.log.XLog;

/**
 * covert java source code to unicode code
 * 
 * @author xiuye
 *
 */
public class EncryptJavaCode {

	public static void main(String args[]) throws IOException {
//		Scanner in = new Scanner(System.in);
//		XLog.log("请输入已存在的文件名：");
//		String fileName = in.nextLine();
//		XCode.java2Unicode(fileName,"..");
//		
//		in.close();

		XLog.lg(Files.isDirectory(Paths.get("out")));

		XCode.unicode2Java("EncryptJava2Unicode.java", "out");
	}

}

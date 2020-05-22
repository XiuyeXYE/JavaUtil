package com.xiuye.util.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.xiuye.util.log.XLog;
import com.xiuye.util.time.XTime;
import com.xiuye.util.time.XTime.Callback;

/**
 * Run code time
 * 
 * @author xiuye
 *
 */
public class XCode {

	/**
	 * run code and return time of code running
	 * 
	 * @param runnable
	 * @return run nanoseconds
	 */
	public static synchronized long run(Runnable runnable) {
		if (Objects.nonNull(runnable)) {
			synchronized (XTime.class) {
				int old = XTime.setLevel(XTime.getLevel()+1);
				XTime.start();
				runnable.run();
				XTime.setLevel(old);
				return XTime.cost();
			}

		}
		return -1;
	}

	/**
	 * run code and return nanoseconds
	 * 
	 * @param runnable
	 * @param cs
	 */
	public static synchronized long runNS(Runnable runnable, Callback... cs) {
		if (Objects.nonNull(runnable)) {
			synchronized (XTime.class) {
				int old = XTime.setLevel(XTime.getLevel()+1);
				XTime.start();
				runnable.run();
				XTime.setLevel(old);
				return XTime.outByNS(cs);
			}
		}
		return -1;
	}

	/**
	 * run code and return milliseconds
	 * 
	 * @param runnable
	 * @param cs
	 */
	public static synchronized long runMS(Runnable runnable, Callback... cs) {
		if (Objects.nonNull(runnable)) {
			synchronized (XTime.class) {
				int old = XTime.setLevel(XTime.getLevel()+1);
				XTime.start();
				runnable.run();
				XTime.setLevel(old);
				return XTime.outByMS(cs);
			}
		}
		return -1;
	}

	/**
	 * run code and return seconds
	 * 
	 * @param runnable
	 * @param cs
	 */
	public static synchronized long runS(Runnable runnable, Callback... cs) {
		if (Objects.nonNull(runnable)) {
			synchronized (XTime.class) {
				int old = XTime.setLevel(XTime.getLevel()+1);
				XTime.start();
				runnable.run();
				XTime.setLevel(old);
				return XTime.outByS(cs);
			}
		}
		return -1;
	}

	// error XTime itself doesn't support that async-ly run code!
	// but we can write threadsafe code!
	/**
	 * async running code
	 * 
	 * @param runnable
	 * @param cs
	 */
	public static void runAsyncMS(Runnable runnable, Callback... cs) {
		new Thread(() -> {
			runMS(runnable, cs);
		}).start();
	}

	/**
	 * async running code
	 * 
	 * @param runnable
	 * @param cs
	 */
	public static void runAsyncNS(Runnable runnable, Callback... cs) {
		new Thread(() -> {
			runNS(runnable, cs);
		}).start();
	}

	/**
	 * async running code
	 * 
	 * @param runnable
	 * @param cs
	 */
	public static void runAsyncS(Runnable runnable, Callback... cs) {
		new Thread(() -> {
			runS(runnable, cs);
		}).start();
	}

	/**
	 * covert java source code to unicode code
	 * 
	 * @param outputFilePath
	 * @throws IOException
	 */
	public static void java2Unicode(String fileName, String outputFilePath) throws IOException {
		File f = new File(fileName);
		if (!f.exists()) {
			XLog.log("文件：" + fileName + "不存在！");
			return;
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

	}

	/**
	 * covert unicode code to java source code
	 * 
	 * @param outputFilePath
	 * @throws IOException
	 */
	public static void unicode2Java(String fileName, String outputFilePath) throws IOException {
		File f = new File(fileName);
		if (!f.exists()) {
			XLog.log("文件：" + fileName + "不存在！");
			return;
		}
		Path outPath = Paths.get(outputFilePath);
		if (!Files.exists(outPath)) {
			Files.createDirectory(outPath);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputFilePath + File.separator + fileName)));
		int ch = -1;
		while ((ch = br.read()) != -1) {
			if (ch == '\\') {
				ch = br.read();
				if (ch == 'u') {
					String s = "";
					for (int i = 0; i < 4; i++) {
						ch = br.read();
						s += (char) ch;
					}
					bw.write(Integer.parseInt(s, 16));
					XLog.print(s);
				} else {
					bw.write(ch);
					XLog.print(ch);
				}
			} else {
				bw.write(ch);
				XLog.print(ch);
			}
		}
		XLog.println();
		br.close();
		bw.close();

	}

}

package com.xiuye.util.time;

import java.util.Objects;

import com.xiuye.util.cls.XMeta;
import com.xiuye.util.cls.XMeta.Caller;
import com.xiuye.util.log.XLog;

public class XTime {

	private static long STIME = -1;

	private static Caller SCALLER = null;

	private static final int LEVEL = 3;
	

	private static void checkTime() {
		if (STIME == -1 || Objects.isNull(SCALLER)) {
			throw new RuntimeException("Not call start() function firstly!");
		}
	}

	private static void executeAllCallback(Callback... cs) {
		for (Callback c : cs) {
			c.run();
		}
	}

	private static void codeSectionMSG() {
		Caller eCaller = XMeta.caller(LEVEL + 1);
		String codeSectionMSG = "\r\n=====This Code Section=====\nFrom\n";
		codeSectionMSG += "    Class Name : " + SCALLER.getClassName() + "\n    Method Name : "
				+ SCALLER.getMethodName() + "\n    File Name : " + SCALLER.getFileName() + "\n    Enter Line : "
				+ (SCALLER.getLineNumber() + 1) + "\nTo\n" + "    Class Name : " + eCaller.getClassName()
				+ "\n    Method Name : " + eCaller.getMethodName() + "\n    File Name : " + eCaller.getFileName()
				+ "\n    Exit Line : " + (eCaller.getLineNumber() - 1);
		XLog.log(codeSectionMSG);
	}

	public interface Callback {
		void run();
	}

	public static long start() {
		SCALLER = XMeta.caller(LEVEL);
		return STIME = System.nanoTime();
	}

	public static long cost() {
		checkTime();
		return System.nanoTime() - STIME;
	}

	public static void reset() {
		STIME = -1;
	}

	public static long outByNS(Callback... cs) {
		long e = System.nanoTime();// place it here precision
		checkTime();
		codeSectionMSG();
		long cost = e - STIME;
		XLog.log("This running time costs : " + cost + " ns");
		executeAllCallback(cs);
		SCALLER = XMeta.caller(LEVEL);
		STIME = System.nanoTime();// place it here precision
		return cost;
	}

	public static long outByMS(Callback... cs) {
		long e = System.nanoTime();
		checkTime();
		codeSectionMSG();
		long cost = e - STIME;
		XLog.log("This running time costs : " + String.format("%.6f", cost / 1000000.0) + " ms");
		executeAllCallback(cs);
		SCALLER = XMeta.caller(LEVEL);
		STIME = System.nanoTime();
		return cost;
	}

	public static long outByS(Callback... cs) {
		long e = System.nanoTime();
		checkTime();
		codeSectionMSG();
		long cost = e - STIME;
		XLog.log("This running time costs : " + String.format("%.9f", cost / 1000000000.0) + " s");
		executeAllCallback(cs);
		SCALLER = XMeta.caller(LEVEL);
		STIME = System.nanoTime();
		return cost;
	}

}

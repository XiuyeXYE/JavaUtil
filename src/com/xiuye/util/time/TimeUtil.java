package com.xiuye.util.time;

import java.util.Objects;

import com.xiuye.util.cls.MetaUtil;
import com.xiuye.util.cls.MetaUtil.Caller;
import com.xiuye.util.log.LogUtil;

public class TimeUtil {
	private static long STIME = -1;

	private static Caller SCALLER = null;

	private static void checkTime() {
		if (STIME == -1 || Objects.isNull(SCALLER)) {
			throw new RuntimeException("Not call start() function firstly!");
		}
	}

	private static void executeAllCallback(Callback...cs) {
		for (Callback c : cs) {
			c.run();
		}
	}
	
	private static void codeSectionMSG() {
		var eCaller = MetaUtil.caller(4);
		var codeSectionMSG = "=====This Code Section=====\nFrom\n";
		codeSectionMSG += "    Class Name : " + SCALLER.getClassName() + "\n    Method Name : "
				+ SCALLER.getMethodName() + "\n    File Name : " + SCALLER.getFileName() + "\n    Enter Line : "
				+ SCALLER.getLineNumber() + "\nTo\n" + "    Class Name : " + eCaller.getClassName()
				+ "\n    Method Name : " + eCaller.getMethodName() + "\n    File Name : " + eCaller.getFileName()
				+ "\n    Exit Line : " + eCaller.getLineNumber();
		LogUtil.log(codeSectionMSG);
	}

	public interface Callback {
		void run();
	}

	public static long start() {
		SCALLER = MetaUtil.caller(3);
		return STIME = System.nanoTime();
	}

	public static long cost() {
		checkTime();
		return System.nanoTime() - STIME;
	}

	public static void reset() {
		STIME = -1;
	}

	public static long outCostOnConsoleNs(Callback... cs) {
		var e = System.nanoTime();//place it here precision
		checkTime();
		codeSectionMSG();
		var cost = e - STIME;
		LogUtil.log("This running time costs : " + cost + " ns");
		executeAllCallback(cs);
		SCALLER = MetaUtil.caller(3);
		STIME = System.nanoTime();//place it here precision
		return cost;
	}

	public static long outCostOnConsoleMs(Callback... cs) {
		var e = System.nanoTime();
		checkTime();
		codeSectionMSG();
		var cost = e - STIME;
		LogUtil.log("This running time costs : " + String.format("%.6f", cost / 1000000.0) + " ms");
		executeAllCallback(cs);
		SCALLER = MetaUtil.caller(3);
		STIME = System.nanoTime();
		return cost;
	}

	public static long outCostOnConsoleS(Callback... cs) {
		var e = System.nanoTime();
		checkTime();
		codeSectionMSG();
		var cost = e - STIME;
		LogUtil.log("This running time costs : " + String.format("%.6f", cost / 1000000000.0) + " s");
		executeAllCallback(cs);
		SCALLER = MetaUtil.caller(3);
		STIME = System.nanoTime();
		return cost;
	}

}

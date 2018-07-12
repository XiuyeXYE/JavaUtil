package com.xiuye.util.time;

import com.xiuye.util.log.LogUtil;

public class TimeUtil {
	private static long STIME = -1;

	private static void checkTime() {
		if (STIME == -1) {
			throw new RuntimeException("Not call start() function!");
		}
	}

	public static long start() {
		return STIME = System.nanoTime();
	}

	public static long cost() {
		checkTime();
		return System.nanoTime() - STIME;
	}

	public static void reset() {
		STIME = -1;
	}

	public static long outCostOnConsoleNs() {
		checkTime();
		var cost = System.nanoTime() - STIME;
		LogUtil.log("This running time costs : " + cost + " ns");
		STIME = System.nanoTime();
		return cost;
	}
	public static long outCostOnConsoleMs() {
		checkTime();
		var cost = System.nanoTime() - STIME;
		LogUtil.log("This running time costs : " + cost/1000000.0 + " ms");
		STIME = System.nanoTime();
		return cost;
	}
}

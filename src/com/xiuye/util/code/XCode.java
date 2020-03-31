package com.xiuye.util.code;

import java.util.Objects;

import com.xiuye.util.time.XTime;
import com.xiuye.util.time.XTime.Callback;

public class XCode {

	/**
	 * run code and return time of code running
	 * 
	 * @param runnable
	 * @return run nanoseconds
	 */
	public static long run(Runnable runnable) {
		if (Objects.nonNull(runnable)) {
			XTime.start();
			runnable.run();
			return XTime.cost();
		}
		return -1;
	}

	/**
	 * run code and return nanoseconds
	 * 
	 * @param runnable
	 * @param cs
	 */
	public static long runNS(Runnable runnable, Callback... cs) {
		if (Objects.nonNull(runnable)) {
			XTime.start();
			runnable.run();
			return XTime.outByNS(cs);
		}
		return -1;
	}

	/**
	 * run code and return milliseconds
	 * 
	 * @param runnable
	 * @param cs
	 */
	public static long runMS(Runnable runnable, Callback... cs) {
		if (Objects.nonNull(runnable)) {
			XTime.start();
			runnable.run();
			return XTime.outByMS(cs);
		}
		return -1;
	}

	/**
	 * run code and return seconds
	 * 
	 * @param runnable
	 * @param cs
	 */
	public static long runS(Runnable runnable, Callback... cs) {
		if (Objects.nonNull(runnable)) {
			XTime.start();
			runnable.run();
			return XTime.outByS(cs);
		}
		return -1;
	}

	public static void runAsync(Runnable runnable) {
		new Thread(runnable).start();
	}

}

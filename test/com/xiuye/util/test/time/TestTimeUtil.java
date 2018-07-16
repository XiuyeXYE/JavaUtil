package com.xiuye.util.test.time;

import org.junit.Test;

import com.xiuye.util.log.LogUtil;
import com.xiuye.util.time.TimeUtil;

public class TestTimeUtil {

	@Test
	public void testCostTime() {
		TimeUtil.start();
		long counter = 0;
		for (int i = 0; i < 1000000000; i++) {
			counter++;

		}
		LogUtil.log(counter);
		TimeUtil.outCostOnConsoleNs();
		TimeUtil.start();
		counter = 0;
		for (int i = 0; i < 1000000000; i++) {
			counter++;

		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LogUtil.log(counter);
		TimeUtil.outCostOnConsoleMs();
		TimeUtil.start();
		counter = 0;
		for (int i = 0; i < 1000000000; i++) {
			counter++;

		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LogUtil.log(counter);
		TimeUtil.outCostOnConsoleS(() -> {
			LogUtil.log("Call in the end or start?");
		});

	}

	@Test
	public void testStackTrace() {
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		int counter = 0;
		for (StackTraceElement ste : stes) {

			LogUtil.log(counter + "." + "StackTraceElement : " + ste);
			LogUtil.log(counter + "." + "FileName : " + ste.getFileName());
			LogUtil.log(counter + "." + "LineNumber : " + ste.getLineNumber());
			LogUtil.log(counter + "." + "ClassName : " + ste.getClassName());
			LogUtil.log(counter + "." + "MethodName : " + ste.getMethodName());
			counter++;
		}
	}

	@Test
	public void testTimeUtilCallerIsOK() {
		TimeUtil.start();
		TimeUtil.outCostOnConsoleNs(() -> {
			LogUtil.log("OK", "YES");
		}, () -> {
			LogUtil.log("End", "Last", 1, 23);
		});
		TimeUtil.outCostOnConsoleMs(() -> {
			LogUtil.log("OK", "YES");
		}, () -> {
			LogUtil.log("End", "Last", 1, 23);
		});
		TimeUtil.outCostOnConsoleS(() -> {
			LogUtil.log("OK", "YES");
		}, () -> {
			LogUtil.log("End", "Last", 1, 23);
		});
	}

}

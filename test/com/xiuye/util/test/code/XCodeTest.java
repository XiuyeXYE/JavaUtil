package com.xiuye.util.test.code;

import org.junit.Test;

import com.xiuye.util.code.XCode;
import com.xiuye.util.log.XLog;

public class XCodeTest {

	@Test
	public void test() throws InterruptedException {
		XCode.runAsyncNS(()->{
			XLog.log("Something");
		}, ()->{
			XLog.log("after running code!");
		});
		XCode.runAsyncMS(()->{
			XLog.log("Something");
		}, ()->{
			XLog.log("after running code!");
		});
		XCode.runAsyncS(()->{
			XLog.log("Something");
		}, ()->{
			XLog.log("after running code!");
		});
		Thread.sleep(3000);
	}
	
}

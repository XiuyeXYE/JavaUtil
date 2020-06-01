package com.xiuye.test.cls;

import org.junit.Test;

import com.xiuye.util.cls.XMeta;
import com.xiuye.util.log.XLog;

public class XMetaTest {

	@Test
	public void testCaller() {
		XLog.ln(XMeta.caller());
		XLog.ln(XMeta.caller().getTrace().length);
		for(StackTraceElement trace : XMeta.caller().getTrace()) {
			XLog.ln(trace);
		}
	}
	
}

package com.xiuye.util.test.time;

import org.junit.Test;

import com.xiuye.util.log.LogUtil;
import com.xiuye.util.time.TimeUtil;

public class TestTimeUtil {

	@Test
	public void testCostTime() {
		TimeUtil.start();
		long counter = 0;
		for(var i=0;i<1000000000;i++) {
			counter ++;
			
		}
		LogUtil.log(counter);
		TimeUtil.outCostOnConsoleNs();
	}
	
}

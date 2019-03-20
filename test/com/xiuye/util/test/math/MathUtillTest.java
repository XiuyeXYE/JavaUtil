package com.xiuye.util.test.math;

import com.xiuye.util.log.LogUtil;
import com.xiuye.util.math.MathUtil;

public class MathUtillTest {
	public static void main(String[] args) {
		LogUtil.log(MathUtil.gcd(5,5));
		LogUtil.log(MathUtil.gcd(5,4));
		LogUtil.log(MathUtil.gcd(8,10));
		
	}
}

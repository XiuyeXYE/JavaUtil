package com.xiuye.util.test.math;

import com.xiuye.util.log.LogUtil;
import com.xiuye.util.math.MathUtil;

public class MathUtillTest {
	public static void main(String[] args) {
		LogUtil.log(MathUtil.gcd(5,5));
		LogUtil.log(MathUtil.gcd(5,4));
		LogUtil.log(MathUtil.gcd(8,10));
		int []a1 = {6,12,55,99,1,22,76};
		LogUtil.log(MathUtil.max(a1));
		double []a2 = {6,12,55,99,1,22,76,987};
		LogUtil.log(MathUtil.max(a2));
		LogUtil.log(MathUtil.average(a1));
		LogUtil.log(MathUtil.average(a2));
		MathUtil.reverse(a1);
		LogUtil.logarray(a1);
		MathUtil.reverse(a2);
		LogUtil.logarray(a2);
		LogUtil.log(MathUtil.abs(-1));
		LogUtil.log(MathUtil.abs(-1.1));
		LogUtil.log(MathUtil.isPrime(9));
		LogUtil.log(MathUtil.isPrime(5));
		LogUtil.log(MathUtil.isPrime(95));
		
		
		
	}
}

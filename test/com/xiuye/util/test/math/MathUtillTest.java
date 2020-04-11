package com.xiuye.util.test.math;

import com.xiuye.util.log.XLog;
import com.xiuye.util.math.XMath;

public class MathUtillTest {
	public static void main(String[] args) {
		XLog.log(XMath.gcd(5, 5));
		XLog.log(XMath.gcd(5, 4));
		XLog.log(XMath.gcd(8, 10));
		int[] a1 = { 6, 12, 55, 99, 1, 22, 76 };
		XLog.log(XMath.max(a1));
		double[] a2 = { 6, 12, 55, 99, 1, 22, 76, 987 };
		XLog.log(XMath.max(a2));
		XLog.log(XMath.average(a1));
		XLog.log(XMath.average(a2));
		XMath.reverse(a1);
		XLog.logArray(a1);
		XMath.reverse(a2);
		XLog.logArray(a2);
		XLog.log(XMath.abs(-1));
		XLog.log(XMath.abs(-1.1));
		XLog.log(XMath.isPrime(9));
		XLog.log(XMath.isPrime(5));
		XLog.log(XMath.isPrime(95));
		XLog.log(XMath.sqrt(2));
		XLog.log(XMath.sqrt(3));
		XLog.log(XMath.sqrt(4));
		XLog.log(XMath.sqrt(5));
		XLog.log(XMath.sqrt(9));
		XLog.log(XMath.sqrt(100));
		XLog.log(XMath.sqrt(101));
		XLog.log(XMath.rightTriangleHypotenuse(3, 4));

	}
}

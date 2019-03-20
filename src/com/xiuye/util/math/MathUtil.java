package com.xiuye.util.math;

public interface MathUtil {

	public static int gcd(int a, int b) {
		if (b == 0)
			return a;
		int r = a % b;
		return gcd(b, r);
	}


	

}

package com.xiuye.util.math;

public interface MathUtil {

	public static int gcd(int a, int b) {
		if (b == 0)
			return a;
		int r = a % b;
		return gcd(b, r);
	}

	public static int max(int a, int b) {
		return a >= b ? a : b;
	}

	public static double max(double a, double b) {
		return a >= b ? a : b;
	}

	public static int max(int[] a) {
		if (a.length <= 0)
			throw new RuntimeException("blank array");
		int max = a[0];
		for (int i = 1; i < a.length; i++) {
//			if(a[i]>max)max=a[i];
			max = max(max, a[i]);
		}
		return max;

	}

	public static double max(double[] a) {
		if (a.length <= 0)
			throw new RuntimeException("blank array");
		double max = a[0];
		for (int i = 1; i < a.length; i++) {
//			if(a[i]>max)max=a[i];
			max = max(max, a[i]);
		}
		return max;

	}

	public static double average(double[] a) {
		double sum = 0.0;

		for (double i : a) {
			sum += i;
		}
		return sum / a.length;
	}

	public static double average(int[] a) {
		double sum = 0.0;

		for (double i : a) {
			sum += i;
		}
		return sum / a.length;
	}

	public static void reverse(int []a) {
		for(int i=0;i<a.length/2;i++) {
			int temp = a[i];
			a[i] = a[a.length-i-1];
			a[a.length-i-1] = temp;
		}
	}
	public static void reverse(double []a) {
		for(int i=0;i<a.length/2;i++) {
			double temp = a[i];
			a[i] = a[a.length-i-1];
			a[a.length-i-1] = temp;
		}
	}
	
	public static int abs(int x) {
		if(x<0)return -x;
		return x;
	}
	public static double abs(double x) {
		if(x<0.0)return -x;
		return x;
	}
	//mark!
	public static boolean isPrime(int x) {
		if(x<2)return false;
		//i <= sqrt(x) <=> i*i <= x 
		for(int i=2;i*i<=x;i++) {
			if(x%i==0)return false;
		}
		return true;
	}
	
	
	
	
}

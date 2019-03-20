package com.xiuye.util.log;

public class LogUtil {

	public static <T> void println(T... t) {
		log(t);
	}

	public static <T> void logarray(T[] arr) {

		for (T t : arr) {
			print(t);
		}
		println();
	}
	public static void logarray(double[] arr) {
		
		for (double t : arr) {
			print(t);
		}
		println();
	}
	public static void logarray(int[] arr) {
		
		for (int t : arr) {
			print(t);
		}
		println();
	}

	@SafeVarargs
	public static <T> void log(T... t) {

		for (int i = 0; i < t.length - 1; i++) {
			System.out.print(t[i] + " ");
		}
		if (t.length > 0) {
			System.out.println(t[t.length - 1]);
		} else {
			System.out.println();
		}
	}

	public static <T> void print(T... ts) {
		for (T t : ts) {
			System.out.print(t + " ");
		}
	}

}

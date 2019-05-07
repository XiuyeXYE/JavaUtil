package com.xiuye.util.log;

public class LogUtil {

	@SafeVarargs
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

		if (t.length == 0)
			System.out.println();
		else {
			for (int i = 0; i < t.length - 1; i++) {
				System.out.print(t[i] + " ");
			}
			System.out.println(t[t.length - 1]);
		}
		
	}
	
	@SafeVarargs
	public static <T> void err(T... t) {
		
		if (t.length == 0)
			System.err.println();
		else {
			for (int i = 0; i < t.length - 1; i++) {
				System.err.print(t[i] + " ");
			}
			System.err.println(t[t.length - 1]);
		}
		
	}

	@SafeVarargs
	public static <T> void print(T... ts) {
		if (ts.length > 0) {
			for (int i = 0; i < ts.length - 1; i++) {
				System.out.print(ts[i] + " ");
			}
			System.out.print(ts[ts.length - 1]);
		}
		
	}
	
	

}

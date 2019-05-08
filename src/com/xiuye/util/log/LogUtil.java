package com.xiuye.util.log;

import java.util.Objects;

public class LogUtil {

	@SafeVarargs
	public static <T> void println(T... t) {
		log(t);
	}

	public static <T> void logArray(T[] arr) {

		for (T t : arr) {
			print(t);
		}
		println();
	}

	public static <T> void logArray(T[] arr, String separator) {

		if (Objects.nonNull(arr)) {
			if (arr.length == 0) {
				print();
			} else {
				for (int i = 0; i < arr.length - 1; i++) {
					print(arr[i] + separator);
				}
				print(arr[arr.length - 1]);
			}

		}
		println();
	}

	public static void logArray(double[] arr, String separator) {

		if (Objects.nonNull(arr)) {
			if (arr.length == 0) {
				print();
			} else {
				for (int i = 0; i < arr.length - 1; i++) {
					print(arr[i] + separator);
				}
				print(arr[arr.length - 1]);
			}

		}
		println();
	}

	public static void logArray(double[] arr) {

		for (double t : arr) {
			print(t);
		}
		println();
	}

	public static void logArray(int[] arr, String separator) {

		if (Objects.nonNull(arr)) {
			if (arr.length == 0) {
				print();
			} else {
				for (int i = 0; i < arr.length - 1; i++) {
					print(arr[i] + separator);
				}
				print(arr[arr.length - 1]);
			}

		}
		println();
	}

	public static void logArray(int[] arr) {

		for (int t : arr) {
			print(t);
		}
		println();
	}

	public static void logArray(long[] arr, String separator) {

		if (Objects.nonNull(arr)) {
			if (arr.length == 0) {
				print();
			} else {
				for (int i = 0; i < arr.length - 1; i++) {
					print(arr[i] + separator);
				}
				print(arr[arr.length - 1]);
			}

		}
		println();
	}

	public static void logArray(long[] arr) {

		for (long t : arr) {
			print(t);
		}
		println();
	}

	public static void logArray(short[] arr, String separator) {

		if (Objects.nonNull(arr)) {
			if (arr.length == 0) {
				print();
			} else {
				for (int i = 0; i < arr.length - 1; i++) {
					print(arr[i] + separator);
				}
				print(arr[arr.length - 1]);
			}

		}
		println();
	}

	public static void logArray(short[] arr) {

		for (short t : arr) {
			print(t);
		}
		println();
	}

	public static void logArray(char[] arr, String separator) {

		if (Objects.nonNull(arr)) {
			if (arr.length == 0) {
				print();
			} else {
				for (int i = 0; i < arr.length - 1; i++) {
					print(arr[i] + separator);
				}
				print(arr[arr.length - 1]);
			}

		}
		println();
	}

	public static void logArray(char[] arr) {

		for (char t : arr) {
			print(t);
		}
		println();
	}

	public static void logArray(byte[] arr, String separator) {

		if (Objects.nonNull(arr)) {
			if (arr.length == 0) {
				print();
			} else {
				for (int i = 0; i < arr.length - 1; i++) {
					print(arr[i] + separator);
				}
				print(arr[arr.length - 1]);
			}

		}
		println();
	}

	public static void logArray(byte[] arr) {

		for (byte t : arr) {
			print(t);
		}
		println();
	}

	public static void logArray(boolean[] arr, String separator) {

		if (Objects.nonNull(arr)) {
			if (arr.length == 0) {
				print();
			} else {
				for (int i = 0; i < arr.length - 1; i++) {
					print(arr[i] + separator);
				}
				print(arr[arr.length - 1]);
			}

		}
		println();
	}

	public static void logArray(boolean[] arr) {

		for (boolean t : arr) {
			print(t);
		}
		println();
	}

	public static void logArray(float[] arr, String separator) {

		if (Objects.nonNull(arr)) {
			if (arr.length == 0) {
				print();
			} else {
				for (int i = 0; i < arr.length - 1; i++) {
					print(arr[i] + separator);
				}
				print(arr[arr.length - 1]);
			}

		}
		println();
	}

	public static void logArray(float[] arr) {

		for (float t : arr) {
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

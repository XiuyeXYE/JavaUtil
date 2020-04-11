package com.xiuye.util.log;

import java.io.PrintStream;
import java.util.Objects;

/**
 * System.out extension
 * 
 * @author xiuye
 *
 */
public class XLog {

	/**
	 * output many params
	 * 
	 * @param <T>
	 * @param t   any many params for output
	 */
	@SafeVarargs
	public static <T> void println(T... t) {
		log(t);
	}

	/**
	 * output many params
	 * 
	 * @param <T>
	 * @param t
	 * @see log
	 */
	@SafeVarargs
	public static <T> void lg(T... t) {
		log(t);
	}

	/**
	 * output level 1 array!
	 * 
	 * @param <T>
	 * @param arr
	 */
	public static <T> void logArray(T[] arr) {

		for (T t : arr) {
			print(t);
		}
		println();
	}

	/**
	 * output level 1 array with separator
	 * 
	 * @param <T>
	 * @param arr
	 * @param separator
	 */
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

	/**
	 * output level 1 array with separator
	 * 
	 * @param arr
	 * @param separator
	 */
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

	/**
	 * output level 1 array with separator
	 * 
	 * @param arr
	 */
	public static void logArray(double[] arr) {

		for (double t : arr) {
			print(t);
		}
		println();
	}

	/**
	 * output level 1 array with separator
	 * 
	 * @param arr
	 * @param separator
	 */
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

	/**
	 * output level 1 array
	 * 
	 * @param arr
	 */
	public static void logArray(int[] arr) {

		for (int t : arr) {
			print(t);
		}
		println();
	}

	/**
	 * output level 1 array with separator
	 * 
	 * @param arr
	 * @param separator
	 */
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

	/**
	 * output level 1 array
	 * 
	 * @param arr
	 */
	public static void logArray(long[] arr) {

		for (long t : arr) {
			print(t);
		}
		println();
	}

	/**
	 * output level 1 array with separator
	 * 
	 * @param arr
	 * @param separator
	 */
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

	/**
	 * output level 1 array
	 * 
	 * @param arr
	 */
	public static void logArray(short[] arr) {

		for (short t : arr) {
			print(t);
		}
		println();
	}

	/**
	 * output level 1 array
	 * 
	 * @param arr
	 * @param separator
	 */
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

	/**
	 * output level 1 array
	 * 
	 * @param arr
	 */
	public static void logArray(char[] arr) {

		for (char t : arr) {
			print(t);
		}
		println();
	}

	/**
	 * output level 1 array with separator
	 * 
	 * @param arr
	 * @param separator
	 */
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

	/**
	 * output level 1 array
	 * 
	 * @param arr
	 */
	public static void logArray(byte[] arr) {

		for (byte t : arr) {
			print(t);
		}
		println();
	}

	/**
	 * output level 1 array with separator
	 * 
	 * @param arr
	 * @param separator
	 */
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

	/**
	 * output level 1 array
	 * 
	 * @param arr
	 */
	public static void logArray(boolean[] arr) {

		for (boolean t : arr) {
			print(t);
		}
		println();
	}

	/**
	 * output level 1 array with separator
	 * 
	 * @param arr
	 * @param separator
	 */
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

	/**
	 * output level 1 array
	 * 
	 * @param arr
	 */
	public static void logArray(float[] arr) {

		for (float t : arr) {
			print(t);
		}
		println();
	}

	private static boolean OUTPUT = true;

	/**
	 * set output true/false
	 * 
	 * @param output
	 * @return old output value
	 */
	public static boolean setOutput(boolean output) {
		boolean oldOutput = OUTPUT;
		OUTPUT = output;
		return oldOutput;
	}

	/**
	 * get output value
	 * 
	 * @return true/false
	 */
	public static boolean isOutput() {
		return OUTPUT;
	}

	/**
	 * set output stream
	 * 
	 * @param out
	 * @return
	 */
	public static PrintStream setOut(PrintStream out) {
		PrintStream oldOut = System.out;
		System.setOut(out);
		return oldOut;
	}

	/**
	 * set err output stream!
	 * 
	 * @param err
	 * @return
	 */
	public static PrintStream setErr(PrintStream err) {
		PrintStream oldErr = System.err;
		System.setErr(err);
		return oldErr;
	}

	/**
	 * get output stream
	 * 
	 * @return
	 */
	public static PrintStream getOut() {
		return System.out;
	}

	/**
	 * get err output stream
	 * 
	 * @return
	 */
	public static PrintStream getErr() {
		return System.err;
	}

	/**
	 * output many params
	 * 
	 * @param <T>
	 * @param t
	 */
	@SafeVarargs
	public static <T> void log(T... t) {
		if (!OUTPUT)
			return;
		if (t.length == 0)
			System.out.println();
		else {
			for (int i = 0; i < t.length - 1; i++) {
				System.out.print(t[i] + " ");
			}
			System.out.println(t[t.length - 1]);
		}

	}

	/**
	 * output many params
	 * 
	 * @param <T>
	 * @param t
	 */
	@SafeVarargs
	public static <T> void err(T... t) {
		if (!OUTPUT)
			return;
		if (t.length == 0)
			System.err.println();
		else {
			for (int i = 0; i < t.length - 1; i++) {
				System.err.print(t[i] + " ");
			}
			System.err.println(t[t.length - 1]);
		}

	}

	/**
	 * output many params
	 * 
	 * @param <T>
	 * @param ts
	 */
	@SafeVarargs
	public static <T> void print(T... ts) {
		if (!OUTPUT)
			return;
		if (ts.length > 0) {
			for (int i = 0; i < ts.length - 1; i++) {
				System.out.print(ts[i] + " ");
			}
			System.out.print(ts[ts.length - 1]);
		}

	}

}

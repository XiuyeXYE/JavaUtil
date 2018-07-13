package com.xiuye.util.log;

public class LogUtil {

	public static <T> void log(T... t) {

		for (T s : t) {
			System.out.print(s + " ");
		}
		System.out.println();
	}

	

}

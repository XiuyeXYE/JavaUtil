package com.xiuye.util.log;

public class LogUtil {

	public static <T> void println(T... t) {
		log(t);
	}
	public static <T> void log(T... t) {

		for (T s : t) {
			System.out.print(s + " ");
		}
		System.out.println();
	}

	
	
	public static <T> void print(T ...ts){
		for(T t : ts) {
			System.out.print(t+" ");
		}
	}

}

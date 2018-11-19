package com.xiuye.util.log;

public class LogUtil {

	
	public static <T> void log(T t) {
		System.out.println(t);
	}
	
	@SafeVarargs
	public static <T> void log(T... t) {

//		for (T s : t) {
//			System.out.print(s + " ");
//		}
//		System.out.println();
		int i=0;
		for(;i<t.length-1;i++) {
			System.out.print(t[i] + " ");
		}
		if(i<t.length) {
			System.out.println(t[t.length-1]);
		}
		else {
			System.out.println();
		}
	}

	

}

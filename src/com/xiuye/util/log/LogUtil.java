package com.xiuye.util.log;

public class LogUtil {

	public static <T> void println(T... t) {
		log(t);
	}
	@SafeVarargs
	public static <T> void log(T... t) {

		for (int i=0;i<t.length-1;i++) {
			System.out.print(t[i] + " ");
		}
		if(t.length>0) {
			System.out.println(t[t.length-1]);
		}
		else {
			System.out.println();
		}
	}

	
	
	public static <T> void print(T ...ts){
		for(T t : ts) {
			System.out.print(t+" ");
		}
	}

}

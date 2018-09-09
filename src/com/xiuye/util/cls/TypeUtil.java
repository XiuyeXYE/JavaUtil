package com.xiuye.util.cls;

public class TypeUtil {

	@SuppressWarnings("unchecked")
	public static<T,E extends T> T dynamic_cast(E e) {
		return (T) e;
	}
	
}

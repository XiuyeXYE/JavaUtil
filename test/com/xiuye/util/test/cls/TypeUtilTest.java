package com.xiuye.util.test.cls;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.xiuye.util.cls.XType;
import com.xiuye.util.log.XLog;

public class TypeUtilTest {

	private static class C {
		static {
			XLog.log("static");
		}

		private C() {
			XLog.log("C::construct");
		}
	}

	@Test
	public void testNew() {
		XType.newInstance(C::new);
		XType.newInstance(C::new);
	}

	public static void main(String[] args) {
		XType.newInstance(A::new);
		XType.newInstance(A::new, "ABC");
		XType.newInstance(A::new, 1);
		XType.newInstance(A::new, 1, 1, 2, 3, 4, 5, 6);
		// Now , T[] <=> T...t
//		TypeUtil.newInstance(A::new, new Integer[]{1,2});
		XType.newInstance(A::new, new int[] { 1, 2 });
		XType.newInstance(A::new, new Integer[] { 1, 2 }, new Integer[] { 1, 2 });
		XType.newInstance(A::new, 1, "abc", 1L);
		XType.newInstance(A::new, "GGGGGG", "abc");
		Object o = "ABC";
		String s = XType.cast(o);
		s = XType.cast("JJJJJJJJJJJJJJJJJJJ");
		XLog.log(s);
//		LogUtil.log(TypeUtil.dynamic_cast(o));//error
		o = XType.cast(s);
		XLog.log(o);
		XLog.log(XType.<String, Object>cast(o));
		XLog.log("ABC", 1, 2, 3, 4, 5, 6, "GGGG", 7L);
		XType.newInstance(B::new, 1, "abc", 1L);
		XLog.log(XType.map());
		XLog.log(XType.list());
		XLog.log(XType.set());
		Map<String, String> m = XType.map();
		m.put("A", "A");
		List<String> l = XType.list();
		l.add("123");
		l.add("123");
		Set<String> se = XType.set();
		se.add("OK");
		se.add("OK");
		XLog.log(m, l, se);
	}

	static class B {
		B(Integer i, String s, Long l) {
			XLog.log("A(int i,String s,Long l)");
		}
	}

	static class A {
		A() {
			XLog.log("A()");
		}

		A(int i) {
			XLog.log("A(int i)");
		}

		A(String s) {
			XLog.log("A(String s)");
		}

		A(String s1, String s2) {
			XLog.log("A(String s1,String s2)");
		}

//		A(Integer []s){
//			LogUtil.log("A(Integer []s)");
//		}
		A(Integer... s) {
			XLog.log("A(Integer ...s)");
		}

		A(String... s) {
			XLog.log("A(String ... s)");
		}

//		A(int...i){
//			LogUtil.log("A(int ... i)");
//		}
		A(int[] i) {
			XLog.log("A(int ... i)");
		}

		A(int i, String s, Long l) {
			XLog.log("A(int i,String s,Long l)");
		}

//		A(T...t){
//			LogUtil.log("A(T...t)");
//		}
		A(Object... t) {
			XLog.log("A(T...t)");
		}
	}

	@Test
	public void testNewArray() {
		// new 一维数组
		int[] i = XType.newInstance(int[]::new, 10);
		XLog.log(i.length);
		XLog.logArray(i);
		// new 一维数组
		String[] ss = XType.newInstance(String[]::new, 10);
		XLog.log(ss.length);
		XLog.logArray(ss, ":");
		// new 二维数组 => new String[10][];
		// new String[][](10/*第一个参数*/)
		String[][] s1 = XType.newInstance(String[][]::new, 10);
		// new 一维数组 => new String[8]
		s1[0] = XType.newInstance(String[]::new, 8);
		XLog.log(s1);
		XLog.logArray(s1);
		XLog.print(s1);
		XLog.println();
		XLog.println();
		// <=> the above
		String[][] s2 = new String[10][];
		XLog.log(s2);
		XLog.logArray(s2, ",");
//		String[]s =new String[0];
		XLog.print(1, 2, 3);
		XLog.print(1);
		XLog.print(2);
		XLog.print(3);
	}

	@Test
	public void testInstantiate() {
		class A {
			A(String... a) {
				XLog.logArray(a, " ");
			}
		}
//		XType.newInstance(A::new, 
//				"123","987","998"
//				,"123","987","998"
//				,"123","987","998"
//				,"123","987","998"
//				,"123","987","998"
//				);
	}

	public static <T, R> R cast(T t) {
		return (R) t;
	}

	@Test
	public void testCast() {
		int i = 100;
		long j = i;
		XLog.log(j);
//		int k = cast(j);//error
//		XLog.log(i);
	}

//	public static <R> R newi() {
//		return XType.newInstance(R::new);
//	}
	@Test
	public void newCases() {
		
	}
	
	@Test
	public void testSyncCollections() {
		XLog.lg(XType.sync(XType.set()));
		XLog.lg(XType.sync(XType.list()));
		XLog.lg(XType.sync(XType.map()));
	}

}

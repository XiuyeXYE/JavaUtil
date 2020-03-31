package com.xiuye.util.cls;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xiuye.util.code.XYClassLoader;

public class XType {

	// 适合引用类型 ,不适合 基本类型
	@SuppressWarnings("unchecked")
	public static <R extends T, T> R cast(T e) {
		return (R) e;
	}

	/**
	 * boolean byte char short int long float double
	 */
	
	

	public interface DefaultConstructor<R> {
		R construct();
	}

	public interface ConstructorWithParam<R, T> {
		R construct(T t);
	}

	public interface ConstructorWithTwoParams<R, T1, T2> {
		R construct(T1 t1, T2 t2);
	}

	public interface ConstructorWithThreeParams<R, T1, T2, T3> {
		R construct(T1 t1, T2 t2, T3 t3);
	}

	public interface ConstructorWithFourParams<R, T1, T2, T3, T4> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4);
	}

	public interface ConstructorWithFiveParams<R, T1, T2, T3, T4, T5> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
	}

	public interface ConstructorWithSixParams<R, T1, T2, T3, T4, T5, T6> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
	}

	public interface ConstructorWithSevenParams<R, T1, T2, T3, T4, T5, T6, T7> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
	}

	public interface ConstructorWithEightParams<R, T1, T2, T3, T4, T5, T6, T7, T8> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
	}

	public interface ConstructorWithParams<R, T> {
		@SuppressWarnings("unchecked")
		R construct(T... t);
	}

	public static <R> R newInstance(DefaultConstructor<R> c) {
		return c.construct();
	}

	public static <R, T> R newInstance(ConstructorWithParam<R, T> c, T t) {
		return c.construct(t);
	}

	public static <R, T1, T2> R newInstance(ConstructorWithTwoParams<R, T1, T2> c, T1 t1, T2 t2) {
		return c.construct(t1, t2);
	}

	public static <R, T1, T2, T3> R newInstance(ConstructorWithThreeParams<R, T1, T2, T3> c, T1 t1, T2 t2, T3 t3) {
		return c.construct(t1, t2, t3);
	}

	public static <R, T1, T2, T3, T4> R newInstance(ConstructorWithFourParams<R, T1, T2, T3, T4> c, T1 t1, T2 t2, T3 t3,
			T4 t4) {
		return c.construct(t1, t2, t3, t4);
	}

	public static <R, T1, T2, T3, T4, T5> R newInstance(ConstructorWithFiveParams<R, T1, T2, T3, T4, T5> c, T1 t1,
			T2 t2, T3 t3, T4 t4, T5 t5) {
		return c.construct(t1, t2, t3, t4, t5);
	}

	public static <R, T1, T2, T3, T4, T5, T6> R newInstance(ConstructorWithSixParams<R, T1, T2, T3, T4, T5, T6> c,
			T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
		return c.construct(t1, t2, t3, t4, t5, t6);
	}

	public static <R, T1, T2, T3, T4, T5, T6, T7> R newInstance(
			ConstructorWithSevenParams<R, T1, T2, T3, T4, T5, T6, T7> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6,
			T7 t7) {
		return c.construct(t1, t2, t3, t4, t5, t6, t7);
	}

	public static <R, T1, T2, T3, T4, T5, T6, T7, T8> R newInstance(
			ConstructorWithEightParams<R, T1, T2, T3, T4, T5, T6, T7, T8> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6,
			T7 t7, T8 t8) {
		return c.construct(t1, t2, t3, t4, t5, t6, t7, t8);
	}

	// Now , T[] <=> T...t
	@SafeVarargs
	public static <R, T> R newInstance(ConstructorWithParams<R, T> c, T... t) {
		return c.construct(t);
	}

//	public static<R,T> R newInstance(ConstructorWithParams<R,T> c,Object...t) {
//		return c.construct(t);
//	}

	/**
	 * hashmap
	 * 
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Map<K, V> map() {
		return newInstance(HashMap::new);
	}

	/**
	 * arraylist
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> list() {
		return newInstance(ArrayList::new);
	}

	/**
	 * hashset
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> set() {
		return newInstance(HashSet::new);
	}

	public static <T> List<T> toList(T[] a) {
		List<T> L = list();
		for (T e : a) {
			L.add(e);
		}
		return L;
	}

	public static List<Integer> toList(int[] a) {
		List<Integer> L = list();
		for (int e : a) {
			L.add(e);
		}
		return L;
	}

	public static ClassLoader createClassLoader(URL[] urls) {
		return new XYClassLoader(urls);
	}

	/**
	 * current class path: .
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public static ClassLoader createClassLoader() throws MalformedURLException {
		return createClassLoader(".");
	}

	public static ClassLoader createClassLoader(String... paths) throws MalformedURLException {
		List<URL> urlsList = list();
		for (String p : paths) {
			URL u = Paths.get(p).toUri().toURL();
			urlsList.add(u);
		}
		URL[] urls = urlsList.toArray(new URL[urlsList.size()]);
//		LogUtil.logarray(urls);
		return createClassLoader(urls);
	}

	public static ClassLoader createClassLoader(List<String> paths) throws MalformedURLException {

		URL[] urls = new URL[paths.size()];

		for (int i = 0; i < urls.length; i++) {
			urls[i] = Paths.get(paths.get(i)).toUri().toURL();
		}

		return createClassLoader(urls);

	}

//	public static Class<?> loadClass(ClassLoader cl, String name) throws ClassNotFoundException {
//		return cl.loadClass(name);
//	}
}

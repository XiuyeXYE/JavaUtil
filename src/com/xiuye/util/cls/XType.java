package com.xiuye.util.cls;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Type operator
 * 
 * @author xiuye
 *
 */
public class XType {

	// 适合引用类型 ,不适合 基本类型
	/**
	 * cast T to R In general,T is ancestor class , R is subclass
	 * 
	 * @param <R  extends T>
	 * @param <T>
	 * @param e   instantiated object
	 * @return reference of subclass
	 */
	@SuppressWarnings("unchecked")
	public static <R extends T, T> R cast(T e) {
		return (R) e;
	}

	// boolean byte char short int long float double

	/**
	 * for default constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 */
	public interface DefaultConstructor<R> {
		R construct();
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T>
	 */
	public interface ConstructorWithParam<R, T> {
		R construct(T t);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 */
	public interface ConstructorWithTwoParams<R, T1, T2> {
		R construct(T1 t1, T2 t2);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 */
	public interface ConstructorWithThreeParams<R, T1, T2, T3> {
		R construct(T1 t1, T2 t2, T3 t3);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 */
	public interface ConstructorWithFourParams<R, T1, T2, T3, T4> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 */
	public interface ConstructorWithFiveParams<R, T1, T2, T3, T4, T5> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 */
	public interface ConstructorWithSixParams<R, T1, T2, T3, T4, T5, T6> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param <T7>
	 */
	public interface ConstructorWithSevenParams<R, T1, T2, T3, T4, T5, T6, T7> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param <T7>
	 * @param <T8>
	 */
	public interface ConstructorWithEightParams<R, T1, T2, T3, T4, T5, T6, T7, T8> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param <T7>
	 * @param <T8>
	 * @param <T9>
	 */
	public interface ConstructorWithNineParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param <T7>
	 * @param <T8>
	 * @param <T9>
	 * @param <T10>
	 */
	public interface ConstructorWithTenParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
		R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10);
	}

	/**
	 * for parameters-ed constructor
	 * 
	 * @author xiuye
	 *
	 * @param <R>
	 * @param <T>
	 */
	public interface ConstructorWithParams<R, T> {
		@SuppressWarnings("unchecked")
		R construct(T... t);
	}

	/**
	 * instantiate object by default constructor
	 * 
	 * @param <R>
	 * @param c
	 * @return
	 */
	public static <R> R newInstance(DefaultConstructor<R> c) {
		return c.construct();
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T>
	 * @param c
	 * @param t
	 * @return
	 */
	public static <R, T> R newInstance(ConstructorWithParam<R, T> c, T t) {
		return c.construct(t);
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param c
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static <R, T1, T2> R newInstance(ConstructorWithTwoParams<R, T1, T2> c, T1 t1, T2 t2) {
		return c.construct(t1, t2);
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param c
	 * @param t1
	 * @param t2
	 * @param t3
	 * @return
	 */
	public static <R, T1, T2, T3> R newInstance(ConstructorWithThreeParams<R, T1, T2, T3> c, T1 t1, T2 t2, T3 t3) {
		return c.construct(t1, t2, t3);
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param c
	 * @param t1
	 * @param t2
	 * @param t3
	 * @param t4
	 * @return
	 */
	public static <R, T1, T2, T3, T4> R newInstance(ConstructorWithFourParams<R, T1, T2, T3, T4> c, T1 t1, T2 t2, T3 t3,
			T4 t4) {
		return c.construct(t1, t2, t3, t4);
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param c
	 * @param t1
	 * @param t2
	 * @param t3
	 * @param t4
	 * @param t5
	 * @return
	 */
	public static <R, T1, T2, T3, T4, T5> R newInstance(ConstructorWithFiveParams<R, T1, T2, T3, T4, T5> c, T1 t1,
			T2 t2, T3 t3, T4 t4, T5 t5) {
		return c.construct(t1, t2, t3, t4, t5);
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param c
	 * @param t1
	 * @param t2
	 * @param t3
	 * @param t4
	 * @param t5
	 * @param t6
	 * @return
	 */
	public static <R, T1, T2, T3, T4, T5, T6> R newInstance(ConstructorWithSixParams<R, T1, T2, T3, T4, T5, T6> c,
			T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
		return c.construct(t1, t2, t3, t4, t5, t6);
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param <T7>
	 * @param c
	 * @param t1
	 * @param t2
	 * @param t3
	 * @param t4
	 * @param t5
	 * @param t6
	 * @param t7
	 * @return
	 */
	public static <R, T1, T2, T3, T4, T5, T6, T7> R newInstance(
			ConstructorWithSevenParams<R, T1, T2, T3, T4, T5, T6, T7> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6,
			T7 t7) {
		return c.construct(t1, t2, t3, t4, t5, t6, t7);
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param <T7>
	 * @param <T8>
	 * @param c
	 * @param t1
	 * @param t2
	 * @param t3
	 * @param t4
	 * @param t5
	 * @param t6
	 * @param t7
	 * @param t8
	 * @return
	 */
	public static <R, T1, T2, T3, T4, T5, T6, T7, T8> R newInstance(
			ConstructorWithEightParams<R, T1, T2, T3, T4, T5, T6, T7, T8> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6,
			T7 t7, T8 t8) {
		return c.construct(t1, t2, t3, t4, t5, t6, t7, t8);
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param <T7>
	 * @param <T8>
	 * @param <T9>
	 * @param c
	 * @param t1
	 * @param t2
	 * @param t3
	 * @param t4
	 * @param t5
	 * @param t6
	 * @param t7
	 * @param t8
	 * @param t9
	 * @return
	 */
	public static <R, T1, T2, T3, T4, T5, T6, T7, T8, T9> R newInstance(
			ConstructorWithNineParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5,
			T6 t6, T7 t7, T8 t8, T9 t9) {
		return c.construct(t1, t2, t3, t4, t5, t6, t7, t8, t9);
	}

	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param <T5>
	 * @param <T6>
	 * @param <T7>
	 * @param <T8>
	 * @param <T9>
	 * @param <T10>
	 * @param c
	 * @param t1
	 * @param t2
	 * @param t3
	 * @param t4
	 * @param t5
	 * @param t6
	 * @param t7
	 * @param t8
	 * @param t9
	 * @param t10
	 * @return
	 */
	public static <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> R newInstance(
			ConstructorWithTenParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5,
			T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
		return c.construct(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
	}

	// Now , T[] <=> T...t
	/**
	 * instantiate object by parameters-ed constructor
	 * 
	 * @param <R>
	 * @param <T>
	 * @param c
	 * @param t
	 * @return
	 */
	@SafeVarargs
	public static <R, T> R newInstance(ConstructorWithParams<R, T> c, T... t) {
		return c.construct(t);
	}

//	public static<R,T> R newInstance(ConstructorWithParams<R,T> c,Object...t) {
//		return c.construct(t);
//	}

	/**
	 * HashMap
	 * 
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Map<K, V> map() {
		return newInstance(HashMap::new);
	}

	/**
	 * ArrayList
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> list() {
		return newInstance(ArrayList::new);
	}

	/**
	 * HashSet
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> set() {
		return newInstance(HashSet::new);
	}

	/**
	 * to ArrayList
	 * 
	 * @param <T>
	 * @param a
	 * @return
	 */
	public static <T> List<T> toList(T[] a) {
		List<T> L = list();
		for (T e : a) {
			L.add(e);
		}
		return L;
	}

	/**
	 * to ArrayList
	 * 
	 * @param a
	 * @return
	 */
	public static List<Integer> toList(int[] a) {
		List<Integer> L = list();
		for (int e : a) {
			L.add(e);
		}
		return L;
	}

	/**
	 * create ClassLoader with paths
	 * 
	 * @param urls
	 * @return
	 */
	public static XYClassLoader createClassLoader(URL[] urls) {
		return new XYClassLoader(urls);
	}

	/**
	 * current class path: .
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public static XYClassLoader createClassLoader() throws MalformedURLException {
		return createClassLoader(".");
	}

	/**
	 * create ClassLoader with paths
	 * 
	 * @param paths
	 * @return
	 * @throws MalformedURLException
	 */
	public static XYClassLoader createClassLoader(String... paths) throws MalformedURLException {
		List<URL> urlsList = list();
		for (String p : paths) {
			URL u = Paths.get(p).toUri().toURL();
			urlsList.add(u);
		}
		URL[] urls = urlsList.toArray(new URL[urlsList.size()]);
//		LogUtil.logarray(urls);
		return createClassLoader(urls);
	}

	/**
	 * create ClassLoader with paths
	 * 
	 * @param paths
	 * @return
	 * @throws MalformedURLException
	 */
	public static XYClassLoader createClassLoader(List<String> paths) throws MalformedURLException {

		URL[] urls = new URL[paths.size()];

		for (int i = 0; i < urls.length; i++) {
			urls[i] = Paths.get(paths.get(i)).toUri().toURL();
		}

		return createClassLoader(urls);

	}

	/**
	 * if first-value is null,return second value
	 * else return first value
	 * 		@param s1
	 * 		@param s2
	 * 		@return
	 */
	public static String nvl(String s1, String s2) {
		if (Objects.isNull(s1))
			return s2;
		return s1;

	}

//	public static Class<?> loadClass(ClassLoader cl, String name) throws ClassNotFoundException {
//		return cl.loadClass(name);
//	}
	
	/**
	 * synchronizedMap
	 * synchronizedSet
	 * synchronizedList
	 * 		@param <T>
	 * 		@param t
	 * 		@return
	 */
	public static <T> T sync(T t) {
		if(t instanceof Map) {
			return cast(Collections.synchronizedMap(cast(t)));
		}
		if(t instanceof List) {
			return cast(Collections.synchronizedList(cast(t)));
		}
		if(t instanceof Set) {
			return cast(Collections.synchronizedSet(cast(t)));
		}
		return t;
	}
	
}

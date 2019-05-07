package com.xiuye.util.cls;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.xiuye.cls.JavaSourceCode;
import com.xiuye.cls.XYClassLoader;

public class TypeUtil {

	// 适合应用类型不适合，基本类型
	@SuppressWarnings("unchecked")
	public static <R extends T, T> R dynamic_cast(T e) {
		return (R) e;
	}

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

	public static <K, V> Map<K, V> createMap() {
		return newInstance(HashMap::new);
	}

	public static <T> List<T> createList() {
		return newInstance(ArrayList::new);
	}

	public static <T> Set<T> createSet() {
		return newInstance(HashSet::new);
	}

	/**
	 * output class file to current path
	 * 
	 * @param files
	 * @return
	 */
	public static boolean compileFile(List<String> files) {
		return compileFile(".", files);
	}

	/**
	 * assign output path
	 * 
	 * @param binPath
	 * @param files
	 * @return
	 */
	public static boolean compileFile(String binPath, List<String> files) {
		List<String> options = createList();
		options.add("-d");
		options.add(binPath);
		return compileFile(options, files);
	}

	public static boolean compileFile(List<String> options, List<String> filenames) {

		JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
//		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		try (StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null)) {
			Iterable<? extends JavaFileObject> compilationUnits = sjfm.getJavaFileObjectsFromStrings(filenames);

			boolean b = jc.getTask(null, sjfm, null, options, null, compilationUnits).call();
//			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
//				LogUtil.err("Error on line", diagnostic.getLineNumber(), "in", diagnostic.getSource().toUri());
			return b;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean compileFile(String... arguments) {
		JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
		return jc.run(null, null, null, arguments) == 0;
	}

	/**
	 * compile source code
	 * 
	 * @param codes
	 * @return
	 */
	public static boolean compileCode(Map<String, String> codes) {
		return compileCode(".", codes);
	}

	public static boolean compileCode(String binPath, Map<String, String> codes) {
		List<String> options = createList();
		options.add("-d");
		options.add(binPath);
		return compileCode(options, codes);
	}

	/**
	 * codes => package:code string
	 * 
	 * @param options
	 * @param codes
	 * @return
	 */
	public static boolean compileCode(List<String> options, Map<String, String> codes) {
		JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
//		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		try (StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null)) {

			List<JavaSourceCode> compilationUnits = createList();
			for (Entry<String, String> code : codes.entrySet()) {
				compilationUnits.add(new JavaSourceCode(code.getKey(), code.getValue()));
			}
			boolean b = jc.getTask(null, sjfm, null, options, null, compilationUnits).call();
//			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
//				LogUtil.err("Error on line", diagnostic.getLineNumber(), "in", diagnostic.getSource().toUri());
			return b;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
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
		List<URL> urlsList = createList();
		for (String p : paths) {
			URL u = Paths.get(p).toUri().toURL();
			urlsList.add(u);
		}
		URL[] urls = urlsList.toArray(new URL[urlsList.size()]);
//		LogUtil.logarray(urls);
		return createClassLoader(urls);
	}

//	public static Class<?> loadClass(ClassLoader cl, String name) throws ClassNotFoundException {
//		return cl.loadClass(name);
//	}
}

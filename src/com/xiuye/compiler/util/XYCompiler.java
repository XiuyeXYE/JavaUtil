package com.xiuye.compiler.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.xiuye.compiler.JavaSourceCode;
import com.xiuye.util.cls.TypeUtil;

/**
 * java code compiler
 * @author admin
 *
 */
public class XYCompiler {

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
		List<String> options = TypeUtil.createList();
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
		List<String> options = TypeUtil.createList();
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

			List<JavaSourceCode> compilationUnits = TypeUtil.createList();
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

}

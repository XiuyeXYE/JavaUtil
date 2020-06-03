package com.xiuye.util.code;

import com.xiuye.util.cls.XType;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * java code compiler
 *
 * @author xiuye
 */
public class XYCompiler {

    /**
     * compile java source code on current path . output class file to current path
     *
     * @param files
     * @return
     */
    public static boolean compileFile(List<String> files) {
        return compileFile(".", files);
    }

    /**
     * compile java source code assign output path
     *
     * @param binPath
     * @param files
     * @return
     */
    public static boolean compileFile(String binPath, List<String> files) {
        List<String> options = XType.list();
        options.add("-d");
        options.add(binPath);
        return compileFile(options, files);
    }

    /**
     * compile java source code
     *
     * @param options   compile arguments
     * @param filenames
     * @return
     */
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

    /**
     * compile java source code for java file
     *
     * @param arguments
     * @return
     */
    public static boolean compileFile(String... arguments) {
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        return jc.run(null, null, null, arguments) == 0;
    }

    /**
     * compile source code for string java code
     *
     * @param codes
     * @return
     */
    public static boolean compileCode(Map<String, String> codes) {
        return compileCode(".", codes);
    }

    /**
     * compile java source string code
     *
     * @param binPath
     * @param codes
     * @return
     */
    public static boolean compileCode(String binPath, Map<String, String> codes) {
        List<String> options = XType.list();
        options.add("-d");
        options.add(binPath);
        return compileCode(options, codes);
    }

    /**
     * compile java source code codes[Map structure] : package(String) =>
     * code(string)
     *
     * @param options compile arguments
     * @param codes
     * @return
     */
    public static boolean compileCode(List<String> options, Map<String, String> codes) {
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
//		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        try (StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null)) {

            List<JavaSourceCode> compilationUnits = XType.list();
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

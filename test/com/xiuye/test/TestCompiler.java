package com.xiuye.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Map;

import org.junit.Test;

import com.xiuye.util.cls.XClassLoader;
import com.xiuye.util.cls.XType;
import com.xiuye.util.code.XCompiler;
import com.xiuye.util.code.gen.ClassInfo;
import com.xiuye.util.log.XLog;

public class TestCompiler {
	
	@Test
	public void testGenerateClass() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		ClassInfo dc = new ClassInfo("com.xy.app", "ABC");
		dc.addImportPackage("com.xiuye.test.TestCompiler");
		dc.addConstructor("System.out.println(\"Hello World!Constructor!\");"
				+ "System.out.println(a);"
				+ "System.out.println(b);"
				+ "","int","a","int","b");
		dc.addField("int", "lang");
		dc.addField("int", "lang2", "100");
		dc.addMethod("int", "f1", "return a>b?a:b;", "int", "a", "int", "b");
//		dc.addMethod("int", "f2", null, "int", "a", "int", "b");
		dc.addMethod("int", "f3", "TestCompiler test = new TestCompiler();"
				+ "try{"
				+ "test.testGenerateClass();"
				+ "}catch(Exception e1){"
				+ "e1.printStackTrace();"
				+ "}"
				+ "return 100;", "int", "a", "int", "b");
		XLog.ln(dc);
		XLog.ln(XType.firstUpperCase("abcdefg"));
		XLog.ln(XType.firstLowerCase("ABCDEFG"));
		
		XLog.ln(dc.getFullName());
		
		Map<String,String> code = XType.map();
		code.put(dc.getFullName(),dc.toString());
		XCompiler.compileCode(code);
		XClassLoader cl = XType.createClassLoader();
		
		Class<?> clazz = cl.load(dc.getFullName());
		XLog.ln(clazz);
		Method m = clazz.getMethod("getLang2");
		Constructor<?> con = clazz.getConstructor(int.class,int.class);
		Object obj = con.newInstance(7,88);
		int a = (int) m.invoke(obj);
		XLog.ln(a);
		Method f1 = clazz.getMethod("f1", int.class,int.class);
		XLog.ln(f1.invoke(obj, 5,8));
		
		//下面的调用会无限的循环,无限的套娃!!!
		//好好看看 f3 和 外部类的 调用关系!!!
		Method f3 = clazz.getMethod("f3", int.class,int.class);
		XLog.ln(f3.invoke(obj, 98,88));
		
	}

	public static void main(String[] args) {

	}

}

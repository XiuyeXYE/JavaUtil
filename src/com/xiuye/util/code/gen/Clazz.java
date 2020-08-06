package com.xiuye.util.code.gen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Map;

import com.xiuye.util.cls.XClassLoader;
import com.xiuye.util.cls.XType;
import com.xiuye.util.code.XCompiler;
import com.xiuye.util.log.XLog;

public class Clazz {

	private ClassInfo clazz;

	public Clazz(String packageName, String simpleClassName) {
		clazz = new ClassInfo();
		clazz.setPackageName(packageName);
		clazz.setAccess(ClassInfo.ACCESS_PUBLIC);
//		clazz.addModifier(ClassInfo.FINAL_MODIFIER);
		clazz.setType(ClassInfo.TYPE_CLASS);
		clazz.setName(simpleClassName);

	}

	public String getFullName() {
		return clazz.getPackageName()+ "." + clazz.getName();
	}

	void addField(String type, String name) {
		FieldInfo fi = new FieldInfo();
		fi.setAccess(FieldInfo.ACCESS_PRIVATE);
//		fi.addModifier(FieldInfo.STATIC_MODIFIER);
//		fi.addModifier(FieldInfo.FINAL_MODIFIER);
		fi.setType(type);
		fi.setName(name);
		clazz.addField(fi);
	}

	void addField(String type, String name, String value) {
		FieldInfo fi = new FieldInfo();
		fi.setAccess(FieldInfo.ACCESS_PRIVATE);
//		fi.addModifier(FieldInfo.STATIC_MODIFIER);
//		fi.addModifier(FieldInfo.FINAL_MODIFIER);
		fi.setType(type);
		fi.setName(name);
		fi.setValue(value);
		clazz.addField(fi);
		String bName = XType.firstUpperCase(name);
		addMethod("void", "set" + bName, "this." + name + "=" + name + ";", type, name);
		addMethod(type, "get" + bName, "return this." + name + ";");
	}

	void addMethod(String returnType, String name, String methodBody, String... params) {

		if (params.length % 2 != 0) {
			throw new RuntimeException("Input params's number must be even times!");
		}
		FunctionInfo fi = new FunctionInfo();
		fi.setAccess(FunctionInfo.ACCESS_PUBLIC);
//		fi.addModifier(FunctionInfo.STATIC_MODIFIER);
//		fi.addModifier(FunctionInfo.FINAL_MODIFIER);
		fi.setType(returnType);
		fi.setName(name);
		for (int i = 0; i < params.length; i += 2) {
			fi.addParameter(params[i], params[i + 1]);
		}

		fi.setFunctionBody(methodBody);
		clazz.addFunction(fi);
	}

	public ClassInfo getClazz() {
		return clazz;
	}

	void importPackage(String importPacage) {
		clazz.addImportPackage(importPacage);
	}

	@Override
	public String toString() {
		return clazz.toString();
	}

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
//		Clazz dc = new Clazz("com.xy.app", "ABC");
		ClassInfo dc = new ClassInfo("com.xy.app", "ABC");
		dc.addField("int", "lang");
		dc.addField("int", "lang2", "100");
		dc.addMethod("int", "f1", "return a>b?a:b;", "int", "a", "int", "b");
//		dc.addMethod("int", "f2", null, "int", "a", "int", "b");

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
		int a = (int) m.invoke(clazz.newInstance());
		XLog.ln(a);
				
	}

}

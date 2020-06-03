package com.xiuye.util.test.code;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.xiuye.util.cls.XType;
import com.xiuye.util.cls.XYClassLoader;
import com.xiuye.util.code.XCode;
import com.xiuye.util.code.XYCompiler;
import com.xiuye.util.code.gen.ClassInfo;
import com.xiuye.util.code.gen.FieldInfo;
import com.xiuye.util.code.gen.FunctionInfo;
import com.xiuye.util.log.XLog;

public class XCodeTest {

	@Test
	public void test() throws InterruptedException {
		XCode.runS(()->{
			XLog.ln("ABC");
			XLog.ln("ABC");
			XLog.ln("ABC");
			XLog.ln("ABC");
			XLog.ln("ABC");
		});
		XCode.runMS(()->{
			XLog.ln("ABC");
			XLog.line("ABC");
			XLog.ln("ABC");
			XLog.line("ABC");
			XLog.ln("ABC");
		});
		XCode.runNS(()->{
			XLog.ln("ABC");
			XLog.line("ABC");
			XLog.ln("ABC");
			XLog.line("ABC");
			XLog.ln("ABC");
		});
		XCode.run(()->{
			XLog.ln("ABC");
			XLog.line("ABC");
			XLog.ln("ABC");
			XLog.line("ABC");
			XLog.ln("ABC");
		});
		XLog.line("DEF");
		XLog.ln("DEF");
	}

	@Test
	public void testCompileCode()
			throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Map<String, String> code = XType.map();
		code.put("com.xiuye.util.test.code.AI",
				"package com.xiuye.util.test.code;" + "import com.xiuye.util.log.XLog;"
						+ " public interface AI extends Runnable{" + " public void f();"
						+ "default void g(){XLog.lg(\"AI::g()\");}}");
		code.put("com.xiuye.util.test.code.AIImpl",
				"package com.xiuye.util.test.code;" + "import com.xiuye.util.log.XLog;"
						+ " public class AIImpl implements AI{" + " public void f(){XLog.lg(\"AI::f()\");} " + ""
						+ "public void run(){XLog.lg(\"AIImpl::run()\");}" + "}");
		XLog.lg(XYCompiler.compileCode(code) ? "successful" : "failure");
		XYClassLoader cl = XType.createClassLoader();
//		XLog.lg(cl.loadClass("com.xiuye.util.test.code.AI"));
		Class<?> clazz = cl.load("com.xiuye.util.test.code.AIImpl");
		clazz = cl.load("com.xiuye.util.test.code.AIImpl");
		XLog.lg(clazz);
		XLog.lg(clazz.newInstance());
		Runnable run = XType.cast(clazz.newInstance());
		run.run();

		XLog.log(run.getClass().getClassLoader());
		XLog.lg(run.getClass().getClassLoader().getParent());
		XLog.lg(run.getClass().getClassLoader().getParent().getParent());
		XLog.lg(run.getClass().getClassLoader().getParent().getParent().getParent());
		XLog.lg(Class.forName("java.lang.Runnable").getClassLoader());
		Map<String, String> codes2 = XType.map();
		codes2.put("com.xiuye.BIImpl",
				"package com.xiuye;" + "import com.xiuye.util.test.code.XCodeTest.BI;"
						+ "import com.xiuye.util.log.XLog;" + " public class BIImpl implements BI{" + "public void b(){"
						+ "XLog.lg(\"BIImpl::b()\");" + "}" + "}");
		codes2.put("com.xiuye.util.test.code.CIImpl",
				"package com.xiuye.util.test.code;" + "import com.xiuye.util.test.code.XCodeTest.CI;"
						+ "import com.xiuye.util.log.XLog;" + " public class CIImpl implements CI{" + "public void c(){"
						+ "XLog.lg(\"CIImpl::c()\");" + "}" + "}");
		XLog.lg(XYCompiler.compileCode(codes2));
		XLog.lg(cl.load("com.xiuye.BIImpl"));
		XLog.lg(cl.load("com.xiuye.BIImpl").newInstance());
		BI b = XType.cast(cl.load("com.xiuye.BIImpl").newInstance());
		b.b();
		CI c = XType.cast(cl.load("com.xiuye.util.test.code.CIImpl").newInstance());
		c.c();

	}

	public interface BI {
		void b();
	}

	public interface CI {
		void c();
	}

	@Test
	public void testGenJavaCode() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		ClassInfo info = new ClassInfo();
		info.setPackageName("com.xiuye.util.test.code");
		info.addImportPackage("com.xiuye.util.log.XLog");
		info.setAccess(ClassInfo.ACCESS_PUBLIC);
		info.addModifier(ClassInfo.FINAL_MODIFIER);
		info.setType(ClassInfo.TYPE_CLASS);
		info.setName("Demo1");

		for (int i = 0; i < 10; i++) {
			FieldInfo fi = new FieldInfo();
			fi.setAccess(FieldInfo.ACCESS_PRIVATE);
//			fi.addModifier(FieldInfo.STATIC_MODIFIER);
			fi.addModifier(FieldInfo.FINAL_MODIFIER);
			fi.setType("int");
			fi.setName("inNumber" + i);
			info.addField(fi);
		}
		for (int i = 0; i < 10; i++) {
			FunctionInfo fi = new FunctionInfo();
			fi.setAccess(FunctionInfo.ACCESS_PUBLIC);
			fi.addModifier(FunctionInfo.STATIC_MODIFIER);
			fi.addModifier(FunctionInfo.FINAL_MODIFIER);
			fi.setType("void");
			fi.setName("function" + i);
			fi.addParameter("int", "a");
			fi.addParameter("int", "b");
			fi.addParameter("long", "c");
			fi.setFunctionBody("XLog.lg(a,b,c);");
			info.addFunction(fi);
		}

		FunctionInfo fi = new FunctionInfo();
		fi.setAccess(FunctionInfo.ACCESS_PUBLIC);
//		fi.setType("");
		fi.setName("Demo1");
		String conBody = "";
		for(int i=0;i<10;i++) {
			conBody += "inNumber" + i+" = "+ i +";";
		}
		
		fi.setFunctionBody(conBody);
		info.addFunction(fi);
		
		XLog.lg(info);
		Map<String,String> codes = XType.map();
		codes.put("com.xiuye.util.test.code.Demo1",info.code());
		XYCompiler.compileCode(codes);
		XYClassLoader cl = XType.createClassLoader();
		XLog.lg(cl.load("com.xiuye.util.test.code.Demo1"));
		Class<?> clazz = cl.load("com.xiuye.util.test.code.Demo1");
		
		Object o = clazz.newInstance();
		Method m = clazz.getDeclaredMethod("function9", int.class,int.class,long.class);
		
		m.invoke(o, 1,2,3);
	}
	
	@Test
	public void testProxyInterface() throws ClassNotFoundException {
		Class<ABC> clazz = XType.cast(Class.forName("com.xiuye.util.test.code.ABC"));
		Method [] ms = clazz.getDeclaredMethods();
		for(Method m : ms) {
			XLog.lg(m);
			XLog.lg(m.getModifiers());
			XLog.lg(m.getReturnType());
			XLog.lg(m.getGenericReturnType());
			XLog.lg(m.getName());
			XLog.lg(m.getParameters());
			XLog.lg(m.getParameterTypes());
			XLog.lg(m.getGenericParameterTypes());
			XLog.lg(m.getParameterCount());
		}
		
		
	}
	@Test
	public void testHowManyThreadsOK() throws InterruptedException {
		for(int i=0;i<10000;i++) {
			int j = i;
			XCode.runAsync(()->{
				XLog.ln(UUID.randomUUID().toString(),j);
//				for(;;);
			});
			
		}
		
//		while(true);
		
		Thread.sleep(3000);
		
		
	}
}

package com.xiuye.test.cls;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.xiuye.util.cls.XType;
import com.xiuye.util.code.XYCompiler;
import com.xiuye.util.log.XLog;

public class CompilerTest {

	@Test
	public void testCompileFile() {
//		List<String> files = Arrays.asList("C:\\Users\\admin\\Desktop\\java\\Demo1.java");
//		XLog.err(XYCompiler.compileFile(files));
//		XLog.err(XYCompiler.compileFile("C:\\Users\\admin\\Desktop\\java\\Demo1.java"));
	}

	@Test
	public void testComileCode()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Map<String, String> codes = XType.map();
		codes.put("com.xiuye.A", "package com.xiuye;" + "import com.xiuye.util.log.XLog;" + "public class A{"
				+ "public A(){" + "XLog.log(\"OK,A created!\");" + "}" + "}");
		XLog.log(XYCompiler.compileCode(codes));
		XLog.log(XYCompiler.compileCode(Arrays.asList("-d",".","-verbose"),codes));
//		LogUtil.log(Paths.get(".").toRealPath());
		// 使用类加载器加载
		ClassLoader cl = XType.createClassLoader();
		Class<?> cls = cl.loadClass("com.xiuye.A");
		XLog.log(cls);
		XLog.log(cls.newInstance());
	}

}

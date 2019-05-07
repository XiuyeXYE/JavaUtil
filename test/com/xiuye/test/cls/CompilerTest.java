package com.xiuye.test.cls;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.xiuye.compiler.util.XYCompiler;
import com.xiuye.util.cls.TypeUtil;
import com.xiuye.util.log.LogUtil;

public class CompilerTest {

	@Test
	public void testCompileFile() {
		List<String> files = Arrays.asList("C:\\Users\\admin\\Desktop\\java\\Demo1.java");
		LogUtil.err(XYCompiler.compileFile(files));
		LogUtil.err(XYCompiler.compileFile("C:\\Users\\admin\\Desktop\\java\\Demo1.java"));
	}

	@Test
	public void testComileCode()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Map<String, String> codes = TypeUtil.createMap();
		codes.put("com.xiuye.A", "package com.xiuye;" + "import com.xiuye.util.log.LogUtil;" + "public class A{"
				+ "public A(){" + "LogUtil.log(\"OK,A created!\");" + "}" + "}");
		LogUtil.log(XYCompiler.compileCode(codes));
		LogUtil.log(XYCompiler.compileCode(Arrays.asList("-d",".","-verbose"),codes));
//		LogUtil.log(Paths.get(".").toRealPath());
		// 使用类加载器加载
		ClassLoader cl = TypeUtil.createClassLoader();
		Class<?> cls = cl.loadClass("com.xiuye.A");
		LogUtil.log(cls);
		LogUtil.log(cls.newInstance());
	}

}

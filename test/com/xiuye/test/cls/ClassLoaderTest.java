package com.xiuye.test.cls;

import java.net.MalformedURLException;

import org.junit.Test;

import com.xiuye.util.cls.TypeUtil;
import com.xiuye.util.log.LogUtil;

public class ClassLoaderTest {

	@Test
	public void testLoadClass() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		ClassLoader cl = TypeUtil.createClassLoader(".","E:\\code\\Utils");
		Class<?> cls = cl.loadClass("A");
		LogUtil.log(cls);
		LogUtil.log(cls.newInstance());
		cls = cl.loadClass("demo.Demo1");
		LogUtil.log(cls);
		LogUtil.log(cls.newInstance());
	}
	
	
	
}

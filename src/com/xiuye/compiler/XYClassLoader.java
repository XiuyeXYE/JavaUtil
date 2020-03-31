package com.xiuye.compiler;

import java.net.URL;
import java.net.URLClassLoader;

import com.xiuye.util.cls.TypeUtil;

public class XYClassLoader extends URLClassLoader {

	public XYClassLoader(URL[] urls) {
		super(urls);
	}

	public <T> Class<T> load(String name) throws ClassNotFoundException{
		return TypeUtil.dynamic_cast(this.loadClass(name));
	}

}

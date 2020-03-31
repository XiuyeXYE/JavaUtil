package com.xiuye.util.code;

import java.net.URL;
import java.net.URLClassLoader;

import com.xiuye.util.cls.XType;

public class XYClassLoader extends URLClassLoader {

	public XYClassLoader(URL[] urls) {
		super(urls);
	}

	public <T> Class<T> load(String name) throws ClassNotFoundException{
		return XType.cast(this.loadClass(name));
	}

}

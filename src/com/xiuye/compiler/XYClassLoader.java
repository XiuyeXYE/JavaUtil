package com.xiuye.compiler;

import java.net.URL;
import java.net.URLClassLoader;

public class XYClassLoader extends URLClassLoader {

	public XYClassLoader(URL[] urls) {
		super(urls);
	}


}

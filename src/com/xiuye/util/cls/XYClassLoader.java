package com.xiuye.util.cls;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * self defined ClassLoader for load class file
 * 
 * @author xiuye
 *
 */
public class XYClassLoader extends URLClassLoader {

	/**
	 * ClassLoader constructor
	 * 
	 * @param urls
	 */
	public XYClassLoader(URL[] urls) {
		super(urls);
	}

	/**
	 * load a class by class name
	 * 
	 * @param <T>
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 */
	public <T> Class<T> load(String name) throws ClassNotFoundException {
		return XType.cast(this.loadClass(name));
	}

}

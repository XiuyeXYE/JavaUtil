package com.xiuye.sharp;

import java.util.Map;
import java.util.Objects;

import com.xiuye.util.cls.XType;

public class SingletonBeanPoolPromise<R> {

	// name -> bean
	// 多元信息保存
	private static final Map<String, Object> beans;
	static {
		beans = XType.sync(XType.map());
	}

	// 一元信息
	private String name;
	private Class<R> clazz;
	private R result;

	public SingletonBeanPoolPromise(String name) {
		this.name = name;
	}

	public SingletonBeanPoolPromise(String name, Class<R> clazz) {
		this.name = name;
		this.clazz = clazz;
	}
	
	public SingletonBeanPoolPromise(String name, R r) {
		this.name = name;
		this.result = r;
	}

	public SingletonBeanPoolPromise<R> getBean() {
		Objects.requireNonNull(this.name);
//		Objects.requireNonNull(this.clazz);
//		return this.getBean(name, clazz);
		result = XType.cast(beans.get(this.name));
		return this;
	}


	private <I> SingletonBeanPoolPromise<R> register(String name, I in) {
		synchronized (SingletonBeanPoolPromise.class) {
			if (Objects.isNull(beans.putIfAbsent(name, in))) {
				return this;
			} else {
				throw new RuntimeException("The bean name: " + name + " already exists!");
			}
		}		
	}
	
	public SingletonBeanPoolPromise<R> register() {
		this.register(name, result);
		return this;
	}

	public Promise<R> end() {
		return Promise.of(result);
	}

}

package com.xiuye.sharp;

import java.util.Map;
import java.util.Objects;

import com.xiuye.util.cls.XType;

//singleton beans manager
public class X2<R> {

	// name -> bean
	// 多元信息保存
	private static final Map<String, Object> nameMappingBeans;
	// singleton
	// class -> bean
	private static final Map<Class<?>, Object> classMappingBeans;
	static {
		nameMappingBeans = XType.sync(XType.map());
		classMappingBeans = XType.sync(XType.map());
	}

	// 一元信息

	private R result;
	// 定义接口做决策!
	private BeanOps<R> beanOps;

	/**
	 * for get bean
	 * 
	 * @param name
	 * @param clazz
	 */
	public X2(String name, Class<R> clazz) {
		this.beanOps = new NameAndClassBeanPoolPromise(name, clazz);
	}

	/**
	 * for get bean
	 * 
	 * @param name
	 */
	public X2(String name) {
		this.beanOps = new NameBeanPoolPromise(name);
	}

	/**
	 * for get bean
	 * 
	 * @param clazz
	 */
	public X2(Class<R> clazz) {
		this.beanOps = new ClassBeanPoolPromise(clazz);
	}

	/**
	 * for register
	 * 
	 * @param name
	 * @param clazz
	 * @param r
	 */
	public X2(String name, Class<R> clazz, R r) {
		this(name, clazz, r, false);
	}

	public X2(String name, Class<R> clazz, R r, boolean replace) {
		this.beanOps = new NameAndClassBeanPoolPromise(name, clazz, r, replace);
	}

	/**
	 * for register
	 * 
	 * @param clazz
	 * @param r
	 */
	public X2(Class<R> clazz, R r) {
		this(clazz, r, false);
	}

	public X2(Class<R> clazz, R r, boolean replace) {
		this.beanOps = new ClassBeanPoolPromise(clazz, r, replace);
	}

	/**
	 * for register
	 * 
	 * @param name
	 * @param r
	 */
	public X2(String name, R r) {
		this(name, r, false);
	}

	public X2(String name, R r, boolean replace) {
		this.beanOps = new NameBeanPoolPromise(name, r, replace);
	}

	/**
	 * 定义接口 做决策 执行那一实现类 操作
	 * 
	 * @author engineer
	 *
	 * @param <T>
	 */
	private interface BeanOps<T> {
		public T getBean();

		public void register();

		/**
		 * register bean by class
		 * 
		 * @param clazz
		 * @param in
		 * @return
		 */
		default void register(Class<?> clazz, T in, boolean replace) {
			synchronized (BeanOps.class) {
				if (replace) {
					classMappingBeans.put(clazz, in);
				} else {
					if (Objects.nonNull(classMappingBeans.putIfAbsent(clazz, in))) {
						throw new RuntimeException("Bean:the class: " + clazz + " already exists!");
					}
				}
			}
		}

		/**
		 * register bean by name
		 * 
		 * @param name
		 * @param in
		 * @return
		 */
		default void register(String name, T in, boolean replace) {
			synchronized (BeanOps.class) {
				if (replace) {
					nameMappingBeans.put(name, in);
				} else {
					if (Objects.nonNull(nameMappingBeans.putIfAbsent(name, in))) {
						throw new RuntimeException("The bean name: " + name + " already exists!");
					}
				}
			}
		}
	}

	/**
	 * register bean or get bean by name
	 * 
	 * @author engineer
	 *
	 */
	class NameBeanPoolPromise implements BeanOps<R> {

		private String name;
		private boolean replace;

		public NameBeanPoolPromise(String name) {
			this.name = name;
		}

		public NameBeanPoolPromise(String name, R r, boolean replace) {
			this(name);
			this.replace = replace;
			result = r;
		}

		public NameBeanPoolPromise(String name, R r) {
			this(name, r, false);
		}

		@Override
		public R getBean() {
			return XType.cast(nameMappingBeans.get(name));
		}

		@Override
		public void register() {
			register(name, result, replace);
		}

	}

	/**
	 * register bean or get bean by class
	 * 
	 * @author engineer
	 *
	 */
	class ClassBeanPoolPromise implements BeanOps<R> {

		private Class<R> clazz;
		private boolean replace;

		public ClassBeanPoolPromise(Class<R> clazz) {
			this.clazz = clazz;
		}

		public ClassBeanPoolPromise(Class<R> clazz, R r, boolean replace) {
			this(clazz);
			this.replace = replace;
			result = r;
		}

		public ClassBeanPoolPromise(Class<R> clazz, R r) {
			this(clazz, r, false);
		}

		@Override
		public R getBean() {
			return clazz.cast(classMappingBeans.get(clazz));
		}

		@Override
		public void register() {
			register(clazz, result, replace);
		}

	}

	/**
	 * register bean or get bean by both name and class
	 * 
	 * @author engineer
	 *
	 */
	class NameAndClassBeanPoolPromise implements BeanOps<R> {

		private String name;
		private Class<R> clazz;
		private boolean replace;

		public NameAndClassBeanPoolPromise(String name, Class<R> clazz) {
			this.name = name;
			this.clazz = clazz;
		}

		public NameAndClassBeanPoolPromise(String name, Class<R> clazz, R r, boolean replace) {
			this(name, clazz);
			this.replace = replace;
			result = r;
		}

		public NameAndClassBeanPoolPromise(String name, Class<R> clazz, R r) {
			this(name, clazz, r, false);
		}

		@Override
		public R getBean() {
			// check
			R r1 = clazz.cast(nameMappingBeans.get(name));
			R r2 = clazz.cast(classMappingBeans.get(clazz));

			if (r1 == null && r1 == null) {
				return null;
			} else if (Objects.nonNull(r1) && Objects.nonNull(r2) && r1.equals(r2)) {
				return r1;
			} else {
				throw new RuntimeException("Did you register bean by name and(&&) class?!");
			}
		}

		@Override
		public void register() {
			register(name, result, replace);
			register(clazz, result, replace);
		}

	}

	public X2<R> getBean() {
		result = this.beanOps.getBean();
		return this;
	}

	public X2<R> register() {
		this.beanOps.register();
		return this;
	}

	/**
	 * return back to promise
	 * 
	 * @return
	 */
	public X<R> end() {
		return X.of(result);
	}

}

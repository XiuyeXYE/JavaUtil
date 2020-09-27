package com.xiuye.sharp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

import com.google.gson.Gson;
import com.xiuye.util.cls.XType;
import com.xiuye.util.json.JsonUtil;
import com.xiuye.util.log.XLog;

public class X<RESULT> {// sharp tools

	//当前结果
	private RESULT result;


	public X() {

	}


	public X(RESULT result) {
		this.result = result;
	}



	public RESULT get() {
		return result;
	}
	
	private static <R, T> R exec(ReturnCallbackNoParam<R> callback, X<T> x) {
		return callback.rcv();
	}

	private static <T> T exec(VoidCallbackNoParam callback, X<T> x) {
		callback.vcv();
		return x.result;
	}
	
	private static <R,I> R exec(ReturnCallbackWithParam<R, I> callback, X<I> x) {
		return callback.rci(x.result);
	}
	
	private static <T> T exec(VoidCallbackWithParam<T> callback, X<T> x) {
		callback.vci(x.result);
		return x.result;
	}

//	private static <R> R errorHandler(VoidCallbackNoParam callback, X<R> x) {
//		try {
//			callback.vcv();
//			// 捕获异常后将上一步的异常 清理掉
//			// 能到这一步，表示编译完美无误的处理异常！
//			// error 是 类中局部变量，好处是不会被 final 约束了
//			x.error = null;
//		} catch (Throwable e) {
//			x.error = e;
//		}
//		// error 的时候 ，result是不存在的！所有返回null是正确的！
//		return null;
//	}

	// 必须自己实现与其他类库无关的接口，哪怕是SDK 标准库
	// 否则 将面临 传参 的 一些莫名其妙的错误！

	/**
	 * R call (I)
	 *
	 * @param <R>
	 * @param <I>
	 */
	public /* static default */ interface ReturnCallbackWithParam<R, I> {
		R rci(I in);
	}

	/**
	 * void callback(I)
	 *
	 * @param <I>
	 */
	public /* static default */ interface VoidCallbackWithParam<I> {
		void vci(I in);
	}

	public /* static default */ interface VoidCallbackNoParam {
		void vcv();
	}

	/**
	 * R call()
	 *
	 * @param <R>
	 */
	public /* static default */ interface ReturnCallbackNoParam<R> {
		R rcv();
	}

//	public static synchronized void ignoreException(boolean ignore) {
//		ignoreException = ignore;
//	}

	// static and non-static all can use it
	private static <I> boolean parseBoolean(I t) {
		if (t == null) {
			return false;
		} else {// existence is true
			boolean b = true;
			if (t instanceof Boolean) {
				b = XType.cast(t);
			}
			return b;
		}
	}

	public static <R> X<R> resolve() {
		return new X<>();
	}

	public static <R> X<R> resolve(R r) {
		return new X<>(r);
	}
	

	public static <R> X<R> of() {
		return resolve();
	}

	public static <R> X<R> of(R t) {
		return resolve(t);
	}
	

	public <R> X<R> THEN(ReturnCallbackNoParam<R> callback) {
		return of(exec(callback, this));
	}

	public <R> X<R> THEN(ReturnCallbackWithParam<R, RESULT> callback) {
		return of(exec(callback, this));
	}

	public X<RESULT> THEN(VoidCallbackWithParam<RESULT> callback) {
		return of(exec(callback, this));
	}

	public X<RESULT> THEN(VoidCallbackNoParam callback) {
		return of(exec(callback, this));
	}

	public <R> X<R> FINALLY(ReturnCallbackNoParam<R> callback) {
		return THEN(callback);
	}

	public <R> X<R> FINALLY(ReturnCallbackWithParam<R, RESULT> callback) {
		return THEN(callback);
	}

	public X<RESULT> FINALLY(VoidCallbackWithParam<RESULT> callback) {
		return THEN(callback);
	}

	public X<RESULT> FINALLY(VoidCallbackNoParam callback) {
		return THEN(callback);
	}

	

	private boolean exist() {
		return result != null;
	}

	// exist
	public X<Boolean> E() {
		return of(exist());
	}

	public <R> X<R> E(ReturnCallbackNoParam<R> callback) {
		return exist() ? THEN(callback) : of();
	}

	public <R> X<R> E(ReturnCallbackWithParam<R, RESULT> callback) {
		return exist() ? THEN(callback) : of();
	}

	public X<RESULT> E(VoidCallbackWithParam<RESULT> callback) {
		return exist() ? THEN(callback) : of();
	}

	public X<RESULT> E(VoidCallbackNoParam callback) {
		return exist() ? THEN(callback) : of();
	}

	public X1<RESULT> begin() {
		return new X1<>(result);
	}

	public static <R> X1<R> beginS() {
		return new X1<>();
	}

	private boolean truely() {
		return parseBoolean(result);
	}

	// true
	public X<Boolean> T() {
		return of(truely());
	}

	public <R> X<R> T(ReturnCallbackNoParam<R> callback) {
		return truely() ? THEN(callback) : of();
	}

	public <R> X<R> T(ReturnCallbackWithParam<R, RESULT> callback) {
		return truely() ? THEN(callback) : of();
	}

	public X<RESULT> T(VoidCallbackWithParam<RESULT> callback) {
		return truely() ? THEN(callback) : of();
	}

	public X<RESULT> T(VoidCallbackNoParam callback) {
		return truely() ? THEN(callback) : of();
	}

	public X<Boolean> F() {
		return of(!truely());
	}

	public <R> X<R> F(ReturnCallbackNoParam<R> callback) {
		return !truely() ? THEN(callback) : of();
	}

	public <R> X<R> F(ReturnCallbackWithParam<R, RESULT> callback) {
		return !truely() ? THEN(callback) : of();
	}

	public X<RESULT> F(VoidCallbackWithParam<RESULT> callback) {
		return !truely() ? THEN(callback) : of();
	}

	public X<RESULT> F(VoidCallbackNoParam callback) {
		return !truely() ? THEN(callback) : of();
	}

	public static abstract class AbstractPromiseTask<FUNC, R, I> extends Thread {

		protected FUNC func;
		protected R result;
		protected I input;

		public AbstractPromiseTask(FUNC func) {
			this.func = func;
		}

		public AbstractPromiseTask(FUNC func, I input) {
			this(func);
			this.input = input;
		}


		public R get() {
			try {
				this.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				
			}
			return result;
		}

		public R get(long millis) throws InterruptedException {
			try {
				this.join(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return result;
		}


		public I getInput() {
			return input;
		}

		public void setInput(I input) {
			this.input = input;
		}
		
		protected <T> T exec(ReturnCallbackNoParam<T> callback) {
			return callback.rcv();
		}

		protected R exec(VoidCallbackNoParam callback) {
			callback.vcv();
			return this.result;
		}
		
		protected R exec(ReturnCallbackWithParam<R, I> callback) {
			return callback.rci(this.input);
		}
		
		protected R exec(VoidCallbackWithParam<I> callback) {
			callback.vci(this.input);
			return this.result;
		}
		

	}

	public static class PromiseTaskRCI<R, I> extends AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I> {

		public PromiseTaskRCI(ReturnCallbackWithParam<R, I> func) {
			super(func);
		}

		public PromiseTaskRCI(ReturnCallbackWithParam<R, I> func, I input) {
			super(func, input);
		}

		@Override
		public void run() {
			super.run();
			result = exec(func);
		}

	

	}

	public static class PromiseTaskRCV<R, I> extends AbstractPromiseTask<ReturnCallbackNoParam<R>, R, I> {

		public PromiseTaskRCV(ReturnCallbackNoParam<R> func) {
			super(func);
		}

		public PromiseTaskRCV(ReturnCallbackNoParam<R> func, I input) {
			super(func, input);
		}

		@Override
		public void run() {
			super.run();
			result = exec(func);
		}

	}

	public static class PromiseTaskVCI<R, I> extends AbstractPromiseTask<VoidCallbackWithParam<I>, R, I> {

		public PromiseTaskVCI(VoidCallbackWithParam<I> func) {
			super(func);
		}

		public PromiseTaskVCI(VoidCallbackWithParam<I> func, I input) {
			super(func, input);
		}

		@Override
		public void run() {
			super.run();
			result = exec(func);
		}

	}

	public static class PromiseTaskVCV<R, I> extends AbstractPromiseTask<VoidCallbackNoParam, R, I> {

		public PromiseTaskVCV(VoidCallbackNoParam func) {
			super(func);
		}

		public PromiseTaskVCV(VoidCallbackNoParam func, I input) {
			super(func, input);
		}

		@Override
		public void run() {
			super.run();
			result = exec(func);
		}

	}
	

	public static <R, I> X<AbstractPromiseTask<VoidCallbackNoParam, R, I>> taskS(VoidCallbackNoParam callback) {
		AbstractPromiseTask<VoidCallbackNoParam, R, I> taskObj = new PromiseTaskVCV<>(callback);
		taskObj.start();
		return of(taskObj);
	}

	public static <R, I> X<AbstractPromiseTask<VoidCallbackWithParam<I>, R, I>> taskS(
			VoidCallbackWithParam<I> callback) {
		AbstractPromiseTask<VoidCallbackWithParam<I>, R, I> taskObj = new PromiseTaskVCI<>(callback);
		taskObj.start();
		return of(taskObj);
	}

	public static <R, I> X<AbstractPromiseTask<VoidCallbackWithParam<I>, R, I>> taskS(VoidCallbackWithParam<I> callback,
			I input) {
		AbstractPromiseTask<VoidCallbackWithParam<I>, R, I> taskObj = new PromiseTaskVCI<>(callback, input);
		taskObj.start();
		return of(taskObj);
	}

	public static <R, I> X<AbstractPromiseTask<ReturnCallbackNoParam<R>, R, I>> taskS(
			ReturnCallbackNoParam<R> callback) {
		AbstractPromiseTask<ReturnCallbackNoParam<R>, R, I> taskObj = new PromiseTaskRCV<>(callback);
		taskObj.start();
		return of(taskObj);
	}

	public static <R, I> X<AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I>> taskS(
			ReturnCallbackWithParam<R, I> callback) {
		AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I> taskObj = new PromiseTaskRCI<>(callback);
		taskObj.start();
		return of(taskObj);
	}

	public static <R, I> X<AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I>> taskS(
			ReturnCallbackWithParam<R, I> callback, I input) {
		AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I> taskObj = new PromiseTaskRCI<>(callback, input);
		taskObj.start();
		return of(taskObj);
	}

	public <R> X<AbstractPromiseTask<VoidCallbackNoParam, R, RESULT>> task(VoidCallbackNoParam callback) {
		AbstractPromiseTask<VoidCallbackNoParam, R, RESULT> taskObj = new PromiseTaskVCV<>(callback, result);
		taskObj.start();
		return of(taskObj);
	}

	public <R> X<AbstractPromiseTask<VoidCallbackWithParam<RESULT>, R, RESULT>> task(
			VoidCallbackWithParam<RESULT> callback) {
		AbstractPromiseTask<VoidCallbackWithParam<RESULT>, R, RESULT> taskObj = new PromiseTaskVCI<>(callback, result);
		taskObj.start();
		return of(taskObj);
	}

	public <R, I> X<AbstractPromiseTask<VoidCallbackWithParam<I>, R, I>> task(VoidCallbackWithParam<I> callback,
			I input) {
		AbstractPromiseTask<VoidCallbackWithParam<I>, R, I> taskObj = new PromiseTaskVCI<>(callback, input);
		taskObj.start();
		return of(taskObj);
	}

	public <R> X<AbstractPromiseTask<ReturnCallbackNoParam<R>, R, RESULT>> task(ReturnCallbackNoParam<R> callback) {
		AbstractPromiseTask<ReturnCallbackNoParam<R>, R, RESULT> taskObj = new PromiseTaskRCV<>(callback, result);
		taskObj.start();
		return of(taskObj);
	}

	public <R> X<AbstractPromiseTask<ReturnCallbackWithParam<R, RESULT>, R, RESULT>> task(
			ReturnCallbackWithParam<R, RESULT> callback) {
		AbstractPromiseTask<ReturnCallbackWithParam<R, RESULT>, R, RESULT> taskObj = new PromiseTaskRCI<>(callback,
				result);
		taskObj.start();
		return of(taskObj);
	}

	public <R, I> X<AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I>> task(
			ReturnCallbackWithParam<R, I> callback, I input) {
		AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I> taskObj = new PromiseTaskRCI<>(callback, input);
		taskObj.start();
		return of(taskObj);
	}

	@SafeVarargs
	public static <R> X<R[]> logS(R... in) {
		XLog.lg(in);
		return of(in);
	}

	@SafeVarargs
	public static <R> X<R[]> lgS(R... in) {
		return logS(in);
	}

	@SafeVarargs
	private static <R> void printLine(R...in) {
		XLog.attach(2);
		XLog.line(in);
		XLog.dettach(2);
	}
	
	@SafeVarargs
	public static <R> X<R[]> lineS(R... in) {
		X<R[]> pro = of(in);
		printLine(in);
		return pro;
	}

	@SafeVarargs
	public static <R> X<R[]> lnS(R... in) {
		X<R[]> pro = of(in);
		printLine(in);
		return pro;
	}

	public X<RESULT> log() {
		XLog.lg(result);
		return of(result);
	}

	public X<RESULT> lg() {
		return log();
	}

	public X<RESULT> line() {
		printLine(result);
		return of(result);
	}

	public X<RESULT> ln() {
		printLine(result);
		return of(result);
	}

	public static <R> X2<R> beanS(String name) {
		return new X2<>(name);
	}

	public static <R> X2<R> beanS(String name, R object) {
		return beanS(name, object, false);
	}

	public static <R> X2<R> beanS(String name, R object, boolean replace) {
		return new X2<>(name, object, replace);
	}

	public static <R> X2<R> beanS(String name, Class<R> clazz) {
		return new X2<>(name, clazz);
	}

	public static <R> X2<R> beanS(String name, Class<R> clazz, R r) {
		return beanS(name, clazz, r, false);
	}

	public static <R> X2<R> beanS(String name, Class<R> clazz, R r, boolean replace) {
		return new X2<>(name, clazz, r, replace);
	}

	public static <R> X2<R> beanS(Class<R> clazz, String name, R r) {
		return beanS(clazz, name, r, false);
	}

	public static <R> X2<R> beanS(Class<R> clazz, String name, R r, boolean replace) {
		return new X2<>(name, clazz, r, replace);
	}

	public static <R> X2<R> beanS(Class<R> clazz) {
		return new X2<>(clazz);
	}

	public static <R> X2<R> beanS(Class<R> clazz, R r) {
		return beanS(clazz, r, false);
	}

	public static <R> X2<R> beanS(Class<R> clazz, R r, boolean replace) {
		return new X2<>(clazz, r, replace);
	}

	public <R> X2<R> bean(String name) {
		return beanS(name);
	}

	public <R> X2<R> bean(String name, R object) {
		return beanS(name, object);
	}

	public <R> X2<R> bean(String name, R object, boolean replace) {
		return beanS(name, object, replace);
	}

	public <R> X2<R> bean(String name, Class<R> clazz) {
		return beanS(name, clazz);
	}

	public <R> X2<R> bean(String name, Class<R> clazz, R r) {
		return beanS(name, clazz, r);
	}

	public <R> X2<R> bean(String name, Class<R> clazz, R r, boolean replace) {
		return beanS(name, clazz, r, replace);
	}

	public <R> X2<R> bean(Class<R> clazz) {
		return beanS(clazz);
	}

	public <R> X2<R> bean(Class<R> clazz, R r) {
		return beanS(clazz, r);
	}

	public <R> X2<R> bean(Class<R> clazz, R r, boolean replace) {
		return beanS(clazz, r, replace);
	}

	public X2<RESULT> bean() {
		return beanS("X", result, true);
	}

	public void set(RESULT r) {
		this.result = r;
	}

	public static X<ServerSocket> tcpS(int port) {

		X<ServerSocket> x = of();
		try {
			x.set(new ServerSocket(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return x;
	}

	

	public X<ServerSocket> tcp(int port) {
		return tcpS(port);
	}

	public static X<Socket> tcpS(String ip, int port) {
		X<Socket> x = of();
		try {
//			return of(new Socket(ip, port));
			x.set(new Socket(ip, port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return x;
	}

	

	public X<Socket> tcp(String ip, int port) {
		return tcp(ip, port);
	}

	public static X<DatagramSocket> udpS(int port) {
		X<DatagramSocket> x = of();
		try {
//			return of(new DatagramSocket(port));
			x.set(new DatagramSocket(port));
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return x;
	}

	

	public X<DatagramSocket> udp(int port) {
		return udpS(port);
	}

	public static X<DatagramSocket> udpS() {
		X<DatagramSocket> x = of();
		try {
			x.set(new DatagramSocket());
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return x;
	}

	

	public X<DatagramSocket> udp() {
		return udpS();
	}

	public X<String> toFormatJson() {
		return of(exec(() -> JsonUtil.instance(JsonUtil.FORMAT_GSON).toJson(result), this));
	}

	public X<String> toJson() {
		return of(exec(() -> JsonUtil.instance().toJson(result), this));
	}

	public <R> X<R> toObject(Class<R> clazz) {
		return of(exec(() -> JsonUtil.instance().fromJson(Objects.nonNull(result) ? result.toString() : null, clazz), this));
	}

	public static X<Gson> formatterJsonKitS() {

		X<Gson> gsonX = of();
		gsonX.set(exec(() -> JsonUtil.instance(JsonUtil.FORMAT_GSON), gsonX));

		return gsonX;
	}

	

	public static X<Gson> jsonKitS() {
		X<Gson> gsonX = of();
		gsonX.set(exec(() -> JsonUtil.instance(), gsonX));
		return gsonX;
	}

	

	public X<Gson> formatterJsonKit() {
		return of(exec(() -> JsonUtil.instance(JsonUtil.FORMAT_GSON), this));
	}

	public X<Gson> jsonKit() {
		return of(exec(() -> JsonUtil.instance(), this));
	}

	public static <R> X<R> x() {
		return resolve();
	}

	public static <R> X<R> x(R t) {
		return resolve(t);
	}

	

}

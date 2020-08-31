package com.xiuye.sharp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.google.gson.Gson;
import com.xiuye.util.cls.XType;
import com.xiuye.util.json.JsonUtil;
import com.xiuye.util.log.XLog;

public class X<RESULT> {// sharp tools

	// 一般是这步的计算结果，传递给下一步
	private RESULT result;
	// 这步产生的错误
	private Throwable error;

	// 异常必须处理后才能进行下一步
	private static boolean ignoreException = true;

	public X() {

	}

	public X(RESULT result) {
		this.result = result;
	}

	public X(RESULT result, Throwable error) {
		this.result = result;
		this.error = error;
	}

	public X(Throwable error) {
		this.error = error;
	}

	public Throwable getError() {
		return error;
	}

	public RESULT get() {
		return result;
	}

	private static <R> void ifError(VoidCallbackWithParam<Throwable> callback, X<R> x) {
		if (!ignoreException && x.error != null) {
			callback.vci(x.error);
		}
	}

	private static <R> void ifError(X<R> x) {
		ifError((e) -> {
			throw new RuntimeException("Some errors occur!" + "Please use \"except\" to handle error next step!", e);
		}, x);
	}
	
	//有异常都捕获
	private static <R, T> R catchExec(ReturnCallbackNoParam<R> callback, X<T> x) {
		ifError(x);
		try {
			return callback.rcv();
		} catch (Throwable e) {
			x.error = e;
		}
		return null;
	}

	private static <T> T catchExec(VoidCallbackNoParam callback, X<T> x) {
		ifError(x);
		try {
			callback.vcv();
		} catch (Throwable e) {
			x.error = e;
		}
		// error 的时候 ，result是不存在的！所有返回null是正确的！
		return x.result;
	}

	private static <R> R errorHandler(VoidCallbackNoParam callback, X<R> x) {
		try {
			callback.vcv();
			// 捕获异常后将上一步的异常 清理掉
			// 能到这一步，表示编译完美无误的处理异常！
			// error 是 类中局部变量，好处是不会被 final 约束了
			x.error = null;
		} catch (Throwable e) {
			x.error = e;
		}
		// error 的时候 ，result是不存在的！所有返回null是正确的！
		return null;
	}

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

	public static void ignoreException(boolean ignore) {
		ignoreException = ignore;
	}

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

	private static <R, E extends Throwable> X<R> resolve(R r, E error) {
		return new X<>(r, error);
	}

	public static <R, E extends Throwable> X<R> reject(E e) {
		return new X<>(e);
	}

	public static <R> X<R> of() {
		return resolve();
	}

	public static <R> X<R> of(R t) {
		return resolve(t);
	}

	public static <R, E extends Throwable> X<R> of(R r, E error) {
		return resolve(r, error);
	}

	public <R> X<R> THEN(ReturnCallbackNoParam<R> callback) {
		return of(catchExec(() -> callback.rcv(), this), this.error);
	}

	public <R> X<R> THEN(ReturnCallbackWithParam<R, RESULT> callback) {
		return of(catchExec(() -> callback.rci(result), this), error);
	}

	public X<RESULT> THEN(VoidCallbackWithParam<RESULT> callback) {
		return of(catchExec(() -> callback.vci(result), this), error);
	}

	public X<RESULT> THEN(VoidCallbackNoParam callback) {
		return of(catchExec(() -> callback.vcv(), this), error);
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

	// 传入都是空！所以随便返回什么类型

	private boolean errorExist() {
		return error != null;
	}

	public X<RESULT> EX(ReturnCallbackNoParam<RESULT> callback) {
		return resolve(errorExist() ? errorHandler(() -> callback.rcv(), this) : result, error);
	}

	public X<RESULT> EX(ReturnCallbackWithParam<RESULT, Throwable> callback) {
		return resolve(errorExist() ? errorHandler(() -> callback.rci(error), this) : result, error);
	}

	public X<RESULT> EX(VoidCallbackWithParam<Throwable> callback) {
		return resolve(errorExist() ? errorHandler(() -> callback.vci(error), this) : result, error);
	}

	public X<RESULT> EX(VoidCallbackNoParam callback) {
		return resolve(errorExist() ? errorHandler(() -> callback.vcv(), this) : result, error);
	}

	private boolean exist() {
		return result != null;
	}

	// exist
	public X<Boolean> E() {
		return of(exist(), error);
	}

	public <R> X<R> E(ReturnCallbackNoParam<R> callback) {
		return exist() ? THEN(callback) : reject(error);
	}

	public <R> X<R> E(ReturnCallbackWithParam<R, RESULT> callback) {
		return exist() ? THEN(callback) : reject(error);
	}

	public X<RESULT> E(VoidCallbackWithParam<RESULT> callback) {
		return exist() ? THEN(callback) : reject(error);
	}

	public X<RESULT> E(VoidCallbackNoParam callback) {
		return exist() ? THEN(callback) : reject(error);
	}

	public X1<RESULT> begin() {
		return new X1<>(result, error);
	}

	public static <R> X1<R> beginS() {
		return new X1<>();
	}

	private boolean truely() {
		return catchExec(() -> parseBoolean(result), this);
	}

	// true
	public X<Boolean> T() {
		return of(truely(), error);
	}

	public <R> X<R> T(ReturnCallbackNoParam<R> callback) {
		return truely() ? THEN(callback) : reject(error);
	}

	public <R> X<R> T(ReturnCallbackWithParam<R, RESULT> callback) {
		return truely() ? THEN(callback) : reject(error);
	}

	public X<RESULT> T(VoidCallbackWithParam<RESULT> callback) {
		return truely() ? THEN(callback) : reject(error);
	}

	public X<RESULT> T(VoidCallbackNoParam callback) {
		return truely() ? THEN(callback) : reject(error);
	}

	public X<Boolean> F() {
		return of(!truely(), error);
	}

	public <R> X<R> F(ReturnCallbackNoParam<R> callback) {
		return !truely() ? THEN(callback) : reject(error);
	}

	public <R> X<R> F(ReturnCallbackWithParam<R, RESULT> callback) {
		return !truely() ? THEN(callback) : reject(error);
	}

	public X<RESULT> F(VoidCallbackWithParam<RESULT> callback) {
		return !truely() ? THEN(callback) : reject(error);
	}

	public X<RESULT> F(VoidCallbackNoParam callback) {
		return !truely() ? THEN(callback) : reject(error);
	}

	public static abstract class AbstractPromiseTask<FUNC, R, I> extends Thread {

		protected FUNC func;
		protected R result;
		protected I input;
		protected Throwable error;

		public AbstractPromiseTask(FUNC func) {
			this.func = func;
		}

		public AbstractPromiseTask(FUNC func, I input) {
			this(func);
			this.input = input;
		}

		protected <T> T catchExec(ReturnCallbackNoParam<T> callback) {
			try {
				return callback.rcv();
			} catch (Throwable e) {
				error = e;
			}
			return null;
		}

		protected <T> T catchExec(VoidCallbackNoParam callback) {
			try {
				callback.vcv();
			} catch (Throwable e) {
				error = e;
			}
			return null;
		}

		public R get() {
			try {
				this.join();
			} catch (InterruptedException e) {
				error = e;
			}
			return result;
		}

		public R get(long millis) throws InterruptedException {
			try {
				this.join(millis);
			} catch (InterruptedException e) {
				error = e;
			}
			return result;
		}

//		public void set(R result) {
//			this.result = result;
//		}

		public Throwable getError() {
			return error;
		}

		public I getInput() {
			return input;
		}

		public void setInput(I input) {
			this.input = input;
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
			result = catchExec(() -> func.rci(input));
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
			result = catchExec(() -> func.rcv());
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
			result = catchExec(() -> func.vci(input));
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
			result = catchExec(() -> func.vcv());
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
		return of(taskObj, error);
	}

	public <R> X<AbstractPromiseTask<VoidCallbackWithParam<RESULT>, R, RESULT>> task(
			VoidCallbackWithParam<RESULT> callback) {
		AbstractPromiseTask<VoidCallbackWithParam<RESULT>, R, RESULT> taskObj = new PromiseTaskVCI<>(callback, result);
		taskObj.start();
		return of(taskObj, error);
	}

	public <R, I> X<AbstractPromiseTask<VoidCallbackWithParam<I>, R, I>> task(VoidCallbackWithParam<I> callback,
			I input) {
		AbstractPromiseTask<VoidCallbackWithParam<I>, R, I> taskObj = new PromiseTaskVCI<>(callback, input);
		taskObj.start();
		return of(taskObj, error);
	}

	public <R> X<AbstractPromiseTask<ReturnCallbackNoParam<R>, R, RESULT>> task(ReturnCallbackNoParam<R> callback) {
		AbstractPromiseTask<ReturnCallbackNoParam<R>, R, RESULT> taskObj = new PromiseTaskRCV<>(callback, result);
		taskObj.start();
		return of(taskObj, error);
	}

	public <R> X<AbstractPromiseTask<ReturnCallbackWithParam<R, RESULT>, R, RESULT>> task(
			ReturnCallbackWithParam<R, RESULT> callback) {
		AbstractPromiseTask<ReturnCallbackWithParam<R, RESULT>, R, RESULT> taskObj = new PromiseTaskRCI<>(callback,
				result);
		taskObj.start();
		return of(taskObj, error);
	}

	public <R, I> X<AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I>> task(
			ReturnCallbackWithParam<R, I> callback, I input) {
		AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I> taskObj = new PromiseTaskRCI<>(callback, input);
		taskObj.start();
		return of(taskObj, error);
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
	public static <R> X<R[]> lineS(R... in) {
		X<R[]> pro = of(in);
		catchExec(() -> {
			XLog.attach(3);
			XLog.line(in);
			XLog.dettach(3);
		}, pro);

		return pro;
	}

	@SafeVarargs
	public static <R> X<R[]> lnS(R... in) {
		X<R[]> pro = of(in);
		catchExec(() -> {
			XLog.attach(3);
			XLog.line(in);
			XLog.dettach(3);
		}, pro);

		return pro;
	}

	public X<RESULT> log() {
		XLog.lg(result);
		return of(result, error);
	}

	public X<RESULT> lg() {
		return log();
	}

	public X<RESULT> line() {
		catchExec(() -> {
			XLog.attach(3);
			XLog.line(result);
			XLog.dettach(3);
		}, this);
		return of(result, error);
	}

	public X<RESULT> ln() {
		catchExec(() -> {
			XLog.attach(3);
			XLog.ln(result);
			XLog.dettach(3);
		}, this);
		return of(result, error);
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
	
	public static <R> X2<R> beanS( Class<R> clazz,String name,R r) {
		return beanS(clazz,name,r,false);
	}
	
	public static <R> X2<R> beanS( Class<R> clazz,String name,R r, boolean replace) {
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

	public X2<RESULT> bean(){		
		return beanS("X",result,true);
	}
	
	public void set(RESULT r) {
		this.result = r;
	}

	public static X<ServerSocket> tcpS(int port) {

		X<ServerSocket> x = of();
		try {
			x.set(new ServerSocket(port));
		} catch (IOException e) {
			x.error = e;
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
//			e.printStackTrace();
			x.error = e;
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
			x.error = e;
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
//			e.printStackTrace();
			x.error = e;
		}
		return x;
	}

	public X<DatagramSocket> udp() {
		return udpS();
	}

	
	public X<String> toFormatJson() {
		return of(catchExec(() -> JsonUtil.instance(JsonUtil.FORMAT_GSON).toJson(result),this), error);
	}

	public X<String> toJson() {
		return of(catchExec(() -> JsonUtil.instance().toJson(result),this));
	}

	public <R> X<R> toObject(Class<R> clazz) {
		return of(catchExec(() -> JsonUtil.instance().fromJson(result != null ? result.toString() : null, clazz),this),
				error);
	}

	public static X<Gson> formatterJsonKitS() {
		
		X<Gson> gsonX = of();
		gsonX.set(
				catchExec(()->JsonUtil.instance(JsonUtil.FORMAT_GSON),
						gsonX));
		
		return gsonX;
	}

	public static X<Gson> jsonKitS() {
		X<Gson> gsonX = of();
		gsonX.set(
				catchExec(()->JsonUtil.instance(),
						gsonX));
		return gsonX;
	}

	public X<Gson> formatterJsonKit() {
		return of(catchExec(() -> JsonUtil.instance(JsonUtil.FORMAT_GSON),this), error);
	}

	public X<Gson> jsonKit() {
		return of(catchExec(() -> JsonUtil.instance(),this), error);
	}

	
	public static <R> X<R> x() {
		return resolve();
	}

	public static <R> X<R> x(R t) {
		return resolve(t);
	}

	public static <R, E extends Throwable> X<R> x(R r, E error) {
		return resolve(r, error);
	}
	
	
	
	
}

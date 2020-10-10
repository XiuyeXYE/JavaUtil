package com.xiuye.sharp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.google.gson.Gson;
import com.xiuye.util.cls.XType;
import com.xiuye.util.json.JsonUtil;
import com.xiuye.util.log.XLog;

public class X<RESULT> {// sharp tools

	// 当前结果
	private RESULT result;

	public X() {

	}

	public X(RESULT result) {
		this.result = result;
	}

	public RESULT get() {
		return result;
	}
	
	public void set(RESULT r) {
		this.result = r;
	}

//	private static <R, T> R exec(ReturnCallbackNoParam<R> callback, X<T> x) {
//		return callback.rcv();
//	}

	private static <T> T exec(VoidCallbackNoParam callback, X<T> x) {
		callback.vcv();
		return x.result;
	}

	private static <R, I> R exec(ReturnCallbackWithParam<R, I> callback, X<I> x) {
		return callback.rci(x.result);
	}

//	private static <T> T exec(VoidCallbackWithParam<T> callback, X<T> x) {
//		callback.vci(x.result);
//		return x.result;
//	}

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

//	public <R> X<R> THEN(ReturnCallbackNoParam<R> callback) {
//		return of(exec(callback, this));
//	}

	public <R> X<R> THEN(ReturnCallbackWithParam<R, RESULT> callback) {
		return of(exec(callback, this));
	}

//	public X<RESULT> THEN(VoidCallbackWithParam<RESULT> callback) {
//		return of(exec(callback, this));
//	}

	public X<RESULT> THEN(VoidCallbackNoParam callback) {
		return of(exec(callback, this));
	}


	private boolean exist() {
		return result != null;
	}

	// exist
	public X<Boolean> E() {
		return of(exist());
	}


	public <R> X<R> E(ReturnCallbackWithParam<R, RESULT> callback) {
		return exist() ? THEN(callback) : of();
	}


	public X<RESULT> E(VoidCallbackNoParam callback) {
		return exist() ? THEN(callback) : of();
	}

	public static <R> X1<R> begin() {
		return new X1<>();
	}

	private boolean truely() {
		return parseBoolean(result);
	}

	// true
	public X<Boolean> T() {
		return of(truely());
	}

	public <R> X<R> T(ReturnCallbackWithParam<R, RESULT> callback) {
		return truely() ? THEN(callback) : of();
	}


	public X<RESULT> T(VoidCallbackNoParam callback) {
		return truely() ? THEN(callback) : of();
	}

	public X<Boolean> F() {
		return of(!truely());
	}


	public <R> X<R> F(ReturnCallbackWithParam<R, RESULT> callback) {
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

//		protected <T> T exec(ReturnCallbackNoParam<T> callback) {
//			return callback.rcv();
//		}

		protected R exec(VoidCallbackNoParam callback) {
			callback.vcv();
			return this.result;
		}

		protected R exec(ReturnCallbackWithParam<R, I> callback) {
			return callback.rci(this.input);
		}

//		protected R exec(VoidCallbackWithParam<I> callback) {
//			callback.vci(this.input);
//			return this.result;
//		}

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

//	public static class PromiseTaskRCV<R, I> extends AbstractPromiseTask<ReturnCallbackNoParam<R>, R, I> {
//
//		public PromiseTaskRCV(ReturnCallbackNoParam<R> func) {
//			super(func);
//		}
//
//		public PromiseTaskRCV(ReturnCallbackNoParam<R> func, I input) {
//			super(func, input);
//		}
//
//		@Override
//		public void run() {
//			super.run();
//			result = exec(func);
//		}
//
//	}

//	public static class PromiseTaskVCI<R, I> extends AbstractPromiseTask<VoidCallbackWithParam<I>, R, I> {
//
//		public PromiseTaskVCI(VoidCallbackWithParam<I> func) {
//			super(func);
//		}
//
//		public PromiseTaskVCI(VoidCallbackWithParam<I> func, I input) {
//			super(func, input);
//		}
//
//		@Override
//		public void run() {
//			super.run();
//			result = exec(func);
//		}
//
//	}

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

	public static <R, I> X<AbstractPromiseTask<VoidCallbackNoParam, R, I>> task(VoidCallbackNoParam callback) {
		AbstractPromiseTask<VoidCallbackNoParam, R, I> taskObj = new PromiseTaskVCV<>(callback);
		taskObj.start();
		return of(taskObj);
	}

//	public static <R, I> X<AbstractPromiseTask<VoidCallbackWithParam<I>, R, I>> taskS(
//			VoidCallbackWithParam<I> callback) {
//		AbstractPromiseTask<VoidCallbackWithParam<I>, R, I> taskObj = new PromiseTaskVCI<>(callback);
//		taskObj.start();
//		return of(taskObj);
//	}

//	public static <R, I> X<AbstractPromiseTask<VoidCallbackWithParam<I>, R, I>> taskS(VoidCallbackWithParam<I> callback,
//			I input) {
//		AbstractPromiseTask<VoidCallbackWithParam<I>, R, I> taskObj = new PromiseTaskVCI<>(callback, input);
//		taskObj.start();
//		return of(taskObj);
//	}

//	public static <R, I> X<AbstractPromiseTask<ReturnCallbackNoParam<R>, R, I>> taskS(
//			ReturnCallbackNoParam<R> callback) {
//		AbstractPromiseTask<ReturnCallbackNoParam<R>, R, I> taskObj = new PromiseTaskRCV<>(callback);
//		taskObj.start();
//		return of(taskObj);
//	}

//	public static <R, I> X<AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I>> task(
//			ReturnCallbackWithParam<R, I> callback) {
//		AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I> taskObj = new PromiseTaskRCI<>(callback);
//		taskObj.start();
//		return of(taskObj);
//	}

	public static <R, I> X<AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I>> task(
			ReturnCallbackWithParam<R, I> callback, I input) {
		AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I> taskObj = new PromiseTaskRCI<>(callback, input);
		taskObj.start();
		return of(taskObj);
	}

//	public <R> X<AbstractPromiseTask<VoidCallbackNoParam, R, RESULT>> task(VoidCallbackNoParam callback) {
//		AbstractPromiseTask<VoidCallbackNoParam, R, RESULT> taskObj = new PromiseTaskVCV<>(callback, result);
//		taskObj.start();
//		return of(taskObj);
//	}

//	public <R> X<AbstractPromiseTask<VoidCallbackWithParam<RESULT>, R, RESULT>> task(
//			VoidCallbackWithParam<RESULT> callback) {
//		AbstractPromiseTask<VoidCallbackWithParam<RESULT>, R, RESULT> taskObj = new PromiseTaskVCI<>(callback, result);
//		taskObj.start();
//		return of(taskObj);
//	}

//	public <R, I> X<AbstractPromiseTask<VoidCallbackWithParam<I>, R, I>> task(VoidCallbackWithParam<I> callback,
//			I input) {
//		AbstractPromiseTask<VoidCallbackWithParam<I>, R, I> taskObj = new PromiseTaskVCI<>(callback, input);
//		taskObj.start();
//		return of(taskObj);
//	}

//	public <R> X<AbstractPromiseTask<ReturnCallbackNoParam<R>, R, RESULT>> task(ReturnCallbackNoParam<R> callback) {
//		AbstractPromiseTask<ReturnCallbackNoParam<R>, R, RESULT> taskObj = new PromiseTaskRCV<>(callback, result);
//		taskObj.start();
//		return of(taskObj);
//	}

	public <R> X<AbstractPromiseTask<ReturnCallbackWithParam<R, RESULT>, R, RESULT>> task(
			ReturnCallbackWithParam<R, RESULT> callback) {
		AbstractPromiseTask<ReturnCallbackWithParam<R, RESULT>, R, RESULT> taskObj = new PromiseTaskRCI<>(callback,
				result);
		taskObj.start();
		return of(taskObj);
	}
//
//	public <R, I> X<AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I>> task(
//			ReturnCallbackWithParam<R, I> callback, I input) {
//		AbstractPromiseTask<ReturnCallbackWithParam<R, I>, R, I> taskObj = new PromiseTaskRCI<>(callback, input);
//		taskObj.start();
//		return of(taskObj);
//	}

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
	private static <R> void printLine(R... in) {
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

	public static <R> X2<R> bean(String name) {
		return new X2<>(name);
	}

	public static <R> X2<R> bean(String name, R object) {
		return bean(name, object, false);
	}

	public static <R> X2<R> bean(String name, R object, boolean replace) {
		return new X2<>(name, object, replace);
	}

	public static <R> X2<R> bean(String name, Class<R> clazz) {
		return new X2<>(name, clazz);
	}

	public static <R> X2<R> bean(String name, Class<R> clazz, R r) {
		return bean(name, clazz, r, false);
	}

	public static <R> X2<R> bean(String name, Class<R> clazz, R r, boolean replace) {
		return new X2<>(name, clazz, r, replace);
	}

	public static <R> X2<R> bean(Class<R> clazz, String name, R r) {
		return bean(clazz, name, r, false);
	}

	public static <R> X2<R> bean(Class<R> clazz, String name, R r, boolean replace) {
		return new X2<>(name, clazz, r, replace);
	}

	public static <R> X2<R> bean(Class<R> clazz) {
		return new X2<>(clazz);
	}

	public static <R> X2<R> bean(Class<R> clazz, R r) {
		return bean(clazz, r, false);
	}

	public static <R> X2<R> bean(Class<R> clazz, R r, boolean replace) {
		return new X2<>(clazz, r, replace);
	}
	

	

	public static X<ServerSocket> tcp(int port) {

		X<ServerSocket> x = of();
		try {
			x.set(new ServerSocket(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return x;
	}

//	public X<ServerSocket> tcp(int port) {
//		return tcpS(port);
//	}

	public static X<Socket> tcp(String ip, int port) {
		X<Socket> x = of();
		try {
//			return of(new Socket(ip, port));
			x.set(new Socket(ip, port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return x;
	}

//	public X<Socket> tcp(String ip, int port) {
//		return tcp(ip, port);
//	}

	public static X<DatagramSocket> udp(int port) {
		X<DatagramSocket> x = of();
		try {
//			return of(new DatagramSocket(port));
			x.set(new DatagramSocket(port));
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return x;
	}

//	public X<DatagramSocket> udp(int port) {
//		return udpS(port);
//	}

	public static X<DatagramSocket> udp() {
		X<DatagramSocket> x = of();
		try {
			x.set(new DatagramSocket());
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return x;
	}

//	public X<DatagramSocket> udp() {
//		return udpS();
//	}

	public X<String> toFormatJson() {
		return of(JsonUtil.instance(JsonUtil.FORMAT_GSON).toJson(result));
	}

	public X<String> toJson() {
		return of(JsonUtil.instance().toJson(result));
	}

	//
	public <R> X<R> toObject(Class<R> clazz) {
		return of(JsonUtil.instance().fromJson((String) this.result, clazz));
	}
	

	public static <T> X<T[]> toArray(List<T> list, T[] arr) {
		return of(list.toArray(arr));
	}

	
	@SuppressWarnings("unchecked")
	public <T> X<T[]> toArray(T[] arr){
		if(result instanceof List) {
			return toArray((List<T>)result,arr);
		}else if(result instanceof Set) {
			int i=0;
			for(T t:(Set<T>) result) {
				arr[i++] = t;
			}
			i = 0;
		}
		return of();
	}
	

	public static <T> X<List<T>> toList(T[] arr) {
		List<T> list = XType.list();
		for (T a : arr) {
			list.add(a);
		}
		return of(list);
	}

//	public <T> X<List<T>> toList(T[] arr) {
//		return toListS(arr);
//	}
	
	@SuppressWarnings("unchecked")
	public <T> X<List<T>> toList(){
		if(result instanceof To) {
			return of(((To<List<T>>)result).get());
		}
		
		return of();
	}
	
	
	public <T> X<List<T>> toList(ParamTo<List<T>,RESULT> to){
		return of(to.get(result));
	}
	

	public static <T> X<Set<T>> toSet(T[] arr) {
		Set<T> set = XType.set();
		for (T a : arr) {
			set.add(a);
		}
		return of(set);
	}

//	public <T> X<Set<T>> toSet(T[] arr) {
//
//		return toSetS(arr);
//	}

	@SuppressWarnings("unchecked")
	public <T> X<Set<T>> toSet(){
		if(result instanceof To) {
			return of(((To<Set<T>>)result).get());
		}
		
		return of();
	}
	
	
	public <T> X<Set<T>> toSet(ParamTo<Set<T>,RESULT> to){
		return of(to.get(result));
	}
	
	
	public static X<Gson> formatterJsonKitS() {

		X<Gson> gsonX = of();
		gsonX.set(JsonUtil.instance(JsonUtil.FORMAT_GSON));
		return gsonX;
	}

	public static X<Gson> jsonKitS() {
		X<Gson> gsonX = of();
		gsonX.set(JsonUtil.instance());
		return gsonX;
	}

//	public X<Gson> formatterJsonKit() {
//		return of(exec((d) -> JsonUtil.instance(JsonUtil.FORMAT_GSON), this));
//	}
//
//	public X<Gson> jsonKit() {
//		return of(exec((d) -> JsonUtil.instance(), this));
//	}

	public static <R> X<R> x() {
		return resolve();
	}

	public static <R> X<R> x(R t) {
		return resolve(t);
	}

	public static X<String> toStringS(byte[] data) {
		return of(new String(data));
	}

	public static X<String> toStringS(byte[] data, String charset) {
		try {
			return of(new String(data, charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return of();
	}

	public X<String> toString(byte[] data) {
		return toStringS(data);
	}

	public X<String> toString(byte[] data, String charset) {
		return toStringS(data, charset);
	}

	public static X<byte[]> toByteS(String s) {
		return of(s.getBytes());
	}

	public static X<byte[]> toByteS(String s, String charset) {
		try {
			return of(s.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return of();
	}

	public X<byte[]> toByte(String s) {
		return toByteS(s);
	}

	public X<byte[]> toByte(String s, String charset) {
		return toByteS(s, charset);
	}

	public static X<byte[]> toByteS(int n) {
		byte[] data = XType.newInstance(byte[]::new, 4);
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) ((n >>> (8 * i)) & 0xff);
		}
		return of(data);
	}

	public static X<byte[]> toByteS(long n) {
		byte[] data = XType.newInstance(byte[]::new, 8);
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) ((n >>> (8 * i)) & 0xff);
		}
		return of(data);
	}

	public static X<byte[]> toByteS(float n) {
		return toByteS(toIntS(n).get());
	}

	public static X<byte[]> toByteS(double n) {
		return toByteS(toLongS(n).get());
	}

	public X<byte[]> toByte(int s) {
		return toByteS(s);
	}

	public X<byte[]> toByte(long s) {
		return toByteS(s);
	}

	public X<byte[]> toByte(float s) {
		return toByteS(s);
	}

	public X<byte[]> toByte(double s) {
		return toByteS(s);
	}

	@SuppressWarnings("unchecked")
	public X<byte[]> toByte() {
		if (result instanceof String) {
			return toByteS((String) result);
		} else if (result instanceof Double) {
			return toByteS((Double) result);
		} else if (result instanceof Float) {
			return toByteS((Float) result);
		} else if (result instanceof Integer) {
			return toByteS((Integer) result);
		} else if (result instanceof Long) {
			return toByteS((Long) result);
		} else if(result instanceof To) {
			return of(((To<byte[]>)result).get());
		}
		return of();
	}

	public static X<Integer> toIntS(byte[] data) {
		int d = 0;
		for (int i = 0; i < data.length; i++) {
			int t = data[i] & 0xff;
			d |= (t << (8 * i));
		}
		return of(d);
	}

	public static X<Long> toLongS(byte[] data) {
		long d = 0;
		for (int i = 0; i < data.length; i++) {
			long t = data[i] & 0xff;
			d |= (t << (8 * i));
		}
		return of(d);
	}

	public static X<Long> toLongS(double d) {
		return of(Double.doubleToLongBits(d));
	}

	public static X<Integer> toIntS(float d) {
		return of(Float.floatToIntBits(d));
	}

	public X<Integer> toInt(byte[] data) {
		return toIntS(data);
	}

	public X<Long> toLong(byte[] data) {
		return toLongS(data);
	}

	public static X<Double> toDoubleS(byte[] data) {
		return toDoubleS(toLongS(data).get());
	}

	public static X<Double> toDoubleS(long d) {
		return of(Double.longBitsToDouble(d));
	}

	public static X<Float> toFloatS(byte[] data) {
		return toFloatS(toIntS(data).get());
	}

	public static X<Float> toFloatS(int d) {
		return of(Float.intBitsToFloat(d));
	}

	public X<Double> toDouble(byte[] data) {
		return toDoubleS(data);
	}

	public X<Double> toDouble(long d) {
		return of(Double.longBitsToDouble(d));
	}

	@SuppressWarnings("unchecked")
	public X<Double> toDouble() {

		if (result instanceof byte[]) {
			return toDoubleS((byte[]) result);
		} else if (result instanceof Long) {
			return toDoubleS((Long) result);
		} else if (result instanceof Double) {
			return of((Double) result);
		} else if(result instanceof To) {
			return of(((To<Double>)result).get());
		}

		return of();

	}

	@SuppressWarnings("unchecked")
	public X<Float> toFloat() {

		if (result instanceof byte[]) {
			return toFloatS((byte[]) result);
		} else if (result instanceof Integer) {
			return toFloatS((Integer) result);
		} else if (result instanceof Float) {
			return of((Float) result);
		}else if(result instanceof To) {
			return of(((To<Float>)result).get());
		}

		return of();

	}

	public X<Float> toFloat(byte[] data) {
		return toFloatS(data);
	}

	public X<Float> toFloat(int d) {
		return toFloatS(d);
	}

	@SuppressWarnings("unchecked")
	public X<Integer> toInt() {
		if (result instanceof byte[]) {
			return toIntS((byte[]) result);
		} else if (result instanceof Float) {
			return toIntS((Float) result);
		} else if (result instanceof Integer) {
			return of((Integer) result);
		}else if(result instanceof To) {
			return of(((To<Integer>)result).get());
		}
		return of();
	}

	@SuppressWarnings("unchecked")
	public X<Long> toLong() {
		if (result instanceof byte[]) {
			return toLongS((byte[]) result);
		} else if (result instanceof Double) {
			return toLongS((Double) result);
		} else if (result instanceof Long) {
			return of((Long) result);
		} else if (result instanceof To) {
			return of(((To<Long>) result).get());
		}
		return of();
	}

	@SuppressWarnings("unchecked")
	public X<String> toStr() {
		
		if(result instanceof byte[]) {
			return toStringS((byte[])result);
		}else if(result instanceof To) {
			return of(((To<String>)result).get());
		}
		
		return of(Objects.isNull(result)?null:result.toString());
	}
	
	public X<String> toStr(String charSet) {
		
		if(result instanceof byte[]) {
			return toStringS((byte[])result,charSet);
		}
		
		return of(Objects.isNull(result)?null:result.toString());
	}
	
	

}

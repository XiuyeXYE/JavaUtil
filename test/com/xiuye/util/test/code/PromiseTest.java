package com.xiuye.util.test.code;

import org.junit.Test;

import com.xiuye.util.log.XLog;
import com.xiuye.weapon.dark.Promise;

public class PromiseTest {

	@Test
	public void test() {
		//default true
//		Promise.implyErrorHandler(false);
		new Promise<>(99999).then(d -> {
			XLog.lg(d);
			XLog.lg("void call(I in)");

		}).then(d -> {
			XLog.lg(d);
			XLog.lg("R call(I in)");

			return 999;
		}).then(() -> {
			XLog.lg("R call()");
			return "ABC";
		}).then(() -> {
			XLog.lg("void call()");
		}).then(() -> {
			XLog.lg("End");
		}).then(() -> {
			throw new RuntimeException("My Exception intentional");
		}).except(e -> {
			XLog.lg(e);
			return e;
		}).except(e -> {
			XLog.lg(e);
		}).except(e -> {
			XLog.lg(e);
		}).except(() -> {
			throw new RuntimeException("Nothing");
		})./* then(()->{}). */except(e -> {
			XLog.lg(e);
			return 100;
		}).except(d -> {
			XLog.log(d);
		}).except(() -> {

		}).except(() -> {
			
		});
	}
	
	@Test
	public void testIfElse() {
		
		Promise.resolve()
		.begin()
		.ef(false)
		.thenDo(()->{
			XLog.lg("123");
		})
		.eeseEf(true)
		.thenDo(()->{
			XLog.lg("thenDo");
		})
		.eeseEf(true)
		.thenDo(()->{
			XLog.lg("thenDo2");
		})
		.eese(()->{
			XLog.lg("ABC");
		})
		.ef(true)
		.thenDo(()->{
			XLog.lg("2 thenDo");
		})
		.eese(()->{
			XLog.lg("EESE");
		})
		.end().then(d->{			
			XLog.lg(d);
		});
		
		XLog.lg(trueflag()|falseflag());
		XLog.lg(trueflag()||falseflag());
		
	}
	
	private boolean trueflag() {
		XLog.lg("trueflag run");
		return true;
	}
	private boolean falseflag() {
		XLog.lg("falseflag run");
		return false;
	}
	
	@Test
	public void testMatchAs() {
		Promise.of().begin().match(123).as(99).thenDo(()->{
			XLog.lg("matched 1");
		}).as(100).as(11).thenDo(()->{
			XLog.lg("matched 2");
		}).defaut(()->{
			XLog.lg("default");
		}).end();
	}

}

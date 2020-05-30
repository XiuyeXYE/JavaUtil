package com.xiuye.util.test.code;

import org.junit.Test;

import com.xiuye.sharp.Promise;
import com.xiuye.util.log.XLog;

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
		.then(()->{
			XLog.lg("123");
		})
		.eeseEf(true)
		.then(()->{
			XLog.lg("then");
		})
		.eeseEf(true)
		.then(()->{
			XLog.lg("then2");
		})
		.eese(()->{
			XLog.lg("ABC");
		})
		.ef(true)
		.then(()->{
			XLog.lg("2 then");
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
		Promise.of().begin().match(123).as(99).then(()->{
			XLog.lg("matched 1");
		}).as(100).as(123).then(()->{
			XLog.lg("matched 2");
		}).defaut(()->{
			XLog.lg("default");
		}).end();
		
		Promise.of().begin().match(11).as(56789).as(11).then(()->{
			XLog.lg("56789");
		}).defaut(()->{
			XLog.lg("default");
		}).then(()->{}).end().then(()->{
			XLog.lg("then");
		});
		XLog.lg(null instanceof String);
		String s = null;
		XLog.lg(s instanceof String);
		
	}
	
	@Test
	public void testAllSwitchStatement() {
		
		Promise.beginS().ef(false).then(()->{
			XLog.lg(false);
		}).eeseEf(true).then(()->{
			XLog.lg(127,true);
		}).eese(()->{
			XLog.lg("else");
		}).ef(true).then(()->{
			XLog.lg(131,true);
		}).eese(()->{
			XLog.lg("eese");
		}).match(5).as(7).then(()->{
			XLog.lg(7);
		})
		.as(5).then(()->{
			XLog.lg(137,5);
		})
		.defaut(()->{
			XLog.lg(999);
		})
		.ef(true).then(()->{
			XLog.lg(139,true);
		})
		.end();
		
		Promise.beginS().match(123).as(123).as(99).then(()->{
			XLog.lg(149,123);
		}).end();
	}
}

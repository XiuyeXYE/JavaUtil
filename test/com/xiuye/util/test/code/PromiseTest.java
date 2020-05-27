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
		
		Promise.resolve().begin().ef(false).thenDo(d->{
			XLog.lg("if",d);
		}).eeseEf(false).thenDo(d->{
			XLog.lg("else if",d);
		}).eese(d->{
			XLog.lg("else",d);
		}).end();
		Object a = null;
		Object b = new Object();
		Promise.resolve().begin().ef(a).thenDo(d->{
			XLog.lg("if",d);
		}).eeseEf(b).thenDo(d->{
			XLog.lg("else if",d);
		}).eese(d->{
			XLog.lg("else",d);
		}).end();
		
		Promise.resolve().begin().ef(false).thenDo(d->{
			XLog.lg("if",d);
		}).eeseEf(false).thenDo(d->{
			XLog.lg("else if",d);
		}).eeseEf(true).thenDo(d->{
			XLog.lg("else if",d);
		}).eese(d->{
			XLog.lg("else",d);
		}).ef(false).thenDo(d->{
		}).thenDo(d->{
		}
		).end();
		
	}

}

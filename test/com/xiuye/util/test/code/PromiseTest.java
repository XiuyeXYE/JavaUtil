package com.xiuye.util.test.code;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
		
		Promise.beginS().match(99)
		.as(123).then(()->{
			XLog.lg(153,123);
		})
		.as(99).as(100).then(()->{
			XLog.lg(157,123);
		})
		.ef(true).then(()->{XLog.lg(159,true);})
		.ef(true).then(()->{XLog.lg(160,true);})
		.match(true).defaut(()->{XLog.lg(161,true);})
		.match("ABC").as("ABC").then(()->{
			XLog.lg("ABC");
		})
		.match(1).as(1).as(2)
		.then(()->{
			XLog.lg(1);
		})
		.ef(false)
		.then(()->{
			XLog.lg(false);
		})
		.eeseEf(true)
		.then(()->{
			XLog.lg(true);
		})
		.ef(false)
		.then(()->{
			XLog.lg(false);
		})
		.eese(()->{
			XLog.lg("else");
		})
		.ef(false)
		.then(()->{
			XLog.lg("doing");
		})
//		.ef(false)
		.match(11)
		.as(88)
		.as(11)
		.then(()->{
			XLog.lg(193);
		})
		.as(11)
		.then(()->{
			XLog.lg(197);
		})
		.defaut(()->{
			XLog.lg("default");
		})
		.ef(true)
		.then(()->{
			XLog.line(true);
		})
		.ef(true)
		.then(()->{
			XLog.ln(true);
		})
		.end();
	}
	
	@Test
	public void testThread() throws InterruptedException {
		Promise.threadS(()->{
			XLog.ln("thread S");
		}).then(t->{
			t.start();
		}).thread(d->{
			XLog.ln(d);
		}).then(d->{
			d.start();
		});
		Thread.sleep(3000);
	}
	
	@Test
	public void testLogOut() throws InterruptedException, ExecutionException, TimeoutException {
		Promise.logS(123,666,"ABC");
		Promise.lgS(123,666,"ABC");
		Promise.lnS(123,666,"ABC");
		Promise.lineS(123,666,"ABC").line().log().toJson().line().toFormatterJson().line().log();
		XLog.ln(123,"ABC");
		XLog.line(123,"ABC");
		String []ss = {
			"A","B","C"	
		};
		Promise.of(ss).line();
		Promise.of(ss).toJson().line().toJson().line();
		Promise.of(ss).toFormatterJson().line().toFormatterJson().line();
		Promise.formatterJsonKitS().then(d->{
			return d.fromJson((String)null, Object.class);
		}).line();
		Promise.of(ss).toJson().line().toObject(String[].class).then(d->{
			for(String s : d) {
				XLog.ln(s);
			}
			return null;
		}).toObject(String[].class).line().and(true).or(true).line().ln();
		
		Promise.of(new A("ABC","DEF")).toFormatterJson().line().toObject(Map.class).line()
		.toFormatterJson().line().toObject(A.class).line()
		.toObject(Map.class).except(e->{
			e.printStackTrace();
		}).line();
		
//		FutureTask<String> futureTask = new FutureTask<>(()->{
//			return "ABC";
//		});
//		XLog.ln(futureTask.get(1, TimeUnit.SECONDS));
// 		Thread.sleep(3000);
	}
	
	private static class A{
		String a;
		String b;
		
		
		
		public A(String a, String b) {
			super();
			this.a = a;
			this.b = b;
		}
		public String getA() {
			return a;
		}
		public void setA(String a) {
			this.a = a;
		}
		public String getB() {
			return b;
		}
		public void setB(String b) {
			this.b = b;
		}
		@Override
		public String toString() {
			return "A [a=" + a + ", b=" + b + "]";
		}
		
		
		
	}
	
	
	
	
}

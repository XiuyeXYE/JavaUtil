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
		new Promise<>(99999).THEN(d -> {
			XLog.lg(d);
			XLog.lg("void call(I in)");

		}).THEN(d -> {
			XLog.lg(d);
			XLog.lg("R call(I in)");

			return 999;
		}).THEN(() -> {
			XLog.lg("R call()");
			return "ABC";
		}).THEN(() -> {
			XLog.lg("void call()");
		}).THEN(() -> {
			XLog.lg("End");
		}).THEN(() -> {
			throw new RuntimeException("My Exception intentional");
		}).EXCEPT(e -> {
			XLog.lg(e);
			return e;
		}).EXCEPT(e -> {
			XLog.lg(e);
		}).EXCEPT(e -> {
			XLog.lg(e);
		}).EXCEPT(() -> {
			throw new RuntimeException("Nothing");
		})./* then(()->{}). */EXCEPT(e -> {
			XLog.lg(e);
			return 100;
		}).EXCEPT(d -> {
			XLog.log(d);
		}).EXCEPT(() -> {

		}).EXCEPT(() -> {
			
		});
	}
	
	@Test
	public void testIfElse() {
		
		Promise.RESOLVE()
		.BEGIN()
		.IF(false)
		.THEN(()->{
			XLog.lg("123");
		})
		.ELIF(true)
		.THEN(()->{
			XLog.lg("then");
		})
		.ELIF(true)
		.THEN(()->{
			XLog.lg("then2");
		})
		.ELSE(()->{
			XLog.lg("ABC");
		})
		.IF(true)
		.THEN(()->{
			XLog.lg("2 then");
		})
		.ELSE(()->{
			XLog.lg("EESE");
		})
		.END().THEN(d->{			
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
		Promise.OF().BEGIN().MATCH(123).AS(99).THEN(()->{
			XLog.lg("matched 1");
		}).AS(100).AS(123).THEN(()->{
			XLog.lg("matched 2");
		}).DEFAUT(()->{
			XLog.lg("default");
		}).END();
		
		Promise.OF().BEGIN().MATCH(11).AS(56789).AS(11).THEN(()->{
			XLog.lg("56789");
		}).DEFAUT(()->{
			XLog.lg("default");
		}).THEN(()->{}).END().THEN(()->{
			XLog.lg("then");
		});
		XLog.lg(null instanceof String);
		String s = null;
		XLog.lg(s instanceof String);
		
	}
	
	@Test
	public void testAllSwitchStatement() {
		
		Promise.BEGINS().IF(false).THEN(()->{
			XLog.lg(false);
		}).ELIF(true).THEN(()->{
			XLog.lg(127,true);
		}).ELSE(()->{
			XLog.lg("else");
		}).IF(true).THEN(()->{
			XLog.lg(131,true);
		}).ELSE(()->{
			XLog.lg("eese");
		}).MATCH(5).AS(7).THEN(()->{
			XLog.lg(7);
		})
		.AS(5).THEN(()->{
			XLog.lg(137,5);
		})
		.DEFAUT(()->{
			XLog.lg(999);
		})
		.IF(true).THEN(()->{
			XLog.lg(139,true);
		})
		.END();
		
		Promise.BEGINS().MATCH(123).AS(123).AS(99).THEN(()->{
			XLog.lg(149,123);
		}).END();
		
		Promise.BEGINS().MATCH(99)
		.AS(123).THEN(()->{
			XLog.lg(153,123);
		})
		.AS(99).AS(100).THEN(()->{
			XLog.lg(157,123);
		})
		.IF(true).THEN(()->{XLog.lg(159,true);})
		.IF(true).THEN(()->{XLog.lg(160,true);})
		.MATCH(true).DEFAUT(()->{XLog.lg(161,true);})
		.MATCH("ABC").AS("ABC").THEN(()->{
			XLog.lg("ABC");
		})
		.MATCH(1).AS(1).AS(2)
		.THEN(()->{
			XLog.lg(1);
		})
		.IF(false)
		.THEN(()->{
			XLog.lg(false);
		})
		.ELIF(true)
		.THEN(()->{
			XLog.lg(true);
		})
		.IF(false)
		.THEN(()->{
			XLog.lg(false);
		})
		.ELSE(()->{
			XLog.lg("else");
		})
		.IF(false)
		.THEN(()->{
			XLog.lg("doing");
		})
//		.ef(false)
		.MATCH(11)
		.AS(88)
		.AS(11)
		.THEN(()->{
			XLog.lg(193);
		})
		.AS(11)
		.THEN(()->{
			XLog.lg(197);
		})
		.DEFAUT(()->{
			XLog.lg("default");
		})
		.IF(true)
		.THEN(()->{
			XLog.line(true);
		})
		.IF(true)
		.THEN(()->{
			XLog.ln(true);
		})
		.END();
	}
	
	@Test
	public void testThread() throws InterruptedException {
		Promise.threadS(()->{
			XLog.ln("thread S");
		}).THEN(t->{
			t.start();
		}).thread(d->{
			XLog.ln(d);
		}).THEN(d->{
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
		Promise.OF(ss).line();
		Promise.OF(ss).toJson().line().toJson().line();
		Promise.OF(ss).toFormatterJson().line().toFormatterJson().line();
		Promise.formatterJsonKitS().THEN(d->{
			return d.fromJson((String)null, Object.class);
		}).line();
		Promise.OF(ss).toJson().line().toObject(String[].class).THEN(d->{
			for(String s : d) {
				XLog.ln(s);
			}
			return null;
		}).toObject(String[].class).line().AND(true).OR(true).line().ln();
		
		Promise.OF(new A("ABC","DEF")).toFormatterJson().line().toObject(Map.class).line()
		.toFormatterJson().line().toObject(A.class).line()
		.toObject(Map.class).EXCEPT(e->{
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

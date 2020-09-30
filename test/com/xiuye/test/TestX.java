package com.xiuye.test;

import java.util.Arrays;

import org.junit.Test;

import com.xiuye.sharp.X;
import com.xiuye.util.cls.XType;
import com.xiuye.util.log.XLog;

public class TestX {

	
	@Test
	public void testBasicFunctions() {
		X.of(123).THEN(d->{
			XLog.ln(d);
		}).THEN(d->{
			XLog.ln("skip exception hander");
		}).E().THEN(d->{
			XLog.ln(d);
		}).T().THEN(d->{
			XLog.lg(d);
		}).T(d->{
			XLog.ln(d);
		});
	}
	
	@Test
	public void testX1BasicFunctions() {
		X.beginS().IF(false).THEN(()->{
			XLog.lg("if");
		}).ELSE(()->{
			XLog.lg("else");
		})/*.ELIF(false)*/.end();
		X.beginS().MATCH("ABC").AS(1).AS(2).THEN(()->{
			XLog.ln("MATCH OK!");
		}).end().ln();
		
		X.beginS().MATCH(123).AS("ABC").THEN(d->{
			XLog.ln(d);
		}).DEFAUT(d->{
			XLog.ln("default",d);
		}).end().ln();
		
		X.beginS().MATCH(888).AS(888).THEN(d->{
			XLog.ln(d);
			return "ABC";
		}).end().ln().THEN(d->{
			XLog.ln(d);
		});
		X.beginS().MATCH(99).DEFAUT(d->{
			XLog.ln(d);
			return "JKL";
		}).end().ln();
	}	
	@Test
	public void testXTask() {
		X.taskS(()->{
			for(int i=0;i<100;i++) {
				XLog.ln(i);
			}
		}).THEN(t->{
			XLog.ln(t.get());
		}).task(d->{
			XLog.lg(d);
		}).THEN(d->{
			XLog.ln(d);
		});
	}
	
	@Test
	public void testOutput() {
		X.lgS(123,5677).lg().ln();
		X.lineS().ln();
		X.lnS("A","C",123).line();
	}
	
	@Test
	public void testBean() {
		X.beanS(String.class).getBean().end().ln();
		X.beanS(String.class,"ABC").register().getBean().end().ln();
		X.beanS(String.class,"C++",true).register().getBean().end().ln();
		X.beanS(String.class).getBean().end().ln();
		X.beanS("KEY",String.class,"ABC",true).register().getBean().end().ln();
		X<String> x = X.beanS(String.class,"ABC","BBB",true).register().getBean().end().ln()
		.bean().register().getBean().end().ln();
		;
		X.lnS(x.get());
		X.beanS("X").getBean().end().ln();
	}
	
	public static void main(String[] args) {
		
		X.lnS(123);
		X.of(123).ln();
		X.lineS(999);
		X.of(123).line();
		
		X.taskS(()->{
			X.lnS("WHAT?");
			return 100;
		}).THEN(d->{
			for(;;) {
				if(!d.isAlive()) {
					X.lnS(d);
					X.lnS(d.getClass());
					X.lnS(d.get());
					X.lnS(d.get());
					X.lnS(d.get());
					X.lnS(d.get());
					X.lnS(d.get());
					return d;
				}
			}			
		});
		
		
		X.toByteS(123).THEN(d->{
			for(byte s : d) {
				XLog.ln(s);
			}
		}).toByte(99L).THEN(d->{
			for(byte b :d) {
				X.lnS(b);
			}
		}).toByte(99.99).THEN(d->{
			for(byte b :d) {
				X.lnS(b);
			}
		});
		
		X.toByteS(9.9).THEN(d->{
			for(byte b :d) {
				X.lnS(b);
			}
			return d;
		}).THEN(d->{
			X.toLongS(d).THEN(g->{
				X.lnS(g);
				return g;
			}).THEN(g->{
				X.toDoubleS(d).ln();
				X.toDoubleS(g).ln();
//				X.toFloatS(d).ln();
			});
		});
		
		X.toLongS(9.9).ln();
		X.toDoubleS(4621762822593629389L).ln();
		
		X.toByteS(4621762822593629389L).THEN(d->{
			for(byte b :d) {
				X.lnS(b);
			}
			return d;
		}).THEN(d->{
			X.toLongS(d).ln();
		});
		
		X.toByteS(9.9f).THEN(d->{
			X.toIntS(d).THEN(g->{
				X.toFloatS(g).ln();
			});
			X.toFloatS(d).ln();
			X.toIntS(d).ln();
		});
		
//		long n = 99999999998L;
//		
//		byte []data = XType.newInstance(byte[]::new,8);
//		
//		for(int i=0;i<data.length;i++) {
//			data[i] = (byte) ((n>>>8*i) & 0xff);
//			X.lnS(data[i]);
//		}
//		
//		long m = 0L;
//		
//		for(int i=0;i<data.length;i++) {
//		
//			long t = data[i]&0xff;
//			long j = (t<<(8*i));
//			 m = m | j;
//			 X.lnS(i,data[i],m,t,j,t<<(8*i));
//		}
//		
//		X.lnS(m);
		
		
//		X.lnS(1L<<33);
//		X.lnS(1<<32);
//		X.lnS(1<<33);
	}

}

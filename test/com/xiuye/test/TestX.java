package com.xiuye.test;

import org.junit.Test;

import com.xiuye.sharp.X;
import com.xiuye.util.log.XLog;

public class TestX {

	
	@Test
	public void testBasicFunctions() {
		X.of(123).THEN(d->{
			XLog.ln(d);
		}).FINALLY(d->{
			XLog.ln(d);
		}).EX(d->{
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
	}
	
	public static void main(String[] args) {

	}

}

package com.xiuye.test;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import com.xiuye.sharp.X;
import com.xiuye.util.cls.XType;
import com.xiuye.util.log.XLog;


public class TestX {
	
	@Test
	public void testObject() {
		
		X.of();
		X.of(123);
		X.of((byte)99);
		
		X.x();
		X.x(123);
		
	}
	
	
	@Test
	public void testThen() {
		X.of(123).THEN(d->{
			XLog.ln(d);
			return "";
		}).THEN(d->{
			XLog.ln(d);
			return "";
		});
	}
	
	
	@Test
	public void testJson() {
		X.of("{a:123}").toObject(Map.class).ln().toFormatJson().ln();	
	}
	
	@Test
	public void testTo() {
		byte [] data = X.toByte("ABC").ln().get();
		X.toString(data).ln();
		data = X.toByte(123).ln().get();
		X.toInt(data).ln();
		data = X.toByte(123L).ln().get();
		X.toLong(data).ln();
		data = X.toByte(123.0f).ln().get();
		X.toFloat(data).ln();
		data = X.toByte(123.0D).ln().get();
		X.toDouble(data).ln();
		
		long d = X.toLong(123.0).ln().get();
		X.toDouble(d).ln();
		
		int i = X.toInt(123.0f).ln().get();
		X.toFloat(i).ln();
		
		List<Integer> list = XType.list();
		Random rand = XType.newInstance(Random::new);
		
		for(int j=0;j<100;j++) {
			list.add(rand.nextInt(1000));
		}
		
		Set<Integer> set = XType.set();
		set.addAll(list);
		
		Integer[] is = X.toArray(list, new Integer[0]).ln().get();
		X.toList(is).ln();
	
		is = X.toArray(set, new Integer[0]).ln().get();
		X.toSet(is).ln();
		
		
		
		
	}
	
	
	@Test
	public void testBean() {
		
		X.bean("x",String.class,"ABC").register().getBean().end().ln();
		X.bean("x").getBean().end().ln();
		X.bean(String.class).getBean().end().ln();
	}
	
	@Test
	public void testTask() {
		
		X.task(d->{
			XLog.ln(d);
			return X.DEFAULT_OBJECT;
		},123).THEN(d->{
			XLog.ln(d,d.get());
			return X.DEFAULT_OBJECT;
		});
		
		X.of(123).task(d->{
			
			X.lnS(d);
			
			return X.DEFAULT_OBJECT;
		});
		
	}
	
	@Test
	public void testNet() {
//		X.tcp(8888).THEN(d->{
//			
//			XLog.ln(d.getLocalPort(),d.getLocalSocketAddress());
//			XLog.ln(d.getInetAddress());
//			try {
//				XLog.ln(d.getReceiveBufferSize());
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//			
//			return X.DEFAULT_OBJECT;
//		});
//		
//		X.udp(9999).THEN(d->{
//			
//			
//			try {
//				XLog.ln(d.getBroadcast());
//				XLog.ln(d.getLocalAddress(),d.getLocalPort());
//				XLog.ln(d.getLocalSocketAddress());
//				XLog.ln(d.getRemoteSocketAddress());
//				XLog.ln(d.getReceiveBufferSize(),d.getSendBufferSize());
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//			
//			return X.DEFAULT_OBJECT;
//		});
	}
	
	
	
	
	

	
	
	public static void main(String[] args) {
		
		
	}

}

package com.xiuye.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xiuye.sharp.To;
import com.xiuye.sharp.X;
import com.xiuye.util.cls.XType;
import com.xiuye.util.log.XLog;

public class TestX {

	
	
	public static void main(String[] args) {
		
		X.of();
		X.resolve();
		X.x();
		X.task(()->{
			XLog.ln("task");
		});
		X.task(d->{
			XLog.ln(d);
			return "output";
		},"input").THEN(d->{
			
			XLog.ln(d);
			
			return null;
		});
		
		X.bean(String.class,"ABC").register().end().ln();
		X.bean(String.class).getBean().end().ln();
		
		X.tcp(8888).THEN(d->{
			XLog.ln(d);
			return null;
		});
		
		X.of("{\"key\":\"value\"}").toObject(Map.class).ln();
//		X.of(100).toObject(Map.class).ln();
//		X.of().toObject(String.class).ln();
		Map<String,Object> m = XType.map();
		m.put("KEYA","VALUEA");
		
		X.of(m).toFormatJson().ln();
		
		X.toArray(Arrays.asList(1,2,3,4,6),new Object[10]).ln();
		X.of(Arrays.asList(1,2,3,4,6)).toArray(new Object[10]).ln();
		
		
		X.toList(new Byte[100]).ln();
		
//		byte [] d = new byte[10];
		
		Set<Byte> set = XType.set();
		
		set.add((byte) 1);
		set.add((byte) 2);
		
		X.toArray(set, new Byte[0]).toList(d->{
			
			List<Byte> list = XType.list();
					
			for(byte b :d) {
				list.add(b);
			}
			
			return list;
		}).ln();
		
		
	}
	static class StringToLong implements To<Long>{

		String s;
		
		public StringToLong(String str) {
			this.s = str;
		}
		
		@Override
		public Long get() {
			return Long.parseLong(s);
		}
		
	}

}

package com.xiuye.util.test.cls;


import com.xiuye.util.cls.TypeUtil;
import com.xiuye.util.log.LogUtil;

public class TypeUtilTest {

	public static void main(String[] args) {
		TypeUtil.newInstance(A::new);
		TypeUtil.newInstance(A::new, "ABC");
		TypeUtil.newInstance(A::new, 1);
		TypeUtil.newInstance(A::new, 1,1,2,3,4,5,6);
		//Now , T[]  <=> T...t  
//		TypeUtil.newInstance(A::new, new Integer[]{1,2});
		TypeUtil.newInstance(A::new, new int[]{1,2});
		TypeUtil.newInstance(A::new, new Integer[]{1,2},new Integer[]{1,2});
		TypeUtil.newInstance(A::new, 1,"abc",1L);
		TypeUtil.newInstance(A::new, "GGGGGG","abc");
//		TypeUtil.newInstance(B::new, 1,"abc",1L);
	}

	static class B{
		B(Integer i,String s,Long l){
			LogUtil.log("A(int i,String s,Long l)");
		}
	}
	static class A{
		A(){
			LogUtil.log("A()");
		}
		A(int i){
			LogUtil.log("A(int i)");
		}
		A(String s){
			LogUtil.log("A(String s)");
		}
		A(String s1,String s2){
			LogUtil.log("A(String s1,String s2)");
		}
//		A(Integer []s){
//			LogUtil.log("A(Integer []s)");
//		}
		A(Integer ...s){
			LogUtil.log("A(Integer ...s)");
		}
		A(String ... s){
			LogUtil.log("A(String ... s)");
		}
//		A(int...i){
//			LogUtil.log("A(int ... i)");
//		}
		A(int[]i){
			LogUtil.log("A(int ... i)");
		}
		A(int i,String s,Long l){
			LogUtil.log("A(int i,String s,Long l)");
		}
//		A(T...t){
//			LogUtil.log("A(T...t)");
//		}
		A(Object...t){
			LogUtil.log("A(T...t)");
		}
	}
}

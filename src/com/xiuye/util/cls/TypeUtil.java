package com.xiuye.util.cls;

public class TypeUtil {

	public static<T,E extends T> T dynamic_cast(E e) {
		return (T) e;
	}
	
	
	public interface DefaultConstructor<R>{
		R construct();
	}
	
	public interface ConstructorWithParam<R,T>{
		R construct(T t);
	}
	
	public interface ConstructorWithTwoParams<R,T1,T2>{
		R construct(T1 t1,T2 t2);
	}
	public interface ConstructorWithThreeParams<R,T1,T2,T3>{
		R construct(T1 t1,T2 t2,T3 t3);
	}
	public interface ConstructorWithFourParams<R,T1,T2,T3,T4>{
		R construct(T1 t1,T2 t2,T3 t3,T4 t4);
	}
	public interface ConstructorWithFiveParams<R,T1,T2,T3,T4,T5>{
		R construct(T1 t1,T2 t2,T3 t3,T4 t4,T5 t5);
	}
	public interface ConstructorWithSixParams<R,T1,T2,T3,T4,T5,T6>{
		R construct(T1 t1,T2 t2,T3 t3,T4 t4,T5 t5,T6 t6);
	}
	public interface ConstructorWithSevenParams<R,T1,T2,T3,T4,T5,T6,T7>{
		R construct(T1 t1,T2 t2,T3 t3,T4 t4,T5 t5,T6 t6,T7 t7);
	}
	public interface ConstructorWithEightParams<R,T1,T2,T3,T4,T5,T6,T7,T8>{
		R construct(T1 t1,T2 t2,T3 t3,T4 t4,T5 t5,T6 t6,T7 t7,T8 t8);
	}
	
	public interface ConstructorWithParams<R,T>{
		@SuppressWarnings("unchecked")
		R construct(T...t);
	}
	
	
	
	
	
	public static<R> R newInstance(DefaultConstructor<R> c) {
		return c.construct();
	}
	
	public static<R,T> R newInstance(ConstructorWithParam<R,T> c,T t) {
		return c.construct(t);
	}
	
	public static<R,T1,T2> R newInstance(ConstructorWithTwoParams<R,T1,T2> c,T1 t1,T2 t2) {
		return c.construct(t1,t2);
	}
	
	public static<R,T1,T2,T3> R newInstance(ConstructorWithThreeParams<R,T1,T2,T3> c,T1 t1,T2 t2,T3 t3) {
		return c.construct(t1,t2,t3);
	}
	
	public static<R,T1,T2,T3,T4> R newInstance(ConstructorWithFourParams<R,T1,T2,T3,T4> c,T1 t1,T2 t2,T3 t3,T4 t4) {
		return c.construct(t1,t2,t3,t4);
	}
	
	public static<R,T1,T2,T3,T4,T5> R newInstance(ConstructorWithFiveParams<R,T1,T2,T3,T4,T5> c,T1 t1,T2 t2,T3 t3,T4 t4,T5 t5) {
		return c.construct(t1,t2,t3,t4,t5);
	}
	
	
	public static<R,T1,T2,T3,T4,T5,T6> R newInstance(ConstructorWithSixParams<R,T1,T2,T3,T4,T5,T6> c,T1 t1,T2 t2,T3 t3,T4 t4,T5 t5,T6 t6) {
		return c.construct(t1,t2,t3,t4,t5,t6);
	}
	
	public static<R,T1,T2,T3,T4,T5,T6,T7> R newInstance(ConstructorWithSevenParams<R,T1,T2,T3,T4,T5,T6,T7> c,T1 t1,T2 t2,T3 t3,T4 t4,T5 t5,T6 t6,T7 t7) {
		return c.construct(t1,t2,t3,t4,t5,t6,t7);
	}
	
	
	public static<R,T1,T2,T3,T4,T5,T6,T7,T8> R newInstance(ConstructorWithEightParams<R,T1,T2,T3,T4,T5,T6,T7,T8> c,T1 t1,T2 t2,T3 t3,T4 t4,T5 t5,T6 t6,T7 t7,T8 t8) {
		return c.construct(t1,t2,t3,t4,t5,t6,t7,t8);
	}
	
	
	
	//Now , T[]  <=> T...t 
	@SafeVarargs
	public static<R,T> R newInstance(ConstructorWithParams<R,T> c,T...t) {
		return c.construct(t);
	}
	
//	public static<R,T> R newInstance(ConstructorWithParams<R,T> c,Object...t) {
//		return c.construct(t);
//	}
	
}

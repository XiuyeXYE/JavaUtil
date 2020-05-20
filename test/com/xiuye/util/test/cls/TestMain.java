package com.xiuye.util.test.cls;

public class TestMain {

	
	
	public static void main(String[] args) {
		new A().new B().g();
	}

}

class A{
	public void f() {
		System.out.println("A::f");
	}
	class B{
		public void g() {
			f();
		}
	}
}
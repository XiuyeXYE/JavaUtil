package com.xiuye.util.graph;

import java.math.BigInteger;

import com.xiuye.sharp.X;

public class GCD {

	public static int gcd(int a,int b) {
		
		if(a<b) {
			int t = a;
			a = b;
			b = t;
		}
		
		while(b!=0) {
			int r = a%b;
			a = b;
			b = r;
		}
		
		return a;
		
		
	}
	
	public static int lcd(int a,int b) {
		int m = a;
		int n = b;
		if(a<b) {
			int t = a;
			a = b;
			b = t;
		}
		
		while(b!=0) {
			int r = a%b;
			a = b;
			b = r;
		}
		
		return m*n/a;
	}
	
	public static int extendGcd(int a,int b,int x,int y) {
		if(b==0) {
			x = 1;
			y = 0;
			return a;
		}
		int r = extendGcd(b, a%b, x, y);
		int temp = y;
		y = x - (a/b)*y;
		x = temp;
		return r;
	}
	
	public static void main(String[] args) {
		X.lnS(gcd(12,6),gcd(3,5));
		X.lnS(lcd(12,6),lcd(3,5));
		BigInteger bi1 = new BigInteger("12345678910");
		BigInteger bi2 = new BigInteger("12345678910");
		
		BigInteger bi3 = bi1.multiply(bi2);
		X.lnS(bi3);
		X.lnS(BigInteger.TEN);
		X.lnS(BigInteger.TEN.pow(500));
	}
	
}

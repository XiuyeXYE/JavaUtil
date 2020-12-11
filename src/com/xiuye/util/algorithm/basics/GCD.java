package com.xiuye.util.algorithm.basics;


import com.xiuye.util.Pointer;

public class GCD {

    //欧几里得算法，求最大公约数
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        int r = a % b;
        return gcd(b, r);
    }

    public static void main(String[] args) {
        Pointer.lg(gcd(3, 5), gcd(100, 50));
    }
}

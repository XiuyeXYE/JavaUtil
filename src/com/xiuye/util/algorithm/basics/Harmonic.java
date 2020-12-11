package com.xiuye.util.algorithm.basics;

import com.xiuye.util.Pointer;

public class Harmonic {
    /**
     * 调和级数
     *
     * @param n
     * @return
     */
    public static double h(int n) {
        double sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += 1.0 / i;
        }
        return sum;
    }

    public static void main(String[] args) {
        Pointer.lg(h(100));
        Pointer.lg(h(101));
        Pointer.lg(h(102));
        Pointer.lg(h(103));
        Pointer.lg(h(104));
        Pointer.lg(h(105));
    }
}

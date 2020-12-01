package com.xiuye.util.algorithm.basics;

import com.xiuye.util.X;

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
        X.lg(h(100));
        X.lg(h(101));
        X.lg(h(102));
        X.lg(h(103));
        X.lg(h(104));
        X.lg(h(105));
    }
}

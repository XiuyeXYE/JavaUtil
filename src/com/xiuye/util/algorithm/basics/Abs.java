package com.xiuye.util.algorithm.basics;

import com.xiuye.util.X;

public class Abs {
    public static int abs(int i) {
        if (i < 0) return -i;
        else return i;
    }

    public static void main(String[] args) {
        X.lg(abs(-199));
    }
}

package com.xiuye.util.algorithm.basics;

import com.xiuye.util.Pointer;

public class Abs {
    public static int abs(int i) {
        if (i < 0) return -i;
        else return i;
    }

    public static void main(String[] args) {
        Pointer.lg(abs(-199));
    }
}

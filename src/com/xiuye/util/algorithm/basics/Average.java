package com.xiuye.util.algorithm.basics;

import com.xiuye.util.X;

public class Average {
    public static double average(int[] arr) {
        if (X.isEmpty(arr)) {
            X.throwREx("传入数组不能为空");
        }
        double value = 0.0;
        for (int i : arr) {
            value += i;
        }
        value = value / arr.length;
        return value;
    }

    public static void main(String[] args) {
        X.lg(average(new int[]{1, 23, 434, 123, 3452, 111, 232}));
    }
}

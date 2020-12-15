package com.xiuye.util.algorithm.basics;

import com.xiuye.util.Pointer;

public class Average {
    public static double average(int[] arr) {
        if (Pointer.isEmpty(arr)) {
            Pointer.throwEx("传入数组不能为空");
        }
        double value = 0.0;
        for (int i : arr) {
            value += i;
        }
        value = value / arr.length;
        return value;
    }

    public static void main(String[] args) {
        Pointer.lg(average(new int[]{1, 23, 434, 123, 3452, 111, 232}));
    }
}

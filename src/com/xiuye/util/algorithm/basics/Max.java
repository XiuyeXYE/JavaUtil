package com.xiuye.util.algorithm.basics;

import com.xiuye.util.Pointer;

public class Max {

    public static int max(int[] arr) {
        if (Pointer.isEmpty(arr)) {
            Pointer.throwREx("传入数组不能为空");
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

    public static void main(String[] args) {
        Pointer.lg(max(new int[]{99123, 34, 4532, 2345, 623623, 98787612, 12412}));
    }
}

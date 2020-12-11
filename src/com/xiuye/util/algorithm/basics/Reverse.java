package com.xiuye.util.algorithm.basics;

import com.xiuye.util.Pointer;

public class Reverse {

    public static int[] reverse(int[] arr) {
        if (Pointer.isEmpty(arr)) {
            Pointer.throwREx("传入数组不能为空");
        }
        int n = arr.length;
        for (int i = 0; i < n / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[n - i - 1];
            arr[n - i - 1] = temp;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = reverse(new int[]{1, 2, 4, 5, 6, 7});
        for (int a : arr) {
            Pointer.print(a, " ");
        }
        Pointer.println();
    }

}

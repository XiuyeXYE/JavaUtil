package com.xiuye.util.algorithm.basics;

import com.xiuye.util.X;

public class BinarySearch {

    //firstly sorted a[]
    public static int rank(int key, int[] a) {
        if (X.isEmpty(a)) {
            X.throwREx("传入数组不能为空");
        }
        //双指针
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo);
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static int rank(int key, int[] a, int lo, int hi) {
        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid]) {
            return rank(key, a, lo, mid - 1);
        } else if (key > a[mid]) {
            return rank(key, a, mid + 1, hi);
        } else return mid;
    }

    public static void main(String[] args) {
        X.lg(rank(13, new int[]{3, 6, 8, 9, 11, 13, 32, 33, 55}));
        X.lg(rank(13, new int[]{13}));
        X.lg(rank(0, new int[]{1}));
        X.lg(rank(7, new int[]{1, 5, 6, 7, 8, 9}, 0, 5));

//        int a = X.readInt();
//        int b = 10;
//        int c = X.readInt();
//        X.lg(a,b,c);
    }

}

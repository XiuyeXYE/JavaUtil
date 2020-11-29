package com.xiuye.util.algorithm.basics;

import com.xiuye.util.X;

import java.util.Random;

public class Matrix {
    /**
     * A[i][s] * B[s][j] = c[i][j]
     *
     * @param a
     * @param b
     * @return
     */
    public static int[][] dot(int a[][], int b[][]) {
        /**
         * i,s * s,j
         */
        int[][] c = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[i].length; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public static void main(String[] args) {
        int[][] a = new int[10][8];
        int[][] b = new int[8][20];
        Random random = new Random();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = random.nextInt(10);
            }
        }
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                b[i][j] = random.nextInt(10);
            }
        }
        X.lg("a=");
        X.lg("[");
        for (int i = 0; i < a.length; i++) {
            X.print("\t\t");
            for (int j = 0; j < a[i].length; j++) {
                X.print(a[i][j], "");
            }
            X.lg();
        }
        X.lg("]");
        X.lg("b=");
        X.lg("[");
        for (int i = 0; i < b.length; i++) {
            X.print("\t\t");
            for (int j = 0; j < b[i].length; j++) {
                X.print(b[i][j], "");
            }
            X.lg();
        }
        X.lg("]");

        int[][] c = dot(a, b);
        X.lg("c=");
        X.lg("[");
        for (int i = 0; i < c.length; i++) {
            X.print("\t\t");
            for (int j = 0; j < c[i].length; j++) {
                X.print(c[i][j], "");
            }
            X.lg();
        }
        X.lg("]");


    }
}

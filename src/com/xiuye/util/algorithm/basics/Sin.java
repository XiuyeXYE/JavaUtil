package com.xiuye.util.algorithm.basics;

import com.xiuye.util.X;

public class Sin {
    /**
     * y = 0.987862x - 0.155271x^3 + 0.00564312x^5
     *
     * @param c
     * @return
     */
    public static double sin1(double cn) {
        X.lg("sin1:", cn);
        double a = 0.987862;
        double b = 0.155271;
        double c = 0.00564312;


        return a * cn - b * cn * cn * cn + c * cn * cn * cn * cn * cn;
    }


    //sin(x) 幂级数
    public static double sin2(double x) {
        //把大于 -2PI 和 2PI 的值放入区间内

        int rSign = 1;
        if (x < 0) {
            rSign = -rSign;
            x = -x;
        }
        // >2PI
        x %= 2 * Math.PI;
        // >

        X.lg("sin2:", x);
        int cnt = 0;
        double derr = 1e-15;

        long exp = 1;
        int sign = 1;
        double r = x;

        for (long n = 1; ; n++) {

            sign = -sign;//-1 or +1
            double xm = x;
            long m = 2 * n + 1;
            exp *= m * (m - 1);
            xm = Math.pow(x, m);
//            X.lg("m:",m);
//            X.lg("exp:",exp);
//            X.lg("xm:",xm);
            if (exp < 0 || xm / exp < derr) {
                break;
            }
            r += sign * xm / exp;
            cnt++;


        }
        X.lg("sin2.cnt:", cnt);
        return rSign * r;

    }

    //cos(x) 幂级数
    public static double cos2(double x) {
        //把大于 -2PI 和 2PI 的值放入区间内

        int rSign = 1;
        if (x < 0) {
//            rSign = -rSign;
            x = -x;
        }
        // >2PI
        x %= 2 * Math.PI;
        // >

        X.lg("cos2:", x);
        int cnt = 0;
        double derr = 1e-15;

        long exp = 1;
        int sign = 1;
        double r = 1;

        for (long n = 1; ; n++) {

            sign = -sign;//-1 or +1
            double xm = x;
            long m = 2 * n;
            exp *= m * (m - 1);
            xm = Math.pow(x, m);
//            X.lg("m:",m);
//            X.lg("exp:",exp);
//            X.lg("xm:",xm);
            if (exp < 0 || xm / exp < derr) {
                break;
            }
            r += sign * xm / exp;
            cnt++;


        }
        X.lg("cos2.cnt:", cnt);
        return rSign * r;

    }


    public static void main(String[] args) {
        X.lg("PI/2", sin1(Math.PI / 2));
        X.lg("PI/6", sin1(Math.PI / 6));
        X.lg("PI/4", sin1(Math.PI / 4));
        X.lg(1 / Math.sqrt(2));
        X.lg("PI/2", sin2(Math.PI / 2));
        X.lg("PI/2+2PI", sin2(Math.PI / 2 + Math.PI * 2));//有精度损失
        X.lg("PI/6", sin2(Math.PI / 6));
        X.lg("PI/6+2PI", sin2(Math.PI / 6 + Math.PI * 2));//有精度损失
        X.lg("PI/4", sin2(Math.PI / 4));
        X.lg("PI/4+2PI", sin2(Math.PI / 4 + Math.PI * 2));//有精度损失
        X.lg("-PI/6", sin2(-Math.PI / 6 + Math.PI * 2));//有精度损失
        X.lg("-PI/6+2PI", sin2(-Math.PI / 6 + Math.PI * 2));//有精度损失
        X.lg("2PI", sin2(Math.PI * 2));//有精度损失
        X.lg("-2PI", sin2(Math.PI * 2));//有精度损失

        X.lg(11.0 % 88);
        X.lg(11.0 / 88);
        X.lg(11.0 / 88 * 88);
        X.lg(Math.PI / Math.PI);
        X.lg(2 * Math.PI / (2 * Math.PI));
        X.lg(-Math.PI / Math.PI);
        X.lg(-2 * Math.PI / (2 * Math.PI));

        X.lg("PI/2", cos2(Math.PI / 2));
        X.lg("PI", cos2(Math.PI));
        X.lg("-PI", cos2(-Math.PI));
        X.lg("PI/6", cos2(Math.PI / 6));
        X.lg("PI/3", cos2(Math.PI / 3));
        X.lg("PI/4", cos2(Math.PI / 4));
        X.lg("2PI", cos2(2 * Math.PI));
        X.lg("0", cos2(0));
//        BigInteger bigInteger = BigInteger.valueOf(2432902008176640000L);
//        X.lg(bigInteger.multiply(BigInteger.valueOf(2432902008176640000L)));
//        X.lg(bigInteger.multiply(BigInteger.valueOf(2432902008176640000L)).doubleValue());

    }
}

package com.xiuye.util.algorithm.basics;

import com.xiuye.util.Pointer;

import java.math.BigDecimal;
import java.math.MathContext;

public class Sin {

    public static final BigDecimal PI = new BigDecimal("3.14159265358979323846");

    /**
     * y = 0.987862x - 0.155271x^3 + 0.00564312x^5
     *
     * @param cn
     * @return
     */
    public static double sin1(double cn) {
        Pointer.lg("sin1:", cn);
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

        Pointer.lg("sin2:", x);
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
        Pointer.lg("sin2.cnt:", cnt);
        return rSign * r;

    }

    private static BigDecimal bigDecimal(String val) {
        return new BigDecimal(val);
    }

    public static BigDecimal sin3(BigDecimal x) {
        //把大于 -2PI 和 2PI 的值放入区间内

        BigDecimal rSign = bigDecimal("1");
//        int rSign = 1;
        if (x.compareTo(bigDecimal("0")) < 0) {
            rSign = rSign.negate();
//            rSign = -rSign;
            x = x.negate();
        }
        // >2PI
        x = x.remainder(PI.multiply(bigDecimal("2")));
//        x %= 2 * Math.PI;
        // >

        Pointer.lg("sin3:", x);
        int cnt = 0;
//        double derr = 1e-15;

        BigDecimal derr = bigDecimal("1e-100");

        BigDecimal exp = bigDecimal("1");
        BigDecimal sign = bigDecimal("1");
//        double r = x;
        BigDecimal r = x;

        for (BigDecimal n = bigDecimal("1"); ; n = n.add(bigDecimal("1"))) {

//            sign = -sign;//-1 or +1
            sign = sign.negate();
            BigDecimal xm = x;
            BigDecimal m = bigDecimal("2").multiply(n).add(bigDecimal("1"));

//            exp *= m * (m - 1);
            exp = exp.multiply(m.multiply(m.subtract(bigDecimal("1"))));
//            xm = Math.pow(x, m);
            xm = x.pow(m.intValue());

//            X.lg("m:",m);
//            X.lg("exp:",exp);
//            X.lg("xm:",xm);
//            exp.compareTo(bigDecimal("0"))<0 ||
            if (xm.divide(exp, MathContext.DECIMAL128).compareTo(derr) < 0) {
                break;
            }
            r = r.add(sign.multiply(xm).divide(exp, MathContext.DECIMAL128));
            cnt++;


        }
        Pointer.lg("sin3.cnt:", cnt);
        r = rSign.multiply(r);

//        BigDecimal complement = bigDecimal("1e-40");
////
//        r = r.add(complement.multiply(rSign));

//        r.round(MathContext.DECIMAL128);
        return r.setScale(20, BigDecimal.ROUND_HALF_UP);
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

        Pointer.lg("cos2:", x);
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
        Pointer.lg("cos2.cnt:", cnt);
        return rSign * r;

    }


    public static void main(String[] args) {
        Pointer.lg("PI/2", sin1(Math.PI / 2));
        Pointer.lg("PI/6", sin1(Math.PI / 6));
        Pointer.lg("PI/4", sin1(Math.PI / 4));
        Pointer.lg(1 / Math.sqrt(2));
        Pointer.lg("PI/2", sin2(Math.PI / 2));
        Pointer.lg("PI/2+2PI", sin2(Math.PI / 2 + Math.PI * 2));//有精度损失
        Pointer.lg("PI/6", sin2(Math.PI / 6));
        Pointer.lg("PI/6+2PI", sin2(Math.PI / 6 + Math.PI * 2));//有精度损失
        Pointer.lg("PI/4", sin2(Math.PI / 4));
        Pointer.lg("PI/4+2PI", sin2(Math.PI / 4 + Math.PI * 2));//有精度损失
        Pointer.lg("-PI/6", sin2(-Math.PI / 6 + Math.PI * 2));//有精度损失
        Pointer.lg("-PI/6+2PI", sin2(-Math.PI / 6 + Math.PI * 2));//有精度损失
        Pointer.lg("2PI", sin2(Math.PI * 2));//有精度损失
        Pointer.lg("-2PI", sin2(Math.PI * 2));//有精度损失

        Pointer.lg(11.0 % 88);
        Pointer.lg(11.0 / 88);
        Pointer.lg(11.0 / 88 * 88);
        Pointer.lg(Math.PI / Math.PI);
        Pointer.lg(2 * Math.PI / (2 * Math.PI));
        Pointer.lg(-Math.PI / Math.PI);
        Pointer.lg(-2 * Math.PI / (2 * Math.PI));

        Pointer.lg("PI/2", cos2(Math.PI / 2));
        Pointer.lg("PI", cos2(Math.PI));
        Pointer.lg("-PI", cos2(-Math.PI));
        Pointer.lg("PI/6", cos2(Math.PI / 6));
        Pointer.lg("PI/3", cos2(Math.PI / 3));
        Pointer.lg("PI/4", cos2(Math.PI / 4));
        Pointer.lg("2PI", cos2(2 * Math.PI));
        Pointer.lg("0", cos2(0));

        Pointer.lg(Double.toString(98.00001), 98.00001);


        Pointer.lg(sin3(PI.divide(bigDecimal("2"), 100, BigDecimal.ROUND_HALF_UP)));
        Pointer.lg(sin3(PI.divide(bigDecimal("2"), 100, BigDecimal.ROUND_HALF_UP).multiply(bigDecimal("-1"))));
        Pointer.lg(sin3(PI.divide(bigDecimal("3"), 100, BigDecimal.ROUND_HALF_UP)));
        Pointer.lg(sin3(PI.divide(bigDecimal("4"), 100, BigDecimal.ROUND_HALF_UP)));
        Pointer.lg(sin3(PI));
//        BigInteger bigInteger = BigInteger.valueOf(2432902008176640000L);
//        X.lg(bigInteger.multiply(BigInteger.valueOf(2432902008176640000L)));
//        X.lg(bigInteger.multiply(BigInteger.valueOf(2432902008176640000L)).doubleValue());

    }
}

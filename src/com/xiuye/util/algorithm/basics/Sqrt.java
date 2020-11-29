package com.xiuye.util.algorithm.basics;

import com.xiuye.util.X;

public class Sqrt {

    //牛顿迭代法
    public static double sqrt1(double c) {
        X.lg("sqrt1:", c);
        if (c < 0) return Double.NaN;
        double err = 1e-15;
        double t = c;
        int cnt = 0;
        // |Xn+1 - Xn| < err

        //终止条件关键在于 最后比值是 1, c=t*t <=> c-t*t=0 <=> c/(t*t)=1 <=> 1-c/(t*t)=0
        //in the end,c==t*t,namely c/(t*t)=1 => 1-c/(t*t) = 0
        //=>(t*t-c)/(t*t)=0 => (t-c/t)=0*t
        while (Math.abs(t - c / t) > err * t) {
            //Xn+1 = Xn - f(Xn)/f'(Xn)
            //Xn+1 = Xn - (Xn*Xn - c)/(2*Xn)
            //Xn+1 = (Xn*Xn+c)/(2*Xn)
            //Xn+1 = (Xn+c/Xn)/2
            //都是牛顿迭代法
            t = (c / t + t) / 2.0;
//            X.lg("cc:",t);
            cnt++;
        }
        X.lg("sqrt1.cnt=", cnt);
        return t;
    }

    //牛顿迭代法
    public static double sqrt2(double c) {
        X.lg("sqrt2:", c);
        if (c < 0) return Double.NaN;
        double err = 1e-15;
        double t = c;
        int cnt = 0;
        /**
         * y = sqrt(x)
         * <=> y*y = x
         * <=> y*y - x = 0
         * let x = c;
         * using computer to calc y.
         * then x*x - c = 0;
         * f(x) = x*x - c,get f(x) = 0 's roots
         * 牛顿迭代法
         * 求曲线上一点（xn,yn）的切线方程
         * y-f(xn)=f'(xn)(x-xn)
         * 求切线于x轴交点
         * -f(xn)=f'(xn)(x-xn)
         * => x=xn-f(xn)/f'(xn)
         * 设求得点为Xn+1
         * 这个Xn+1趋近于f(x)=0的零点，也就是根
         * 可以画出图像，不断以Xn+1的点作为新的曲线切线的点
         * 再求切线与x轴交点，会是一样格式的公式
         * 所以不断迭代 Xn+1，xn 就得到趋近于 根的点
         * 选择趋近 根 的起点 xn
         * 1.迭代公式需要设置起点，
         * 2.因为 f(x) = x*x - c 的图像把起点设置在正半边 ，得到的结果是正的
         * 3.迭代公式终结条件应该是 Xn+1*Xn+1 - c == 0!
         *  因为从一开始就是求得 x*x - c = 0;但是迭代公司设置起点使得x*x-c > 0,==无法成立，
         *  就不会进入循环进行计算了
         *  应该 是 Xn+1*Xn+1 - c != 0,循环进行到不满足终止条件 ,但是永远不会结束计算 因为 浮点数 == 0不精确
         * 4.因为计算机的公式是趋近于，而且计算机浮点数运算并不精确，是无法做计算的 x*x -c =0，
         *  只能在合适的 逼近区间内，所有有一个err
         */
        //终止条件
        while (Math.abs(t * t - c) > err) {
            t = (t * t + c) / (2 * t);
//            X.lg("cc:",t);
            cnt++;
        }
        X.lg("sqrt2.cnt=", cnt);
        return t;
    }

    //牛顿迭代法
    public static double sqrt3(double c) {
        X.lg("sqrt3:", c);
        if (c < 0) return Double.NaN;
        double err = 1e-15;
        double t = c;
        int cnt = 0;
        //never finish!
//        while (Math.abs(t*t-c)!= 0){//equals 0
//            X.lg(t,t*t-c);
//            t = (t*t+c)/(2*t);
//        }
        //never finish
//        while (t*t!=c){//equals 0
//            X.lg(t,t*t-c);
//            t = (t*t+c)/(2*t);
//        }
        //这个和公式2是一样，只是没有简化
        //Xn+1 = xn - f(xn)/f'(xn)
        while (Math.abs(t * t - c) > err) {
            t = t - (t * t - c) / (2 * t);
//            X.lg("cc:",t);
            cnt++;
        }
        X.lg("sqrt3.cnt=", cnt);
        return t;
    }


    //牛顿迭代法
    public static double sqrt4(double c) {
        X.lg("sqrt4:", c);
        if (c < 0) return Double.NaN;
        double t = c;
        int cnt = 0;
        //固定循环次数
        for (int i = 0; i < 1000; i++) {
//            t = t - (t*t-c)/(2*t);
            t = (t * t + c) / (2 * t);
            cnt++;
        }
        X.lg("sqrt4.cnt=", cnt);
        return t;
    }

    //二分法求根(sqrt) 有缺陷

    /**
     * 任取两点，x1,x2
     * if f(x1)*f(x2) < 0
     * 有零点
     * 否则，无
     * 取中点x=(x1+x2)/2
     * if f(x)*f(x1)>0,同号，let x1=x
     * if f(x)*f(x1)<0,异号，let x2=x
     * 如此迭代 x1,x2
     * 当x1=x2 终止。
     * 但是这不是理想的，x1!=x2 ,so |x2-x1|<10^-15就好
     * 求 y=sqrt(x) <=> y*y=x
     * <=> y*y-x=0 <=> x=c,then x*x-c=0
     * 求二次函数的根 f(x)=x*x-c , f(x)=0
     * 连续函数 零点定理
     *
     * @param c
     * @return
     */
    public static double sqrt5(double c) {
        X.lg("sqrt5:", c);
        double x1 = 0;//固定设置x1 为 0;
//        double x2 = c;//固定设置X2 为 c;起始点不能为x小于1的点，否则小数算的是错误的结果
        double x2 = c + 1;//固定设置X2 为 c;起始点不能为x小于1的点，否则小数算的是错误的结果，所以+1
        int cnt = 0;
        double x = (x1 + x2) / 2;//求中点
        cnt++;
        double err = 1e-15;

        //f(x)=x*x-c
        while (Math.abs(x2 - x1) > err) {//求x*x-c=0的近似根
            //f(x1)*f(x2) > or < 0
            double v = (x1 * x1 - c) * (x * x - c);
            if (v < 0) {//x1与x不同边
                x2 = x;
            } else {//x1与x同边
                x1 = x;
            }
            x = (x1 + x2) / 2;
            cnt++;
        }

        x = (x1 + x2) / 2;
        cnt++;
        X.lg("sqrt5.cnt=", cnt);
        return x;

    }

    /**
     * 弦截法
     * (x1,f(x2))和(x2,f(x2))两点连线 与 x轴的交点(x,0)
     * 然后 和二分法的 判断相同！
     * 1.求出直线方程 两点式
     * y - y1 = (y2-y1)/(x2-x1)*(x-x1)
     * 2.求x
     * x=x1-(y1*(x2-x1)/(y2-y1))
     * x=x1-(x1*x1-c)/(x2+x1)
     * x=(x1*x2+c)/(x2+x1)
     * 仍是求 f(x)=x*x-c,f(x)=0的近似根
     *
     * @param c
     * @return
     */
    public static double sqrt6(double c) {
        X.lg("sqrt6:", c);
        int cnt = 0;
        double err = 1e-15;
        double x1 = 0;
//        double x2 = c;//固定设置X2 为 c;起始点不能为x小于1的点，否则小数算的是错误的结果
        double x2 = c;//固定设置X2 为 c;起始点不能为x小于1的点，否则小数算的是错误的结果+1能保证x2始终在右边且f(x2)>0
        double x = (x1 * x2 + c) / (x2 + x1);
        cnt++;
        //由于x与x1始终在同一边
        //所有区间趋近的时候 用 x-x1
        //另外 x2 = c 开始的时候 c*c - c
        //算的x点与x1点，x2的值始终不变的！
//        while (Math.abs(x2 - x1) > err) {
        while (Math.abs(x - x1) > err) {
//        while (Math.abs(x1*x1 - c) > err) {//这种弊端还是很明显的，就是精度有问题，否则就是无限循环
//            X.lg(x1,x2,x1*x1,c,x-x1);
            //f(x1)*f(x2) > or < 0
            //下面这个计算是无效的
            //始终都是 x 与 x1(起始0) 在同一边
//            double v = (x1 * x1 - c) * (x * x - c);
//            if (v < 0) {//x1与x不同边
//                x2 = x;
//            } else {//x1与x同边
//                x1 = x;
//            }
            //始终都是 x 与 x1(起始0) 在同一边，直接x1=x
            x1 = x;
            x = (x1 * x2 + c) / (x2 + x1);
            //这是优化过的，去掉了中间量x，有点问题
//            x1 = (x1 * x2 + c) / (x2 + x1);
            cnt++;
        }

        x = (x1 * x2 + c) / (x2 + x1);
        cnt++;
        X.lg("sqrt6.cnt=", cnt);
        return x;

    }

    /**
     * 平方加中点迭代法
     *
     * @param c
     * @return
     */
    //y=sqrt(x) => y^2=x
    //利用 x^2 - c = 0 加 中点来计算
    public static double sqrt7(double c) {
        X.lg("sqrt7:", c);
        int cnt = 0;
        //一定要选取合适的初值
        double lo = 0;
        //对于小数有问题，+1，解决
        double hi = c + 1;
        double x = (lo + hi) / 2;
        cnt++;
        double err = 1e-15;

        while (Math.abs(x * x - c) > err) {
            if (x * x > c) {//大于就缩小最高值
                hi = x;
            } else {//小于就增大最小值
                lo = x;
            }
            x = (lo + hi) / 2;
            cnt++;
        }
        x = (lo + hi) / 2;
        cnt++;
        X.lg("sqrt7.cnt=", cnt);
        return x;
    }


    public static void main(String[] args) {
        X.lg(sqrt1(2));//4次循环
        X.lg(sqrt1(0));
        X.lg(sqrt1(5));//6次循环
        X.lg(sqrt2(2));//4次循环
        X.lg(sqrt2(0));
        X.lg(sqrt2(5));//6次循环
        X.lg(sqrt3(2));//4次循环
        X.lg(sqrt3(0));
        X.lg(sqrt3(5));//6次循环
        X.lg(sqrt4(2));//1000次循环
        X.lg(sqrt4(0));//计算0是错误的 NaN
        X.lg(sqrt4(5));//1000次循环
        X.lg(sqrt5(2));
        X.lg(sqrt5(0));
        X.lg(sqrt5(5));
        X.lg(sqrt6(2));
        X.lg(sqrt6(0));
        X.lg(sqrt6(5));

        //测试小数
        X.lg(sqrt1(0.25));
        X.lg(sqrt2(0.25));
        X.lg(sqrt3(0.25));
        X.lg(sqrt4(0.25));
        X.lg(sqrt5(0.25));
        X.lg(sqrt6(0.25));

        X.lg(sqrt7(0));
        X.lg(sqrt7(2));
        X.lg(sqrt7(5));
        X.lg(sqrt7(0.25));

        X.lg(sqrt1(1));
        X.lg(sqrt2(1));
        X.lg(sqrt3(1));
        X.lg(sqrt4(1));
        X.lg(sqrt5(1));
        X.lg(sqrt6(1));
        X.lg(sqrt7(1));
//        X.lg(Math.abs(Double.NaN));
//        X.lg(Double.NaN);
//        X.lg(1d/0.0d);
//        X.lg(0d/0.0d);
//        X.lg(0/0);
    }

}

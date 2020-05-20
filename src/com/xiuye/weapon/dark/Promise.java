package com.xiuye.weapon.dark;


import com.xiuye.util.log.XLog;

/**
 * Promise 设计纲要
 * 1.Promise 必须有 结果 ，有 错误 就处理 错误
 * 2.Promise 调用了 then 处理结果 中，遇到错误，
 *      应可以延迟 处理！新的错误 可以 覆盖旧的异常 error
 *      public Promise(RESULT result,Throwable error)
 *      可以把上一个 Promise未处理的错误继承和传递下来再处理
 * 3.Result值不具有继承效果，一调用 then就处理掉，没有延迟效果
 * 4.Promise每调用一次 then except 等，都会返回新的 Promise对象。
 *  这样有利于线程安全。
 * 5.except 表示处理异常，运行过程中没有新的异常产生不应将上次的异常继续
 *      传递下去，应及时清理掉
 * 6.except 和 then 的互相下次调用 应该毫 无约束
 * @param <RESULT>
 */
public class Promise<RESULT> {
    private RESULT result;
    private Throwable error;

    public Promise(RESULT result){
        this.result = result;
    }

    //继承上一个的Promise未处理的error
    public Promise(RESULT result,Throwable error){
        this.result = result;
        this.error = error;
    }

    //直接调用构造函数的返回新的Promise
    public Promise(Callback<RESULT> callback){
        catchExec(()->{
            result = callback.call();
        });
    }
    public <INPUT> Promise(CallbackWithParam<RESULT,INPUT> callback, INPUT in){
        catchExec(()-> {
            result = callback.call(in);
        });
    }
    public Promise(RunnableWithParam<RESULT> callback, RESULT in){
        catchExec(()-> {
            callback.run(in);
        });
    }
    public Promise(Runnable callback){
        catchExec(()-> {
            callback.run();
        });
    }

    public <R> Promise<R> then(Callback<R> callback){
        return new Promise<>(catchExec(()->callback.call()),error);
    }

    public <R> Promise<R> then(CallbackWithParam<R,RESULT> callback){
        return new Promise<>(catchExec(()->callback.call(result)),error);
    }

    //没有返回类型的,（相当于）继承了RESULT 类型！
    public Promise<RESULT> then(RunnableWithParam<RESULT> callback){
        return new Promise<>(catchExec(()->callback.run(result)),error);
    }

    public Promise<RESULT> then(Runnable callback){
        return new Promise<>(catchExec(()->callback.run()),error);
    }

//    private <R> Promise<R> thenInherit(Promise<R> pro){
//        if(pro.error == null && error != null){
//            //继承上一个的Promise未处理的error
//            pro.error = error;
//
//        }
//
//        return pro;
//    }

    //传入都是空！所以随便返回什么类型
    public <R> Promise<R> except(Callback<R> callback){
        return new Promise<>(catchExec(()->{
            R r = callback.call();
            //捕获异常后将上一步的异常 清理掉
            //能到这一步，表示编译完美无误的处理异常！
            //error 是 类中局部变量，好处是不会被 final 约束了
            error = null;
            return r;
        }),error);
    }
    //input and return 都有；传入进去后，再次有error的话就传给下一个新的
    public <R> Promise<R> except(CallbackWithParam<R,Throwable> callback){
        return new Promise<>(catchExec(()->{
            R r = callback.call(error);
            //能到这一步，表示编译完美无误的处理异常！
            error= null;
            return r;
        }),error);
    }

    public Promise<RESULT> except(RunnableWithParam<Throwable> callback){
        return new Promise<>(catchExec(()->{
            callback.run(error);
            //能到这一步，表示编译完美无误的处理异常！
            error = null;
        }),error);
    }

    public Promise<RESULT> except(Runnable callback){
        return new Promise<>(catchExec(()->{
            callback.run();
            //能到这一步，表示编译完美无误的处理异常！
            error = null;
        }),error);
    }

//    public Promise<RESULT> exceptInherit(Promise<RESULT> pro){
//        if(pro.result == null && result!= null){
//            pro.result = result;
//            //捕获异常后将上一步的异常 清理掉
//            error = null;
//        }
//        return pro;
//    }

    /**
     * 捕获代码执行过程中的异常！
     * @param run
     * @return
     */
    private RESULT catchExec(Runnable run){
        try {
            run.run();
        }catch (Throwable e) {
            error = e;
        }
        return null;
    }

    /**
     * 捕获代码执行过程中的异常！
     * @param run
     * @param <R>
     * @return
     */

    private<R> R catchExec(Callback<R> run){
        try {
            return run.call();
        }catch (Throwable e) {
            error = e;
        }
        return null;
    }

    /**
     * R call (I)
     * @param <R>
     * @param <I>
     */
    public static interface CallbackWithParam<R,I>{
        R call(I in);
    }

    /**
     * void run(I)
     * @param <I>
     */
    public static interface RunnableWithParam<I>{
        void run(I in);
    }

    /**
     * R call()
     * @param <R>
     */
    public static interface Callback<R>{
        R call();
    }


}


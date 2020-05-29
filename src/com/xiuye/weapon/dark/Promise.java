package com.xiuye.weapon.dark;

import com.xiuye.util.cls.XType;


/**
 * Promise 设计纲要
 * 1.Promise 必须有 结果，即使计算过程中有异常错误，结果应该为null， 并传递给 下一个新的Promise，有 错误
 * 就处理 错误
 * 2.then 处理Promise的结果
 * 3.except处理Promise或者then中的异常错误等
 * 4.Promise 调用了 then
 * 处理结果 中，遇到错误， 应可以延迟 处理！新的错误 可以 覆盖旧的异常 error public Promise(RESULT
 * result,Throwable error) 可以把上一个 Promise未处理的错误继承和传递下来再处理
 * 5.Result值不具有继承效果，一调用
 * then就处理掉，没有延迟处理效果
 * 6.Promise每调用一次 then except 等，都会返回新的 Promise对象。 这样有利于线程安全。
 * 7.except 表示处理异常，运行过程中没有新的异常产生不应将上次的异常继续 传递下去，应及时清理掉 7.except 和 then 的互相下次调用
 * 应该毫 无任何约束，十分流畅
 * 8.内部变量result保存callback的结果，error保存callback运行中产生的异常 或者未处理的异常
 * 9.世界上，每次执行Promise都是直接执行的！
 * 10.this promise is only single thread
 * 11.result不仅是当前Promise计算的结果，
 * 更是传递给下一个的入参，其作用带有next属性
 * 12.只满足就近匹配，前一个的Promise的计算结果是后一个
 * Promise的传入值！十分重要。
 * 13.添加分支结构！
 * @param <RESULT> 结果类型
 */
public class Promise<RESULT> {


    // 一般是这步的计算结果，传递给下一步
    private RESULT result;
    // 这步产生的错误
    private Throwable error;

    // 异常必须处理后才能进行下一步
    private static boolean exIndeed = true;

    public Promise() {
    }

    /**
     * 直接传入结果的Promise 其实就相当于上一个结果传递给下一个！
     *
     * @param result
     */
    public Promise(RESULT result) {
        this.result = result;
    }

    /**
     * 传入callback返回的结果，并传入callback运行中产生error 或者未处理的error
     *
     * @param result 新的callback计算的结果
     * @param error  传入callback调用产生的error 或者继承上一个的Promise未处理的error
     */
    public Promise(RESULT result, Throwable error) {
        this.result = result;
        this.error = error;
    }

    /**
     * 直接传递错误
     *
     * @param error
     */
    public Promise(Throwable error) {
        this.error = error;
    }

    /**
     * 直接调用构造函数的返回新的Promise T call()
     *
     * @param callback
     */
//    public Promise(ReturnCallbackNoParam<RESULT> callback) {
//        catchExec(() -> result = callback.rcv());
//    }

//    public Promise(ReturnCallbackNoParam<RESULT> callback, Throwable error) {
////        catchExec(() -> result = callback.rcv());
//        this(callback);
//        error = error;
////        this(catchExec(() ->callback.rcv()),error);
//    }


    /**
     * R call(I)
     *
     * @param callback
     * @param in
     * @param <INPUT>
     */
//    public <INPUT> Promise(ReturnCallbackWithParam<RESULT, INPUT> callback, INPUT in) {
//        catchExec(() -> result = callback.rci(in));
//    }

//    public <INPUT> Promise(ReturnCallbackWithParam<RESULT, INPUT> callback, INPUT in, Throwable error) {
////        catchExec(() -> result = callback.rci(in));
//        this(callback, in);
//        error = error;
//    }


    /**
     * void call(I)
     *
     * @param callback
     * @param in
     */
//    public Promise(VoidCallbackWithParam<RESULT> callback, RESULT in) {
//        catchExec(() -> callback.vci(in));
//    }

//    public Promise(VoidCallbackWithParam<RESULT> callback, RESULT in, Throwable error) {
////        catchExec(() -> callback.vci(in));
//
//        this(callback, in);
//        error = error;
//
//    }

    /**
     * void call()
     *
     * @param callback
     */
//    public Promise(VoidCallbackNoParam callback) {
//        catchExec(() -> callback.vcv());
//    }

//    public Promise(VoidCallbackNoParam callback, Throwable error) {
////        catchExec(() -> callback.vcv());
//        this(callback);
//        error = error;
//    }


    /**
     * 不处理上一个Promise返回的结果，
     * 并返回新的结果给 下一个Promise
     * void call()
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> then(ReturnCallbackNoParam<R> callback) {
        return new Promise<>(catchExec(() -> callback.rcv()), error);
    }

    /**
     * 处理上一个Promise返回的结果,
     * 并返回新的结果给 下一个Promise
     * R call(I)
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> then(ReturnCallbackWithParam<R, RESULT> callback) {
        return new Promise<>(catchExec(() -> callback.rci(result)), error);
    }

    // 没有返回类型的,（相当于）继承了RESULT 类型！

    /**
     * 处理上一个Promise返回的结果,
     * 不返回新的结果给 下一个Promise
     * void call(I)
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> then(VoidCallbackWithParam<RESULT> callback) {
        return new Promise<>(catchExec(() -> callback.vci(result)), error);
    }

    /**
     * 不处理上一个Promise返回的结果,
     * 不返回新的结果给 下一个Promise
     * void call()
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> then(VoidCallbackNoParam callback) {
        return new Promise<>(catchExec(() -> callback.vcv()), error);
    }

    /**
     * 最终
     * equals finally
     * void call()
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> lastly(ReturnCallbackNoParam<R> callback) {
        return then(callback);
    }

    /**
     * 最终
     * equals finally
     * R call(I)
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> lastly(ReturnCallbackWithParam<R, RESULT> callback) {
        return then(callback);
    }

    // 没有返回类型的,（相当于）继承了RESULT 类型！

    /**
     * 最终
     * equals finally
     * void call(I)
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> lastly(VoidCallbackWithParam<RESULT> callback) {
        return then(callback);
    }

    /**
     * 最终
     * equals finally
     * void call()
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> lastly(VoidCallbackNoParam callback) {
        return then(callback);
    }

    /**
     * 判断result是否不等于null
     * 也就是存在啊
     *
     * @return
     */
    public boolean exist() {
        return result != null;
    }


    /**
     * 上一个Promise结果存在（!=null）则执行！
     * 不处理上一个Promise返回的结果，
     * 并返回新的结果给 下一个Promise
     * void call()
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> exist(ReturnCallbackNoParam<R> callback) {
        //为null就不必再次传入result了
        return exist() ? then(callback) : new Promise<>(error);
    }

    /**
     * 上一个Promise结果存在（!=null）则执行！
     * 处理上一个Promise返回的结果,
     * 并返回新的结果给 下一个Promise
     * R call(I)
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> exist(ReturnCallbackWithParam<R, RESULT> callback) {
        return exist() ? then(callback) : new Promise<>(error);
    }

    // 没有返回类型的,（相当于）继承了RESULT 类型！

    /**
     * 上一个Promise结果存在（!=null）则执行！
     * 处理上一个Promise返回的结果,
     * 不返回新的结果给 下一个Promise
     * void call(I)
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> exist(VoidCallbackWithParam<RESULT> callback) {
        return exist() ? then(callback) : new Promise<>(error);
    }

    /**
     * 上一个Promise结果存在（!=null）则执行！
     * 不处理上一个Promise返回的结果,
     * 不返回新的结果给 下一个Promise
     * void call()
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> exist(VoidCallbackNoParam callback) {
        return exist() ? then(callback) : new Promise<>(error);
    }

    /**
     * 存在result并且是boolean类型，
     * 并进行转化
     *
     * @return
     */
    public boolean truely() {
        boolean b = false;
        if (exist()) {
            b = true;
            if (result instanceof Boolean) {
                b = XType.cast(result);
            }
        }
        return b;
    }

    private <I> boolean parseBoolean(I t) {
        if (t == null) {
            return false;
        } else {
            boolean b = true;
            if (t instanceof Boolean) {
                b = XType.cast(t);
            }
            return b;
        }
    }

    /**
     * if result == true
     * void call()
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> truely(ReturnCallbackNoParam<R> callback) {
        return truely() ? then(callback) : new Promise<>(error);
    }

    /**
     * if result == true
     * R call(I)
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> truely(ReturnCallbackWithParam<R, RESULT> callback) {
        return truely() ? then(callback) : new Promise<>(error);
    }

    // 没有返回类型的,（相当于）继承了RESULT 类型！

    /**
     * if result == true
     * void call(I)
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> truely(VoidCallbackWithParam<RESULT> callback) {
        return truely() ? then(callback) : new Promise<>(error);
    }

    /**
     * if result == true
     * void call()
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> truely(VoidCallbackNoParam callback) {
        return truely() ? then(callback) : new Promise<>(error);
    }

    /**
     * if result == false
     * void call()
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> falsely(ReturnCallbackNoParam<R> callback) {
        return !truely() ? then(callback) : new Promise<>(error);
    }

    /**
     * if result == false
     * R call(I)
     *
     * @param callback lambda代码
     * @param <R>      处理异常后返回的结果
     * @return 新的Promise对象
     */
    public <R> Promise<R> falsely(ReturnCallbackWithParam<R, RESULT> callback) {
        return !truely() ? then(callback) : new Promise<>(error);
    }

    // 没有返回类型的,（相当于）继承了RESULT 类型！

    /**
     * if result == false
     * void call(I)
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> falsely(VoidCallbackWithParam<RESULT> callback) {
        return !truely() ? then(callback) : new Promise<>(error);
    }

    /**
     * if result == false
     * void call()
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> falsely(VoidCallbackNoParam callback) {
        return !truely() ? then(callback) : new Promise<>(error);
    }


    //extension API
    //没有优先级额！ 因为方便实现,就用就近原则吧！
    public Promise<Boolean> and(boolean flag) {
        return resolve(truely() && flag, error);
    }


    public Promise<Boolean> and(ReturnCallbackNoParam<Boolean> callback) {
//        return !truely() ? then(callback) : new Promise<>(error);
        return resolve(truely() && parseBoolean(catchExec(() -> callback.rcv())), error);
    }


    public Promise<Boolean> and(ReturnCallbackWithParam<Boolean, RESULT> callback) {
//        return !truely() ? then(callback) : new Promise<>(error);
//        return resolve(callback,result,error);
        return resolve(truely() && parseBoolean(catchExec(() -> callback.rci(result))), error);
    }


    public Promise<Boolean> and(VoidCallbackWithParam<RESULT> callback) {
////        return !truely() ? then(callback) : new Promise<>(error);
        return resolve(truely() && parseBoolean(catchExec(() -> callback.vci(result))), error);
    }


    public Promise<Boolean> and(VoidCallbackNoParam callback) {
//        return !truely() ? then(callback) : new Promise<>(error);
        return resolve(truely() && parseBoolean(catchExec(() -> callback.vcv())), error);
    }


    public Promise<Boolean> or(boolean flag) {
        return resolve(truely() || flag, error);
    }

    public Promise<Boolean> or(ReturnCallbackNoParam<Boolean> callback) {
        return resolve(truely() || parseBoolean(catchExec(() -> callback.rcv())), error);
    }


    public Promise<Boolean> or(ReturnCallbackWithParam<Boolean, RESULT> callback) {
        return resolve(truely() || parseBoolean(catchExec(() -> callback.rci(result))), error);
    }


    public Promise<Boolean> or(VoidCallbackWithParam<RESULT> callback) {
        return resolve(truely() || parseBoolean(catchExec(() -> callback.vci(result))), error);
    }


    public Promise<Boolean> or(VoidCallbackNoParam callback) {
        return resolve(truely() || parseBoolean(catchExec(() -> callback.vcv())), error);
    }

    public Promise<Boolean> not() {
        return resolve(!truely(), error);
    }

    public Promise<Boolean> not(ReturnCallbackNoParam<Boolean> callback) {
        return resolve(!parseBoolean(catchExec(() -> callback.rcv())), error);
    }


    public Promise<Boolean> not(ReturnCallbackWithParam<Boolean, RESULT> callback) {
        return resolve(!parseBoolean(catchExec(() -> callback.rci(result))), error);
    }


    public Promise<Boolean> not(VoidCallbackWithParam<RESULT> callback) {
        return resolve(!parseBoolean(catchExec(() -> callback.vci(result))), error);
    }

    public Promise<Boolean> not(VoidCallbackNoParam callback) {
        return resolve(!parseBoolean(catchExec(() -> callback.vcv())), error);
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

    // 传入都是空！所以随便返回什么类型

    public boolean errorExist() {
        return error != null;
    }

    /**
     * 有错误就执行，没有错误，就跳过
     * R call() 处理上一个Promise的错误！ 不接受上一个Promise的错误并返回新的Promise！
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> except(ReturnCallbackNoParam<RESULT> callback) {
//        return errorExist()
//                ? new Promise<>(errorHandler(() -> callback.rcv()), error)
//                : new Promise<>(result, error);
//        return new Promise<>(errorHandler(() -> callback.rcv()), error);
        return resolve(errorExist()?errorHandler(() -> callback.rcv()):result,error);
    }

    // input and return 都有；传入进去后，再次有error的话就传给下一个新的

    /**
     * 有错误就执行，没有错误，就跳过
     * R call(I) 处理上一个Promise的错误！ 接受上一个Promise的错误并返回新的Promise！
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> except(ReturnCallbackWithParam<RESULT, Throwable> callback) {
//        return errorExist()
//                ? new Promise<>(errorHandler(() -> callback.rci(error)), error)
//                : new Promise<>(result, error);
//        return new Promise<>(errorHandler(() -> callback.rci(error)), error);
          return resolve(errorExist()?errorHandler(() -> callback.rci(error)):result,error); 
    }

    /**
     * 有错误就执行，没有错误，就跳过
     * void call(I) 处理上一个Promise的错误！ 接受上一个Promise的错误并返回新的Promise！
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> except(VoidCallbackWithParam<Throwable> callback) {
//        return errorExist()
//                ? new Promise<>(errorHandler(() -> callback.vci(error)), error)
//                : new Promise<>(result, error);
//        return new Promise<>(errorHandler(() -> callback.vci(error)), error);
          return resolve(errorExist()?errorHandler(() -> callback.vci(error)):result,error);
    }

    /**
     * 有错误就执行，没有错误，就跳过
     * void call() 处理上一个Promise的错误！ 接受上一个Promise的错误并返回新的Promise！
     *
     * @param callback lambda代码
     * @return 新的Promise对象
     */
    public Promise<RESULT> except(VoidCallbackNoParam callback) {
//        return errorExist()
//                ? resolve(errorHandler(() -> callback.vcv()), error)
//                : resolve(result, error);
              
//        return new Promise<>(errorHandler(() -> callback.vcv()), error);
    	return resolve(errorExist()?errorHandler(() -> callback.vcv()):result,error);
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
     *
     * @param callback
     * @return
     */
    private RESULT catchExec(VoidCallbackNoParam callback) {
        ifError();
        try {
            callback.vcv();
        } catch (Throwable e) {
            error = e;
        }
        // error 的时候 ，result是不存在的！所有返回null是正确的！
        return null;
    }

    /**
     * 捕获代码执行过程中的异常！
     *
     * @param callback
     * @param <R>
     * @return
     */
    private <R> R catchExec(ReturnCallbackNoParam<R> callback) {
        ifError();
        try {
            return callback.rcv();
        } catch (Throwable e) {
            error = e;
        }
        return null;
    }

    /**
     * 捕获代码执行过程中的异常！ for except 的 异常处理代码
     *
     * @param callback
     * @return
     */
    private RESULT errorHandler(VoidCallbackNoParam callback) {
        try {
            callback.vcv();
            // 捕获异常后将上一步的异常 清理掉
            // 能到这一步，表示编译完美无误的处理异常！
            // error 是 类中局部变量，好处是不会被 final 约束了
            error = null;
        } catch (Throwable e) {
            error = e;
        }
        // error 的时候 ，result是不存在的！所有返回null是正确的！
        return null;
    }

    /**
     * 捕获代码执行过程中的异常！ for except 的 异常处理代码
     * 错误处理后返回正常值
     *
     * @param callback
     * @param <R>
     * @return
     */
    private <R> R errorHandler(ReturnCallbackNoParam<R> callback) {
        try {
            R r = callback.rcv();
            // 捕获异常后将上一步的异常 清理掉
            // 能到这一步，表示编译完美无误的处理异常！
            // error 是 类中局部变量，好处是不会被 final 约束了
            error = null;
            return r;
        } catch (Throwable e) {
            error = e;
        }
        return null;
    }

    /**
     * 检查错误，用于错误是否必须显示、及时 调用except 处理掉 如果错误存在并且及时错误开启的话
     *
     * @param callback
     */
    private void ifError(VoidCallbackWithParam<Throwable> callback) {
        if (exIndeed && error != null) {
            callback.vci(error);
        }
    }

    private void ifError() {
        ifError((e) -> {
            throw new RuntimeException("Some errors occur!" + "Please use \"except\" to handle error next step!",
                    error);
        });
    }

    //必须自己实现与其他类库无关的接口，哪怕是SDK 标准库
    //否则 将面临 传参 的 一些莫名其妙的错误！

    /**
     * R call (I)
     *
     * @param <R>
     * @param <I>
     */
    public interface ReturnCallbackWithParam<R, I> {
        R rci(I in);
    }

    /**
     * void callback(I)
     *
     * @param <I>
     */
    public interface VoidCallbackWithParam<I> {
        void vci(I in);
    }

    public interface VoidCallbackNoParam {
        void vcv();
    }

    /**
     * R call()
     *
     * @param <R>
     */
    public interface ReturnCallbackNoParam<R> {
        R rcv();
    }

    public Throwable getError() {
        return error;
    }

    public RESULT get() {
        return result;
    }

    public static void implyErrorHandler(boolean implicit) {
        exIndeed = !implicit;
    }


    public static <R> Promise<R> resolve() {
        return new Promise<>();
    }

    /**
     * give value promise
     *
     * @param r
     * @param <R>
     * @return
     */
    public static <R> Promise<R> resolve(R r) {
        return new Promise<>(r);
    }

    private static <R,E extends Throwable> Promise<R> resolve(R r, E error) {
        return new Promise<>(r, error);
    }

    /**
     * since here,so don't have input
     *
     * @param callback
     * @param <R>
     * @return
     */
//    public static <R> Promise<R> resolve(ReturnCallbackNoParam<R> callback) {
//        return new Promise<>(callback);
//    }
//
//    public static <R> Promise<R> resolve(ReturnCallbackNoParam<R> callback, Throwable error) {
//        return new Promise<>(callback, error);
//    }
//
//    public static <R, I> Promise<R> resolve(ReturnCallbackWithParam<R, I> callback, I rlt) {
//        return new Promise<>(callback, rlt);
//    }
//
//    public static <R, I> Promise<R> resolve(ReturnCallbackWithParam<R, I> callback, I rlt, Throwable error) {
//        return new Promise<>(callback, rlt, error);
//    }
//
//    // 没有返回类型的,（相当于）继承了RESULT 类型！
//    //向Promise传递result
//    public static <R> Promise<R> resolve(VoidCallbackWithParam<R> callback, R rlt) {
//        return new Promise<>(callback, rlt);
//    }
//
//    public static <R> Promise<R> resolve(VoidCallbackWithParam<R> callback, R rlt, Throwable error) {
//        return new Promise<>(callback, rlt, error);
//    }
//
//    public static Promise resolve(VoidCallbackNoParam callback) {
//        return new Promise(callback);
//    }
//
//    public static Promise resolve(VoidCallbackNoParam callback, Throwable error) {
//        return new Promise(callback, error);
//    }


    /**
     * exception result promise
     *
     * @param e
     * @return
     */
    public static <R extends Throwable> Promise<R> reject(R e) {
        return new Promise<>(e);
    }

    public static <R> Promise<R> of(){
    	return resolve();
    }
    
    public static <R> Promise<R> of(R t){
    	return resolve(t);
    }
    
    public static <R,E extends Throwable> Promise<R> of(R r,E error){
    	return resolve(r, error);
    }
    

    public <R> ProgramPromise<R> begin() {
      return new ProgramPromise<R>(XType.list());
    }
   


}

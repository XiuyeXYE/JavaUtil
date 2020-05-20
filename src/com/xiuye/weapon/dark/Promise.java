package com.xiuye.weapon.dark;


public class Promise<RESULT> {
    private RESULT result;

    public Promise(Callback<RESULT> callback){
        result = callback.call();
    }
    public <INPUT> Promise(CallbackWithParam<RESULT,INPUT> callback, INPUT in){
        result = callback.call(in);
    }
    public Promise(RunnableWithParam<RESULT> callback, RESULT in){
        callback.run(in);
    }

    public <R> Promise<R> then(CallbackWithParam<R,RESULT> callback){
        return new Promise<>(callback,result);
    }

    public Promise<RESULT> then(RunnableWithParam<RESULT> callback){
        return new Promise<RESULT>(callback,result);
    }


    
    public static interface CallbackWithParam<R,I>{
        R call(I in);
    }
    public static interface RunnableWithParam<I>{
        void run(I in);
    }
    public static interface Callback<R>{
        R call();
    }

}


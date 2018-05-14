package com.huitong.deal.https;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Created by Zheng on 2018/5/14.
 */

public class ErrorTransformer<T> implements ObservableTransformer{

    @Override
    public ObservableSource apply(Observable upstream) {
        //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
        return (Observable<T>) upstream.map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
    }
}

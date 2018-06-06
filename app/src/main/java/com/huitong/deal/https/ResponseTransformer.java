package com.huitong.deal.https;

import com.huitong.deal.beans.HttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/5/17.
 */

public class ResponseTransformer {

    public static ObservableTransformer changeThread(){
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * ObservableTransformer<upstream,downstream>
     * 用于将上游的流upstream转换为下游的流downstream
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResult<T>, T> handleResult() {
        return new ObservableTransformer<HttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResult<T>> upstream) {
                return upstream
                        .onErrorResumeNext(new ErrorResumeFunction<T>())
                        .flatMap(new ResponseFunction<T>());
            }
        };
    }

    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends HttpResult<T>>>{

        @Override
        public ObservableSource<? extends HttpResult<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    private static class ResponseFunction<T> implements Function<HttpResult<T>, ObservableSource<T>>{

        @Override
        public ObservableSource<T> apply(HttpResult<T> tHttpResult) throws Exception {
            String status= tHttpResult.getStatus();
            String description= tHttpResult.getDescription();
            if ("success".equals(status)){
                if (tHttpResult.getData()== null){
                    return (ObservableSource<T>) Observable.just("null");
                }
                return Observable.just(tHttpResult.getData());
            }
            return Observable.error(new ApiException(500, description));
        }
    }

}

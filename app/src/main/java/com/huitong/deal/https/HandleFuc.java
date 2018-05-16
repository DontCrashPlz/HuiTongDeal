package com.huitong.deal.https;

import com.huitong.deal.beans.HttpResult;

import io.reactivex.functions.Function;
@Deprecated
public class HandleFuc<T> implements Function<HttpResult<T>, T> {
    @Override
    public T apply(HttpResult<T> response) throws Exception {
        //response中code码不会0 出现错误
        if (!response.isSuccess())
            throw new RuntimeException(response.getStatus() + "" + response.getDescription() != null ? response.getDescription() : "");
        return response.getData();
    }
}
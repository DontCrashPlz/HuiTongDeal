package com.huitong.deal.https;

/**
 * Created by Zheng on 2018/5/30.
 */

public class HttpUtils {

    public static String parseThrowableMsg(Throwable throwable){
        if (throwable instanceof ApiException){
            ApiException exception= (ApiException) throwable;
            if (exception.getCode()== 1000) return "网络请求发生未知错误";
            else if (exception.getCode()== 1001) return "网络数据解析错误";
            else if (exception.getCode()== 1002) return "网络连接失败，请检查网络";
            else if (exception.getCode()== 1003) return "网络协议错误";
            else {
                if (exception.getDisplayMessage()== null || exception.getDisplayMessage().trim().length()< 1)
                    return "网络请求异常";
                return exception.getDisplayMessage();
            }
        }
        return "网络请求发生未知错误";
    }

}

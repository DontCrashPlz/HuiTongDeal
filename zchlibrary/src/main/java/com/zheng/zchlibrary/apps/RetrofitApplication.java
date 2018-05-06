package com.zheng.zchlibrary.apps;

import android.app.*;

import com.zheng.zchlibrary.https.NovateCookieManger;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.SharedPrefUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zheng on 2017/12/14.
 */

public class RetrofitApplication extends Application {

    public static final String IP_ADDRESS="ip_address";
    public static final String PORT_NUMBER="port_number";

    /**--------------------SingleInstance-----------------------*/

    private static RetrofitApplication mSingleInstance;

    public static RetrofitApplication getInstance(){
        return mSingleInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSingleInstance= this;

        initHttpInstance();

    }

    /**--------------------Retrofit Config-----------------------*/

    private Retrofit mRetrofit;
//    private ApiService apiService;
    private OkHttpClient mOkHttpClient;
    private String mBaseUrl;

    public String getBaseUrl(){
        LogUtil.d("mBaseUrl",mBaseUrl);
        if (mBaseUrl== null)
            return "http://192.168.1.68:80/";
        return mBaseUrl;
    }

    public void initHttpInstance(){

        StringBuilder builder= new StringBuilder();
        builder.append("http://");
        builder.append(SharedPrefUtils.get(this,IP_ADDRESS,"192.168.1.68"));
        builder.append(":");
        builder.append(SharedPrefUtils.get(this,PORT_NUMBER,"80"));
        builder.append("/");

        LogUtil.d("ipAddress",builder.toString());
        mBaseUrl= builder.toString();

        mOkHttpClient= new OkHttpClient.Builder()
                .cookieJar(new NovateCookieManger(getApplicationContext()))
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .build();
//        mOkHttpClient= new OkHttpClient.Builder()
//                .addInterceptor(new SaveCookiesInterceptor())
//                .addInterceptor(new ReadCookiesInterceptor())
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .writeTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(5,TimeUnit.SECONDS)
//                .build();

        mRetrofit= new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        apiService= mRetrofit.create(ApiService.class);
    }

    /**----------------------else util Class---------------------*/


}

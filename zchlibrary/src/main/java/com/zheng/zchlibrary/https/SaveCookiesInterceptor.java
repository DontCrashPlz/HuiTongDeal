package com.zheng.zchlibrary.https;

import android.content.Context;
import android.content.SharedPreferences;

import com.zheng.zchlibrary.apps.RetrofitApplication;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

@Deprecated
public class SaveCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            SharedPreferences sp = RetrofitApplication.getInstance().getSharedPreferences("Cookies",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("cookies",cookies);
            editor.apply();
//            SharedPrefUtils.put(MyApplication.getInstance(),"cookies",cookies);
//            Preferences.getDefaultPreferences().edit()
//                    .putStringSet(Preferences.PREF_COOKIES, cookies)
//                    .apply();
        }

        return originalResponse;
    }
}
package com.huitong.deal.apps;

import android.widget.Toast;

import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.beans.UserInfoEntity;
import com.zheng.zchlibrary.apps.BaseApplication;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.ScreenUtils;
import com.zheng.zchlibrary.utils.SharedPrefUtils;

/**
 * Created by Zheng on 2018/4/20.
 */

public class MyApplication extends BaseApplication {

    public static final String TOKEN_TAG= "huitong_token";

    private static MyApplication mSingleInstance;

    public static MyApplication getInstance(){
        return mSingleInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSingleInstance= this;

        loadUser();

    }

    //根据本地保存的token加载用户
    public void loadUser(){
        if (loadLocalToken()){
//            loadUser(appToken);
        }
    }

    /********************** Token相关方法 *************************/
    //全局token值
    public static String appToken;
    //是否保存有token值
    public boolean isHadToken(){
        if (SharedPrefUtils.contains(getApplicationContext(), TOKEN_TAG)){
            String token= (String) SharedPrefUtils.get(getApplicationContext(), TOKEN_TAG, "");
            if (token!= null && token.length()>0){
                LogUtil.d("application token", token);
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    //设置token值
    public void setToken(String token){
        if (token== null || token.length()== 0){
            Toast.makeText(getApplicationContext(), "token值无效", Toast.LENGTH_SHORT).show();
            return;
        }
        if (SharedPrefUtils.contains(getApplicationContext(), TOKEN_TAG)){
            SharedPrefUtils.remove(getApplicationContext(), TOKEN_TAG);
        }
        SharedPrefUtils.put(getApplicationContext(), TOKEN_TAG, token);
    }
    //设置token值
    public String getToken(){
        if (!SharedPrefUtils.contains(getApplicationContext(), TOKEN_TAG)){
            Toast.makeText(getApplicationContext(), "未获得有效token值", Toast.LENGTH_SHORT).show();
            return "";
        }
        String token= (String) SharedPrefUtils.get(getApplicationContext(), TOKEN_TAG, "");
        if (token!= null && token.length()>0){
            LogUtil.d("loadLocalToken", "成功" + token);
            return token;
        }else {
            LogUtil.d("loadLocalToken", "本地token无效");
            Toast.makeText(getApplicationContext(), "未获得有效token值", Toast.LENGTH_SHORT).show();
            return "";
        }
    }
    //app启动时加载本地保存的token,返回值表示是否加载成功
    private boolean loadLocalToken(){
        if (SharedPrefUtils.contains(getApplicationContext(), TOKEN_TAG)){
            String token= (String) SharedPrefUtils.get(getApplicationContext(), TOKEN_TAG, "");
            if (token!= null && token.length()>0){
                LogUtil.d("loadLocalToken", "成功" + token);
                appToken= token;
                return true;
            }else {
                LogUtil.d("loadLocalToken", "本地token无效");
                return false;
            }
        }else {
            LogUtil.d("loadLocalToken", "没有本地token");
            return false;
        }
    }

    /********************** UserEntity相关方法 *************************/
    //全局UserEntity
    public static UserInfoDataEntity appUser;
    //是否存在全局UserEntity对象
    public boolean isHadUser(){
        if (appUser== null){
            return false;
        }
        if (appUser.getId()> 0){
            return true;
        }
        return false;
    }

}

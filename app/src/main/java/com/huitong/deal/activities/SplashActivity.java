package com.huitong.deal.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.UserInfoEntity;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.beans.UpdateInfoEntity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.SharedPrefUtils;
import com.zheng.zchlibrary.utils.UpdateInfoParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by Zheng on 2018/5/16.
 */

public class SplashActivity extends AppCompatActivity {

    private Disposable mDisposable;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE= 1;
    private static final String APP_LAUNCHED_TAG= "welcome_huitong";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //是否有写入文件的权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
//            //如果没有权限，提示申请原因并申请权限
//            Toast.makeText(this, "附件下载等功能需要写入文件权限", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }else {
            //todo 如果有权限，进入APP
            checkUpdateAndEnterApp();
        }

    }

    private void checkUpdateAndEnterApp(){
        Observable.timer(2, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<UpdateInfoEntity>>() {
                    @Override
                    public ObservableSource<UpdateInfoEntity> apply(Long aLong) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<UpdateInfoEntity>() {
                            @Override
                            public void subscribe(ObservableEmitter<UpdateInfoEntity> e) throws Exception {
                                URL url= new URL("http://47.92.28.185/app/update.xml");
                                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                                connection.setConnectTimeout(3000);
                                InputStream is= connection.getInputStream();
                                UpdateInfoEntity entity= UpdateInfoParser.getUpdataInfo(is);
                                e.onNext(entity);
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateInfoEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable= d;
                    }

                    @Override
                    public void onNext(UpdateInfoEntity value) {
                        LogUtil.e("update onNext", value.toString());
                        enterApp(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        UpdateInfoEntity entity= new UpdateInfoEntity();
                        entity.setVersion("unknow");
                        LogUtil.e("update onError", entity.toString());
                        enterApp(entity);
                    }

                    @Override
                    public void onComplete() {
                        UpdateInfoEntity entity= new UpdateInfoEntity();
                        entity.setVersion("unknow");
                        LogUtil.e("update onComplete", entity.toString());
                        enterApp(entity);
                    }
                });
    }

//    private Observable<Long> getTimerObservable(){
//        return Observable.timer(3, TimeUnit.SECONDS);
//    }

//    private Observable<UpdateInfoEntity> getUpdataInfoObservable(){
//        return Observable.create(new ObservableOnSubscribe<UpdateInfoEntity>() {
//            @Override
//            public void subscribe(ObservableEmitter<UpdateInfoEntity> e) throws Exception {
//                URL url= new URL("http://47.92.28.185/app/update/android.xml");
//                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
//                connection.setConnectTimeout(3000);
//                InputStream is= connection.getInputStream();
//                UpdateInfoEntity entity= UpdateInfoParser.getUpdataInfo(is);
//                e.onNext(entity);
//            }
//        });
//    }

    private void enterApp(UpdateInfoEntity entity){
        Intent intent= new Intent();
        if (SharedPrefUtils.contains(this, APP_LAUNCHED_TAG)//判断是否第一次打开app
                && (boolean)(SharedPrefUtils.get(this, APP_LAUNCHED_TAG, true))){
            if (MyApplication.getInstance().isHadToken()){//判断是否保存有token
                intent.setClass(this, HomeActivity.class);
            }else {
                intent.setClass(this, LoginActivity.class);
            }
        }else {
            intent.setClass(this, GuideViewActivity.class);
        }
        intent.putExtra("launcher_tag", 0);
        intent.putExtra("update_entity", entity);
        startActivity(intent);
        SharedPrefUtils.put(this, APP_LAUNCHED_TAG, true);
        if (mDisposable!= null && !mDisposable.isDisposed()){
            mDisposable.dispose();
        }
        finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode== MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                //todo 如果获得权限，进入APP
                checkUpdateAndEnterApp();
            }else {
                //todo 如果没有获得权限，Toast提示，而后进入APP
//                Toast.makeText(this, "授权失败，附件下载等功能可能受到影响！", Toast.LENGTH_LONG).show();
                checkUpdateAndEnterApp();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}

package com.zheng.zchlibrary.apps;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;
import com.zheng.zchlibrary.utils.LogUtil;

import java.io.File;

/**
 * Created by Zheng on 2018/4/14.
 */

public class BaseApplication extends Application {

    private static BaseApplication mSingleInstance;

    public static BaseApplication getInstance(){
        return mSingleInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSingleInstance= this;

        //init MyUncatchExceptionHandler
        MyUncatchExceptionHandler mUncatchExceptionHandler= MyUncatchExceptionHandler.getInstance();
        mUncatchExceptionHandler.init(this, getExternalFilesDir("").getPath());

        // 初始化ACache，可以考虑在此初始化时将缓存地址更改为磁盘SD卡(网络请求的缓存默认放在data/data目录下了)
        //此处初始化Acache创建放置图片缓存的文件夹
        File cacheDir = new File(getApplicationContext().getExternalCacheDir().getAbsolutePath()+"/myCache");
        ACache.get(cacheDir, 20 * 1024 * 1024, Integer.MAX_VALUE);

        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Toast.makeText(getApplicationContext(), "加载内核是否成功:"+b, Toast.LENGTH_SHORT).show();
                LogUtil.e("@@","加载内核是否成功:"+b);
            }
        });

    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        ActivityManager.getInstance().removeAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}

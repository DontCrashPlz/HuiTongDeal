package com.zheng.zchlibrary.apps;

import android.app.Application;

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

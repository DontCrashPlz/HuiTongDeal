package com.zheng.zchlibrary.apps;

import android.app.Activity;

import com.zheng.zchlibrary.utils.LogUtil;

import java.util.Stack;

/**
 * Created by Zheng on 2017/10/16.
 * 全局Activity管理器
 */

public class ActivityManager {

    private static ActivityManager mInstance= null;

    private static Stack<Activity> mActivityStack= new Stack<>();

    private ActivityManager(){}

    public static ActivityManager getInstance(){
        if (mInstance== null){
            synchronized (ActivityManager.class){
                if (mInstance== null){
                    mInstance= new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    public void addActivity(Activity activity){
        if (mActivityStack== null){
            mActivityStack= new Stack<>();
        }
        mActivityStack.add(activity);
    }

    public void removeActivity(Activity activity){
        if (activity!= null){
            mActivityStack.remove(activity);
            activity.finish();
        }

    }

    public void removeAll(){
        for (int i=0; i<mActivityStack.size(); i++){
            if (mActivityStack.get(i)!= null)
                mActivityStack.get(i).finish();
        }
        mActivityStack.clear();
    }

    /**
     * 移除栈顶之下的所有Activity
     */
    public void removeAllExceptTop(){
        LogUtil.e("remove当前Activity栈size", ""+ mActivityStack.size());
        int stackSize= mActivityStack.size();
        for (int i=0; i< stackSize; i++){
            if (i== (stackSize - 1)) continue;
            Activity recentActivity= mActivityStack.get(i);
            LogUtil.e("remove当前Activity栈位置 " + i , String.valueOf(recentActivity== null));
            if (recentActivity!= null){
                LogUtil.e("remove当前Activity栈位置 " + i , recentActivity.getClass().getSimpleName());
                mActivityStack.remove(mActivityStack);
                recentActivity.finish();
            }
        }
    }

    public void logActivityStackInfo(){
        for (int i=0; i<mActivityStack.size(); i++){
            if (mActivityStack.get(i)!= null)
                LogUtil.e("log当前Activity栈位置 " + i , mActivityStack.get(i).getClass().getSimpleName());
        }
    }

}

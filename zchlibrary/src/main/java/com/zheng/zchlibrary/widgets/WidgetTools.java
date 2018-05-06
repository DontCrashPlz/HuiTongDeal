package com.zheng.zchlibrary.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 全局工具类
 * 这里定义一些全局使用的工具方法
 * Created by Zheng on 2016/5/4.
 */
public class WidgetTools {

    /**
     * 显示自定义弹窗
     */
    public static void showCustomDialog(Context context, int ResourcesId, int themeId){
        View view = LayoutInflater.from(context).inflate(ResourcesId, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(context, themeId);
        dialog.setContentView(view);
        dialog.show();

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

}

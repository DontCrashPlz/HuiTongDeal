package com.zheng.zchlibrary.interfaces;

import android.support.annotation.Nullable;

/**
 * 所有显示数据页面的父接口
 * Created by Zheng on 2018/4/14.
 */

public interface IShowDataView extends IBaseView {
    void showProgressBar();
    void dismissProgressBar();
    void showErrorTip(@Nullable String errorMsg);
    void showEmptyTip();
}

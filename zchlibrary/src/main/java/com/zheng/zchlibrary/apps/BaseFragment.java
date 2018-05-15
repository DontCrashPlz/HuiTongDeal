package com.zheng.zchlibrary.apps;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zheng.zchlibrary.interfaces.*;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.NetworkUtil;
import com.zheng.zchlibrary.utils.ToastUtils;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Zheng on 2018/4/14.
 */

public abstract class BaseFragment extends Fragment implements IBaseView {

    private final String fragmentTag= this.getClass().getSimpleName();

    public CompositeDisposable compositeDisposable;

    public ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(fragmentTag, fragmentTag + "is Created!");
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        initProgressDialog();
    }

    @Override
    public void onDestroy() {
        LogUtil.e(fragmentTag, fragmentTag + "is Destroyed!");
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }

    @Override
    public void showLongToast(String msg) {
        if (msg!= null)
            ToastUtils.showLongToast(getRealContext(), msg);
    }

    @Override
    public void showShortToast(String msg) {
        if (msg!= null)
            ToastUtils.showShortToast(getRealContext(), msg);
    }

    @Override
    public Context getRealContext() {
        return getContext();
    }

    public void addNetWork(Disposable disposable){
//        if (!NetworkUtil.isNetworkAvailable(getRealContext())){
//            showShortToast("网络不可用");
//            return;
//        }
        try {
            if (compositeDisposable== null){
                compositeDisposable= new CompositeDisposable();
            }
            compositeDisposable.add(disposable);
        } catch (Exception e){
            Log.e("exception", e.toString());
            showShortToast("网络异常");
        }
    }

    public void clearNetWork(){
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public abstract void initProgressDialog();

    public void showDialog(){
        if (dialog!= null && !dialog.isShowing()){
            dialog.show();
        }
    }

    public void dismissDialog(){
        if (dialog!= null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

}

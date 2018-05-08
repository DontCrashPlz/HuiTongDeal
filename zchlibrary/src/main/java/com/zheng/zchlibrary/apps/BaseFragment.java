package com.zheng.zchlibrary.apps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.zheng.zchlibrary.interfaces.*;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.ToastUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Zheng on 2018/4/14.
 */

public class BaseFragment extends Fragment implements IBaseView {

    private final String fragmentTag= this.getClass().getSimpleName();

    public CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(fragmentTag, fragmentTag + "is Created!");
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
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
        if (compositeDisposable== null){
            compositeDisposable= new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}

package com.zheng.zchlibrary.apps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.zheng.zchlibrary.interfaces.IBaseView;
import com.zheng.zchlibrary.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Zheng on 2017/10/16.
 */

public class BaseActivity extends AppCompatActivity implements IBaseView {

    private final String ACTIVITY_TAG= this.getClass().getSimpleName();

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        LogUtil.e(ACTIVITY_TAG, ACTIVITY_TAG + " was Created.");
        ActivityManager.getInstance().addActivity(this);

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        LogUtil.e(ACTIVITY_TAG, ACTIVITY_TAG + " was Destroyed.");
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }

    @Override
    public void showLongToast(String msg) {
        if (msg!= null)
            Toast.makeText(getRealContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showShortToast(String msg) {
        if (msg!= null)
            Toast.makeText(getRealContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getRealContext() {
        return this;
    }
}

package com.zheng.zchlibrary.apps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.zheng.zchlibrary.interfaces.*;
import com.zheng.zchlibrary.utils.LogUtil;

/**
 * Created by Zheng on 2018/4/14.
 */

public class BaseFragment extends Fragment implements IBaseView {

    private final String fragmentTag= this.getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.e(fragmentTag, fragmentTag + "is Created!");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(fragmentTag, fragmentTag + "is Destroyed!");
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
        return getContext();
    }
}

package com.huitong.deal.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.huitong.deal.R;

/**
 * Created by Zheng on 2018/5/13.
 */

public class XiaDanDialog extends Dialog {

    public XiaDanDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xiadan_dialog);
    }
}

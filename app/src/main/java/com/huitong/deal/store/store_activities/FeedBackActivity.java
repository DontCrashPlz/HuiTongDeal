package com.huitong.deal.store.store_activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by Zheng on 2018/6/9.
 */

public class FeedBackActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private EditText mSuggestEt;
    private Button mCommitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_feedback);

        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("帮助反馈");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mSuggestEt= findViewById(R.id.feedback_edittext);
        mCommitBtn= findViewById(R.id.feedback_commit);
        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suggestStr= mSuggestEt.getText().toString().trim();
                if (suggestStr!= null && suggestStr.length()> 0){
                    showDialog();
                    commitFeedBack();
                }
            }
        });
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在提交..");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }

    private void commitFeedBack(){
        addNetWork(Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        dismissDialog();
                        showShortToast("提交成功，感谢您的反馈");
                        finish();
                    }
                }));
    }

}

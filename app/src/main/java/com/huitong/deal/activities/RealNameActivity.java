package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/16.
 */

public class RealNameActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;

    private EditText mNameEt;
    private EditText mIdCardEt;
    private RadioButton mManRbtn;
    private RadioButton mWomanRbtn;
    private EditText mNationEt;
    private EditText mAddressEt;

    private Button mCommitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname);

        initUI();
    }

    private void initUI() {
        mBackIv= (ImageView) findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= (TextView) findViewById(R.id.toolbar_title);
        mTitleTv.setText("实名认证");

        mNameEt= (EditText) findViewById(R.id.realname_et_name);
        mIdCardEt= (EditText) findViewById(R.id.realname_et_idcard);
        mManRbtn= (RadioButton) findViewById(R.id.realname_rbtn_man);
        mWomanRbtn= (RadioButton) findViewById(R.id.realname_rbtn_woman);
        mNationEt= (EditText) findViewById(R.id.realname_et_nation);
        mAddressEt= (EditText) findViewById(R.id.realname_et_address);

        mCommitBtn= (Button) findViewById(R.id.realname_btn_commit);
        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= mNameEt.getText().toString().trim();
                String idcard= mIdCardEt.getText().toString().trim();
                boolean isMan= mManRbtn.isChecked();
                boolean isWoman= mWomanRbtn.isChecked();
                String nation= mNationEt.getText().toString().trim();
                String address= mAddressEt.getText().toString().trim();

                if (name== null || name.length()< 1){
                    showShortToast("请输入您的姓名");
                    return;
                }
                if (idcard== null || idcard.length()< 1){
                    showShortToast("请输入您的身份证号");
                    return;
                }
                if (!isMan && !isWoman){
                    showShortToast("请选择性别");
                    return;
                }
                if (nation== null || nation.length()< 1){
                    showShortToast("请输入您的民族");
                    return;
                }

                String token= MyApplication.getInstance().getToken();
                String sex= isMan ? "1"  :"2";
                if (token!= null && token.length()> 0){
                    addNetWork(Network.getInstance().certifyRealName(
                            token,
                            name,
                            idcard,
                            sex,
                            nation,
                            address)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> stringHttpResult) throws Exception {
                            if ("error".equals(stringHttpResult.getStatus())){
                                showShortToast(stringHttpResult.getDescription());
                            }else if ("success".equals(stringHttpResult.getStatus())){
                                showShortToast("认证完成");
                                finish();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtil.d("throwable", throwable.toString());
                            showShortToast("网络请求失败");
                        }
                    }));
                }
            }
        });
    }
}

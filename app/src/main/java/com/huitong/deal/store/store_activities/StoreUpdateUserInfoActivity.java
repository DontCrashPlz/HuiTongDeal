package com.huitong.deal.store.store_activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/6/9.
 */

public class StoreUpdateUserInfoActivity extends BaseActivity {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private CircleImageView mHeadCiv;
    private TextView mUserNameTv;
    private EditText mNickNameEt;
    private FrameLayout mSexPanel;
    private TextView mSexTv;
    private FrameLayout mBirthdayPanel;
    private TextView mBirthdayTv;

    private Button mCommitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_update_userinfo);

        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("修改资料");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mHeadCiv= findViewById(R.id.update_userinfo_userhead);
        Glide.with(getRealContext()).load(MyApplication.appUser.getUserinfo().getHeadimgurl()).into(mHeadCiv);

        mUserNameTv= findViewById(R.id.update_userinfo_username);
        mUserNameTv.setText(MyApplication.appUser.getUsername());

        mNickNameEt= findViewById(R.id.update_userinfo_nickname);
        mSexPanel= findViewById(R.id.update_userinfo_panel_sex);
        mSexTv= findViewById(R.id.update_userinfo_sex);
        mSexPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSexChooseDialog();
            }
        });

        mBirthdayPanel= findViewById(R.id.update_userinfo_panel_birthday);
        mBirthdayTv= findViewById(R.id.update_userinfo_birthday);
        mBirthdayPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        mCommitBtn= findViewById(R.id.update_userinfo_commit);
        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNickNameEt.getText()== null || mNickNameEt.getText().toString().trim().length()< 1){
                    showShortToast("请设置您的昵称");
                    return;
                }
                if (mSexTv.getText()== null || mSexTv.getText().toString().trim().length()< 1){
                    showShortToast("请设置您的性别");
                    return;
                }
                if (mBirthdayTv.getText()== null || mBirthdayTv.getText().toString().trim().length()< 1){
                    showShortToast("请输入您的生日");
                    return;
                }
                String sex= "0";
                if ("保密".equals(mSexTv.getText().toString().trim())){
                    sex= "0";
                }else if ("男".equals(mSexTv.getText().toString().trim())){
                    sex= "1";
                }else if ("女".equals(mSexTv.getText().toString().trim())){
                    sex= "2";
                }
                addNetWork(Network.getInstance().updateUserInfo(
                        mBirthdayTv.getText().toString().trim(),
                        MyApplication.appToken,
                        sex,
                        mNickNameEt.getText().toString().trim())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(ResponseTransformer.<String>handleResult())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                dismissDialog();
                                showShortToast("修改成功");
                                finish();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissDialog();
                                showShortToast(HttpUtils.parseThrowableMsg(throwable));
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                showDialog();
                            }
                        }));
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

    private String[] sexArry = new String[]{"保密", "男", "女"};// 性别选择
    private void showSexChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                mSexTv.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    private void showDatePickerDialog() {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作
                        mBirthdayTv.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                    }
                }
                // 设置初始日期
                , Calendar.getInstance().get(Calendar.YEAR)
                , Calendar.getInstance().get(Calendar.MONTH)
                , Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                .show();
    }

}

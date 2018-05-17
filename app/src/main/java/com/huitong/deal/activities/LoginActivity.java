package com.huitong.deal.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.fragments.ForgetPasswordFragment;
import com.huitong.deal.fragments.LoginWithPasswordFragment;
import com.huitong.deal.fragments.LoginWithVerificationFragment;
import com.huitong.deal.fragments.SignInFragment;
import com.huitong.deal.fragments.SignInSuccessFragment;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.beans.UpdateInfoEntity;
import com.zheng.zchlibrary.utils.DownloadManager;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.Tools;

import java.io.File;

/**
 * Created by Zheng on 2018/4/12.
 */

public class LoginActivity extends BaseActivity {

    public static final int FRAGMENT_TAG_PASSWORD= 0;
    public static final int FRAGMENT_TAG_VERIFICATION= 1;
    public static final int FRAGMENT_TAG_SIGNIN= 2;
    public static final int FRAGMENT_TAG_SIGNSUCCESS= 3;
    public static final int FRAGMENT_TAG_FORGET= 4;

    private FragmentManager manager;

    private int mLauncherTag;
    private UpdateInfoEntity entity;
    private String mCurrentVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager= getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragment, LoginWithPasswordFragment.newInstance(""))
                .commit();

        //launcher_tag标识是从哪里启动的LoginActivity，0表示正常启动，1表示用户退出登录
        mLauncherTag= getIntent().getIntExtra("launcher_tag", 0);

        if (mLauncherTag!= 0) return;

        entity= (UpdateInfoEntity) getIntent().getSerializableExtra("update_entity");
        try {
            mCurrentVersion= Tools.getVersionName(getRealContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("unknow".equals(entity.getVersion())){
            LogUtil.d("update", "更新异常");
        }else {
            if (mCurrentVersion.equals(entity.getVersion())){
                LogUtil.d("update", "无需更新");
            }else{
                //todo 更新
                showUpdateDialog(entity);
            }
        }

    }

    @Override
    public void initProgressDialog() {

    }

    public void changeFragment(int fragmentTag){
        switch (fragmentTag){
            case 0:{//密码登录页面
                manager.beginTransaction()
                        .replace(R.id.fragment, LoginWithPasswordFragment.newInstance(""))
                        .commit();
                break;
            }
            case 1:{//验证码登录页面
                manager.beginTransaction()
                        .replace(R.id.fragment, LoginWithVerificationFragment.newInstance(""))
                        .commit();
                break;
            }
            case 2:{//注册页面
                manager.beginTransaction()
                        .replace(R.id.fragment, SignInFragment.newInstance(""))
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case 3:{//注册成功页面
                manager.beginTransaction()
                        .replace(R.id.fragment, SignInSuccessFragment.newInstance(""))
                        .commit();
                break;
            }
            case 4:{//忘记密码页面
                manager.beginTransaction()
                        .replace(R.id.fragment, ForgetPasswordFragment.newInstance(""))
                        .addToBackStack(null)
                        .commit();
                break;
            }
            default:
                showShortToast("页面切换失败");
                break;
        }
    }

    public void showUpdateDialog(final UpdateInfoEntity info){
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.layout_update_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(LoginActivity.this, R.style.custom_dialog_no_titlebar);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        ((TextView)(view.findViewById(R.id.update_info_tv))).setText(info.getDescription());

        // 取消按钮监听
        view.findViewById(R.id.layout_exit_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 确定按钮监听
        view.findViewById(R.id.layout_exit_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                downLoadApk(info);
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    protected void downLoadApk(final UpdateInfoEntity info) {
        //进度条对话框
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在下载更新");
        progressDialog.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = DownloadManager.getFileFromServer(info.getUrl(), progressDialog);
                    //sleep(3000);
                    installApk(file);
                    progressDialog.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    progressDialog.dismiss(); //结束掉进度条对话框
                    Looper.prepare();
                    showShortToast("安装包下载失败");
                    Looper.loop();
                    e.printStackTrace();
                }
            }}.start();
    }

    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

}

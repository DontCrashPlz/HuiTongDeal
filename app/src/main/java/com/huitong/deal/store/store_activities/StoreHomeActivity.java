package com.huitong.deal.store.store_activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.activities.LoginActivity;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.store.store_fragments.StoreHomeCommodityFragment;
import com.huitong.deal.store.store_fragments.StoreHomeHomeFragment;
import com.huitong.deal.store.store_fragments.StoreHomeMineFragment;
import com.huitong.deal.store.store_fragments.StoreHomeShoppingFragment;
import com.zheng.zchlibrary.apps.ActivityManager;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.beans.UpdateInfoEntity;
import com.zheng.zchlibrary.utils.DownloadManager;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.Tools;

import java.io.File;

/**
 * Created by Zheng on 2018/6/5.
 */

public class StoreHomeActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    public static final String STORE_HOME_LAUNCH_TAG= "store_home_launch_tag";

    private RadioButton mHomeRbtn;
    public RadioButton mCommodityRbtn;
    private RadioButton mShoppingRbtn;
    private RadioButton mMineRbtn;

    private FragmentManager mManager;

    private int mLauncherTag;//启动标识，用于判断从哪里启动的StoreActivity
    private int mLaunchViewTag;//页面启动标识，用于判断启动StoreActivity后停留在那个fragment
    private UpdateInfoEntity entity;
    private String mCurrentVersion;

    public int gc_id= 0;//启动商品列表页面时的商品分类id，默认0为全部商品

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_home);
        ActivityManager.getInstance().removeAllExceptTop();

        initUI();

        //store_home_launch_tag标识启动StoreActivity后停留在哪个Fragment
        //0表示首页，1表示全部商品，2表示购物车，3表示个人中心
        mLaunchViewTag= getIntent().getIntExtra(STORE_HOME_LAUNCH_TAG, 0);
        if (mLaunchViewTag== 0){
            switchFragment(0);
        }else if (mLaunchViewTag== 2){
            switchFragment(2);
        }

        //launcher_tag标识是从哪里启动的StoreActivity
        //0表示正常启动，1表示从特惠专区退回，2表示从商品详情页跳转，正常启动时intent中会携带版本更新信息
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

    private void initUI(){

        mManager= getSupportFragmentManager();

        mHomeRbtn= findViewById(R.id.home_rbtn_home);
        mHomeRbtn.setOnCheckedChangeListener(this);
        mCommodityRbtn= findViewById(R.id.home_rbtn_commodity);
        mCommodityRbtn.setOnCheckedChangeListener(this);
        mShoppingRbtn= findViewById(R.id.home_rbtn_shopping);
        mShoppingRbtn.setOnCheckedChangeListener(this);
        mMineRbtn= findViewById(R.id.home_rbtn_mine);
        mMineRbtn.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int vId= buttonView.getId();
        switch (vId){
            case R.id.home_rbtn_home:
                if (isChecked){
                    mManager.beginTransaction()
                            .replace(R.id.home_fly_fragment, StoreHomeHomeFragment.newInstance(""))
                            .commit();
                }
                break;
            case R.id.home_rbtn_commodity:
                if (isChecked){
                    mManager.beginTransaction()
                            .replace(R.id.home_fly_fragment, StoreHomeCommodityFragment.newInstance(gc_id))
                            .commit();
                    gc_id= 0;
                }
                break;
            case R.id.home_rbtn_shopping:
                if (isChecked){
                    if (!MyApplication.getInstance().loadLocalToken()){
                        startActivity(new Intent(getRealContext(), LoginActivity.class));
                        mShoppingRbtn.setChecked(false);
                        return;
                    }
                    mManager.beginTransaction()
                            .replace(R.id.home_fly_fragment, StoreHomeShoppingFragment.newInstance(""))
                            .commit();
                }
                break;
            case R.id.home_rbtn_mine:
                if (isChecked){
                    if (!MyApplication.getInstance().loadLocalToken()){
                        startActivity(new Intent(getRealContext(), LoginActivity.class));
                        mMineRbtn.setChecked(false);
                        return;
                    }
                    mManager.beginTransaction()
                            .replace(R.id.home_fly_fragment, StoreHomeMineFragment.newInstance(""))
                            .commit();
                }
                break;
            default:
                showShortToast("无效的导航条切换");
                break;
        }
    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                showShortToast("再按一次退出程序");
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                MyApplication.getInstance().AppExit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showUpdateDialog(final UpdateInfoEntity info){
        View view = LayoutInflater.from(getRealContext()).inflate(R.layout.layout_update_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(getRealContext(), R.style.custom_dialog_no_titlebar);
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

    public void switchFragment(int viewTag){
        if (viewTag== 0){
            mHomeRbtn.setChecked(true);
        }else if (viewTag== 1){
            mCommodityRbtn.setChecked(true);
        }else if (viewTag== 2){
            mShoppingRbtn.setChecked(true);
        }else if (viewTag== 3){
            mMineRbtn.setChecked(true);
        }
    }

}

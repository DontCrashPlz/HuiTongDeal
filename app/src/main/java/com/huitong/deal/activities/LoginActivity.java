package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.fragments.ForgetPasswordFragment;
import com.huitong.deal.fragments.LoginWithPasswordFragment;
import com.huitong.deal.fragments.LoginWithVerificationFragment;
import com.huitong.deal.fragments.SignInFragment;
import com.huitong.deal.fragments.SignInSuccessFragment;
import com.zheng.zchlibrary.apps.BaseActivity;

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


    private TextView mTitleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTitleTv= findViewById(R.id.toolbar_title);

        manager= getSupportFragmentManager();
        changeFragment(FRAGMENT_TAG_PASSWORD);

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
                mTitleTv.setText("帐号登录");
                break;
            }
            case 1:{//验证码登录页面
                manager.beginTransaction()
                        .replace(R.id.fragment, LoginWithVerificationFragment.newInstance(""))
                        .commit();
                mTitleTv.setText("验证码登录");
                break;
            }
            case 2:{//注册页面
                manager.beginTransaction()
                        .replace(R.id.fragment, SignInFragment.newInstance(""))
                        .addToBackStack(null)
                        .commit();
                mTitleTv.setText("注册");
                break;
            }
            case 3:{//注册成功页面
                manager.beginTransaction()
                        .replace(R.id.fragment, SignInSuccessFragment.newInstance(""))
                        .commit();
                mTitleTv.setText("注册成功");
                break;
            }
            case 4:{//忘记密码页面
                manager.beginTransaction()
                        .replace(R.id.fragment, ForgetPasswordFragment.newInstance(""))
                        .addToBackStack(null)
                        .commit();
                mTitleTv.setText("忘记密码");
                break;
            }
            default:
                showShortToast("页面切换失败");
                break;
        }
    }

}

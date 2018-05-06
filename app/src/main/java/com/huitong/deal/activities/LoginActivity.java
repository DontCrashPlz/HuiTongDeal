package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager= getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragment, LoginWithPasswordFragment.newInstance(""))
                .commit();

    }

    public void changeFragment(int fragmentTag){
        switch (fragmentTag){
            case 0:{
                manager.beginTransaction()
                        .replace(R.id.fragment, LoginWithPasswordFragment.newInstance(""))
                        .commit();
                break;
            }
            case 1:{
                manager.beginTransaction()
                        .replace(R.id.fragment, LoginWithVerificationFragment.newInstance(""))
                        .commit();
                break;
            }
            case 2:{
                manager.beginTransaction()
                        .replace(R.id.fragment, SignInFragment.newInstance(""))
                        .commit();
                break;
            }
            case 3:{
                manager.beginTransaction()
                        .replace(R.id.fragment, SignInSuccessFragment.newInstance(""))
                        .commit();
                break;
            }
            case 4:{
                manager.beginTransaction()
                        .replace(R.id.fragment, ForgetPasswordFragment.newInstance(""))
                        .commit();
                break;
            }
            default:
                showShortToast("页面切换失败");
                break;
        }
    }

}

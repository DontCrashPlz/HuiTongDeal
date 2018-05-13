package com.huitong.deal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.fragments.ForgetPasswordFragment;
import com.huitong.deal.fragments.LoginWithPasswordFragment;
import com.huitong.deal.fragments.LoginWithVerificationFragment;
import com.huitong.deal.fragments.SignInFragment;
import com.huitong.deal.fragments.SignInSuccessFragment;
import com.huitong.deal.https.Network;
import com.zheng.zchlibrary.apps.BaseActivity;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

        String appToken= MyApplication.getInstance().getToken();
        if (appToken!= null && appToken.length()> 0){
            addNetWork(Network.getInstance().getUserInfo(appToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<HttpResult<UserInfoDataEntity>>() {
                        @Override
                        public void accept(HttpResult<UserInfoDataEntity> userInfoDataEntityHttpResult) throws Exception {
                            if ("error".equals(userInfoDataEntityHttpResult.getStatus())){
                                showShortToast(userInfoDataEntityHttpResult.getDescription());
                            }else if ("success".equals(userInfoDataEntityHttpResult.getStatus())){
                                if (userInfoDataEntityHttpResult.getData()!= null)
                                    MyApplication.appUser= userInfoDataEntityHttpResult.getData();
                            }
                        }
                    }));
        }

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

}

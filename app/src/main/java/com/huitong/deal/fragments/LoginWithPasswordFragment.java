package com.huitong.deal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.activities.HomeActivity;
import com.huitong.deal.activities.LoginActivity;
import com.zheng.zchlibrary.apps.BaseFragment;

/**
 * Created by Zheng on 2018/4/13.
 */

public class LoginWithPasswordFragment extends BaseFragment {

    public static LoginWithPasswordFragment newInstance(String content){
        LoginWithPasswordFragment instance = new LoginWithPasswordFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_login_with_password, container, false);

        Button mLoginWithVerifivationBtn= mView.findViewById(R.id.login_btn_verification);
        mLoginWithVerifivationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_VERIFICATION);
            }
        });

        TextView mForgetTv= mView.findViewById(R.id.login_tv_forget);
        mForgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_FORGET);
            }
        });

        Button mRegisterBtn= mView.findViewById(R.id.login_btn_register);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_SIGNIN);
            }
        });

        Button mLoginBtn= mView.findViewById(R.id.login_btn_login);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRealContext().startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        return mView;
    }
}

package com.huitong.deal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.huitong.deal.R;
import com.huitong.deal.activities.LoginActivity;
import com.zheng.zchlibrary.apps.BaseFragment;

/**
 * Created by Zheng on 2018/4/13.
 */

public class SignInSuccessFragment extends BaseFragment {

    public static SignInSuccessFragment newInstance(String content){
        SignInSuccessFragment instance = new SignInSuccessFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_login_sign_in_success, container, false);

        Button mRegisterBtn= mView.findViewById(R.id.login_btn_login);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_TAG_PASSWORD);
            }
        });

        return mView;
    }

    @Override
    public void initProgressDialog() {

    }
}

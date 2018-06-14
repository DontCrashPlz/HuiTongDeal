package com.huitong.deal.store.store_activities;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huitong.deal.R;
import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans_store.AreaEntity;
import com.huitong.deal.https.HttpUtils;
import com.huitong.deal.https.Network;
import com.huitong.deal.https.ResponseTransformer;
import com.huitong.deal.store.store_adapter.AreaListAdapter;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/16.
 */

public class StoreRealNameActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mFuncTv;

    private LinearLayout mPanel1;
    private LinearLayout mPanel2;
    private LinearLayout mPanel3;
    private LinearLayout mPanel4;

    private EditText mNameEt;
    private EditText mIdCardEt;
    private TextView mProvinceTv;
    private TextView mCityTv;
    private TextView mAreaTv;
    private EditText mAddressEt;
    private Button mCommitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_realname);

        initUI();

        //请求省份列表
        doAreaListRequest("", 0);
    }

    @Override
    public void initProgressDialog() {
        dialog= new ProgressDialog(getRealContext());
        dialog.setLabel("正在请求数据..");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }

    private void initUI() {
        mBackIv= findViewById(R.id.toolbar_back);
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= findViewById(R.id.toolbar_title);
        mTitleTv.setText("实名认证");
        mFuncTv= findViewById(R.id.toolbar_function);
        mFuncTv.setVisibility(View.INVISIBLE);

        mPanel1= findViewById(R.id.store_realname_panel1);
        mPanel2= findViewById(R.id.store_realname_panel2);
        mPanel3= findViewById(R.id.store_realname_panel3);
        mPanel4= findViewById(R.id.store_realname_panel4);

        mNameEt= findViewById(R.id.store_realname_name);
        mIdCardEt= findViewById(R.id.store_realname_idcard);
        mProvinceTv= findViewById(R.id.store_realname_province);
        mProvinceTv.setOnClickListener(this);
        mCityTv= findViewById(R.id.store_realname_city);
        mCityTv.setOnClickListener(this);
        mAreaTv= findViewById(R.id.store_realname_area);
        mAreaTv.setOnClickListener(this);
        mAddressEt= findViewById(R.id.store_realname_address);
        mCommitBtn= findViewById(R.id.store_realname_button);

        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= mNameEt.getText().toString().trim();
                String idcard= mIdCardEt.getText().toString().trim();
                String address= mAddressEt.getText().toString().trim();
                mPanel1.setBackgroundResource(R.drawable.store_edittext_background_white_corners);
                mPanel2.setBackgroundResource(R.drawable.store_edittext_background_white_corners);
                mPanel3.setBackgroundResource(R.drawable.store_edittext_background_white_corners);
                mPanel4.setBackgroundResource(R.drawable.store_edittext_background_white_corners);
                if (name== null || name.length()< 1){
                    mPanel1.setBackgroundResource(R.drawable.store_edittext_background_pink_corners);
                    showShortToast("请填写真实姓名");
                    return;
                }
                if (idcard== null || idcard.length()< 1){
                    mPanel2.setBackgroundResource(R.drawable.store_edittext_background_pink_corners);
                    showShortToast("请填写身份证号码");
                    return;
                }
                if ("0".equals(mAreaId)){
                    mPanel3.setBackgroundResource(R.drawable.store_edittext_background_pink_corners);
                    showShortToast("请选择区域");
                    return;
                }
                if (address== null || address.trim().length()< 1){
                    mPanel4.setBackgroundResource(R.drawable.store_edittext_background_pink_corners);
                    showShortToast("请填写详细地址");
                    return;
                }

                addNetWork(Network.getInstance().doCertification(
                        mAreaId,
                        idcard,
                        MyApplication.appToken,
                        address,
                        name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(ResponseTransformer.<String>handleResult())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                dismissDialog();
                                showShortToast("实名认证成功");
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

    /**
     * 请求区域列表
     * @param areaId 区域id，如果是“”，请求省份列表；如果是省份id，请求下属城市列表；如果是城市列表，请求下属区县列表；
     * @param requestTag 请求标签，0表示省份列表请求，1表示城市列表请求，2表示区县列表请求
     */
    private void doAreaListRequest(String areaId, final int requestTag){
        addNetWork(Network.getInstance().getAreaList(areaId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ResponseTransformer.<ArrayList<AreaEntity>>handleResult())
                .subscribe(new Consumer<ArrayList<AreaEntity>>() {
                    @Override
                    public void accept(ArrayList<AreaEntity> areaEntities) throws Exception {
                        dismissDialog();
                        if (requestTag== 0){
                            mProvinceList= areaEntities;
                        }
                        if (requestTag== 1){
                            mCityList= areaEntities;
                        }
                        if (requestTag== 2){
                            mAreaList= areaEntities;
                        }
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

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.store_realname_province:{
                showAreaPopupWindow(0);
                break;
            }
            case R.id.store_realname_city:{
                showAreaPopupWindow(1);
                break;
            }
            case R.id.store_realname_area:{
                showAreaPopupWindow(2);
                break;
            }
        }
    }

    private TextView mSelectotTitle;
    private RecyclerView mSelectorRecycler;
    private AreaListAdapter mSelectorAdapter;
    private PopupWindow mAreaPopWindow;
    private String mProviceId= "0";//当前选定的省份id
    private String mCityId= "0";//当前选定的城市id
    private String mAreaId= "0";//当前选定的区县id
    private ArrayList<AreaEntity> mProvinceList=new ArrayList<>();//省份列表
    private ArrayList<AreaEntity> mCityList=new ArrayList<>();//城市列表
    private ArrayList<AreaEntity> mAreaList=new ArrayList<>();//区市列表
    /**
     * 弹出区域选择器
     * @param levelTag 区域等级标签，0表示弹出省份列表，1表示弹出城市列表，2表示弹出区县列表
     */
    private void showAreaPopupWindow(final int levelTag) {
        View popView = View.inflate(this, R.layout.store_layout_area_selector, null);
        mSelectotTitle = popView.findViewById(R.id.area_selector_title);
        if (levelTag== 0) mSelectotTitle.setText("请选择省份");
        if (levelTag== 1) mSelectotTitle.setText("请选择城市");
        if (levelTag== 2) mSelectotTitle.setText("请选择区县");
        mSelectorRecycler = popView.findViewById(R.id.area_selector_recycler);
        mSelectorRecycler.setLayoutManager(new LinearLayoutManager(getRealContext()));
        if (levelTag== 0) mSelectorAdapter= new AreaListAdapter(R.layout.store_item_area_selector, mProvinceList);
        if (levelTag== 1) mSelectorAdapter= new AreaListAdapter(R.layout.store_item_area_selector, mCityList);
        if (levelTag== 2) mSelectorAdapter= new AreaListAdapter(R.layout.store_item_area_selector, mAreaList);
        mSelectorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (levelTag== 0){
                    mProviceId= mProvinceList.get(position).getValue();
                    mProvinceTv.setText(mProvinceList.get(position).getText());
                    //选择省份之后要重置城市和区县
                    mCityId= "0";
                    mCityTv.setText("请选择..");
                    mAreaId= "0";
                    mAreaTv.setText("请选择..");
                    mAreaPopWindow.dismiss();
                    doAreaListRequest(mProviceId, 1);
                }
                if (levelTag== 1){
                    mCityId= mCityList.get(position).getValue();
                    mCityTv.setText(mCityList.get(position).getText());
                    mAreaPopWindow.dismiss();
                    doAreaListRequest(mCityId, 2);
                }
                if (levelTag== 2){
                    mAreaId= mAreaList.get(position).getValue();
                    mAreaTv.setText(mAreaList.get(position).getText());
                    mAreaPopWindow.dismiss();
                }
            }
        });
        mSelectorRecycler.setAdapter(mSelectorAdapter);

        int width = getResources().getDisplayMetrics().widthPixels * 3 / 4;
        int height = getResources().getDisplayMetrics().heightPixels * 3 / 5;
        mAreaPopWindow = new PopupWindow(popView, width, height);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mAreaPopWindow.setBackgroundDrawable(dw);
        mAreaPopWindow.setFocusable(true);
        mAreaPopWindow.setTouchable(true);
        mAreaPopWindow.setOutsideTouchable(true);//允许在外侧点击取消

        mAreaPopWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

}

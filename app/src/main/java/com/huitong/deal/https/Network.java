package com.huitong.deal.https;

import android.support.annotation.Nullable;
import android.util.Log;

import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans.ChiCangEntity;
import com.huitong.deal.beans.ChiCangEntity2;
import com.huitong.deal.beans.ChiCangHistoryEntity;
import com.huitong.deal.beans.ChiCangHistoryEntity2;
import com.huitong.deal.beans.ChiCangHistoryQueryParam;
import com.huitong.deal.beans.ChongZhiEntity;
import com.huitong.deal.beans.ChongZhiHistoryEntity;
import com.huitong.deal.beans.CommitOrderEntity;
import com.huitong.deal.beans.CommodityDetailEntity;
import com.huitong.deal.beans.CommodityListEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.KLineEntity;
import com.huitong.deal.beans.LeverageEntity;
import com.huitong.deal.beans.ListDataEntity;
import com.huitong.deal.beans.LoginEntity;
import com.huitong.deal.beans.PayEntity;
import com.huitong.deal.beans.PayStatusEntity;
import com.huitong.deal.beans.PayTypeEntity;
import com.huitong.deal.beans.TiXianHistoryEntity;
import com.huitong.deal.beans.TiXianHistoryQueryParam;
import com.huitong.deal.beans.TimeLineEntity;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.beans.VerificationCodeEntity;
import com.huitong.deal.beans_store.AddressEntity;
import com.huitong.deal.beans_store.AreaEntity;
import com.huitong.deal.beans_store.HomePageEntity;
import com.huitong.deal.beans_store.OrderListEntity;
import com.huitong.deal.beans_store.ProductClassEntity;
import com.huitong.deal.beans_store.ProductDetailEntity;
import com.huitong.deal.beans_store.ProductListEntity;
import com.huitong.deal.beans_store.RecentInviteEntity;
import com.huitong.deal.beans_store.ShopCartEntity;
import com.huitong.deal.beans_store.StoreBillEntity;
import com.huitong.deal.beans_store.StoreOrderEntity;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zheng on 2018/4/23.
 */

public class Network {

    private static Network mInstance= null;

    public static Network getInstance(){
        if (mInstance== null){
            synchronized (Network.class){
                if (mInstance== null){
                    mInstance= new Network();
                }
            }
        }
        return mInstance;
    }

    private Network(){
        if (apiService == null) {
            if (mOkHttpClient== null){
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                mOkHttpClient= new OkHttpClient.Builder()
//                        .cookieJar(new NovateCookieManger(MyApplication.getInstance()))
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15,TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .build();
            }
            if (mRetrofit== null){
                mRetrofit= new Retrofit.Builder()
                        .client(mOkHttpClient)
                        .baseUrl(BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
            apiService = mRetrofit.create(ApiService.class);
        }
    }

    private static ApiService apiService;
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    //private static final String BASEURL= "http://47.92.28.185/";
    //private static final String BASEURL= "http://47.92.94.101/";
    private static final String BASEURL= "http://www.zzxxjx.cn/";

//    private Map<String, String> getParamsMap(){
//        HashMap<String, String> params= new HashMap<>();
//        return params;
//    }
    public static ApiService getApiService(){
        return apiService;
    }

    /**
     * 用户名登录
     * @param userName
     * @param password
     * @return
     */
    public Observable<HttpResult<LoginEntity>> doLoginWithAccount(String userName, String password){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<LoginEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<LoginEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("type", "account");
        params.put("userName", userName);
        params.put("password", password);
        params.put("sysCode","shop");
        params.put("loginType","shopUser");
        params.put("teminalType","app");
        params.put("useType","shop_login");
        return apiService.doLogin(params);
    }

    /**
     * 手机号码登录
     * @param mobile
     * @param smsCode
     * @return
     */
    public Observable<HttpResult<LoginEntity>> doLoginWithMobile(String mobile, String smsCode){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<LoginEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<LoginEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("type", "mobile");
        params.put("mobile", mobile);
        params.put("smsCode", smsCode);
        params.put("sysCode", "shop");
        params.put("loginType", "shopUser");
        params.put("teminalType", "app");
        params.put("useType", "shop_login");
        return apiService.doLogin(params);
    }

    /**
     * 注册
     * @param mobile
     * @param smsCode
     * @param password
     * @param userNo
     * @param address
     * @return
     */
    public Observable<HttpResult<String>> doRigister(String mobile,
                                                     String smsCode,
                                                     String password,
                                                     String userNo,
                                                     String address){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<String>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<String>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("mobile", mobile);
        params.put("smsCode", smsCode);
        params.put("useType", "shop_reg");
        params.put("password", password);
        if (userNo!= null && userNo.length()> 0){
            params.put("userNo", userNo);
        }
        if (address!= null && address.length()> 0){
            params.put("address", address);
        }
        params.put("regType", "1");
        params.put("teminalType", "app");
        params.put("sysCode", "shop");
        return apiService.doRigister(params);
    }

    /**
     * 获取用户信息
     * @param appToken
     * @return
     */
    public Observable<HttpResult<UserInfoDataEntity>> getUserInfo(String appToken){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<UserInfoDataEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<UserInfoDataEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getUserInfo(appToken);
    }

    /**
     * 实名认证
     * @param appToken
     * @param realName
     * @param idCard
     * @param sex
     * @param nation
     * @param address
     * @return
     */
    public Observable<HttpResult<String>> certifyRealName(String appToken,
                                                          String realName,
                                                          String idCard,
                                                          String sex,
                                                          String nation,
                                                          String address){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<String>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<String>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("ShopUser.trueName", realName);
        params.put("shopUserInfo.idCardNo", idCard);
        params.put("shopUserInfo.nation", nation);
        if (address!= null && address.length()> 0){
            params.put("shopUserInfo.address", address);
        }
        params.put("shopUserInfo.sex", sex);
        return apiService.certifyRealName(params);
    }

    /**
     * 手机号唯一验证
     * @param mobile
     * @return
     */
    public Observable<HttpResult<String>> checkMobile(String mobile){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<String>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<String>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.checkMobile(mobile);
    }

    /**
     * 忘记登录密码
     * @param mobile
     * @param smsCode
     * @param password
     * @return
     */
    public Observable<HttpResult<String>> resetPassword(String mobile,
                                                        String smsCode,
                                                        String password){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<String>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<String>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("mobile", mobile);
        params.put("smsCode", smsCode);
        params.put("useType", NetParams.VERIFICATION_USERTYPT_FINDPASS);
        params.put("password", password);
        return apiService.resetPassword(params);
    }

    /**
     * 修改登录密码
     * @param appToken
     * @param oldpassword
     * @param password
     * @param repassword
     * @return
     */
    public Observable<HttpResult<String>> modifyPassword(String appToken,
                                                         String oldpassword,
                                                         String password,
                                                         String repassword){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<String>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<String>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("oldpassword", oldpassword);
        params.put("password", password);
        params.put("repassword", repassword);
        return apiService.modifyPassword(params);
    }

    /**
     * 设置支付密码
     * @param appToken
     * @param smsCode
     * @param mobile
     * @param password
     * @param payPassword
     * @return
     */
    public Observable<HttpResult<String>> setPayPassword(String appToken,
                                                         String smsCode,
                                                         String mobile,
                                                         String password,
                                                         String payPassword){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<String>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<String>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("smsCode", smsCode);
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("payPass", payPassword);
        return apiService.setPayPassword(params);
    }

    /**
     * 获取短信验证码
     * @param mobile
     * @param useTypr
     * @return
     */
    public Observable<HttpResult<VerificationCodeEntity>> getVerificationCode(
            String mobile,
            String useTypr){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<VerificationCodeEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<VerificationCodeEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("mobile", mobile);
        params.put("type", useTypr);
        return apiService.getVerificationCode(params);
    }

    /**
     * 下单
     * @param appToken
     * @param stockId
     * @param stockCode
     * @param nowPrice
     * @param buyPrice
     * @param buyCount
     * @param feerate
     * @param leverage
     * @param buyType
     * @return
     */
    public Observable<HttpResult<CommitOrderEntity>> commitOrder(
            String appToken,
            String stockId,
            String stockCode,
            String nowPrice,
            String buyPrice,
            String buyCount,
            String feerate,
            String leverage,
            String buyType){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<CommitOrderEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<CommitOrderEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("stockId", stockId);
        params.put("stockCode", stockCode);
        params.put("nowPrice", nowPrice);
        params.put("buyPrice", buyPrice);
        params.put("buyCount", buyCount);
        params.put("feerate", feerate);
        params.put("leverage", leverage);
        params.put("buyType", buyType);
        Log.e("xiaDanParam", params.toString());
        return apiService.commitOrder(params);
    }

    /**
     * 获取持仓列表
     * @param appToken
     * @return
     */
    public Observable<HttpResult<ArrayList<ChiCangEntity2>>> getChiCangList(String appToken){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ArrayList<ChiCangEntity2>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ArrayList<ChiCangEntity2>>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getChiCangList(appToken);
    }

    /**
     * 获取持仓历史列表
     * @param appToken
     * @param pageNumber
     * @return
     */
    public Observable<HttpResult<ListDataEntity<ChiCangHistoryEntity2,ChiCangHistoryQueryParam>>> getChiCangHistoryList(
            String appToken,
            String pageNumber){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ListDataEntity<ChiCangHistoryEntity2,ChiCangHistoryQueryParam>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ListDataEntity<ChiCangHistoryEntity2,ChiCangHistoryQueryParam>>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("pageSize", "8");
        params.put("pageNumber", pageNumber);
        return apiService.getChiCangHistoryList(params);
    }

    /**
     * 获取持仓详情
     * @param appToken
     * @param positionNo
     * @return
     */
    public Observable<HttpResult<ChiCangEntity2>> getChiCangDetail(String appToken, String positionNo){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ChiCangEntity2>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ChiCangEntity2>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getChiCangDetail(appToken, positionNo);
    }

    /**
     * 获取持仓历史详情
     * @param appToken
     * @param orderNo
     * @return
     */
    public Observable<HttpResult<ChiCangHistoryEntity2>> getChiCangHistoryDetail(String appToken, String orderNo){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ChiCangHistoryEntity2>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ChiCangHistoryEntity2>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getChiCangHistoryDetail(appToken, orderNo);
    }

    /**
     * 平仓
     * @param appToken
     * @param positionNo
     * @param closePrice
     * @return
     */
    public Observable<HttpResult<Boolean>> pingCang(String appToken,
                                                    String positionNo,
                                                    String closePrice){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<Boolean>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<Boolean>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("positionNo", positionNo);
        params.put("closePrice", closePrice);
        params.put("closeType", "2");
        return apiService.pingCang(params);
    }

    /**
     * 提现
     * @param appToken
     * @param name
     * @param cardNum
     * @param tiXianNum
     * @param payPassword
     * @return
     */
    public Observable<HttpResult<String>> tiXian(
            String appToken,
            String name,
            String cardNum,
            String tiXianNum,
            String payPassword,
            String bank,
            String bank_branch){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<String>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<String>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("stmCach.cach_user_name", name);
        params.put("stmCach.cach_account", cardNum);
        params.put("stmCach.cach_amount", tiXianNum);
        params.put("payPass", payPassword);
        params.put("stmCach.cach_bark", bank);
        params.put("stmCach.cach_bark_branch", bank_branch);
        return apiService.tiXian(params);
    }

    /**
     * 获取提现记录
     * @param appToken
     * @return
     */
    public Observable<HttpResult<ListDataEntity<TiXianHistoryEntity, TiXianHistoryQueryParam>>> getTiXianHistory(String appToken, int pageNumber){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ListDataEntity<TiXianHistoryEntity, TiXianHistoryQueryParam>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ListDataEntity<TiXianHistoryEntity, TiXianHistoryQueryParam>>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getTiXianHistory(appToken, String.valueOf(pageNumber));
    }

    /**
     * 获取提现详情
     * @param appToken
     * @param id
     * @return
     */
    public Observable<HttpResult<TiXianHistoryEntity>> getTiXianHistoryDetail(String appToken, String id){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<TiXianHistoryEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<TiXianHistoryEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getTiXianHistoryDetail(appToken, id);
    }

    /**
     * 获取产品列表
     * @param appToken
     * @return
     */
    public Observable<HttpResult<ArrayList<CommodityDetailEntity>>> getCommodityList(String appToken){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ArrayList<CommodityDetailEntity>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ArrayList<CommodityDetailEntity>>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getCommodityList(appToken);
    }

    /**
     * 获取产品详情
     * @param appToken
     * @param stockId
     * @return
     */
    public Observable<HttpResult<CommodityDetailEntity>> getCommodityDetail(String appToken, String stockId){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<CommodityDetailEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<CommodityDetailEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getCommodityDetail(appToken, stockId);
    }

    /**
     * 获取产品杠杆列表
     * @param appToken
     * @param stockCode
     * @return
     */
    public Observable<HttpResult<ArrayList<LeverageEntity>>> getLeverageList(String appToken, String stockCode){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ArrayList<LeverageEntity>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ArrayList<LeverageEntity>>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getLeverageList(appToken, stockCode);
    }

    /**
     * 获取产品分时图
     * @param appToken
     * @param stockCode
     * @return
     */
    public Observable<HttpResult<TimeLineEntity>> getTimeLine(String appToken, String stockCode){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<TimeLineEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<TimeLineEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getTimeLine(appToken, stockCode);
    }

    /**
     * 获取产品K线图
     * @param appToken
     * @param stockCode
     * @param kType
     * @return
     */
    public Observable<HttpResult<KLineEntity>> getKLine(String appToken, String stockCode, String kType){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<KLineEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<KLineEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getKLine(appToken, stockCode, kType);
    }

    /**
     * 手机号是否存在
     * @param mobile
     * @return
     */
    public Observable<HttpResult<String>> isExistMobile(String mobile){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<String>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<String>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.isExistMobile(mobile);
    }

    /**
     * 用户名是否存在
     * @param userName
     * @return
     */
    public Observable<HttpResult<String>> isExistUserName(String userName){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<String>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<String>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.isExistUserName(userName);
    }

    /**
     * 充值
     * @param appToken
     * @param amount
     * @return
     */
    public Observable<HttpResult<ChongZhiEntity>> chongZhi(String appToken, String amount, String remark){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ChongZhiEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ChongZhiEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.chongZhi(appToken, amount, remark);
    }

    /**
     * 获取充值列表
     * @param appToken
     * @param pageNumber
     * @return
     */
    public Observable<HttpResult<ListDataEntity<ChongZhiHistoryEntity, ChiCangHistoryQueryParam>>> getChongZhiHistory(String appToken, int pageNumber){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ListDataEntity<ChongZhiHistoryEntity, ChiCangHistoryQueryParam>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ListDataEntity<ChongZhiHistoryEntity, ChiCangHistoryQueryParam>>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("pageSize", "20");
        params.put("splitpage", String.valueOf(pageNumber));
        return apiService.getChongZhiHistory(params);
    }

    /**
     * 获取账单列表
     * @param appToken
     * @param pageNumber
     * @return
     */
    public Observable<HttpResult<ListDataEntity<BillEntity, ChiCangHistoryQueryParam>>> getBill(String appToken, int pageNumber){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ListDataEntity<BillEntity, ChiCangHistoryQueryParam>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ListDataEntity<BillEntity, ChiCangHistoryQueryParam>>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("billType", "-1");
        params.put("pageSize", "20");
        params.put("pageNumber", String.valueOf(pageNumber));
        return apiService.getBill(params);
    }

    /**
     * 获取商城系统账单列表
     * @param appToken
     * @param pageNumber
     * @return
     */
    public Observable<HttpResult<ListDataEntity<StoreBillEntity, ChiCangHistoryQueryParam>>> getStoreBill(String appToken, int pageNumber){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ListDataEntity<StoreBillEntity, ChiCangHistoryQueryParam>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ListDataEntity<StoreBillEntity, ChiCangHistoryQueryParam>>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("_query.billType", "-1");
        params.put("pageSize", "20");
        params.put("pageNumber", String.valueOf(pageNumber));
        return apiService.getStoreBill(params);
    }

    /**
     * 获取账单详情
     * @param appToken
     * @param id
     * @return
     */
    public Observable<HttpResult<BillEntity>> getBillDetail(String appToken, String id){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<BillEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<BillEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getBillDetail(appToken, id);
    }

    /**
     * 获取支付列表
     * @param appToken
     * @return
     */
    public Observable<HttpResult<ArrayList<PayTypeEntity>>> getPayList(String appToken){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ArrayList<PayTypeEntity>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ArrayList<PayTypeEntity>>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        return apiService.getPayList(appToken, "app");
    }

    /**
     * 请求支付
     * @param appToken
     * @param orderNo
     * @param payType
     * @return
     */
    public Observable<HttpResult<PayEntity>> requestPay(String appToken, String orderNo, String payType){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<PayEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<PayEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("orderNo", orderNo);
        params.put("orderType", "precharge");
        params.put("payType", payType);
        params.put("terminalType", "app");
        return apiService.requestPay(params);
    }

    /**
     * 商城请求支付
     * @param appToken
     * @param orderNo
     * @param payType
     * @return
     */
    public Observable<HttpResult<PayEntity>> storeRequestPay(String appToken, String orderNo, String payType){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<PayEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<PayEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("orderNo", orderNo);
        params.put("orderType", "integralOrder");
        params.put("payType", payType);
        params.put("terminalType", "app");
        return apiService.requestPay(params);
    }

    /**
     * 支付状态
     * @param appToken
     * @param orderNo
     * @return
     */
    public Observable<HttpResult<PayStatusEntity>> queryPay(String appToken, String orderNo){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<PayStatusEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<PayStatusEntity>> e) throws Exception {
                    HttpResult httpResult= new HttpResult();
                    httpResult.setStatus("error");
                    httpResult.setDescription("网络不可用");
                    e.onNext(httpResult);
                }
            });
        }
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("orderNo", orderNo);
        params.put("orderType", "precharge");
        params.put("terminalType", "app");
        return apiService.queryPay(params);
    }

    /*****************************************商城相关*******************************************/

    /**
     * 产品首页
     * @return
     */
    public Observable<HttpResult<HomePageEntity>> getStoreHomeData(){
        return Network.getApiService().getStoreHomeData();
    }

    /**
     * 商品列表
     * @param gc_id 商品分类id，用于筛选商品分类，等于0不传这个参数，将默认查询所有商品
     * @param orderType 商品排序条件，等于0为综合和销量条件查询（默认），等于1为了价格条件查询
     * @param orderMode 商品排序方式，等于0为降序（默认），等于1为升序
     * @param pageNumber 商品列表请求页码
     * @return
     */
    public Observable<HttpResult<ProductListEntity>> getProductList(
            int gc_id,
            int orderType,
            int orderMode,
            int pageNumber){
        HashMap<String, String> params= new HashMap<>();
        if (gc_id> 0){
            params.put("_query.gc_id", String.valueOf(gc_id));//商品分类：首页的8种分类
            params.put("_query.level", "2");//固定为2
        }
        if (orderType== 0){
            params.put("orderColunm", "goods_salenum");//价格用store_price，销量和综合用goods_salenum
        }else if (orderType== 1){
            params.put("orderColunm", "store_price");//价格用store_price，销量和综合用goods_salenum
        }
        if (orderMode== 0){
            params.put("orderMode", "desc");//排序方式，desc降序，asc升序
        }else if (orderMode== 1){
            params.put("orderMode", "asc");//排序方式，desc降序，asc升序
        }
        params.put("pageSize", "10");//每页数据数
        params.put("splitpage", "1");//是否分页，固定值
        params.put("pageNumber", String.valueOf(pageNumber));//页码
        return Network.getApiService().getProductList(params);
    }

    /**
     * 商品详情
     * @return
     */
    public Observable<HttpResult<ProductDetailEntity>> getProductDetail(String goodId){
        return Network.getApiService().getProductDetail(goodId);
    }

    /**
     * 商品分类
     * @return
     */
    public Observable<HttpResult<ArrayList<ProductClassEntity>>> getProductClass(){
        return Network.getApiService().getProductClass();
    }

    /**
     * 地址列表
     * @return
     */
    public Observable<HttpResult<ArrayList<AddressEntity>>> getAddressList(String appToken){
        return Network.getApiService().getAddressList(appToken);
    }

    /**
     * 设置默认地址
     * @return
     */
    public Observable<HttpResult<String>> setDefaultAddress(String addressId, String appToken){
        return Network.getApiService().setDefaultAddress(addressId, appToken);
    }

    /**
     * 删除地址
     * @return
     */
    public Observable<HttpResult<String>> deleteAddress(String addressId, String appToken){
        return Network.getApiService().deleteAddress(addressId, appToken);
    }

    /**
     * 根据id获取地址详情
     * @return
     */
    public Observable<HttpResult<AddressEntity>> findAddressById(String addressId, String appToken){
        return Network.getApiService().findAddressById(addressId, appToken);
    }

    /**
     * 添加地址
     * @return
     */
    public Observable<HttpResult<String>> saveAddress(String appToken, AddressEntity entity){
        HashMap<String, String> params= new HashMap<>();
        params.put("shopUserAddress.area_id", String.valueOf(entity.getArea_id()));
        params.put("shopUserAddress.recvName", entity.getRecvname());
        params.put("shopUserAddress.mobile", entity.getMobile());
        params.put("shopUserAddress.zip", entity.getZip());
        params.put("appToken", appToken);
        params.put("shopUserAddress.address", entity.getAddress());
        return Network.getApiService().saveAddress(params);
    }

    /**
     * 修改地址
     * @return
     */
    public Observable<HttpResult<String>> updateAddress(String appToken, AddressEntity entity){
        HashMap<String, String> params= new HashMap<>();
        params.put("shopUserAddress.area_id", String.valueOf(entity.getArea_id()));
        params.put("shopUserAddress.recvName", entity.getRecvname());
        params.put("shopUserAddress.mobile", entity.getMobile());
        params.put("shopUserAddress.zip", entity.getZip());
        params.put("appToken", appToken);
        params.put("shopUserAddress.address", entity.getAddress());
        params.put("shopUserAddress.id", String.valueOf(entity.getId()));
        return Network.getApiService().updateAddress(params);
    }

    /**
     * 获取区市列表，如果areaId=“”，请求省级列表
     * @return
     */
    public Observable<HttpResult<ArrayList<AreaEntity>>> getAreaList(String areaId){
        return Network.getApiService().getAreaList(areaId);
    }

    /**
     * 实名认证
     * @return
     */
    public Observable<HttpResult<String>> doCertification(
            String area_id,
            String idCard,
            String appToken,
            String address,
            String trueName){
        HashMap<String, String> params= new HashMap<>();
        params.put("area_id", area_id);
        params.put("idCardNo", idCard);
        params.put("appToken", appToken);
        params.put("detailAddress", address);
        params.put("trueName", trueName);
        return Network.getApiService().doCertification(params);
    }

    /**
     * 修改用户资料
     * @return
     */
    public Observable<HttpResult<String>> updateUserInfo(
            String birthday,
            String appToken,
            String sex,
            String nickName){
        HashMap<String, String> params= new HashMap<>();
        params.put("birthday", birthday);
        params.put("appToken", appToken);
        params.put("sex", sex);
        params.put("nickName", nickName);
        return Network.getApiService().updateUserInfo(params);
    }

    /**
     * 获取一周内输入的邀请码
     * @return
     */
    public Observable<HttpResult<RecentInviteEntity>> getRecentInvite(String appToken){
        return Network.getApiService().getRecentInvite(appToken);
    }

    /**
     * 验证邀请码
     * @return
     */
    public Observable<HttpResult<String>> checkInviteCode(String appToken, String inviteCode){
        return Network.getApiService().checkInviteCode(appToken, inviteCode);
    }

    /**
     * 修改一条购物车
     * @param appToken
     * @param id
     * @param count
     * @return
     */
    public Observable<HttpResult<String>> updateShopCart(String appToken,String id,String count){
        return Network.getApiService().updateShopCart("app", appToken, id, count);
    }

    /**
     * 删除一条购物车
     * @param appToken
     * @param cartItemId
     * @return
     */
    public Observable<HttpResult<String>> deleteShopCart(String appToken, String cartItemId){
        return Network.getApiService().deleteShopCart(appToken, cartItemId);
    }

    /**
     * 添加一条购物车
     * @param appToken
     * @param goods_id
     * @param price
     * @param count
     * @return
     */
    public Observable<HttpResult<String>> addShopCart(
            String appToken,
            String goods_id,
            String price,
            String count){
        return Network.getApiService().addShopCart(appToken, goods_id, price, count);
    }

    /**
     * 获取购物车列表
     * @param appToken
     * @return
     */
    public Observable<HttpResult<ShopCartEntity>> getShopCartList(String appToken){
        return Network.getApiService().getShopCartList(appToken);
    }

    /**
     * 提交立即购买订单
     * @return
     */
    public Observable<HttpResult<StoreOrderEntity>> commitNowBuyOrder(
            String addressId,
            String goodId,
            String count,
            String appToken){
        return Network.getApiService().commitNowBuyOrder(
                "",
                addressId,
                goodId,
                count,
                appToken,
                "app");
    }

    /**
     * 获取商城支付列表
     * @return
     */
    public Observable<HttpResult<ArrayList<PayTypeEntity>>> getStorePayList(){
        return Network.getApiService().getStorePayList("integral");
    }

    /**
     * 用余额和积分支付
     * @param appToken
     * @param orderNo 订单编号
     * @param payPass 支付密码
     * @param payType 0表示购物券支付，1表示提货券支付
     * @param price 支付金额
     * @return
     */
    public Observable<HttpResult<String>> payForOrder(
            String appToken,
            String orderNo,
            String payPass,
            int payType,
            int price){
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("orderType", "integralOrder");
        params.put("orderNo", orderNo);
        params.put("payPwd", payPass);
        params.put("terminalType", "app");
        if (payType== 0){
            params.put("payType", "balance");
            params.put("total", String.valueOf(price));
        }else if (payType== 1){
            params.put("payType", "integral");
            params.put("integralPrice", String.valueOf(price));
            params.put("total", String.valueOf(price));
        }
        return Network.getApiService().payForOrder(params);
    }

    /**
     * 修改支付密码
     * @return
     */
    public Observable<HttpResult<String>> resetPayPass(String appToken,
                                                       String oldPayPass,
                                                       String payPass,
                                                       String rePayPass){
        return Network.getApiService().resetPayPass(appToken, oldPayPass, payPass, rePayPass);
    }

    /**
     * 提交购物车订单
     * @return
     */
    public Observable<HttpResult<StoreOrderEntity>> commitBuyCartOrder(String appToken,
                                                       String cartItemIds,
                                                       String addr_id){
        return Network.getApiService().commitBuyCartOrder(appToken, cartItemIds, addr_id);
    }

    /**
     * 获取订单列表
     * @param appToken
     * @param pageNumber 页码
     * @param orderType 订单类型 0全部订单；10待付款；20待发货；30已发货；50订单完成
     * @return
     */
    public Observable<HttpResult<OrderListEntity>> getOrderList(
            String appToken,
            int pageNumber,
            int orderType){
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("pageSize", "5");
        params.put("pageNumber", String.valueOf(pageNumber));
        params.put("orderMode", "desc");
        if (orderType!= 0){
            params.put("_query.order_status", String.valueOf(orderType));
        }
        params.put("splitpage", "1");
        params.put("orderColunm", "addTime");
        return Network.getApiService().getOrderList(params);
    }

    /**
     * 取消订单
     * @return
     */
    public Observable<HttpResult<String>> deleteOrder(String appToken, String orderId){
        return Network.getApiService().deleteOrder(appToken, orderId);
    }

    /**
     * 订单确认收货
     * @return
     */
    public Observable<HttpResult<String>> confirmOrder(String appToken, String orderId){
        return Network.getApiService().confirmOrder(appToken, orderId);
    }

}

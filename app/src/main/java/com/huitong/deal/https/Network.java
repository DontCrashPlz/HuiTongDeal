package com.huitong.deal.https;

import android.util.Log;

import com.huitong.deal.apps.MyApplication;
import com.huitong.deal.beans.BillEntity;
import com.huitong.deal.beans.ChiCangEntity;
import com.huitong.deal.beans.ChiCangHistoryEntity;
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
import com.huitong.deal.beans_store.ProductDetailEntity;
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
    private static final String BASEURL= "http://47.92.94.101/";

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
    public Observable<HttpResult<ArrayList<ChiCangEntity>>> getChiCangList(String appToken){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ArrayList<ChiCangEntity>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ArrayList<ChiCangEntity>>> e) throws Exception {
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
    public Observable<HttpResult<ListDataEntity<ChiCangHistoryEntity,ChiCangHistoryQueryParam>>> getChiCangHistoryList(
            String appToken,
            String pageNumber){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ListDataEntity<ChiCangHistoryEntity,ChiCangHistoryQueryParam>>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ListDataEntity<ChiCangHistoryEntity,ChiCangHistoryQueryParam>>> e) throws Exception {
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
    public Observable<HttpResult<ChiCangEntity>> getChiCangDetail(String appToken, String positionNo){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ChiCangEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ChiCangEntity>> e) throws Exception {
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
    public Observable<HttpResult<ChiCangHistoryEntity>> getChiCangHistoryDetail(String appToken, String orderNo){
        if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())){
            return Observable.create(new ObservableOnSubscribe<HttpResult<ChiCangHistoryEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<HttpResult<ChiCangHistoryEntity>> e) throws Exception {
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
    public Observable<HttpResult<ChongZhiEntity>> chongZhi(String appToken, String amount){
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
        return apiService.chongZhi(appToken, amount);
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
     * 产品详情
     * @return
     */
    public Observable<HttpResult<ProductDetailEntity>> getProductDetail(String goodId){
        return Network.getApiService().getProductDetail(goodId);
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

}

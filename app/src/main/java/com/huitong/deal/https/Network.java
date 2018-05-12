package com.huitong.deal.https;

import com.huitong.deal.beans.ChiCangEntity;
import com.huitong.deal.beans.ChiCangHistoryEntity;
import com.huitong.deal.beans.ChiCangHistoryQueryParam;
import com.huitong.deal.beans.CommitOrderEntity;
import com.huitong.deal.beans.CommodityDetailEntity;
import com.huitong.deal.beans.CommodityListEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.KLineEntity;
import com.huitong.deal.beans.LeverageEntity;
import com.huitong.deal.beans.ListDataEntity;
import com.huitong.deal.beans.LoginEntity;
import com.huitong.deal.beans.TiXianHistoryEntity;
import com.huitong.deal.beans.TiXianHistoryQueryParam;
import com.huitong.deal.beans.TimeLineEntity;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.beans.VerificationCodeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private static final String BASEURL= "http://47.92.28.185/";

//    private Map<String, String> getParamsMap(){
//        HashMap<String, String> params= new HashMap<>();
//        return params;
//    }

    /**
     * 用户名登录
     * @param userName
     * @param password
     * @return
     */
    public Observable<HttpResult<LoginEntity>> doLoginWithAccount(String userName, String password){
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
        HashMap<String, String> params= new HashMap<>();
        params.put("mobile", mobile);
        params.put("smsCode", smsCode);
        params.put("useType", "userFindPass");
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
        return apiService.commitOrder(params);
    }

    /**
     * 获取持仓列表
     * @param appToken
     * @return
     */
    public Observable<HttpResult<ArrayList<ChiCangEntity>>> getChiCangList(String appToken){
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
        return apiService.getChiCangDetail(appToken, positionNo);
    }

    /**
     * 获取持仓历史详情
     * @param appToken
     * @param orderNo
     * @return
     */
    public Observable<HttpResult<ChiCangHistoryEntity>> getChiCangHistoryDetail(String appToken, String orderNo){
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
            String payPassword){
        HashMap<String, String> params= new HashMap<>();
        params.put("appToken", appToken);
        params.put("stmCach.cach_user_name", name);
        params.put("stmCach.cach_account", cardNum);
        params.put("stmCach.cach_amount", tiXianNum);
        params.put("payPass", payPassword);
        return apiService.tiXian(params);
    }

    /**
     * 获取提现记录
     * @param appToken
     * @return
     */
    public Observable<HttpResult<ListDataEntity<TiXianHistoryEntity, TiXianHistoryQueryParam>>> getTiXianHistory(String appToken){
        return apiService.getTiXianHistory(appToken);
    }

    /**
     * 获取提现详情
     * @param appToken
     * @param id
     * @return
     */
    public Observable<HttpResult<TiXianHistoryEntity>> getTiXianHistoryDetail(String appToken, String id){
        return apiService.getTiXianHistoryDetail(appToken, id);
    }

    /**
     * 获取产品列表
     * @param appToken
     * @return
     */
    public Observable<HttpResult<ArrayList<CommodityDetailEntity>>> getCommodityList(String appToken){
        return apiService.getCommodityList(appToken);
    }

    /**
     * 获取产品详情
     * @param appToken
     * @param stockId
     * @return
     */
    public Observable<HttpResult<CommodityDetailEntity>> getCommodityDetail(String appToken, String stockId){
        return apiService.getCommodityDetail(appToken, stockId);
    }

    /**
     * 获取产品杠杆列表
     * @param appToken
     * @param stockCode
     * @return
     */
    public Observable<HttpResult<ArrayList<LeverageEntity>>> getLeverageList(String appToken, String stockCode){
        return apiService.getLeverageList(appToken, stockCode);
    }

    /**
     * 获取产品分时图
     * @param appToken
     * @param stockCode
     * @return
     */
    public Observable<HttpResult<TimeLineEntity>> getTimeLine(String appToken, String stockCode){
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
        return apiService.getKLine(appToken, stockCode, kType);
    }

    /**
     * 手机号是否存在
     * @param mobile
     * @return
     */
    public Observable<HttpResult<String>> isExistMobile(String mobile){
        return apiService.isExistMobile(mobile);
    }

    /**
     * 用户名是否存在
     * @param userName
     * @return
     */
    public Observable<HttpResult<String>> isExistUserName(String userName){
        return apiService.isExistUserName(userName);
    }

}

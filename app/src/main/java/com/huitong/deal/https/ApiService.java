package com.huitong.deal.https;

import com.huitong.deal.beans.ChiCangEntity;
import com.huitong.deal.beans.ChiCangHistoryEntity;
import com.huitong.deal.beans.ChiCangHistoryQueryParam;
import com.huitong.deal.beans.CommitOrderEntity;
import com.huitong.deal.beans.HttpResult;
import com.huitong.deal.beans.ListDataEntity;
import com.huitong.deal.beans.LoginEntity;
import com.huitong.deal.beans.TiXianHistoryEntity;
import com.huitong.deal.beans.TiXianHistoryQueryParam;
import com.huitong.deal.beans.UserInfoDataEntity;
import com.huitong.deal.beans.VerificationCodeEntity;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Zheng on 2018/4/22.
 */

public interface ApiService {

    @GET("/api/stm/login/login")
    Observable<HttpResult<LoginEntity>> doLogin(@QueryMap Map<String, String> params);

    @GET("/api/stm/login/register")
    Observable<HttpResult<String>> doRigister(@QueryMap Map<String, String> params);

    @GET("/api/stm/user/getUserInfo")
    Observable<HttpResult<UserInfoDataEntity>> getUserInfo(@Query("appToken") String appToken);

    @GET("/api/stm/user/certificationSub")
    Observable<HttpResult<String>> certifyRealName(@QueryMap Map<String, String> params);

    @GET("/api/trade/checkMobile")
    Observable<HttpResult<String>> checkMobile(@Query("mobile") String mobile);

    @GET("/api/stm/user/resetPassword")
    Observable<HttpResult<String>> resetPassword(@QueryMap Map<String, String> params);

    @GET("/api/stm/user/setPayPass")
    Observable<HttpResult<String>> setPayPassword(@QueryMap Map<String, String> params);

    @GET("/api/pt/authcode/getSmsCode")
    Observable<HttpResult<VerificationCodeEntity>> getVerificationCode(@QueryMap Map<String, String> params);

    @GET("/api/stm/trade/unifiedOrder")
    Observable<HttpResult<CommitOrderEntity>> commitOrder(@QueryMap Map<String, String> params);

    @GET("/api/stm/trade/queryPosition")
    Observable<HttpResult<ArrayList<ChiCangEntity>>> getChiCangList(@Query("appToken") String appToken);

    @GET("/api/stm/trade/queryOrder")
    Observable<HttpResult<ListDataEntity<ChiCangHistoryEntity,ChiCangHistoryQueryParam>>> getChiCangHistoryList(@QueryMap Map<String, String> params);

    @GET("/api/stm/trade/getPositionDetail")
    Observable<HttpResult<ChiCangEntity>> getChiCangDetail(@Query("appToken") String appToken, @Query("positionNo") String positionNo);

    @GET("/api/stm/trade/getOrderDetail")
    Observable<HttpResult<ChiCangHistoryEntity>> getChiCangHistoryDetail(@Query("appToken") String appToken, @Query("orderNo") String orderNo);

    @GET("/api/stm/trade/closePosition")
    Observable<HttpResult<Boolean>> pingCang(@QueryMap Map<String, String> params);

    @GET("/api/stm/cach/userCach")
    Observable<HttpResult<String>> tiXian(@QueryMap Map<String, String> params);

    @GET("/api/stm/cach/queryCach")
    Observable<HttpResult<ListDataEntity<TiXianHistoryEntity, TiXianHistoryQueryParam>>> getTiXianHistory(@Query("appToken") String appToken);

    @GET("/api/stm/cach/getCachDetail")
    Observable<HttpResult<TiXianHistoryEntity>> getTiXianHistoryDetail(@Query("appToken") String appToken, @Query("id") String id);
}

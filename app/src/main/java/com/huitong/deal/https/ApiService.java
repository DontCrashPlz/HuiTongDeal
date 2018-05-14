package com.huitong.deal.https;

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

    @GET("/api/stm/login/resetPassword")
    Observable<HttpResult<String>> resetPassword(@QueryMap Map<String, String> params);

    @GET("/api/stm/user/modifyLoginPass")
    Observable<HttpResult<String>> modifyPassword(@QueryMap Map<String, String> params);

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
    Observable<HttpResult<ListDataEntity<TiXianHistoryEntity, TiXianHistoryQueryParam>>> getTiXianHistory(@Query("appToken") String appToken, @Query("pageNumber") String pageNumber);

    @GET("/api/stm/cach/getCachDetail")
    Observable<HttpResult<TiXianHistoryEntity>> getTiXianHistoryDetail(@Query("appToken") String appToken, @Query("id") String id);

    @GET("/api/stm/stock/stockList")
    Observable<HttpResult<ArrayList<CommodityDetailEntity>>> getCommodityList(@Query("appToken") String appToken);

    @GET("/api/stm/stock/getStockdetail")
    Observable<HttpResult<CommodityDetailEntity>> getCommodityDetail(@Query("appToken") String appToken, @Query("stockId") String stockId);

    @GET("/api/stm/stock/getLeverageList")
    Observable<HttpResult<ArrayList<LeverageEntity>>> getLeverageList(@Query("appToken") String appToken, @Query("stockCode") String stockCode);

    @GET("/api/stm/stock/getStockTimeLine")
    Observable<HttpResult<TimeLineEntity>> getTimeLine(@Query("appToken") String appToken, @Query("stockCode") String stockCode);

    @GET("/api/stm/stock/getStockKLine")
    Observable<HttpResult<KLineEntity>> getKLine(@Query("appToken") String appToken, @Query("stockCode") String stockCode, @Query("kType") String kType);

    @GET("/api/common/user/isExistMobile")
    Observable<HttpResult<String>> isExistMobile(@Query("mobile") String mobile);

    @GET("/api/common/user/isExistUserName")
    Observable<HttpResult<String>> isExistUserName(@Query("userName") String userName);

    /*******************充值*****************/
    @GET("/api/stm/precharge/userPrecharge")
    Observable<HttpResult<ChongZhiEntity>> chongZhi(@Query("appToken") String appToken, @Query("amount") String amount);
    @GET("/api/stm/precharge/queryPrecharge")
    Observable<HttpResult<ListDataEntity<ChongZhiHistoryEntity, ChiCangHistoryQueryParam>>> getChongZhiHistory(@QueryMap Map<String, String> params);

    /*******************账单*****************/
    @GET("/api/stm/user/account")
    Observable<HttpResult<ListDataEntity<BillEntity, ChiCangHistoryQueryParam>>> getBill(@QueryMap Map<String, String> params);
    @GET("/api/stm/user/account/getBillDetail")
    Observable<HttpResult<BillEntity>> getBillDetail(@Query("appToken") String appToken, @Query("id") String id);

    /*******************支付*****************/
    @GET("/api/pay/getPayList")
    Observable<HttpResult<ArrayList<PayTypeEntity>>> getPayList(@Query("appToken") String appToken, @Query("terminalType") String terminalType);
    @GET("/api/pay/requestPay")
    Observable<HttpResult<PayEntity>> requestPay(@QueryMap Map<String, String> params);
    @GET("/api/pay/queryPay")
    Observable<HttpResult<PayStatusEntity>> queryPay(@QueryMap Map<String, String> params);
}

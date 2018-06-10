package com.huitong.deal.https;

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

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("/api/user/userCenter")//@GET("/api/stm/user/getUserInfo")
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
    Observable<HttpResult<ArrayList<ChiCangEntity2>>> getChiCangList(@Query("appToken") String appToken);

    @GET("/api/stm/trade/queryOrder")
    Observable<HttpResult<ListDataEntity<ChiCangHistoryEntity2,ChiCangHistoryQueryParam>>> getChiCangHistoryList(@QueryMap Map<String, String> params);

    @GET("/api/stm/trade/getPositionDetail")
    Observable<HttpResult<ChiCangEntity2>> getChiCangDetail(@Query("appToken") String appToken, @Query("positionNo") String positionNo);

    @GET("/api/stm/trade/getOrderDetail")
    Observable<HttpResult<ChiCangHistoryEntity2>> getChiCangHistoryDetail(@Query("appToken") String appToken, @Query("orderNo") String orderNo);

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
    @FormUrlEncoded
    @POST("/api/stm/precharge/userPrecharge")
    Observable<HttpResult<ChongZhiEntity>> chongZhi(@Query("appToken") String appToken, @Query("amount") String amount, @Field("remark") String remark);
    @GET("/api/stm/precharge/queryPrecharge")
    Observable<HttpResult<ListDataEntity<ChongZhiHistoryEntity, ChiCangHistoryQueryParam>>> getChongZhiHistory(@QueryMap Map<String, String> params);

    /*******************账单*****************/
    @GET("/api/stm/user/account")
    Observable<HttpResult<ListDataEntity<BillEntity, ChiCangHistoryQueryParam>>> getBill(@QueryMap Map<String, String> params);
    //商城系统账单
    @GET("/api/account/queryBill")
    Observable<HttpResult<ListDataEntity<StoreBillEntity, ChiCangHistoryQueryParam>>> getStoreBill(@QueryMap Map<String, String> params);
    @GET("/api/stm/user/account/getBillDetail")
    Observable<HttpResult<BillEntity>> getBillDetail(@Query("appToken") String appToken, @Query("id") String id);

    /*******************支付*****************/
    @GET("/api/pay/getPayList")
    Observable<HttpResult<ArrayList<PayTypeEntity>>> getPayList(@Query("appToken") String appToken, @Query("terminalType") String terminalType);
    @GET("/api/pay/requestPay")
    Observable<HttpResult<PayEntity>> requestPay(@QueryMap Map<String, String> params);
    @GET("/api/pay/queryPay")
    Observable<HttpResult<PayStatusEntity>> queryPay(@QueryMap Map<String, String> params);

    /*******************商城*****************/
    @GET("/api/home/home")//商城首页
    Observable<HttpResult<HomePageEntity>> getStoreHomeData();
    @GET("/api/product/list")//商品列表
    Observable<HttpResult<ProductListEntity>> getProductList(@QueryMap Map<String, String> params);
    @GET("/api/product/view")//商品详情
    Observable<HttpResult<ProductDetailEntity>> getProductDetail(@Query("id") String goodId);
    @GET("/api/goodclass/listShow")//商品分类
    Observable<HttpResult<ArrayList<ProductClassEntity>>> getProductClass();

    /***********************地址管理*******************/
    @FormUrlEncoded
    @POST("/api/address/save")//添加地址
    Observable<HttpResult<String>> saveAddress(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("/api/address/update")//修改地址
    Observable<HttpResult<String>> updateAddress(@FieldMap Map<String, String> params);
    @GET("/api/address")//查询地址
    Observable<HttpResult<ArrayList<AddressEntity>>> getAddressList(@Query("appToken") String appToken);
    @GET("/api/address/delete")//删除地址
    Observable<HttpResult<String>> deleteAddress(@Query("id") String id, @Query("appToken") String appToken);
    @GET("/api/area/getAreaName")//获取区域名称
    Observable<HttpResult<String>> getAreaName(@Query("areaId") String areaId);
    @GET("/api/area/areaJson")//获取区域列表
    Observable<HttpResult<ArrayList<AreaEntity>>> getAreaList(@Query("parentId") String areaId);
    @GET("/api/address/setDefault")//设置默认地址
    Observable<HttpResult<String>> setDefaultAddress(@Query("id") String id, @Query("appToken") String appToken);
    @GET("/api/address/view")//根据地址id查询地址详情
    Observable<HttpResult<AddressEntity>> findAddressById(@Query("id") String id, @Query("appToken") String appToken);

    /*********************实名认证***********************/
    @FormUrlEncoded
    @POST("/api/user/certification")//实名认证
    Observable<HttpResult<String>> doCertification(@FieldMap Map<String, String> params);

    /*********************修改资料***********************/
    @FormUrlEncoded
    @POST("/api/user/update")//修改资料
    Observable<HttpResult<String>> updateUserInfo(@FieldMap Map<String, String> params);

    /*********************特惠专区***********************/
    @GET("/api/activityZone/getUserNo")//获取最近一周输入的邀请码
    Observable<HttpResult<RecentInviteEntity>> getRecentInvite(@Query("appToken") String appToken);
    @GET("/api/user/checkCDK")//验证邀请码
    Observable<HttpResult<String>> checkInviteCode(@Query("appToken") String appToken, @Query("inviterNo") String inviterNo);

    /*********************购物车***********************/
    @GET("/api/shopCart/updateItem")//修改购物车商品
    Observable<HttpResult<String>> updateShopCart(
            @Query("cart_type") String cart_type,
            @Query("appToken") String appToken,
            @Query("id") String id,
            @Query("count") String count);
    @GET("/api/shopCart/deleteItem")//删除购物车商品
    Observable<HttpResult<String>> deleteShopCart(
            @Query("appToken") String appToken,
            @Query("cartItemId") String cartItemId);
    @GET("/api/shopCart/addItem")//添加购物车商品
    Observable<HttpResult<String>> addShopCart(
            @Query("appToken") String appToken,
            @Query("goods_id") String goods_id,
            @Query("price") String price,
            @Query("count") String count);
    @GET("/api/shopCart/list")//获取购物车列表
    Observable<HttpResult<ShopCartEntity>> getShopCartList(@Query("appToken") String appToken);

    /*********************订单管理***********************/
    @GET("/api/shopCart/nowBuyOrderSave")//立即购买提交订单
    Observable<HttpResult<StoreOrderEntity>> commitNowBuyOrder(
            @Query("msg") String msg,
            @Query("addr_id") String addr_id,
            @Query("goods_id") String goods_id,
            @Query("count") String count,
            @Query("appToken") String appToken,
            @Query("from") String from);
    @GET("/api/order/view")//根据订单id查询订单详情
    Observable<HttpResult<String>> getOrderDetail(@Query("appToken") String appToken, @Query("orderId") String orderId);
    @FormUrlEncoded
    @POST("/api/shopCart/onSaveOrderForAndroind")//购物车结算
    Observable<HttpResult<StoreOrderEntity>> commitBuyCartOrder(
            @Field("appToken") String appToken,
            @Field("cartItemIds") String cartItemIds,
            @Field("addr_id") String addr_id);

    //获取订单列表
    @GET("/api/order/queryForAndroind")
    Observable<HttpResult<OrderListEntity>> getOrderList(@QueryMap Map<String, String> params);
    //取消订单
    @GET("/api/order/cancel")
    Observable<HttpResult<String>> deleteOrder(@Query("appToken") String appToken, @Query("orderId") String orderId);
    //订单确认收货
    @GET("/api/order/cancel")
    Observable<HttpResult<String>> confirmOrder(@Query("appToken") String appToken, @Query("orderId") String orderId);

    /*********************商城支付***********************/
    @GET("/api/pay/getPayList")//获取商城支付列表
    Observable<HttpResult<ArrayList<PayTypeEntity>>> getStorePayList(@Query("sysCode") String sysCode);
    @GET("/api/pay/accountPay")//余额和积分支付
    Observable<HttpResult<String>> payForOrder(@QueryMap Map<String, String> params);

    @GET("/api/user/setUserPayPass")//修改支付密码
    Observable<HttpResult<String>> resetPayPass(@Query("appToken") String appToken,
                                                @Query("oldPayPass") String oldPayPass,
                                                @Query("payPass") String payPass,
                                                @Query("rePayPass") String rePayPass);


}

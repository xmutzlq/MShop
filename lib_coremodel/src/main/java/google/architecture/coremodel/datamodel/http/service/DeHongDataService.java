package google.architecture.coremodel.datamodel.http.service;

import java.util.List;
import java.util.Map;

import google.architecture.coremodel.data.AddressData;
import google.architecture.coremodel.data.AdressListData;
import google.architecture.coremodel.data.BidsData;
import google.architecture.coremodel.data.CartData;
import google.architecture.coremodel.data.CartNum;
import google.architecture.coremodel.data.CartResultData;
import google.architecture.coremodel.data.CommentDetailData;
import google.architecture.coremodel.data.DetailCommentPageInfo;
import google.architecture.coremodel.data.DetailMainInfo;
import google.architecture.coremodel.data.FavoriteData;
import google.architecture.coremodel.data.FavoriteResultData;
import google.architecture.coremodel.data.FootprintData;
import google.architecture.coremodel.data.GoodsSpecData;
import google.architecture.coremodel.data.HomeData;
import google.architecture.coremodel.data.MyOrderData;
import google.architecture.coremodel.data.OpDiscoverCates;
import google.architecture.coremodel.data.OpDiscoverIndexResult;
import google.architecture.coremodel.data.OrderData;
import google.architecture.coremodel.data.OrderDetailData;
import google.architecture.coremodel.data.OrderResultData;
import google.architecture.coremodel.data.PayOrderData;
import google.architecture.coremodel.data.PayWayData;
import google.architecture.coremodel.data.QRCodeData;
import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.coremodel.data.SearchHotKey;
import google.architecture.coremodel.data.SearchResult;
import google.architecture.coremodel.data.StartInfo;
import google.architecture.coremodel.data.UploadResultData;
import google.architecture.coremodel.data.VersionInfo;
import google.architecture.coremodel.data.xlj.AppVersion;
import google.architecture.coremodel.data.xlj.FootScanData;
import google.architecture.coremodel.data.xlj.FullBodyDate;
import google.architecture.coremodel.data.xlj.PromotionMedia;
import google.architecture.coremodel.data.xlj.TecentAccessToken;
import google.architecture.coremodel.data.xlj.TecentResponseResult;
import google.architecture.coremodel.data.xlj.TecentTicket;
import google.architecture.coremodel.data.xlj.goodsdetail.GoodsDetailData;
import google.architecture.coremodel.data.xlj.personal.UserInfos;
import google.architecture.coremodel.data.xlj.shopdata.ShopData;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.coremodel.datamodel.http.HttpResult;
import google.architecture.coremodel.datamodel.http.XLJ_HttpResult;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface DeHongDataService {
    @FormUrlEncoded
    @POST(ApiConstants.Intelligent_List)
    Flowable<HttpResult<List<BidsData.IntelligentBidInfo>>> getBidData(@Field("any") String any);

    @FormUrlEncoded
    @POST(ApiConstants.StartInfo)
    Flowable<HttpResult<StartInfo>> getStartInfo(@Field("any") String any);

    @FormUrlEncoded
    @POST(ApiConstants.T_StartInfo)
    Flowable<HttpResult<StartInfo>> getTStartInfo(@Field("any") String any);

    @FormUrlEncoded
    @POST(ApiConstants.T_VersionInfo)
    Flowable<HttpResult<VersionInfo>> getTVersionInfo(@Field("any") String any);

    // 用户接口
    @FormUrlEncoded
    @POST(ApiConstants.UserInfo)
    Flowable<HttpResult<S_UserInfo>> getUserInfo(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.Register)
    Flowable<HttpResult<S_UserInfo>> register(@Field("reg_way") String regWay,
                                          @Field("username") String userName, @Field("only_username") String only_username, @Field("password") String passWd, @Field("code") String code);

    @FormUrlEncoded
    @POST(ApiConstants.Login)
    Flowable<HttpResult<S_UserInfo>> login(@Field("username") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST(ApiConstants.QuickLogin)
    Flowable<HttpResult<S_UserInfo>> quickLogin(@Field("openid") String openid, @Field("nickname") String nickname, @Field("head_img_url") String head_img_url,
                                         @Field("gender") String gender, @Field("source") String source);

    @FormUrlEncoded
    @POST(ApiConstants.QrcodeLogin)
    Flowable<HttpResult<QRCodeData>> qrcodeLogin(@Field("token") String token, @Field("state") String state, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.ResetLoginPasswd)
    Flowable<HttpResult<String>> resetLoginPasswd(@Field("check_way") String checkWay, @Field("username") String userName, @Field("code") String code,
                                                  @Field("passwd_new") String passwdNew, @Field("passwd_confirm") String passwdConfirm);
    @FormUrlEncoded
    @POST(ApiConstants.ModifyLoginPasswd)
    Flowable<HttpResult<String>> modifyLoginPasswd(@Field("access_token") String acessToken, @Field("user_id") String userId, @Field("passwd_old") String passwdOld,
                                                   @Field("passwd_new") String passwdNew, @Field("passwd_confirm") String passwdConfirm);
    @FormUrlEncoded
    @POST(ApiConstants.ModifyEmail)
    Flowable<HttpResult<String>> modifyEmail(@Field("access_token") String acessToken, @Field("user_id") String userId, @Field("email") String email, @Field("code") String code);

    @FormUrlEncoded
    @POST(ApiConstants.ModifyCellphone)
    Flowable<HttpResult<String>> modifyCellphone(@Field("access_token") String acessToken, @Field("user_id") String userId, @Field("cellphone") String cellphone, @Field("code") String code);

    @FormUrlEncoded
    @POST(ApiConstants.ModifyNickName)
    Flowable<HttpResult<String>> modifyNickName(@Field("nickname") String nickname, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.UpLoad)
    Flowable<HttpResult<UploadResultData>> upload(@Field("pic") String pic, @Field("type") String type, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.UpdateHeadPic)
    Flowable<HttpResult<UploadResultData>> updateHeadPic(@Field("user_id") String user_id, @Field("head_pic") String head_pic);

    // 工具接口
    @FormUrlEncoded
    @POST(ApiConstants.SendEmail)
    Flowable<HttpResult<String>> sendEmail(@Field("scene") String scene, @Field("receipt_email") String receipt_cellphone, @Field("uid") String uid);

    @FormUrlEncoded
    @POST(ApiConstants.SendShortMsg)
    Flowable<HttpResult<String>> sendShotMsg(@Field("scene") String scene, @Field("receipt_cellphone") String receipt_cellphone, @Field("uid") String uid);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCategory)
    Flowable<HttpResult<OpDiscoverIndexResult>> getShoppingCategory(@Field("any") String any);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCategoryRight)
    Flowable<HttpResult<OpDiscoverIndexResult>> getShoppingCategoryRight(@Field("id") String parentId);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingHome)
    Flowable<HttpResult<HomeData>> getShoppingHome(@Field("any") String any);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingSearchHotKey)
    Flowable<HttpResult<SearchHotKey>> getSearchHotKey(@Field("any") String any);

    @Headers(ApiConstants.HEADER_UN_EN_PARAMS)
    @FormUrlEncoded
    @POST(ApiConstants.ShoppingSearchList)
    Flowable<HttpResult<SearchResult>> getSearchList(@Field("search_id") String search_id, @Field("search_name") String search_name,
                                                     @Field("orderField") String orderField, @Field("orderDirection") String orderDirection,
                                                     @Field("min_price") String min_price, @Field("max_price") String max_price,
                                                     @Field("page") String page, @Field("pagesize") String pagesize, @FieldMap Map<String, String> otherParams);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCommodity)
    Flowable<HttpResult<DetailMainInfo>> getDetailCommodity(@Field("goods_id") String goods_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingChoiceGoodsSpec)
    Flowable<HttpResult<GoodsSpecData>> choiceGoodsSpec(@Field("goods_id") String goods_id, @Field("item_ids") String item_ids);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingComments)
    Flowable<HttpResult<DetailCommentPageInfo>> getDetailComments(@Field("goods_id") String goods_id, @Field("page") String page, @Field("pagesize") String pagesize);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCommentsDetail)
    Flowable<HttpResult<CommentDetailData>> getCommentDetail(@Field("user_id") String user_id, @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCommentsCommit)
    Flowable<HttpResult<String>> commitComment(@Field("user_id") String user_id, @Field("order_id") String order_id, @Field("is_anonymous") String is_anonymous, @Field("comment_json") String comment_json);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingFeedBackCommit)
    Flowable<HttpResult<String>> commitFeedBack(@Field("user_id") String user_id, @Field("content") String content, @Field("images") String images);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCart)
    Flowable<HttpResult<CartData>> getCarts(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingFavorites)
    Flowable<HttpResult<FavoriteResultData>> checkFavorites(@Field("goods_id") String goods_id, @Field("user_id") String user_id, @Field("act_way") String act_way);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingDelFavorites)
    Flowable<HttpResult<String>> delFavorites(@Field("goods_id") String goods_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingFavoritesList)
    Flowable<HttpResult<FavoriteData>> getFavorites(@Field("user_id") String user_id, @Field("page") String page, @Field("pagesize") String pagesize);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingFootprintList)
    Flowable<HttpResult<FootprintData>> getFootprints(@Field("user_id") String user_id, @Field("page") String page, @Field("pagesize") String pagesize);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingFootprintDel)
    Flowable<HttpResult<String>> delFootprints(@Field("user_id") String user_id, @Field("id") String id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingAddCart)
    Flowable<HttpResult<CartNum>> addCart(@Field("goods_id") String goods_id, @Field("user_id") String user_id, @Field("item_ids") String item_ids, @Field("goods_num") String goods_num);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCartUpdate)
    Flowable<HttpResult<CartResultData>> cartUpdate(@Field("ids") String ids, @Field("user_id") String user_id, @Field("goods_num") String goods_num, @Field("selected") String selected);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCartDelete)
    Flowable<HttpResult<String>> cartDelete(@Field("ids") String ids, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCartSettlement)
    Flowable<HttpResult<OrderData>> cartSettlement(@Field("from") String from, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingDirectOrder)
    Flowable<HttpResult<OrderData>> directOrder(@Field("from") String from, @Field("goods_id") String goods_id, @Field("user_id") String user_id, @Field("item_ids") String item_ids, @Field("goods_num") String goods_num);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingAddressList)
    Flowable<HttpResult<AdressListData>> addressList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingSaveAddress)
    Flowable<HttpResult<String>> saveAddress(@Field("user_id") String user_id, @Field("act_way") String act_way, @Field("province_name") String province_name,
                                             @Field("city_name") String city_name, @Field("district_name") String district_name, @Field("town_name") String town_name,
                                             @Field("address") String address, @Field("zipcode") String zipcode, @Field("is_default") String is_default,
                                             @Field("address_id") String address_id, @Field("consignee") String consignee, @Field("mobile") String mobile,
                                             @Field("province_id") String province_id, @Field("city_id") String city_id, @Field("district_id") String district_id,
                                             @Field("town_id") String town_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingAddressProvince)
    Flowable<HttpResult<AddressData>> getProvince(@Field("any") String any);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingAddressCity)
    Flowable<HttpResult<AddressData>> getCity(@Field("province_id") String province_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingAddressDistrict)
    Flowable<HttpResult<AddressData>> getDistrict(@Field("city_id") String city_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingAddressTown)
    Flowable<HttpResult<AddressData>> getTown(@Field("district_id") String district_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingSubmitOrder)
    Flowable<HttpResult<OrderResultData>> submitOrder(@Field("from") String from, @Field("goods_id") String goods_id, @Field("user_id") String user_id,
                                                      @Field("item_ids") String item_ids, @Field("goods_num") String goods_num,
                                                      @Field("address_id") String address_id, @Field("user_note") String user_note);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingPayWay)
    Flowable<HttpResult<PayWayData>> payWay(@Field("user_id") String user_id, @Field("order_no") String order_no, @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingPayOrder)
    Flowable<HttpResult<PayOrderData>> payOrder(@Field("user_id") String user_id, @Field("order_no") String order_no, @Field("order_id") String order_id, @Field("code") String code);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingMyOrderList)
    Flowable<HttpResult<MyOrderData>> myOrderList(@Field("user_id") String user_id, @Field("order_state") String order_state, @Field("page") String page, @Field("pagesize") String pagesize);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingOrderDetail)
    Flowable<HttpResult<OrderDetailData>> orderDetail(@Field("user_id") String user_id, @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingCancelOrder)
    Flowable<HttpResult<String>> cancelOrder(@Field("user_id") String user_id, @Field("order_id") String order_id, @Field("cancel_reason") String cancel_reason);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingDeleteOrder)
    Flowable<HttpResult<String>> deleteOrder(@Field("user_id") String user_id, @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingConfirmReceipt)
    Flowable<HttpResult<String>> confirmReceipt(@Field("user_id") String user_id, @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST(ApiConstants.ShoppingReminding)
    Flowable<HttpResult<String>> remindingSend(@Field("user_id") String user_id, @Field("order_id") String order_id);

    //##========新乐纪=======##
    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Shop_Data)
    Flowable<XLJ_HttpResult<ShopData>> xlj_getShopData(@Field("request_json") String requestJson);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Goods_Detail)
    Flowable<XLJ_HttpResult<GoodsDetailData>> xlj_getGoodsDetail(@Field("request_json") String requestJson);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Goods_List)
    Flowable<XLJ_HttpResult<SearchResult>> xlj_getGoodsList(@Field("request_json") String requestJson);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Cat_TOP)
    Flowable<XLJ_HttpResult<List<OpDiscoverCates>>> xlj_getTopCat(@Field("request_json") String requestJson);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Cat_Child)
    Flowable<XLJ_HttpResult<List<OpDiscoverCates>>> xlj_getChildCat(@Field("request_json") String requestJson);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_FROM_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Get_User_token)
    Flowable<XLJ_HttpResult<String>> xlj_getUserToken(@Field("wxUnionId")String wxUnionId, @Field("method")String method);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_FROM_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Get_Refresh_token)
    Flowable<XLJ_HttpResult<String>> xlj_getRefreshToken(@Field("userToken")String userToken, @Field("method")String method);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_FROM_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Get_User_Info)
    Flowable<XLJ_HttpResult<UserInfos>> xlj_getUserInfo(@Field("userToken")String userToken, @Field("method")String method);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Upload_Device_Info)
    Flowable<XLJ_HttpResult> xlj_uploadDeviceInfo(@Field("request_json") String requestJson);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Get_Promotion_Media)
    Flowable<XLJ_HttpResult<PromotionMedia>> xlj_getPromotionMedia(@Field("request_json") String requestJson);

    @Headers({ApiConstants.HEADER_TECENT_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @GET(ApiConstants.XLJ_Get_Tecent_AccessToken)
    Flowable<TecentAccessToken> xlj_getTecentAccessToken(@QueryMap Map<String,String> params);

    @Headers({ApiConstants.HEADER_TECENT_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @GET(ApiConstants.XLJ_Get_Tecent_Ticket)
    Flowable<TecentTicket> xlj_getTecentTicket(@QueryMap Map<String,String> params);

    @Headers({ApiConstants.HEADER_TECENT_URL, ApiConstants.HEADER_USE_TEXT_REQUEST})
    @GET(ApiConstants.XLJ_Get_Tecent_WX_OpenId)
    Flowable<TecentResponseResult> xlj_getTecentWXOpenId(@QueryMap Map<String,String> params);

    @Headers({ApiConstants.HEADER_UAV_URL, ApiConstants.HEADER_USE_FROM_REQUEST})
    @POST(ApiConstants.XLJ_Get_FootScan_Token)
    Flowable<XLJ_HttpResult<FootScanData>> xlj_getFootScanToken(@QueryMap Map<String,String> param);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Get_App_Version_Info)
    Flowable<XLJ_HttpResult<AppVersion>> xlj_getAppVersionInfo(@Field("request_json") String requestJson);

    @Headers({ApiConstants.HEADER_MULTI_URL, ApiConstants.HEADER_USE_JSON_REQUEST})
    @FormUrlEncoded
    @POST(ApiConstants.XLJ_Get_Try_Data)
    Flowable<XLJ_HttpResult<FullBodyDate>> xlj_getTryData(@Field("request_json") String requestJson);

}

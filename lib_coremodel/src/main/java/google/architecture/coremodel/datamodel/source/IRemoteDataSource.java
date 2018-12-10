package google.architecture.coremodel.datamodel.source;

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
import google.architecture.coremodel.datamodel.http.HttpResult;
import io.reactivex.Flowable;

/**
 * @author lq.zeng
 * @date 2018/4/8
 */

public interface IRemoteDataSource {
    Flowable<HttpResult<List<BidsData.IntelligentBidInfo>>> getBidData();
    Flowable<HttpResult<StartInfo>> getStartInfo();
    Flowable<HttpResult<StartInfo>> getTStartInfo();
    Flowable<HttpResult<VersionInfo>> getTVersionInfo();
    /**
     * 注册方式
     * email:邮箱注册
     * cellphone：手机号注册
     * @param regWay
     * @param userName
     * @param passWd
     * @param code
     * @return
     */
    Flowable<HttpResult<S_UserInfo>> register(String regWay, String userName, String only_username, String passWd, String code);
    Flowable<HttpResult<S_UserInfo>> login(String userName, String passWd);
    Flowable<HttpResult<S_UserInfo>> quickLogin(String openid, String nickname, String head_img_url, String gender, String source);
    Flowable<HttpResult<QRCodeData>> qrcodeLogin(String token, String state, String uid);
    Flowable<HttpResult<S_UserInfo>> getUserInfo(String uid);
    /**
     * 1：第三方登录关联用户验证码
     * 2：用户注册验证码
     * 3: 修改密码
     * 4: 重置密码
     * 5: 更换邮箱
     * @param scene
     * @param receipt_cellphone
     * @param uid
     * @return
     */
    Flowable<HttpResult<String>> sendEmail(String scene, String receipt_cellphone, String uid);
    Flowable<HttpResult<String>> sendShotMsg(String scene, String receipt_cellphone, String uid);

    Flowable<HttpResult<UploadResultData>> upload(String pic, String type, String user_id);
    Flowable<HttpResult<UploadResultData>> updateHeadPic(String user_id, String head_pic);

    Flowable<HttpResult<String>> resetLoginPasswd(String checkWay, String userName, String code,
                                                  String passwdNew, String passwdConfirm);
    Flowable<HttpResult<String>> modifyLoginPasswd(String acessToken, String userId, String passwdOld, String passwdNew, String passwdConfirm);
    Flowable<HttpResult<String>> modifyEmail(String acessToken, String userId, String email, String code);
    Flowable<HttpResult<String>> modifyCellphone(String acessToken, String userId, String cellphone, String code);
    Flowable<HttpResult<String>> modifyNickName(String nickname, String user_id);

    //商城
    Flowable<HttpResult<OpDiscoverIndexResult>> getShoppingCategory();
    Flowable<HttpResult<OpDiscoverIndexResult>> getShoppingCategoryRight(String parentId);
    Flowable<HttpResult<HomeData>> getShoppingHome();

    Flowable<HttpResult<SearchHotKey>> getSearchHotKey();
    Flowable<HttpResult<SearchResult>> getSearchList(String search_id, String search_name,
                                                     String orderField, String orderDirection,
                                                     String min_price, String max_price,
                                                     String page, String pagesize,
                                                     Map<String, String> otherParams);

    Flowable<HttpResult<DetailMainInfo>> getDetailCommodity(String goods_id, String user_id);
    Flowable<HttpResult<GoodsSpecData>> choiceGoodsSpec(String goods_id, String item_ids);
    Flowable<HttpResult<DetailCommentPageInfo>> getDetailComments(String goods_id, String page, String pagesize);
    Flowable<HttpResult<CommentDetailData>> getCommentDetail(String user_id, String order_id);
    Flowable<HttpResult<String>> commitComment(String user_id, String order_id, String is_anonymous, String comment_json);
    Flowable<HttpResult<String>> commitFeedBack(String user_id, String content, String images);

    Flowable<HttpResult<FavoriteResultData>> checkFavorites(String goods_id, String user_id, String act_way);
    Flowable<HttpResult<String>> delFavorites(String goods_id, String user_id);
    Flowable<HttpResult<FavoriteData>> getFavorites(String user_id, String page, String pagesize);
    Flowable<HttpResult<FootprintData>> getFootprints(String user_id, String page, String pagesize);
    Flowable<HttpResult<String>> delFootprints(String user_id, String goods_id);

    Flowable<HttpResult<CartData>> getCarts(String user_id);
    Flowable<HttpResult<CartNum>> addCart(String goods_id, String user_id, String item_ids, String goods_num);
    Flowable<HttpResult<CartResultData>> cartUpdate(String ids, String user_id, String goods_num, String selected);
    Flowable<HttpResult<String>> cartDelete(String ids, String user_id);
    Flowable<HttpResult<OrderData>> cartSettlement(String from, String user_id);
    Flowable<HttpResult<OrderData>> directOrder(String from, String goods_id, String user_id, String item_ids, String goods_num);


    Flowable<HttpResult<AdressListData>> addressList(String user_id);
    Flowable<HttpResult<String>> saveAddress(String user_id, String act_way, String province_name,
                                             String city_name, String district_name, String town_name,
                                             String address, String zipcode, String is_default,
                                             String address_id, String consignee, String mobile,
                                             String province_id, String city_id, String district_id,
                                             String town_id);
    Flowable<HttpResult<AddressData>> getProvince();
    Flowable<HttpResult<AddressData>> getCity(String province_id);
    Flowable<HttpResult<AddressData>> getDistrict(String city_id);
    Flowable<HttpResult<AddressData>> getTown(String district_id);

    Flowable<HttpResult<OrderResultData>> submitOrder(String from, String goods_id, String user_id,
                                                      String item_ids, String goods_num,
                                                      String address_id, String user_note);
    Flowable<HttpResult<PayWayData>> payWay(String user_id, String order_no, String order_id);
    Flowable<HttpResult<PayOrderData>> payOrder(String user_id, String order_no, String order_id, String code);
    Flowable<HttpResult<MyOrderData>> myOrderList(String user_id, String order_state, String page, String pagesize);
    Flowable<HttpResult<OrderDetailData>> orderDetail(String user_id, String order_id);
    Flowable<HttpResult<String>> cancelOrder(String user_id, String order_id, String cancel_reason);
    Flowable<HttpResult<String>> deleteOrder(String user_id, String order_id);
    Flowable<HttpResult<String>> confirmReceipt(String user_id, String order_id);
    Flowable<HttpResult<String>> remindingSend(String user_id, String order_id);
}

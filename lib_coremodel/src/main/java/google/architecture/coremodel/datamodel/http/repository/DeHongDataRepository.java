package google.architecture.coremodel.datamodel.http.repository;

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
import google.architecture.coremodel.datamodel.db.AppDatabase;
import google.architecture.coremodel.datamodel.db.entity.SearchInfoEntity;
import google.architecture.coremodel.datamodel.db.entity.UserInfoEntity;
import google.architecture.coremodel.datamodel.http.ApiClient;
import google.architecture.coremodel.datamodel.http.HttpResult;
import google.architecture.coremodel.datamodel.source.IRemoteDataSource;
import google.architecture.coremodel.datamodel.source.RemoteDataSourceImpl;
import google.architecture.coremodel.util.Utils;
import io.reactivex.Flowable;

/**
 *
 */
public class DeHongDataRepository implements IRemoteDataSource {

    private static DeHongDataRepository INSTANCE;

    private IRemoteDataSource remoteDataSource;
    private AppDatabase appDatabase;

    private DeHongDataRepository() {
        remoteDataSource = new RemoteDataSourceImpl(ApiClient.getDeHongDataService());
        appDatabase = AppDatabase.get(Utils.getContext());
    }

    public static DeHongDataRepository get() {
        if (INSTANCE == null) {
            synchronized (DeHongDataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DeHongDataRepository();
                }
            }
        }
        return INSTANCE;
    }

    public Flowable<UserInfoEntity> loadUserInfoEntity(String id) {
        return appDatabase.userInfoDao().loadUserInfoEntity(id);
    }

    public void insertUserInfoEntity(UserInfoEntity userInfoEntity) {
        appDatabase.userInfoDao().insertUserInfoEntity(userInfoEntity);
    }

    public Flowable<List<SearchInfoEntity>> loadSearchInfoEntity() {
        return appDatabase.searchInfoDao().loadSearchInfoEntity();
    }

    public long insertSearchInfoEntity(SearchInfoEntity searchInfoEntity) {
        return appDatabase.searchInfoDao().insertSearchInfoEntity(searchInfoEntity);
    }

    public int deleteSearchInfoEntitys(List<SearchInfoEntity> searchInfoEntities) {
        return appDatabase.searchInfoDao().deleteSearchInfoEntitys(searchInfoEntities);
    }

    public int deleteSearchInfoEntity(SearchInfoEntity searchInfoEntitie) {
        return appDatabase.searchInfoDao().deleteSearchInfoEntity(searchInfoEntitie);
    }

    @Override
    public Flowable<HttpResult<List<BidsData.IntelligentBidInfo>>> getBidData() {
        return remoteDataSource.getBidData();
    }

    @Override
    public Flowable<HttpResult<StartInfo>> getStartInfo() {
        return remoteDataSource.getStartInfo();
    }

    @Override
    public Flowable<HttpResult<StartInfo>> getTStartInfo() {
        return remoteDataSource.getTStartInfo();
    }

    @Override
    public Flowable<HttpResult<VersionInfo>> getTVersionInfo() {
        return remoteDataSource.getTVersionInfo();
    }

    @Override
    public Flowable<HttpResult<S_UserInfo>> register(String regWay, String userName, String only_username, String passWd, String code) {
        return remoteDataSource.register(regWay, userName, only_username, passWd, code);
    }

    @Override
    public Flowable<HttpResult<S_UserInfo>> login(String userName, String passWd) {
        return remoteDataSource.login(userName, passWd);
    }

    @Override
    public Flowable<HttpResult<S_UserInfo>> quickLogin(String openid, String nickname, String head_img_url, String gender, String source) {
        return remoteDataSource.quickLogin(openid, nickname, head_img_url, gender, source);
    }

    @Override
    public Flowable<HttpResult<QRCodeData>> qrcodeLogin(String token, String state, String uid) {
        return remoteDataSource.qrcodeLogin(token, state, uid);
    }

    @Override
    public Flowable<HttpResult<S_UserInfo>> getUserInfo(String uid) {
        return remoteDataSource.getUserInfo(uid);
    }

    @Override
    public Flowable<HttpResult<String>> sendEmail(String scene, String receipt_cellphone, String uid) {
        return remoteDataSource.sendEmail(scene, receipt_cellphone, uid);
    }

    @Override
    public Flowable<HttpResult<String>> sendShotMsg(String scene, String receipt_cellphone, String uid) {
        return remoteDataSource.sendShotMsg(scene, receipt_cellphone, uid);
    }

    @Override
    public Flowable<HttpResult<UploadResultData>> upload(String pic, String type, String user_id) {
        return remoteDataSource.upload(pic, type, user_id);
    }

    @Override
    public Flowable<HttpResult<UploadResultData>> updateHeadPic(String user_id, String head_pic) {
        return remoteDataSource.updateHeadPic(user_id, head_pic);
    }

    @Override
    public Flowable<HttpResult<String>> resetLoginPasswd(String checkWay, String userName, String code, String passwdNew, String passwdConfirm) {
        return remoteDataSource.resetLoginPasswd(checkWay, userName, code, passwdNew, passwdConfirm);
    }

    @Override
    public Flowable<HttpResult<String>> modifyLoginPasswd(String acessToken, String userId, String passwdOld, String passwdNew, String passwdConfirm) {
        return remoteDataSource.modifyLoginPasswd(acessToken, userId, passwdOld, passwdNew, passwdConfirm);
    }

    @Override
    public Flowable<HttpResult<String>> modifyEmail(String acessToken, String userId, String email, String code) {
        return remoteDataSource.modifyEmail(acessToken, userId, email, code);
    }

    @Override
    public Flowable<HttpResult<String>> modifyCellphone(String acessToken, String userId, String cellphone, String code) {
        return remoteDataSource.modifyCellphone(acessToken, userId, cellphone, code);
    }

    @Override
    public Flowable<HttpResult<String>> modifyNickName(String nickname, String user_id) {
        return remoteDataSource.modifyNickName(nickname, user_id);
    }

    @Override
    public Flowable<HttpResult<OpDiscoverIndexResult>> getShoppingCategory() {
        return remoteDataSource.getShoppingCategory();
    }

    @Override
    public Flowable<HttpResult<OpDiscoverIndexResult>> getShoppingCategoryRight(String parentId) {
        return remoteDataSource.getShoppingCategoryRight(parentId);
    }

    @Override
    public Flowable<HttpResult<HomeData>> getShoppingHome() {
        return remoteDataSource.getShoppingHome();
    }

    @Override
    public Flowable<HttpResult<SearchHotKey>> getSearchHotKey() {
        return remoteDataSource.getSearchHotKey();
    }

    @Override
    public Flowable<HttpResult<SearchResult>> getSearchList(String search_id, String search_name, String orderField, String orderDirection,
                                                            String min_price, String max_price, String page, String pagesize,
                                                            Map<String, String> otherParams) {
        return remoteDataSource.getSearchList(search_id, search_name, orderField, orderDirection, min_price, max_price, page, pagesize, otherParams);
    }

    @Override
    public Flowable<HttpResult<DetailMainInfo>> getDetailCommodity(String goods_id, String user_id) {
        return remoteDataSource.getDetailCommodity(goods_id, user_id);
    }

    @Override
    public Flowable<HttpResult<GoodsSpecData>> choiceGoodsSpec(String goods_id, String item_ids) {
        return remoteDataSource.choiceGoodsSpec(goods_id, item_ids);
    }

    @Override
    public Flowable<HttpResult<DetailCommentPageInfo>> getDetailComments(String goods_id, String page, String pagesize) {
        return remoteDataSource.getDetailComments(goods_id, page, pagesize);
    }

    @Override
    public Flowable<HttpResult<CommentDetailData>> getCommentDetail(String user_id, String order_id) {
        return remoteDataSource.getCommentDetail(user_id, order_id);
    }

    @Override
    public Flowable<HttpResult<String>> commitComment(String user_id, String order_id, String is_anonymous, String comment_json) {
        return remoteDataSource.commitComment(user_id, order_id, is_anonymous, comment_json);
    }

    @Override
    public Flowable<HttpResult<String>> commitFeedBack(String user_id, String content, String images) {
        return remoteDataSource.commitFeedBack(user_id, content, images);
    }

    @Override
    public Flowable<HttpResult<CartData>> getCarts(String user_id) {
        return remoteDataSource.getCarts(user_id);
    }

    @Override
    public Flowable<HttpResult<FavoriteResultData>> checkFavorites(String goods_id, String user_id, String act_way) {
        return remoteDataSource.checkFavorites(goods_id, user_id, act_way);
    }

    @Override
    public Flowable<HttpResult<String>> delFavorites(String goods_id, String user_id) {
        return remoteDataSource.delFavorites(goods_id, user_id);
    }

    @Override
    public Flowable<HttpResult<FavoriteData>> getFavorites(String user_id, String page, String pagesize) {
        return remoteDataSource.getFavorites(user_id, page, pagesize);
    }

    @Override
    public Flowable<HttpResult<FootprintData>> getFootprints(String user_id, String page, String pagesize) {
        return remoteDataSource.getFootprints(user_id, page, pagesize);
    }

    @Override
    public Flowable<HttpResult<String>> delFootprints(String user_id, String goods_id) {
        return remoteDataSource.delFootprints(user_id, goods_id);
    }

    @Override
    public Flowable<HttpResult<CartNum>> addCart(String goods_id, String user_id, String item_ids, String goods_num) {
        return remoteDataSource.addCart(goods_id, user_id, item_ids, goods_num);
    }

    @Override
    public Flowable<HttpResult<CartResultData>> cartUpdate(String ids, String user_id, String goods_num, String selected) {
        return remoteDataSource.cartUpdate(ids, user_id, goods_num, selected);
    }

    @Override
    public Flowable<HttpResult<String>> cartDelete(String ids, String user_id) {
        return remoteDataSource.cartDelete(ids, user_id);
    }

    @Override
    public Flowable<HttpResult<OrderData>> cartSettlement(String from, String user_id) {
        return remoteDataSource.cartSettlement(from, user_id);
    }

    @Override
    public Flowable<HttpResult<OrderData>> directOrder(String from, String goods_id, String user_id, String item_ids, String goods_num) {
        return remoteDataSource.directOrder(from, goods_id, user_id, item_ids, goods_num);
    }

    @Override
    public Flowable<HttpResult<AdressListData>> addressList(String user_id) {
        return remoteDataSource.addressList(user_id);
    }

    @Override
    public Flowable<HttpResult<String>> saveAddress(String user_id, String act_way, String province_name, String city_name, String district_name, String town_name, String address, String zipcode, String is_default, String address_id, String consignee, String mobile, String province_id, String city_id, String district_id, String town_id) {
        return remoteDataSource.saveAddress(user_id, act_way, province_name, city_name, district_name, town_name, address, zipcode, is_default, address_id, consignee, mobile, province_id, city_id, district_id, town_id);
    }

    @Override
    public Flowable<HttpResult<AddressData>> getProvince() {
        return remoteDataSource.getProvince();
    }

    @Override
    public Flowable<HttpResult<AddressData>> getCity(String province_id) {
        return remoteDataSource.getCity(province_id);
    }

    @Override
    public Flowable<HttpResult<AddressData>> getDistrict(String city_id) {
        return remoteDataSource.getDistrict(city_id);
    }

    @Override
    public Flowable<HttpResult<AddressData>> getTown(String district_id) {
        return remoteDataSource.getTown(district_id);
    }

    @Override
    public Flowable<HttpResult<OrderResultData>> submitOrder(String from, String goods_id, String user_id, String item_ids, String goods_num, String address_id, String user_note) {
        return remoteDataSource.submitOrder(from, goods_id, user_id, item_ids, goods_num, address_id, user_note);
    }


    @Override
    public Flowable<HttpResult<PayWayData>> payWay(String user_id, String order_no, String order_id) {
        return remoteDataSource.payWay(user_id, order_no, order_id);
    }

    @Override
    public Flowable<HttpResult<PayOrderData>> payOrder(String user_id, String order_no, String order_id, String code) {
        return remoteDataSource.payOrder(user_id, order_no, order_id, code);
    }

    @Override
    public Flowable<HttpResult<MyOrderData>> myOrderList(String user_id, String order_state, String page, String pagesize) {
        return remoteDataSource.myOrderList(user_id, order_state, page, pagesize);
    }

    @Override
    public Flowable<HttpResult<OrderDetailData>> orderDetail(String user_id, String order_id) {
        return remoteDataSource.orderDetail(user_id, order_id);
    }

    @Override
    public Flowable<HttpResult<String>> cancelOrder(String user_id, String order_id, String cancel_reason) {
        return remoteDataSource.cancelOrder(user_id, order_id, cancel_reason);
    }

    @Override
    public Flowable<HttpResult<String>> deleteOrder(String user_id, String order_id) {
        return remoteDataSource.deleteOrder(user_id, order_id);
    }

    @Override
    public Flowable<HttpResult<String>> confirmReceipt(String user_id, String order_id) {
        return remoteDataSource.confirmReceipt(user_id, order_id);
    }

    @Override
    public Flowable<HttpResult<String>> remindingSend(String user_id, String order_id) {
        return remoteDataSource.remindingSend(user_id, order_id);
    }

}

package google.architecture.coremodel.datamodel.http;

import google.architecture.coremodel.BuildConfig;

/**
 * @author lq.zeng
 * @date 2018/4/8
 */

public class ApiConstants {

    // OkHttp多Uri配置[@Headers({HEADER_MULTI_URL})]
    public static final String HEADER_prefix = "url_prefix";
    public static final String HEADER_OTHER = "other";
    public static final String HEADER_TECENT = "tecent";
    public static final String HEADER_UAV = "uav";
    public static final String HEADER_MULTI_URL = HEADER_prefix + ":" + HEADER_OTHER;
    public static final String HEADER_TECENT_URL = HEADER_prefix +":"+ HEADER_TECENT;
    public static final String HEADER_UAV_URL = HEADER_prefix +":"+ HEADER_UAV;

    //配置Headers(),post请求时不加密
    public static final String HEADER_UN_EN_PARAMS_prefix = "un_en_params_prefix";
    public static final String HEADER_UN_EN_PARAMS_VALUE = "true";
    public static final String HEADER_UN_EN_PARAMS = HEADER_UN_EN_PARAMS_prefix + ":" + HEADER_UN_EN_PARAMS_VALUE;

    //配置Headers(),post请求使用Request Payload方式
    public static final String HEADER_USE_JSON_REQUEST_prefix = "use_json_request";

    public static final String HEADER_USE_JSON_REQUEST_VALUE = "payload";
    public static final String HEADER_USE_JSON_REQUEST = HEADER_USE_JSON_REQUEST_prefix + ":" + HEADER_USE_JSON_REQUEST_VALUE;

    public static final String HEADER_USE_FORM_REQUEST_VALUE = "form";
    public static final String HEADER_USE_FROM_REQUEST = HEADER_USE_JSON_REQUEST_prefix + ":" + HEADER_USE_FORM_REQUEST_VALUE;

    public static final String HEADER_USE_TEXT_REQUEST_VALUE = "text";
    public static final String HEADER_USE_TEXT_REQUEST = HEADER_USE_JSON_REQUEST_prefix + ":" + HEADER_USE_TEXT_REQUEST_VALUE;

    // 设置超时
    public static final long DEFAULT_CONNECT_TIMEOUT = 10L;
    public static final long DEFAULT_READ_TIMEOUT = DEFAULT_CONNECT_TIMEOUT + 5L;
    public static final long DEFAULT_WRITE_TIMEOUT = DEFAULT_READ_TIMEOUT + 5L;

    // 项目主地址
    public static final String URLHost = BuildConfig.APP_REQUEST_URL;
    // 项目分地址
    public static String GankHost = "https://xljapi.s.cn/";//"https://mwx.s.cn/";
    public static String TecentHost = "https://api.weixin.qq.com/";
    public static String XLJimgHost = "https://xljapi.s.cn/";//新乐纪图片服务器地址
    public static String XLJuavHost = "https://api2.3dculab.com/";//扫脚登录
    //项目分地址
    public static final String GoodsHost = "http://7xij5m.com1.z0.glb.clouddn.com/spRecommend.txt";

    // 项目分割
    public static final String URL_BASE = "/";

    // 测试地址1
    public static final String Intelligent_List = URL_BASE + "index/index";
    public static final String StartInfo = URL_BASE + "index/startInfo";
    public static final String Register = URL_BASE + "user/register";
    public static final String Login = URL_BASE + "user/login";
    public static final String QuickLogin = URL_BASE + "user/quickLogin";
    public static final String QrcodeLogin = URL_BASE + "user/qrcodeLogin";
    public static final String UserInfo = URL_BASE + "tool/getuserinfo";
    public static final String SendEmail = URL_BASE + "tool/sendEmailCode";
    public static final String SendShortMsg = URL_BASE + "tool/sendSmsCode";
    public static final String ResetLoginPasswd = URL_BASE + "user_set/resetloginpasswd";
    public static final String ModifyLoginPasswd = URL_BASE + "user_set/editloginpasswd";
    public static final String ModifyEmail = URL_BASE + "user_set/editemail";
    public static final String ModifyCellphone = URL_BASE + "user_set/editcellphone";
    public static final String ModifyNickName = URL_BASE + "user/modifyusernickname";

    public static final String UpLoad = URL_BASE + "tool/commonuploadpic";
    public static final String UpdateHeadPic = URL_BASE + "user/headpic";

    //贴呗(正式线不需要PreFix)
    private static final String T_PreFix = ""; //[api/]
    public static final String T_StartInfo = URL_BASE + T_PreFix + "index/startInfo";
    public static final String T_VersionInfo = URL_BASE + T_PreFix + "index/getVersionInfo";

    //商城-分类
    public static final String ShoppingCategory = URL_BASE + "goods_category/index";
    //商城-分类子分类
    public static final String ShoppingCategoryRight = URL_BASE + "goods_category/nextcontent";
    //商城-首页
    public static final String ShoppingHome = URL_BASE + "index/index";
    //商城-搜索热词
    public static final String ShoppingSearchHotKey = URL_BASE + "index/search";
    //商城-搜索结果
    public static final String ShoppingSearchList = URL_BASE + "index/search_list";
    //商城-详情页
    public static final String ShoppingCommodity = URL_BASE + "goods/item";
    //商城-详情页规格选择
    public static final String ShoppingChoiceGoodsSpec = URL_BASE + "goods/changegoodsspec";
    //商城-收藏
    public static final String ShoppingFavorites = URL_BASE + "goods/collectgoods";
    //商城-删除收藏
    public static final String ShoppingDelFavorites = URL_BASE + "user/delcollection";
    //商城-收藏列表
    public static final String ShoppingFavoritesList = URL_BASE + "user/collection";
    //商城-足迹
    public static final String ShoppingFootprintList = URL_BASE + "user/uservisitlog";
    //商城-删除用户足迹
    public static final String ShoppingFootprintDel = URL_BASE + "user/delvisitlog";
    //商城-评论页
    public static final String ShoppingComments = URL_BASE + "comment/index";
    //商城-评论详情
    public static final String ShoppingCommentsDetail = URL_BASE + "comment/orderdetail";
    //商城-评论提交
    public static final String ShoppingCommentsCommit = URL_BASE + "comment/comment";
    //商城-评论提交
    public static final String ShoppingFeedBackCommit = URL_BASE + "user/feedback";
    //商城-加入购物车
    public static final String ShoppingAddCart = URL_BASE + "cart/add";
    //商城-购物车
    public static final String ShoppingCart = URL_BASE + "cart/mycart";
    //商城-更新购物车
    public static final String ShoppingCartUpdate = URL_BASE + "cart/edit";
    //商城-删除购物车
    public static final String ShoppingCartDelete = URL_BASE + "cart/delete";
    //商城-购物车结算
    public static final String ShoppingCartSettlement = URL_BASE + "order/confirm_order";
    //商城-确认订单(直购)
    public static final String ShoppingDirectOrder = URL_BASE + "order/confirm_order";
    //商城-地址
    public static final String ShoppingAddressList = URL_BASE + "user/addresslist";
    //商城-地址
    public static final String ShoppingSaveAddress = URL_BASE + "user/saveaddress";
    //商城-地址-省份
    public static final String ShoppingAddressProvince = URL_BASE + "tool/getprovinceaddress";
    //商城-地址-城市
    public static final String ShoppingAddressCity = URL_BASE + "tool/getcityaddress";
    //商城-地址-地区
    public static final String ShoppingAddressDistrict = URL_BASE + "tool/getdistrictaddress";
    //商城-地址-乡镇
    public static final String ShoppingAddressTown = URL_BASE + "tool/gettownaddress";

    //商城-提交订单(购物车购)
    public static final String ShoppingSubmitOrder = URL_BASE + "order/submit_order";

    //商城-订单支付方式
    public static final String ShoppingPayWay = URL_BASE + "payment/payway";
    //商城-订单生成
    public static final String ShoppingPayOrder = URL_BASE + "payment/payorder";
    //商城-我的订单
    public static final String ShoppingMyOrderList = URL_BASE + "order/myorder";
    //商城-订单详情
    public static final String ShoppingOrderDetail = URL_BASE + "order/detail";
    //商城-取消订单
    public static final String ShoppingCancelOrder = URL_BASE + "order/cancel";
    //商城-删除订单
    public static final String ShoppingDeleteOrder = URL_BASE + "order/delete";
    //商城-确认收货
    public static final String ShoppingConfirmReceipt = URL_BASE + "order/receivesure";
    //商城-提醒发货
    public static final String ShoppingReminding = URL_BASE + "order/remindsend";


    //##======新乐纪======##
    public static final String XLJ_Shop_Data = URL_BASE + "app/Shops/getShopData";
    public static final String XLJ_Goods_List = URL_BASE + "app/Lists/goodsList";
    public static final String XLJ_Goods_Detail = URL_BASE + "app/Goods/goodsDetail";
    public static final String XLJ_Cat_TOP = URL_BASE + "app/GoodsCats/getTopCat";
    public static final String XLJ_Cat_Child = URL_BASE + "app/GoodsCats/getChildCat";
    public static final String XLJ_Get_User_token = URL_BASE + "app/Login/appWxlogin";
    public static final String XLJ_Get_Refresh_token = URL_BASE + "app/Login/getRefreshToken";
    public static final String XLJ_Get_User_Info = URL_BASE + "app/Users/index";
    public static final String XLJ_Upload_Device_Info = URL_BASE + "app/Goods/setDvToken";
    public static final String XLJ_Get_Promotion_Media = URL_BASE + "app/Shops/getMedia";
    public static final String XLJ_Get_Tecent_AccessToken = URL_BASE + "cgi-bin/token";
    public static final String XLJ_Get_Tecent_Ticket = URL_BASE + "cgi-bin/ticket/getticket";
    public static final String XLJ_Get_Tecent_WX_OpenId = URL_BASE + "sns/oauth2/access_token";
    public static final String XLJ_Get_FootScan_Token = URL_BASE + "UAV2.0.0/api/PhoneVm/CheckPhoneNumForUA";
    public static final String XLJ_Get_App_Version_Info = URL_BASE + "app/shops/getUpdate";
    public static final String XLJ_Get_Try_Data = URL_BASE + "app/TryApi/getTryData";

    public static int IDLE_SECOND = 30;//系统空闲时间

    public static boolean isConnectFootScan = false;//是否连接扫脚仪(默认为否)

}

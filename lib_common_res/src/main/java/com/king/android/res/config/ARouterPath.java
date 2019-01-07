package com.king.android.res.config;

/**
 * 路由path<p>
 * 注意：【多组件之间需要独立分组】<p>
 * Aty : Activity
 * Fgt : Fragment
 */

public class ARouterPath {
    public static final String PRE_FIX = "/"; //开始符
    public static final String APP_TAG = "ZDZ_Shopping_"; //App标识

    public static final String AppMainAty = PRE_FIX + APP_TAG + "/main/aty/page";
    public static final String AppSplashAty = PRE_FIX + APP_TAG + "/splash/aty/page";

    //Home界面
    public static final String HomeAty = PRE_FIX + APP_TAG + "home/aty/page";
    public static final String HomeFgt = PRE_FIX + APP_TAG + "home/fgt/page";
    public static final String SearchAty = PRE_FIX + APP_TAG + "home/search/aty/page";
    public static final String Search2Aty = PRE_FIX + APP_TAG + "home/search2/aty/page";
    public static final String FilterMainFgt = PRE_FIX + APP_TAG + "home/filter/main/fgt/page";

    //购物车界面
    public static final String CartAty = PRE_FIX + APP_TAG + "cart/aty/page";
    public static final String CartFgt = PRE_FIX + APP_TAG + "cart/fgt/page";

    //分类界面
    public static final String CategoryFgt = PRE_FIX + APP_TAG + "category/fgt/page";

    //详情界面
    public static final String DetailAty = PRE_FIX + APP_TAG + "detail/aty/page";
    public static final String DetailCommentFgt = PRE_FIX + APP_TAG + "detail/comment/fgt/page";
    public static final String DetailCommodityFgt = PRE_FIX + APP_TAG + "detail/commodity/fgt/page";
    public static final String DetailRecommendFgt = PRE_FIX + APP_TAG + "detail/recommend/fgt/page";
    public static final String DetailDetailFgt = PRE_FIX + APP_TAG + "detail/detail/fgt/page";
    public static final String DetailGridRecommendFgt = PRE_FIX + APP_TAG + "detail/recommend/grid/fgt/page";

    //Web界面
    public static final String WebPage = PRE_FIX + APP_TAG + "web/aty/page";

    /**个人中心Activity*/
    public static final String PersonalAty = PRE_FIX + APP_TAG + "personal/aty/page";
    /**个人中心Fragment-商城个人*/
    public static final String PersonalShoppingFgt = PRE_FIX + APP_TAG + "personal/shopping/fgt/page";
    /**个人中心Fragment-商城我的订单Activity*/
    public static final String PersonalShoppingOrderAty = PRE_FIX + APP_TAG + "personal/shopping/order/aty/page";
    /**个人中心Fragment-商城我的评论Activity*/
    public static final String PersonalShoppingCommentAty = PRE_FIX + APP_TAG + "personal/shopping/comment/aty/page";
    /**个人中心Fragment-商城我的评论详情Activity*/
    public static final String PersonalShoppingCommentDetailAty = PRE_FIX + APP_TAG + "personal/shopping/comment_detail/aty/page";
    /**个人中心Fragment*/
    public static final String PersonalFgt = PRE_FIX + APP_TAG + "personal/fgt/page";
    public static final String PersonalLikeFgt = PRE_FIX + APP_TAG + "personal/like/fgt/page";
    /**个人中心用户反馈**/
    public static final String PersonalFeedBackAty = PRE_FIX + APP_TAG + "personal/feedback/aty/page";
    /**个人中心我的收藏**/
    public static final String PersonalFavoritesAty = PRE_FIX + APP_TAG + "personal/favorites/aty/page";
    /**个人中心足迹**/
    public static final String PersonalFootprintsAty = PRE_FIX + APP_TAG + "personal/footprint/aty/page";
    /**个人中心登录Activity*/
    public static final String PersonalLoginAty = PRE_FIX + APP_TAG + "personal/aty/login";
    /**个人中心登录-商城Activity*/
    public static final String PersonalLoginShoppingAty = PRE_FIX + APP_TAG + "personalshopping/aty/login";
    /**个人中心重置登录Activity*/
    public static final String PersonalResetLoginAty = PRE_FIX + APP_TAG + "personal/aty/reset_login";
    /**个人中心修改登录Activity*/
    public static final String PersonalModifyLoginAty = PRE_FIX + APP_TAG + "personal/aty/modify_login";
    /**个人中心修改个人账户Activity*/
    public static final String PersonalModifyAccountAty = PRE_FIX + APP_TAG + "personal/aty/modify_account";
    /**个人中心设置Activity*/
    public static final String PersonalSettingAty = PRE_FIX + APP_TAG + "personal/aty/setting";
    /**个人中心设置-账户安全Activity*/
    public static final String PersonalSettingAccountSafeAty = PRE_FIX + APP_TAG + "personal/aty/setting/safe/aty/page";
    /**个人中心个人信息Activity*/
    public static final String PersonalInfoAty = PRE_FIX + APP_TAG + "personal/aty/info";
    /**设备信息*/
    public static final String DeviceInfoAty = PRE_FIX + APP_TAG + "personal/aty/device_info";
    /**域名地址设置*/
    public static final String DomainInfoAty = PRE_FIX + APP_TAG + "personal/aty/domain_info";
    /**系统空闲时间设置*/
    public static final String IdleTimeAty = PRE_FIX + APP_TAG + "personal/aty/idle_time";
    /**微信扫码登录页面*/
    public static final String WeixinLoginAty = PRE_FIX + APP_TAG + "personal/aty/weixin_login";
    /**广告宣传页面*/
    public static final String PromotionAty = PRE_FIX + APP_TAG + "common/aty/promotion";

    /**个人中心我的订单-全部*/
    public static final String PersonalAllOrderListFgt = PRE_FIX + APP_TAG + "personal/myorder/fgt/all_order";
    /**个人中心我的订单-待付款*/
    public static final String PersonalWaitingPayListFgt = PRE_FIX + APP_TAG + "personal/myorder/fgt/waiting_pay";
    /**个人中心我的订单-待收货*/
    public static final String PersonalWaitingReceiptListFgt = PRE_FIX + APP_TAG + "personal/myorder/fgt/waiting_receipt";
    /**个人中心我的订单-待评价*/
    public static final String PersonalWaitingCommentListFgt = PRE_FIX + APP_TAG + "personal/myorder/fgt/waiting_comment";
    /**个人中心我的订单-已完成*/
    public static final String PersonalCancelListFgt = PRE_FIX + APP_TAG + "personal/myorder/fgt/cancel";
    /**个人中心我的订单-已取消*/
    public static final String PersonalFinishListFgt = PRE_FIX + APP_TAG + "personal/myorder/fgt/finish";
    /**个人中心我的订单-详情*/
    public static final String PersonalShoppingOrderDetailAty = PRE_FIX + APP_TAG + "personal/shopping/order_detail/aty/page";

    /**鸿利宝列表Fragment*/
    public static final String ItelligentListFgt = PRE_FIX + APP_TAG + "bids/itelligent/fgt/list";

    /**关于Fragment*/
    public static final String AboutFgt = PRE_FIX + APP_TAG + "about/fgt/fragment";

    public static final String OrderAddressChoiceActy = PRE_FIX + APP_TAG + "address/choice/aty/page";
    public static final String OrderAddressAddActy = PRE_FIX + APP_TAG + "address/add/aty/page";
    public static final String OrderAddressManageActy = PRE_FIX + APP_TAG + "address/manage/aty/page";

    public static final String OrderPayActy = PRE_FIX + APP_TAG + "order/pay/aty/page";
    public static final String OrderMainActy = PRE_FIX + APP_TAG + "order/main/aty/page";

}

package google.architecture.coremodel.datamodel.http.event;

import android.os.Bundle;

/**
 * @author lq.zeng
 * @date 2018/4/19
 */

public class CommEvent {
    public static final String MSG_TYPE_TOKEN_EXPIRE = "token_expire";
    public static final String MSG_TYPE_OPEN_SCAN = "open_scan";
    public static final String MSG_TYPE_CHECK_NET = "check_net";
    public static final String MSG_TYPE_HOME_GO = "home_go";
    /**更新用户数据**/
    public static final String MSG_TYPE_UPDATE_USER_INFO = "update_user_info";
    /**更新购物车与收藏**/
    public static final String MSG_TYPE_UPDATE_FAVORITE_CART = "update_favorite_cart";
    /**更新购物车**/
    public static final String MSG_TYPE_UPDATE_CART = "update_cart";
    /**一级界面购物车更新**/
    public static final String MSG_TYPE_UPDATE_CART_MAIN = "update_cart_main";
    /**规格更新通知*/
    public static final String MSG_TYPE_GOODS_SPC_UPDATE = "goods_spc_update";
    /**配送**/
    public static final String MSG_TYPE_OPEN_AREA = "open_area";
    public static final String MSG_TYPE_CLOSE_AREA = "close_area";
    /**全部分类**/
    public static final String MSG_TYPE_OPEN_ALL_CATE = "open_all_cate";
    public static final String MSG_TYPE_CLOSE_ALL_CATE = "close_all_cate";
    /**关闭侧边栏**/
    public static final String MSG_TYPE_CLOSE_ALL_FILTER = "close_all_filter";
    /**关闭页面**/
    public static final String MSG_TYPE_CLOSE_PAGE = "close_page";
    /**搜索结果**/
    public static final String MSG_TYPE_CLOSE_SEARCH2 = "close_search2";
    /**通知地址选择结果**/
    public static final String MSG_TYPE_NOTIFY_UPDATE = "notify_update";
    /**帅选结果**/
    public static final String MSG_TYPE_FILTER_RESULT = "filter_result";

    /**通知动画开启**/
    public static final String MSG_TYPE_OPEN_ANIMATOR = "open_animator";

    /**更新用户所有订单**/
    public static final String MSG_TYPE_UPDATE_USER_ALL_ORDER = "update_user_all_order";
    /**更新用户待付款订单**/
    public static final String MSG_TYPE_UPDATE_USER_WAITING_PAY_ORDER = "update_user_waiting_pay_order";
    /**更新用户待收货订单**/
    public static final String MSG_TYPE_UPDATE_USER_WAITING_RECEIPT_ORDER = "update_user_waiting_receipt_order";
    /**更新用户已完成订单**/
    public static final String MSG_TYPE_UPDATE_USER_FINISH_ORDER = "update_user_finish_order";
    /**更新用户已取消订单**/
    public static final String MSG_TYPE_UPDATE_USER_CANCEL_ORDER = "update_user_cancel_order";

    /**传参**/
    public static final String KEY_EXTRA_VALUE = "extra_value";
    public static final String KEY_EXTRA_VALUE_2 = "extra_value_2";

    public String msgType;
    public Bundle bundle;

    public CommEvent(String msgType){
        this.msgType = msgType;
    };

    public CommEvent(String msgType, Bundle bundle){
        this.msgType = msgType;
        this.bundle = bundle;
    };
}

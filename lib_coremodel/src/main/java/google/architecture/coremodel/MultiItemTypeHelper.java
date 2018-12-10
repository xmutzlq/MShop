package google.architecture.coremodel;

/**
 * @author lq.zeng
 * @date 2018/5/10
 */

public final class MultiItemTypeHelper {
    //购物车
    public static final int TYPE_BUSINESSES = 0;
    public static final int TYPE_BUSINESSES_GOODS = 1;
    public static final int TYPE_GUESS_YOU_LIKE = 2;
    public static final int TYPE_GUESS_YOU_LIKE_TITLE = 3;

    public static final int TYPE_EMPTY = 4;
    public static final int TYPE_LINE = 5;
    //个人中心
    public static final int TYPE_PERSONAL_TITLE = 6;
    public static final int TYPE_PERSONAL_ORDER = 7;
    public static final int TYPE_PERSONAL_SERVICE = 8;

    //订单界面
    public static final int TYPE_ORDER_BUSINESSES = 9;
    public static final int TYPE_ORDER_BUSINESSES_GOODS = 10;
    public static final int TYPE_ORDER_LEFT = 11;
    public static final int TYPE_ORDER_PAY = 12;
    public static final int TYPE_ORDER_ADDRESS = 13;
    public static final int TYPE_ORDER_STATE = 14;

    //图片上传
    public static final int TYPE_IMG_UPLOAD = 0;
    public static final int TYPE_IMG_UPLOAD_HOLDER = 15;
}

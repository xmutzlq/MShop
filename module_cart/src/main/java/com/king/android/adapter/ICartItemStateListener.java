package com.king.android.adapter;

/**
 * @author lq.zeng
 * @date 2018/9/5
 */

public interface ICartItemStateListener {
    boolean enableShopChecked(boolean shopEnable);
    boolean enableGoodsChecked(int goodsState);
}

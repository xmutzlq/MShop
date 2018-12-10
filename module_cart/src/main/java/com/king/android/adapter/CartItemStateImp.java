package com.king.android.adapter;

/**
 * @author lq.zeng
 * @date 2018/9/5
 */

public class CartItemStateImp implements ICartItemStateListener {

    private CartResultHelper helper;

    public CartItemStateImp(CartResultHelper helper) {
        this.helper = helper;
    }

    @Override
    public boolean enableShopChecked(boolean shopEnable) {
        return shopEnable //所有商品缺货
                || helper != null && CartResultHelper.STATE_DELETE == helper.getControllState(); //用户删除状态
    }

    @Override
    public boolean enableGoodsChecked(int goodsState) {
        return !(goodsState == -1) //商品缺货
                || helper != null && CartResultHelper.STATE_DELETE == helper.getControllState(); //用户删除状态
    }
}

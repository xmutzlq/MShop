package com.king.android.adapter;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/6
 */

public interface CartResultHelperApi<T> {

    public static final int STATE_NONE = -1;
    public static final int STATE_DELETE = 0;

    /**获取总件数**/
    public void getCountResult(List<T> cartData, ICountResult countResult);

    /**获取总价格**/
    public void getMoneyResult(List<T> cartData, IMoneyResult moneyResult);

    /**检测全选状态**/
    public void checkAllState(List<T> cartData, ICartItemStateListener cartItemStateListener, ICheckAllState checkAllState);

    /**全选反选**/
    public void notifyAllChecked(List<T> cartData, boolean isCheckedAll, ICartItemStateListener cartItemStateListener, INotifyAllChecked notifyAllChecked);

    /**获取所有购物车Id，根据cartData不同可以取得对应的商店购物车Id**/
    public void getCartIds(List<T> cartData, StringBuilder ids, ICartItemStateListener cartItemStateListener, IPostIdsResult postIdsResult);

    public boolean checkOnNetWork();

    public interface IMoneyResult {
        void onMoneyResult(double money);
    }

    public interface ICountResult {
        void onCountResult(int count);
    }

    public interface ICheckAllState {
        void onCheckAllState(boolean canCheckAll);
    }

    public interface INotifyAllChecked {
        void onNotifyAllChecked();
    }

    public interface IPostIdsResult {
        void onIdsResult(String ids);
    }
}

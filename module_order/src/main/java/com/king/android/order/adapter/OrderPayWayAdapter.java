package com.king.android.order.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.order.R;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.coremodel.data.PayWayItemData;

/**
 * @author lq.zeng
 * @date 2018/9/11
 */

public class OrderPayWayAdapter extends BaseQuickAdapter<PayWayItemData, BaseViewHolder> {

    private int prePosition;

    public OrderPayWayAdapter(int layoutResId, @Nullable List<PayWayItemData> data) {
        super(layoutResId, data);
    }

    public int getPosition() {
        return prePosition;
    }

    @Override
    protected void convert(BaseViewHolder helper, PayWayItemData item) {
        ImageLoader.get().load(helper.getView(R.id.pay_way_iv), item.getIcon());
        helper.setText(R.id.pay_way_tv, item.getName());
        helper.setChecked(R.id.pay_way_check, item.isChecked);
    }

    public void setChecked(int position) {
        if(getData().get(position).isChecked) return;
        getData().get(position).isChecked = true;
        getData().get(prePosition).isChecked = false;
        prePosition = position;
    }
}

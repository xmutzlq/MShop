package com.king.android.order.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.order.R;

import java.util.List;

import google.architecture.coremodel.data.AdressListData;

/**
 * @author lq.zeng
 * @date 2018/9/10
 */

public class AdressManageAdapter extends BaseQuickAdapter<AdressListData.AddressListItem, BaseViewHolder> {

    public static final String TAG_EDIT = "edit";
    public static final String TAG_DEL = "del";
    public static final String TAG_CHECK = "check";

    public AdressManageAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdressListData.AddressListItem item) {
        helper.setTag(R.id.btn_address_edit, TAG_EDIT);
        helper.setTag(R.id.btn_address_del, TAG_DEL);
        helper.setTag(R.id.btn_default_address_cb, TAG_CHECK);
        helper.addOnClickListener(R.id.btn_address_edit);
        helper.addOnClickListener(R.id.btn_address_del);
        helper.addOnClickListener(R.id.btn_default_address_cb);
        helper.setText(R.id.address_info_consignee, item.getConsignee());
        helper.setText(R.id.address_info_consignee_phone, item.getMobile());
        String addressStr = item.getAddress();
        helper.setText(R.id.address_info_default, addressStr);
        helper.setGone(R.id.address_info_cb, false);

        helper.setChecked(R.id.address_default_cb, item.getIs_default() == 1);
    }
}

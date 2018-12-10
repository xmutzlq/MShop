package com.king.android.order.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.order.R;

import java.util.List;

import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.data.AdressListData;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class AdressChoiceAdapter extends BaseQuickAdapter<AdressListData.AddressListItem, BaseViewHolder>{

    public AdressChoiceAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdressListData.AddressListItem item) {
        helper.itemView.setClickable(true);
        helper.itemView.setBackgroundResource(R.drawable.bg_gv);
        helper.setText(R.id.address_info_consignee, item.getConsignee());
        helper.setText(R.id.address_info_consignee_phone, item.getMobile());
        String addressStr = item.getAddress();
        helper.setText(R.id.address_info_default, addressStr);
        if(item.getIs_default() == 1) {
            addressStr = "[默认地址]" + addressStr;
            helper.setText(R.id.address_info_default, Spans.builder().text(addressStr)
                    .newSpanPart(0, 6, new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_FD644B))).build());
        }
        helper.setChecked(R.id.address_info_cb, item.isSelected);
    }

    public void setChecked(int position) {
        if(getData().get(position).isSelected) return;
        getData().get(position).isSelected = !getData().get(position).isSelected;
        notifyItemChanged(position);
    }
}

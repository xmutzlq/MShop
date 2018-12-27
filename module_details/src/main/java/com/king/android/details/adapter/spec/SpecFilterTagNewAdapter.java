package com.king.android.details.adapter.spec;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.king.android.details.R;

import java.util.HashSet;

import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.widget.CommFlowLayout;
import google.architecture.common.widget.adapter.TagAdapter;
import google.architecture.coremodel.data.xlj.goodsdetail.List;

/**
 * @author lq.zeng
 * @date 2018/7/12
 */

public class SpecFilterTagNewAdapter extends TagAdapter<List> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public SpecFilterTagNewAdapter(Context context, java.util.List<List> datas) {
        super(datas);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(CommFlowLayout parent, int position, List info) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = DimensionsUtil.dip2px(mContext, 12);
        final TextView tv = (TextView) mLayoutInflater.inflate(R.layout.filter_item_tag, parent, false);
        tv.setLayoutParams(layoutParams);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tv.setPadding(DimensionsUtil.DIPToPX(15), DimensionsUtil.DIPToPX(5),
                DimensionsUtil.DIPToPX(15), DimensionsUtil.DIPToPX(5));
        tv.setText(info.getItemName());
        tv.setTextColor(info.getIsDefault() == 1 ? R.drawable.bg_filter_tag : ContextCompat.getColor(mContext, R.color.color_e1e1e1));
        return tv;
    }

    @Override
    public HashSet<Integer> getPreCheckedList() {
        HashSet<Integer> preSet = new HashSet<>();
        for (int i = 0; i < getData().size(); i++) {
            if(getData().get(i).getIsDefault() == 1) {
                preSet.add(i);
            }
        }
        return preSet;
    }

    @Override
    public void onSelected(int position, View view) {
        getData().get(position).setIsDefault(1);
    }

    @Override
    public void unSelected(int position, View view) {
        getData().get(position).setIsDefault(0);
    }

    @Override
    public boolean isEnabled(int position) {
        return TextUtils.isEmpty(getData().get(position).getShopStock()) ||
                Integer.valueOf(getData().get(position).getShopStock()) > 0;
    }
}

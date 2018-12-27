package com.king.android.details.adapter.spec.provider;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;
import com.king.android.details.adapter.spec.SpecAdapter;
import com.king.android.details.cache.SpecData;

import google.architecture.common.widget.adapter.BaseItemProvider;
import google.architecture.coremodel.util.TextUtil;

/**
 * @author lq.zeng
 * @date 2018/7/13
 */

public class CountSpecProvider extends BaseItemProvider<SpecData, BaseViewHolder> {

    @Override
    public int viewType() {
        return SpecAdapter.TYPE_SERVICE;
    }

    @Override
    public int layout() {
        return R.layout.layout_spec_item_count;
    }

    @Override
    public void convert(BaseViewHolder helper, SpecData data, int position) {
        TextView decrease = helper.getView(R.id.btnDecrease);
        TextView increase = helper.getView(R.id.btnIncrease);
        EditText input = helper.getView(R.id.etAmount);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtil.isEmpty(s)) { //最小1
                    input.setText("1");
                } else {
                    if(Integer.valueOf(s.toString()) < 1) { //最小1
                        input.setText("1");
                    } else if(Integer.valueOf(s.toString()) > 200) { //最大200
                        input.setText("200");
                    }

                    decrease.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_404040));
                    increase.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_404040));

                    if(Integer.valueOf(input.getText().toString()) == 1) {
                        decrease.setTextColor(ContextCompat.getColor(mContext, R.color.gray_cc));
                        increase.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_404040));
                    }

                    if(Integer.valueOf(input.getText().toString()) == 200) {
                        decrease.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_404040));
                        increase.setTextColor(ContextCompat.getColor(mContext, R.color.gray_cc));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        decrease.setOnClickListener(v -> {
            int cNum = Integer.valueOf(input.getText().toString()) - 1;
            input.setText(String.valueOf(cNum));
            data.count = cNum;
        });
        increase.setOnClickListener(v -> {
            int cNum = Integer.valueOf(input.getText().toString()) + 1;
            input.setText(String.valueOf(cNum));
            data.count = cNum;
        });
    }

    @Override
    public void onClick(BaseViewHolder helper, SpecData data, int position) {

    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, SpecData data, int position) {
        return true;
    }
}

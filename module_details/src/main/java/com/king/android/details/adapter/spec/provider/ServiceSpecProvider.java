package com.king.android.details.adapter.spec.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;
import com.king.android.details.adapter.spec.SpecAdapter;
import com.king.android.details.cache.SpecData;

import google.architecture.common.widget.adapter.BaseItemProvider;

/**
 * @author lq.zeng
 * @date 2018/7/13
 */

public class ServiceSpecProvider extends BaseItemProvider<SpecData, BaseViewHolder> {

    @Override
    public int viewType() {
        return SpecAdapter.TYPE_COUNT;
    }

    @Override
    public int layout() {
        return R.layout.layout_spec_item_count;
    }

    @Override
    public void convert(BaseViewHolder helper, SpecData data, int position) {
    }

    @Override
    public void onClick(BaseViewHolder helper, SpecData data, int position) {
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, SpecData data, int position) {
        return true;
    }
}

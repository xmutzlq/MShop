package com.king.android.details.adapter.spec.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;
import com.king.android.details.adapter.spec.SpecAdapter;

import google.architecture.common.widget.adapter.BaseItemProvider;

public class ConfirmSpecProvider extends BaseItemProvider {
    @Override
    public int viewType() {
        return SpecAdapter.TYPE_CONFIRM;
    }

    @Override
    public int layout() {
        return R.layout.layout__spec_item_confirm;
    }

    @Override
    public void convert(BaseViewHolder helper, Object data, int position) {

    }
}

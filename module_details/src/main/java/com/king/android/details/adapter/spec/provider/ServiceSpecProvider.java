package com.king.android.details.adapter.spec.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;
import com.king.android.details.adapter.spec.SpecAdapter;
import com.king.android.details.adapter.spec.SpecFilterTagNewAdapter;
import com.king.android.details.cache.SpecData;

import google.architecture.common.widget.TagFlowLayout;
import google.architecture.common.widget.adapter.BaseItemProvider;

/**
 * @author lq.zeng
 * @date 2018/7/13
 */

public class ServiceSpecProvider extends BaseItemProvider<SpecData, BaseViewHolder> {

    private ColorSpecProvider.IItemCheckedListener itemCheckedListener;

    public ServiceSpecProvider(ColorSpecProvider.IItemCheckedListener itemCheckedListener) {
        this.itemCheckedListener = itemCheckedListener;
    }

    @Override
    public int viewType() {
        return SpecAdapter.TYPE_SERVICE;
    }

    @Override
    public int layout() {
        return R.layout.layout_spec_item_color;
    }

    @Override
    public void convert(BaseViewHolder helper, SpecData data, int position) {
        helper.setText(R.id.spec_item_color_title, data.name);
        SpecFilterTagNewAdapter adapter = new SpecFilterTagNewAdapter(mContext, data.item1);
        TagFlowLayout tagFlowLayout = helper.getView(R.id.filter_spec_layout);
        tagFlowLayout.setMaxSelectCount(1);
        tagFlowLayout.setOnTagClickListener((view, position1, parent) -> {
            if(itemCheckedListener != null && adapter.getData().get(position1).getIsDefault() == 1) {
                itemCheckedListener.onItemCheckedListener(viewType(), adapter.getData().get(position1).getCatId(), tagFlowLayout.getSelectedList());
            }
            return true;
        });
        tagFlowLayout.setAdapter(adapter);
    }

    @Override
    public void onClick(BaseViewHolder helper, SpecData data, int position) {
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, SpecData data, int position) {
        return true;
    }

}

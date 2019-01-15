package com.king.android.details.adapter.spec;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.adapter.spec.provider.ColorSpecProvider;
import com.king.android.details.adapter.spec.provider.CountSpecProvider;
import com.king.android.details.adapter.spec.provider.ServiceSpecProvider;
import com.king.android.details.cache.SpecData;

import java.util.List;

import google.architecture.common.widget.adapter.MultipleItemRvAdapter;

/**
 * @author lq.zeng
 * @date 2018/7/12
 */

public class SpecAdapter extends MultipleItemRvAdapter<SpecData, BaseViewHolder> {

    public static final int TYPE_COLOR = 100; //颜色
    public static final int TYPE_COUNT = 200; //数量
    public static final int TYPE_SERVICE = 300; //服务
    public static final int TYPE_CONFIRM = 400; //确定

    private ColorSpecProvider.IItemCheckedListener itemCheckedListener;

    public SpecAdapter(@Nullable List<SpecData> data, ColorSpecProvider.IItemCheckedListener itemCheckedListener) {
        super(data);
        this.itemCheckedListener = itemCheckedListener;
        finishInitialize();
    }

    @Override
    protected int getViewType(SpecData specData) {
        if (specData.type == SpecData.TYPE_COLOR) {
            return TYPE_COLOR;
        } else if (specData.type == SpecData.TYPE_COUNT) {
            return TYPE_COUNT;
        } else if (specData.type == SpecData.TEXT_SERVICE) {
            return TYPE_SERVICE;
        } else if (specData.type == SpecData.TEXT_CONFIRM) {
            return TYPE_SERVICE;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new ColorSpecProvider(itemCheckedListener));
        mProviderDelegate.registerProvider(new CountSpecProvider());
        mProviderDelegate.registerProvider(new ServiceSpecProvider(itemCheckedListener));
    }
}

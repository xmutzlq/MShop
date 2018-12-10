package com.king.android.details.adapter;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;

/**
 * @author lq.zeng
 * @date 2018/7/9
 */

public class DetailListTestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DetailListTestAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ViewCompat.setTransitionName(helper.getView(R.id.test_img), mContext.getString(R.string.transitionName_list_img_to_img));
        ImageLoader.get().load(helper.getView(R.id.test_img), item);
    }

}

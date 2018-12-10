package com.king.android.details.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;

/**
 * @author lq.zeng
 * @date 2018/6/26
 */

public class DetailHotRecAdapter extends BaseQuickAdapter<DetailHotRecEntity, BaseViewHolder>{

    public DetailHotRecAdapter(int layoutResId, @Nullable List<DetailHotRecEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailHotRecEntity item) {
        helper.setText(R.id.goods_tv, item.name);
        ImageLoader.get().load(helper.getView(R.id.goods_iv), item.url);
    }
}

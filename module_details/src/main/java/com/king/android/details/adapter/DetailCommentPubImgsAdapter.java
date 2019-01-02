package com.king.android.details.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.coremodel.datamodel.http.ApiConstants;

/**
 * @author lq.zeng
 * @date 2018/6/28
 */

public class DetailCommentPubImgsAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    public DetailCommentPubImgsAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView pubImg = helper.getView(R.id.common_ratio_iv);
        String tmpImg = ApiConstants.GankHost + item;
        ImageLoader.get().load(pubImg, tmpImg);
    }
}

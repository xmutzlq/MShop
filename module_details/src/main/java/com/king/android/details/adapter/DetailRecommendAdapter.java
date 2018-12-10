package com.king.android.details.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.coremodel.data.DetailRecommendInfo;

/**
 * @author lq.zeng
 * @date 2018/6/29
 */

public class DetailRecommendAdapter extends BaseQuickAdapter<DetailRecommendInfo, BaseViewHolder> {

    private boolean isRank;

    public DetailRecommendAdapter(int layoutResId, @Nullable List<DetailRecommendInfo> data) {
        super(layoutResId, data);
    }

    public DetailRecommendAdapter(int layoutResId, @Nullable List<DetailRecommendInfo> data, boolean isRank) {
        super(layoutResId, data);
        this.isRank = isRank;
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailRecommendInfo item) {
        ImageView iconView = helper.getView(R.id.common_ratio_iv);
        ImageLoader.get().load(helper.getView(R.id.common_ratio_iv), item.rec_url, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target< Drawable > target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource
                    dataSource, boolean isFirstResource) {
                iconView.setImageDrawable(resource);
                return false;
            }
        });
        helper.setText(R.id.recommend_item_name_tv, item.rec_title);
        helper.setText(R.id.recommend_item_price_tv, "¥" + item.rec_price);

        if(isRank) {
            ImageView rankRightIv = helper.getView(R.id.recommend_item_right_iv);
            if(helper.getAdapterPosition() == 0) {
                rankRightIv.setImageResource(R.mipmap.ic_rank_n1);
            } else if(helper.getAdapterPosition() == 1) {
                rankRightIv.setImageResource(R.mipmap.ic_rank_n2);
            } else if(helper.getAdapterPosition() == 2) {
                rankRightIv.setImageResource(R.mipmap.ic_rank_n3);
            } else {
                rankRightIv.setImageResource(0);
            }
        }
    }
}

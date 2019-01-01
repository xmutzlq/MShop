package com.king.android.details.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;
import com.king.android.res.config.ARouterPath;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.coremodel.data.xlj.goodsdetail.Like;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/6/29
 */

public class DetailRecommendAdapter extends BaseQuickAdapter<Like, BaseViewHolder> {

    private boolean isRank;

    public DetailRecommendAdapter(int layoutResId, @Nullable List<Like> data) {
        super(layoutResId, data);
        setOnItemClickListener((adapter, view, position) -> {
            String goodsId = getData().get(position).getGoodsId();
            Bundle bundle = new Bundle();
            bundle.putString(CommKeyUtil.EXTRA_KEY, goodsId);
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_OPEN_GOODS_DETAIL_PAGE, bundle));
        });
    }

    public DetailRecommendAdapter(int layoutResId, @Nullable List<Like> data, boolean isRank) {
        super(layoutResId, data);
        this.isRank = isRank;
        setOnItemClickListener((adapter, view, position) -> {
            String goodsId = getData().get(position).getGoodsId();
            ARouter.getInstance().build(ARouterPath.DetailAty)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .withString(CommEvent.KEY_EXTRA_VALUE, goodsId).navigation();
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, Like item) {
        ImageView iconView = helper.getView(R.id.common_ratio_iv);
        String recUrl = ApiConstants.GankHost + item.getGoodsImg();
        ImageLoader.get().load(helper.getView(R.id.common_ratio_iv), recUrl, new RequestListener<Drawable>() {
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
        helper.setText(R.id.recommend_item_name_tv, item.getGoodsName());
        helper.setText(R.id.recommend_item_price_tv, "¥" + item.getShopPrice());
        helper.setText(R.id.recommend_item_market_price_tv, "¥" + item.getMarketPrice());
        ((TextView)helper.getView(R.id.recommend_item_market_price_tv)).getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);

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

package com.king.android.details.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.widget.rating.CBRatingBar;
import google.architecture.coremodel.data.DetailCommentInfo;

/**
 * @author lq.zeng
 * @date 2018/6/28
 */

public class DetailCommentAdapter extends BaseQuickAdapter<DetailCommentInfo, BaseViewHolder> {


    public DetailCommentAdapter(int layoutResId, @Nullable List<DetailCommentInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailCommentInfo item) {
        CircleImageView circleImageView = helper.getView(R.id.detail_commodity_comment_u_head_ico);
        ImageLoader.get().load(helper.getView(R.id.detail_commodity_comment_u_head_ico), item.getHead_pic(), new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                circleImageView.setImageDrawable(resource);
                return false;
            }
        });
        helper.setText(R.id.detail_commodity_comment_u_head_nick, item.getUsername());
        CBRatingBar starsView = helper.getView(R.id.detail_commodity_comment_u_start);
//        starsView.setStarProgress(Float.valueOf(item.userCommentStarts));
        starsView.setVisibility(View.GONE);
        helper.setText(R.id.detail_commodity_comments, item.getContent());

        DetailCommentPubImgsAdapter commentPubImgsAdapter = new DetailCommentPubImgsAdapter(R.layout.layout_common_ratio_imgeview, item.getImage());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        RecyclerView recyclerView = helper.getView(R.id.detail_commodity_comment_pub_rv);
        recyclerView.addItemDecoration(new DetailDividerItemPubImgDecoration(mContext, DimensionsUtil.DIPToPX(5)));
        recyclerView.setAdapter(commentPubImgsAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        helper.setText(R.id.detail_commodity_comments_buy_goods, item.getSpec_depict());
    }

}

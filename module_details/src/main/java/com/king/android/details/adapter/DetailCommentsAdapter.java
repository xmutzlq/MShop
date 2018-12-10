package com.king.android.details.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.king.android.details.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.widget.ExpandableTextView;
import google.architecture.coremodel.data.DetailCommentsInfo;

/**
 * @author lq.zeng
 * @date 2018/8/29
 */

public class DetailCommentsAdapter extends BaseQuickAdapter<DetailCommentsInfo, BaseViewHolder> {

    private final SparseBooleanArray mCollapsedStatus;

    public DetailCommentsAdapter(int layoutResId, @Nullable List<DetailCommentsInfo> data) {
        super(layoutResId, data);
        mCollapsedStatus = new SparseBooleanArray();
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailCommentsInfo item) {
        NineGridImageView nineGridImageView = helper.getView(R.id.detail_comment_publish_img);
        ExpandableTextView expandableTextView = helper.getView(R.id.detail_comment_publish_ex);
        CircleImageView circleImageView = helper.getView(R.id.detail_comment_u_head_ico);
        ImageLoader.get().load(helper.getView(R.id.detail_comment_u_head_ico), item.getHead_pic(), new RequestListener<Drawable>() {
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
        helper.setText(R.id.detail_comment_u_head_nick, item.getUsername());
        helper.setText(R.id.detail_comment_publish_time, item.getCreate_time());
        expandableTextView.setOnExpandStateChangeListener((textView, isExpanded) -> {
        });
        expandableTextView.setText(item.getContent(), mCollapsedStatus, helper.getAdapterPosition());
        nineGridImageView.setAdapter(new NineGridImageViewAdapter<String>() {

            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                imageView.setFocusable(false);
                imageView.setFocusableInTouchMode(false);
                ImageLoader.get().load(imageView, s);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });
        nineGridImageView.setImagesData(item.getImage());
    }

}

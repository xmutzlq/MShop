package com.king.android.details.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.king.android.details.R;

import java.util.ArrayList;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.AppCompat;
import google.architecture.common.widget.banner.recycle.CommRecyclingPagerAdapter;

/**
 * @author lq.zeng
 * @date 2018/6/8
 */

public class DetailBannerAdapter extends CommRecyclingPagerAdapter<String>{

    public DetailBannerAdapter(Context context, ArrayList<String> imageIdList) {
        super(context, imageIdList);
    }

    @Override
    protected void loadImage(CommRecyclingPagerAdapter.ViewHolder holder, String imgId, int position) {
        ImageLoader.get().load(holder.imageView, imgId, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if(!isCircularRevealAnimateEnd()) {
                    AppCompat.headerStartPostponedTransition(holder.imageView, new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            holder.imageView.setImageDrawable(resource);
                            setCircularRevealAnimateEnd(true);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                } else {
                    holder.imageView.setImageDrawable(resource);
                }
                return false;
            }
        });
    }

    @Override
    protected String getTransitionName() {
        return getContext().getResources().getString(R.string.transitionName_list_img_to_img);
    }
}

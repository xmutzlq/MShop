package google.architecture.common.widget.banner.recycle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import google.architecture.common.imgloader.ImageLoader;

public class LocalBannerAdapter extends CommRecyclingPagerAdapter<Integer> {
    public LocalBannerAdapter(Context context, ArrayList<Integer> imageIdList) {
        super(context, imageIdList);
    }

    @Override
    protected void loadImage(ViewHolder holder, Integer imgId, int position) {
        ImageLoader.get().load(holder.imageView, imgId, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.imageView.setImageDrawable(resource);
                return false;
            }
        });
    }
}

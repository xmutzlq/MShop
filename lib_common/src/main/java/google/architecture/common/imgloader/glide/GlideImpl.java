package google.architecture.common.imgloader.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import google.architecture.common.R;
import google.architecture.common.imgloader.IImageLoader;
import google.architecture.common.util.CacheAssistUtil;

public class GlideImpl implements IImageLoader {

    private static final String KEY_MEMORY = "com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout";

    private static final float SIZE_MULTIPLIER = 0.3f;
    private static final int TIMEOUT_MS = 16000;

    private DrawableTransitionOptions normalTransitionOptions = new DrawableTransitionOptions();
    private BitmapTransitionOptions bitmapTransitionOptions = new BitmapTransitionOptions();

    private static Option<Integer> TIMEOUT_OPTION = Option.memory(KEY_MEMORY, TIMEOUT_MS);

    @Override
    public void loadCropCircleHeader(Activity activity, String url, int width, int height, SimpleTarget<Drawable> listener) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        options.override(width, height);
        options.diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
        GlideApp.with(activity)
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .transform(new MultiTransformation<>(new CircleCrop()))
                .apply(options)
                .into(listener);
    }

    @Override
    public void load(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        GlideApp.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadBySize(ImageView imageView, String url, int width, int height) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        options.centerCrop();
        options.override(width, height);
        GlideApp.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, String url, RequestListener<Drawable> listener) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).centerCrop().dontAnimate();
        GlideApp.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(listener != null) {
                            listener.onLoadFailed(e, model, target, isFirstResource);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(listener != null) {
                            listener.onResourceReady(resource, model, target, dataSource, isFirstResource);
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, int resId, RequestListener<Drawable> listener) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        GlideApp.with(imageView.getContext())
                .load(resId)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        if(listener != null) {
                            listener.onLoadFailed(e, model, target, isFirstResource);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        if(listener != null) {
                            listener.onResourceReady(resource, model, target, dataSource,
                                    isFirstResource);
                        }
                        return false;
                    }
                })
                .transition(normalTransitionOptions)
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, File file) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        GlideApp.with(imageView.getContext())
                .load(file)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadSkipCache(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        GlideApp.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadThumb(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        GlideApp.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .thumbnail(SIZE_MULTIPLIER)
                .transition(normalTransitionOptions)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCropCircle(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        GlideApp.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .transform(new MultiTransformation<>(new CircleCrop()))
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCropCircle(ImageView imageView, String url, int width, int height) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        options.override(width, height);
        GlideApp.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .transform(new MultiTransformation<>(new CircleCrop()))
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCropCircle(ImageView imageView, int resId) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        options.transform(new MultiTransformation<>(new CircleCrop()));
        GlideApp.with(imageView.getContext())
                .load(resId)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCropCircleHeader(ImageView imageView, String url, int width, int height) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        options.override(width, height);
        options.diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
        GlideApp.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .transform(new MultiTransformation<>(new CircleCrop()))
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadRoundCorner(ImageView imageView, File file) {
        RequestOptions options = new RequestOptions().error(R.drawable.image_mark).placeholder(R.drawable.image_mark).dontAnimate();
        options.transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(20)));
        GlideApp.with(imageView.getContext())
                .load(file)
                .dontAnimate()
                .placeholder(R.drawable.image_mark)
                .set(TIMEOUT_OPTION, TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void clearImageDiskCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(() -> Glide.get(context.getApplicationContext()).clearDiskCache()).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context.getApplicationContext()).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trimMemory(Context context, int level) {
        Glide.get(context).trimMemory(level);
    }

    @Override
    public String getCacheSize(Context context) {
        try {
            return CacheAssistUtil.getFormatSize(CacheAssistUtil.getFolderSize(Glide.getPhotoCacheDir(context.getApplicationContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

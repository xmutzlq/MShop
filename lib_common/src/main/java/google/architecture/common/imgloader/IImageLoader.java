package google.architecture.common.imgloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

public interface IImageLoader {

    void loadCropCircleHeader(Activity activity, String url, int width, int height, SimpleTarget<Drawable> listener);

    void load(ImageView imageView, String url);

    void loadBySize(ImageView imageView, String url, int width, int height);

    void load(ImageView imageView, String url, RequestListener<Drawable> listener);

    void load(ImageView imageView, int resId, RequestListener<Drawable> listener);

    void load(ImageView imageView, File file);

    void loadSkipCache(ImageView imageView, String url);

    void loadThumb(ImageView imageView, String url);

    void loadCropCircle(ImageView imageView, String url);

    void loadCropCircle(ImageView imageView, String url, int width, int height);

    void loadCropCircle(ImageView imageView, int resId);

    void loadCropCircleHeader(ImageView imageView, String url, int width, int height);

    void loadRoundCorner(ImageView imageView, File url);

    //清除硬盘缓存
    void clearImageDiskCache(final Context context);

    //清除内存缓存
    void clearImageMemoryCache(Context context);

    //根据不同的内存状态，来响应不同的内存释放策略
    void trimMemory(Context context, int level);

    //获取缓存大小
    String getCacheSize(Context context);
}

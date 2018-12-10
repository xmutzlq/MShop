package google.architecture.common.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

import google.architecture.common.R;
import google.architecture.coremodel.data.OpDiscoverCates;

/**
 * @author lq.zeng
 * @date 2018/6/7
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String url = "";
        if(path instanceof OpDiscoverCates) {
            url = ((OpDiscoverCates) path).getPic();
        } else if(path instanceof String) {
            url = (String) path;
            imageView.setScaleX(0.5f);
            imageView.setScaleY(0.5f);
        }
        google.architecture.common.imgloader.ImageLoader.get().load(imageView, url);
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.layout_common_imgeview, null);
        return imageView;
    }
}

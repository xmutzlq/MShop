package google.architecture.common.widget.banner.recycle;

import android.content.Context;

import java.util.ArrayList;

import google.architecture.common.imgloader.ImageLoader;

public class CommonImagePagerAdapter extends CommRecyclingPagerAdapter<String> {

    public CommonImagePagerAdapter(Context context, ArrayList<String> imageIdList) {
        super(context, imageIdList);
    }

    @Override
    protected void loadImage(ViewHolder holder, String imgId, int position) {
        ImageLoader.get().load(holder.imageView, imgId);
    }
}

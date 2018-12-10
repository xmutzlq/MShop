package google.architecture.common.widget.banner.recycle;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/6/8
 */

public class CommRecyclingPagerAdapter<T> extends RecyclingPagerAdapter {

    private Context context;
    private List<T> imageIdList;

    private int size;
    private boolean isInfiniteLoop;
    private boolean isCircularRevealAnimateEnd;

    private AdvertImagePagerAdapter.IBannerClickListener mBannerClickListener;

    public Context getContext() {
        return context;
    }

    public CommRecyclingPagerAdapter(Context context, ArrayList<T> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = imageIdList.size();
        isInfiniteLoop = false;
        isCircularRevealAnimateEnd = false;
    }

    public CommRecyclingPagerAdapter setBannerClickListener(AdvertImagePagerAdapter.IBannerClickListener listener) {
        mBannerClickListener = listener;
        return this;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return position % size;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        CommRecyclingPagerAdapter.ViewHolder holder;
        if (view == null) {
            holder = new CommRecyclingPagerAdapter.ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ViewCompat.setTransitionName(holder.imageView, getTransitionName());
            view.setTag(holder);
        } else {
            holder = (CommRecyclingPagerAdapter.ViewHolder) view.getTag();
        }

        loadImage(holder, imageIdList.get(getPosition(position)), getPosition(position));

        holder.imageView.setOnClickListener(v -> {
            if (mBannerClickListener != null) {
                mBannerClickListener.onBannerClickListener(getPosition(position));
            }
        });
        return view;
    }

    protected String getTransitionName() {
        return "";
    }

    protected void loadImage(ViewHolder holder, T imgId, int position) { }

    public static class ViewHolder {
        public ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop
     *            the isInfiniteLoop to set
     */
    public CommRecyclingPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    public boolean isCircularRevealAnimateEnd() {return isCircularRevealAnimateEnd;}

    public void setCircularRevealAnimateEnd(boolean isCircularRevealAnimateEnd) {
        this.isCircularRevealAnimateEnd = isCircularRevealAnimateEnd;
    }

    public static interface IBannerClickListener {
        public void onBannerClickListener(int position);
    }
}

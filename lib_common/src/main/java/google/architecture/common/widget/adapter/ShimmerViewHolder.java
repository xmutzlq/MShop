package google.architecture.common.widget.adapter;

import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * @author lq.zeng
 * @date 2018/10/19
 */

public class ShimmerViewHolder extends com.chad.library.adapter.base.BaseViewHolder {

    public ShimmerViewHolder(View view) {
        super(view);
        if (view instanceof ShimmerFrameLayout) {
            final ShimmerFrameLayout shimmerView = (ShimmerFrameLayout) view;
            shimmerView.setAutoStart(false);
        }
    }

    public void startAnim() {

        if (itemView instanceof ShimmerFrameLayout) {
            final ShimmerFrameLayout shimmerView = (ShimmerFrameLayout) itemView;
            if (!shimmerView.isAnimationStarted()) {
                shimmerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shimmerView.startShimmerAnimation();
                    }
                }, 100);

            }
        }
    }

    public void stopAnim() {

        if (itemView instanceof ShimmerFrameLayout) {
            final ShimmerFrameLayout shimmerView = (ShimmerFrameLayout) itemView;
            if (shimmerView.isAnimationStarted()) {
                shimmerView.setAutoStart(false);
            }
        }
    }
}

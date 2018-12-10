package google.architecture.common.util;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author lq.zeng
 * @date 2018/12/7
 */

public class TransitionInformation {
    /**
     * Covering view.
     */
    private View coveringView;

    /**
     * Touch point.
     */
    private PointF touchPoint;

    /**
     * Remember transition.
     *
     * @param coveringView Covering view.
     * @param touchPoint   Touch point.
     */
    public void rememberTransition(@NonNull final View coveringView, @NonNull final PointF touchPoint) {
        this.coveringView = coveringView;
        this.touchPoint = touchPoint;
    }

    /**
     * Clear informations.
     */
    public void clear() {
        coveringView = null;
        touchPoint = null;
    }

    public View getCoveringView() {
        return coveringView;
    }

    public void setCoveringView(View coveringView) {
        this.coveringView = coveringView;
    }

    public PointF getTouchPoint() {
        return touchPoint;
    }

    public void setTouchPoint(PointF touchPoint) {
        this.touchPoint = touchPoint;
    }
}

package google.architecture.common.widget;

import android.view.View;

/**
 * @author lq.zeng
 * @date 2018/6/14
 */

public interface IScrollable {
    /**
            * 根据速度，距离，和事件进行平滑滚动
     * @param yVelocit
     * @param distance
     * @param duration
     */
    void smoothScrollBy(int yVelocit, int distance, int duration);

    /**
     * 是否滑动到顶部
     */
    boolean isTop();

    View getScrollView();

    void setScrollView(View scrollView);
}

package google.architecture.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author lq.zeng
 * @date 2018/6/25
 */

public class FollowView extends ThumbUpView{

    public boolean isFollowEnable;

    public void setCanFollow(boolean isFollowEnable) {
        this.isFollowEnable = isFollowEnable;
    }

    public FollowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isFollowEnable && super.onTouchEvent(ev);
    }
}

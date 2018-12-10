package google.architecture.common.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author lq.zeng
 * @date 2018/6/13
 */

public class MyBehavior extends AppBarLayout.ScrollingViewBehavior{

    private boolean isOnMove;
    private IBehaviorListener mBehaviorListener;
    private int touchSlop;
    private float xDistance, yDistance, xLast, yLast;

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context,attrs);
//        touchSlop = ViewConfiguration.get(BaseApplication.getIns()).getScaledTouchSlop();
        touchSlop = 10;
    }

    public void setBehaviorListener(IBehaviorListener listener) {
        mBehaviorListener = listener;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (!isOnMove && xDistance < yDistance && yDistance > touchSlop) {
                    isOnMove = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isOnMove = false;
                break;
        }
        if(mBehaviorListener != null) {
            mBehaviorListener.onBehavior(isOnMove);
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    public interface IBehaviorListener{
        void onBehavior(boolean isOnMove);
    }
}

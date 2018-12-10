package google.architecture.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

import google.architecture.common.widget.nested.MyNestedScrollView;

/**
 * @author lq.zeng
 * @date 2018/6/12
 */

public class JudgeNestedScrollView extends MyNestedScrollView {

    public JudgeNestedScrollView(Context context) {
        super(context);
    }

    public JudgeNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JudgeNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ans = super.onInterceptTouchEvent(ev);
        if (ans && ev.getAction() == MotionEvent.ACTION_DOWN) {
            onTouchEvent(ev);
            Field field = null;
            try {
                field = MyNestedScrollView.class.getDeclaredField("mIsBeingDragged");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            if (field != null) {
                field.setAccessible(true);
                try {
                    field.setBoolean(this, false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        return ans;
    }

}

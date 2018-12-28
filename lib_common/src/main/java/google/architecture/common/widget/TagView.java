package google.architecture.common.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * @author lq.zeng
 * @date 2018/5/25
 */

public class TagView extends FrameLayout implements Checkable {
    private boolean isInterrupt;
    private boolean isChecked;
    private static int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    public TagView(Context context) {
        super(context);
    }

    public View getTagView() {
        return getChildAt(0);
    }

    public void setInterrupt(boolean isInterrupt) {
        this.isInterrupt = isInterrupt;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isInterrupt ? isInterrupt : super.onTouchEvent(event);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}

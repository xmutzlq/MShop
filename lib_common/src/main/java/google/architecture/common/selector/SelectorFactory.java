package google.architecture.common.selector;

import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;

/**
 * @author lq.zeng
 * @date 2018/9/5
 */

public class SelectorFactory {
    public static StateListDrawable create(ISelector selector) {
        return getStateListDrawable(selector);
    }

    @NonNull
    private static StateListDrawable getStateListDrawable(ISelector selector) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, selector.getDisableDrawable());
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selector.getSelectDrawable());
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, selector.getNormalDrawable());
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, selector.getPressedDrawable());
        return stateListDrawable;
    }
}

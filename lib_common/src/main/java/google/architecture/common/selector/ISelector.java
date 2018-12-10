package google.architecture.common.selector;

import android.graphics.drawable.Drawable;

/**
 * @author lq.zeng
 * @date 2018/9/5
 */

public interface ISelector {
    Drawable getNormalDrawable();

    Drawable getPressedDrawable();

    Drawable getDisableDrawable();

    Drawable getSelectDrawable();
}

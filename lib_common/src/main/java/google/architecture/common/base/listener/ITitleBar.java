package google.architecture.common.base.listener;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * @author lq.zeng
 * @date 2018/5/11
 */

public interface ITitleBar {

    void setCanBack(boolean isEnable);

    /**
     * 设置title
     *
     * @param str
     */

    void setTitleName(@NonNull String str);

    void setTitleName(@StringRes int str);

    /**
     * 设置左边
     *
     * @param strId
     */
    void setLeftText(@StringRes int strId, View.OnClickListener clickListener);

    void setLeftText(@NonNull String str, View.OnClickListener clickListener);

    void setLeftButton(Drawable drawable, View.OnClickListener clickListener);

    void setLeftButton(@DrawableRes int drawableId, View.OnClickListener clickListener);


    /**
     * 设置右边
     *
     * @param str
     */
    void setRightText(@NonNull String str, View.OnClickListener clickListener);

    void setRightText(@StringRes int strId, View.OnClickListener clickListener);

    void setRightButton(@NonNull Drawable drawable, View.OnClickListener clickListener);

    void setRightButton(@DrawableRes int drawableId, View.OnClickListener clickListener);
}

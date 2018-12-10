package google.architecture.common.widget.decoration;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author lq.zeng
 * @date 2018/6/27
 */

public class Dp2Px {
    private Dp2Px() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int convert(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}

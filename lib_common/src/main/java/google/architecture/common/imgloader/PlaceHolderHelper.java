package google.architecture.common.imgloader;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author lq.zeng
 * @date 2018/11/15
 */

public class PlaceHolderHelper {
    public static Drawable getPlaceHolderColorDrawable() {
        int currColor = (int) -(Math.random() * (16777216 - 1) + 1);
        return new ColorDrawable(currColor);
    }
}

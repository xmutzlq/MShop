package google.architecture.common.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;

/**
 * @author lq.zeng
 * @date 2018/6/21
 */

public class BackgroundCompat {
    public static void setBackgroundWithBitmap(View view, Resources res, Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(new BitmapDrawable(res, bitmap));
        } else {
            view.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }
    }
}

package google.architecture.common.util;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;

import google.architecture.common.selector.SelectorFactory;
import google.architecture.common.selector.SelectorShape;

/**
 * @author lq.zeng
 * @date 2018/5/21
 */

public final class AppCompat {

    public final static String WIDTH = "width";

    public final static String HEIGHT = "height";

    public static void setBackground(View view) {
        setBackground(view, null);
    }



    public static void setBackgroundPressed(Context context, View view, int colorRes) {
        setBackgroundPressed(context, view, colorRes, 0.8f);
    }

    public static void setBackgroundPressed(Context context, View view, int colorRes, float ratio) {
        int color = ContextCompat.getColor(context, colorRes);
        Drawable drawable = SelectorFactory.create(new SelectorShape.SelectorBuilder()
                .normalColor(color)
                .pressColor(AppCompat.darker(color, ratio)).build());
        setBackground(view, drawable);
    }

    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static void startActivityWithPostponedTransition() {

    }

    //启动转场动画
    public static void scheduleStartPostponedTransition(final FragmentActivity context, final View sharedElement) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElement.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public boolean onPreDraw() {
                            sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                            context.supportStartPostponedEnterTransition();
                            return true;
                        }
                    });
        }
    }

    //启动转场动画
    public static void headerStartPostponedTransition(final View sharedElement, Animator.AnimatorListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Animator circularReveal = ViewAnimationUtils.createCircularReveal(sharedElement, sharedElement.getWidth() / 2, 0, 0, sharedElement.getWidth());
            circularReveal.setInterpolator(new AccelerateInterpolator(1.5f));
            circularReveal.setDuration(500);
            if(listener != null) circularReveal.addListener(listener);
            circularReveal.start();
        }
    }

    //暂停转场动画
    public static void schedulePostponeEnterTransition(FragmentActivity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.supportPostponeEnterTransition();
        }
    }

    //结束转场动画
    public static void scheduleFinishAfterTransition(FragmentActivity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.finishAfterTransition();
        }
    }

    // 明度
    public static int darker(int color, float ratio) {
        color = (color >> 24) == 0 ? 0x22808080 : color;
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= ratio;
        return Color.HSVToColor(color >> 24, hsv);
    }
}

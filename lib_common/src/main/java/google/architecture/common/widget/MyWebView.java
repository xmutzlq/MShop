package google.architecture.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.apkfuns.logutils.LogUtils;
import com.king.android.sharesdk.utils.LogUtil;
import com.tencent.smtt.sdk.WebView;

/**
 * @author lq.zeng
 * @date 2018/7/6
 */

public class MyWebView extends WebView {
    public float oldY;
    private int t;

    private boolean isNormal = true;

    private IFixWebView fixWebView;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIsNormal(boolean isNormal) {
        this.isNormal = isNormal;
    }

    public void setFixWebView(IFixWebView fixWebView) {
        this.fixWebView = fixWebView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtils.tag("zlq").e("onTouchEvent, isNormal = " + isNormal);
        if(isNormal) {
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float Y = ev.getY();
                float Ys = Y - oldY;

                //滑动到顶部让父控件重新获得触摸事件
                LogUtils.tag("zlq").e("Ys = " + Ys + ", t = " + t);
                if (Ys > 0 && t == 0) {
                    getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    if(fixWebView != null) {
                        fixWebView.onFixWebView(false);
                    }
                }
                break;

            case MotionEvent.ACTION_DOWN:
                getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                if(fixWebView != null) {
                    fixWebView.onFixWebView(true);
                }
                oldY = ev.getY();
                break;

            case MotionEvent.ACTION_UP:
                getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                if(fixWebView != null) {
                    fixWebView.onFixWebView(true);
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.t = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }

//    @Override
//    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
//        boolean ret = super.drawChild(canvas, child, drawingTime);
//        canvas.save();
//        Paint paint = new Paint();
//        paint.setColor(0x7fff0000);
//        paint.setTextSize(24.f);
//        paint.setAntiAlias(true);
//        if (getX5WebViewExtension() != null) {
//            canvas.drawText(this.getContext().getPackageName() + "-pid:"
//                    + android.os.Process.myPid(), 10, 50, paint);
//            canvas.drawText(
//                    "X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
//                    100, paint);
//        } else {
//            canvas.drawText(this.getContext().getPackageName() + "-pid:"
//                    + android.os.Process.myPid(), 10, 50, paint);
//            canvas.drawText("Sys Core", 10, 100, paint);
//        }
//        canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
//        canvas.drawText(Build.MODEL, 10, 200, paint);
//        canvas.restore();
//        return ret;
//    }

    public interface IFixWebView {
        void onFixWebView(boolean isSelfControl);
    }
}

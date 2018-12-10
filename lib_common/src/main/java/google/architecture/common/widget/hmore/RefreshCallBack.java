package google.architecture.common.widget.hmore;

/**
 * @author lq.zeng
 * @date 2018/6/27
 */

public interface RefreshCallBack {
    void onLeftRefreshing();
    void onRightRefreshing();
    void onInterceptTouchEvent(boolean isRelease);
}

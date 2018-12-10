package google.architecture.common.base.top;

import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

/**
 * @author lq.zeng
 * @date 2018/5/2
 */

public interface UIView extends BaseView {
    /**
     * res资源获取
     *
     * @return
     */
    Resources getResources();

    /**
     * 根据id获得控件
     * 需要调用在initView()之后,否则会出现NullPointerException
     *
     * @param viewId
     * @param <V>
     * @return
     */
    <V extends View> V findView(@IdRes int viewId);

    /**
     * 回退
     */

    void onBack();

    /**
     * 获取Acitivity
     *
     * @return
     */
    AppCompatActivity getAppcompatActivity();

    /**
     * 主要视图View
     *
     * @return
     */
    View getContentView();

    /**
     * Frgament跳转.
     *
     * @param tofragment
     */
    void startFragment(Fragment tofragment);

    /**
     * Frgament跳转.
     *
     * @param tofragment
     */
    void startFragment(Fragment tofragment, String tag);

    Window getWindow();

    ActionBar getSupportActionBar();

    void setSupportActionBar(@Nullable Toolbar toolbar);
}
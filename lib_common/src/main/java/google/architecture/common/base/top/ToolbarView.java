package google.architecture.common.base.top;

import android.support.annotation.LayoutRes;

import google.architecture.common.base.top.options.ToolbarOptions;

/**
 * @author lq.zeng
 * @date 2018/5/2
 */

public interface ToolbarView extends BaseView {
    /**
     * 获得ToolbarHelper,Presenter可以通过ToolbarHelper的来控制toolbar
     */
    ToolbarHelper getToolbarHelper();

    /**
     * 是否使用MaterialDesign风格
     *
     * @return
     */
    boolean isMaterialDesign();

    /**
     * 通过这个修改toolbar的样式layout,不需要可以传0或者-1;
     *
     * @return
     */
    @LayoutRes
    int getToolbarLayout();

    /**
     * 回退
     */
    void onBack();

    ToolbarOptions getToolbarOptions();
}

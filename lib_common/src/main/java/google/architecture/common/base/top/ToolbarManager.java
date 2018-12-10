package google.architecture.common.base.top;

import google.architecture.common.base.top.options.ToolbarOptions;

/**
 * @author lq.zeng
 * @date 2018/5/2
 */

public class ToolbarManager {
    public static ToolbarOptions mToolbarOptions;

    public static ToolbarOptions getToolbarOptions() {
        if (mToolbarOptions == null) {
            return ToolbarOptions.Create();
        }
        return mToolbarOptions;
    }

    public static void setToolbarOptions(ToolbarOptions toolbarOptions) {
        mToolbarOptions = toolbarOptions;
    }
}

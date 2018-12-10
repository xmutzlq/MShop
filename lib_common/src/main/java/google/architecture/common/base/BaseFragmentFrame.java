package google.architecture.common.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apkfuns.logutils.LogUtils;

import google.architecture.common.base.listener.ActivityObservable;
import google.architecture.common.base.listener.IActivityObservable;
import google.architecture.common.base.listener.IActivityObserver;
import google.architecture.common.base.listener.IAppContextObservable;
import google.architecture.common.base.listener.IAppContextObserver;
import google.architecture.common.base.listener.IPanelView;
import google.architecture.common.base.listener.ITitleBar;
import google.architecture.common.base.top.ToolbarHelper;
import google.architecture.common.base.top.ToolbarView;
import google.architecture.common.base.top.options.ToolbarOptions;

/**
 * @author lq.zeng
 * @date 2018/4/16
 */

public class BaseFragmentFrame extends LazyLoadBaseFragment implements IAppContextObserver, IActivityObserver, IActivityObservable,
        IPanelView, ITitleBar, ToolbarView {

    public Context mContext;
    private ActivityObservable mActivityObservable;
    private boolean isPanelViewCreate = false;
    private boolean isCallBackRegister = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        LogUtils.tag("zlq").e("onAttach = " + context.getPackageName());
        registerObservable();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.tag("zlq").e("onDetach");
        tryAutoDestroy();
        unregisterObservable();
        releaseActivityObservable();
    }

    /**
     * 标识是否销毁
     *
     * @return
     */
    public boolean isDestroy() {
        return !isPanelViewCreate;
    }

    /**
     * 创建
     */
    protected void tryAutoCreate() {
        if (!isPanelViewCreate) {
            if (isAdded()) {
                dispatchCreate();
            }
        }
    }

    /**
     * 销毁
     */
    private void tryAutoDestroy() {
        if (isPanelViewCreate) {
            dispatchDestroy();
        }
    }

    protected void dispatchCreate() {
        isPanelViewCreate = true;
        try {
            onCreate();
        } catch (Exception e) {
        }
    }

    protected void dispatchDestroy() {
        try {
            onDestroy();
        } catch (Exception e) {
        }
        isPanelViewCreate = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void registerObservable() {
        if (!isCallBackRegister) {
            if (mContext != null && mContext instanceof IActivityObservable) {
                ((IActivityObservable) mContext).registerActivityObserver(this);
            }
            if (getContext() instanceof IAppContextObservable) {
                ((IAppContextObservable) getContext()).registerContextObservable(this);
            }
            isCallBackRegister = true;
        }
    }

    private void unregisterObservable() {
        if (isCallBackRegister) {
            if (mContext != null && mContext instanceof IActivityObservable) {
                ((IActivityObservable) mContext).unregisterActivityObserver(this);
            }
            if (getContext() instanceof IAppContextObservable) {
                ((IAppContextObservable) getContext()).unregisterContextObservable(this);
            }
            isCallBackRegister = false;
        }
    }

    private ActivityObservable getActivityObservable() {
        if (mActivityObservable == null) {
            mActivityObservable = new ActivityObservable();
        }
        return mActivityObservable;
    }

    private void releaseActivityObservable() {
        if (mActivityObservable != null) {
            mActivityObservable.release();
            mActivityObservable = null;
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onReLoad() {

    }

    @Override
    public void registerActivityObserver(IActivityObserver observer) {
        getActivityObservable().registerActivityObserver(observer);
    }

    @Override
    public void unregisterActivityObserver(IActivityObserver observer) {
        getActivityObservable().unregisterActivityObserver(observer);
    }

    @Override
    public boolean dispatchMenuOpened(int featureId, Menu menu) {
        if (mActivityObservable != null) {
            return getActivityObservable().dispatchMenuOpened(featureId, menu);
        }
        return false;
    }

    @Override
    public boolean dispatchOptionsItemSelected(MenuItem item) {
        if (mActivityObservable != null) {
            return getActivityObservable().dispatchOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public void dispatchActivityResume(boolean isFirst) {
        if (mActivityObservable != null) {
            getActivityObservable().dispatchActivityResume(isFirst);
        }
    }

    @Override
    public void dispatchActivityPause() {
        if (mActivityObservable != null) {
            getActivityObservable().dispatchActivityPause();
        }
    }

    @Override
    public boolean dispatchBackPressed() {
        if (mActivityObservable != null) {
            return getActivityObservable().dispatchBackPressed();
        }
        return false;
    }

    @Override
    public boolean dispatchActivityResult(int requestCode, int resultCode, Intent data) {
        if (mActivityObservable != null) {
            return getActivityObservable().dispatchActivityResult(requestCode, resultCode, data);
        }
        return false;
    }

    @Override
    public boolean onActivityResultPatch(int requestCode, int resultCode, Intent data) {
        return dispatchActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return dispatchOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return dispatchMenuOpened(featureId, menu);
    }

    @Override
    public void onActivityPause() {
        dispatchActivityPause();
    }

    @Override
    public void onActivityResume(boolean isFirst) {
        dispatchActivityResume(isFirst);
    }

    @Override
    public boolean onBackPressed() {
        return dispatchBackPressed();
    }

    @Override
    public void onLanguageChange() {
    }

    @Override
    public void onLoadTheme() {
    }

    @Override
    public void onUserLoginStateChange(boolean isLogin) {
    }

    @Override
    public void onNetworkChange(boolean isAvailable) {
    }

    @Override
    public void setCanBack(boolean isEnable) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setCanBack(isEnable);
        }
    }

    @Override
    public void setTitleName(@NonNull String str) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setTitleName(str);
        }
    }

    @Override
    public void setTitleName(int str) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setTitleName(str);
        }
    }

    @Override
    public void setLeftText(int strId, View.OnClickListener clickListener) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setLeftText(strId, clickListener);
        }
    }

    @Override
    public void setLeftText(@NonNull String str, View.OnClickListener clickListener) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setLeftText(str, clickListener);
        }
    }

    @Override
    public void setLeftButton(Drawable drawable, View.OnClickListener clickListener) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setLeftButton(drawable, clickListener);
        }
    }

    @Override
    public void setLeftButton(int drawableId, View.OnClickListener clickListener) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setLeftButton(drawableId, clickListener);
        }
    }

    @Override
    public void setRightText(@NonNull String str, View.OnClickListener clickListener) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setRightText(str, clickListener);
        }
    }

    @Override
    public void setRightText(int strId, View.OnClickListener clickListener) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setRightText(strId, clickListener);
        }
    }

    @Override
    public void setRightButton(@NonNull Drawable drawable, View.OnClickListener clickListener) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setRightButton(drawable, clickListener);
        }
    }

    @Override
    public void setRightButton(int drawableId, View.OnClickListener clickListener) {
        if (mContext instanceof ITitleBar) {
            ((ITitleBar) mContext).setRightButton(drawableId, clickListener);
        }
    }

    @Override
    public ToolbarHelper getToolbarHelper() {
        if (mContext instanceof ToolbarView) {
            return ((ToolbarView) mContext).getToolbarHelper();
        }
        return null;
    }

    @Override
    public boolean isMaterialDesign() {
        if (mContext instanceof ToolbarView) {
            return ((ToolbarView) mContext).isMaterialDesign();
        }
        return false;
    }

    @Override
    public int getToolbarLayout() {
        if (mContext instanceof ToolbarView) {
            return ((ToolbarView) mContext).getToolbarLayout();
        }
        return 0;
    }

    @Override
    public void onBack() {
        if (mContext instanceof ToolbarView) {
            ((ToolbarView) mContext).onBack();
        }
    }

    @Override
    public ToolbarOptions getToolbarOptions() {
        if (mContext instanceof ToolbarView) {
            return ((ToolbarView) mContext).getToolbarOptions();
        }
        return null;
    }
}

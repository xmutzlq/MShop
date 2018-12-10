package google.architecture.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import google.architecture.common.base.listener.ActivityObservable;
import google.architecture.common.base.listener.AppBrocastAction;
import google.architecture.common.base.listener.AppContextObservable;
import google.architecture.common.base.listener.IActivityObservable;
import google.architecture.common.base.listener.IActivityObserver;
import google.architecture.common.base.listener.IAppContextObservable;
import google.architecture.common.base.listener.IAppContextObserver;
import google.architecture.common.base.listener.ITitleBar;
import google.architecture.common.base.listener.LifeCycleListener;
import google.architecture.common.util.NetworkUtils;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.util.NetUtils;
import solid.ren.skinlibrary.base.SkinBaseActivity;

/**
 * @author lq.zeng
 * @date 2018/4/16
 */

public class BaseActivityFrame extends SkinBaseActivity implements IAppContextObserver, IAppContextObservable, IActivityObservable, ITitleBar {

    protected BaseActivityFrame this_ = this;

    private HashMap<String, BroadcastReceiver> mBroadcastReceivers = new HashMap<>();
    private ArrayList<LifeCycleListener> mLifeCycleListeners = new ArrayList<>();
    private NetWorkListener mNetWorkListener;
    private AppContextObservable mAppContextObservable;
    private ActivityObservable mActivityObservable;
    private boolean isFirst;
    private Boolean isNetAvailable;

    /**
     * 注册一些需要和activity捆绑的生命周期
     * @param l
     */
    public void registerLifeCycleListener(LifeCycleListener l){
        if(l != null && !mLifeCycleListeners.contains(l)){
            mLifeCycleListeners.add(l);
        }
    }

    /**
     * 注销
     * @param l
     */
    public void unregisterLifeCycleListener(LifeCycleListener l){
        if(l != null){
            mLifeCycleListeners.remove(l);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewManager.getInstance().addActivity(this);
        isFirst = true;
        mNetWorkListener = new NetWorkListener();
        isNetAvailable = NetUtils.isNetConnected(this_);
        mAppContextObservable = new AppContextObservable();
        mActivityObservable = new ActivityObservable();
        initBroadcastReceiver();
        registerContextObservable(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tryDispatchNetworkChange(NetUtils.isNetConnected(this_));
        registerBroadcastReceiver(android.net.ConnectivityManager.CONNECTIVITY_ACTION, mNetWorkListener);
        dispatchActivityResume(isFirst);
        if (isFirst) {
            isFirst = false;
        }
    }

    @Override
    protected void onPause() {
        unregisterBroadcastReceiver(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        isNetAvailable = NetworkUtils.isConnected();
        dispatchActivityPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewManager.getInstance().finishActivity(this);
        unregisterAllBroadcastReceiver();
        if (mActivityObservable != null) {
            mActivityObservable.release();
            mActivityObservable = null;
        }
        if (mAppContextObservable != null) {
            mAppContextObservable.release();
            mAppContextObservable = null;
        }
        ArrayList<LifeCycleListener> temp = new ArrayList<>(mLifeCycleListeners);
        mLifeCycleListeners.clear();
        for (LifeCycleListener listener : temp) {
            listener.onActivityDestroy();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 结束Activity&从堆栈中移除
            ViewManager.getInstance().finishActivity(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void registerActivityObserver(IActivityObserver observer) {
        if (mActivityObservable != null) {
            mActivityObservable.registerActivityObserver(observer);
        }
    }

    @Override
    public void unregisterActivityObserver(IActivityObserver observer) {
        if (mActivityObservable != null) {
            mActivityObservable.unregisterActivityObserver(observer);
        }
    }

    @Override
    public boolean dispatchMenuOpened(int featureId, Menu menu) {
        if (mActivityObservable != null) {
            return mActivityObservable.dispatchMenuOpened(featureId, menu);
        }
        return false;
    }

    @Override
    public boolean dispatchOptionsItemSelected(MenuItem item) {
        if (mActivityObservable != null) {
            return mActivityObservable.dispatchOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public void dispatchActivityResume(boolean isFirst) {
        if (mActivityObservable != null) {
            mActivityObservable.dispatchActivityResume(isFirst);
        }
    }

    @Override
    public void dispatchActivityPause() {
        if (mActivityObservable != null) {
            mActivityObservable.dispatchActivityPause();
        }
    }

    @Override
    public boolean dispatchBackPressed() {
        if (mActivityObservable != null) {
            return mActivityObservable.dispatchBackPressed();
        }
        return false;
    }

    @Override
    public boolean dispatchActivityResult(int requestCode, int resultCode, Intent data) {
        if (mActivityObservable != null) {
            return mActivityObservable.dispatchActivityResult(requestCode, resultCode, data);
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        dispatchActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (!dispatchMenuOpened(featureId, menu)) {
            return super.onMenuOpened(featureId, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!dispatchOptionsItemSelected(item)) {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onLoadTheme() {
        // 由子类完成onLoadTheme的逻辑
    }

    @Override
    public void onUserLoginStateChange(boolean isLogin) {
        // 由子类完成onUserLoginStateChange的逻辑
    }

    @Override
    public void onNetworkChange(boolean isAvailable) {
        // 由子类完成onNetworkChange的逻辑
    }

    @Override
    public void onLanguageChange() {
        // 由子类完成onLanguageChange的逻辑
    }

    @Override
    public void registerContextObservable(IAppContextObserver contextCallBack) {
        if (mAppContextObservable != null) {
            mAppContextObservable.registerContextObservable(contextCallBack);
        }
    }

    @Override
    public void unregisterContextObservable(IAppContextObserver contextCallBack) {
        if (mAppContextObservable != null) {
            mAppContextObservable.unregisterContextObservable(contextCallBack);
        }
    }

    @Override
    public void dispatchLoadTheme() {
        if (mAppContextObservable != null) {
            mAppContextObservable.dispatchLoadTheme();
        }
    }

    @Override
    public void dispatchUserLoginStateChange(boolean isLogin) {
        if (mAppContextObservable != null) {
            mAppContextObservable.dispatchUserLoginStateChange(isLogin);
        }
    }

    @Override
    public void dispatchContextNetworkChange(boolean isAvailable) {
        if (mAppContextObservable != null) {
            mAppContextObservable.dispatchContextNetworkChange(isAvailable);
        }
    }

    private void tryDispatchNetworkChange(boolean isAvailable) {
        if (isNetAvailable == null || isNetAvailable != isAvailable) {
            isNetAvailable = isAvailable;
            dispatchContextNetworkChange(isAvailable);
        }
    }

    @Override
    public void dispatchLanguageChange() {
        if (mAppContextObservable != null) {
            mAppContextObservable.dispatchLanguageChange();
        }
    }

    protected void dispatchExitApp() {
    }

    private void initBroadcastReceiver() {
        // 初始化退出应用程序监听
        mBroadcastReceivers.put(AppBrocastAction.ACTION_CLOSE_APP, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                dispatchExitApp();
            }
        });
        // 初始化登录状态变更监听
        mBroadcastReceivers.put(AppBrocastAction.ACTION_USER_LOGIN_STATE_CHANGE, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                dispatchUserLoginStateChange(Account.get().isLogin());
            }
        });
        // 初始化主题变更监听
        mBroadcastReceivers.put(AppBrocastAction.ACTION_THEME_CHANGE, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                dispatchLoadTheme();
            }
        });
        // 初始化语言变更监听
        mBroadcastReceivers.put(AppBrocastAction.ACTION_LANGUAGE_CHANGE, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                dispatchLanguageChange();
            }
        });
        // 初始同用户强制退出监听
        mBroadcastReceivers.put(AppBrocastAction.ACTION_NOTIFY_LOGOUT_IMPOSED, new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if(this_ != null && !this_.isFinishing()) {
                    //处理强制退出
                }
            }
        });
        registerAllBroadcastReceiver();
    }

    private void registerBroadcastReceiver(String action, BroadcastReceiver receiver) {
        BroadcastReceiver oldReceiver = mBroadcastReceivers.get(action);
        if (oldReceiver == null) {
            mBroadcastReceivers.put(action, receiver);
            super.registerReceiver(receiver, new IntentFilter(action));
        }
    }

    private void unregisterBroadcastReceiver(String action) {
        BroadcastReceiver receiver = mBroadcastReceivers.get(action);
        if (receiver != null) {
            mBroadcastReceivers.remove(action);
            super.unregisterReceiver(receiver);
        }
    }

    private void registerAllBroadcastReceiver() {
        for (Map.Entry<String, BroadcastReceiver> entry : mBroadcastReceivers.entrySet()) {
            super.registerReceiver(entry.getValue(), new IntentFilter(entry.getKey()));
        }
    }

    private void unregisterAllBroadcastReceiver() {
        for (BroadcastReceiver receiver : mBroadcastReceivers.values()) {
            super.unregisterReceiver(receiver);
        }
        mBroadcastReceivers.clear();
    }

    @Override
    public void setCanBack(boolean isEnable) {

    }

    @Override
    public void setTitleName(@NonNull String str) {

    }

    @Override
    public void setTitleName(@StringRes int str) {

    }

    @Override
    public void setLeftText(int strId, View.OnClickListener clickListener) {

    }

    @Override
    public void setLeftText(@NonNull String str, View.OnClickListener clickListener) {

    }

    @Override
    public void setLeftButton(Drawable drawable, View.OnClickListener clickListener) {

    }

    @Override
    public void setLeftButton(int drawableId, View.OnClickListener clickListener) {

    }

    @Override
    public void setRightText(@NonNull String str, View.OnClickListener clickListener) {

    }

    @Override
    public void setRightText(int strId, View.OnClickListener clickListener) {

    }

    @Override
    public void setRightButton(@NonNull Drawable drawable, View.OnClickListener clickListener) {

    }

    @Override
    public void setRightButton(int drawableId, View.OnClickListener clickListener) {

    }

    private class NetWorkListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            tryDispatchNetworkChange(NetUtils.isNetConnected(this_));
        }
    }
}

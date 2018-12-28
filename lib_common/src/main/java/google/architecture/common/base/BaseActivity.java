package google.architecture.common.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.king.android.res.util.DebouncingOnClickListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import google.architecture.common.R;
import google.architecture.common.base.listener.AppBrocastAction;
import google.architecture.common.base.top.ToolbarHelper;
import google.architecture.common.base.top.ToolbarManager;
import google.architecture.common.base.top.ToolbarView;
import google.architecture.common.base.top.options.ToolbarOptions;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.NetUtils;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * <p>Activity基类 </p>
 * @name BaseActivity
 */
public abstract class BaseActivity<VB extends ViewDataBinding> extends BaseActivityFrame implements ToolbarView, CustomAdapt {

    protected VB binding;
    protected BaseViewModel viewModel;
    private RunCallBack runCallBack;
    private DataCallBack dataCallBack;
    private EmptyCallBack emptyCallBack;

    private ToolbarHelper mToolbarHelper;
    private ImmersionBar mImmersionBar;

    protected ProgressDialog progressDialog;
    private View rootView;

    protected abstract int getLayout();
    /**非空数据回调**/
    protected void onDataResult(Object o){};
    /**空数据回调**/
    protected void onEmptyDisplaying() {};
    protected void responseEvent(CommEvent event){}

    @Override
    public Context getContext() {
        return this;
    }

    protected void onCreateBindView() {}

    public void showProgressDialog(int message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.setMessage(getString(message));
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * 是否按照宽度进行等比例适配 (为了保证在高宽比不同的屏幕上也能正常适配, 所以只能在宽度和高度之中选一个作为基准进行适配)
     * @return {@code true} 为按照宽度适配, {@code false} 为按照高度适配
     */
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    /**
     * 这里使用 IPhone 的设计图, IPhone 的设计图尺寸为 750px * 1334px, 高换算成 dp 为 667 (1334px / 2 = 667dp)
     * <p>
     * 返回设计图上的设计尺寸, 单位 dp
     * {@link #getSizeInDp} 须配合 {@link #isBaseOnWidth()} 使用, 规则如下:
     * 如果 {@link #isBaseOnWidth()} 返回 {@code true}, {@link #getSizeInDp} 则应该返回设计图的总宽度
     * 如果 {@link #isBaseOnWidth()} 返回 {@code false}, {@link #getSizeInDp} 则应该返回设计图的总高度
     * 如果您不需要自定义设计图上的设计尺寸, 想继续使用在 AndroidManifest 中填写的设计图尺寸, {@link #getSizeInDp} 则返回 {@code 0}
     *
     * @return 设计图上的设计尺寸, 单位 dp
     */
    @Override
    public float getSizeInDp() {
        return 667;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BaseApplication.APP_STATUS != BaseApplication.APP_STATUS_NORMAL) { // 非正常启动流程，直接重新初始化应用界面
            BaseApplication.reInitApp();
            ViewManager.getInstance().finishActivity();
            return;
        } else { // 正常启动流程
            EventBus.getDefault().register(this);
            setOrientation();
            //初始化一次
            binding = DataBindingUtil.setContentView(this, getLayout());
            if(binding != null) {
                rootView = binding.getRoot();
            } else {
                rootView = getLayoutInflater().inflate(getLayout(), null);
                setContentView(rootView);
            }
            onCreateBindView();
            mToolbarHelper = getToolbarHelper();
            //初始化沉浸式
            if (isImmersionBarEnabled()) {
                initImmersionBar();
            }
            requestPermissions();
        }
    }

    public void setOrientation() {
//        Density.setDefault(this_);
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected boolean isStatusBarTransparent(){ return false; };

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        if(isStatusBarTransparent()) {
            mImmersionBar.transparentStatusBar();
        }else {
            mImmersionBar.statusBarColor(R.color.colorPrimary);
            mImmersionBar.fitsSystemWindows(true);
        }
        mImmersionBar.init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_TOKEN_EXPIRE.equals(event.msgType)) {
            Account.get().clearUserInfo();
            sendBroadcast(new Intent(AppBrocastAction.ACTION_USER_LOGIN_STATE_CHANGE));
        } else if(CommEvent.MSG_TYPE_CHECK_NET.equals(event.msgType)) {
            dismissProgressDialog();
            if(!NetUtils.isNetConnected(this_)) {
                
            }
        } else {
            responseEvent(event);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        release();
        super.onDestroy();
    }

    protected void release() {
        EventBus.getDefault().unregister(this);
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        if (viewModel != null) {
            viewModel.isRunning.removeOnPropertyChangedCallback(runCallBack);
        }
        if(viewModel != null) {
            viewModel.clear();
            viewModel.clearAll();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!dispatchBackPressed()) {
                ViewManager.getInstance().finishActivity(this);
                return super.onKeyUp(keyCode, event);
            } else {
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean isMaterialDesign() {
        return false;
    }

    @Override
    public int getToolbarLayout() {
        return ToolbarHelper.TOOLBAR_TEMPLET_DEFUATL;
    }

    @Override
    public ToolbarHelper getToolbarHelper() {
        if (mToolbarHelper == null && rootView != null) {
            mToolbarHelper = ToolbarHelper.Create(this, rootView);
            mToolbarHelper.setToolbarOptions(getToolbarOptions());
        }
        return mToolbarHelper;
    }

    @Override
    public ToolbarOptions getToolbarOptions() {
        return ToolbarManager.getToolbarOptions();
    }

    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void setCanBack(boolean isEnable) {
        if(mToolbarHelper != null) mToolbarHelper.setCanBack(isEnable);
    }

    @Override
    public void setTitleName(@NonNull String str) {
        if(mToolbarHelper != null) mToolbarHelper.setTitle(str);
    }

    @Override
    public void setTitleName(@StringRes int str) {
        if(mToolbarHelper != null)  mToolbarHelper.setTitle(str);
    }

    @Override
    public void setLeftText(int strId, View.OnClickListener clickListener) {
        if(mToolbarHelper != null) mToolbarHelper.setLeftText(strId, clickListener);
    }

    @Override
    public void setLeftText(@NonNull String str, View.OnClickListener clickListener) {
        if(mToolbarHelper != null) mToolbarHelper.setLeftText(str, clickListener);
    }

    @Override
    public void setLeftButton(int drawableId, View.OnClickListener clickListener) {
        if(mToolbarHelper != null) mToolbarHelper.setLeftButton(drawableId, clickListener);
    }

    @Override
    public void setLeftButton(Drawable drawable, View.OnClickListener clickListener) {
        if(mToolbarHelper != null) mToolbarHelper.setLeftButton(drawable, clickListener);
    }

    @Override
    public void setRightText(int strId, View.OnClickListener clickListener) {
        if(mToolbarHelper != null) mToolbarHelper.setRightText(strId, clickListener);
    }

    @Override
    public void setRightText(@NonNull String str, View.OnClickListener clickListener) {
        if(mToolbarHelper != null) mToolbarHelper.setRightText(str, clickListener);
    }

    @Override
    public void setRightButton(int drawableId, View.OnClickListener clickListener) {
        if(mToolbarHelper != null) mToolbarHelper.setRightButton(drawableId, clickListener);
    }

    @Override
    public void setRightButton(@NonNull Drawable drawable, View.OnClickListener clickListener) {
        if(mToolbarHelper != null) mToolbarHelper.setRightButton(drawable, clickListener);
    }

    protected void setToolbar(Toolbar toolbar) {
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void addRunStatusChangeCallBack(BaseViewModel viewModel) {
        if (viewModel == null) {
            return;
        }
        this.viewModel = viewModel;
        if (runCallBack == null) {
            runCallBack = new RunCallBack();
        }
        this.viewModel.isRunning.addOnPropertyChangedCallback(runCallBack);
        if(dataCallBack == null) {
            dataCallBack = new DataCallBack();
        }
        this.viewModel.data.addOnPropertyChangedCallback(dataCallBack);
        if(emptyCallBack == null) {
            emptyCallBack = new EmptyCallBack();
        }
        this.viewModel.isEmpty.addOnPropertyChangedCallback(emptyCallBack);
    }

    protected void runStatusChange(boolean isRunning) {
        if (isRunning) {
            showProgressDialog(R.string.wait);
        } else {
            dismissProgressDialog();
        }
    }

    private class RunCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            runStatusChange(viewModel.isRunning.get());
        }
    }

    private class DataCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
            onDataResult(viewModel.data.get());
        }
    }

    private class EmptyCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
            dismissProgressDialog();
            onEmptyDisplaying();
        }
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS)
                .subscribe(permission->{
                    if (permission.granted) {
                        // 用户已经同意该权限
                        LogUtils.tag("zlq").e(permission.name + " is granted.");
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        LogUtils.tag("zlq").e(permission.name + " is denied. More info should be provided.");
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        LogUtils.tag("zlq").e( permission.name + " is denied.");
                    }
                });
    }
}

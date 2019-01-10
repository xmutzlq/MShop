package com.bop.android.shopping;

import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bop.android.shopping.databinding.ActivityMainBinding;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.SelectDialog;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.LoginViewModel;
import google.architecture.common.widget.NoScrollViewPager;
import google.architecture.coremodel.data.QRCodeData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

@Route(path = ARouterPath.AppMainAty)
public class ActivityMain extends BaseActivity<ActivityMainBinding> implements BottomNavigationView.OnNavigationItemSelectedListener{

    public static final int REQUEST_CODE_SCAN = 0x1;

    private NoScrollViewPager mPager;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private FragmentAdapter mAdapter;

    private LoginViewModel viewModel = new LoginViewModel();
    private QRCodeLoginCallBack callBack = new QRCodeLoginCallBack();
    private boolean isComfirmLogin;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(false);

        if(getToolbarHelper().getAppBarLayout() != null) {
            getToolbarHelper().getAppBarLayout().setVisibility(View.GONE);
        }

//        addBadgeAt(1, 5);

        binding.setOnNavigationItemSelectedListener(this);
        binding.navigation.enableAnimation(false);
        binding.navigation.enableShiftingMode(false);
        binding.navigation.enableItemShiftingMode(false);

        mPager = binding.containerPager;
        mPager.setOffscreenPageLimit(3);
        mPager.setScanScroll(false);

        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);

        Looper.myQueue().addIdleHandler(() -> {
            BaseFragment fragmentNews = (BaseFragment) ARouter.getInstance().build(ARouterPath.HomeFgt).navigation();
            BaseFragment fragmentGirls = (BaseFragment) ARouter.getInstance().build(ARouterPath.CategoryFgt).navigation();
            BaseFragment fragmentBids = (BaseFragment) ARouter.getInstance().build(ARouterPath.CartFgt).navigation();
            BaseFragment fragmentPersonal = (BaseFragment) ARouter.getInstance().build(ARouterPath.PersonalShoppingFgt).navigation();
            mFragments.add(fragmentNews);
            mFragments.add(fragmentGirls);
//            mFragments.add(fragmentBids);
            mFragments.add(fragmentPersonal);
            binding.setViewPaAdapter(mAdapter);
            return false;
        });

        viewModel.qrCodeData.addOnPropertyChangedCallback(callBack);
    }

    private void goTab(int position) {
        BaseApplication.getHandler().postDelayed(()->{
            binding.navigation.setCurrentItem(position);
            mPager.setCurrentItem(position);}, 300);
    }

    @Override
    protected void responseEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_OPEN_SCAN.equals(event.msgType)) {
//            Intent intent = new Intent(this, CaptureActivity.class);
////            startActivityForResult(intent, REQUEST_CODE_SCAN);
            ARouter.getInstance().build(ARouterPath.FootScanLoginAty).navigation(ActivityMain.this);
        } else if(CommEvent.MSG_TYPE_HOME_GO.equals(event.msgType)) {
            int position = event.bundle.getInt(CommKeyUtil.EXTRA_KEY);
            if(position >= 0) goTab(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SCAN) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null)  return;
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String token = bundle.getString(CodeUtils.RESULT_STRING);
                    if(TextUtils.isEmpty(token)) return;
                    String[] params = token.split("/");
                    if(params == null || params.length == 0) return;
                    token = params[params.length - 1].substring(0, params[params.length - 1].length() - 5);
                    final String finalToken = token;
                    SelectDialog.show(this_, getResources().getString(R.string.str_scan_login_title),
                            getResources().getString(R.string.str_scan_login_tip), (dialog, which) -> {
                        isComfirmLogin = false;
                        viewModel.qrcodeLogin(finalToken, "2");
                    });
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.showShortToast("二维码有误");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class QRCodeLoginCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            QRCodeData qrCodeData = viewModel.qrCodeData.get();
            if (qrCodeData != null) {
                ToastUtils.showShortToast(qrCodeData.errmsg);
                if(isComfirmLogin) return;
                isComfirmLogin = true;
                viewModel.qrcodeLogin(qrCodeData.user_token, "3");
            }
        }
    }

    @Override
    public void onUserLoginStateChange(boolean isLogin) {
        super.onUserLoginStateChange(isLogin);
        ARouter.getInstance()
                .build(ARouterPath.AppMainAty)
                .withFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .navigation(this_);
        if(!isLogin) {
            mFragments.get(2).onReLoad();
            goTab(2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            startActivity(intent);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.navigation_home) {
            mPager.setCurrentItem(0);
            return true;
        } else if (i == R.id.navigation_dashboard) {
            mPager.setCurrentItem(1);
            return true;
        } else if (i == R.id.navigation_notifications) {
            mPager.setCurrentItem(3);
            return true;
        }
//        else if (i == R.id.navigation_bids) {
//            mPager.setCurrentItem(2);
//            return true;
//        }
        return false;
    }

    /**
     * 添加消息
     * @param position
     * @param number
     * @return
     */
    private Badge addBadgeAt(int position, int number) {
        return new QBadgeView(this)
                .setBadgeNumber(number)
                .setGravityOffset(18, 2, true)
                .bindTarget(binding.navigation.getBottomNavigationItemView(position))
                .setOnDragStateChangedListener((dragState, badge, targetView)->{if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState){}});
    }
}

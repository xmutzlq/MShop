package com.king.android.details;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.king.android.details.adapter.DetailFragmentAdapter;
import com.king.android.details.cache.DetailInfoManager;
import com.king.android.details.view.DetCommodityNewFragment;
import com.king.android.details.view.DetailHeaderView;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.MessageDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.base.ViewManager;
import google.architecture.common.dialog.CustomDialog;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.AppCompat;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.DeviceUtils;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.DetailViewModel;
import google.architecture.common.widget.CustomPopWindow;
import google.architecture.common.widget.NoScrollViewPager;
import google.architecture.coremodel.data.CartNum;
import google.architecture.coremodel.data.xlj.goodsdetail.Like;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

@Route(path = ARouterPath.DetailAty)
public class ActivityDetails extends BaseActivity {

    private DetailHeaderView detailHeaderView;
    private NoScrollViewPager mViewPager;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private DetailFragmentAdapter mAdapter;

    private DetailViewModel detailViewModel;
    private DetailInfoManager mDetailInfoManager;

    private LinearLayout cartLay;
    private ImageView favoriteIv;
    private TextView favoriteTv;

    private int operType = 1;
    public boolean isFromUmengMessage;

    private java.util.List<Like> likes;

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_details;
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        isFromUmengMessage = true;
//        if(mFragments != null && mFragments.size() > 0) {
//            mFragments.get(0).onReLoad();
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getIns().setGoodsId("");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(false);
        setTitleName(getString(R.string.title_goods_detail));

        detailViewModel = new DetailViewModel();

        mViewPager = findViewById(R.id.detail_view_pager);
        mViewPager.setOffscreenPageLimit(4);
        //Head
        ViewGroup mHeader = findViewById(R.id.detail_header_fl);
        detailHeaderView = new DetailHeaderView(this_);
//        detailHeaderView.setHeadViewBgRes(false);
        detailHeaderView.setHeadViewBgRes(false, R.mipmap.bg_detail_bar);
        detailHeaderView.setIndicatorClickable(true);
        detailHeaderView.bindToViewPager(mViewPager);
        mHeader.removeAllViews();
        mHeader.addView(detailHeaderView);
        detailHeaderView.setHeadClickListener(v -> ViewManager.getInstance().finishActivity());

        cartLay = findViewById(R.id.detail_btn_go_cart);
        cartLay.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.CartAty).navigation();
        });

        /**首页**/
        findViewById(R.id.detail_btn_service).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CommKeyUtil.EXTRA_KEY, 0);
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_HOME_GO, bundle));
            ARouter.getInstance().build(ARouterPath.AppMainAty).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation(this_);
        });

        /**收藏**/
//        favoriteIv = findViewById(R.id.detail_btn_like_iv);
//        favoriteTv = findViewById(R.id.detail_btn_like_tv);
//        findViewById(R.id.detail_btn_like).setOnClickListener(v -> {
//            if(mDetailInfoManager == null) return;
//            if(mFragments.get(0) instanceof DetCommodityFragment) {
//                String goodsId = mDetailInfoManager.getGoodsId();
//                boolean hasFavorite = mDetailInfoManager.hasFavorites();
//                detailViewModel.checkFavorites(goodsId, hasFavorite, t -> {
//                    FavoriteResultData favoriteResultData = (FavoriteResultData) t;
//                    mDetailInfoManager.setHasFavorites(hasFavorite ? 0 : 1);
//                    boolean favorite = mDetailInfoManager.hasFavorites();
//                    updateFavorite(favoriteResultData.getCollect_flag() == 1);
//                });
//            }
//        });

        /**加入购物车**/
//        findViewById(R.id.detail_btn_add_cart).setOnClickListener(v -> {
//            if(mDetailInfoManager == null) return;
//            if(mFragments.get(0) instanceof DetCommodityFragment) {
//                ((DetCommodityFragment)mFragments.get(0)).showBuyDialog(DetBottomSheetSpecFragment.TYPE_ADD_CART);
//            }
//        });

        /**立即购买**/
        findViewById(R.id.detail_btn_buy).setOnClickListener(v -> {
            if(mFragments.get(0) instanceof DetCommodityNewFragment) {
                ((DetCommodityNewFragment)mFragments.get(0)).showQrCode();
            }
        });

        mAdapter = new DetailFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0) {
                    //detailHeaderView.updateHead(false, positionOffsetPixels, ScreenUtils.getScreenWidth());
                }
            }

            @Override
            public void onPageSelected(int position) {
                if(position > 0) {
                    //detailHeaderView.updateHead(false, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenWidth());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

//        updateHeader(false, 0, ScreenUtils.getScreenWidth());

        Looper.myQueue().addIdleHandler(() -> {
            AppCompat.schedulePostponeEnterTransition(this_);
            //Contents
            BaseFragment fragmentCommodity = (BaseFragment) ARouter.getInstance().build(ARouterPath.DetailCommodityFgt).navigation();
            BaseFragment fragmentDetail = (BaseFragment) ARouter.getInstance().build(ARouterPath.DetailDetailFgt).navigation();
            BaseFragment fragmentComment = (BaseFragment) ARouter.getInstance().build(ARouterPath.DetailCommentFgt).navigation();
            BaseFragment fragmentRecommend = (BaseFragment) ARouter.getInstance().build(ARouterPath.DetailRecommendFgt).navigation();

            mFragments.add(fragmentCommodity);
            mFragments.add(fragmentDetail);
            mFragments.add(fragmentComment);
            mFragments.add(fragmentRecommend);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setScanScroll(true);

            return false;
        });
    }

    public void setLikes(java.util.List<Like> likes) {
        this.likes = likes;
    }

    public java.util.List<Like> getLikes() {
        return this.likes;
    }

    public void setNeedIntercept(boolean isNeedIntercept) {
        if(mFragments.get(0) instanceof DetCommodityNewFragment) {
            ((DetCommodityNewFragment)mFragments.get(0)).setNeedIntercept(isNeedIntercept);
        }
    }

    /**刷新所有Fragment, 除了Fragment(0)**/
    public void refreshFragments() {
        for (int i = 0; i < mFragments.size(); i++) {
            if(i > 0) {
                BaseFragment fragment = mFragments.get(i);
                fragment.onReLoad();
            }
        }
    }

    public void gotoTag(int index) {
        mViewPager.postDelayed(() -> {
            mViewPager.setCurrentItem(index);
        }, 100);
    }

    public void updateHeader(boolean isVertical, int verticalOffset, int totalScrollRange) {
        detailHeaderView.updateHead(isVertical, verticalOffset, totalScrollRange);
    }

    public void interceptScroll(boolean canScroll) {
        mViewPager.setScanScroll(canScroll);
    }

    public void setDispatchView(View view) {
        mViewPager.setDispatchView(view);
    }

    public void setWebDetailSelfControl(boolean isWebDetailSelfControl) {
        mViewPager.isWebDetailSelfControl = isWebDetailSelfControl;
    }

    public void playHeadTitleAnimat(boolean isClose) {
         detailHeaderView.playHeaderTitleAnimat(isClose);
    }

    @Override
    public boolean dispatchBackPressed() {
        if(mViewPager.getCurrentItem() != 0) {
            gotoTag(0);
            return true;
        }
        return false;
    }

    @Override
    protected void responseEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_UPDATE_FAVORITE_CART.equals(event.msgType)) { //更新购物车和收藏状态
//            mDetailInfoManager = ((DetCommodityFragment)mFragments.get(0)).getDetailInfoManager();
            Bundle bundle = event.bundle;
            String favorite = bundle.getString(CommKeyUtil.EXTRA_KEY);
            String cartNum = bundle.getString(CommKeyUtil.EXTRA_KEY_2);
            updateFavorite(Integer.valueOf(favorite) > 0);
            updateCartNum(Integer.valueOf(cartNum));
        } else if(CommEvent.MSG_TYPE_UPDATE_CART.equals(event.msgType)) { //更新购物车
            Bundle bundle = event.bundle;
            String[] params = bundle.getStringArray(CommKeyUtil.EXTRA_KEY);
            if(params == null || params.length < 2) return;
            String itemIds = params[0];
            if(params[0].substring(params[0].length() - 1).equals(",")) {
                itemIds = params[0].substring(0, params[0].length() - 1);
            }
            detailViewModel.addCart(mDetailInfoManager.getGoodsId(),
                    itemIds, Integer.valueOf(params[1]), t -> {
                CartNum result = (CartNum) t;
                int cartNum = result.getCart_num();
                updateCartNum(cartNum);
            }, (code, msg) -> {
                LogUtils.tag("zlq").e("msg = " + msg);
                ToastUtils.showShortToast(msg);});
        } else if(CommEvent.MSG_OPEN_GOODS_DETAIL_PAGE.equals(event.msgType)) {
            release();
            Bundle bundle = event.bundle;
            boolean isFromUmeng = bundle.getBoolean(CommKeyUtil.EXTRA_KEY_2);
            if(isFromUmeng) {
                String productNo = bundle.getString(CommKeyUtil.EXTRA_KEY);
                ARouter.getInstance().build(ARouterPath.DetailAty)
                        .withString(CommEvent.KEY_EXTRA_VALUE_2, productNo).navigation(this_);
            } else {
                String goodsId = bundle.getString(CommKeyUtil.EXTRA_KEY);
                ARouter.getInstance().build(ARouterPath.DetailAty)
                        .withString(CommEvent.KEY_EXTRA_VALUE, goodsId).navigation(this_);
            }
            ViewManager.getInstance().finishActivity();
        }
    }

    private void updateFavorite(boolean haveFavorite) {
        favoriteIv.setImageResource(haveFavorite ? R.mipmap.favorite : R.mipmap.like);
        favoriteTv.setText(haveFavorite ? "已收藏" : "收藏");
    }

    private void updateCartNum(int num) {
        addBadgeAt(num);
    }

    /**
     * 添加购物车数量
     * @param number
     * @return
     */
    private Badge addBadgeAt(int number) {
        return new QBadgeView(this)
                .setBadgeNumber(number)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeTextSize(10, true)
                .setGravityOffset(0, 0, true)
                .bindTarget(cartLay)
                .setOnDragStateChangedListener((dragState, badge, targetView)->{if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState){}});
    }

}

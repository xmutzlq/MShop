package com.king.android.details.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.king.android.details.ActivityDetails;
import com.king.android.details.R;
import com.king.android.details.adapter.DetailBannerAdapter;
import com.king.android.details.adapter.DetailCommentAdapter;
import com.king.android.details.adapter.DetailDividerItemLineDecoration;
import com.king.android.details.adapter.DetailRecommendParentAdapter;
import com.king.android.details.cache.DetailInfoManager;
import com.king.android.details.databinding.FragmentDetCommodityBinding;
import com.king.android.details.util.DetailUtil;
import com.king.android.details.util.SpecParams;
import com.king.android.details.widget.DetailRecommendCircleNavigator;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.base.ViewManager;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.AppCompat;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.xlj.GoodsDetailRequestEntity;
import google.architecture.common.viewmodel.xlj.XLJ_GoodsDetailViewModel;
import google.architecture.common.widget.CustomPopWindow;
import google.architecture.common.widget.hmore.RefreshCallBack;
import google.architecture.common.widget.hmore.pager.GravityPagerSnapHelper;
import google.architecture.common.widget.nested.MyNestedScrollView;
import google.architecture.common.widget.nested.SlideDetailsLayout;
import google.architecture.common.widget.preload.FiftyShadesOf;
import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.data.DetailSpecSelectedInfo;
import google.architecture.coremodel.data.xlj.goodsdetail.GoodsDetailData;
import google.architecture.coremodel.data.xlj.goodsdetail.Like;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.ConvertUtils;
import google.architecture.coremodel.util.TextUtil;

/**
 * @author lq.zeng
 * @date 2018/6/8
 */
@Route(path = ARouterPath.DetailCommodityFgt)
public class DetCommodityNewFragment extends BaseFragment<FragmentDetCommodityBinding> implements AppBarLayout.OnOffsetChangedListener,
        RefreshCallBack, SlideDetailsLayout.OnSlideDetailsListener {

    private LayoutInflater mLayoutInflater;
    private ActivityDetails mActivityDetails;
    private boolean isLikeState;
    private boolean isRecommendState;
    private boolean hasLoadDetailWeb;

    private XLJ_GoodsDetailViewModel xlj_goodsDetailViewModel;
    private FiftyShadesOf fiftyShadesOf;

    private DetailInfoManager detailInfoManager;
    private DetailBannerAdapter adapter;

    private GoodsDetailData info;
    private String goodsId;
    private String goodsNo;

    public DetailInfoManager getDetailInfoManager() {
        return detailInfoManager;
    }

    public void showBuyDialog(int type) {
        if(detailInfoManager == null || detailInfoManager.getDetailInfo() == null
                || detailInfoManager.getDetailInfo().getSpec_goods_list().size() == 0) return;
        DetBottomSheetSpecFragment detBottomSheetSpecFragment = new DetBottomSheetSpecFragment();
        detBottomSheetSpecFragment.setData(detailInfoManager.getGoodsId(),
                detailInfoManager.getDetailInfo().getSpec_goods_list(), detailInfoManager.getDetailInfo().getSpec_select_info());
        detBottomSheetSpecFragment.setOperateType(type);
        detBottomSheetSpecFragment.show(getChildFragmentManager(), "Dialog");
    }

    public void goService() {
        if(detailInfoManager == null || detailInfoManager.getDetailInfo() == null
                || detailInfoManager.getDetailInfo().getItem() == null || TextUtil.isEmpty(detailInfoManager.getDetailInfo().getItem().getChat_url())) return;
        ARouter.getInstance().build(ARouterPath.WebPage).withString(CommKeyUtil.EXTRA_KEY, detailInfoManager.getDetailInfo().getItem().getChat_url()).navigation(mActivityDetails);
    }

    public void showQrCode() {
        if(info == null) return;
        final View rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_det_qrcode, null);
        ImageView imageView = rootView.findViewById(R.id.det_qr_code_iv);
        ImageLoader.get().load(imageView, ApiConstants.XLJimgHost + info.getQrcodeImg(), new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                ToastUtils.showShortToast("请检查网络是否链接");
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                imageView.setImageDrawable(resource);
                new CustomPopWindow.PopupWindowBuilder(mContext)
                        .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                        .setBgDarkAlpha(0.5f) // 控制亮度
                        .setView(rootView)
                        .create()
                        .showAtLocation(rootView,Gravity.CENTER,0,0);
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null && context instanceof ActivityDetails) {
            mActivityDetails = (ActivityDetails) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityDetails = null;
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_det_commodity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailInfoManager = new DetailInfoManager();
        mLayoutInflater = LayoutInflater.from(getContext());
        ViewGroup.LayoutParams layoutParams = binding.detailCommodityBannerRl.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenWidth();
        binding.detailCommodityBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateBannerIndicator(position, binding.detailCommodityBanner.getAdapter().getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.detailNestedScrollView.setFillViewport(true);
        binding.detailNestedScrollView.setOnScrollChangeListener((MyNestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                    || scrollY == 0) { //滚动到底或顶
                binding.detailNestedScrollView.setOutSizeInterrupt(true);
            }
        });

        binding.detailSlide.setOnSlideDetailsListener(this);

        binding.detailNestedScrollView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fadein));
    }

    @Override
    public void onMessageEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_OPEN_ANIMATOR.equals(event.msgType)) {
            AppCompat.scheduleStartPostponedTransition(mActivityDetails, binding.detailCommodityBannerRl);
        }
    }

    @Override
    public void onFragmentFirstVisible() {
        if(null != getActivity() && getActivity().getIntent() != null) {
            goodsId = getActivity().getIntent().getStringExtra(CommEvent.KEY_EXTRA_VALUE);
            goodsNo = getActivity().getIntent().getStringExtra(CommEvent.KEY_EXTRA_VALUE_2);
            loadCommodityData(goodsId, goodsNo);
        }
    }

    @Override
    public void onReLoad() {
        super.onReLoad();
        goodsNo = getActivity().getIntent().getStringExtra(CommEvent.KEY_EXTRA_VALUE_2);
        loadCommodityData(goodsId, goodsNo);
    }

    @Override
    protected void onDataResult(Object o) {
        info = (GoodsDetailData) o;

        DetDetailFragment.url = info.getGoodsDesc();

        if(!hasLoadDetailWeb) {
            hasLoadDetailWeb = true;
            BaseFragment fragment = (BaseFragment) ARouter.getInstance().build(ARouterPath.DetailDetailFgt)
                    .withBoolean(DetDetailFragment.DetDetailTag, false)
                    .withString(CommKeyUtil.EXTRA_KEY, info.getGoodsDesc()).navigation();
            mActivityDetails.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_bottom_page, fragment).commit();
        }

        //gallery
        adapter = new DetailBannerAdapter(mContext, new ArrayList<>(info.getGallery()));
        binding.detailCommodityBanner.setAdapter(adapter);
        updateBannerIndicator(0, info.getGallery().size());

        //名称价格区
        binding.xljLayoutPrice.detailLayoutName.setText(info.getGoodsName());
        binding.xljLayoutPrice.detailLayoutPromotionPrice.setText("¥" + info.getShopPrice());
        if(!TextUtils.isEmpty(info.getMarketPrice())) {
            binding.xljLayoutPrice.detailLayoutPrice.setText("¥" + info.getMarketPrice());
            binding.xljLayoutPrice.detailLayoutPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        } else {
            binding.xljLayoutPrice.detailLayoutPrice.setVisibility(View.GONE);
        }

        //优惠券区
        if(info.getCouponsList() != null && info.getCouponsList().size() > 0) {
            binding.xljLayoutTicket.detailLayoutTicketName.setText(getString(R.string.detail_ticket_get_value,
                    info.getCouponsList().get(0).getUseMoney(), info.getCouponsList().get(0).getCouponValue()));
            binding.xljLayoutTicket.detailLayoutTicketGiftTv.setText(getString(R.string.detail_ticket_gift_value));
            binding.xljLayoutTicket.getRoot().setOnClickListener(view -> {
                TicketBottomSheetFragment ticketBottomSheetFragment = new TicketBottomSheetFragment();
                ticketBottomSheetFragment.show(getChildFragmentManager(), "Dialog");
            });
        } else {
            binding.xljLayoutTicket.getRoot().setVisibility(View.GONE);
        }

        //已选
        binding.xljLayoutChoice.xljLayoutChoiceChoose.choiceItemLeftStr.setText(R.string.detail_choice);
        String color = "";
        String size = "";
        if(info.getSpec() != null
                && info.getSpec().get1() != null
                && info.getSpec().get1().getList() != null
                && info.getSpec().get1().getList().size() > 0
                && info.getDefaultSpecs() != null
                && info.getDefaultSpecs().get1() != null) {
            color = DetailUtil.getDefaultColor(info.getSpec().get1().getList(), info.getDefaultSpecs().get1());
        }
        if(info.getSpec() != null
                && info.getSpec().get5() != null
                && info.getSpec().get5().getList() != null
                && info.getSpec().get5().getList().size() > 0
                && info.getDefaultSpecs() != null
                && info.getDefaultSpecs().get5() != null) {
            size = DetailUtil.getDefaultSizeAndSaveCount(info.getSpec().get5().getList(), info.getDefaultSpecs().get5()).pSize;
        }
        updateSpec(color + "；" + size);

        binding.xljLayoutChoice.xljLayoutChoiceServer.choiceItemLeftStr.setText(R.string.detail_server);
        binding.xljLayoutChoice.xljLayoutChoiceServer.choiceItemCenterStr.setText(R.string.detail_server_str);

        binding.xljLayoutChoice.xljLayoutChoiceParams.choiceItemLeftStr.setText(R.string.detail_param);
        binding.xljLayoutChoice.xljLayoutChoiceParams.choiceItemCenterStr.setText(R.string.detail_param_str);

        if(!TextUtils.isEmpty(color) && !TextUtils.isEmpty(size)) {
            //已选
            binding.xljLayoutChoice.xljLayoutChoiceChoose.getRoot().setOnClickListener(v -> {
                ChooseBottomSheetFragment chooseBottomSheetFragment = new ChooseBottomSheetFragment();

                SpecParams specParams = new SpecParams();
                SpecParams.SpecChild specChild1 = new SpecParams.SpecChild();
                specChild1.name = info.getSpec().get1().getName();
                specChild1.detailSpecInfos = info.getSpec().get1().getList();
                specParams.specsColor = specChild1;

                SpecParams.SpecChild specChild5 = new SpecParams.SpecChild();
                specChild5.name = info.getSpec().get5().getName();
                specChild5.detailSpecInfos = info.getSpec().get5().getList();
                specParams.specsSize = specChild5;

                specParams.defaultColorId = info.getDefaultSpecs().get1();
                specParams.defaultSizeId = info.getDefaultSpecs().get5();
                specParams.img = ApiConstants.XLJimgHost + info.getGoodsImg();
                specParams.price = info.getShopPrice();
                chooseBottomSheetFragment.setData(specParams);
                chooseBottomSheetFragment.setNotifySpecChange(newSpec ->  updateSpec(newSpec));
                chooseBottomSheetFragment.show(getChildFragmentManager(), "Dialog");
            });
        }

        binding.xljLayoutChoice.xljLayoutChoiceServer.getRoot().setOnClickListener(v -> {
            ServerBottomSheetFragment serverBottomSheetFragment = new ServerBottomSheetFragment();
            serverBottomSheetFragment.show(getChildFragmentManager(), "Dialog");
        });
        binding.xljLayoutChoice.xljLayoutChoiceParams.getRoot().setOnClickListener(v -> {
            if(info.getAttrs() == null || info.getAttrs().size() == 0) return;
            ParamBottomSheetFragment paramBottomSheetFragment = new ParamBottomSheetFragment();
            paramBottomSheetFragment.setData(info.getAttrs());
            paramBottomSheetFragment.show(getChildFragmentManager(), "Dialog");
        });

        //评价
        String commentNum = mContext.getResources().getString(R.string.comm_str_comment_count, info.getAppraiseNum());
        String commentPercent = "查看全部";
        binding.layoutDetComment.detailCommodityCommentTv.setLeftString(Spans.builder().text(commentNum,
                14, ContextCompat.getColor(mContext, R.color.black)).build());
        binding.layoutDetComment.detailCommodityCommentTv.setRightTvDrawableRight(this.getResources().getDrawable(R.drawable.ic_arrow_right));
        binding.layoutDetComment.detailCommodityCommentTv.getRightTextView().setCompoundDrawablePadding(10); //调整间距
        binding.layoutDetComment.detailCommodityCommentTv.setRightString(Spans.builder().text(commentPercent,
                14, ContextCompat.getColor(mContext, R.color.color_A30598)).build());
        binding.layoutDetComment.detailCommodityCommentTv.setBottomDividerLineVisibility(View.GONE); //去线
        DetailCommentAdapter commentAdapter = new DetailCommentAdapter(R.layout.comment_item, info.getGoodsAppr());
        binding.layoutDetComment.detailCommodityCommentRec.addItemDecoration(new DetailDividerItemLineDecoration(mContext));
        binding.layoutDetComment.detailCommodityCommentRec.setAdapter(commentAdapter);
        binding.layoutDetComment.detailCommodityCommentRec.setNestedScrollingEnabled(false);
        binding.layoutDetComment.detailCommodityCommentRec.setHasFixedSize(true);
        binding.layoutDetComment.detailCommodityCommentRec.setLayoutManager(new LinearLayoutManager(mContext));


        //猜你喜欢
        isRecommendState = true;
        LinearLayoutManager detailLinearLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);
        binding.layoutDetRecommend.detailCommodityRecommendRec.setLayoutManager(detailLinearLayoutManager);
        binding.layoutDetRecommend.detailCommodityRecommendRec.setNestedScrollingEnabled(false);
        binding.layoutDetRecommend.detailCommodityRecommendRec.setHasFixedSize(true);

        if(mActivityDetails != null) mActivityDetails.setLikes(info.getLike());

        java.util.List<Like> pageDetailRecommendInfos = new ArrayList<>(info.getLike());
        //总记录数
        int rows = pageDetailRecommendInfos.size();
        //每页显示的记录数
        int pageCount = 6;
        //页数
        int pageSize = (rows - 1) / pageCount + 1;
        if(pageSize == 0) pageSize = 1;
        java.util.List<java.util.List<Like>> pages = ConvertUtils.averageAssign(pageDetailRecommendInfos, pageSize);
        DetailRecommendParentAdapter detailRecommendAdapter = new DetailRecommendParentAdapter(R.layout.layout_comm_recyclerview_auto_size, pages);
        binding.layoutDetRecommend.detailCommodityRecommendRec.setAdapter(detailRecommendAdapter);
        final DetailRecommendCircleNavigator circleNavigator = new DetailRecommendCircleNavigator(mContext);
        circleNavigator.setFollowTouch(false);
        circleNavigator.setCircleCount(pageSize);
        new GravityPagerSnapHelper(Gravity.START, true, position -> {
            circleNavigator.onPageSelected(position);
        }).attachToRecyclerView(binding.layoutDetRecommend.detailCommodityRecommendRec);
        binding.layoutDetRecommend.detailCommodityRecommendIndicator.setNavigator(circleNavigator);

        if(mActivityDetails.isFromUmengMessage) {
            mActivityDetails.isFromUmengMessage = false;
            mActivityDetails.refreshFragments();
        }
    }

    private void loadCommodityData(String goodsId, String goodsNo) {
        xlj_goodsDetailViewModel = new XLJ_GoodsDetailViewModel();
        addRunStatusChangeCallBack(xlj_goodsDetailViewModel);
        xlj_goodsDetailViewModel.getGoodsDetail(GoodsDetailRequestEntity.getRequestJson(goodsId, goodsNo),
                (code, msg) -> {
                    if(code == 12000 && !TextUtil.isEmpty(msg)) {
                        ToastUtils.showShortToast(msg);
                        ViewManager.getInstance().finishActivity();
                    }
                });
    }

    private void updateBannerIndicator(int position, int count) {
        binding.detailBannerIndicatorTv.setText(Spans.builder().text(String.valueOf(position + 1),16, Color.WHITE)
                .text("/", 12, Color.WHITE)
                .text(String.valueOf(count), 12, Color.WHITE).build());
    }

    @Override
    protected void responseEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_GOODS_SPC_UPDATE.equals(event.msgType)) { //规格更新
            Bundle bundle = event.bundle;
            if(bundle != null) {
                DetailSpecSelectedInfo detailSpecSelectedInfo = bundle.getParcelable(CommKeyUtil.EXTRA_KEY);
                if(detailSpecSelectedInfo != null) {
                    detailInfoManager.updateSpecSelected(detailSpecSelectedInfo);
                }
            }
        }
    }

    private void updateSpec(String spec) {
        binding.xljLayoutChoice.xljLayoutChoiceChoose.choiceItemCenterStr.setText(spec);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        binding.detailAppbarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        binding.detailAppbarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        if(mActivityDetails != null) mActivityDetails.updateHeader(true, verticalOffset, appBarLayout.getTotalScrollRange());
    }

    @Override
    public void onLeftRefreshing() {

    }

    @Override
    public void onRightRefreshing() {
    }

    @Override
    public void onInterceptTouchEvent(boolean isRelease) {
        if(mActivityDetails != null) {
            mActivityDetails.interceptScroll(isRelease);
        }
    }

    public void setNeedIntercept(boolean isNeedIntercept) {
        binding.detailSlide.setNeedIntercept(isNeedIntercept);
    }

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if(status == SlideDetailsLayout.Status.CLOSE) { //当前为商品详情页
            mActivityDetails.setWebDetailSelfControl(false);
            binding.detailSlide.smoothClose(true);
            setNeedIntercept(false);
            binding.detailPullTagIv.setImageResource(R.mipmap.ic_arrow_up_full);
            binding.detailPullTipTv.setText(R.string.detail_pull_up_tip);
            mActivityDetails.playHeadTitleAnimat(true);
            mActivityDetails.interceptScroll(true);
        } else if(status == SlideDetailsLayout.Status.OPEN) { //当前为图文详情页
            mActivityDetails.setWebDetailSelfControl(true);
            binding.detailSlide.smoothOpen(true);
            setNeedIntercept(true);
            binding.detailPullTagIv.setImageResource(R.mipmap.ic_arrow_down_full);
            binding.detailPullTipTv.setText(R.string.detail_pull_down_tip);
            mActivityDetails.playHeadTitleAnimat(false);
            mActivityDetails.interceptScroll(false);
        }
    }
}

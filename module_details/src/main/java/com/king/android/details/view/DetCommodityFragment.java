package com.king.android.details.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.details.ActivityDetails;
import com.king.android.details.R;
import com.king.android.details.adapter.DetailBannerAdapter;
import com.king.android.details.adapter.DetailCommentAdapter;
import com.king.android.details.adapter.DetailDividerItemDecoration;
import com.king.android.details.adapter.DetailDividerItemLineDecoration;
import com.king.android.details.adapter.DetailDividerItemMarginDecoration;
import com.king.android.details.adapter.DetailHotRecAdapter;
import com.king.android.details.adapter.DetailHotRecEntity;
import com.king.android.details.adapter.DetailRecommendAdapter;
import com.king.android.details.adapter.DetailRecommendParentAdapter;
import com.king.android.details.cache.DetailInfoManager;
import com.king.android.details.databinding.FragmentDetCommodityBinding;
import com.king.android.details.widget.DetailRecommendCircleNavigator;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.util.ConvertUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.AppCompat;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.viewmodel.DetailCommdityViewModel;
import google.architecture.common.widget.CommFlowLayout;
import google.architecture.common.widget.adapter.TagAdapter;
import google.architecture.common.widget.hmore.HorizontalRefreshLayout;
import google.architecture.common.widget.hmore.LoadingRefreshHeader;
import google.architecture.common.widget.hmore.RefreshCallBack;
import google.architecture.common.widget.hmore.pager.GravityPagerSnapHelper;
import google.architecture.common.widget.nested.MyNestedScrollView;
import google.architecture.common.widget.nested.SlideDetailsLayout;
import google.architecture.common.widget.preload.FiftyShadesOf;
import google.architecture.common.widget.span.RoundBackgroundColorSpan;
import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.data.DetailMainInfo;
import google.architecture.coremodel.data.DetailRecommendInfo;
import google.architecture.coremodel.data.DetailSpecSelectedInfo;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.TextUtil;

/**
 * @author lq.zeng
 * @date 2018/6/8
 */
@Route(path = ARouterPath.DetailCommodityFgt)
public class DetCommodityFragment extends BaseFragment<FragmentDetCommodityBinding> implements AppBarLayout.OnOffsetChangedListener,
        RefreshCallBack, SlideDetailsLayout.OnSlideDetailsListener {

    private LayoutInflater mLayoutInflater;
    private ActivityDetails mActivityDetails;
    private boolean isLikeState;
    private boolean isRecommendState;
    private boolean hasLoadDetailWeb;

    private DetailCommdityViewModel mDetailCommdityViewModel;
    private FiftyShadesOf fiftyShadesOf;

    private View allCommentBtn;

    private DetailInfoManager detailInfoManager;
    private DetailBannerAdapter adapter;

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
        allCommentBtn = findViewById(view, R.id.common_left_btn);
        allCommentBtn.setOnClickListener(v -> {
            if(mActivityDetails != null) {
                mActivityDetails.gotoTag(2);
            }
        });

        detailInfoManager = new DetailInfoManager();
        mLayoutInflater = LayoutInflater.from(getContext());
        ViewGroup.LayoutParams layoutParams = binding.detailCommodityBannerRl.getLayoutParams();
        layoutParams.height = (int) (ScreenUtils.getScreenHeight() * 0.56);
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

//        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)binding.detailNestedScrollView.getLayoutParams()).getBehavior();
//        if(behavior != null && behavior instanceof MyBehavior) {
//            ((MyBehavior)behavior).setBehaviorListener(isOnMove -> {
//                if (mActivityDetails != null) mActivityDetails.interceptScroll(!isOnMove);
//            });
//        }

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
            String goodsId = getActivity().getIntent().getStringExtra(CommEvent.KEY_EXTRA_VALUE);
            loadCommodityData(goodsId);
        }
    }

    @Override
    protected void onDataResult(Object o) {
        DetailMainInfo info = (DetailMainInfo) o;

        if(!hasLoadDetailWeb) {
            hasLoadDetailWeb = true;
            mActivityDetails.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_bottom_page, DetDetailFragment.newInstance(false, info.getItem().getDetail_url()),
                            DetDetailFragment.class.getSimpleName()).commit();
        }

        detailInfoManager.bindData(info);
        //更新收藏和购物车
        Bundle bundle = new Bundle();
        bundle.putString(CommKeyUtil.EXTRA_KEY, info.getItem().getGoods_user_collect());
        bundle.putString(CommKeyUtil.EXTRA_KEY_2, info.getCart_num());
        EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_UPDATE_FAVORITE_CART, bundle));

        //gallery
        adapter = new DetailBannerAdapter(mContext, new ArrayList<>(info.getGoods_images_list()));
        binding.detailCommodityBanner.setAdapter(adapter);
        updateBannerIndicator(0, info.getGoods_images_list().size());

        //价格区
        updateGoodsPrice(info.getSpec_select_info().getPrice(), info.getItem().getMarket_price());

        //标题区
        updateGoodsTitle(info.getSpec_select_info().getGoods_name());

        //关注
        binding.layoutDetTitle.detailCommodityFollow.setVisibility(View.GONE);
//        binding.layoutDetTitle.detailCommodityFollowStateIv.setUnLikeType(ThumbUpView.LikeType.broken);
//        binding.layoutDetTitle.detailCommodityFollowStateIv.setCracksColor(Color.WHITE);
//        binding.layoutDetTitle.detailCommodityFollowStateIv.setFillColor(ContextCompat.getColor(mContext, R.color.color_FD644B));
//        binding.layoutDetTitle.detailCommodityFollowStateIv.setEdgeColor(ContextCompat.getColor(mContext, R.color.color_282828));
//        binding.layoutDetTitle.detailCommodityFollowStateIv.setOnThumbUp(like -> {
//            isLikeState = like;
//            binding.layoutDetTitle.detailCommodityFollowStateTv.setText(like ? R.string.comm_str_follow : R.string.comm_str_unfollow);
//        });
//        binding.layoutDetTitle.detailCommodityFollowStateIv.UnLike();
//        binding.layoutDetTitle.detailCommodityFollowStateTv.setText(R.string.comm_str_unfollow);
//        binding.layoutDetTitle.detailCommodityFollow.setOnClickListener(v -> {
//            if(!isLikeState) {
//                binding.layoutDetTitle.detailCommodityFollowStateIv.Like();
//            } else {
//                binding.layoutDetTitle.detailCommodityFollowStateIv.UnLike();
//            }
//        });

        //券
//        String couponsVal = "【足球盛宴，畅想狂欢】荣誉勋章，SSD+HDD双硬盘，支持M.2接口固态硬盘，两级可调背光键盘，GTX1050Ti“吃鸡”利器【购物返券包】";
        String couponsVal = info.getItem().getGoods_remark();
        binding.layoutDetTitle.detailCommodityCoupons.setText(Spans.builder().text(couponsVal, 13, ContextCompat.getColor(mContext, R.color.color_666666))
                /*.newSpanPart(0, 1, new ForegroundColorSpan(Color.BLACK))
                .newSpanPart(10, 11, new ForegroundColorSpan(Color.BLACK))
                .newSpanPart(couponsVal.length() - 7, couponsVal.length(), new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_FD644B)))
                .newSpanPart(couponsVal.length() - 7, couponsVal.length(), new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        ToastUtils.showShortToast("购物券");
                    }
                })*/.build());

        //已选
        updateGoodsSelectedSpc(info.getSpec_select_info().getGoods_name());

        //配送
        String addressVal = "福建>厦门>思明区>城区\n现货，18:00前下单,预计06月27日(周三)送达";
        binding.layoutDetShippingAddress.detailCommodityShippingAddressTv.setVisibility(View.GONE);
        binding.layoutDetShippingAddress.detailCommodityShippingAddressTv.setLeftString(Spans.builder().text(mContext.getResources().getString(R.string.comm_str_shipping_address),
                12, ContextCompat.getColor(mContext, R.color.bg_999999)).build());
        binding.layoutDetShippingAddress.detailCommodityShippingAddressTv.setCenterTvDrawableLeft(this.getResources().getDrawable(R.mipmap.ic_location));
        binding.layoutDetShippingAddress.detailCommodityShippingAddressTv.getCenterTextView().setCompoundDrawablePadding(10); //调整图标间距
        binding.layoutDetShippingAddress.detailCommodityShippingAddressTv.setCenterString(Spans.builder().text(addressVal,13, ContextCompat.getColor(mContext, R.color.color_666666))
                .newSpanPart(13, 15, new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_FD644B)))
                .newSpanPart(13, 15, new StyleSpan(Typeface.BOLD)).build());
        binding.layoutDetShippingAddress.detailCommodityShippingAddressTv.setBottomDividerLineVisibility(View.GONE); //去线

        //热门配件
        List<DetailHotRecEntity> detailHotRecEntitList = new ArrayList<>();
        DetailHotRecEntity entity1 = new DetailHotRecEntity("鼠标",
                "https://img12.360buyimg.com/img/jfs/t17584/27/382255612/15593/31e4b670/5a700830N4a9fe862.jpg");
        detailHotRecEntitList.add(entity1);
        DetailHotRecEntity entity2 = new DetailHotRecEntity("键盘",
                "https://img12.360buyimg.com/img/jfs/t15520/287/1978144911/20699/158b839e/5a700c47N066b6dae.jpg");
        detailHotRecEntitList.add(entity2);
        DetailHotRecEntity entity3 = new DetailHotRecEntity("电脑耳麦",
                "https://img12.360buyimg.com/img/jfs/t14746/79/2126116649/17622/166d1837/5a700c72Nef9064e7.jpg");
        detailHotRecEntitList.add(entity3);
        DetailHotRecEntity entity4 = new DetailHotRecEntity("显示器",
                "https://img12.360buyimg.com/img/jfs/t19495/218/359359191/44113/15577b51/5a700c9eN7f163e65.jpg");
        detailHotRecEntitList.add(entity4);
        DetailHotRecEntity entity5 = new DetailHotRecEntity("U盘",
                "https://img12.360buyimg.com/img/jfs/t19627/62/359424737/14618/f55d3200/5a70084bN3052d2c0.jpg");
        detailHotRecEntitList.add(entity5);
        DetailHotRecEntity entity6 = new DetailHotRecEntity("鼠标垫",
                "https://img12.360buyimg.com/img/jfs/t15517/62/2029859222/18632/725ad22d/5a700ac9N7d3ce907.jpg");
        detailHotRecEntitList.add(entity6);
        DetailHotRecEntity entity7 = new DetailHotRecEntity("移动硬盘",
                "https://img12.360buyimg.com/img/jfs/t14635/5/2153389228/13632/fd26c618/5a700ad7Ncdbf82c2.jpg");
        detailHotRecEntitList.add(entity7);

        DetailHotRecAdapter adapter = new DetailHotRecAdapter(R.layout.item_horizon, detailHotRecEntitList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        binding.layoutDetHotRec.detailCommodityHotRec.setLayoutManager(linearLayoutManager);
        binding.layoutDetHotRec.detailCommodityHotRec.addItemDecoration(new DetailDividerItemDecoration(mContext));
        binding.layoutDetHotRec.detailCommodityHotRec.setAdapter(adapter);
        binding.layoutDetHotRec.detailCommodityHotRec.setNestedScrollingEnabled(false);
        binding.layoutDetHotRec.detailCommodityHotRec.setHasFixedSize(true);

        binding.layoutDetHotRec.detailCommodityHotRecTv.setLeftString(Spans.builder().text(mContext.getResources().getString(R.string.comm_str_hot_rec),
                12, ContextCompat.getColor(mContext, R.color.bg_999999)).build());
        binding.layoutDetHotRec.detailCommodityHotRecTv.setRightString(Spans.builder().text(mContext.getResources().getString(R.string.comm_str_check_all),
                12, ContextCompat.getColor(mContext, R.color.bg_999999)).build());
        binding.layoutDetHotRec.detailCommodityHotRecTv.setBottomDividerLineVisibility(View.GONE); //去线

        binding.layoutDetHotRec.detailCommodityHotRecRefresh.setRefreshCallback(this);
        binding.layoutDetHotRec.detailCommodityHotRecRefresh.setRefreshHeader(new LoadingRefreshHeader(mContext), HorizontalRefreshLayout.RIGHT);
        binding.layoutDetHotRec.detailCommodityHotRecRefresh.setVisibility(View.GONE);
        binding.layoutDetHotRec.detailCommodityHotRecTv.setVisibility(View.GONE);

        //评价
        String commentNum = mContext.getResources().getString(R.string.comm_str_comment_count, info.getItem().getComment_count());
        String commentPercent = mContext.getResources().getString(R.string.comm_str_good_comment_percent, info.getItem().getComment_score());
        binding.layoutDetComment.detailCommodityCommentTv.setLeftTvDrawableLeft(this.getResources().getDrawable(R.drawable.ic_red_left_tag));
        binding.layoutDetComment.detailCommodityCommentTv.getLeftTextView().setCompoundDrawablePadding(10); //调整间距
        binding.layoutDetComment.detailCommodityCommentTv.setLeftString(Spans.builder().text(commentNum,
                14, ContextCompat.getColor(mContext, R.color.black)).typeface(Typeface.DEFAULT_BOLD).build());
        binding.layoutDetComment.detailCommodityCommentTv.setRightTvDrawableRight(this.getResources().getDrawable(R.drawable.ic_arrow_right));
        binding.layoutDetComment.detailCommodityCommentTv.getRightTextView().setCompoundDrawablePadding(10); //调整间距
        binding.layoutDetComment.detailCommodityCommentTv.setRightString(Spans.builder().text(commentPercent,
                13, ContextCompat.getColor(mContext, R.color.color_333333))
                .newSpanPart(3, commentPercent.length(), new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_FD644B))).build());
        binding.layoutDetComment.detailCommodityCommentTv.setBottomDividerLineVisibility(View.GONE); //去线

        List<String> commentVals = new ArrayList<>();
        commentVals.add("运行稳定675");
        commentVals.add("质量上乘98");
        commentVals.add("系统良好78");
        commentVals.add("系统充足74");
        commentVals.add("简洁大方59");
        commentVals.add("做工一流51");

        binding.layoutDetComment.detailCommodityCommentTag.setAdapter(new TagAdapter<String>(commentVals) {
            @Override
            public View getView(CommFlowLayout parent, int position, String s) {
                TextView tv = (TextView) mLayoutInflater.inflate(R.layout.comment_item_tag,
                        binding.layoutDetComment.detailCommodityCommentTag, false);
                tv.setText(s);
                return tv;
            }
        });
        binding.layoutDetComment.detailCommodityCommentTag.relayoutToCompress();
        binding.layoutDetComment.detailCommodityCommentTag.setVisibility(View.GONE);

//        List<CommentData> commentDatas = new ArrayList<>();
//        commentDatas.add(new CommentData("杰***3",
//                "双十一买的，百分百正品，自己摸索下载安装了wps for mac破解版，很好用。就是一个问题键盘的按键挺送的，不知道大家是不是都一样？" +
//                        "自己另外买了蓝牙键盘和鼠标。11号拍的14号到的，在双十一高峰期，物流表现赞今天一天都在使用，电池电量还有百分之六十五，电池很赞！",
//                "http://1251412368.vod2.myqcloud.com/vodtransgzp1251412368/9031868223370466926/coverBySnapshot/1508147047_1360392293.100_0.jpg",
//                "100", "银色，2017新i5升级版"));
//        commentDatas.add(new CommentData("J***w",
//                "双十一买的，百分百正品，自己摸索下载安装了wps for mac破解版，很好用。就是一个问题键盘的按键挺送的，不知道大家是不是都一样？" +
//                        "自己另外买了蓝牙键盘和鼠标。11号拍的14号到的，在双十一高峰期，物流表现赞今天一天都在使用，电池电量还有百分之六十五，电池很赞！",
//                "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3860616424,1789830124&fm=80&w=179&h=119&img.PNG",
//                "80","银色，2017新i5处理器升级版"));
        DetailCommentAdapter commentAdapter = new DetailCommentAdapter(R.layout.comment_item, info.getComment_list());
        binding.layoutDetComment.detailCommodityCommentRec.addItemDecoration(new DetailDividerItemLineDecoration(mContext));
        binding.layoutDetComment.detailCommodityCommentRec.setAdapter(commentAdapter);
        binding.layoutDetComment.detailCommodityCommentRec.setNestedScrollingEnabled(false);
        binding.layoutDetComment.detailCommodityCommentRec.setHasFixedSize(true);
        binding.layoutDetComment.detailCommodityCommentRec.setLayoutManager(new LinearLayoutManager(mContext));

        //问答
        String qa = "问答";
        binding.layoutDetQuestionsAnswers.detailCommodityQaTv.setLeftTvDrawableLeft(this.getResources().getDrawable(R.drawable.ic_red_left_tag));
        binding.layoutDetQuestionsAnswers.detailCommodityQaTv.getLeftTextView().setCompoundDrawablePadding(10); //调整间距
        binding.layoutDetQuestionsAnswers.detailCommodityQaTv.setLeftString(Spans.builder().text(qa,
                14, ContextCompat.getColor(mContext, R.color.black)).typeface(Typeface.DEFAULT_BOLD).build());
        binding.layoutDetQuestionsAnswers.detailCommodityQaTv.setRightTvDrawableRight(this.getResources().getDrawable(R.drawable.ic_arrow_right));
        binding.layoutDetQuestionsAnswers.detailCommodityQaTv.getRightTextView().setCompoundDrawablePadding(10); //调整间距
        binding.layoutDetQuestionsAnswers.detailCommodityQaTv.setBottomDividerLineVisibility(View.GONE); //去线

        if(info.getConsult_list() == null || info.getConsult_list().size() == 0) {
            binding.layoutDetQuestionsAnswers.detailCommodityQaRoot.setVisibility(View.GONE);
        }

        if(info.getConsult_list() != null && info.getConsult_list().size() > 0) {
            String a = info.getConsult_list().get(0).getContent();
            binding.layoutDetQuestionsAnswers.detailCommodityQa1Tv.setLeftTvDrawableLeft(this.getResources().getDrawable(R.mipmap.ic_qa));
            binding.layoutDetQuestionsAnswers.detailCommodityQa1Tv.getLeftTextView().setCompoundDrawablePadding(12); //调整间距
            binding.layoutDetQuestionsAnswers.detailCommodityQa1Tv.setLeftString(Spans.builder().text(a,
                    14, ContextCompat.getColor(mContext, R.color.color_282828)).build());
            String a_1 = info.getConsult_list().get(0).getReply_count() + "个回答";
            binding.layoutDetQuestionsAnswers.detailCommodityQa1Tv.setLeftBottomString(Spans.builder().text(a_1,
                    14, ContextCompat.getColor(mContext, R.color.color_9b9b9b)).build());
            binding.layoutDetQuestionsAnswers.detailCommodityQa1Tv.getLeftBottomTextView().setPadding(DimensionsUtil.DIPToPX(25),0,0,0);
            binding.layoutDetQuestionsAnswers.detailCommodityQa1Tv.setBottomDividerLineVisibility(View.GONE); //去线
        }

        if(info.getConsult_list() != null && info.getConsult_list().size() > 1) {
            String b = info.getConsult_list().get(1).getContent();
            binding.layoutDetQuestionsAnswers.detailCommodityQa2Tv.setLeftTvDrawableLeft(this.getResources().getDrawable(R.mipmap.ic_qa));
            binding.layoutDetQuestionsAnswers.detailCommodityQa2Tv.getLeftTextView().setCompoundDrawablePadding(12); //调整间距
            binding.layoutDetQuestionsAnswers.detailCommodityQa2Tv.setLeftString(Spans.builder().text(b,
                    14, ContextCompat.getColor(mContext, R.color.color_282828)).build());
            String b_1 = info.getConsult_list().get(1).getReply_count() + "个回答";
            binding.layoutDetQuestionsAnswers.detailCommodityQa2Tv.setLeftBottomString(Spans.builder().text(b_1,
                    14, ContextCompat.getColor(mContext, R.color.color_9b9b9b)).build());
            binding.layoutDetQuestionsAnswers.detailCommodityQa2Tv.getLeftBottomTextView().setPadding(DimensionsUtil.DIPToPX(25),0,0,0);
            binding.layoutDetQuestionsAnswers.detailCommodityQa2Tv.setBottomDividerLineVisibility(View.GONE); //去线
        }

        String qCount = getResources().getString(R.string.comm_str_qa_check_all, info.getItem().getConsult_count());
        ((TextView)findViewById(getView(), R.id.common_center_btn)).setText(qCount);

        //推荐
        isRecommendState = true;
        LinearLayoutManager detailLinearLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);
        binding.layoutDetRecommend.detailCommodityRecommendRec.setLayoutManager(detailLinearLayoutManager);
        binding.layoutDetRecommend.detailCommodityRecommendRec.setNestedScrollingEnabled(false);
        binding.layoutDetRecommend.detailCommodityRecommendRec.setHasFixedSize(true);
        List<DetailRecommendInfo> pageDetailRecommendInfos = new ArrayList<>(info.getGoods_recommend_list());
        //总记录数
        int rows = pageDetailRecommendInfos.size();
        //每页显示的记录数
        int pageCount = 6;
        //页数
        int pageSize = (rows - 1) / pageCount + 1;
        if(pageSize == 0) pageSize = 1;
        List<List<DetailRecommendInfo>> pages = ConvertUtils.averageAssign(pageDetailRecommendInfos, pageSize);
        DetailRecommendParentAdapter detailRecommendAdapter = new DetailRecommendParentAdapter(R.layout.layout_comm_recyclerview_auto_size, pages);
        binding.layoutDetRecommend.detailCommodityRecommendRec.setAdapter(detailRecommendAdapter);
        final DetailRecommendCircleNavigator circleNavigator = new DetailRecommendCircleNavigator(mContext);
        circleNavigator.setFollowTouch(false);
        circleNavigator.setCircleCount(pageSize);
        new GravityPagerSnapHelper(Gravity.START, true, position -> {
            circleNavigator.onPageSelected(position);
        }).attachToRecyclerView(binding.layoutDetRecommend.detailCommodityRecommendRec);
        binding.layoutDetRecommend.detailCommodityRecommendIndicator.setNavigator(circleNavigator);
        binding.layoutDetRecommend.detailCommodityRecommendMoreTv.setText(R.string.comm_str_recommend_more);

        //排行版
        binding.layoutDetRecommend.detailCommodityRecommendRank.addItemDecoration(new DetailDividerItemMarginDecoration(mContext, DimensionsUtil.DIPToPX(8), true));
        binding.layoutDetRecommend.detailCommodityRecommendRank.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.layoutDetRecommend.detailCommodityRecommendRank.setHasFixedSize(true);
        binding.layoutDetRecommend.detailCommodityRecommendRank.setNestedScrollingEnabled(false);
        binding.layoutDetRecommend.detailCommodityRecommendRank.setAdapter(new DetailRecommendAdapter(R.layout.recommend_item, info.getGoods_sale_list(), true));

        TextView leftTag = binding.layoutDetRecommend.getRoot().findViewById(R.id.detail_commodity_recommend_left_tag);
        TextView rightTag = binding.layoutDetRecommend.getRoot().findViewById(R.id.detail_commodity_recommend_right_tag);
        leftTag.setOnClickListener(v -> {
            if(isRecommendState)return;
            isRecommendState = true;
            leftTag.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff593e));
            rightTag.setTextColor(ContextCompat.getColor(mContext, R.color.color_bababa));
            binding.layoutDetRecommend.detailCommodityRecommendMoreTv.setText(R.string.comm_str_recommend_more);
            binding.layoutDetRecommend.detailCommodityRecommendIndicator.setVisibility(View.VISIBLE);
            binding.layoutDetRecommend.detailCommodityRecommendRec.setVisibility(View.VISIBLE);
            binding.layoutDetRecommend.detailCommodityRecommendRank.setVisibility(View.GONE);
        });
        rightTag.setOnClickListener(v -> {
            if(!isRecommendState)return;
            isRecommendState = false;
            leftTag.setTextColor(ContextCompat.getColor(mContext, R.color.color_bababa));
            rightTag.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff593e));
            binding.layoutDetRecommend.detailCommodityRecommendMoreTv.setText(R.string.comm_str_recommend_rank_more);
            binding.layoutDetRecommend.detailCommodityRecommendIndicator.setVisibility(View.GONE);
            binding.layoutDetRecommend.detailCommodityRecommendRec.setVisibility(View.GONE);
            binding.layoutDetRecommend.detailCommodityRecommendRank.setVisibility(View.VISIBLE);
        });
    }

    private void loadCommodityData(String goodsId) {
//        fiftyShadesOf = FiftyShadesOf.with(mContext).on(binding.layoutPricePromotion.getRoot(), binding.layoutDetTitle.getRoot(),
//                binding.layoutDetChoice.getRoot(), binding.layoutDetShippingAddress.getRoot()).fadein(true).start();
//        fiftyShadesOf.stop();
        mDetailCommdityViewModel = new DetailCommdityViewModel();
        addRunStatusChangeCallBack(mDetailCommdityViewModel);
        mDetailCommdityViewModel.getDetailCommdityInfo(goodsId);
    }

    private void updateBannerIndicator(int position, int count) {
        binding.detailBannerIndicatorTv.setText(Spans.builder().text(String.valueOf(position + 1),16, Color.WHITE)
                .text("/", 12, Color.WHITE)
                .text(String.valueOf(count), 12, Color.WHITE).build());
    }

    private void updateGoodsPrice(String price, String mPrice) {
        String curPrice = price;
        String marketPrice = mPrice;
        int color = Color.WHITE;
        if(!TextUtil.isEmpty(marketPrice)) {
            AppCompat.setBackground(binding.layoutPricePromotion.detailLayoutPriceRoot,
                    ContextCompat.getDrawable(getContext(), R.drawable.bg_gradient_bar_detail_price));
            binding.layoutPricePromotion.detailLayoutPromotionPrice.setVisibility(View.VISIBLE);
            binding.layoutPricePromotion.detailLayoutPromotionTimeRoot.setVisibility(View.VISIBLE);
            long time = (long)30 * 60 * 1000;
            binding.layoutPricePromotion.detailLayoutPromotionTime.start(time); //倒计时
        } else {
            color = Color.RED;
            binding.layoutPricePromotion.detailLayoutPriceRoot.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.layoutPricePromotion.detailLayoutPromotionPrice.setVisibility(View.GONE);
            binding.layoutPricePromotion.detailLayoutPromotionTimeRoot.setVisibility(View.GONE);
        }
        binding.layoutPricePromotion.detailLayoutPrice.setText(Spans.builder().text("¥", 12, color)
                .text(curPrice, 23, color).typeface(Typeface.DEFAULT_BOLD).build());

        binding.layoutPricePromotion.detailLayoutPromotionPrice.setText(Spans.builder().text("市场价：", 12, color)
                .text("¥" + marketPrice, 12, color).deleteLine().build());
    }

    private void updateGoodsTitle(String goodsTitle) {
        String title = goodsTitle;
        boolean isNeedTup = false;
        if(isNeedTup) {
            RoundBackgroundColorSpan roundBackgroundColorSpan = new RoundBackgroundColorSpan(ContextCompat.getColor(mContext, R.color.color_FD644B),
                    ContextCompat.getColor(mContext, R.color.white), 23, DimensionsUtil.sp2px(mContext, 13)); //圆角
            AbsoluteSizeSpan spanSize = new AbsoluteSizeSpan(DimensionsUtil.sp2px(mContext, 11)); //文字大小
//        String title = "自营 联想(Lenovo)拯救者R720 15.6英寸大屏游戏笔记本电脑(i5-7300HQ 8G 1T+128G SSD GTX1050Ti 4G IPS 黑金)";
            binding.layoutDetTitle.detailCommodityTitle.setRightString(Spans.builder().text(title, 14, Color.BLACK).typeface(Typeface.DEFAULT_BOLD)
                    .newSpanPart(0,2, roundBackgroundColorSpan).newSpanPart(0,2, spanSize).build());
        } else {
            binding.layoutDetTitle.detailCommodityTitle.setLeftString(Spans.builder().text(title, 14, Color.BLACK).typeface(Typeface.DEFAULT_BOLD).build());
        }
        binding.layoutDetTitle.detailCommodityTitle.setBottomDividerLineVisibility(View.GONE); //去线
    }

    private void updateGoodsSelectedSpc(String goodsName) {
        String choiceVal = goodsName + "，1件，可选服务";
        binding.layoutDetChoice.detailCommodityChoiceStateTv.setCenterString(Spans.builder().text(choiceVal,14, Color.BLACK).typeface(Typeface.DEFAULT_BOLD).build());
        binding.layoutDetChoice.detailCommodityChoiceStateTv.setBottomDividerLineVisibility(View.GONE); //去线
        binding.layoutDetChoice.getRoot().setOnClickListener(v -> {
            if(detailInfoManager.getDetailInfo() == null || detailInfoManager.getDetailInfo().getSpec_goods_list().size() == 0) return;
            DetBottomSheetSpecFragment detBottomSheetSpecFragment = new DetBottomSheetSpecFragment();
            detBottomSheetSpecFragment.setData(false, detailInfoManager.getGoodsId(),
                    detailInfoManager.getDetailInfo().getSpec_goods_list(), detailInfoManager.getDetailInfo().getSpec_select_info());
            detBottomSheetSpecFragment.show(getChildFragmentManager(), "Dialog");
        });
    }

    @Override
    protected void responseEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_GOODS_SPC_UPDATE.equals(event.msgType)) { //规格更新
            Bundle bundle = event.bundle;
            if(bundle != null) {
                DetailSpecSelectedInfo detailSpecSelectedInfo = bundle.getParcelable(CommKeyUtil.EXTRA_KEY);
                if(detailSpecSelectedInfo != null) {
                    detailInfoManager.updateSpecSelected(detailSpecSelectedInfo);
                    updateGoodsPrice(detailInfoManager.getDetailInfo().getSpec_select_info().getPrice(),
                            detailInfoManager.getDetailInfo().getItem().getMarket_price());
                    updateGoodsTitle(detailInfoManager.getDetailInfo().getSpec_select_info().getGoods_name());
                    updateGoodsSelectedSpc(detailInfoManager.getDetailInfo().getSpec_select_info().getGoods_name());
                }
            }
        }
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
        if(mActivityDetails != null) mActivityDetails.updateHeader(true, verticalOffset, appBarLayout.getTotalScrollRange());
    }

    @Override
    public void onLeftRefreshing() {

    }

    @Override
    public void onRightRefreshing() {
        binding.layoutDetHotRec.detailCommodityHotRecRefresh.postDelayed(()->{
            binding.layoutDetHotRec.detailCommodityHotRecRefresh.onRefreshComplete();
        }, 2000);
    }

    @Override
    public void onInterceptTouchEvent(boolean isRelease) {
        if(mActivityDetails != null) {
            mActivityDetails.interceptScroll(isRelease);
        }
    }

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if(status == SlideDetailsLayout.Status.CLOSE) { //当前为商品详情页
            binding.detailSlide.smoothClose(true);
            binding.detailPullTagIv.setImageResource(R.mipmap.ic_arrow_up_full);
            binding.detailPullTipTv.setText(R.string.detail_pull_up_tip);
            mActivityDetails.playHeadTitleAnimat(true);
            mActivityDetails.interceptScroll(true);
        } else if(status == SlideDetailsLayout.Status.OPEN) { //当前为图文详情页
            binding.detailSlide.smoothOpen(true);
            binding.detailPullTagIv.setImageResource(R.mipmap.ic_arrow_down_full);
            binding.detailPullTipTv.setText(R.string.detail_pull_down_tip);
            mActivityDetails.playHeadTitleAnimat(false);
            mActivityDetails.interceptScroll(false);
        }
    }
}

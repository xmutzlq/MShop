package google.architecture.personal;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.king.android.res.config.ARouterPath;
import com.king.android.sharesdk.AppShareMain;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.statusbar.StatusbarUtils;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.PersonalViewModel;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.data.PersonalContentData;
import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.personal.adapter.PersonalContentAdapter;
import google.architecture.personal.databinding.FragmentShoppingPersonal2Binding;
import google.architecture.personal.holder.PersonalContentItemDecoration;
import google.architecture.personal.holder.PersonalContentSection;
import google.architecture.personal.login.ActivityLogin;

/**
 * @author lq.zeng
 * @date 2018/7/23
 */
//@Route(path = ARouterPath.PersonalShoppingFgt)
public class FragmentShoppingPersonal2 extends BaseFragment<FragmentShoppingPersonal2Binding> implements AppShareMain.IShareResponse {

    private int height = 0;// 滑动开始变色的高,真实项目中此高度是由头部view高度决定
    private int overallXScroll = 0;

    private PersonalContentAdapter personalContentAdapter;
    private List<PersonalContentSection> personalContentSections;
    private PersonalViewModel personalViewModel;

    public static FragmentShoppingPersonal2 newInstance() {
        return new FragmentShoppingPersonal2();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        getToolbarHelper().getAppBarLayout().setVisibility(View.GONE);
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_shopping_personal2;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        height = getResources().getDimensionPixelSize(com.king.android.res.R.dimen.size_200dp) / 2;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.height = StatusbarUtils.getStatusBarHeight(mContext);
        binding.personalStatusBar.setLayoutParams(lp);
        binding.personalRefreshLayout.setEnableOverScrollBounce(false);
        binding.personalRefreshLayout.setOnMultiPurposeListener(listener);

        personalContentSections = new ArrayList<>();
        personalContentAdapter = new PersonalContentAdapter(R.layout.layout_content_item, R.layout.layout_content_title, personalContentSections);
        personalContentAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PersonalContentSection mySection = personalContentSections.get(position);
            if(PersonalContentSection.SECTION_TYPE_ITEM == mySection.getType()) {
                if(!Account.get().isLogin()){
                    ToastUtils.showShortToast("请先登录");
                    return;
                }
                if(mySection.t.getName().equals("我的收藏")) {
                    ARouter.getInstance().build(ARouterPath.PersonalFavoritesAty).navigation();
                } else if(mySection.t.getName().equals("地址管理")) {
                    ARouter.getInstance().build(ARouterPath.OrderAddressChoiceActy)
                            .withString(CommKeyUtil.EXTRA_KEY, Account.get().getUserInfo().address_id).navigation();
                } else if(mySection.t.getName().equals("浏览记录")) {
                    ARouter.getInstance().build(ARouterPath.PersonalFootprintsAty).navigation();
                } else if(mySection.t.getName().equals("用户反馈")) {
                    ARouter.getInstance().build(ARouterPath.PersonalFeedBackAty).navigation();
                }
            }
        });
        personalContentAdapter.bindToRecyclerView(binding.personalContainber);
        View headView = LayoutInflater.from(mContext).inflate(R.layout.layout_content_head, null);
        headView.setOnClickListener(v -> {
            if(!Account.get().isLogin()) {
                ARouter.getInstance()
                        .build(ARouterPath.PersonalLoginAty)
                        .withInt(ActivityLogin.TYPE_FLAG, ActivityLogin.LOGIN_FLAG)
                        .navigation(getActivity());
            } else {
                ARouter.getInstance()
                        .build(ARouterPath.PersonalInfoAty)
                        .navigation(getActivity());
            }
        });
        personalContentAdapter.addHeaderView(headView);

        QMUIFrameLayout qmuiFrameLayout = personalContentAdapter.getHeaderLayout().findViewById(R.id.shadow_layout);
        qmuiFrameLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 6),
                QMUIDisplayHelper.dp2px(mContext, 8), 0.4f);
        //拿到我的订单各个ItemView
        View waitingPayView = personalContentAdapter.getHeaderLayout().findViewById(R.id.layout_waiting_for_pay);
        View waitingReceiptView = personalContentAdapter.getHeaderLayout().findViewById(R.id.layout_waiting_for_receipt);
        View waitingCommentView = personalContentAdapter.getHeaderLayout().findViewById(R.id.layout_waiting_for_comment);
        View waitingServiceView = personalContentAdapter.getHeaderLayout().findViewById(R.id.layout_service_for_other);

        personalContentAdapter.getHeaderLayout().findViewById(R.id.tv_all_order_label).setOnClickListener(v ->
                ARouter.getInstance().build(ARouterPath.PersonalShoppingOrderAty).withInt(CommKeyUtil.EXTRA_KEY, 0).navigation()); //全部订单
        waitingPayView.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.PersonalShoppingOrderAty)
                .withInt(CommKeyUtil.EXTRA_KEY, 1).navigation()); //待付款
        waitingReceiptView.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.PersonalShoppingOrderAty)
                .withInt(CommKeyUtil.EXTRA_KEY, 2).navigation()); //待收货
        waitingCommentView.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.PersonalShoppingCommentAty)
                .withInt(CommKeyUtil.EXTRA_KEY, 0).navigation()); //待评价
        waitingServiceView.setOnClickListener(v -> {});

        setOrderItem(waitingPayView.findViewById(R.id.personal_content_item_iv), waitingPayView.findViewById(R.id.personal_content_item_tv),
                R.mipmap.ic_need_pay, getResources().getString(R.string.personal_order_1));
        setOrderItem(waitingReceiptView.findViewById(R.id.personal_content_item_iv), waitingReceiptView.findViewById(R.id.personal_content_item_tv),
                R.mipmap.ic_need_take, getResources().getString(R.string.personal_order_2));
        setOrderItem(waitingCommentView.findViewById(R.id.personal_content_item_iv), waitingCommentView.findViewById(R.id.personal_content_item_tv),
                R.mipmap.ic_need_feed, getResources().getString(R.string.personal_order_3));
        setOrderItem(waitingServiceView.findViewById(R.id.personal_content_item_iv), waitingServiceView.findViewById(R.id.personal_content_item_tv),
                R.mipmap.ic_pay_back, getResources().getString(R.string.personal_order_4));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int dataPos) {
                int type = personalContentAdapter.getData().get(dataPos).getType();
                switch (type) {
                    case PersonalContentSection.SECTION_TYPE_ITEM:
                        return 4;
                    default:
                    case PersonalContentSection.SECTION_TYPE_TITLE:
                        return 1;
                }
            }
        });
        binding.personalContainber.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                overallXScroll = overallXScroll + dy;
                if (overallXScroll <= 0) {   //设置标题的背景颜色
                    binding.userHeadHeadIv.setVisibility(View.GONE);
                    binding.userHeadHeadTv.setVisibility(View.GONE);
                    binding.personalHeadRl.setBackgroundColor(Color.argb(0, 0, 0, 0));
                } else if (overallXScroll > 0 && overallXScroll <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) overallXScroll / height;
                    float alpha = (255 * scale);
                    binding.userHeadHeadIv.setVisibility(View.GONE);
                    binding.userHeadHeadTv.setVisibility(View.GONE);
                    binding.personalHeadRl.setBackgroundColor(Color.argb((int) alpha, 255, 200, 55));
                } else {
                    binding.userHeadHeadIv.setVisibility(View.VISIBLE);
                    binding.userHeadHeadTv.setVisibility(View.VISIBLE);
                    binding.personalHeadRl.setBackgroundColor(Color.argb(255, 255, 200, 55));
                }
            }
        });
        binding.personalContainber.addItemDecoration(new PersonalContentItemDecoration());
        binding.personalContainber.setLayoutManager(gridLayoutManager);
        binding.personalContainber.setAdapter(personalContentAdapter);
        binding.personalContainber.setHasFixedSize(true);

        //设置按钮
        binding.personalSetting.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.PersonalSettingAty).navigation();
        });
    }

    private void setOrderItem(ImageView iv, TextView tv, int imgRes, String titleStr) {
        ImageLoader.get().load(iv, imgRes, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                iv.setImageDrawable(resource);
                return false;
            }
        });
        tv.setText(titleStr);
    }

    @Override
    public void onFragmentFirstVisible() {
        personalContentSections.clear();
        List<PersonalContentData> personalContentDatas = new ArrayList<>();

        //我的服务
        PersonalContentData personalContentData2 = new PersonalContentData();
        personalContentData2.setName("我的服务");
        List<PersonalContentData> personalContentItems2 = new ArrayList<>();
        personalContentItems2.add(new PersonalContentData(String.valueOf(R.mipmap.ic_collection_fill), "我的收藏"));
        personalContentItems2.add(new PersonalContentData(String.valueOf(R.mipmap.ic_foot_print), "浏览记录"));
        //personalContentItems2.add(new PersonalContentData(String.valueOf(R.mipmap.ic_ticket), "优惠券"));
        personalContentItems2.add(new PersonalContentData(String.valueOf(R.mipmap.ic_invite_people), "新人邀请"));
        personalContentItems2.add(new PersonalContentData(String.valueOf(R.mipmap.ic_u_money), "我的资产"));
        personalContentItems2.add(new PersonalContentData(String.valueOf(R.mipmap.ic_u_location), "地址管理"));
        personalContentItems2.add(new PersonalContentData(String.valueOf(R.mipmap.ic_feed_back), "用户反馈"));
        personalContentItems2.add(new PersonalContentData(String.valueOf(R.mipmap.ic_help_center), "帮助中心"));
        personalContentData2.setItems(personalContentItems2);
        personalContentDatas.add(personalContentData2);
//        PersonalContentSection personalContentSectionTitle2 = new PersonalContentSection(true, personalContentDatas.get(0));
//        personalContentSectionTitle2.setMore(true);
//        personalContentSectionTitle2.setType(PersonalContentSection.SECTION_TYPE_TITLE);
//        personalContentSections.add(personalContentSectionTitle2);

        for (PersonalContentData data : personalContentDatas.get(0).getItems()) {
            PersonalContentSection personalContentSectionItem = new PersonalContentSection(data);
            personalContentSectionItem.setType(PersonalContentSection.SECTION_TYPE_ITEM);
            personalContentSections.add(personalContentSectionItem);
        }

        if(personalContentAdapter != null) personalContentAdapter.notifyDataSetChanged();
        binding.personalContainber.post(()->{
            binding.personalContainber.scrollToPosition(0);
        });

        personalViewModel = new PersonalViewModel();
        addRunStatusChangeCallBack(personalViewModel);
        loadData();
    }

    private void loadData() {
        if(personalViewModel != null) personalViewModel.getUserInfo();
    }

    @Override
    protected void onDataResult(Object o) {
        S_UserInfo s_userInfo = (S_UserInfo) o;
        Account.get().setUserInfoToLocal(s_userInfo);
        updatePersonal(s_userInfo);
    }

    @Override
    public void onUserLoginStateChange(boolean isLogin) {
        super.onUserLoginStateChange(isLogin);
        if(isLogin) {
            S_UserInfo userInfo = Account.get().getUserInfo();
            if(userInfo != null) {
                updatePersonal(userInfo);
            }
        } else {
            updatePersonal(null);
        }
    }

    @Override
    public void onMessageEvent(CommEvent event) {
        super.onMessageEvent(event);
        if(CommEvent.MSG_TYPE_UPDATE_USER_INFO.equals(event.msgType)) {
            updatePersonal(Account.get().getUserInfo());
        }
    }

    private void updatePersonal(S_UserInfo userInfo) {
        TextView userNameTv = personalContentAdapter.getHeaderLayout().findViewById(R.id.user_head_tv);
        ImageView userHeadIv = personalContentAdapter.getHeaderLayout().findViewById(R.id.user_head_iv);
        if(Account.get().isLogin()) {
            if(userInfo != null) {
                if(TextUtils.isEmpty(userInfo.username)) {
                    userNameTv.setText(userInfo.nickname);
                } else {
                    userNameTv.setText(userInfo.username);
                }
                if(TextUtils.isEmpty(userInfo.head_pic)) {
                    userHeadIv.setImageResource(R.mipmap.ic_u_head);
                } else {
                    ImageLoader.get().loadCropCircleHeader(userHeadIv, userInfo.head_pic,
                            DimensionsUtil.dip2px(mContext, 60), DimensionsUtil.dip2px(mContext, 60));
                }
            }
        } else {
            userNameTv.setText(R.string.login_register);
            userHeadIv.setImageResource(R.mipmap.ic_u_head);
        }
    }

    SimpleMultiPurposeListener listener = new SimpleMultiPurposeListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishLoadMore(0);
        }
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(0);
            loadData();
        }
        @Override
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

        }
    };

    @Override
    public void onComplete(int actionType, Platform platform) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onCancel() {

    }
}

package google.architecture.home.substance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.util.DebouncingOnClickListener;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import net.soulwolf.widget.ratiolayout.RatioDatumMode;
import net.soulwolf.widget.ratiolayout.widget.RatioFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.vcontent.BaseDelegateAdapter;
import google.architecture.common.vcontent.BaseViewHolder;
import google.architecture.common.widget.SpaceViewItemLine;
import google.architecture.common.widget.banner.CirclePageIndicator;
import google.architecture.common.widget.banner.recycle.AdvertImagePagerAdapter;
import google.architecture.common.widget.banner.recycle.LocalBannerAdapter;
import google.architecture.common.widget.banner.recycle.RecycleAutoScrollViewPager;
import google.architecture.coremodel.data.HomeData;
import google.architecture.coremodel.data.HomeItemsInfo;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.home.R;
import google.architecture.home.adapter.NewestGoodsAdapter;

/**
 * @author lq.zeng
 * @date 2018/5/2
 */

public class PageAdapterImp implements PageAdapter {

    private Context mContext;
    private boolean isBannerBind;
    private boolean isNewestGoodsBind;

    public PageAdapterImp(Context context) {
        mContext = context;
    }

    @Override
    public void reSetBinded() {
        isBannerBind = false;
        isNewestGoodsBind = false;
    }

    @Override
    public BaseDelegateAdapter initTop() {
        return new BaseDelegateAdapter(mContext, new StickyLayoutHelper(), R.layout.home_item_top, 1, PageConstans.viewType.typeTop) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initTitle(final String title, final String titleCenter, final String rightTip, final int drawableRightRes,
                                         final int titleBgRes, final boolean isNeedBottomLine,
                                         final int clickType, final IActionTitleRightLabelClick listener) {
        return new BaseDelegateAdapter(mContext, new LinearLayoutHelper(), R.layout.home_item_title, 1, PageConstans.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                //左边
                if(!TextUtils.isEmpty(title)) {
                    holder.setText(R.id.tv_title, title);
                    holder.setVisible(R.id.tv_title, true);
                } else {
                    holder.setVisible(R.id.tv_title, false);
                }

                //中间
                if(!TextUtils.isEmpty(titleCenter)) {
                    holder.setText(R.id.tv_center_title, titleCenter);
                    holder.setVisible(R.id.tv_center_title, true);
                } else {
                    holder.setVisible(R.id.tv_center_title, false);
                }

                //右边
                TextView rightTv = holder.getView(R.id.tv_title_right_label);
                if(!TextUtils.isEmpty(rightTip)) {
                    if(drawableRightRes > 0) {
                        rightTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRightRes, 0);
                    } else {
                        rightTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                    rightTv.setText(rightTip);
                    rightTv.setVisibility(View.VISIBLE);
                } else {
                    rightTv.setVisibility(View.GONE);
                }
                rightTv.setOnClickListener(new DebouncingOnClickListener() {
                    @Override
                    public void doClick(View v) {
                        if(listener != null) {
                            listener.onActionTitleRightLabelClick(v, clickType);
                        }
                    }
                });

                if(titleBgRes > 0) {
                    holder.setBackgroundColor(R.id.bg_title, ContextCompat.getColor(mContext, titleBgRes));
                } else {
                    holder.setBackgroundColor(R.id.bg_title, ContextCompat.getColor(mContext, R.color.white));
                }

                //底线
                if(isNeedBottomLine) {
                    holder.setVisible(R.id.bottom_line, true);
                } else {
                    holder.setVisible(R.id.bottom_line, false);
                }
            }
        };
    }

    @Override
    public BaseDelegateAdapter initBanner(HomeData.HomeInfo banner) {
        //banner
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setLayoutViewBindListener((layoutView, baseLayoutHelper) -> {
            if(layoutView != null) {
                isBannerBind = true;
            }
        });
        linearLayoutHelper.setLayoutViewUnBindListener((layoutView, baseLayoutHelper) -> {
            if(layoutView == null) {
                isBannerBind = false;
            }
        });
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_banner, 1, PageConstans.viewType.typeBanner) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 绑定数据
                if(!isBannerBind) {
                    View rootView = holder.getView(R.id.home_banner_root);
                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    //lp.height = banner.getStyle().getPageHeight() * 2;
                    lp.height = ScreenUtils.getScreenHeight()/2+50;
                    rootView.setLayoutParams(lp);
                    RecycleAutoScrollViewPager mBanner = holder.getView(R.id.adv_image);
                    /*mBanner.setAdapter(new AdvertImagePagerAdapter(mContext, new ArrayList<>(banner.getItems())).setBannerClickListener(position1 -> {
                        HomeGoHelper.goPage(mContext, banner.getItems().get(position1).getAction());
                    }));*/
                    ArrayList<Integer> imgList = new ArrayList<>();
                    imgList.add(R.drawable.top_banner);
                    mBanner.setAdapter(new LocalBannerAdapter(mContext,imgList));
                    CirclePageIndicator indicator = holder.getView(R.id.adv_circlePageIndicator);
                    indicator.setViewPager(mBanner);
                    mBanner.setInterval(banner.getStyle().getAutoScroll());
                    mBanner.startAutoScroll();
                    mBanner.setCurrentItem(banner.getItems().size() * 500);
                    mBanner.setNoScroll(banner.getItems().size() == 1);
                    mBanner.requestFocus();
                    indicator.setVisibility(banner.getItems().size() == 1 ? View.GONE : View.VISIBLE);
                }
            }
        };
    }

    @Override
    public BaseDelegateAdapter initBrand() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext,linearLayoutHelper, R.layout.brand_layout,1,PageConstans.viewType.typeBrand){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initSecondBrand() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.temp_second_brand, 1, PageConstans.viewType.typeBrand){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initRowBrands() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.temp_row_shoes, 1, PageConstans.viewType.typeBrand){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initLongPic() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.temp_long_pic, 1, PageConstans.viewType.typeBrand){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
    }

    /*@Override
    public BaseDelegateAdapter initHomeAd(HomeData.HomeInfo adImages) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 0);
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_ads, adImages.getItems().size(), PageConstans.viewType.typeNews) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                RatioFrameLayout rf = holder.getView(R.id.home_ads_rf_grid);
                if(adImages.getItems().get(position).getStyle() != null
                        && adImages.getItems().get(position).getStyle().getAspectRatio() > 0) {
                    rf.setRatio(RatioDatumMode.DATUM_WIDTH, adImages.getItems().get(position).getStyle().getAspectRatio(), 1);
                }
                ImageLoader.get().load(holder.getView(R.id.home_ads_iv), adImages.getItems().get(position).getImgUrl());
                holder.getItemView().setOnClickListener(v -> {
                    HomeGoHelper.goPage(mContext, adImages.getItems().get(position).getAction());
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initRecommend(HomeData.HomeInfo recommends) {
        final Random random = new Random();
        final List<HomeItemsInfo> tempRecommendData = new ArrayList<>();
        for (int i = 0; i < recommends.getStyle().getNumberOfColumns(); i ++){
            tempRecommendData.add(recommends.getItems().get(random.nextInt(recommends.getItems().size())));
        }
        recommends.cacheItems = new ArrayList<>(tempRecommendData);
        final float aspectRatio = recommends.getItems().get(0).getStyle().getAspectRatio();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(recommends.getStyle().getNumberOfColumns());
        return new BaseDelegateAdapter(mContext, gridLayoutHelper, R.layout.home_item_gride, recommends.cacheItems.size(), PageConstans.viewType.typeRecommend) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                RatioFrameLayout rf = holder.getView(R.id.rf_grid);
                rf.setRatio(RatioDatumMode.DATUM_WIDTH, aspectRatio, 1);
                HomeItemsInfo model = recommends.cacheItems.get(position);
                holder.setText(R.id.tv_new_seed_title, model.getTitle());
                holder.getView(R.id.tv_new_seed_title).requestLayout();
                ImageLoader.get().load(holder.getView(R.id.iv_new_seed_ic), model.getImgUrl());
                holder.getItemView().setOnClickListener(v -> {
                    HomeGoHelper.goPage(mContext, model.getAction());
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initNewestGoods(HomeData.HomeInfo newestGoods) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setLayoutViewBindListener((layoutView, baseLayoutHelper) -> {
            if(layoutView != null) {
                isNewestGoodsBind = true;
            }
        });
        linearLayoutHelper.setLayoutViewUnBindListener((layoutView, baseLayoutHelper) -> {
            if(layoutView == null) {
                isNewestGoodsBind = false;
            }
        });
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.layout_comm_recyclerview, 1, PageConstans.viewType.typeNewestGoods) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if(!isNewestGoodsBind) {
                    final NewestGoodsAdapter adapter = new NewestGoodsAdapter(mContext, newestGoods.getStyle().getNumberOfColumns());
                    final RecyclerView recyclerView = holder.getView(R.id.comm_recyclerView);
                    recyclerView.setItemAnimator(null);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setHasFixedSize(true);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.addItemDecoration(new SpaceViewItemLine(DimensionsUtil.dip2px(mContext, 12)));
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
                                adapter.setScrolling(false);
//                                adapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
                            } else {
                                adapter.setScrolling(true);
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    adapter.getItems().addAll(newestGoods.getItems());
                }
            }
        };
    }

    @Override
    public BaseDelegateAdapter initYouLikeList(HomeData.HomeInfo likeGoods) {

        StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper();
        staggeredGridLayoutHelper.setBgColor(ContextCompat.getColor(mContext, R.color.common_bg));// 设置背景颜色
        // 特有属性
        if(likeGoods.getStyle().getNumberOfColumns() > 0) staggeredGridLayoutHelper.setLane(likeGoods.getStyle().getNumberOfColumns());// 设置控制瀑布流每行的Item数
        if(likeGoods.getStyle().gethGap() > 0) staggeredGridLayoutHelper.setHGap(likeGoods.getStyle().gethGap() - DimensionsUtil.dip2px(mContext, 2));// 设置子元素之间的水平间距
        if(likeGoods.getStyle().getvGap() > 0) staggeredGridLayoutHelper.setVGap(likeGoods.getStyle().getvGap() - DimensionsUtil.dip2px(mContext, 2));// 设置子元素之间的垂直间距

        return new BaseDelegateAdapter(mContext, staggeredGridLayoutHelper, R.layout.layout_comm_guess_you_like, likeGoods.getItems().size(), PageConstans.viewType.typeYouLikeList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 绑定数据
                QMUILinearLayout qmuiLinearLayout = holder.getView(R.id.ll_new_seed_item);
                qmuiLinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 6),
                        QMUIDisplayHelper.dp2px(mContext, 0), 0);

                RatioFrameLayout rf = holder.getView(R.id.rf_you_like);
                if(likeGoods.getItems().get(position).getStyle() != null
                        && likeGoods.getItems().get(position).getStyle().getAspectRatio() > 0) {
                    rf.setRatio(RatioDatumMode.DATUM_WIDTH, likeGoods.getItems().get(position).getStyle().getAspectRatio(), 1);
                }

                HomeItemsInfo model = likeGoods.getItems().get(position);
                holder.setText(R.id.tv_you_like, model.getTitle());
                holder.setText(R.id.tv_you_like_money, "¥" + model.getPrice());
                holder.setText(R.id.tv_you_like_sale_count, "累计销售量" + model.getNumber());
                ImageView imageView = holder.getView(R.id.iv_you_like);
                ImageLoader.get().load(imageView, model.getImgUrl());

                holder.getItemView().setOnClickListener(v -> {
                    if(model.getAction().getGoGoodsDetail() != null
                            && !TextUtils.isEmpty(model.getAction().getGoGoodsDetail().getId())) {
                        String goodsId = model.getAction().getGoGoodsDetail().getId();
                        ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE, goodsId).navigation();
                    }
                });
            }
        };
    }*/
}

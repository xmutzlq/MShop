package google.architecture.home.substance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.util.ScreenUtils;
import google.architecture.common.vcontent.BaseDelegateAdapter;
import google.architecture.common.vcontent.BaseViewHolder;
import google.architecture.common.widget.banner.CirclePageIndicator;
import google.architecture.common.widget.banner.recycle.CommRecyclingPagerAdapter;
import google.architecture.common.widget.banner.recycle.CommonImagePagerAdapter;
import google.architecture.common.widget.banner.recycle.RecycleAutoScrollViewPager;
import google.architecture.coremodel.data.xlj.shopdata.ImgInfo;
import google.architecture.home.R;

public class PageAdapterNew {

    private Context mContext;

    public PageAdapterNew(Context context){
        mContext = context;
    }

    public BaseDelegateAdapter initBanner(final List<ImgInfo> dataList){
        //banner
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_banner, 1, PageConstans.viewType.typeBanner){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                View rootView = holder.getView(R.id.home_banner_root);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.height = ScreenUtils.getScreenHeight()/2+50;
                rootView.setLayoutParams(lp);
                RecycleAutoScrollViewPager mBanner = holder.getView(R.id.adv_image);
                ArrayList<String> imgList = new ArrayList<>();
                for(ImgInfo info :dataList){
                    imgList.add(info.getImageUrl());
                }
                mBanner.setAdapter(new CommonImagePagerAdapter(mContext, imgList).setBannerClickListener(new CommRecyclingPagerAdapter.IBannerClickListener() {
                    @Override
                    public void onBannerClickListener(int position) {

                    }
                }));
                CirclePageIndicator indicator = holder.getView(R.id.adv_circlePageIndicator);
                indicator.setViewPager(mBanner);
                //mBanner.setInterval(banner.getStyle().getAutoScroll());
                mBanner.startAutoScroll();
                mBanner.setCurrentItem(imgList.size() * 500);
                mBanner.setNoScroll(imgList.size() == 1);
                mBanner.requestFocus();
                indicator.setVisibility(imgList.size() == 1 ? View.GONE : View.VISIBLE);
            }
        };
    }

    public BaseDelegateAdapter initBrands(){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_brands,1, PageConstans.viewType.typeBanner){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                RecyclerView brandView = holder.getView(R.id.brand_recycler_view);
            }
        };
    }

}

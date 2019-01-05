package google.architecture.home.substance;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.vcontent.BaseDelegateAdapter;
import google.architecture.common.vcontent.BaseViewHolder;
import google.architecture.common.widget.banner.CirclePageIndicator;
import google.architecture.common.widget.banner.recycle.CommRecyclingPagerAdapter;
import google.architecture.common.widget.banner.recycle.CommonImagePagerAdapter;
import google.architecture.common.widget.banner.recycle.RecycleAutoScrollViewPager;
import google.architecture.coremodel.data.xlj.shopdata.Goods;
import google.architecture.coremodel.data.xlj.shopdata.ImgInfo;
import google.architecture.coremodel.data.xlj.shopdata.TextInfo;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.home.R;
import google.architecture.home.adapter.HomeBrandAdapter;
import google.architecture.home.adapter.HomeGridAdapter;
import google.architecture.home.adapter.HomeHorizontalListAdapter;

public class PageAdapterNew {

    private Context mContext;

    public PageAdapterNew(Context context){
        mContext = context;
    }

    public void reSetBinded(){

    }

    public BaseDelegateAdapter initTitle(String title){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_common_title, 1,PageConstans.viewType.typeBanner){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ((TextView)holder.getView(R.id.title_tv)).setText(title);
            }
        };
    }

    public BaseDelegateAdapter initCommonImageView(int resId){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_common_image, 1,PageConstans.viewType.typeBanner){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ((ImageView)holder.getView(R.id.common_img_iv)).setImageResource(resId);
            }
        };
    }

    public BaseDelegateAdapter initCommonImageView(String url){
        return initCommonImageView(url, 0, 0, 0, 0);
    }

    public BaseDelegateAdapter initCommonImageView(String url, int marginLeft, int marginTop, int marginRight, int marginBottom){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_common_image, 1,PageConstans.viewType.typeCommonView){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView iv = holder.getView(R.id.common_img_iv);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(DimensionsUtil.dip2px(mContext,marginLeft),DimensionsUtil.dip2px(mContext,marginTop),DimensionsUtil.dip2px(mContext,marginRight),DimensionsUtil.dip2px(mContext,marginBottom));
                iv.setLayoutParams(lp);
                ImageLoader.get().load(iv, ApiConstants.GankHost+url);
            }
        };
    }

    public BaseDelegateAdapter initCenterImageView(String url,int height){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_center_image, 1,PageConstans.viewType.typeCenterView){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,DimensionsUtil.dip2px(mContext,height));
                holder.getView(R.id.common_img_iv).setLayoutParams(lp);
                ImageLoader.get().load(holder.getView(R.id.common_img_iv), ApiConstants.GankHost+url);
                /*ImageLoader.get().load(holder.getView(R.id.common_img_iv), ApiConstants.GankHost + url, new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ((ImageView)holder.getView(R.id.common_img_iv)).setImageDrawable(resource);
                        return false;
                    }
                });*/
            }
        };
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
                lp.height = (int)(((float)ScreenUtils.getScreenWidth())/0.889);
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

    public BaseDelegateAdapter initBrands(List<ImgInfo> list){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_brands,1, PageConstans.viewType.typeBrand){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                RecyclerView brandView = holder.getView(R.id.brand_recycler_view);
                HomeBrandAdapter adapter = new HomeBrandAdapter(list, new HomeBrandAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        openGoodsType("",list.get(pos).getUrlids());
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                brandView.setLayoutManager(layoutManager);
                brandView.setAdapter(adapter);
            }
        };
    }

    public BaseDelegateAdapter initSecondBrand() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.temp_second_brand, 1, PageConstans.viewType.typeBrand){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
    }

    public BaseDelegateAdapter initNewCollection(List<Goods> list){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_common_recycle_view, 1,PageConstans.viewType.typeBrand){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                RecyclerView recycleView = holder.getView(R.id.common_recycle_view);
                HomeGridAdapter adapter = new HomeGridAdapter(list);
                recycleView.setAdapter(adapter);
                GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
                recycleView.setLayoutManager(layoutManager);
            }
        };
    }

    public BaseDelegateAdapter initHorizontalScrollBrandList(List<Goods> list){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_common_recycle_view, 1,PageConstans.viewType.typeHorizontal){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                RecyclerView recycleView = holder.getView(R.id.common_recycle_view);
                HomeHorizontalListAdapter adapter = new HomeHorizontalListAdapter(list, new HomeHorizontalListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        openGoodsDetail(list.get(pos).getGoodsId()+"");
                    }
                });
                recycleView.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recycleView.setLayoutManager(layoutManager);
            }
        };
    }

    public BaseDelegateAdapter initThreeSome(List<Goods> list){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_tree_some, 1,PageConstans.viewType.typeThreeSome){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                View brandAview = holder.getView(R.id.brand_a);
                View brandBview = holder.getView(R.id.brand_b);

                ImageView imgA = holder.getView(R.id.goods_img_iv);
                TextView goodsNameA = holder.getView(R.id.goods_name_tv);
                TextView priceA = holder.getView(R.id.goods_price_original_tv);

                ImageView imgB = holder.getView(R.id.goods_img_b_iv);
                TextView goodsNameB = holder.getView(R.id.goods_name_b_tv);
                TextView priceB = holder.getView(R.id.goods_price_original_b_tv);

                if(list.size()>= 2){
                    Goods itemA = list.get(0);
                    Goods itemB = list.get(1);
                    ImageLoader.get().load(imgA, ApiConstants.GankHost+itemA.getImageUrl());
                    goodsNameA.setText(itemA.getGoodsName());
                    priceA.setText("RMB/"+itemA.getShopPrice());
                    ImageLoader.get().load(imgB, ApiConstants.GankHost+itemB.getImageUrl());
                    goodsNameB.setText(itemB.getGoodsName());
                    priceB.setText("RMB/"+itemB.getShopPrice());
                }else if(list.size() == 1){
                    brandBview.setVisibility(View.INVISIBLE);
                }else{
                    brandAview.setVisibility(View.INVISIBLE);
                    brandBview.setVisibility(View.INVISIBLE);
                }

            }
        };
    }

    public BaseDelegateAdapter initClassify(List<List<TextInfo>> classify){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_classify, 1,PageConstans.viewType.typeClassify){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ViewGroup sneakerList = holder.getView(R.id.sneaker_list);
                ViewGroup clothingList = holder.getView(R.id.clothing_list);
                ViewGroup accessoriesList = holder.getView(R.id.accessories_list);
                sneakerList.removeAllViews();
                clothingList.removeAllViews();
                accessoriesList.removeAllViews();
                for(int i = 0; i < classify.size(); i++){
                    for(int j = 0; j < classify.get(i).size(); j++){
                        TextInfo info = classify.get(i).get(j);
                        TextView text = new TextView(mContext);
                        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0,DimensionsUtil.dip2px(mContext,15),0,DimensionsUtil.dip2px(mContext,15));
                        text.setLayoutParams(lp);*/
                        text.setBackgroundResource(R.drawable.classify_underline);
                        text.setPadding(0,DimensionsUtil.dip2px(mContext,8),0,DimensionsUtil.dip2px(mContext,8));
                        text.setText(info.getText());
                        text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openGoodsType("", info.getUrlids());
                            }
                        });
                        if(i == 0){
                            sneakerList.addView(text);
                        }else if(i == 1){
                            clothingList.addView(text);
                        }else if(i == 2){
                            accessoriesList.addView(text);
                        }
                    }
                }
            }
        };
    }

    public BaseDelegateAdapter initColumnTwo(String urlA, String urlB){
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(mContext, linearLayoutHelper, R.layout.home_item_column_two, 1,PageConstans.viewType.typeColumnTwo){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView colA = holder.getView(R.id.col_a_iv);
                ImageView colB = holder.getView(R.id.col_b_iv);
                ImageLoader.get().load(colA,ApiConstants.GankHost+urlA);
                ImageLoader.get().load(colB,ApiConstants.GankHost+urlB);
            }
        };
    }

    private void openGoodsType(String catName,String urlids){
        ARouter.getInstance().build(ARouterPath.Search2Aty).withString(CommKeyUtil.EXTRA_VALUE,catName).withString(CommKeyUtil.EXTRA_KEY,urlids).navigation(mContext);
    }

    private void openGoodsDetail(String goodsIs){
        ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE,goodsIs).navigation(mContext);
    }

}

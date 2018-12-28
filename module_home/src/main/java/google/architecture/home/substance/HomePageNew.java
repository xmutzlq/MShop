package google.architecture.home.substance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.vcontent.BaseDelegateAdapter;
import google.architecture.common.vcontent.ContantDelegate;
import google.architecture.coremodel.data.xlj.shopdata.ShopData;
import google.architecture.home.R;

public class HomePageNew implements ContantDelegate, PageAdapter.IActionTitleRightLabelClick {

    private Context mContext;
    private PageAdapterNew mPageAdapter;
    private DelegateAdapter delegateAdapter;
    private ShopData mData;

    public HomePageNew(Context activity, ShopData data){
        mContext = activity;
        mPageAdapter = new PageAdapterNew(mContext);
        mData = data;
    }

    @Override
    public DelegateAdapter initRecyclerView(RecyclerView recyclerView) {
        //初始化
        //创建VirtualLayoutManager对象
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        //设置适配器
        delegateAdapter = new DelegateAdapter(layoutManager, false);
        recyclerView.setAdapter(delegateAdapter);
        return delegateAdapter;
    }

    @Override
    public List<BaseDelegateAdapter> initItemDelegates() {
        List<BaseDelegateAdapter> itemDelegates = new ArrayList<>();
        itemDelegates.add(mPageAdapter.initBanner(mData.getSlideImg()));//顶部图片滚动
        itemDelegates.add(mPageAdapter.initBrands(mData.getBrandNav()));//品牌
        itemDelegates.add(mPageAdapter.initSecondBrand());
        itemDelegates.add(mPageAdapter.initTitle("NEW COLLECTION"));
        itemDelegates.add(mPageAdapter.initNewCollection(mData.getGoodsList().get(0)));//new Collection
        itemDelegates.add(mPageAdapter.initCommonImageView(R.drawable.tem_img_a));//装饰图片
        itemDelegates.add(mPageAdapter.initHorizontalScrollBrandList(mData.getGoodsList().get(1)));
        itemDelegates.add(mPageAdapter.initThreeSome(mData.getGoodsList().get(2)));
        itemDelegates.add(mPageAdapter.initCenterImageView(mData.getFloor().get(6).getImageUrl(),25));
        itemDelegates.add(mPageAdapter.initCommonImageView(mData.getFloor().get(7).getImageUrl()));
        itemDelegates.add(mPageAdapter.initCenterImageView(mData.getFloor().get(8).getImageUrl(),25));
        itemDelegates.add(mPageAdapter.initColumnTwo(mData.getFloor().get(9).getImageUrl(),mData.getFloor().get(10).getImageUrl()));
        itemDelegates.add(mPageAdapter.initClassify(mData.getClassify()));//classify
        itemDelegates.add(mPageAdapter.initCommonImageView(mData.getFloor().get(11).getImageUrl(),5,5,5,5));
        itemDelegates.add(mPageAdapter.initHorizontalScrollBrandList(mData.getGoodsList().get(3)));
        itemDelegates.add(mPageAdapter.initCommonImageView(mData.getFloor().get(12).getImageUrl(),5,5,5,5));
        itemDelegates.add(mPageAdapter.initHorizontalScrollBrandList(mData.getGoodsList().get(4)));
        itemDelegates.add(mPageAdapter.initCommonImageView(mData.getFloor().get(13).getImageUrl(),5,5,5,5));
        itemDelegates.add(mPageAdapter.initHorizontalScrollBrandList(mData.getGoodsList().get(5)));
        itemDelegates.add(mPageAdapter.initCommonImageView(mData.getFloor().get(14).getImageUrl(),5,5,5,5));
        itemDelegates.add(mPageAdapter.initHorizontalScrollBrandList(mData.getGoodsList().get(6)));
        itemDelegates.add(mPageAdapter.initCommonImageView(mData.getFloor().get(15).getImageUrl(),5,5,5,5));
        itemDelegates.add(mPageAdapter.initHorizontalScrollBrandList(mData.getGoodsList().get(7)));
        itemDelegates.add(mPageAdapter.initCommonImageView(mData.getFloor().get(16).getImageUrl(),5,5,5,5));
        itemDelegates.add(mPageAdapter.initHorizontalScrollBrandList(mData.getGoodsList().get(8)));
        return itemDelegates;
    }

    @Override
    public void onActionTitleRightLabelClick(View v, int type) {

    }
}

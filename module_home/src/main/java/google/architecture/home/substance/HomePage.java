package google.architecture.home.substance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import google.architecture.common.vcontent.BaseDelegateAdapter;
import google.architecture.common.vcontent.ContantDelegate;
import google.architecture.coremodel.data.HomeData;
import google.architecture.coremodel.data.HomeItemsInfo;
import google.architecture.coremodel.util.TextUtil;
import google.architecture.home.R;
import io.reactivex.Flowable;

/**
 * @author lq.zeng
 * @date 2018/5/2
 */

public class HomePage implements ContantDelegate, PageAdapter.IActionTitleRightLabelClick {
    private Context mContext;
    private PageAdapter mPageAdapter;
    private DelegateAdapter delegateAdapter;
    private List<HomeData.HomeInfo> mHomeInfo;

    public HomePage(Context activity, List<HomeData.HomeInfo> homeInfo) {
        mContext = activity;
        mHomeInfo = homeInfo;
        mPageAdapter = new PageAdapterImp(mContext);
    }

    public void reSetBinded() {
        mPageAdapter.reSetBinded();
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
        viewPool.setMaxRecycledViews(PageConstans.viewType.typeBanner, 2);
        viewPool.setMaxRecycledViews(PageConstans.viewType.typeGv, 8);
        viewPool.setMaxRecycledViews(PageConstans.viewType.typeNews, 2);
        viewPool.setMaxRecycledViews(PageConstans.viewType.typeRecommend, 4);
        viewPool.setMaxRecycledViews(PageConstans.viewType.typeNewestGoods, 5);
        viewPool.setMaxRecycledViews(PageConstans.viewType.typeYouLikeList, 10);
        recyclerView.setRecycledViewPool(viewPool);

        //设置适配器
        delegateAdapter = new DelegateAdapter(layoutManager, false);
        recyclerView.setAdapter(delegateAdapter);
        return delegateAdapter;
    }

    @Override
    public List<BaseDelegateAdapter> initItemDelegates() {
        List<BaseDelegateAdapter> itemDelegates = new ArrayList<>();
        for (HomeData.HomeInfo info : mHomeInfo) {
            switch (info.getType()) {
                case HomeTypes.TYPE_CONTAINER_SCROLL:
                    if(HomeModels.MODEL_BANNER.equals(info.getId())) {
                        itemDelegates.add(mPageAdapter.initBanner(info));
                    } else if(HomeModels.MODEL_NEW_GOODS.equals(info.getId())) {
                        if(info.getHeader() != null
                                && !TextUtil.isEmpty(info.getHeader().getTitle())) {
                            itemDelegates.add(mPageAdapter.initTitle("", info.getHeader().getTitle(),
                                    "", 0, 0, false, -1, null));
                        }
                        itemDelegates.add(mPageAdapter.initNewestGoods(info));
                    }
                    break;
                case HomeTypes.TYPE_CONTAINER_ONE_COLUMN:
                    if(HomeModels.MODEL_IMAGE.equals(info.getId())) {
                        itemDelegates.add(mPageAdapter.initHomeAd(info));
                    }
                    break;
                case HomeTypes.TYPE_CONTAINER_TWO_COLUMN:
                    if(HomeModels.MODEL_GOODS.equals(info.getId())) {
                        if(info.getHeader() != null
                                && !TextUtil.isEmpty(info.getHeader().getTitle())) {
                            itemDelegates.add(mPageAdapter.initTitle("", info.getHeader().getTitle(),
                                    "", 0, R.color.common_bg, false, -1, null));
                        }
                        itemDelegates.add(mPageAdapter.initYouLikeList(info));
                    }
                    break;
                case HomeTypes.TYPE_CONTAINER_FOUR_COLUMN:
                    if(HomeModels.MODEL_RECOMMENT_ICON.equals(info.getId())) {
                        if(info.getHeader() != null) {
                            itemDelegates.add(mPageAdapter.initTitle(info.getHeader().getTitle(), "",
                                    info.getHeader().getText(), R.mipmap.icon_title_right_exchange, 0, false,
                                    PageConstans.titleRightClickType.clickTypeRecommnedExchange, this));
                        }
                        itemDelegates.add(mPageAdapter.initRecommend(info));
                    }  else if(HomeModels.MODEL_HOT_BRAND.equals(info.getId())) {
                        if(info.getHeader() != null
                                && !TextUtil.isEmpty(info.getHeader().getTitle())) {
                            itemDelegates.add(mPageAdapter.initTitle("", info.getHeader().getTitle(),
                                    "", 0, 0, false, -1, null));
                        }
                        itemDelegates.add(mPageAdapter.initGvMenu(info));
                    }
                    break;
                case HomeTypes.TYPE_CONTAINER_FIVE_COLUMN:
                    if(HomeModels.MODEL_ICON.equals(info.getId())) {
                        itemDelegates.add(mPageAdapter.initGvMenu(info));
                    }
                    break;
            }
        }
        return itemDelegates;
    }

    @Override
    public void onActionTitleRightLabelClick(View v, int type) {
        switch (type) {
            case PageConstans.titleRightClickType.clickTypeRecommnedExchange:
                Flowable.fromIterable(mHomeInfo)
                    //过滤出推荐的数据
                    .filter(homeInfo -> HomeTypes.TYPE_CONTAINER_FOUR_COLUMN.equals(homeInfo.getType())
                            && HomeModels.MODEL_RECOMMENT_ICON.equals(homeInfo.getId())).take(1)
                    //随机创建四个替换数据
                    .flatMap(homeInfo -> {
                        final Random random = new Random();
                        final List<HomeItemsInfo> tempRecommendData = new ArrayList<>();
                        for (int i = 0; i < homeInfo.getStyle().getNumberOfColumns(); i ++){ //创建
                            tempRecommendData.add(homeInfo.getItems().get(random.nextInt(homeInfo.getItems().size())));
                        }
                        if(homeInfo.cacheItems == null) {
                            homeInfo.cacheItems = new ArrayList<>();
                        }
                        homeInfo.cacheItems.clear();
                        homeInfo.cacheItems.addAll(tempRecommendData);
                        return Flowable.just(1);})
                    //更新数据
                    .subscribe(integer -> {
                        DelegateAdapter.Adapter adapter = delegateAdapter.findAdapterByIndex(0);
                        adapter.notifyDataSetChanged();
                    });
                break;
        }
    }
}

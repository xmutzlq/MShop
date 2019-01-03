package google.architecture.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.view.HomeSearchScrollController;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.LinkedList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.viewmodel.HomeViewModel;
import google.architecture.coremodel.data.xlj.shopdata.ShopData;
import google.architecture.home.substance.HomePageNew;

/**
 * @Desc FragmentNews
 */
@Route(path = ARouterPath.HomeFgt)
public class FragmentHome extends BaseFragment {

    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    ViewGroup searchLayout;
    ImageView floor;

    HomeViewModel mHomeViewModel;
    HomeSearchScrollController searchScrollAdapter;

    List<DelegateAdapter.Adapter> mAdapters;
    boolean isOnSecondFloor;
    boolean isRefresh;

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapters = new LinkedList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        getToolbarHelper().getAppBarLayout().setVisibility(View.GONE);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floor = (ImageView)findViewById(view, R.id.secondfloor);
        final TwoLevelHeader header = (TwoLevelHeader)findViewById(view, R.id.header);
        header.setOnTwoLevelListener(refreshLayout1 -> {
            isOnSecondFloor = true;
            searchLayout.setVisibility(View.GONE);
            ((ImageView)findViewById(view, R.id.secondfloor_content)).animate().alpha(1).setDuration(2000);
            refreshLayout.getLayout().postDelayed(()->{
                isOnSecondFloor = false;
                searchLayout.setVisibility(View.VISIBLE);
                header.finishTwoLevel();
                ((ImageView)findViewById(view, R.id.secondfloor_content)).animate().alpha(0).setDuration(1000);
            } ,5000);
            return true;//true 将会展开二楼状态 false 关闭刷新
        });

        searchLayout = (LinearLayout)findViewById(view, R.id.home_search_layout);
        refreshLayout = (SmartRefreshLayout)findViewById(view, R.id.home_refreshLayout);
        refreshLayout.setOnMultiPurposeListener(listener);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableOverScrollBounce(false);
        recyclerView = (RecyclerView) findViewById(view, R.id.comm_recyclerView);
        searchScrollAdapter = new HomeSearchScrollController(mContext);
        searchScrollAdapter.bindRollView(searchLayout);
        recyclerView.addOnScrollListener(searchScrollAdapter);
    }

    @Override
    public void onFragmentFirstVisible() {
        loadHomeData();
    }

    private void loadHomeData() {
        mHomeViewModel = new HomeViewModel();
        addRunStatusChangeCallBack(mHomeViewModel);
        mHomeViewModel.getShoppingHome(!isRefresh);
    }

    @Override
    protected void onDataResult(Object o) {
        refreshLayout.finishRefresh(0);
       /* HomeData data = (HomeData)o;
        if(data == null || data.getCard() == null || data.getCard().size() == 0) return; //暂无数据
        for (HomeData.HomeInfo info : data.getCard()) {
            if(HomeTypes.TYPE_CONTAINER_SCROLL.equals(info.getType())
                && HomeModels.MODEL_BANNER.equals(info.getId())) {
                searchScrollAdapter.setMaxHeight(info.getStyle().getPageHeight());
                break;
            }
        }
        HomePage homePage = new HomePage(mContext, data.getCard());
        if(isRefresh){
            isRefresh = false;
            homePage.reSetBinded();
            mAdapters.clear();
        }
        DelegateAdapter delegateAdapter = homePage.initRecyclerView(recyclerView);*/
        ShopData data = (ShopData) o;
        searchScrollAdapter.setMaxHeight(250);
        HomePageNew homePage = new HomePageNew(mContext,data);
        DelegateAdapter delegateAdapter = homePage.initRecyclerView(recyclerView);
        mAdapters.addAll(homePage.initItemDelegates());
        delegateAdapter.setAdapters(mAdapters);
        recyclerView.requestLayout();
    }

    SimpleMultiPurposeListener listener = new SimpleMultiPurposeListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishLoadMore(0);
        }
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            isRefresh = true;
            loadHomeData();
        }
        @Override
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
            floor.setTranslationY(Math.min(offset - floor.getHeight(), refreshLayout.getLayout().getHeight() - floor.getHeight()));
            if(isDragging && offset > 0 || isOnSecondFloor) {
                searchLayout.setVisibility(View.GONE);
            } else {
                searchLayout.setVisibility(View.VISIBLE);
            }
        }
    };

}

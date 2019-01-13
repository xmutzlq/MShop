package google.architecture.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.king.android.res.config.ARouterPath;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import google.architecture.common.base.BasePagingActivity;
import google.architecture.common.base.ViewManager;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.util.FragmentUtils;
import google.architecture.common.util.TransitionHelper;
import google.architecture.common.viewmodel.HomeSearchViewModel;
import google.architecture.common.widget.CommDrawerLayout;
import google.architecture.common.widget.GridSpacingItemDecoration;
import google.architecture.coremodel.data.SearchResult;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.PreferencesUtils;
import google.architecture.home.adapter.HomeSearchGoodsAdapter;
import google.architecture.home.databinding.ActivitySearchSecondBinding;
import google.architecture.home.widget.CommFilterTabView;

/**
 * @author lq.zeng
 * @date 2018/5/23
 */
@Route(path = ARouterPath.Search2Aty)
public class ActivitySearchSecond extends BasePagingActivity<ActivitySearchSecondBinding>
        implements CommFilterTabView.FilterOpenListener, CommFilterTabView.TabClickListener {

    private CommFilterTabView tabView;
    //搜索结果数据
    private GridLayoutManager layoutManager;
    private HomeSearchGoodsAdapter mHomeSearchGoodsAdapter;
    private HomeSearchViewModel mHomeSearchViewModel;

    private String searchInputValue;
    private String searchInputId;
    private String searchInputCatId;

    private boolean isPriceLower;
    private boolean hasLoadFilter;

    private boolean isNeedBackStact;

    @Override
    protected int getLayout() {
        return R.layout.activity_search_second;
    }

    @Override
    protected boolean isNeedRefreshing() {
        return false;
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        mHomeSearchGoodsAdapter = new HomeSearchGoodsAdapter();
        return mHomeSearchGoodsAdapter;
    }

    @Override
    protected void prePareRecycleView() {
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        recyclerView.setPadding(DimensionsUtil.dip2px(this_, 15), DimensionsUtil.dip2px(this_, 15),
                DimensionsUtil.dip2px(this_, 15), DimensionsUtil.dip2px(this_, 15));
        if(recyclerView.getItemDecorationCount() > 0) recyclerView.removeItemDecorationAt(0);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 30, false));
        layoutManager = new GridLayoutManager(this_, 2);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void afterRecycleView() {
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int state = SearchResult.GoodsItem.ITEM_TYPE_LIST;
                if(adapter.getData() != null
                        && adapter.getData().size() > 0
                        && position < adapter.getData().size()) {
                    state = mHomeSearchGoodsAdapter.getData().get(position).getItemType();
                }
                return state == SearchResult.GoodsItem.ITEM_TYPE_LIST ? 2 : 1;
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this.getApplicationContext());

        setCanBack(false);
        setTitleName(getString(R.string.xlj_goods_list));

        searchInputValue = getIntent().getExtras().getString(CommKeyUtil.EXTRA_VALUE, ""); //分类项中的：catId
        searchInputId = getIntent().getExtras().getString(CommKeyUtil.EXTRA_KEY, "0"); //分类项中的：urlids
        searchInputCatId = getIntent().getExtras().getString(CommKeyUtil.EXTRA_KEY_2, "0");

        //搜索结果
        binding.tvSearch2Result.setText(searchInputValue);
        binding.tvSearch2Result.setVisibility(TextUtils.isEmpty(searchInputValue) ? View.GONE : View.VISIBLE);
        binding.hsSearch2Result.post(()->binding.hsSearch2Result.fullScroll(HorizontalScrollView.FOCUS_RIGHT));
        binding.tvSearch2Result.setOnClickListener(v -> {
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_CLOSE_SEARCH2));
            ViewManager.getInstance().finishActivity();});

        //搜索结果右侧保留空间
        binding.tvSearch2ResultKeep.setOnClickListener(v -> {ViewManager.getInstance().finishActivity();});
        //搜索结果外边框
        binding.homeSearch2View.setOnClickListener(v -> {ViewManager.getInstance().finishActivity();});
        //返回按钮
        binding.ibSearch2Back.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean(CommEvent.KEY_EXTRA_VALUE, true);
            CommEvent event = new CommEvent(CommEvent.MSG_TYPE_CLOSE_SEARCH2);
            event.bundle = bundle;
            EventBus.getDefault().post(event);
            ViewManager.getInstance().finishActivity();
        });
        //列表、网格交替按钮
        binding.ibSearch2Exchange.setOnClickListener(v -> {
            mHomeSearchViewModel.setState(mHomeSearchGoodsAdapter.exchangeShownState());
            binding.ibSearch2Exchange.setImageResource(mHomeSearchViewModel.getState() == SearchResult.GoodsItem.ITEM_TYPE_LIST ? R.mipmap.ic_grid : R.mipmap.ic_list);
            if(recyclerView.getItemDecorationCount() > 0) recyclerView.removeItemDecorationAt(0);
            RecyclerView.ItemDecoration itemDecoration = mHomeSearchViewModel.getState() == SearchResult.GoodsItem.ITEM_TYPE_LIST ?
                    new GridSpacingItemDecoration(1, 30, false) :
                    new GridSpacingItemDecoration(2, 30, false);
            recyclerView.addItemDecoration(itemDecoration);
            mHomeSearchGoodsAdapter.refresh();
            recyclerView.setLayoutManager(layoutManager);
        });
        //主筛选
        tabView = binding.filterTabView;
        tabView.setFilterOpenListener(this);
        tabView.setTabClickListener(this);

        //设置列表点击
        adapter.setOnItemClickListener((adapter, view, position) -> {
            String goodsId = mHomeSearchGoodsAdapter.getData().get(position).getGoods_id();
            if(view.findViewById(R.id.search_goods_list_iv) != null) {
                ActivityOptionsCompat optionsCompat = TransitionHelper.getTransitionOptionsCompat(this_, view.findViewById(R.id.search_goods_list_iv));
                ARouter.getInstance().build(ARouterPath.DetailAty).withOptionsCompat(optionsCompat).withString(CommEvent.KEY_EXTRA_VALUE, goodsId).navigation(this_);
            } else if(view.findViewById(R.id.search_goods_grid_iv) != null) {
                ActivityOptionsCompat optionsCompat = TransitionHelper.getTransitionOptionsCompat(this_, view.findViewById(R.id.search_goods_grid_iv));
                ARouter.getInstance().build(ARouterPath.DetailAty).withOptionsCompat(optionsCompat).withString(CommEvent.KEY_EXTRA_VALUE, goodsId).navigation(this_);
            } else {
                ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE, goodsId).navigation(this_);
            }
        });

        //加载数据
        Looper.myQueue().addIdleHandler(() -> {
            mHomeSearchViewModel = new HomeSearchViewModel();
            setListViewModel(mHomeSearchViewModel);
            LogUtils.tag("zlq").e("searchInputId = " + searchInputId + ", searchInputCatId = " + searchInputCatId);
//            mHomeSearchViewModel.loadSearchResultData(searchInputId, searchInputValue);
            mHomeSearchViewModel.loadSearchResultDataNew(searchInputId, searchInputCatId, searchInputValue, 1, 0);
            pagingHelper.onRefresh();
            return false;
        });
    }

    @Override
    protected void onDataResult(Object o) {
        super.onDataResult(o);
        //右侧筛选
        if(!hasLoadFilter) {
            hasLoadFilter = true;
            int position = PreferencesUtils.getInt(this_, PreferencesUtils.PREFERENCE_KEY_FILTER_ALL_CATE_POSITION, -1);
            String choiceCate = position == -1 ? "" : mHomeSearchViewModel.getFilterData().get(0).getList().get(position).getName();
            Looper.myQueue().addIdleHandler(() -> {
                binding.filterDrawerLayout.setDrawerLockMode(CommDrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                FragmentUtils.addFragment(getSupportFragmentManager(), FragmentFilterMain.newInstance(new ArrayList<>(mHomeSearchViewModel.getFilterData()), choiceCate),
                        R.id.filter_drawer_content, false, true);
                return  false;
            });
        }
    }

    @Override
    protected void onEmptyDisplaying() {
        adapter.setEmptyView(R.layout.layout_empty_search_result, (ViewGroup) recyclerView.getParent());
    }

    @Override
    protected void responseEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_OPEN_AREA.equals(event.msgType)) { //打开配送区域
            isNeedBackStact = true;
            FragmentUtils.addFragment(getSupportFragmentManager(), FragmentFilterAreaSelect.newInstance(), R.id.filter_drawer_content, false, true);
        } else if(CommEvent.MSG_TYPE_CLOSE_AREA.equals(event.msgType)) { //关闭配送区域
            isNeedBackStact = false;
            FragmentUtils.popFragment(getSupportFragmentManager());
            if(event.bundle != null) {
                String value = event.bundle.getString(CommEvent.KEY_EXTRA_VALUE);
                FragmentFilterMain filterMain = (FragmentFilterMain)FragmentUtils.findFragment(getSupportFragmentManager(), FragmentFilterMain.class);
                filterMain.refreshLocation(value);
            }
        } else if(CommEvent.MSG_TYPE_OPEN_ALL_CATE.equals(event.msgType)) {  //打开所有分类
            isNeedBackStact = true;
//            FragmentUtils.addFragment(getSupportFragmentManager(), FragmentFilterAllCate.newInstance(new ArrayList<>(filterData.getCat())),
//                    R.id.filter_drawer_content, false, true);
        } else if(CommEvent.MSG_TYPE_CLOSE_ALL_CATE.equals(event.msgType)) { //关闭所有分类
            isNeedBackStact = false;
            FragmentUtils.popFragment(getSupportFragmentManager());
            if(event.bundle != null) {
                String value = event.bundle.getString(CommEvent.KEY_EXTRA_VALUE);
                FragmentFilterMain filterMain = (FragmentFilterMain)FragmentUtils.findFragment(getSupportFragmentManager(), FragmentFilterMain.class);
                filterMain.refreshCates(value);
            }
        } else if(CommEvent.MSG_TYPE_FILTER_RESULT.equals(event.msgType)) {
            if(event.bundle != null) {
                String type = event.bundle.getString(CommKeyUtil.EXTRA_KEY_2);
                SearchResult.FilterResultData filterResultData = event.bundle.getParcelable(CommKeyUtil.EXTRA_KEY);
//                mHomeSearchViewModel.setFilterResultData(filterResultData);
                if(TextUtils.isEmpty(type) || !FragmentFilterMain.TYPE_FILTER_RESET.equals(type)) {
                    searchInputId = filterResultData.getParams();
                    onTabClick(false, tabView.getActionTag());
                }
            }
        } else if(CommEvent.MSG_TYPE_CLOSE_ALL_FILTER.equals(event.msgType)) {
            if(binding.filterDrawerLayout != null && binding.filterDrawerContent != null) {
                binding.filterDrawerLayout.closeDrawer(binding.filterDrawerContent);
            }
        }
    }

    @Override
    public void onFilterOpenListener() {
        binding.filterDrawerLayout.setDrawerLockMode(CommDrawerLayout.LOCK_MODE_UNLOCKED);
        binding.filterDrawerLayout.openDrawer(binding.filterDrawerContent);
    }

    @Override
    public void onTabClick(boolean fromBtn, String type) {
        if(CommFilterTabView.TAB_FILTER.equals(type)) return;
        if(CommFilterTabView.TAB_EXCHANGE.equals(type)) {
            binding.ibSearch2Exchange.performClick();
            return;
        }
        switch (type) {
            case CommFilterTabView.TAB_COLLIGATION:
                isPriceLower = false;
                if(mHomeSearchViewModel.getFilterResultData() != null) {
                    mHomeSearchViewModel.loadSearchResultData(searchInputId, searchInputValue,"", "",
                            mHomeSearchViewModel.getFilterResultData().getLowPrice(), mHomeSearchViewModel.getFilterResultData().getHeightPrice(), mHomeSearchViewModel.getFilterResultData().getParams());
                } else {
                    mHomeSearchViewModel.loadSearchResultData(searchInputId, searchInputValue,
                            "", "", "", "");
                }
                break;
            case CommFilterTabView.TAB_SALES_VOLUME:
                isPriceLower = false;
                if(mHomeSearchViewModel.getFilterResultData() != null) {
                    mHomeSearchViewModel.loadSearchResultData(searchInputId, searchInputValue,"sales_sum", "",
                            mHomeSearchViewModel.getFilterResultData().getLowPrice(), mHomeSearchViewModel.getFilterResultData().getHeightPrice(),
                            mHomeSearchViewModel.getFilterResultData().getParams());
                } else {
                    mHomeSearchViewModel.loadSearchResultData(searchInputId, searchInputValue,
                            "sales_sum", "", "", "");
                }
                break;
            case CommFilterTabView.TAB_PRICE:
                if(fromBtn) isPriceLower = !isPriceLower;
                String order = isPriceLower ? "desc" : "asc";
                if(mHomeSearchViewModel.getFilterResultData() != null) {
                    mHomeSearchViewModel.loadSearchResultData(searchInputId, searchInputValue,"shop_price", order,
                            mHomeSearchViewModel.getFilterResultData().getLowPrice(), mHomeSearchViewModel.getFilterResultData().getHeightPrice(),
                            mHomeSearchViewModel.getFilterResultData().getParams());
                } else {
                    mHomeSearchViewModel.loadSearchResultData(searchInputId, searchInputValue,
                            "shop_price", order, "", "");
                }
                break;
                //2 = 新品
            case CommFilterTabView.TAB_NEWEST:
                mHomeSearchViewModel.loadSearchResultDataNew(searchInputId, searchInputCatId, searchInputValue, 2, 0);
                break;
                //3 = 人气
            case CommFilterTabView.TAB_HOT:
                mHomeSearchViewModel.loadSearchResultDataNew(searchInputId, searchInputCatId, searchInputValue, 3, 0);
                break;
                //1 = 默认
            case CommFilterTabView.TAB_DEFAULT:
                mHomeSearchViewModel.loadSearchResultDataNew(searchInputId, searchInputCatId, searchInputValue, 1, 0);
                break;
                //0：升序 1：降序
            case CommFilterTabView.TAB_DEFAULT_AES: //默认—升
                mHomeSearchViewModel.loadSearchResultDataNew(searchInputId, searchInputCatId, searchInputValue, 1, 0);
                break;
            case CommFilterTabView.TAB_DEFAULT_DES: //默认—降
                mHomeSearchViewModel.loadSearchResultDataNew(searchInputId, searchInputCatId, searchInputValue, 1, 1);
                break;
        }
        pagingHelper.onRefresh();
    }

    @Override//关闭侧滑页面
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (binding.filterDrawerLayout.isDrawerOpen(binding.filterDrawerContent)) {
                if(isNeedBackStact) {
                    isNeedBackStact = false;
                    FragmentUtils.popFragment(getSupportFragmentManager());
                } else {
                    binding.filterDrawerLayout.closeDrawer(binding.filterDrawerContent);
                }
            } else {
                Bundle bundle = new Bundle();
                bundle.putBoolean(CommEvent.KEY_EXTRA_VALUE, true);
                CommEvent closeEvent = new CommEvent(CommEvent.MSG_TYPE_CLOSE_SEARCH2);
                closeEvent.bundle = bundle;
                EventBus.getDefault().post(closeEvent);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

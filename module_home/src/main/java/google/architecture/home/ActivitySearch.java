package google.architecture.home;

import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.SelectDialog;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;

import java.util.ArrayList;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.KeyboardUtils;
import google.architecture.common.viewmodel.HomeSearchViewModel;
import google.architecture.coremodel.data.SearchHotKey;
import google.architecture.coremodel.datamodel.db.entity.SearchInfoEntity;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.TextUtil;
import google.architecture.home.adapter.FlowLayoutAdapter;
import google.architecture.home.adapter.HomeSearchHistoryBindingAdapter;
import google.architecture.home.databinding.ActivitySearchBinding;
import google.architecture.home.search.ScrollLinearLayoutManager;
import google.architecture.home.search.SearchHistoryItemDecoration;

/**
 * @author lq.zeng
 * @date 2018/5/21
 */
@Route(path = ARouterPath.SearchAty)
public class ActivitySearch extends BaseActivity<ActivitySearchBinding> {
    private HomeSearchViewModel homeSearchViewModel = new HomeSearchViewModel();
    private HomeSearchHistoryBindingAdapter adapter;
    private String inputValue;

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //搜索按钮
        binding.homeSearch.setOnClickListener(v -> {
            KeyboardUtils.closeSoftInput(this_, binding.homeSearchView);
            if(!TextUtils.isEmpty(inputValue)) {
                SearchInfoEntity entity = new SearchInfoEntity(inputValue);
                homeSearchViewModel.saveHistorySearchCurr(entity);
                gotoSearchSecond();
            }
        });

        //搜索框
        binding.homeSearchView.setQueryHint(getResources().getString(R.string.home_search_input_hint));
        binding.homeSearchView.requestFocus();
        binding.homeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    binding.homeSearchView.setIconified(true);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                inputValue = newText;
                return false;
            }
        });

        //热搜
        FlowLayoutAdapter flowLayoutAdapter = new FlowLayoutAdapter(this_, new ArrayList<>());
        binding.homeSearchFlowLayout.setOnTagClickListener((view, position, parent) -> {
            KeyboardUtils.closeSoftInput(this_, binding.homeSearchView);
            if(!TextUtils.isEmpty(flowLayoutAdapter.getItem(position))) {
                SearchInfoEntity entity = new SearchInfoEntity(flowLayoutAdapter.getItem(position));
                homeSearchViewModel.saveHistorySearchCurr(entity);
            }
            binding.homeSearchView.setText(flowLayoutAdapter.getItem(position));
            gotoSearchSecond(flowLayoutAdapter.getItem(position));
            return true;
        });
        binding.homeSearchFlowLayout.setAdapter(flowLayoutAdapter);
        //历史记录
        adapter = new HomeSearchHistoryBindingAdapter(R.layout.home_search_history_item, new ArrayList<>());
        SearchHistoryItemDecoration searchHistoryItemDecoration = new SearchHistoryItemDecoration(this_, SearchHistoryItemDecoration.VERTICAL_LIST);
        binding.homeSearchHistoryLv.addItemDecoration(searchHistoryItemDecoration);
        binding.homeSearchHistoryLv.setNestedScrollingEnabled(false);
        binding.homeSearchHistoryLv.setSwipeItemClickListener((itemView, position) -> {
            binding.homeSearchView.setText(adapter.getItem(position).keyWord);
            gotoSearchSecond();
        });
        //删除历史记录
        binding.homeSearchHistoryLv.setSwipeItemLongClickListener((itemView, position) -> {
            SelectDialog.show(this_, "确定要删除该记录吗？","", (dialog, which) -> {
                homeSearchViewModel.deleteHistorySearchCurr(adapter.getItem(position));
                adapter.setSearchHistoryList(homeSearchViewModel.historySearch.get());
                setListState(adapter.getItemCount());
            });
        });
        binding.homeSearchHistoryLv.setAdapter(adapter);
        ScrollLinearLayoutManager scrollLinearLayoutManager = new ScrollLinearLayoutManager(this_);
        scrollLinearLayoutManager.setScrollEnabled(false);
        binding.homeSearchHistoryLv.setLayoutManager(scrollLinearLayoutManager);

        //清空历史记录
        binding.homeSearchDelete.setOnClickListener(v -> {
            SelectDialog.show(this_, "确定清空历史搜索吗？", "",  (dialog, which) -> {
                homeSearchViewModel.deleteHistorySearchCurr(adapter.getData());
                adapter.setSearchHistoryList(homeSearchViewModel.historySearch.get());
                setListState(adapter.getItemCount());
            });
        });

        //加载回调
        homeSearchViewModel.historySearch.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                adapter.setSearchHistoryList(homeSearchViewModel.historySearch.get());
                setListState(adapter.getItemCount());
            }
        });

        Looper.myQueue().addIdleHandler(() -> {
            addRunStatusChangeCallBack(homeSearchViewModel);
//            homeSearchViewModel.loadHotData();
            homeSearchViewModel.loadHistorySearch();
            return false;
        });
    }

    @Override
    protected void onDataResult(Object o) {
        SearchHotKey hotKey = (SearchHotKey)o;
        ((FlowLayoutAdapter)binding.homeSearchFlowLayout.getAdapter()).refreshData(hotKey.getHotKeyword());
    }

    private void setListState(int size) {
        if(size > 0) {
            binding.homeSearchDelete.setVisibility(View.VISIBLE);
            binding.hisotryLl.setVisibility(View.VISIBLE);
        } else {
            binding.homeSearchDelete.setVisibility(View.GONE);
            binding.hisotryLl.setVisibility(View.GONE);
        }
    }

    @Override
    protected void responseEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_CLOSE_SEARCH2.equals(event.msgType)) {
            boolean isClose = event.bundle != null ? event.bundle.getBoolean(CommEvent.KEY_EXTRA_VALUE) : false;
            if(isClose) {
                finish();
            }
        }
    }

    //侧滑删除
    SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.size_70dp);
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        SwipeMenuItem deleteItem = new SwipeMenuItem(this_)
                .setBackground(R.drawable.selector_red)
                .setText(getResources().getString(R.string.home_search_del))
                .setTextColor(Color.WHITE)
                .setWidth(width)
                .setHeight(height);
        swipeRightMenu.addMenuItem(deleteItem);
    };

    //侧滑删除
    SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
        menuBridge.closeMenu();
        int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
        if(adapter != null) adapter.notifyItemRemoved(adapterPosition);
    };

    private void gotoSearchSecond() {
        gotoSearchSecond("");
    }

    private void gotoSearchSecond(String searchId) {
        KeyboardUtils.closeSoftInput(this_, binding.homeSearchView);
        ARouter.getInstance()
                .build(ARouterPath.Search2Aty)
                .withString(CommKeyUtil.EXTRA_VALUE, TextUtil.isEmpty(searchId) ? inputValue : searchId)
                .navigation(this_);
    }
}

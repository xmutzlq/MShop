package google.architecture.common.base;

import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import google.architecture.common.R;
import google.architecture.common.util.ResourceUtil;
import google.architecture.common.widget.CustomLoadMoreView;
import google.architecture.common.widget.LoadMoreHelper;
import google.architecture.coremodel.datamodel.RefreshListModel;

/**
 * 分页列表
 * @author lq.zeng
 * @date 2018/9/26
 */

public abstract class BasePagingActivity<VB extends ViewDataBinding> extends BaseActivity<VB> {

    protected SwipeRefreshLayout swipeRefresh;
    protected RecyclerView recyclerView;
    protected BaseQuickAdapter adapter;
    protected PagingHelper pagingHelper;

    /**是否需要下拉刷新**/
    protected boolean isNeedRefreshing() {
        return true;
    }

    protected abstract BaseQuickAdapter getAdapter();
    protected abstract void prePareRecycleView();
    protected abstract void afterRecycleView();
    protected void onRefreshLoadingShown(){};

    public void setListViewModel(BaseListViewModel viewModel) {
        pagingHelper.setListViewModel(viewModel);
        addRunStatusChangeCallBack(viewModel);
        pagingHelper.listViewModel.ending.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(pagingHelper.listViewModel.ending.get()) {
                    if(adapter != null) adapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagingHelper = new PagingHelper();
        int swipeRefreshId = ResourceUtil.getId(this_, "id_swipe_refresh");
        int recyclerViewId = ResourceUtil.getId(this_, "id_recycler_view");
        if(swipeRefreshId == 0 || recyclerViewId == 0) {
            LogUtils.tag("zlq").e("swipeRefresh控件的ID必须配置：activity_swipe_refresh recyclerView控件的ID必须配置：activity_recycler_view");
            ViewManager.getInstance().finishActivity();
        }
        recyclerView = findViewById(recyclerViewId);
        swipeRefresh = findViewById(swipeRefreshId);
        swipeRefresh.setEnabled(isNeedRefreshing());
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(() -> {
            onRefreshLoadingShown();
            if(pagingHelper != null) pagingHelper.onRefresh();
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (LoadMoreHelper.isLoadMore(recyclerView, newState)
                        && pagingHelper != null) {
                    if(pagingHelper.getCurrentPage() == pagingHelper.getTotalPage()) {
                        if(adapter != null) adapter.loadMoreEnd();
                    } else {
                        pagingHelper.onLoadMore();
                    }
                }
            }
        });

        adapter = getAdapter();
        adapter.setOnLoadMoreListener(() -> {}, recyclerView);
        adapter.disableLoadMoreIfNotFullPage();
        adapter.setLoadMoreView(new CustomLoadMoreView()); // 加载更多的View

        ISetAdapter setAdapter = new RecyclerViewSetAdapter();
        InvocationHandler handler = new RecyclerViewProxy(setAdapter);
        ISetAdapter setAdapterProxy = (ISetAdapter) Proxy.newProxyInstance(handler.getClass().getClassLoader(), setAdapter.getClass().getInterfaces(), handler);
        setAdapterProxy.setAdapter();
    }

    @Override
    protected void onDataResult(Object o) {
        super.onDataResult(o);
        Looper.myQueue().addIdleHandler(() -> {
            if(swipeRefresh != null) swipeRefresh.setRefreshing(false);
            if(o instanceof RefreshListModel) {
                RefreshListModel refreshListModel = (RefreshListModel) o;
                if (refreshListModel != null) {
                    if (refreshListModel.isRefreshType()) {
                        if(adapter != null) adapter.setNewData(refreshListModel.list);
                        if(recyclerView != null) recyclerView.smoothScrollToPosition(0);
                        if(pagingHelper.isFull()) {
                            if(adapter != null) adapter.loadMoreEnd();
                        }
                    } else if (refreshListModel.isUpdateType()) {
                        if(refreshListModel.list == null || refreshListModel.list.size() == 0) {
                            if(adapter != null) adapter.loadMoreEnd();
                        } else {
                            if(adapter != null) adapter.addData(refreshListModel.list);
                        }
                    }
                }
            }
            return false;
        });
    }

    @Override
    protected void runStatusChange(boolean isRunning) {
        if(pagingHelper != null) pagingHelper.runStatusChange();
        if(pagingHelper != null && pagingHelper.isNormalRunStatus()) {
            super.runStatusChange(isRunning);
        } else {
            if(adapter != null) adapter.setEnableLoadMore(true);
        }
    }

    public class RecyclerViewProxy implements InvocationHandler {

        private ISetAdapter setAdapter;

        public RecyclerViewProxy(ISetAdapter setAdapter) {
            this.setAdapter = setAdapter;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            prePareRecycleView();
            method.invoke(setAdapter, args);
            afterRecycleView();
            return null;
        }
    }

    public class RecyclerViewSetAdapter implements ISetAdapter {

        @Override
        public void setAdapter() {
            recyclerView.setAdapter(adapter);
        }
    }

    public interface ISetAdapter {
        void setAdapter();
    }
}

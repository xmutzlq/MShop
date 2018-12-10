package google.architecture.common.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
 * @author lq.zeng
 * @date 2018/10/16
 */

public abstract class BasePagingFragment<VB extends ViewDataBinding> extends BaseFragment<VB> {
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

    public void setListViewModel(BaseListViewModel viewModel) {
        pagingHelper.setListViewModel(viewModel);
        addRunStatusChangeCallBack(viewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagingHelper = new PagingHelper();
        int swipeRefreshId = ResourceUtil.getId(mContext, "id_swipe_refresh");
        int recyclerViewId = ResourceUtil.getId(mContext, "id_recycler_view");
        if(swipeRefreshId == 0 || recyclerViewId == 0) {
            LogUtils.tag("zlq").e("swipeRefresh控件的ID必须配置：activity_swipe_refresh recyclerView控件的ID必须配置：activity_recycler_view");
            ViewManager.getInstance().finishActivity();
        }
        recyclerView = findViewById(view, recyclerViewId);
        swipeRefresh = findViewById(view, swipeRefreshId);
        swipeRefresh.setEnabled(isNeedRefreshing());
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(() -> {
            if(pagingHelper != null) pagingHelper.onRefresh();
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (LoadMoreHelper.isLoadMore(recyclerView, newState)) {
                    if(pagingHelper != null) pagingHelper.onLoadMore();
                }
            }
        });

        adapter = getAdapter();
        adapter.setOnLoadMoreListener(() -> {}, recyclerView);
        adapter.disableLoadMoreIfNotFullPage();
        adapter.setLoadMoreView(new CustomLoadMoreView());

        ISetAdapter setAdapter = new RecyclerViewSetAdapter();
        InvocationHandler handler = new RecyclerViewProxy(setAdapter);
        ISetAdapter setAdapterProxy = (ISetAdapter) Proxy.newProxyInstance(handler.getClass().getClassLoader(), setAdapter.getClass().getInterfaces(), handler);
        setAdapterProxy.setAdapter();
    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        if(swipeRefresh != null) swipeRefresh.setRefreshing(true);
        if(pagingHelper != null) pagingHelper.onRefresh();
    }

    @Override
    protected void onDataResult(Object o) {
        super.onDataResult(o);
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
    }

    @Override
    protected void runStatusChange(boolean isRunning) {
        LogUtils.tag("zlq").e("isRunning = " + isRunning);
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

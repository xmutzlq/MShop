package google.architecture.common.base;

import android.support.v4.widget.SwipeRefreshLayout;


/**
 * @author lq.zeng
 * @date 2018/9/26
 */

public class PagingHelper implements SwipeRefreshLayout.OnRefreshListener {

    protected BaseListViewModel listViewModel;

    protected boolean isStopRefresh;
    protected boolean isStopLoadMore;
    protected boolean isRefresh;

    public void setListViewModel(BaseListViewModel viewModel) {
        listViewModel = viewModel;
    }

    public BaseListViewModel getListViewModel() {
        return listViewModel;
    }

    @Override
    public void onRefresh() {
        if (listViewModel == null) {
            return;
        }
        if (isStopRefresh) {
            listViewModel.refreshing.set(false);
            return;
        }
        isRefresh = true;
        listViewModel.fetchData(isRefresh);
    }

    public void onLoadMore() {
        if (isStopLoadMore || listViewModel == null) {
            return;
        }
        isRefresh = false;
        listViewModel.fetchData(isRefresh);
    }

    public void runStatusChange() {
        if (listViewModel != null &&
                !listViewModel.isRunning.get()) { // 网络请求结束隐藏下拉刷新
            listViewModel.refreshing.set(false);
        }
    }

    public boolean isNormalRunStatus() {
        return isRefresh;
    }

    public boolean isFull() {
        return listViewModel.isFull();
    }
}

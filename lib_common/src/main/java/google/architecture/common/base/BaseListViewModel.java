package google.architecture.common.base;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import java.util.List;

import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;

public abstract class BaseListViewModel extends UIViewModel implements ErrorConsumer.IPresenter {

    protected static final int NORMAL_PAGE = 1;
    protected static final int PAGE_SIZE = 10;
    protected int pageTotal;
    protected int page = NORMAL_PAGE;
    protected boolean isRefresh;

    public ObservableBoolean refreshing = new ObservableBoolean(false); // 提供给下拉刷新

    public abstract void refreshData(boolean refresh);

    public BaseListViewModel() {
        super();
    }

    public boolean isFull() {
        return isRefresh && page == pageTotal;
    }

    @Override
    public void onPresenter(int code, String msg) {
        if(page > NORMAL_PAGE) page--;
    }

    public void fetchData(boolean refresh) {
        if (refresh) {
            if (isRunning.get()) {
                refreshing.set(false);
                return;
            }
            page = NORMAL_PAGE;
        } else {
            if (isRunning.get() || isEmpty.get() || page > pageTotal) {
                return;
            }
            page++;
        }
        setRefresh(refresh);
        refreshData(refresh);
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
        if (isRefresh) { // 网络请求开始显示下拉刷新
            refreshing.set(true);
        }
    }

    @Override
    protected <T> void setDataList(List<T> list, ObservableList<T> observableList) {
        if (observableList == null) {
            return;
        }
        checkEmpty(list);
        if (!isEmpty.get()) {
            if (isRefresh) {
                observableList.clear();
            }
            observableList.addAll(list);

            if (list.size() < PAGE_SIZE) {
                isEmpty.set(true);
            }
        }
    }
}

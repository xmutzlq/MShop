package google.architecture.common.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import google.architecture.common.base.BaseListViewModel;
import google.architecture.coremodel.data.SearchResult;
import google.architecture.coremodel.datamodel.RefreshListModel;
import google.architecture.coremodel.datamodel.db.entity.SearchInfoEntity;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/5/21
 */

public class HomeSearchViewModel extends BaseListViewModel {
    private static final int MAX_HISTORY = 10;

    public ObservableField<List<SearchInfoEntity>> historySearch = new ObservableField<>();
    //搜索过滤数据
    private int state = SearchResult.GoodsItem.ITEM_TYPE_LIST;
    private List<SearchResult.FilterContainer> filterData;
    private SearchResult.FilterResultData filterResultData;

    //搜索参数
    private SearchResultParams searchResultParams;

    public void loadSearchResultData(String search_id, String search_name) {
        searchResultParams = new SearchResultParams(search_id, search_name, "", "", "", "", new HashMap<>());
    }

    public void loadSearchResultData(String search_id, String search_name, String orderField,
                                     String orderDirection, String min_price, String max_price) {
        searchResultParams = new SearchResultParams(search_id, search_name, orderField, orderDirection, min_price, max_price, new HashMap<>());
    }

    public void loadSearchResultData(String search_id, String search_name, String orderField,
                                     String orderDirection, String min_price, String max_price, Map<String, String> otherParams) {
        searchResultParams = new SearchResultParams(search_id, search_name, orderField, orderDirection, min_price, max_price, otherParams);
    }

    public void loadHotData() {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getSearchHotKey().doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setDataObject(result.getData(), data)).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void loadHistorySearch() {
        final CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(DeHongDataRepository.get().loadSearchInfoEntity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(searchInfoEntityList -> {
                    disposable.clear();
                    Collections.reverse(searchInfoEntityList); //倒序
                    historySearch.set(searchInfoEntityList);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void saveHistorySearch(SearchInfoEntity entity) {
        Flowable.create(e -> {
            long num = DeHongDataRepository.get().insertSearchInfoEntity(entity);
            e.onComplete();
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {})
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public void saveHistorySearchCurr(SearchInfoEntity entity) {
        if(!historySearch.get().contains(entity)) {
            if(historySearch.get().size() == MAX_HISTORY) {
                SearchInfoEntity delEntity = historySearch.get().get(historySearch.get().size() - 1);
                deleteHistorySearchCurr(delEntity);
                historySearch.get().remove(delEntity);
            }
            saveHistorySearch(entity);
            historySearch.get().add(0, entity);
            historySearch.notifyChange();
        }
    }

    public void deleteHistorySearchCurr(SearchInfoEntity searchInfoEntity) {
        historySearch.get().remove(searchInfoEntity);
        Flowable.create(e -> {
            int num = DeHongDataRepository.get().deleteSearchInfoEntity(searchInfoEntity);
            e.onComplete();
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {})
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public void deleteHistorySearchCurr(List<SearchInfoEntity> searchInfoEntities) {
        historySearch.get().clear();
        Flowable.create(e -> {
            int num = DeHongDataRepository.get().deleteSearchInfoEntitys(searchInfoEntities);
            e.onComplete();
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {})
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    @Override
    public void refreshData(boolean refresh) {
        if(searchResultParams != null) {
            String search_id = searchResultParams.getSearch_id();
            String search_name = searchResultParams.getSearch_name();
            String orderField = searchResultParams.getOrderField();
            String orderDirection = searchResultParams.getOrderDirection();
            String min_price = searchResultParams.getMin_price();
            String max_price = searchResultParams.getMax_price();
            Map<String, String> otherParams = searchResultParams.getOtherParams();
            if (isRunning.get()) return;
            disposable.add(DeHongDataRepository.get().getSearchList(search_id, search_name, orderField,
                orderDirection, min_price, max_price, String.valueOf(page), String.valueOf(PAGE_SIZE), otherParams)
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    SearchResult searchResult = result.getData();
                    pageTotal = searchResult.getGoods_total();
                    filterData = searchResult.getScreening_conditions();
                    for (SearchResult.GoodsItem data : searchResult.getGoods_list()) {
                        data.setItemType(state);
                    }
                    RefreshListModel<SearchResult.GoodsItem> refreshListModel = new RefreshListModel<>();
                    if (refresh) {
                        refreshListModel.setRefreshType(searchResult.getGoods_list());
                    } else {
                        refreshListModel.setUpdateType(searchResult.getGoods_list());
                    }
                    if(refreshListModel.isRefreshType()) checkEmpty(result.getData().getGoods_list());
                    setDataObject(refreshListModel, data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer(this)));
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<SearchResult.FilterContainer> getFilterData() {
        return filterData;
    }

    public void setFilterData(List<SearchResult.FilterContainer> filterData) {
        this.filterData = filterData;
    }

    public SearchResult.FilterResultData getFilterResultData() {
        return filterResultData;
    }

    public void setFilterResultData(SearchResult.FilterResultData filterResultData) {
        this.filterResultData = filterResultData;
    }

    public static class SearchResultParams {
        private String search_id;
        private String search_name;
        private String orderField;
        private String orderDirection;
        private String min_price;
        private String max_price;
        private Map<String, String> otherParams;

        public SearchResultParams(String search_id, String search_name, String orderField,
                                  String orderDirection, String min_price, String max_price, Map<String, String> otherParams) {
            this.search_id = search_id;
            this.search_name = search_name;
            this.orderField = orderField;
            this.orderDirection = orderDirection;
            this.min_price = min_price;
            this.max_price = max_price;
            this.otherParams = otherParams;
        }

        public String getSearch_id() {
            return TextUtils.isEmpty(search_id) ? "" : search_id;
        }

        public void setSearch_id(String search_id) {
            this.search_id = search_id;
        }

        public String getSearch_name() {
            return TextUtils.isEmpty(search_name) ? "" : search_name;
        }

        public void setSearch_name(String search_name) {
            this.search_name = search_name;
        }

        public String getOrderField() {
            return TextUtils.isEmpty(orderField) ? "" : orderField;
        }

        public void setOrderField(String orderField) {
            this.orderField = orderField;
        }

        public String getOrderDirection() {
            return TextUtils.isEmpty(orderDirection) ? "" : orderDirection;
        }

        public void setOrderDirection(String orderDirection) {
            this.orderDirection = orderDirection;
        }

        public String getMin_price() {
            return TextUtils.isEmpty(min_price) ? "" : min_price;
        }

        public void setMin_price(String min_price) {
            this.min_price = min_price;
        }

        public String getMax_price() {
            return TextUtils.isEmpty(max_price) ? "" : max_price;
        }

        public void setMax_price(String max_price) {
            this.max_price = max_price;
        }

        public Map<String, String> getOtherParams() {
            return otherParams == null ? new HashMap<>() : otherParams;
        }

        public void setOtherParams(Map<String, String> otherParams) {
            this.otherParams = otherParams;
        }
    }
}

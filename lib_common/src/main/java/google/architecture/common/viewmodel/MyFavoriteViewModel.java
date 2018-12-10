package google.architecture.common.viewmodel;

import google.architecture.common.base.BaseListViewModel;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.data.FavoriteData;
import google.architecture.coremodel.datamodel.RefreshListModel;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/10/18
 */

public class MyFavoriteViewModel extends BaseListViewModel {

    public void getFavorites() {
        disposable.add(DeHongDataRepository.get().getFavorites(Account.get().getUserId(), String.valueOf(page), String.valueOf(PAGE_SIZE)).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setDataObject(result.getData(), data)).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    @Override
    public void refreshData(boolean refresh) {
        disposable.add(DeHongDataRepository.get().getFavorites(Account.get().getUserId(), String.valueOf(page), String.valueOf(PAGE_SIZE))
                .doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    pageTotal = result.getData().getTotalpage();
                    RefreshListModel<FavoriteData.FavoriteItem> refreshListModel = new RefreshListModel<>();
                    if (refresh) {
                        refreshListModel.setRefreshType(result.getData().getList());
                    } else {
                        refreshListModel.setUpdateType(result.getData().getList());
                    }
                    if(refreshListModel.isRefreshType()) checkEmpty(result.getData().getList());
                    setDataObject(refreshListModel, data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }


}

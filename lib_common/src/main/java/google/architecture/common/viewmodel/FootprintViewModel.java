package google.architecture.common.viewmodel;

import google.architecture.common.base.BaseListViewModel;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.data.FootprintInfo;
import google.architecture.coremodel.datamodel.RefreshListModel;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/10/19
 */

public class FootprintViewModel extends BaseListViewModel {

    public void getFootprints() {
        disposable.add(DeHongDataRepository.get().getFootprints(Account.get().getUserId(), String.valueOf(page), String.valueOf(PAGE_SIZE)).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setDataObject(result.getData(), data)).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    @Override
    public void refreshData(boolean refresh) {
        disposable.add(DeHongDataRepository.get().getFootprints(Account.get().getUserId(), String.valueOf(page), String.valueOf(PAGE_SIZE))
                .doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    pageTotal = result.getData().getTotalpage();
                    RefreshListModel<FootprintInfo> refreshListModel = new RefreshListModel<>();
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

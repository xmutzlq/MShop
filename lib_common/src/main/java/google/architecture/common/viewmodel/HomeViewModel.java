package google.architecture.common.viewmodel;

import google.architecture.common.base.BaseViewModel;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/7/31
 */

public class HomeViewModel extends BaseViewModel {
    public void getShoppingHome() {
        getShoppingHome(true);
    }

    public void getShoppingHome(boolean isNeedRunningState) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getShoppingHome().doOnSubscribe(disposable -> isRunning.set(isNeedRunningState))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}

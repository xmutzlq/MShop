package google.architecture.common.viewmodel;

import google.architecture.common.base.BaseViewModel;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/8/13
 */

public class UpdateViewModel extends BaseViewModel {

    public void getVersionInfo() {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getTVersionInfo().doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void getIndex() {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getBidData().doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}

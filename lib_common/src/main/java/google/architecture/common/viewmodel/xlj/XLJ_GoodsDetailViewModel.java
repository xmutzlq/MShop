package google.architecture.common.viewmodel.xlj;

import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

public class XLJ_GoodsDetailViewModel extends UIViewModel {
    public void getGoodsDetail(String requestJson) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().xlj_getGoodsDetail(requestJson)
                .doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}

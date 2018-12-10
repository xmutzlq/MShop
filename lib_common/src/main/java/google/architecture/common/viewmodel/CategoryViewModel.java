package google.architecture.common.viewmodel;

import google.architecture.common.base.BaseViewModel;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/7/24
 */

public class CategoryViewModel extends BaseViewModel {

    public void getShoppingCategory() {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getShoppingCategory().doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void getShoppingCategory(String parentId) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getShoppingCategoryRight(parentId).doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}

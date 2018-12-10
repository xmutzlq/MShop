package google.architecture.common.viewmodel;

import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/8/30
 */

public class CartViewModel extends UIViewModel {
    public void getCarts() {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getCarts(Account.get().getUserId()).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void updateCart(String ids, String goods_num, String selected, IDoOnNext doOnNext, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().cartUpdate(ids, Account.get().getUserId(), goods_num, selected), doOnNext, messageResult);
    }

    public void deleteCart(String ids, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().cartDelete(ids, Account.get().getUserId()), messageResult);
    }
}

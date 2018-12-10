package google.architecture.common.viewmodel;

import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/10/16
 */

public class MyOrderOperateViewModel extends UIViewModel {

    public void getMyOrderDetail(String orderId) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().orderDetail(Account.get().getUserId(), orderId)
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void cancelOrder(String orderId, String reason, IMessageResult messageResult) {
        disposable.add(subscribe(DeHongDataRepository.get().cancelOrder(Account.get().getUserId(), orderId, reason), messageResult));
    }

    public void deleteOrder(String orderId, IMessageResult messageResult) {
        disposable.add(subscribe(DeHongDataRepository.get().deleteOrder(Account.get().getUserId(), orderId), messageResult));
    }

    public void confirmReceipt(String orderId, IMessageResult messageResult) {
        disposable.add(subscribe(DeHongDataRepository.get().confirmReceipt(Account.get().getUserId(), orderId), messageResult));
    }

    public void remindingSend(String orderId, IMessageResult messageResult) {
        disposable.add(subscribe(DeHongDataRepository.get().remindingSend(Account.get().getUserId(), orderId), messageResult));
    }
}

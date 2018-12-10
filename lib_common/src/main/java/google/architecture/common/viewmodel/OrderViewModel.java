package google.architecture.common.viewmodel;

import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderViewModel extends UIViewModel{

    public void getCartSettlement(String from) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().cartSettlement(from, Account.get().getUserId())
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void directOrder(String goodsId, String itemsId, String goodsNum) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().directOrder("goods", goodsId, Account.get().getUserId(), itemsId, goodsNum)
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void submitOrderByCart(String addressId, String note, IDoOnNext doOnNext) {
        submitOrder("cart","", "", "", addressId, note, doOnNext);
    }

    public void submitOrder(String from, String goodsId, String itemsId, String goodsNum, String addressId, String noteJson, IDoOnNext doOnNext) {
        subscribe(DeHongDataRepository.get().submitOrder(from, goodsId, Account.get().getUserId(), itemsId, goodsNum, addressId, noteJson), doOnNext);
    }
}

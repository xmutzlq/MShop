package google.architecture.common.viewmodel;

import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/8/28
 */

public class DetailCommdityViewModel extends UIViewModel {

    public void getDetailCommdityInfo(String goodsId) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getDetailCommodity(goodsId, Account.get().getUserId())
                .doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void choiceGoodsSpec(String goodsId, String itemsId, IDoOnNext doOnNext) {
        subscribe(DeHongDataRepository.get().choiceGoodsSpec(goodsId, itemsId), doOnNext);
    }
}

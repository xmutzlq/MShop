package google.architecture.common.viewmodel;


import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/8/31
 */

public class DetailViewModel extends UIViewModel {
    public void checkFavorites(String goodsId, boolean hasFavorite, IDoOnNext doOnNext) {
        String actWay = hasFavorite ? "cancel" : "collect";
        subscribe(DeHongDataRepository.get().checkFavorites(goodsId, Account.get().getUserId(), actWay), doOnNext);
    }

    public void addCart(String goodsId, String itemIds, int goodsNum, IDoOnNext doOnNext, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().addCart(goodsId, Account.get().getUserId(), itemIds, String.valueOf(goodsNum)), doOnNext, messageResult);
    }

}

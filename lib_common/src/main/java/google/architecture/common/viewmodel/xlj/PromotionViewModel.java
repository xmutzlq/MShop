package google.architecture.common.viewmodel.xlj;

import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.coremodel.data.xlj.PromotionMedia;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.XLJ_HttpResult;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

public class PromotionViewModel extends UIViewModel {

    public void getPromotionMedia(IDoOnNext onCompleteListener){
        String requestJson = "{"
                +"\"appType\":\"android\","
                +"\"appToken\":\"y7w7jkt12E6I3BM9\","
                +"\"method\":\"Shops/getMedia\""
                +"}";
        subscribe(DeHongDataRepository.get().xlj_getPromotionMedia(requestJson), onCompleteListener);
        /*disposable.add(DeHongDataRepository.get().xlj_getPromotionMedia(requestJson).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {onCompleteListener.doOnNext(result.getData());})
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));*/
    }
}

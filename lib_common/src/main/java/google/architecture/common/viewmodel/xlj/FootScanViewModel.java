package google.architecture.common.viewmodel.xlj;

import java.util.HashMap;
import java.util.Map;

import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

public class FootScanViewModel extends UIViewModel {

    public void getUserToken(String phoneNum){
        Map<String, String> map = new HashMap<>();
        map.put("phoneNum","11111111111");
        disposable.add(DeHongDataRepository.get().xlj_getFootScanToken(map).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    setDataObject(result.getData(), data);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

}

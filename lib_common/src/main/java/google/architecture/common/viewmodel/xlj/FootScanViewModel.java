package google.architecture.common.viewmodel.xlj;

import java.util.HashMap;
import java.util.Map;

import google.architecture.common.base.BaseApplication;
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

    public void getTryData(){
        String requestJson = "{" +
                "\"appType\":\"android\"," +
                "\"appToken\":\"y7w7jkt12E6I3BM9\"," +
                "\"method\":\"TryApi/getTryData\"," +
                "\"wxunId\":\""+ BaseApplication.getIns().getmUserAccessToken()+"\"," +
                "\"mobile\":\""+BaseApplication.getIns().getmScPhoneNum()+"\"" +
                "}";
        disposable.add(DeHongDataRepository.get().xlj_getTryData(requestJson).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    setDataObject(result.getData(), data);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

}

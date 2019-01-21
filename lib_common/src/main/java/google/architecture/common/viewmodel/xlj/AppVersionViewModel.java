package google.architecture.common.viewmodel.xlj;

import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

public class AppVersionViewModel extends UIViewModel {

    public void getAppVersionInfo(IDoOnNext onCompleteListener){
        String requestJson = "{"
                +"\"appType\":\"android\","
                +"\"appToken\":\"y7w7jkt12E6I3BM9\","
                +"\"method\":\"Shops/getMedia\""
                +"}";
        subscribe(DeHongDataRepository.get().xlj_getAppVersionInfo(requestJson),onCompleteListener);
    }

}

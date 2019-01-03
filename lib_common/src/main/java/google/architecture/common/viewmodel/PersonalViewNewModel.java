package google.architecture.common.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import google.architecture.coremodel.data.xlj.personal.UserInfos;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

public class PersonalViewNewModel extends UIViewModel {

    public ObservableField<UserInfos> userInfosObservableField = new ObservableField<>();

    public void loadData(){
        String wxUnionId = "95131c72-24cf-4981-abd9-0ee5b9e177";
        String method = "Login/appWxlogin";
        disposable.add(DeHongDataRepository.get().xlj_getUserToken(wxUnionId, method).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> loginToAccount(result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void loginToAccount(String userToken) {
        disposable.add(DeHongDataRepository.get().xlj_getUserInfo(userToken, "Users/index")
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> refreshUserInfo(result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void refreshUserInfo(UserInfos userInfos) {
        userInfosObservableField.set(userInfos);
    }

    public void uploadDeviceInfo(String deviceToken ,String uniqueId,String deviceName){
        String requestJson = "{\"appType\":\"android\",\"appToken\":\"y7w7jkt12E6I3BM9\",\"method\":\"Goods/setDvToken\","
                +"\"name\":\""+deviceName+"\","
                +"\"onlySign\":\""+uniqueId+"\","
                +"\"dvToken\":\""+deviceToken+"\""
                +"}";
        disposable.add(DeHongDataRepository.get().xlj_uploadDeviceInfo(requestJson).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext((result) -> {
                    setDataObject(result, data);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer())
        );
    }

}

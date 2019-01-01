package google.architecture.common.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import google.architecture.coremodel.data.xlj.personal.UserInfos;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

public class PersonalViewNewModel extends UIViewModel {

    public ObservableField<UserInfos> userInfos = new ObservableField<>();

    public void loadData(){
        String wxUnionId = "95131c72-24cf-4981-abd9-0ee5b9e177";
        String method = "Login/appWxlogin";
        subscribe(DeHongDataRepository.get().xlj_getUserToken(wxUnionId, method), new IDoOnNext() {
            @Override
            public void doOnNext(Object t) {
                String userToken = t.toString();
                if(!TextUtils.isEmpty(userToken)){
                    subscribe(DeHongDataRepository.get().xlj_getUserInfo(userToken, "Users/index"), new IDoOnNext() {
                        @Override
                        public void doOnNext(Object t) {
                            UserInfos infos = (UserInfos)t;
                            userInfos.set(infos);
                        }
                    });
                }
            }
        });
    }

}

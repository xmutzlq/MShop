package google.architecture.common.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.util.ToastUtils;
import google.architecture.coremodel.data.xlj.TecentAccessToken;
import google.architecture.coremodel.data.xlj.TecentResponseResult;
import google.architecture.coremodel.data.xlj.TecentTicket;
import google.architecture.coremodel.data.xlj.personal.UserInfos;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import google.architecture.coremodel.util.PreferencesUtils;

public class PersonalViewNewModel extends UIViewModel {

    public static final String TecentTicket = "TecentTicket";
    private static final String TecentTicket_Expires_In = "TecentTicket_Expires_In"; //保存获取时间

    public ObservableField<UserInfos> userInfosObservableField = new ObservableField<>();
    private INotifyQrCodeState notifyQrCodeState;

    public void setNotifyQrCodeState(INotifyQrCodeState notifyQrCodeState) {
        this.notifyQrCodeState = notifyQrCodeState;
    }

    public void loadData(){
        String wxUnionId = BaseApplication.getIns().getmUserAccessToken();
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
                .subscribe(new EmptyConsumer(), new ErrorConsumer((code, msg) -> {
                    ToastUtils.showShortToast(msg);
                })));
    }

    private void refreshUserInfo(UserInfos userInfos) {
        BaseApplication.getIns().setUserInfos(userInfos);
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

    public void getTecentAccessToken(Map params){
        DeHongDataRepository.get().xlj_getTecentAccessToken(params).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    TecentAccessToken token = result;
                    String tencentTicket = PreferencesUtils.getString(BaseApplication.getIns(), TecentTicket);
                    long tencentTicketExp = PreferencesUtils.getLong(BaseApplication.getIns(), TecentTicket_Expires_In);
                    if(!TextUtils.isEmpty(tencentTicket) && tencentTicketExp > 0) { //存在TecentTicket并且存有时间
                        if((System.currentTimeMillis() - tencentTicketExp) / 1000 > 7200) { //(超过存在时间，保存)单位是秒
                            getTecentTicket(token.getAccessToken());
                        } else {
                            setDataObject(token, data);
                        }
                    } else { //不存在TecentTicket，保存
                        getTecentTicket(token.getAccessToken());
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public void getTecentTicket(String accessToken){
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken);
        params.put("type","2");
        DeHongDataRepository.get().xlj_getTecentTicket(params).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    TecentTicket ticket = result;
                    PreferencesUtils.putLong(BaseApplication.getIns(), TecentTicket_Expires_In, System.currentTimeMillis());
                    PreferencesUtils.putString(BaseApplication.getIns(), TecentTicket, ticket.getTicket());
                    setDataObject(ticket, data);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public void getTencentWxOpenId(String appid, String secret, String code, IDoOnNext doOnNext) {
        Map<String,String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("code", code);
        params.put("grant_type","authorization_code");
        DeHongDataRepository.get().xlj_getTecentWXOpenId(params).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    TecentResponseResult tecentResponseResult = result;
                    if(!TextUtils.isEmpty(tecentResponseResult.getUnionid())) {
                        BaseApplication.getIns().setmUserAccessToken(tecentResponseResult.getUnionid());
                        if(doOnNext != null) doOnNext.doOnNext(tecentResponseResult);
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    /**检测Ticket是否过期**/
    public boolean isTicketExp() {
        String tencentTicket = PreferencesUtils.getString(BaseApplication.getIns(), TecentTicket);
        long tencentTicketExp = PreferencesUtils.getLong(BaseApplication.getIns(), TecentTicket_Expires_In);
        if(TextUtils.isEmpty(tencentTicket) || (System.currentTimeMillis() - tencentTicketExp) / 1000 > 7200) {
            return true;
        }
        return false;
    }

    public interface INotifyQrCodeState {
        void notifyQrCodeState(int state);
    }
}

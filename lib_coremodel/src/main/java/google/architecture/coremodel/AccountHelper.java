package google.architecture.coremodel;

import com.apkfuns.logutils.LogUtils;

import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.coremodel.datamodel.db.entity.UserInfoEntity;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import google.architecture.coremodel.util.PreferencesUtils;
import google.architecture.coremodel.util.Utils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/4/17
 */

public final class AccountHelper {
    private static final String TAG = "AccountHelper";

    public static final String OPEN_ID = "openId";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String ACESS_TOKEN = "acess_token";

    private AccountHelper() {
    }

    public static String getOpenId() {
        return PreferencesUtils.getString(Utils.getContext(), OPEN_ID, "");
    }

    public static String getUserId() {
        return PreferencesUtils.getString(Utils.getContext(), USER_ID, "");
    }

    public static String getUserName() {
        return PreferencesUtils.getString(Utils.getContext(), USER_NAME, "");
    }

    public static String getAcessToken() {
        return PreferencesUtils.getString(Utils.getContext(), ACESS_TOKEN, "");
    }

    public static void initUserInfo() {
        final CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(DeHongDataRepository.get().loadUserInfoEntity(UserInfoEntity.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.e(TAG,
                        "initUserInfo doOnError: " + throwable.toString()))
                .doOnNext(userInfoEntity -> {
                    disposable.clear();
                    if(Utils.isMainThread()) {
                        LogUtils.tag("zlq").e("initUserInfo = isMainThread");
                    }
                    if (userInfoEntity != null && userInfoEntity.userInfo != null) {
                        Account.get().setUserInfoToLocal(userInfoEntity.userInfo);
                    }
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public static void setUserInfoToLocal(S_UserInfo userInfo) {
        Flowable.create(e -> {
            UserInfoEntity userInfoEntity = new UserInfoEntity(UserInfoEntity.ID, userInfo);
            DeHongDataRepository.get().insertUserInfoEntity(userInfoEntity);
            e.onComplete();
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.e(TAG,
                        "setUserInfoToLocal doOnError: " + throwable.toString()))
                .doOnComplete(() -> {})
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public static void clearUserInfo() {
        setUserInfoToLocal(null);
    }

    public static void setOpenIdToLocal(String openId) {
        PreferencesUtils.putString(Utils.getContext(), OPEN_ID, openId);
    }

    public static void setUserIdToLocal(String userId) {
        PreferencesUtils.putString(Utils.getContext(), USER_ID, userId);
    }

    public static void setUserNameToLocal(String userName) {
        PreferencesUtils.putString(Utils.getContext(), USER_NAME, userName);
    }

    public static void setAcessTokenToLocal(String acessToken) {
        PreferencesUtils.putString(Utils.getContext(), ACESS_TOKEN, acessToken);
    }
}

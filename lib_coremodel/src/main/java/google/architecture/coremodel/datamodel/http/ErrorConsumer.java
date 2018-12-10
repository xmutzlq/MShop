package google.architecture.coremodel.datamodel.http;

import com.apkfuns.logutils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.datamodel.http.exception.ApiException;
import google.architecture.coremodel.datamodel.http.exception.CodeException;
import google.architecture.coremodel.datamodel.http.exception.FactoryException;
import io.reactivex.annotations.NonNull;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Consumer;

/**
 * @author lq.zeng
 * @date 2018/4/8
 */

public class ErrorConsumer implements Consumer<Throwable> {

    public WeakReference<IPresenter> presenter;

    public ErrorConsumer() {

    }

    public ErrorConsumer(IPresenter presenter) {
        this.presenter = new WeakReference<>(presenter);
    }

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        if(throwable != null && throwable instanceof CompositeException){
            CompositeException compositeException = (CompositeException) throwable;
            throwable = compositeException.getExceptions().get(0);
        }
        LogUtils.tag("zlq").e("throwable = " + throwable.getMessage());
        ApiException exception = FactoryException.analysisExcetpion(throwable);
        LogUtils.tag("zlq").e("exception = " + exception.getDisplayMessage());
        if(presenter != null && presenter.get() != null) {
            presenter.get().onPresenter(exception.getCode(), exception.getDisplayMessage());
        }
        //TOKEN过时
        if(CodeException.IServerCode.ERROR_TOKEN_EXPIRED == exception.getCode()) {
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_TOKEN_EXPIRE));
        }
        EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_CHECK_NET));
    }

    public interface IPresenter {
        void onPresenter(int code, String msg);
    }
}

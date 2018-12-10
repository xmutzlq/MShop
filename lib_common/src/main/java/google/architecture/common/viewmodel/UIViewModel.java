package google.architecture.common.viewmodel;

import android.text.TextUtils;

import google.architecture.common.base.BaseViewModel;
import google.architecture.common.util.ToastUtils;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.HttpResult;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * @author lq.zeng
 * @date 2018/4/12
 */

public class UIViewModel extends BaseViewModel implements  EmptyConsumer.IPresenter, ErrorConsumer.IPresenter{

    public IMessageResult messageResult;

    /**只弹结果**/
    public Disposable subscribe(Flowable flowable) {
        return subscribe(flowable, null, null);
    }

    /**只弹结果并回调[HttpResult中data!=null情况]**/
    public Disposable subscribe(Flowable flowable, final IDoOnNext doOnNext ) {
        return subscribe(flowable, doOnNext, null);
    }

    /**只弹结果并回调[HttpResult中data==null的情况]**/
    public Disposable subscribe(Flowable flowable, final IMessageResult messageResult ) {
        return subscribe(flowable, null, messageResult);
    }

    /**
     * 弹结果并回调[HttpResult中data!=null情况]，与消息结果[HttpResult中data==null的情况]
     * @param flowable
     * @param doOnNext
     * @return Disposable
     */
    public Disposable subscribe(Flowable flowable, final IDoOnNext doOnNext, final IMessageResult messageResult) {
        this.messageResult = messageResult;
        return flowable.doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(o -> handlerNext(o, doOnNext))
                .subscribe(new EmptyConsumer(this), new ErrorConsumer(this));
    }

    private void handlerNext(final Object o, final IDoOnNext doOnNext) {
        isRunning.set(false);
        clear();
        if(doOnNext != null) {
            doOnNext.doOnNext(((HttpResult)o).getData());
        }
    }

    @Override
    public void onPresenter(int code, String msg) {
        isRunning.set(false);
        clear();
        if(!TextUtils.isEmpty(msg)
                /*&& (disposable != null && !disposable.isDisposed() || disposableMap != null && disposableMap.size() > 0)*/) {
            ToastUtils.showShortToast(msg);
        }
        if(messageResult != null
                /*&& (disposable != null && !disposable.isDisposed() || disposableMap != null && disposableMap.size() > 0)*/) { //请求成功的情况
            messageResult.onMessageResult(code, msg);
        }
    }

    @Override
    public void onSuccessPresenter(int code, String msg) {
        if(!TextUtils.isEmpty(msg) && !msg.equals("success")
                /* && (disposable != null && !disposable.isDisposed() || disposableMap != null && disposableMap.size() > 0)*/) {
            ToastUtils.showShortToast(msg);
        }
    }

    public interface IDoOnNext {
        void doOnNext(Object t);
    }

    public interface IMessageResult {
        void onMessageResult(int code, String msg);
    }
}

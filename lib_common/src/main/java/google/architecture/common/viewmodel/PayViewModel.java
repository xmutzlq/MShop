package google.architecture.common.viewmodel;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.apkfuns.logutils.LogUtils;

import java.util.Map;

import google.architecture.common.pay.ali.PayResult;
import google.architecture.common.util.ToastUtils;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.exception.ExceptionFunc;
import google.architecture.coremodel.datamodel.http.exception.ServerException;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import google.architecture.coremodel.util.TextUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/9/11
 */

public class PayViewModel extends UIViewModel {

    public void aliPay(Activity activity, String orderInfo) {
        if (isRunning.get()) return;
        Observable payObservable = Observable.create(e -> {
            Map<String, String> result = new PayTask(activity).payV2(orderInfo, true);
            e.onNext(result);
        });

        disposable.add(
                payObservable.subscribeOn(Schedulers.io())
                // Http、Json及其他异常捕获通知
                .onErrorResumeNext(new ExceptionFunc())
                // 后台异常捕获通知
                .map(o -> {
                    Map<String, String> result = (Map<String, String>)o;
                    PayResult payResult = new PayResult(result);
                    if(!TextUtils.isEmpty(payResult.getResult())) {
                        Log.e("zlq","payResult = " + payResult.toString());
                    }
                    if(!TextUtil.isEmpty(payResult.getResultStatus())
                            && Integer.valueOf(payResult.getResultStatus()) == RESULT_CODE_SUCCESS) {
                        return payResult;
                    }
                    throw new ServerException(payResult.getResultStatus(), payResult.getMemo());
                })
                .observeOn(AndroidSchedulers.mainThread())
                // App内部查看的日志
                .doOnError(throwable -> LogUtils.tag("zlq").e("doOnError: " + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    setDataObject(result, data);
                }).subscribe(new EmptyConsumer(), throwable -> {
                    ToastUtils.showShortToast("支付失败");
                }));
    }

    public void payOrder(String orderNo, String orderId, String code, IDoOnNext doOnNext) {
        subscribe(DeHongDataRepository.get().payOrder(Account.get().getUserId(), orderNo, orderId, code), doOnNext);
    }

    public void getPayWay(String orderNo, String orderId, IDoOnNext doOnNext) {
        subscribe(DeHongDataRepository.get().payWay(Account.get().getUserId(), orderNo, orderId), doOnNext);
    }

    private static final int RESULT_CODE_SUCCESS = 9000; //订单支付成功
    private static final int RESULT_CODE_ORDER_HANDLING = 8000; //正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
    private static final int RESULT_CODE_ORDER_FAULT = 4000; //订单支付失败
    private static final int RESULT_CODE_REPEAT_REQUEST = 5000; //重复请求
    private static final int RESULT_CODE_CANCEL = 6001; //用户中途取消
    private static final int RESULT_CODE_NET_ERROR = 6002; //网络连接出错
    private static final int RESULT_CODE_PAY_RESULT_ERROR = 6004; //支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
    //其它支付错误
}

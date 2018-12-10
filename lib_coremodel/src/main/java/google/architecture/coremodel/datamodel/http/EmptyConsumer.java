package google.architecture.coremodel.datamodel.http;

import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author lq.zeng
 * @date 2018/4/8
 */

public class EmptyConsumer implements Consumer<Object> {

    public WeakReference<IPresenter> presenter;

    public EmptyConsumer() {}

    public EmptyConsumer(IPresenter presenter) {
        this.presenter = new WeakReference<>(presenter);
    }

    @Override
    public void accept(@NonNull Object object) throws Exception {
        if(object != null && object instanceof HttpResult) {
            String code = ((HttpResult) object).getCode();
            String info = ((HttpResult) object).getInfo();
            if(TextUtils.isEmpty(((HttpResult) object).getInfo())) {
                LogUtils.tag("zlq").e("getInfo = " + ((HttpResult) object).getInfo());
            }
            if(presenter != null && presenter.get() != null) {
                int resultCode = TextUtils.isEmpty(code) ? 0 : Integer.valueOf(code);
                presenter.get().onSuccessPresenter(resultCode, info);
            }
        }
    }

    public interface IPresenter {
        void onSuccessPresenter(int code, String msg);
    }
}

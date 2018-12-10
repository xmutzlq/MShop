package google.architecture.coremodel.datamodel.http.exception;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 主要是将异常信息转化为用户”能看懂”的友好提示
 * @author lq.zeng
 * @date 2018/4/10
 */

public class ExceptionFunc implements Function<Throwable, Observable> {

    @Override
    public Observable apply(Throwable throwable) {
        return Observable.error(throwable);
    }
}

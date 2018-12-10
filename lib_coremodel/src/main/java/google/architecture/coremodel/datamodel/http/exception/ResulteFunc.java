package google.architecture.coremodel.datamodel.http.exception;

import google.architecture.coremodel.datamodel.http.HttpResult;
import io.reactivex.functions.Function;

/**
 * 拦截固定格式的公共数据类型HttpResult<T>,判断里面的状态码
 * @author lq.zeng
 * @date 2018/4/12
 */

public class ResulteFunc implements Function<HttpResult, HttpResult> {
    @Override
    public HttpResult apply(HttpResult tHttpResult) {
        if(tHttpResult.isOK()) {
            return tHttpResult;
        }
        throw new ServerException(tHttpResult.getCode(), tHttpResult.getInfo());
    }
}

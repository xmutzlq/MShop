package google.architecture.coremodel.datamodel.http.exception;

import google.architecture.coremodel.datamodel.http.HttpResult;
import google.architecture.coremodel.datamodel.http.XLJ_HttpResult;
import io.reactivex.functions.Function;

public class XLJ_ResultFunc implements Function<XLJ_HttpResult, XLJ_HttpResult> {
    @Override
    public XLJ_HttpResult apply(XLJ_HttpResult tHttpResult) {
        if(tHttpResult.isOK()) {
            return tHttpResult;
        }
        throw new ServerException(tHttpResult.getCode(), tHttpResult.getInfo());
    }
}
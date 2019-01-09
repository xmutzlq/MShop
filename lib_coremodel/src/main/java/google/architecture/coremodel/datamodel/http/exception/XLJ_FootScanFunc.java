package google.architecture.coremodel.datamodel.http.exception;

import google.architecture.coremodel.datamodel.http.XLJ_HttpResult;
import io.reactivex.functions.Function;

public class XLJ_FootScanFunc implements Function<XLJ_HttpResult, XLJ_HttpResult> {
    @Override
    public XLJ_HttpResult apply(XLJ_HttpResult xlj_httpResult) throws Exception {
        return xlj_httpResult;
    }
}

package google.architecture.coremodel.datamodel.http.exception;

import google.architecture.coremodel.data.xlj.TecentResponseResult;
import google.architecture.coremodel.data.xlj.TecentTicket;
import io.reactivex.functions.Function;

public class XLJ_TecentResultFunc implements Function<TecentResponseResult,TecentResponseResult> {
    @Override
    public TecentResponseResult apply(TecentResponseResult tecentTicket) throws Exception {
        return tecentTicket;
    }
}

package google.architecture.coremodel.datamodel.http.exception;

import google.architecture.coremodel.data.xlj.TecentAccessToken;
import io.reactivex.functions.Function;

public class XLJ_TecentAccessTokenFunc implements Function<TecentAccessToken,TecentAccessToken> {
    @Override
    public TecentAccessToken apply(TecentAccessToken tecentAccessToken) throws Exception {
        return tecentAccessToken;
    }
}

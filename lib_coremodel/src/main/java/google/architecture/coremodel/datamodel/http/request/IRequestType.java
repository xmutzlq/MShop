package google.architecture.coremodel.datamodel.http.request;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public interface IRequestType {
    RequestBody makeRequest(FormBody formBody);
}

package google.architecture.coremodel.datamodel.http.request;

import android.text.TextUtils;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PayloadRequest implements IRequestType {

    @Override
    public RequestBody makeRequest(FormBody formBody) {
        StringBuilder encodedParams = new StringBuilder();

        for (int i = 0; i < formBody.size(); i++) {
            if(!TextUtils.isEmpty(formBody.name(i))) {
                encodedParams.append(formBody.encodedName(i));
            }
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), encodedParams.toString());
        return body;
    }

}

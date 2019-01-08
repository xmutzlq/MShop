package google.architecture.coremodel.datamodel.http.request;

import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author lq.zeng
 * @date 2019/1/8
 */

public class TextPlainRequest implements IRequestType {

    @Override
    public RequestBody makeRequest(FormBody formBody) {
        StringBuilder encodedParams = new StringBuilder();

        for (int i = 0; i < formBody.size(); i++) {
            if(!TextUtils.isEmpty(formBody.name(i))) {
                encodedParams.append(formBody.value(i));
            }
        }
        LogUtils.tag("zlq").e("text_param = " + encodedParams.toString());
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), encodedParams.toString());
        return body;
    }
}

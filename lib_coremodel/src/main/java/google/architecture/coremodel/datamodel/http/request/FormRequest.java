package google.architecture.coremodel.datamodel.http.request;

import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import google.architecture.coremodel.util.DESSecretUtils;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 表单请求
 */
public class FormRequest implements IRequestType {

    @Override
    public RequestBody makeRequest(FormBody formBody) {
        StringBuilder sb = new StringBuilder();

        // 构造加密串
        StringBuilder encodedParams = new StringBuilder();

        for (int i = 0; i < formBody.size(); i++) {
            if(!TextUtils.isEmpty(formBody.name(i))) {
                encodedParams.append(formBody.encodedName(i));
            }
            encodedParams.append('=');
            if(!TextUtils.isEmpty(formBody.value(i))) {
                encodedParams.append(formBody.encodedValue(i));
            }
            encodedParams.append('&');
        }
        LogUtils.tag("zlq").e("encodedParams = " + encodedParams.substring(0, encodedParams.length() - 1).toString());
        String secret = DESSecretUtils.AES_cbc_encrypt(encodedParams.substring(0, encodedParams.length() - 1).toString());
        LogUtils.tag("zlq").e("local_secret = " + secret);
        sb.append("param=").append(secret);
        LogUtils.tag("zlq").e("param = " + secret);
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), sb.toString());
        return body;
    }
}

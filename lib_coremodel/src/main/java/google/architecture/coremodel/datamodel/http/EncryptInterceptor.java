package google.architecture.coremodel.datamodel.http;

import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;
import java.util.List;

import google.architecture.coremodel.datamodel.http.request.IRequestType;
import google.architecture.coremodel.datamodel.http.request.RequestFactory;
import google.architecture.coremodel.util.DESSecretUtils;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 加密拦截器
 * @author lq.zeng
 * @date 2018/4/9
 */

public class EncryptInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("GET")) {
        } else if (request.method().equals("POST")) {
            if (request.body() instanceof FormBody) {
                String requestType = RequestFactory.TYPE_FORM;
                FormBody formBody = (FormBody) request.body();
                List<String> headerValues = request.headers(ApiConstants.HEADER_USE_JSON_REQUEST_prefix);
                if (headerValues != null && headerValues.size() > 0) {
                    String headerValue = headerValues.get(0);
                    if (ApiConstants.HEADER_USE_JSON_REQUEST_VALUE.equals(headerValue)) {
                        requestType = headerValue;
                    }
                }
                IRequestType requestResult = RequestFactory.makeRequest(requestType);
                request = request.newBuilder()
                        .post(requestResult.makeRequest(formBody))
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent",getUserAgent())
                        .build();
            }
        }
        return chain.proceed(request);
    }

    /**
     * 返回正确的UserAgent
     * @return
     */
    private  static String getUserAgent(){
        String userAgent = "";
        StringBuffer sb = new StringBuffer();
        userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        LogUtils.tag("zlq").e("User-Agent","User-Agent: "+ sb.toString());
        return sb.toString();
    }
}

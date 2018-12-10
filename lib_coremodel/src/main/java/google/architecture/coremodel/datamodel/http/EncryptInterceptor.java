package google.architecture.coremodel.datamodel.http;

import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;

import google.architecture.coremodel.util.DESSecretUtils;
import okhttp3.FormBody;
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
                //FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();
                // 构造加密串
                StringBuilder encodedParams = new StringBuilder();
                for (int i = 0; i < formBody.size(); i++) {
                    if(!TextUtils.isEmpty(formBody.name(i))) {
                        encodedParams.append(formBody.encodedName(i));
                    }
                    encodedParams.append('=');
                    if(!TextUtils.isEmpty(formBody.value(i))) {
//                        if(request.headers().names().contains(ApiConstants.HEADER_UN_EN_PARAMS_prefix)) {
//                            LogUtils.tag("zlq").e("params normal");
//                            encodedParams.append(formBody.value(i));
//                        } else {
//                            LogUtils.tag("zlq").e("params encode");
                            encodedParams.append(formBody.encodedValue(i));
//                        }
                    }
                    encodedParams.append('&');
                }
                LogUtils.tag("zlq").e("encodedParams = " + encodedParams.substring(0, encodedParams.length() - 1).toString());
                String secret = DESSecretUtils.AES_cbc_encrypt(encodedParams.substring(0, encodedParams.length() - 1).toString());
                LogUtils.tag("zlq").e("local_secret = " + secret);
                // 正常使用FormBody后台收不到
                //formBody = bodyBuilder.addEncoded("param=", secret).build();
                // 所以使用RequestBody[application/x-www-form-urlencoded]
                StringBuilder sb = new StringBuilder("param=").append(secret);
                LogUtils.tag("zlq").e("param = " + secret);
                RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), sb.toString());
                request = request.newBuilder().post(body).removeHeader("User-Agent").addHeader("User-Agent",getUserAgent())
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

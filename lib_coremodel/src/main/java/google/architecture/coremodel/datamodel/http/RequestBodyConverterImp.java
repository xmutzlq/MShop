package google.architecture.coremodel.datamodel.http;

import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import google.architecture.coremodel.util.DESSecretUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * 加密
 * @param <T>
 */
public class RequestBodyConverterImp<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    RequestBodyConverterImp(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        String json = value.toString().replaceAll(", ",",");
        json = json.replaceAll("\\{", "{\"");
        json = json.replaceAll("[=]", "\"=\"").replaceAll(",","\",\"");
        json = json.replaceAll("[}]", "\"}");
        LogUtils.tag("zlq").e("json_ = " + json);
        StringBuilder encodedParams = new StringBuilder();
        Map<String, String> stringMap = gson.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            if(!TextUtils.isEmpty(entry.getKey())) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), UTF_8.toString()));
            }
            encodedParams.append('=');
            if(!TextUtils.isEmpty(entry.getValue())) {
                encodedParams.append(URLEncoder.encode(entry.getValue(), UTF_8.toString()));
            }
            encodedParams.append('&');
        }
        try {
            json = new StringBuffer("param=").append(DESSecretUtils.AES_cbc_encrypt(encodedParams.toString())).toString();
            LogUtils.tag("zlq").e("json = " + json);
        } catch (Exception e) {}
        return RequestBody.create(MEDIA_TYPE, json);
    }
}

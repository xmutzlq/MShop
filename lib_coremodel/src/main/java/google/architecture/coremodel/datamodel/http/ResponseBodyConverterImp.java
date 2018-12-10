package google.architecture.coremodel.datamodel.http;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import google.architecture.coremodel.util.DESSecretUtils;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 解密
 * @param <T>
 */
public class ResponseBodyConverterImp<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    ResponseBodyConverterImp(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String response = value.string();
            LogUtils.tag("zlq").e("remote_secret = " + response);
            response = DESSecretUtils.AES_cbc_decrypt(response);
            LogUtils.tag("zlq").e("decrypt_data = " + response);
            return adapter.fromJson(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if(value != null) value.close();
        }
    }
}

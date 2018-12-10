package google.architecture.coremodel.datamodel.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author lq.zeng
 * @date 2018/4/8
 */
public class GsonFactory extends Converter.Factory {
    public static GsonFactory create() {
        return create(new GsonBuilder().setLenient().create());
    }

    public static GsonFactory create(Gson gson) {
        if (gson == null)
            throw new NullPointerException("gson == null");
        return new GsonFactory(gson);
    }

    private final Gson gson;

    private GsonFactory(Gson gson) {
        this.gson = gson;
    }

    /**
     * 主要用于响应体的处理，Factory中默认实现为返回null，表示不处理
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new ResponseBodyConverterImp<>(gson, adapter); // 响应
    }

    /**
     * 主要用于请求体的处理，Factory中默认实现为返回null，不能处理返回null
     * 作用对象Part、PartMap、Body
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new RequestBodyConverterImp<>(gson, adapter); // 请求
    }
}

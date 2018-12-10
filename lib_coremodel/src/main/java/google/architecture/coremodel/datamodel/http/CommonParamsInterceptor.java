package google.architecture.coremodel.datamodel.http;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公共参数拦截器
 * @author lq.zeng
 * @date 2018/4/9
 */
public class CommonParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("GET")) {
        } else if (request.method().equals("POST")) {
            if (request.body() instanceof FormBody) {
                PostValues postValues = new PostValues();
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();
                // 其他参数
                for (int i = 0; i < formBody.size(); i++) {
                    postValues.put(formBody.name(i), formBody.value(i));
                }
                // 公共参数
                postValues.makeCommParams();
                postValues.preparePost();
                postValues.printLog();
                // 将数据重新放入bodyBuilder
                for(Map.Entry<String, String> entry : postValues.postValues().entrySet()){
                    bodyBuilder.add(entry.getKey(), entry.getValue());
                }
                formBody = bodyBuilder.build();
                request = request.newBuilder().post(formBody).build();
            }
        }
        return chain.proceed(request);
    }
}

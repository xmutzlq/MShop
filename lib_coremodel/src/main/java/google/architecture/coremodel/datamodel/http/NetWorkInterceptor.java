package google.architecture.coremodel.datamodel.http;

import java.io.IOException;

import google.architecture.coremodel.datamodel.http.progress.ProgressManager;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author lq.zeng
 * @date 2018/9/18
 */

public class NetWorkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return ProgressManager.getInstance().wrapResponseBody(chain.proceed(ProgressManager.getInstance().wrapRequestBody(chain.request())));
    }
}

package google.architecture.coremodel.datamodel.http.upload;

import net.gotev.uploadservice.http.HttpConnection;
import net.gotev.uploadservice.http.HttpStack;

import java.io.IOException;

import google.architecture.coremodel.datamodel.http.ApiClient;

/**
 * @author lq.zeng
 * @date 2018/9/18
 */

public class OkHttpStack implements HttpStack {

    @Override
    public HttpConnection createNewConnection(String method, String url) throws IOException {
        return new OkHttpStackConnection(ApiClient.getOkHttpClient(), method, url);
    }
}

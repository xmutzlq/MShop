package google.architecture.coremodel.datamodel.http;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author lq.zeng
 * @date 2018/8/20
 */

public class TrustAllCerts implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {}

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {}

    @Override
    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
}

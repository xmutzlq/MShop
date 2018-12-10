package google.architecture.coremodel.datamodel.http;


import java.util.concurrent.TimeUnit;

import google.architecture.coremodel.BuildConfig;
import google.architecture.coremodel.datamodel.http.service.DeHongDataService;
import google.architecture.coremodel.datamodel.http.service.DynamicApiService;
import google.architecture.coremodel.datamodel.http.service.GankDataService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static google.architecture.coremodel.datamodel.http.ApiConstants.DEFAULT_CONNECT_TIMEOUT;
import static google.architecture.coremodel.datamodel.http.ApiConstants.DEFAULT_READ_TIMEOUT;
import static google.architecture.coremodel.datamodel.http.ApiConstants.DEFAULT_WRITE_TIMEOUT;
/**
 * @author lq.zeng
 * @date 2018/4/8
 */

public class ApiClient{

    public static GankDataService getGankDataService(){

        GankDataService gankDataService = initService(GankDataService.class);

        return gankDataService;
    }

    public static DeHongDataService getDeHongDataService(){

        DeHongDataService dehongDataService = initService(DeHongDataService.class);

        return dehongDataService;
    }

    public static DynamicApiService getDynamicDataService(String baseUrl){

        DynamicApiService dynamicApiService = ApiClient.initService(DynamicApiService.class);

        return dynamicApiService;
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = getOkHttpClientInstance();
        return  okHttpClient;
    }

    /**
     * 获得想要的 retrofit service
     * @param clazz    想要的 retrofit service 接口，Retrofit会代理生成对应的实体类
     * @param <T>      api service
     * @return
     */
    private static <T> T initService(Class<T> clazz) {
        return getRetrofitInstance().create(clazz);
    }

    /**单例retrofit*/
    private static Retrofit retrofitInstance;
    private static Retrofit getRetrofitInstance(){
        if (retrofitInstance == null) {
            synchronized (ApiClient.class) {
                if (retrofitInstance == null) {
                    retrofitInstance = new Retrofit.Builder()
                            .baseUrl(ApiConstants.URLHost)
                            // 自定义Gson工厂
                            .addConverterFactory(GsonFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClientInstance())
                            .build();
                }
            }
        }
        return retrofitInstance;
    }

    /**单例OkHttpClient*/
    private static OkHttpClient okHttpClientInstance;
    private static OkHttpClient getOkHttpClientInstance(){
        if (okHttpClientInstance == null) {
            synchronized (ApiClient.class) {
                if (okHttpClientInstance == null) {
                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
                    // 不重复请求
                    builder.retryOnConnectionFailure(false);
                    // 超时设置
                    builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
                    builder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
                    builder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);

                    // 打印日志
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(httpLoggingInterceptor);
                    }
                    builder.addNetworkInterceptor(new NetWorkInterceptor());
                    // 头部拦截器
                    builder.addInterceptor(new HeaderInterceptor());
                    // 公共参数拦截器
                    builder.addInterceptor(new CommonParamsInterceptor());
                    // 加密参数拦截器
                    builder.addInterceptor(new EncryptInterceptor());
                    //过滤HTTPS
                    builder.sslSocketFactory(HttpsTrustManager.createSSLSocketFactory());
                    builder.hostnameVerifier(new HttpsTrustManager.TrustAllHostnameVerifier());

                    okHttpClientInstance = builder.build();
                }
            }
        }
        return okHttpClientInstance;
    }
}

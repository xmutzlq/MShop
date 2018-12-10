package google.architecture.coremodel.datamodel.http.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DynamicApiService<T>{

    @GET
    Observable<ResponseBody> getDynamicData(@Url String fullUrl);

}

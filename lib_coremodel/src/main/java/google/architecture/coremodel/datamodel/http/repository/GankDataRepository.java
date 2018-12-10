package google.architecture.coremodel.datamodel.http.repository;

import google.architecture.coremodel.datamodel.http.ApiClient;
import google.architecture.coremodel.data.NewsData;
import google.architecture.coremodel.data.GirlsData;
import io.reactivex.Observable;

public class GankDataRepository {

    public static Observable<GirlsData>  getFuliDataRepository(String size, String index){

        Observable<GirlsData> observableForGetFuliDataFromNetWork = ApiClient.getGankDataService().getFuliData(size,index);
        //可以操作Observable来筛选网络或者是本地数据
        return observableForGetFuliDataFromNetWork;
    }

    public static Observable<NewsData> getNewsDataRepository(String size, String index){

        Observable<NewsData> observableForGetAndroidDataFromNetWork = ApiClient.getGankDataService().getAndroidData(size,index);
        //可以操作Observable来筛选网络或者是本地数据
        return observableForGetAndroidDataFromNetWork;
    }

}

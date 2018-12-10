package google.architecture.home.area.db;

import java.util.List;

import google.architecture.common.widget.address.ISelectAble;
import io.reactivex.Observable;

/**
 * @author lq.zeng
 * @date 2018/5/30
 */

public interface DBRxAction {
    Observable<List<ISelectAble>> getProvinces();
    Observable<List<ISelectAble>> getCitys(String provinceId);
    Observable<List<ISelectAble>> getCountrys(String cityId);
    Observable<List<ISelectAble>> getStreets(String countryId);
}

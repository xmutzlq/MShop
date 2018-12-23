package google.architecture.common.viewmodel.xlj;

import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

public class XLJ_GoodsDetailViewModel extends UIViewModel {
    public void getGoodsDetail(String requestJson, IDoOnNext doOnNext) {
        subscribe(DeHongDataRepository.get().xlj_getGoodsDetail(requestJson), doOnNext);
    }
}

package google.architecture.common.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import google.architecture.coremodel.data.BidsData;
import google.architecture.coremodel.datamodel.http.HttpResult;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import io.reactivex.disposables.Disposable;

public class FinancingViewModel extends UIViewModel {

    private List<BidsData.IntelligentBidInfo> bannerItemList = new ArrayList<>();
    private MutableLiveData<List<BidsData.IntelligentBidInfo>> bannerLiveData = new MutableLiveData<>();

    private Disposable localDisposable;

    public FinancingViewModel() {

    }

    public void initLocalBanners() {
        localDisposable = subscribe(DeHongDataRepository.get().getBidData(),
                (t) -> setLocalBanners((HttpResult<List<BidsData.IntelligentBidInfo>>) t));
    }

    private void setLocalBanners(HttpResult<List<BidsData.IntelligentBidInfo>> banners) {
        if (banners != null && bannerItemList.isEmpty()) {
            bannerLiveData.setValue(banners.getData());
        }
        localDisposable.dispose();
    }

    private void refreshDataOnNext(List<BidsData.IntelligentBidInfo> bannerItems) {
        bannerItemList.clear();
        bannerItemList.addAll(bannerItems);
        bannerLiveData.setValue(bannerItemList);
    }

    public MutableLiveData<List<BidsData.IntelligentBidInfo>> getBannerLiveData() {
        return bannerLiveData;
    }

    @Override
    public void clear() {
        super.clear();
        if(localDisposable != null) {
            localDisposable.dispose();
        }
    }
}

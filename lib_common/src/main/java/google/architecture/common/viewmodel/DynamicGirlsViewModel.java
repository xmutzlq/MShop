package google.architecture.common.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import google.architecture.common.base.BaseDynamicViewModel;
import google.architecture.coremodel.data.GirlsData;

public class DynamicGirlsViewModel extends BaseDynamicViewModel<GirlsData> {

    public DynamicGirlsViewModel(@NonNull Application application, String fullUrl) {
        super(application, fullUrl);
    }
}

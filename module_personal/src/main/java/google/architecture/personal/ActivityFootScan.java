package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.viewmodel.xlj.FootScanViewModel;
import google.architecture.personal.databinding.ActivityFootScanBinding;

@Route(path = ARouterPath.FootScanLoginAty)
public class ActivityFootScan extends BaseActivity<ActivityFootScanBinding> {
    @Override
    protected int getLayout() {
        return R.layout.activity_foot_scan;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FootScanViewModel viewModel = new FootScanViewModel();
        addRunStatusChangeCallBack(viewModel);
        viewModel.getUserToken("11111111111");
    }

    @Override
    protected void onDataResult(Object o) {
        super.onDataResult(o);
    }
}

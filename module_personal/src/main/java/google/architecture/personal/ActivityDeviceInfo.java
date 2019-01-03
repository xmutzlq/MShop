package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.util.DeviceUtils;
import google.architecture.personal.databinding.ActivityDeviceInfoBinding;

public class ActivityDeviceInfo extends BaseActivity<ActivityDeviceInfoBinding> {
    @Override
    protected int getLayout() {
        return R.layout.activity_device_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        binding.deviceTokenTv.setText(BaseApplication.getIns().getDeviceToken());
        binding.uniqueIdTv.setText(DeviceUtils.getUniqueId(this));
    }
}

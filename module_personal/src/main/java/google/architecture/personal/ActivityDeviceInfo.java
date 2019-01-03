package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.util.DeviceUtils;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.PersonalViewNewModel;
import google.architecture.coremodel.datamodel.http.XLJ_HttpResult;
import google.architecture.personal.databinding.ActivityDeviceInfoBinding;

@Route(path = ARouterPath.DeviceInfoAty)
public class ActivityDeviceInfo extends BaseActivity<ActivityDeviceInfoBinding> {
    private PersonalViewNewModel mPersonViewModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_device_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setTitleName("设备信息");
        mPersonViewModel = new PersonalViewNewModel();
        addRunStatusChangeCallBack(mPersonViewModel);
    }

    private void init(){
        binding.deviceTokenTv.setText(BaseApplication.getIns().getDeviceToken());
        binding.uniqueIdTv.setText(DeviceUtils.getUniqueId(this));
        binding.btnUploadDeviceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()){
                    mPersonViewModel.uploadDeviceInfo(binding.deviceTokenTv.getText().toString(), binding.uniqueIdTv.getText().toString(), binding.deviceNameEt.getText().toString());
                }else{
                    ToastUtils.showLongToast("请输入设备名称");
                }

            }
        });
    }

    private boolean validateFields(){
        if(TextUtils.isEmpty(binding.deviceNameEt.getText().toString())){
            return false;
        }
        return true;
    }

    @Override
    protected void onDataResult(Object o) {
        super.onDataResult(o);
        XLJ_HttpResult result = (XLJ_HttpResult)o;
        if(result.isOK()){
            ToastUtils.showLongToast("上传成功");
        }else{
            ToastUtils.showLongToast("上传失败");
        }
    }
}

package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.util.ToastUtils;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.coremodel.util.PreferencesUtils;
import google.architecture.personal.databinding.ActivityIdleTimeSettingBinding;

@Route(path = ARouterPath.IdleTimeAty)
public class ActivityIdleTimeSetting extends BaseActivity<ActivityIdleTimeSettingBinding> {
    @Override
    protected int getLayout() {
        return R.layout.activity_idle_time_setting;
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("系统空闲时间设置");
        binding.idleTimeEt.setText(ApiConstants.IDLE_SECOND);

        //保存
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.idleTimeEt.getText().toString())){
                    ToastUtils.showLongToast("应用空闲时间");
                    return;
                }
                ApiConstants.IDLE_SECOND = Integer.parseInt(binding.idleTimeEt.getText().toString());
                PreferencesUtils.putInt(ActivityIdleTimeSetting.this, "idle_time",ApiConstants.IDLE_SECOND);
                ToastUtils.showLongToast("保存成功");
            }
        });
    }
}

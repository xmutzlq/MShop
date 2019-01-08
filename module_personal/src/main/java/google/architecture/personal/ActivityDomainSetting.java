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
import google.architecture.personal.databinding.ActivityDomainSettingBinding;

@Route(path = ARouterPath.DomainInfoAty)
public class ActivityDomainSetting extends BaseActivity<ActivityDomainSettingBinding> {
    @Override
    protected int getLayout() {
        return R.layout.activity_domain_setting;
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("域名设置");
        binding.domainEt.setText(ApiConstants.GankHost);
        binding.imgPathEt.setText(ApiConstants.XLJimgHost);
        //保存
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.domainEt.getText().toString())){
                    ToastUtils.showLongToast("请输入新域名");
                    return;
                }
                if(TextUtils.isEmpty(binding.imgPathEt.getText().toString())){
                    ToastUtils.showLongToast("请输入新图片地址");
                    return;
                }
                ApiConstants.GankHost = binding.domainEt.getText().toString();
                ApiConstants.XLJimgHost = binding.imgPathEt.getText().toString();
                PreferencesUtils.putString(ActivityDomainSetting.this, "domain_address",ApiConstants.GankHost);
                PreferencesUtils.putString(ActivityDomainSetting.this, "xlj_img_address",ApiConstants.XLJimgHost);
                ToastUtils.showLongToast("保存成功");
            }
        });
    }
}

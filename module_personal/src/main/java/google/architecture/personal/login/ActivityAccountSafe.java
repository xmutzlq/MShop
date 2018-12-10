package google.architecture.personal.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseActivity;
import google.architecture.personal.R;
import google.architecture.personal.databinding.ActivityAccountSafeBinding;

/**
 * @author lq.zeng
 * @date 2018/10/17
 */
@Route(path = ARouterPath.PersonalSettingAccountSafeAty)
public class ActivityAccountSafe extends BaseActivity<ActivityAccountSafeBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_account_safe;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleName(getString(R.string.personal_account_safe));

        binding.personalSettingModifyLogin.setOnSuperTextViewClickListener(superTextView -> {
            ARouter.getInstance().build(ARouterPath.PersonalModifyLoginAty).navigation();
        });

        binding.personalSettingModifyPhoneEmail.setOnSuperTextViewClickListener(superTextView -> {
            ARouter.getInstance().build(ARouterPath.PersonalModifyAccountAty).navigation();
        });
    }
}

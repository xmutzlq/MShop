package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.util.DebouncingOnClickListener;

import google.architecture.common.base.BaseActivity;
import google.architecture.personal.databinding.ActivityPersonalBinding;
import google.architecture.personal.login.ActivityLogin;

/**
 * @author lq.zeng
 * @date 2018/4/11
 */

@Route(path = ARouterPath.PersonalAty)
public class ActivityPersonal extends BaseActivity<ActivityPersonalBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_personal;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.executePendingBindings();
        binding.btnLogin.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                ARouter.getInstance()
                        .build(ARouterPath.PersonalLoginAty)
                        .withInt(ActivityLogin.TYPE_FLAG, ActivityLogin.LOGIN_FLAG)
                        .navigation(ActivityPersonal.this);
            }
        });
        binding.btnRegister.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                ARouter.getInstance()
                        .build(ARouterPath.PersonalLoginAty)
                        .withInt(ActivityLogin.TYPE_FLAG, ActivityLogin.REGISTER_FLAG)
                        .navigation(ActivityPersonal.this);
            }
        });
    }
}

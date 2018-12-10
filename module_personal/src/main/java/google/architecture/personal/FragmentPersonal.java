package google.architecture.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.util.DebouncingOnClickListener;

import google.architecture.common.base.BaseFragment;
import google.architecture.coremodel.Account;
import google.architecture.personal.databinding.FragmentPersonalBinding;
import google.architecture.personal.login.ActivityLogin;

/**
 * @author lq.zeng
 * @date 2018/4/11
 */
@Route(path = ARouterPath.PersonalFgt)
public class FragmentPersonal extends BaseFragment<FragmentPersonalBinding> {

    private static final int REQUEST_CODE_PERSONAL_INFO = 1000;

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        getToolbarHelper().getAppBarLayout().setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void onCreateBindView() {
        ARouter.getInstance().inject(FragmentPersonal.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnLogin.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                ARouter.getInstance()
                        .build(ARouterPath.PersonalLoginAty)
                        .withInt(ActivityLogin.TYPE_FLAG, ActivityLogin.LOGIN_FLAG)
                        .navigation(getActivity(), REQUEST_CODE_PERSONAL_INFO);
            }
        });
        binding.btnRegister.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                ARouter.getInstance()
                        .build(ARouterPath.PersonalLoginAty)
                        .withInt(ActivityLogin.TYPE_FLAG, ActivityLogin.REGISTER_FLAG)
                        .navigation();
            }
        });
        binding.btnUnlogin.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                Account.get().clearUserInfo();
                refreshPersonalInfo();
            }
        });
        binding.btnModifyLogin.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                ARouter.getInstance()
                        .build(ARouterPath.PersonalModifyLoginAty)
                        .navigation();
            }
        });
        binding.btnModifyAccount.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                ARouter.getInstance()
                        .build(ARouterPath.PersonalModifyAccountAty)
                        .navigation();
            }
        });
        refreshPersonalInfo();
    }

    @Override
    public boolean onActivityResultPatch(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PERSONAL_INFO) {
            if (resultCode == ActivityLogin.RESULT_CODE_SUC) {
                refreshPersonalInfo();
            }
        }
        return super.onActivityResultPatch(requestCode, resultCode, data);
    }

    @Override
    public void onUserLoginStateChange(boolean isLogin) {
        super.onUserLoginStateChange(isLogin);
        LogUtils.tag("zlq").e("FragmentPersonal = onUserLoginStateChange" );
    }

    @Override
    public void onReLoad() {
        refreshPersonalInfo();
    }

    private void refreshPersonalInfo() {
        if(Account.get().isLogin()) {
            binding.btnLogin.setVisibility(View.GONE);
            binding.btnRegister.setVisibility(View.GONE);
            binding.loginBtnLl.setVisibility(View.VISIBLE);
        } else {
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.btnRegister.setVisibility(View.VISIBLE);
            binding.loginBtnLl.setVisibility(View.GONE);
        }
        updateAccountInfo();
    }

    private void updateAccountInfo() {
        String userName = Account.get().getUserName();
        if(TextUtils.isEmpty(userName)) {
            binding.personalInfoTv.setVisibility(View.GONE);
        } else {
            binding.personalInfoTv.setVisibility(View.VISIBLE);
            if(userName.contains("@")) {
                binding.personalInfoTv.setText("邮箱：" + userName);
            } else {
                binding.personalInfoTv.setText("电话：" + userName);
            }
        }
    }
}

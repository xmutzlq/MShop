package google.architecture.personal.login;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.util.DebouncingOnClickListener;
import com.king.android.sharesdk.AppShareMain;

import cn.sharesdk.framework.Platform;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.KeyboardUtils;
import google.architecture.common.viewmodel.LoginViewModel;
import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.personal.R;
import google.architecture.personal.databinding.FragmentLoginBinding;
import google.architecture.personal.login.listener.LoginListener;

/**
 * @author lq.zeng
 * @date 2018/4/11
 */

public class FragmentLogin extends BaseFragment<FragmentLoginBinding> implements AppShareMain.IShareResponse {

    private LoginListener listener;
    private LoginViewModel loginViewModel;
    private LoginCallBack loginCallBack = new LoginCallBack();

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new LoginViewModel();
        binding.setLoginViewModel(loginViewModel);
        addRunStatusChangeCallBack(loginViewModel);

        binding.toolbar.setTitle(R.string.login);
        setToolbar(binding.toolbar);

        loginViewModel.userInfo.addOnPropertyChangedCallback(loginCallBack);

        initListener();

        loginViewModel.loginAccountStr.set("18666666666");
        loginViewModel.loginPwdStr.set("123456");

    }

    private void initListener() {
        binding.loginBtn.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                loginViewModel.loginToAccount();
            }
        });

        binding.ivQqLogin.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                AppShareMain.build(getContext()).withResponse(FragmentLogin.this)
                        .actionWhat(AppShareMain.Action.ILogin.LOGIN_QQ);

            }
        });

        binding.ivWechatLogin.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                AppShareMain.build(getContext()).withResponse(FragmentLogin.this)
                        .actionWhat(AppShareMain.Action.ILogin.LOGIN_WX);
            }
        });

        binding.ivSinaLogin.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                AppShareMain.build(getContext()).withResponse(FragmentLogin.this)
                        .actionWhat(AppShareMain.Action.ILogin.LOGIN_SINA);
            }
        });

        binding.restLoginBtn.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                ARouter.getInstance()
                        .build(ARouterPath.PersonalResetLoginAty)
                        .navigation();
            }
        });

        binding.pwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.pwdLayout.setErrorEnabled(false);

                String result = s.toString();
                binding.loginBtn.setEnabled(!TextUtils.isEmpty(result));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.pwdEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    loginViewModel.loginToAccount();
                }
                return false;
            }
        });

        binding.registerNewUser.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(ARouterPath.PersonalLoginAty)
                    .withInt(ActivityLogin.TYPE_FLAG, ActivityLogin.REGISTER_FLAG)
                    .navigation(mContext);
        });
    }

    @Override
    public void onDestroyView() {
        KeyboardUtils.closeSoftInput(getActivity(), binding.pwdEt);
        loginViewModel.userInfo.removeOnPropertyChangedCallback(loginCallBack);
        loginViewModel.clear();
        super.onDestroyView();
    }

    @Override
    public void onComplete(final int actionType, final Platform platform) {
        synchronized (this) {
            BaseApplication.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    switch (actionType) {
                        case AppShareMain.Action.ILogin.LOGIN_QQ:
                            if(platform != null && platform.getDb() != null
                                    && !TextUtils.isEmpty(platform.getDb().getUserId())) {
                                loginViewModel.quickLoginToAccount(platform.getDb().getUserId(),
                                        platform.getDb().getUserName(), platform.getDb().getUserIcon(),
                                        platform.getDb().getUserGender(), LoginViewModel.LOGIN_TYPE_QQ);
                            }
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onCancel() {

    }

    private class LoginCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            S_UserInfo userInfo = loginViewModel.userInfo.get();
            if (userInfo != null && listener != null) {
                listener.onLoginSuc(userInfo);
            }
        }
    }
}

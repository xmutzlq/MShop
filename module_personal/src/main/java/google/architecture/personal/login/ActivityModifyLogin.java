package google.architecture.personal.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.util.DebouncingOnClickListener;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.util.KeyboardUtils;
import google.architecture.common.viewmodel.ModifyLoginViewModel;
import google.architecture.personal.R;
import google.architecture.personal.databinding.FragmentModifyLoginBinding;

/**
 * @author lq.zeng
 * @date 2018/4/18
 */

@Route(path = ARouterPath.PersonalModifyLoginAty)
public class ActivityModifyLogin extends BaseActivity<FragmentModifyLoginBinding> {

    ModifyLoginViewModel modifyLoginViewModel = new ModifyLoginViewModel();

    @Override
    protected int getLayout() {
        return R.layout.fragment_modify_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setModifyLoginViewModel(modifyLoginViewModel);
        addRunStatusChangeCallBack(modifyLoginViewModel);
        binding.toolbar.setTitle(R.string.modify_login);
        setToolbar(binding.toolbar);
        initListener();
    }

    private void initListener() {

        binding.modifyLoginBtn.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                modifyLoginViewModel.modifyLoginAccount();
            }
        });

        binding.pwdComfirmEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.pwdLayout.setErrorEnabled(false);

                String result = s.toString();
                binding.modifyLoginBtn.setEnabled(!TextUtils.isEmpty(result));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        KeyboardUtils.closeSoftInput(this, binding.pwdEt);
        modifyLoginViewModel.clear();
        super.onDestroy();
    }
}

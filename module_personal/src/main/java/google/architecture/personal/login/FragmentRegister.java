package google.architecture.personal.login;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.king.android.res.util.DebouncingOnClickListener;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.KeyboardUtils;
import google.architecture.common.util.RegexUtils;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.RegisterViewModel;
import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.coremodel.util.TextUtil;
import google.architecture.personal.R;
import google.architecture.personal.databinding.FragmentRegisterBinding;
import google.architecture.personal.login.card.CardItem;
import google.architecture.personal.login.card.CardPagerAdapter;
import google.architecture.personal.login.card.ShadowTransformer;
import google.architecture.personal.login.listener.LoginListener;

/**
 * @author lq.zeng
 * @date 2018/4/11
 */

public class FragmentRegister extends BaseFragment<FragmentRegisterBinding> implements ViewPager.OnPageChangeListener, CardPagerAdapter.ICardItemChildClick {

    private RegisterViewModel registerViewModel = new RegisterViewModel();
    private RegisterCallBack registerCallBack = new RegisterCallBack();

    private LoginListener listener;
    private int currentPosition;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    public static FragmentRegister newInstance() {
        return new FragmentRegister();
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setRegisterViewModel(registerViewModel);
        addRunStatusChangeCallBack(registerViewModel);
        registerViewModel.userInfo.addOnPropertyChangedCallback(registerCallBack);

        binding.toolbar.setTitle(R.string.register);
        setToolbar(binding.toolbar);

        initListener();

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.setCardItemChildClick(this);
        mCardAdapter.addCardItem(new CardItem(CardItem.IRegisterType.REGISTER_TYPE_PHONE, getResources().getString(R.string.personal_info_secret_phone)));
        mCardAdapter.addCardItem(new CardItem(CardItem.IRegisterType.REGISTER_TYPE_EMAIL, getResources().getString(R.string.personal_info_secret_email)));
        mCardShadowTransformer = new ShadowTransformer(binding.registerCardViewpager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);
        binding.registerCardViewpager.setAdapter(mCardAdapter);
        binding.registerCardViewpager.setPageTransformer(false, mCardShadowTransformer);
        binding.registerCardViewpager.setOffscreenPageLimit(2);
        binding.registerCardViewpager.addOnPageChangeListener(this);
        binding.registerCardViewpager.setCurrentItem(currentPosition);
    }

    private void initListener() {

        binding.registerBtn.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
             String account = mCardAdapter.getCardItemAt(currentPosition).getInputValue();
             String way = mCardAdapter.getCardItemAt(currentPosition).getType();
             registerViewModel.userNameStr.set(account);
             registerViewModel.wayStr.set(way);
            if(TextUtil.isEmpty(account)) {
                ToastUtils.showShortToast("请输入账号");
                return;
            }

            if(TextUtil.isEmpty(binding.onlyNameEt.getText().toString())) {
                ToastUtils.showShortToast("请输入用户名");
                return;
            }

            if(TextUtil.isEmpty(binding.pwdEt.getText().toString())) {
                ToastUtils.showShortToast("请输入密码");
                return;
            }

            if(TextUtil.isEmpty(binding.codeEt.getText().toString())) {
                ToastUtils.showShortToast("请输入验证码");
                return;
            }

            switch (way) {
                case CardItem.IRegisterType.REGISTER_TYPE_PHONE: //手机号是否合法
                    if(!RegexUtils.isMobileExact(account)) {
                        ToastUtils.showShortToast("请输入正确的手机号码");
                        return;
                    }
                    break;
                case CardItem.IRegisterType.REGISTER_TYPE_EMAIL: //邮箱是否合法
                    if(!RegexUtils.isEmail(account)) {
                        ToastUtils.showShortToast("请输入正确的邮箱");
                        return;
                    }
                    break;
            }

            if(binding.pwdEt.getText().toString().length() < 6) {
                ToastUtils.showShortToast("请输入不小于6位的密码");
                return;
            }

            registerViewModel.register();
            }
        });

        binding.codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.pwdLayout.setErrorEnabled(false);

                String result = s.toString();
                binding.registerBtn.setEnabled(!TextUtils.isEmpty(result));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.codeEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_GO) {
                registerViewModel.register();
            }
            return false;
        });
    }

    @Override
    public void onDestroyView() {
        KeyboardUtils.closeSoftInput(getActivity(), binding.pwdEt);
        registerViewModel.userInfo.removeOnPropertyChangedCallback(registerCallBack);
        registerViewModel.clear();
        super.onDestroyView();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        CardItem item = mCardAdapter.getCardItemAt(position);
        registerViewModel.wayStr.set(String.valueOf(item.getType()));
        registerViewModel.userNameStr.set(item.getInputValue());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSend(String type) {
        registerViewModel.userNameStr.set(mCardAdapter.getCardItemAt(currentPosition).getInputValue());
        if(CardItem.IRegisterType.REGISTER_TYPE_PHONE == type) {
            registerViewModel.sendShotMsg();
        } else {
            registerViewModel.sendEmail();
        }
    }

    private class RegisterCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            S_UserInfo userInfo = registerViewModel.userInfo.get();
            if (userInfo != null && listener != null) {
                listener.onLoginSuc(userInfo);
            }
        }
    }
}

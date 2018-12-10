package google.architecture.personal.login;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.util.DebouncingOnClickListener;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.ViewManager;
import google.architecture.common.util.KeyboardUtils;
import google.architecture.common.util.RegexUtils;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.ModifyUserAccountViewModel;
import google.architecture.coremodel.util.TextUtil;
import google.architecture.personal.R;
import google.architecture.personal.databinding.FragmentModifyAccountBinding;
import google.architecture.personal.login.card.CardItem;
import google.architecture.personal.login.card.CardPagerAdapter;
import google.architecture.personal.login.card.ShadowTransformer;

/**
 * @author lq.zeng
 * @date 2018/4/23
 */
@Route(path = ARouterPath.PersonalModifyAccountAty)
public class ActivityModifyUserAccount extends BaseActivity<FragmentModifyAccountBinding> implements ViewPager.OnPageChangeListener, CardPagerAdapter.ICardItemChildClick {

    ModifyUserAccountViewModel modifyUserAccountViewModel = new ModifyUserAccountViewModel();

    private int currentPosition;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected int getLayout() {
        return R.layout.fragment_modify_account;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setModifyUserAccountViewModel(modifyUserAccountViewModel);
        addRunStatusChangeCallBack(modifyUserAccountViewModel);
        binding.toolbar.setTitle(R.string.modify_account);
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

        binding.modifyAccountBtn.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
            String account = mCardAdapter.getCardItemAt(currentPosition).getInputValue();
            String way = mCardAdapter.getCardItemAt(currentPosition).getType();
            modifyUserAccountViewModel.userNameStr.set(account);
            modifyUserAccountViewModel.wayStr.set(way);
            if(TextUtil.isEmpty(account)) {
                ToastUtils.showShortToast("请输入账号");
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
                    modifyUserAccountViewModel.modifyCellphoneAccount();
                    break;
                case CardItem.IRegisterType.REGISTER_TYPE_EMAIL: //邮箱是否合法
                    if(!RegexUtils.isEmail(account)) {
                        ToastUtils.showShortToast("请输入正确的邮箱");
                        return;
                    }
                    modifyUserAccountViewModel.modifyEmailAccount();
                    break;
            }
            }
        });

        binding.codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String result = s.toString();
                binding.modifyAccountBtn.setEnabled(!TextUtils.isEmpty(result));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        modifyUserAccountViewModel.modifyResultNotify.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                ViewManager.getInstance().finishActivity();
            }
        });
    }

    @Override
    protected void onDestroy() {
        KeyboardUtils.closeSoftInput(this, binding.codeEt);
        modifyUserAccountViewModel.clear();
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        CardItem item = mCardAdapter.getCardItemAt(position);
        modifyUserAccountViewModel.wayStr.set(String.valueOf(item.getType()));
        modifyUserAccountViewModel.userNameStr.set(item.getInputValue());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSend(String type) {
        modifyUserAccountViewModel.userNameStr.set(mCardAdapter.getCardItemAt(currentPosition).getInputValue());
        if(CardItem.IRegisterType.REGISTER_TYPE_PHONE == type) {
            modifyUserAccountViewModel.sendShotMsg();
        } else {
            modifyUserAccountViewModel.sendEmail();
        }
    }
}
package google.architecture.personal.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.listener.AppBrocastAction;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.personal.R;
import google.architecture.personal.login.listener.LoginListener;

/**
 * 用户界面
 * @author lq.zeng
 * @date 2018/4/11
 */
@Route(path = ARouterPath.PersonalLoginAty)
public class ActivityLogin extends BaseActivity implements LoginListener {

    public static final String TYPE_FLAG = "typeFlag";

    public static final int RESULT_CODE_SUC = 10001;
    public static final int LOGIN_FLAG = 3000;
    public static final int REGISTER_FLAG = 3001;

    private FragmentLogin loginFragment;
    private FragmentRegister registerFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        int typeFlag = getIntent().getIntExtra(TYPE_FLAG, REGISTER_FLAG);
        if (typeFlag == LOGIN_FLAG) {
            switchLoginFragment();
        } else if (typeFlag == REGISTER_FLAG) {
            switchRegisterFragment();
        } else {
            switchRegisterFragment();
        }
    }

    //登录界面
    private void switchLoginFragment() {
        if (loginFragment == null) {
            loginFragment = FragmentLogin.newInstance();
            loginFragment.setLoginListener(this);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_fl, loginFragment,
                        FragmentLogin.class.getSimpleName())
                .commit();
    }

    //注册界面
    private void switchRegisterFragment() {
        if (registerFragment == null) {
            registerFragment = FragmentRegister.newInstance();
            registerFragment.setLoginListener(this);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_fl, registerFragment,
                        FragmentRegister.class.getSimpleName())
                .commit();
    }

    @Override
    public void onLoginSuc(S_UserInfo userInfo) {
        Account.get().setUserInfoToLocal(userInfo);
        String str = getResources().getString(R.string.welcome_back, userInfo.username);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        sendBroadcast(new Intent(AppBrocastAction.ACTION_USER_LOGIN_STATE_CHANGE));
    }
}

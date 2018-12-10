package google.architecture.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseFragment;

/**
 * @author lq.zeng
 * @date 2018/6/11
 */
@Route(path = ARouterPath.PersonalLoginShoppingAty)
public class ActivityShoppingPersonal extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_shopping_personal;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Looper.myQueue().addIdleHandler(() -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_shopping_personal, (BaseFragment)ARouter.getInstance().build(ARouterPath.PersonalShoppingFgt).navigation()).commit();
            return false;
        });
    }

    @Override
    public void onUserLoginStateChange(boolean isLogin) {
        super.onUserLoginStateChange(isLogin);
        ARouter.getInstance()
                .build(ARouterPath.PersonalLoginShoppingAty)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .navigation(this_);
    }
}

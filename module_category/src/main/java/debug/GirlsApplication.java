package debug;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;

import google.architecture.common.base.BaseApplication;
import google.architecture.coremodel.Account;

/**
 * Created by dxx on 2017/11/15.
 * 组件化编译的时候才生效
 */

public class GirlsApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        ARouter.init(this);
        //1号用户当成测试用户，后端需关闭登录失效功能
        Account.get().setUserIdToLocal("1");
    }
}

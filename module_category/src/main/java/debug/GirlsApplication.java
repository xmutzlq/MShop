package debug;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.util.Utils;
import google.architecture.coremodel.Account;

/**
 * Created by dxx on 2017/11/15.
 * 组件化编译的时候才生效
 */

public class GirlsApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.APP_STATUS = BaseApplication.APP_STATUS_NORMAL;
        Fresco.initialize(this);
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        //1号用户当成测试用户，后端需关闭登录失效功能
        Account.get().setUserIdToLocal("1");
    }
}

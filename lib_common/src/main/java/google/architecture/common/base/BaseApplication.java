package google.architecture.common.base;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.util.ArrayMap;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.king.android.res.application.BaseApp;
import com.king.android.res.config.ARouterPath;
import com.king.android.sharesdk.utils.PublicStaticData;
import com.kongzue.dialog.v2.DialogSettings;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
//import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;

import net.gotev.uploadservice.Logger;
import net.gotev.uploadservice.UploadService;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import google.architecture.common.R;
import google.architecture.common.util.DynamicTimeFormat;
import google.architecture.common.util.KeyboardUtils;
import google.architecture.common.util.Utils;
import google.architecture.coremodel.BuildConfig;
import google.architecture.coremodel.datamodel.http.upload.OkHttpStack;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 * @name BaseApplication
 */
public abstract class BaseApplication extends BaseApp {

    public static final String ROOT_PACKAGE = "com.guiying.module";
    public final static int APP_STATUS_KILLED = 0; // 表示应用是被杀死后在启动的
    public final static int APP_STATUS_NORMAL = 1; // 表示应用时正常的启动流程
    public static int APP_STATUS = APP_STATUS_KILLED; // 记录App的启动状态

    private static BaseApplication sInstance;

    private List<ApplicationDelegate> mAppDelegateList;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//启用矢量图兼容
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


    public static BaseApplication getIns() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Utils.init(this);

//        Density.setDensity(this);

        AutoSizeConfig.getInstance().setCustomFragment(true);

//        Account.get().initUserInfo();

        //CrashReport.initCrashReport(this);


        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");

        mAppDelegateList = ClassUtils.getObjectsWithInterface(this, ApplicationDelegate.class, ROOT_PACKAGE);
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onCreate();
        }

        // Set your application namespace to avoid conflicts with other apps using this library
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        // Set upload service debug log messages level
        Logger.setLogLevel(Logger.LogLevel.DEBUG);
        // Set up the Http Stack to use. If you omit this or comment it, HurlStack will be used by default
        UploadService.HTTP_STACK = new OkHttpStack();
        // setup backoff multiplier
        UploadService.BACKOFF_MULTIPLIER = 2;

        //初始化对话框参数
        DialogSettings.type = DialogSettings.TYPE_IOS;
        //设置提示框主题为亮色主题
        DialogSettings.tip_theme = DialogSettings.THEME_DARK;
        //设置对话框主题为暗色主题
        DialogSettings.dialog_theme = DialogSettings.THEME_LIGHT;
        DialogSettings.use_blur = false;
        DialogSettings.dialog_message_text_size = 15;
        LogUtils.getLogConfig()
                .configAllowLog(google.architecture.common.BuildConfig.DEBUG)
                .configTagPrefix(BuildConfig.APP_NAME)
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");

        PublicStaticData.myShareSDK = new ShareSDK();
        PublicStaticData.myShareSDK.initSDK(getApplicationContext());

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.tag("zlq").e("onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
        //设置开启优化方案
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
    }

    /**
     * 重新初始化应用界面，清空当前Activity棧，并启动欢迎页面
     */
    public static void reInitApp() {
        ARouter.getInstance().build(ARouterPath.AppSplashAty).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).navigation();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        KeyboardUtils.removeAllKeyboardToggleListeners();
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTerminate();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTrimMemory(level);
        }
    }

    @Override
    protected String getAppSdcardDir() {
        return AppContext.fileDiretory;
    }

    @Override
    protected boolean isWriteLogToSdcard() {
        return false;
    }

    @Override
    protected boolean isDebug() {
        return true;
    }
}

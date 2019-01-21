package google.architecture.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import google.architecture.common.upgrade.UpdateManager;
import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.common.viewmodel.xlj.AppVersionViewModel;
import google.architecture.coremodel.data.xlj.AppVersion;

public class CheckAppVersionUtil {

    private static boolean isLoading = false;

    public static void checkNewVersion(Activity activity){
        if(isLoading)
            return;

        isLoading = true;
        AppVersionViewModel viewModel = new AppVersionViewModel();
        viewModel.getAppVersionInfo(new UIViewModel.IDoOnNext() {
            @Override
            public void doOnNext(Object t) {
                AppVersion version = (AppVersion)t;
                isLoading = false;
                showDownPanel(version,activity);
            }
        });
    }

    private static void showDownPanel(AppVersion version,Activity activity){

        try {
            final int versionCode = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;
            if(versionCode < Integer.parseInt(version.getVersionCode())){
                new UpdateManager(activity,version).update();
            }else{
                ToastUtils.showLongToast("当前已经是最新版本！");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}

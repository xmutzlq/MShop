package google.architecture.personal.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import google.architecture.common.base.BaseActivity;
import google.architecture.personal.R;
import google.architecture.personal.databinding.ActivitySettingBinding;

/**
 * @author lq.zeng
 * @date 2018/9/17
 */
//@Route(path = ARouterPath.PersonalSettingAty)
public class ActivitySetting extends BaseActivity<ActivitySettingBinding> {
    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(true);
        setTitleName(getString(R.string.personal_setting_title));

        binding.personalSettingAccountSafe.setOnSuperTextViewClickListener(superTextView -> {
            ARouter.getInstance().build(ARouterPath.PersonalSettingAccountSafeAty).navigation();
        });

        binding.personalSettingShareApp.setOnSuperTextViewClickListener(superTextView -> {
            showShareUI();
        });

    }

    private void showShareUI() {
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_WECHAT_MOMENT = 1;
        final int TAG_SHARE_WEIBO = 2;
        final int TAG_SHARE_CHAT = 3;
        final int TAG_SHARE_LOCAL = 4;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(this_);
        builder.addItem(R.mipmap.share_wechat_icon, "分享到微信", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.share_friends_icon, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.share_weibo_icon, "分享到微博", TAG_SHARE_WEIBO, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.share_link_icon, "分享到私信",TAG_SHARE_CHAT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener((dialog, itemView) -> {
                    dialog.dismiss();
                    int tag = (int) itemView.getTag();
                    switch (tag) {
                        case TAG_SHARE_WECHAT_FRIEND:
                            Toast.makeText(this_, "分享到微信", Toast.LENGTH_SHORT).show();
                            break;
                        case TAG_SHARE_WECHAT_MOMENT:
                            Toast.makeText(this_, "分享到朋友圈", Toast.LENGTH_SHORT).show();
                            break;
                        case TAG_SHARE_WEIBO:
                            Toast.makeText(this_, "分享到微博", Toast.LENGTH_SHORT).show();
                            break;
                        case TAG_SHARE_CHAT:
                            Toast.makeText(this_, "分享到私信", Toast.LENGTH_SHORT).show();
                            break;
                        case TAG_SHARE_LOCAL:
                            Toast.makeText(this_, "保存到本地", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }).build().show();
    }
}

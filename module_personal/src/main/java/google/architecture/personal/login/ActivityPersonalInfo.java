package google.architecture.personal.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.SelectDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.MalformedURLException;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.listener.AppBrocastAction;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.imgpicker.Constant;
import google.architecture.common.imgpicker.activity.ImagePickActivity;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.viewmodel.PersonalViewModel;
import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.datamodel.http.upload.Base64UploadRequest;
import google.architecture.personal.R;
import google.architecture.personal.databinding.ActivityPersonalInfoBinding;

import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_NEED_CAMERA;
import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_NEED_FOLDER_LIST;

/**
 * @author lq.zeng
 * @date 2018/9/17
 */
@Route(path = ARouterPath.PersonalInfoAty)
public class ActivityPersonalInfo extends BaseActivity<ActivityPersonalInfoBinding> {

    private PersonalViewModel personalViewModel;
    private S_UserInfo s_userInfo;
    private boolean isNeedNotifyUpdateUserInfo;

    @Override
    protected int getLayout() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(true);
        setTitleName(getString(R.string.personal_info_title));

        Intent intent = getIntent();
        if(intent != null) {
            s_userInfo = intent.getParcelableExtra(CommKeyUtil.EXTRA_VALUE);
        }

        personalViewModel = new PersonalViewModel();
        addRunStatusChangeCallBack(personalViewModel);

        if(s_userInfo != null) {
            updatePersonalInfo();
        } else {
            loadData();
        }

        binding.personalInfoBaseHeadTv.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, ImagePickActivity.class);
            intent1.putExtra(IS_NEED_CAMERA, true);
            intent1.putExtra(Constant.MAX_NUMBER, 1);
            intent1.putExtra(IS_NEED_FOLDER_LIST, true);
            startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
        });

        binding.personalInfoBaseNameTv.setOnSuperTextViewClickListener(superTextView -> {
            InputDialog.show(this_, getString(R.string.modify_nick_name), "", (dialog, inputText) -> {
                personalViewModel.modifyNickName(inputText, (code, msg) -> {
                    if(code == 0) {
                        isNeedNotifyUpdateUserInfo = true;
                        binding.personalInfoBaseNameTv.setRightString(inputText);
                        s_userInfo.nickname = inputText;
                        Account.get().setUserInfoToLocal(s_userInfo);
                    }
                });
                dialog.dismiss();
            }) ;
        });

        binding.btnLoginOut.setOnClickListener(v -> {
            SelectDialog.show(this_, getString(R.string.login_out_confirm), "", (dialog, which) -> {
                Account.get().clearUserInfo();
                BaseApplication.getIns().sendBroadcast(new Intent(AppBrocastAction.ACTION_USER_LOGIN_STATE_CHANGE));
            });
        });
    }

    @Override
    protected void onDataResult(Object o) {
        s_userInfo = (S_UserInfo) o;
        Account.get().setUserInfoToLocal(s_userInfo);
        updatePersonalInfo();
    }

    private void loadData() {
        if(personalViewModel != null) personalViewModel.getUserInfo();
    }

    private void updatePersonalInfo() {
        if(s_userInfo == null) return;
        ImageLoader.get().loadCropCircleHeader(this_, s_userInfo.head_pic,
                DimensionsUtil.dip2px(this_, 40), DimensionsUtil.dip2px(this_, 40),
                new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                binding.personalInfoBaseHeadTv.setRightTvDrawableRight(resource);
            }
        });

        binding.personalInfoBaseAccountTv.setRightString(s_userInfo.username);

        if(TextUtils.isEmpty(s_userInfo.nickname)) {
            binding.personalInfoBaseNameTv.setVisibility(View.GONE);
        } else {
            binding.personalInfoBaseNameTv.setVisibility(View.VISIBLE);
            binding.personalInfoBaseNameTv.setRightString(s_userInfo.nickname);
        }

        if(TextUtils.isEmpty(s_userInfo.password)) {
            binding.personalInfoSecretSetPwdTv.setRightString(Spans.builder().text("未设置密码").color(R.color.color_ff593e).build());
        } else {
            binding.personalInfoSecretSetPwdTv.setVisibility(View.GONE);
        }
        binding.personalInfoSecretPhoneTv.setRightString(s_userInfo.mobile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    File file = (File) data.getSerializableExtra(CommKeyUtil.EXTRA_KEY);
                    if(file == null && !file.exists()) return;
                    personalViewModel.imgUpload(PersonalViewModel.TYPE_UPLOAD_HEADER_PIC,
                            "1",
                            file,
                            new PersonalViewModel.IImgUploadNotify() {
                        @Override
                        public void onLoading() {
                            showProgressDialog(R.string.file_operating);
                        }

                        @Override
                        public void onLoadingNext() {
                            dismissProgressDialog();
                            showProgressDialog(R.string.uploading);
                        }

                        @Override
                        public void onComplete() {
                            dismissProgressDialog();
                        }

                        @Override
                        public void onError() {
                            dismissProgressDialog();
                        }

                        @Override
                        public void onResult(Object o) {
                            isNeedNotifyUpdateUserInfo = true;
                            loadData();
                        }
                    });
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isNeedNotifyUpdateUserInfo) {
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_UPDATE_USER_INFO));
        }
    }

    private void uploadHeader(String picSource) throws MalformedURLException {
        Base64UploadRequest uploadRequest = new Base64UploadRequest(this_, "");
        uploadRequest.addParameter("type", "3");
        uploadRequest.addParameter("user_id", Account.get().getUserId());
        uploadRequest.addParameter("pic", picSource);
        uploadRequest.startUpload();
    }
}

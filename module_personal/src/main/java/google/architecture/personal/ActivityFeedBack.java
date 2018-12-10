package google.architecture.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.king.android.res.config.ARouterPath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.ViewManager;
import google.architecture.common.imgpicker.Constant;
import google.architecture.common.imgpicker.activity.ImagePickActivity;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.NetworkUtils;
import google.architecture.common.viewmodel.PersonalViewModel;
import google.architecture.common.viewmodel.UserViewModel;
import google.architecture.common.widget.adapter.ImgUploadAdapter;
import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.ImageFile;
import google.architecture.personal.databinding.ActivityFeedBackBinding;

import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_AUTO_SELECTED;
import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_NEED_CAMERA;
import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_NEED_FOLDER_LIST;
import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_NEED_IMAGE_CROP;

/**
 * @author lq.zeng
 * @date 2018/10/24
 */
@Route(path = ARouterPath.PersonalFeedBackAty)
public class ActivityFeedBack extends BaseActivity<ActivityFeedBackBinding> {

    private UserViewModel userViewModel;
    private PersonalViewModel personalViewModel;
    private List<ImageFile> imgUploads;
    private ImgUploadAdapter imgUploadAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(true);
        setTitleName(getString(R.string.feed_back_title));

        setRightText(getResources().getString(R.string.str_commit), v -> {
            StringBuilder imgStringBuilder = new StringBuilder();
            if(imgUploads != null && imgUploads.size() > 0) {
                for (ImageFile imageFile : imgUploads) {
                    if(MultiItemTypeHelper.TYPE_IMG_UPLOAD == imageFile.getItemType()) {
                        imgStringBuilder.append(NetworkUtils.getEncodeParam(imageFile.getUrl())).append(",");
                    }
                }
            }
            String ids = imgStringBuilder.toString();
            int start = ids.length() - 1;
            int end = ids.length();
            if(ids.length() > 0 && ids.substring(start, end).equals(",")) ids = ids.substring(0, start);
            if(userViewModel != null) userViewModel.commitFeedBack(binding.editText1.getText().toString(), ids, (code, msg) -> {
                if(code == 0) {
                    ViewManager.getInstance().finishActivity();
                }
            });
        });
        personalViewModel = new PersonalViewModel();
        registerLifeCycleListener(personalViewModel);

        userViewModel = new UserViewModel();
        addRunStatusChangeCallBack(userViewModel);

        imgUploads = new ArrayList<>();
        imgUploads.add(new ImageFile(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER));
        //上传
        binding.imgUploadRv.setNestedScrollingEnabled(false);
        if(binding.imgUploadRv.getItemDecorationCount() > 0) binding.imgUploadRv.removeItemDecorationAt(0);
        binding.imgUploadRv.setLayoutManager(new GridLayoutManager(this_, 4));
        imgUploadAdapter = new ImgUploadAdapter(imgUploads);
        imgUploadAdapter.setImageUploadListener((position, path) -> {
            personalViewModel.imgUpload(PersonalViewModel.TYPE_UPLOAD_COMMENT_PIC,
                String.valueOf(position),
                new File(path),
                new PersonalViewModel.IImgUploadNotify() {
                    @Override
                    public void onLoading() {
                        LogUtils.tag("zlq").e("upload = onLoading");
                    }

                    @Override
                    public void onLoadingNext() {
                        LogUtils.tag("zlq").e("upload = onLoadingNext");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.tag("zlq").e("upload = onComplete");
                    }

                    @Override
                    public void onError() {
                        LogUtils.tag("zlq").e("upload = onError");
                    }

                    @Override
                    public void onResult(Object o) {
                        String picUrl = (String)o;
                        LogUtils.tag("zlq").e("upload = onResult, url = " + picUrl);
                        imgUploadAdapter.getData().get(position).setUrl(picUrl);
                        imgUploadAdapter.getData().get(position).setUploadState(ImageFile.STATE_UPLOADED);
                        imgUploadAdapter.notifyItemChanged(position);
                    }
                });
        });
        imgUploadAdapter.setOnItemClickListener((adapter, view, position) -> {
            if(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER == adapter.getItemViewType(position)) {
                //过滤跳转
                Intent intent1 = new Intent(this_, ImagePickActivity.class);
                intent1.putExtra(Constant.MAX_NUMBER, ImgUploadAdapter.MAX_UPLOAD_IMG_SIZE);
                intent1.putExtra(IS_NEED_CAMERA, true);
                intent1.putExtra(IS_NEED_FOLDER_LIST, true);
                intent1.putExtra(IS_NEED_IMAGE_CROP, false);
                if(imgUploads != null && imgUploads.size() > 0) {
                    ArrayList<ImageFile> files = new ArrayList<>();
                    for (ImageFile imageFile : imgUploads) {
                        if(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER != imageFile.getItemType()) {
                            files.add(imageFile);
                        }
                    }
                    intent1.putParcelableArrayListExtra(IS_AUTO_SELECTED, files);
                }
                startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
            }
        });
        binding.imgUploadRv.setAdapter(imgUploadAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {
            if(resultCode == RESULT_OK) {
                ArrayList<ImageFile> imageFiles = data.getParcelableArrayListExtra(CommKeyUtil.EXTRA_KEY);
                if(imageFiles != null && imageFiles.size() > 0) {
                    imgUploads.clear();
                    imgUploads.addAll(imageFiles);
                    if(imageFiles.size() < ImgUploadAdapter.MAX_UPLOAD_IMG_SIZE) { //最大选择，去除首个
                        imgUploads.add(new ImageFile(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER));
                    }
                    imgUploadAdapter.notifyItemRangeChanged(0, imgUploads.size(), imgUploads);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(personalViewModel != null) personalViewModel.clear();
    }
}

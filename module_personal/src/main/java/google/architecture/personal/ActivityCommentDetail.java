package google.architecture.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.king.android.res.config.ARouterPath;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.ViewManager;
import google.architecture.common.imgpicker.Constant;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.NetworkUtils;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.DetailCommentViewModel;
import google.architecture.common.viewmodel.PersonalViewModel;
import google.architecture.common.widget.adapter.ImgUploadAdapter;
import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.CommentDetailData;
import google.architecture.coremodel.data.ImageFile;
import google.architecture.coremodel.data.OrderRemoteGoodsData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.personal.adapter.CommentDetailAdapter;
import google.architecture.personal.databinding.ActivityCommentCommitBinding;

/**
 * @author lq.zeng
 * @date 2018/9/29
 */
@Route(path = ARouterPath.PersonalShoppingCommentDetailAty)
public class ActivityCommentDetail extends BaseActivity<ActivityCommentCommitBinding> {

    private DetailCommentViewModel detailCommentViewModel;
    private PersonalViewModel personalViewModel;
    private CommentDetailAdapter adapter;

    private String orderId;

    @Override
    protected int getLayout() {
        return R.layout.activity_comment_commit;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanBack(true);
        setTitleName(getString(R.string.personal_my_comment_commit_title));

        setRightText(getResources().getString(R.string.str_commit), v -> {
            //提交
            if(adapter == null || adapter.getData() == null || adapter.getData().size() == 0) {
                ToastUtils.showShortToast("提交失败");
                return;
            }

            List<DetailCommentViewModel.CommentJson> commentJsons = new ArrayList<>();
            for (OrderRemoteGoodsData orderRemoteGoodsData : adapter.getData()) {
                DetailCommentViewModel.CommentJson commentJson = new DetailCommentViewModel.CommentJson();
                commentJson.setGoods_id(orderRemoteGoodsData.getGoods_id());
                commentJson.setItem_ids(orderRemoteGoodsData.getItem_ids());
                commentJson.setContent(NetworkUtils.getEncodeParam(orderRemoteGoodsData.commentInputValue));
                if(orderRemoteGoodsData.imgUploads != null && orderRemoteGoodsData.imgUploads.size() > 0) {
                    List<String> images = new ArrayList<>();
                    for (ImageFile imageFile : orderRemoteGoodsData.imgUploads) {
                        if(MultiItemTypeHelper.TYPE_IMG_UPLOAD == imageFile.getItemType()) {
                            images.add(NetworkUtils.getEncodeParam(imageFile.getUrl()));
                        }
                    }
                    commentJson.setImage(images);
                } else {
                    commentJson.setImage(new ArrayList<>());
                }
                commentJson.setSatisfy_rank(orderRemoteGoodsData.commentLevel);
                commentJson.setDeliver_rank("1");
                commentJson.setService_rank("1");
                commentJson.setGoods_rank("1");
                commentJsons.add(commentJson);
            }
            Gson gson = new Gson();
            String jsonStr = gson.toJson(commentJsons);
            LogUtils.tag("zlq").e("jsonStr = " + jsonStr);
            detailCommentViewModel.commitComment(orderId, jsonStr, "1",  (code, msg) -> {
                if(code == 0) {
                    EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_NOTIFY_UPDATE));
                    ViewManager.getInstance().finishActivity();
                }
            });
        });

        orderId = getIntent().getStringExtra(CommKeyUtil.EXTRA_KEY);

        personalViewModel = new PersonalViewModel();
        registerLifeCycleListener(personalViewModel);
        adapter = new CommentDetailAdapter(R.layout.item_comment_detail, new ArrayList<>(), personalViewModel);
        binding.commentDetailRv.setLayoutManager(new LinearLayoutManager(this_));
        binding.commentDetailRv.setAdapter(adapter);

        detailCommentViewModel = new DetailCommentViewModel();
        addRunStatusChangeCallBack(detailCommentViewModel);

        loadData();
    }

    private void loadData() {
        detailCommentViewModel.getCommentDetail(orderId);
    }

    @Override
    protected void onDataResult(Object o) {
        CommentDetailData commentDetailData = (CommentDetailData) o;
        for (int i = 0; i < commentDetailData.getShop_list().size(); i ++) {
            commentDetailData.getShop_list().get(i).imgUploads = new ArrayList<>();
            commentDetailData.getShop_list().get(i).imgUploads.add(new ImageFile(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER));
        }
        adapter.setNewData(commentDetailData.getShop_list());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {
            if(resultCode == RESULT_OK) {
                ArrayList<ImageFile> imageFiles = data.getParcelableArrayListExtra(CommKeyUtil.EXTRA_KEY);
                if(imageFiles != null && imageFiles.size() > 0) {
                    adapter.getData().get(adapter.getUploadPosition()).imgUploads.clear();
                    adapter.getData().get(adapter.getUploadPosition()).imgUploads.addAll(imageFiles);
                    if(imageFiles.size() < ImgUploadAdapter.MAX_UPLOAD_IMG_SIZE) { //最大选择，去除首个
                        adapter.getData().get(adapter.getUploadPosition()).imgUploads.add(new ImageFile(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER));
                    }
                    adapter.notifyItemChanged(adapter.getUploadPosition(), adapter.getData().get(adapter.getUploadPosition()));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterLifeCycleListener(personalViewModel);
    }
}

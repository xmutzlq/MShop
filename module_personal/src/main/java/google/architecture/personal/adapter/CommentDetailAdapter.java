package google.architecture.personal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.imgpicker.Constant;
import google.architecture.common.imgpicker.activity.ImagePickActivity;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.viewmodel.PersonalViewModel;
import google.architecture.common.widget.adapter.ImgUploadAdapter;
import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.ImageFile;
import google.architecture.coremodel.data.OrderRemoteGoodsData;
import google.architecture.coremodel.util.TextUtil;
import google.architecture.personal.R;

import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_AUTO_SELECTED;
import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_NEED_CAMERA;
import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_NEED_FOLDER_LIST;
import static google.architecture.common.imgpicker.activity.ImagePickActivity.IS_NEED_IMAGE_CROP;

/**
 * @author lq.zeng
 * @date 2018/9/29
 */

public class CommentDetailAdapter extends BaseQuickAdapter<OrderRemoteGoodsData, BaseViewHolder> {

    private static final int MAX_INPUT_LIMIT = 500;

    private PersonalViewModel personalViewModel;
    private int uploadPosition;

    public CommentDetailAdapter(int layoutResId, @Nullable List<OrderRemoteGoodsData> data, PersonalViewModel personalViewModel) {
        super(layoutResId, data);
        this.personalViewModel = personalViewModel;
    }

    public int getUploadPosition() {
        return uploadPosition;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderRemoteGoodsData item) {

        //上传
        RecyclerView uploadRv = helper.getView(R.id.img_upload_rv);
        uploadRv.setNestedScrollingEnabled(false);
        if(uploadRv.getItemDecorationCount() > 0) uploadRv.removeItemDecorationAt(0);
        uploadRv.setLayoutManager(new GridLayoutManager(mContext, 4));
        ImgUploadAdapter imgUploadAdapter = new ImgUploadAdapter(item.imgUploads);
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
                uploadPosition = helper.getAdapterPosition();
                Intent intent1 = new Intent(mContext, ImagePickActivity.class);
                intent1.putExtra(Constant.MAX_NUMBER, ImgUploadAdapter.MAX_UPLOAD_IMG_SIZE);
                intent1.putExtra(IS_NEED_CAMERA, true);
                intent1.putExtra(IS_NEED_FOLDER_LIST, true);
                intent1.putExtra(IS_NEED_IMAGE_CROP, false);
//                intent1.putExtra(IS_TAKEN_AUTO_SELECTED, true);
                if(getData().get(uploadPosition).imgUploads != null && getData().get(uploadPosition).imgUploads.size() > 0) {
                    ArrayList<ImageFile> files = new ArrayList<>();
                    for (ImageFile imageFile : getData().get(uploadPosition).imgUploads) {
                        if(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER != imageFile.getItemType()) {
                            files.add(imageFile);
                        }
                    }
                    intent1.putParcelableArrayListExtra(IS_AUTO_SELECTED, files);
                }
                if (mContext instanceof Activity) {
                    ((Activity) mContext).startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
                }
            }
        });
        uploadRv.setAdapter(imgUploadAdapter);

        ImageLoader.get().loadBySize(helper.getView(R.id.goods_img_iv), item.getImage(), DimensionsUtil.dip2px(mContext, 60), DimensionsUtil.dip2px(mContext, 60));
        helper.setText(R.id.goods_name_tv, item.getGoods_name());

        TextView greatTv = helper.getView(R.id.goods_comment_great_btn);
        TextView goodTv = helper.getView(R.id.goods_comment_good_btn);
        TextView badTv = helper.getView(R.id.goods_comment_bad_btn);
        greatTv.setTag("1");
        goodTv.setTag("2");
        badTv.setTag("3");
        if(item.commentLevel.equals(greatTv.getTag())) {
            greatTv.setSelected(true);
            goodTv.setSelected(false);
            badTv.setSelected(false);
        } else if(item.commentLevel.equals(goodTv.getTag())) {
            greatTv.setSelected(false);
            goodTv.setSelected(true);
            badTv.setSelected(false);
        } else if(item.commentLevel.equals(badTv.getTag())) {
            greatTv.setSelected(false);
            goodTv.setSelected(false);
            badTv.setSelected(true);
        }
        greatTv.setOnClickListener(v -> {
            item.commentLevel = (String)v.getTag();
            greatTv.setSelected(true);
            goodTv.setSelected(false);
            badTv.setSelected(false);
        });
        goodTv.setOnClickListener(v -> {
            item.commentLevel = (String)v.getTag();
            greatTv.setSelected(false);
            goodTv.setSelected(true);
            badTv.setSelected(false);
        });
        badTv.setOnClickListener(v -> {
            item.commentLevel = (String)v.getTag();
            greatTv.setSelected(false);
            goodTv.setSelected(false);
            badTv.setSelected(true);
        });
        EditText editText = helper.getView(R.id.editText1);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_LIMIT)});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.commentInputValue = s.toString();
                int needInputMinSize = TextUtil.isEmpty(s.toString()) ? 0 : s.toString().length();
                updateMinInputSize(needInputMinSize, helper.getView(R.id.watch_text_count_tv));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        editText.setText(item.commentInputValue);
    }

    private void updateMinInputSize(int inputSize, TextView textView) {
        if(inputSize == 0) {
            textView.setText(mContext.getString(R.string.input_min_size, OrderRemoteGoodsData.min_input_size));
        } else if(inputSize < OrderRemoteGoodsData.min_input_size) {
            textView.setText(mContext.getString(R.string.input_min_size, OrderRemoteGoodsData.min_input_size - inputSize));
        } else {
            textView.setText(inputSize + "/" + MAX_INPUT_LIMIT);
        }
    }
}

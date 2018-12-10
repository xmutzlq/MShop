package google.architecture.common.widget.adapter;

import android.support.annotation.NonNull;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.res.application.BaseApp;

import java.io.File;
import java.util.List;

import google.architecture.common.R;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.ImageFile;
import google.architecture.coremodel.datamodel.http.progress.ProgressListener;
import google.architecture.coremodel.datamodel.http.progress.body.ProgressInfo;

/**
 * @author lq.zeng
 * @date 2018/9/30
 */

public class ImgUploadAdapter extends BaseMultiItemQuickAdapter<ImageFile, BaseViewHolder> {

    public static final int MAX_UPLOAD_IMG_SIZE = 5;
    private ProgressInfo mLastUploadingingInfo;
    private ImageUploadListener imageUploadListener;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ImgUploadAdapter(List<ImageFile> data) {
        super(data);
        addItemType(MultiItemTypeHelper.TYPE_IMG_UPLOAD, R.layout.item_img_upload);
        addItemType(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER, R.layout.item_img_upload_add);
    }

    public void setImageUploadListener(ImageUploadListener imageUploadListener) {
        this.imageUploadListener = imageUploadListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageFile item) {
        switch (helper.getItemViewType()) {
            case MultiItemTypeHelper.TYPE_IMG_UPLOAD: //商家
                final ImageFile lv0 = item;
                ImageLoader.get().loadRoundCorner(helper.getView(R.id.iv_img_upload), new File(lv0.getPath()));
                helper.getView(R.id.iv_img_delete).setOnClickListener(v -> {
                    if(helper.getAdapterPosition() < 0)return;
                    getData().remove(helper.getAdapterPosition());
                    if(!isContains(getData()) && getData().size() < MAX_UPLOAD_IMG_SIZE) { //最大选择，去除首个
                        getData().add(new ImageFile(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER));
                    }
                    notifyItemRemoved(helper.getAdapterPosition());
                });

                //图片状态
                if(ImageFile.STATE_UPLOADED == lv0.getUploadState()) {
                    helper.setGone(R.id.iv_img_delete, true);
                    helper.setGone(R.id.pb_img_upload, false);
                } else {
                    helper.setGone(R.id.iv_img_delete, false);
                    helper.setGone(R.id.pb_img_upload, true);
                }

                //上传图片
                if(ImageFile.STATE_UPLOADING == lv0.getUploadState()) {
//                    ProgressManager.getInstance().setKeyReadyListener(key -> {
//                        ProgressManager.getInstance().addRequestListener(new String(key), getUploadListener());
//                    });
                    if(imageUploadListener != null) {
                        imageUploadListener.onImageUpload(helper.getAdapterPosition(), lv0.getPath());
                    }
                }
                break;
        }
    }

    private boolean isContains(List<ImageFile> data) {
        for (ImageFile imageFile : data) {
            if(MultiItemTypeHelper.TYPE_IMG_UPLOAD_HOLDER == imageFile.getItemType()) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    private ProgressListener getUploadListener() {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                // 如果你不屏蔽用户重复点击上传或下载按钮,就可能存在同一个 Url 地址,上一次的上传或下载操作都还没结束,
                // 又开始了新的上传或下载操作,那现在就需要用到 id(请求开始时的时间) 来区分正在执行的进度信息
                // 这里我就取最新的上传进度用来展示,顺便展示下 id 的用法

                if (mLastUploadingingInfo == null) {
                    mLastUploadingingInfo = progressInfo;
                }

                //因为是以请求开始时的时间作为 Id ,所以值越大,说明该请求越新
                if (progressInfo.getId() < mLastUploadingingInfo.getId()) {
                    return;
                } else if (progressInfo.getId() > mLastUploadingingInfo.getId()) {
                    mLastUploadingingInfo = progressInfo;
                }

                int progress = mLastUploadingingInfo.getPercent();
//                mUploadProgress.setProgress(progress);
//                mUploadProgressText.setText(progress + "%");
                LogUtils.tag("zlq").e("--Upload-- " + progress + " %  " + mLastUploadingingInfo.getSpeed() + " byte/s  " + mLastUploadingingInfo.toString());
                if (mLastUploadingingInfo.isFinish()) {
                    //说明已经上传完成
                    LogUtils.tag("zlq").e("--Upload-- finish");
                }
            }

            @Override
            public void onError(long id, Exception e) {
                BaseApp.getHandler().post(() -> {
//                    mUploadProgress.setProgress(0);
//                    mUploadProgressText.setText("error");
                });
            }
        };
    }

    public interface ImageUploadListener {
        void onImageUpload(int position, String path);
    }
}

package google.architecture.common.viewmodel;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.util.ImageUtils;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.coremodel.data.UploadResultData;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import google.architecture.coremodel.util.Base64Encoder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * @author lq.zeng
 * @date 2018/4/11
 */

public class PersonalViewModel extends UIViewModel {

    public static final int TYPE_UPLOAD_HEADER_PIC = 0;
    public static final int TYPE_UPLOAD_COMMENT_PIC = 1;
    private static final int IMAGE_MAX_HEIGHT = 800; //800、1920
    private static final int IMAGE_MAX_WIDTH = 480; //480、1080

    public ObservableField<S_UserInfo> userInfo = new ObservableField<>();

    public void getUserInfo() {
        disposable.add(DeHongDataRepository.get().getUserInfo(Account.get().getUserId()).doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setDataObject(result.getData(), data)).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void getShoppingCategory() {
        disposable.add(DeHongDataRepository.get().getShoppingCategory().doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void modifyNickName(String nickName, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().modifyNickName(nickName, Account.get().getUserId()), messageResult);
    }

    /**
     * 删除用户足迹
     * @param goodsIds 多个Id用逗号隔开
     */
    public void delFootprints(String goodsIds, IMessageResult messageResult) {
        disposable.add(subscribe(DeHongDataRepository.get().delFootprints(Account.get().getUserId(), goodsIds), messageResult));
    }

    /**
     * 删除用户收藏
     * @param goodsId 多个Id用逗号隔开
     */
    public void delMyFavorites(String goodsId, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().delFavorites(goodsId, Account.get().getUserId()), messageResult);
    }

    public void uploadHeader(String id, String pic, IDoOnNext doOnNext) {
        uploadCommon("3", id, pic, doOnNext);
    }

    public void updateHeadPic(String picRemote, IMessageResult messageResult) {
        disposable.add(subscribe(DeHongDataRepository.get().updateHeadPic(Account.get().getUserId(), picRemote), messageResult));
    }

    public void uploadComment(String id, String pic, IDoOnNext doOnNext) {
        uploadCommon("1", id, pic, doOnNext);
    }

    public void uploadCommon(String type, String id, String pic, IDoOnNext doOnNext) {
        disposableMap.put(id, new CompositeDisposable());
        disposableMap.get(id).add(subscribe(DeHongDataRepository.get().upload(pic, type, Account.get().getUserId()), doOnNext));
    }

    public void imgUpload(int uploadType, String id, File file, IImgUploadNotify imgUploadNotify) {
        Observable.create(e -> {
            List<File> files = new ArrayList<>();
            files.add(file);
            files = Luban.with(BaseApplication.getIns()).load(files).get();
            Bitmap bitmap = ImageUtils.getBitmap(files.get(0)); //图像
            String type = ImageUtils.getImageType(file);
            if(TYPE_UPLOAD_HEADER_PIC != uploadType) {
                int sampleSize = 1; //默认缩放为1
                while ((bitmap.getHeight() / sampleSize > IMAGE_MAX_HEIGHT)
                        || (bitmap.getWidth() / sampleSize > IMAGE_MAX_WIDTH)) {
                    sampleSize *= 2;
                }
                LogUtils.tag("zlq").e("sampleSize = " + sampleSize);
                bitmap = ImageUtils.compressBySampleSize(bitmap, sampleSize);
            }
            byte[] resource = ImageUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.JPEG);
            String base64Resource = Base64Encoder.encode(resource);
            e.onNext(new String[] {type ,base64Resource});
        }).map(o -> {
            String[] source = (String[])o;
            return  "data:image/" + source[0] + ";base64," + source[1];
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {

            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                if(imgUploadNotify != null) {
                    imgUploadNotify.onLoading();
                }
            }

            @Override
            public void onNext(String s) {
                mDisposable.dispose();
                if(imgUploadNotify != null) {
                    imgUploadNotify.onLoadingNext();
                }
                switch (uploadType) {
                    case TYPE_UPLOAD_HEADER_PIC:
                        uploadHeader(id, s, t -> {
                            UploadResultData uploadResultData = (UploadResultData) t;
                            if(!TextUtils.isEmpty(uploadResultData.getPic())) {
                                updateHeadPic(uploadResultData.getPic(), (code, msg) -> {
                                    if(code == 0) {
                                        if(imgUploadNotify != null) {
                                            imgUploadNotify.onResult(null);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    case TYPE_UPLOAD_COMMENT_PIC:
                        LogUtils.tag("zlq").e("message = uploadComment[id = " + id + "]");
                        uploadComment(id, s, t -> {
                            LogUtils.tag("zlq").e("message = uploadComment_success");
                            UploadResultData uploadResultData = (UploadResultData) t;
                            if(!TextUtils.isEmpty(uploadResultData.getPic())) {
                                if(imgUploadNotify != null) {
                                    imgUploadNotify.onResult(uploadResultData.getPic());
                                }
                            }
                        });
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                mDisposable.dispose();
                if(imgUploadNotify != null) {
                    imgUploadNotify.onError();
                }
            }

            @Override
            public void onComplete() {
                mDisposable.dispose();
                if(imgUploadNotify != null) {
                    imgUploadNotify.onComplete();
                }
            }
        });
    }

    public interface IImgUploadNotify<T> {
        void onLoading();
        void onLoadingNext();
        void onComplete();
        void onError();
        void onResult(T t);
    }
}

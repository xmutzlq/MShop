package google.architecture.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kk.taurus.playerbase.assist.AssistPlay;
import com.kk.taurus.playerbase.assist.OnAssistPlayEventHandler;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.kk.taurus.playerbase.window.FloatWindow;
import com.kk.taurus.playerbase.window.FloatWindowParams;

import google.architecture.common.R;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.pay.DataInter;
import google.architecture.common.util.PUtil;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.common.viewmodel.xlj.PromotionViewModel;
import google.architecture.coremodel.data.xlj.PromotionMedia;

public class PromotionDialog extends Dialog {

    private final ImageView mShowImgIv;
    private final FrameLayout mVideoContainer;
    private PromotionViewModel mViewModel;
    private FrameLayout mWindowVideoContainer;
    private FloatWindow mFloatWindow;
    private RelationAssist mAssist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new PromotionViewModel();
    }

    public PromotionDialog(@NonNull Context context) {
        super(context,R.style.DialogStyle);
        setCanceledOnTouchOutside(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = ScreenUtils.getScreenWidth();
        params.height = ScreenUtils.getScreenHeight()-(ScreenUtils.getTitleHight(context)+ScreenUtils.getBackButtonHight(context));
        getWindow().setAttributes(params);
        setContentView(R.layout.dialog_promotion);
        View container = findViewById(R.id.container_layout);
        container.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(),(ScreenUtils.getScreenHeight()-(ScreenUtils.getTitleHight(context)/*+ScreenUtils.getBackButtonHight(context)*/))));
        mShowImgIv = findViewById(R.id.show_img_iv);
        mVideoContainer = findViewById(R.id.videoContainer);
        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromotionDialog.this.dismiss();
            }
        });
        initVideoView();
    }

    private void initVideoView(){
        int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
        int width = (int) (widthPixels * 0.8f);
        int height = getContext().getResources().getDisplayMetrics().heightPixels;

        int type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){//8.0+
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            type =  WindowManager.LayoutParams.TYPE_PHONE;
        }

        mWindowVideoContainer = new FrameLayout(getContext());
        mFloatWindow = new FloatWindow(getContext(), mWindowVideoContainer,
                new FloatWindowParams()
                        .setWindowType(type)
                        .setX(100)
                        .setY(400)
                        .setWidth(width)
                        .setHeight(height));
        mFloatWindow.setBackgroundColor(Color.BLACK);

        mAssist = new RelationAssist(getContext());
        mAssist.getSuperContainer().setBackgroundColor(Color.BLACK);
        mAssist.setEventAssistHandler(eventHandler);


    }


    private OnAssistPlayEventHandler eventHandler = new OnAssistPlayEventHandler(){
        @Override
        public void onAssistHandle(AssistPlay assist, int eventCode, Bundle bundle) {
            super.onAssistHandle(assist, eventCode, bundle);
            switch (eventCode){
                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                    onBackPressed();
                    break;
                case DataInter.Event.EVENT_CODE_ERROR_SHOW:
                    mAssist.stop();
                    break;

            }
        }
        @Override
        public void requestRetry(AssistPlay assist, Bundle bundle) {
            if(PUtil.isTopActivity((Activity) getContext())){
                super.requestRetry(assist, bundle);
            }
        }
    };


    @Override
    public void show() {
        super.show();
        mViewModel.getPromotionMedia(new UIViewModel.IDoOnNext() {
            @Override
            public void doOnNext(Object t) {
                PromotionMedia result = (PromotionMedia)t;
                if(result.getType().equalsIgnoreCase("img")){
                    mShowImgIv.setVisibility(View.VISIBLE);
                    mVideoContainer.setVisibility(View.GONE);
                    ImageLoader.get().load(mShowImgIv, result.getUrl(), new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            mShowImgIv.setBackground(resource);
                            return false;
                        }
                    });
                }else if(result.getType().equalsIgnoreCase("mp4")){
                    mShowImgIv.setVisibility(View.GONE);
                    mVideoContainer.setVisibility(View.VISIBLE);

                    DataSource dataSource = new DataSource();
                    dataSource.setData(/*result.getUrl()*/"https://mov.bn.netease.com/open-movie/nos/mp4/2016/01/11/SBC46Q9DV_hd.mp4");
                    dataSource.setTitle("标题");

                    mAssist.setDataSource(dataSource);
                    mAssist.attachContainer(mVideoContainer);
                    mAssist.play();
                }

            }
        });
    }

    private void closeWindow(){
        if(mFloatWindow.isWindowShow()){
            mFloatWindow.close();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeWindow();
    }
}

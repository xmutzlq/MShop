package google.architecture.common.promotion;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.king.android.res.config.ARouterPath;
import com.kk.taurus.playerbase.assist.AssistPlay;
import com.kk.taurus.playerbase.assist.OnAssistPlayEventHandler;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.window.FloatWindow;
import com.kk.taurus.playerbase.window.FloatWindowParams;

import google.architecture.common.R;
import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseActivityFrame;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.pay.DataInter;
import google.architecture.common.util.PUtil;
import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.common.viewmodel.xlj.PromotionViewModel;
import google.architecture.coremodel.data.xlj.PromotionMedia;

@Route(path = ARouterPath.PromotionAty)
public class ActivityPromotion extends BaseActivityFrame {
    private FloatWindow mFloatWindow;
    private FrameLayout mWindowVideoContainer;
    private RelationAssist mAssist;
    private FrameLayout mVideoContainer;
    private ImageView mShowImgIv;

    private PromotionViewModel mPromotionViewModel;

    /*@Override
    protected int getLayout() {
        return R.layout.activity_promotion;
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_promotion);
        mPromotionViewModel = new PromotionViewModel();

        mVideoContainer = findViewById(R.id.videoContainer);
        mShowImgIv = findViewById(R.id.show_img_iv);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int width = (int) (widthPixels * 0.8f);
        int height = getResources().getDisplayMetrics().heightPixels;

        int type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){//8.0+
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            type =  WindowManager.LayoutParams.TYPE_PHONE;
        }

        mWindowVideoContainer = new FrameLayout(this);
        mFloatWindow = new FloatWindow(this, mWindowVideoContainer,
                new FloatWindowParams()
                        .setWindowType(type)
                        .setX(100)
                        .setY(400)
                        .setWidth(width)
                        .setHeight(width*9/16));
        mFloatWindow.setBackgroundColor(Color.BLACK);

        mAssist = new RelationAssist(this);
        mAssist.getSuperContainer().setBackgroundColor(Color.BLACK);
        mAssist.setEventAssistHandler(eventHandler);

//        DataSource dataSource = new DataSource();
//        dataSource.setData("https://images.s.cn/images/20190104/demo2.mp4");
//        dataSource.setTitle("神奇的珊瑚");
//
//        mAssist.setDataSource(dataSource);
//        mAssist.attachContainer(mVideoContainer, true);
        mAssist.setOnPlayerEventListener(new OnPlayerEventListener() {
            @Override
            public void onPlayerEvent(int eventCode, Bundle bundle) {
                if(eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET){
                    System.out.println("======szq======PLAYER_EVENT_ON_DATA_SOURCE_SET");
                }else if(eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_START){
                    System.out.println("======szq======PLAYER_EVENT_ON_BUFFERING_START");
                }else if(eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_END){
                    System.out.println("======szq======PLAYER_EVENT_ON_BUFFERING_END");
                }else if(eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_START){
                    System.out.println("======szq======PLAYER_EVENT_ON_START");
                }else if(eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_STOP){
                    System.out.println("======szq======PLAYER_EVENT_ON_STOP");
                }else if(eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_DESTROY){
                    System.out.println("======szq======PLAYER_EVENT_ON_DESTROY");
                }else if(eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE){//视频播放完毕
                    System.out.println("======szq======PLAYER_EVENT_ON_PLAY_COMPLETE");
                    mAssist.play();
                }
            }
        });
//        mAssist.play();

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadData();
    }

    private void loadData(){
        mPromotionViewModel.getPromotionMedia(new UIViewModel.IDoOnNext() {
            @Override
            public void doOnNext(Object t) {
                PromotionMedia result = (PromotionMedia)t;
                if(result.getType().equalsIgnoreCase("imgs")){
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
                }else if(result.getType().equalsIgnoreCase("img")){
                    mShowImgIv.setVisibility(View.GONE);
                    mVideoContainer.setVisibility(View.VISIBLE);

                    DataSource dataSource = new DataSource();
                    dataSource.setData(result.getUrl());
                    dataSource.setTitle("标题");

                    mAssist.setDataSource(dataSource);
                    mAssist.attachContainer(mVideoContainer, true);
                    mAssist.play();

                }
            }
        });
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
            if(PUtil.isTopActivity(ActivityPromotion.this)){
                super.requestRetry(assist, bundle);
            }
        }
    };

    private void closeWindow(){
        if(mFloatWindow.isWindowShow()){
            mFloatWindow.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeWindow();
        mAssist.destroy();
    }

}

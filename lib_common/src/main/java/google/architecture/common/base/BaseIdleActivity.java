package google.architecture.common.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import google.architecture.common.dialog.PromotionDialog;

/**
 * 一个用于检测空闲时间的activity类
 */
public abstract class BaseIdleActivity extends BaseActivityFrame {

    private final int WHAT_SHOW_DIALOG = 23;
    private final long waitTimeMilli = 10*1000;//空闲固定秒数
    private PromotionDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new PromotionDialog(this);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                restTime();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        restTime();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_SHOW_DIALOG:{
                    if(!mDialog.isShowing()){
                        mDialog.show();
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if(mHandler != null && mHandler.hasMessages(WHAT_SHOW_DIALOG)){
            mHandler.removeMessages(WHAT_SHOW_DIALOG);
        }
    }

    private void restTime(){
        if(mHandler.hasMessages(WHAT_SHOW_DIALOG)){
            mHandler.removeMessages(WHAT_SHOW_DIALOG);
        }
        Message msg = mHandler.obtainMessage(WHAT_SHOW_DIALOG);
        mHandler.sendMessageDelayed(msg,waitTimeMilli);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mHandler.removeMessages(WHAT_SHOW_DIALOG);
            }
            case MotionEvent.ACTION_UP:{
                restTime();
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mHandler.removeMessages(WHAT_SHOW_DIALOG);
            }
            case MotionEvent.ACTION_UP:{
                restTime();
                break;
            }
        }
        return super.onTouchEvent(event);
    }
}

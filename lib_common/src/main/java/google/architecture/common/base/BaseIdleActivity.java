package google.architecture.common.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;

import google.architecture.common.base.listener.KeyboardChangeListener;
import google.architecture.common.dialog.PromotionDialog;
import google.architecture.coremodel.datamodel.http.ApiConstants;

/**
 * 一个用于检测空闲时间的activity类
 */
public abstract class BaseIdleActivity extends BaseActivityFrame {

    private final int WHAT_SHOW_DIALOG = 23;
    private PromotionDialog mDialog;

    private boolean isPause = false;
    private boolean isDestory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new PromotionDialog(this);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(!isPause) {
                    restTime();
                }
            }
        });


        KeyboardChangeListener softKeyboardStateHelper = new KeyboardChangeListener(this);
        softKeyboardStateHelper.setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (isShow) {
                    //键盘的弹出
                    mHandler.removeMessages(WHAT_SHOW_DIALOG);
                } else {
                    //键盘的收起
                    restTime();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        restTime();
        isPause = false;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_SHOW_DIALOG:{
                    if(!mDialog.isShowing() && !isDestory){
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
        isPause = true;
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    private void restTime(){
        if(mHandler.hasMessages(WHAT_SHOW_DIALOG)){
            mHandler.removeMessages(WHAT_SHOW_DIALOG);
        }
        Message msg = mHandler.obtainMessage(WHAT_SHOW_DIALOG);
        mHandler.sendMessageDelayed(msg,ApiConstants.IDLE_SECOND*1000);//空闲固定秒数
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getAction()){
            case KeyEvent.ACTION_DOWN:
                mHandler.removeMessages(WHAT_SHOW_DIALOG);
                break;
            case KeyEvent.ACTION_UP:
                restTime();
                break;
        }
        return super.dispatchKeyEvent(event);
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

    @Override
    protected void onDestroy() {
        isDestory = true;
        super.onDestroy();
        if(mDialog != null) {
            mDialog.closeWindow();
        }
    }
}

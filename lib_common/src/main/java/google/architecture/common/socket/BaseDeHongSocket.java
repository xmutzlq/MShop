package google.architecture.common.socket;

import android.os.Handler;
import android.os.Looper;

/**
 * Socket基类
 * @author zlq
 * @date 2016年11月14日 上午11:29:34
 */
public abstract class BaseDeHongSocket {
    private Handler mUIHandler;
    protected Object lock;

    public BaseDeHongSocket() {
        mUIHandler = new Handler(Looper.getMainLooper());
        lock = new Object();
    }

    protected void runOnUiThread(Runnable runnable) {
        mUIHandler.post(runnable);
    }
}

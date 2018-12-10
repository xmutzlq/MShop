package google.architecture.common.imgloader.listener;

/**
 * @author lq.zeng
 * @date 2018/5/4
 */

public interface ProgressLoadListener {
    void update(int bytesRead, int contentLength);

    void onException();

    void onResourceReady();
}

package google.architecture.common.imgpicker.adapter;

/**
 * @author lq.zeng
 * @date 2018/9/17
 */

public interface OnSelectStateListener<T> {
    void OnSelectStateChanged(boolean state, T file);
}

package google.architecture.common.widget.address;

import java.util.ArrayList;

/**
 * @author lq.zeng
 * @date 2018/5/29
 */

public interface SelectedListener {
    /**
     * 回调接口，根据选择深度，按顺序返回选择的对象。
     * */
    void onAddressSelected(ArrayList<ISelectAble> selectAbles);
}

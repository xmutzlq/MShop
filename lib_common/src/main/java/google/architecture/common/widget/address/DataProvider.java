package google.architecture.common.widget.address;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/5/29
 */

public interface DataProvider {
    void provideData(int currentDeep, int preId, DataReceiver receiver);

    interface DataReceiver {
        void send(List<ISelectAble> data);
    }
}

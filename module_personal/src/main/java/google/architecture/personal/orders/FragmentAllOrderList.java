package google.architecture.personal.orders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */
@Route(path = ARouterPath.PersonalAllOrderListFgt)
public class FragmentAllOrderList extends FragmentMyOrderList {

    @Override
    protected String getOrderState() {
        return "0";
    }
}

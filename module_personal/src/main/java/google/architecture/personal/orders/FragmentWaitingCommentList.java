package google.architecture.personal.orders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

/**
 * @author lq.zeng
 * @date 2018/9/29
 */
@Route(path = ARouterPath.PersonalWaitingCommentListFgt)
public class FragmentWaitingCommentList extends FragmentMyOrderList {

    @Override
    protected String getOrderState() {
        return "4";
    }
}

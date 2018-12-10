package google.architecture.home.substance;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;

import org.greenrobot.eventbus.EventBus;

import google.architecture.common.util.CommKeyUtil;
import google.architecture.coremodel.data.HomeItemsAction;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/10/25
 */

public class HomeGoHelper {

    public static void goGoodsDetail(Context context, View transitionLayout, HomeItemsAction action) {
        goPage(context, transitionLayout, action);
    }

    public static void goPage(Context context, HomeItemsAction action) {
        goPage(context, null, action);
    }

    public static void goPage(Context context, View transitionLayout, HomeItemsAction action) {
        if(context == null) return;
        if(action.getGoUrl() != null) { //跳网页
            ARouter.getInstance().build(ARouterPath.WebPage).withString(CommKeyUtil.EXTRA_KEY, action.getGoUrl().getUrl()).navigation(context);
        } else if(action.getGoGoodsDetail() != null) { //跳转商品详情
//            if(transitionLayout == null) return;
//            ActivityOptionsCompat optionsCompat = TransitionHelper.getTransitionOptionsCompat((Activity) context, transitionLayout);
            ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE, action.getGoGoodsDetail().getId())
//                    .withOptionsCompat(optionsCompat)
                    .navigation();
        } else if(action.getGoCategory() != null) { //跳转分类(id=0时，为分类首页)
            Bundle bundle = new Bundle();
            bundle.putInt(CommKeyUtil.EXTRA_KEY, 1);
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_HOME_GO, bundle));
        } else if(action.getGoCatList() != null) { //跳转至购物车(user_id=0时，需要先登录)
            Bundle bundle = new Bundle();
            bundle.putInt(CommKeyUtil.EXTRA_KEY, 2);
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_HOME_GO, bundle));
        } else if(action.getGoUserCenter() != null) { //跳转用户中心(user_id=0时，需要先登录)
            Bundle bundle = new Bundle();
            bundle.putInt(CommKeyUtil.EXTRA_KEY, 3);
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_HOME_GO, bundle));
        } else if(action.getGoUserMessage() != null) { //跳转用户消息中心(user_id=0时，需要先登录)

        } else if(action.getGoUserMessageDetail() != null) { //跳转用户某个消息(user_id=0时，需要先登录)

        } else if(action.getGoUserFeedback() != null) { //跳转用户反馈(user_id=0时，需要先登录)
            ARouter.getInstance().build(ARouterPath.PersonalFeedBackAty).navigation(context);
        }
    }
}

package google.architecture.common.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.king.android.res.config.ARouterPath;

import java.lang.ref.WeakReference;

import google.architecture.common.util.CommKeyUtil;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/11/2
 */

public class JavaScriptObject implements JsCallJavaObj {

    private WeakReference<Activity> mContext;

    public JavaScriptObject(Activity context) {
        super();
        mContext = new WeakReference<Activity>(context);
    }

    /**
     * 跳转网页
     */
    @JavascriptInterface
    @Override
    public void goUrl(String url) {
        LogUtils.tag("zlq").e("goUrl_url = " + url);
        ARouter.getInstance().build(ARouterPath.WebPage).withString(CommKeyUtil.EXTRA_KEY, url).navigation(mContext.get());
    }

    @JavascriptInterface
    @Override
    public void goGoodsDetail(String goodsId) {
        LogUtils.tag("zlq").e("goGoodsDetail_goodsId = " + goodsId);
        ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE, goodsId).navigation(mContext.get());
    }
}

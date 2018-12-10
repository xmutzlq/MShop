package com.king.android.sharesdk;

import android.content.Context;

import com.king.android.sharesdk.utils.LogUtil;
import com.king.android.sharesdk.utils.ShareSDKUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author lq.zeng
 * @date 2018/4/24
 */

public class AppShareMain implements PlatformActionListener {

    public static class Action {
        /*三方分享*/
        public interface IShare {
            /*微信朋友圈分享*/
            int SHARE_WXM = 0x10;
            /*微信分享*/
            int SHARE_WX = 0x11;
            /*新浪微博分享*/
            int SHARE_SINA = 0x12;
            /*QQ空间分享*/
            int SHARE_QZONE = 0x13;
            /*QQ分享*/
            int SHARE_QQ = 0x14;
        }

        /*三方登录*/
        public interface ILogin {
            /*QQ登录*/
            int LOGIN_QQ = 0x21;
            /*微信登录*/
            int LOGIN_WX = 0x22;
            /*新浪微博登录*/
            int LOGIN_SINA = 0x23;
        }
    }

    private int SharesdkType;
    private int ActionType;
    private String title;
    private String content;
    private String picUrl;

    private WeakReference<Context> mContext;
    private IShareResponse shareResponse;

    public AppShareMain(Context context) {
        mContext = new WeakReference<>(context);
    }

    public static AppShareMain build(Context context) {
        return new AppShareMain(context);
    }

    public AppShareMain widthTitle(String title) {
        this.title = title;
        return this;
    }

    public AppShareMain widthPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public AppShareMain widthContent(String content) {
        this.content = content;
        return this;
    }

    public AppShareMain withResponse(IShareResponse shareResponse) {
        this.shareResponse = shareResponse;
        return this;
    }

    private boolean haveContext() {
        return mContext != null && mContext.get() != null;
    }

    private void showShare(String picurl) {
        if(!haveContext()) return;
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setText(content);
        if (picurl != null) {
            oks.setImageUrl(picurl);
        }
        // 启动分享GUI
        oks.show(mContext.get());
    }

    public void actionWhat(int action) {
        ActionType = action;
        switch (action) {
            case Action.ILogin.LOGIN_QQ://QQ登录
                SharesdkType = ShareSDKUtils.LOGINTYPE;
                ShareSDKUtils.Login(QQ.NAME, this);
                break;
            case Action.ILogin.LOGIN_WX://微信登录
                SharesdkType = ShareSDKUtils.LOGINTYPE;
                ShareSDKUtils.Login(Wechat.NAME, this);
                break;
            case Action.ILogin.LOGIN_SINA://微博登录
                SharesdkType = ShareSDKUtils.LOGINTYPE;
                ShareSDKUtils.Login(SinaWeibo.NAME, this);
                break;
            case Action.IShare.SHARE_WXM://朋友圈分享(微信需要有图片)
                SharesdkType = ShareSDKUtils.SHARETYPE;
                ShareSDKUtils.shareWXM(title, title, picUrl, "http://www.haidu.com", this);
                break;
            case Action.IShare.SHARE_WX://微信分享(微信需要有图片)
                SharesdkType = ShareSDKUtils.SHARETYPE;
                ShareSDKUtils.shareWX(title, content, picUrl, "http://www.haidu.com", this);
                break;
            case Action.IShare.SHARE_SINA://微博分享
                SharesdkType = ShareSDKUtils.SHARETYPE;
                ShareSDKUtils.shareSina(content, picUrl, this);
                break;
            case Action.IShare.SHARE_QZONE://空间
                SharesdkType = ShareSDKUtils.SHARETYPE;
                ShareSDKUtils.shareQzone(title, content, picUrl, "http://www.haidu.com", this);
                break;
            case Action.IShare.SHARE_QQ://QQ
                SharesdkType = ShareSDKUtils.SHARETYPE;
                ShareSDKUtils.shareQQ(title, content, picUrl, "http://www.haidu.com", this);
                break;
            default:
                showShare(picUrl);
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (SharesdkType == ShareSDKUtils.LOGINTYPE) {
            LogUtil.Log_e("zlq", "登录成功");
            LogUtil.Log_e("zlq", "openid:" + platform.getDb().getUserId());//拿到登录后的openid
            LogUtil.Log_e("zlq", "username:" + platform.getDb().getUserName());//拿到登录后的昵称
            LogUtil.Log_e("zlq", "gender:" + platform.getDb().getUserGender());//拿到登录后的昵称
            LogUtil.Log_e("zlq", "head_url:" + platform.getDb().getUserIcon());//拿到登录后的昵称
            if(shareResponse != null) {
                shareResponse.onComplete(ActionType, platform);
            }
        } else {
            LogUtil.Log_e("zlq", "分享成功");
            if(shareResponse != null) {
                shareResponse.onComplete(ActionType,null);
            }
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        LogUtil.Log_e("zlq", SharesdkType == ShareSDKUtils.LOGINTYPE ? "登录失败" + throwable.toString() : "分享失败" + throwable.toString());
        if(shareResponse != null) {
            shareResponse.onError();
        }
    }

    @Override
    public void onCancel(Platform platform, int i) {
        LogUtil.Log_e("zlq", SharesdkType == ShareSDKUtils.LOGINTYPE ? "登录取消" : "分享取消");
        if(shareResponse != null) {
            shareResponse.onCancel();
        }
    }

    public static interface IShareResponse {
        public void onComplete(int actionType, Platform platform);
        public void onError();
        public void onCancel();
    }
}

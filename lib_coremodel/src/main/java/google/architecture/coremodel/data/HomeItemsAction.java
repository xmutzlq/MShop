package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/5
 */

public class HomeItemsAction {

    @SerializedName("goUrl")
    private ActionParams goUrl;
    @SerializedName("goGoodsDetail")
    private ActionParams goGoodsDetail;
    @SerializedName("goCategory")
    private ActionParams goCategory;
    @SerializedName("goCatList")
    private ActionParams goCatList;
    @SerializedName("goUserCenter")
    private ActionParams goUserCenter;
    @SerializedName("goUserMessage")
    private ActionParams goUserMessage;
    @SerializedName("goUserMessageDetail")
    private ActionParams goUserMessageDetail;
    @SerializedName("goUserFeedback")
    private ActionParams goUserFeedback;

    public ActionParams getGoUrl() {
        return goUrl;
    }

    public void setGoUrl(ActionParams goUrl) {
        this.goUrl = goUrl;
    }

    public ActionParams getGoGoodsDetail() {
        return goGoodsDetail;
    }

    public void setGoGoodsDetail(ActionParams goGoodsDetail) {
        this.goGoodsDetail = goGoodsDetail;
    }

    public ActionParams getGoCategory() {
        return goCategory;
    }

    public void setGoCategory(ActionParams goCategory) {
        this.goCategory = goCategory;
    }

    public ActionParams getGoCatList() {
        return goCatList;
    }

    public void setGoCatList(ActionParams goCatList) {
        this.goCatList = goCatList;
    }

    public ActionParams getGoUserCenter() {
        return goUserCenter;
    }

    public void setGoUserCenter(ActionParams goUserCenter) {
        this.goUserCenter = goUserCenter;
    }

    public ActionParams getGoUserMessage() {
        return goUserMessage;
    }

    public void setGoUserMessage(ActionParams goUserMessage) {
        this.goUserMessage = goUserMessage;
    }

    public ActionParams getGoUserMessageDetail() {
        return goUserMessageDetail;
    }

    public void setGoUserMessageDetail(ActionParams goUserMessageDetail) {
        this.goUserMessageDetail = goUserMessageDetail;
    }

    public ActionParams getGoUserFeedback() {
        return goUserFeedback;
    }

    public void setGoUserFeedback(ActionParams goUserFeedback) {
        this.goUserFeedback = goUserFeedback;
    }

    public static class ActionParams {
        @SerializedName("url")
        private String url; //跳转链接
        @SerializedName("id")
        private String id; //跳转分类(id=0时，为分类首页)
        @SerializedName("user_id")
        private String user_id; //跳转至购物车(user_id=0时，需要先登录)

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}

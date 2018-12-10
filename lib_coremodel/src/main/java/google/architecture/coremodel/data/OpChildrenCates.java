package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/6/6
 */

public class OpChildrenCates {
    @SerializedName("banner")
    private List<OpDiscoverCates> banner;  //Banner

    @SerializedName("webview")
    private List<OpDiscoverCates> webview; // WebView

    public List<OpDiscoverCates> getBanner() {

        return banner;
    }

    public void setBanner(List<OpDiscoverCates> banner) {

        this.banner = banner;
    }

    public List<OpDiscoverCates> getWebview() {

        return webview;
    }

    public void setWebview(List<OpDiscoverCates> webview) {

        this.webview = webview;
    }
}

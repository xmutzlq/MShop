package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/7/31
 */

public class HomeHeaderInfo {
    @SerializedName("type")
    private int type;
    @SerializedName("title")
    private String title;
    @SerializedName("text")
    private String text;
    @SerializedName("action")
    private HomeItemsAction action;

    @SerializedName("style")
    private HomeStyleInfo styleInfo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HomeItemsAction getAction() {
        return action;
    }

    public void setAction(HomeItemsAction action) {
        this.action = action;
    }

    public HomeStyleInfo getStyleInfo() {
        return styleInfo;
    }

    public void setStyleInfo(HomeStyleInfo styleInfo) {
        this.styleInfo = styleInfo;
    }

}

package google.architecture.coremodel.data.xlj.shopdata;

import com.google.gson.annotations.SerializedName;

public class TextInfo {
    @SerializedName("text")
    private String text;
    @SerializedName("urlids")
    private String urlids;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrlids() {
        return urlids;
    }

    public void setUrlids(String urlids) {
        this.urlids = urlids;
    }
}

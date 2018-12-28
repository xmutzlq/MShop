package google.architecture.coremodel.data.xlj.shopdata;

import com.google.gson.annotations.SerializedName;

public class ImgInfo {
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("urlids")
    private String urlids;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrlids() {
        return urlids;
    }

    public void setUrlids(String urlids) {
        this.urlids = urlids;
    }
}

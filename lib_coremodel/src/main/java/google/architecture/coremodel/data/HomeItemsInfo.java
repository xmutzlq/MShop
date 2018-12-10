package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/7/31
 */

public class HomeItemsInfo {
    @SerializedName("title")
    private String title;
    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("type")
    private int type;
    @SerializedName("action")
    private HomeItemsAction action;

    @SerializedName("price")
    private String price;
    @SerializedName("number")
    private String number;

    @SerializedName("style")
    private HomeItemStyle style;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public HomeItemsAction getAction() {
        return action;
    }

    public void setAction(HomeItemsAction action) {
        this.action = action;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public HomeItemStyle getStyle() {
        return style;
    }

    public void setStyle(HomeItemStyle style) {
        this.style = style;
    }

    public static class HomeItemStyle {
        @SerializedName("aspectRatio")
        private float aspectRatio;

        @SerializedName("margin")
        private List<Integer> margin;

        public float getAspectRatio() {
            return aspectRatio;
        }

        public void setAspectRatio(float aspectRatio) {
            this.aspectRatio = aspectRatio;
        }

        public List<Integer> getMargin() {
            return margin;
        }

        public void setMargin(List<Integer> margin) {
            this.margin = margin;
        }
    }

    @Override
    public String toString() {
        return "HomeItemsInfo{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", type='" + type + '\'' +
                ", action='" + action + '\'' +
                ", price='" + price + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}

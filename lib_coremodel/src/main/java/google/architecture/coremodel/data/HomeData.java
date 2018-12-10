package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/7/31
 */

public class HomeData  {

    @SerializedName("card")
    private List<HomeInfo> card;

    public List<HomeInfo> getCard() {
        return card;
    }

    public void setCard(List<HomeInfo> card) {
        this.card = card;
    }

    public static class HomeInfo {
        @SerializedName("type")
        private String type;
        @SerializedName("id")
        private String id;

        @SerializedName("style")
        private HomeStyleInfo style;
        @SerializedName("items")
        private List<HomeItemsInfo> items;

        public List<HomeItemsInfo> cacheItems;

        @SerializedName("header")
        private HomeHeaderInfo header;
        @SerializedName("footer")
        private HomeFooterInfo footer;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public HomeStyleInfo getStyle() {
            return style;
        }

        public void setStyle(HomeStyleInfo style) {
            this.style = style;
        }

        public List<HomeItemsInfo> getItems() {
            return items;
        }

        public void setItems(List<HomeItemsInfo> items) {
            this.items = items;
        }

        public HomeHeaderInfo getHeader() {
            return header;
        }

        public void setHeader(HomeHeaderInfo header) {
            this.header = header;
        }

        public HomeFooterInfo getFooter() {
            return footer;
        }

        public void setFooter(HomeFooterInfo footer) {
            this.footer = footer;
        }

    }

}

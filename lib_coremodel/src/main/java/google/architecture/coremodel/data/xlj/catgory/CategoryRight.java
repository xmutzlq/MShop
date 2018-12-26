package google.architecture.coremodel.data.xlj.catgory;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/12/26
 */

public class CategoryRight {

    @SerializedName("data")
    private List<CategoryRightChild> data;

    public List<CategoryRightChild> getData() {
        return data;
    }

    public void setData(List<CategoryRightChild> data) {
        this.data = data;
    }

    public static class CategoryRightChild {
        @SerializedName("urlids")
        private String urlids;
        @SerializedName("parentId")
        private String parentId;
        @SerializedName("catName")
        private String catName;
        @SerializedName("catImg")
        private String catImg;

        public String getUrlids() {
            return urlids;
        }

        public void setUrlids(String urlids) {
            this.urlids = urlids;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getCatImg() {
            return catImg;
        }

        public void setCatImg(String catImg) {
            this.catImg = catImg;
        }
    }
}

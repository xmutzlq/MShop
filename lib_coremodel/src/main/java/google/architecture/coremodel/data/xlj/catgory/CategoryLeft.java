package google.architecture.coremodel.data.xlj.catgory;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/12/26
 */

public class CategoryLeft {

    @SerializedName("data")
    private List<CategoryLeftChild> data;

    public List<CategoryLeftChild> getData() {
        return data;
    }

    public void setData(List<CategoryLeftChild> data) {
        this.data = data;
    }

    public static class CategoryLeftChild {
        @SerializedName("catName")
        private String catName;
        @SerializedName("catId")
        private String catId;

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }
    }
}

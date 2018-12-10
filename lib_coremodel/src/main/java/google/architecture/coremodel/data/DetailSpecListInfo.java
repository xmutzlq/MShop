package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/8/28
 */

public class DetailSpecListInfo {
    @SerializedName("item_id")
    private String item_id; //规格id
    @SerializedName("item_name")
    private String item_name; //规格值
    @SerializedName("selected")
    private int selected; //是否选中 1=是 -1=否
    @SerializedName("is_choose")
    private int is_choose; //是否可选择 1=是 -1=否

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getIs_choose() {
        return is_choose;
    }

    public void setIs_choose(int is_choose) {
        this.is_choose = is_choose;
    }
}

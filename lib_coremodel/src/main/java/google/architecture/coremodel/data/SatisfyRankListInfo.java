package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/29
 */

public class SatisfyRankListInfo {
    @SerializedName("name")
    private String name; //描述
    @SerializedName("value")
    private String value; //值
    @SerializedName("logo")
    private String logo; //图片
    @SerializedName("selected")
    private String selected; //是否默认选中，1是，0否

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}

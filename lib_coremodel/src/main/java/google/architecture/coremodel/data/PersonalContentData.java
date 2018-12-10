package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/4
 */

public class PersonalContentData {
    @SerializedName("title")
    private String name;
    @SerializedName("img")
    private String img;
    @SerializedName("items")
    private List<PersonalContentData> items;

    public PersonalContentData() {

    }

    public PersonalContentData(String img, String name) {
        this.img = img;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<PersonalContentData> getItems() {
        return items;
    }

    public void setItems(List<PersonalContentData> items) {
        this.items = items;
    }
}

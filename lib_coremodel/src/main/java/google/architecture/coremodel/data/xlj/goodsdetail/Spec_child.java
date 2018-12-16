package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class Spec_child {
    @SerializedName("name")
    private String name;
    @SerializedName("list")
    private java.util.List<List> list;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }
    public java.util.List<List> getList() {
        return list;
    }
}

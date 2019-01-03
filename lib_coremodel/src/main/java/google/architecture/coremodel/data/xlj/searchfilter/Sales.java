package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2019/1/3
 */

public class Sales {
    @SerializedName("class")
    private String salesClass;
    @SerializedName("name")
    private String name;

    public String getSalesClass() {
        return salesClass;
    }

    public void setSalesClass(String salesClass) {
        this.salesClass = salesClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

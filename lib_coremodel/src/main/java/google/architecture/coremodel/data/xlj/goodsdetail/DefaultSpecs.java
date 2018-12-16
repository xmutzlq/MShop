package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class DefaultSpecs {
    @SerializedName("1")
    private String s_1;
    @SerializedName("5")
    private String s_5;
    public void set1(String s_1) {
        this.s_1 = s_1;
    }
    public String get1() {
        return s_1;
    }

    public void set5(String s_5) {
        this.s_5 = s_5;
    }
    public String get5() {
        return s_5;
    }

}

package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class Spec {
    @SerializedName("1")
    private Spec_child spec_1;
    @SerializedName("5")
    private Spec_child spec_5;
    public void set1(Spec_child spec_1) {
        this.spec_1 = spec_1;
    }
    public Spec_child get1() {
        return spec_1;
    }

    public void set5(Spec_child spec_5) {
        this.spec_5 = spec_5;
    }
    public Spec_child get5() {
        return spec_5;
    }

}

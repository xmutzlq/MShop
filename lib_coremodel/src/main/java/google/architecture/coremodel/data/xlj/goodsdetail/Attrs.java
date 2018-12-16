package google.architecture.coremodel.data.xlj.goodsdetail;

/**
 * Copyright 2018 bejson.com
 */

import com.google.gson.annotations.SerializedName;

public class Attrs {
    @SerializedName("attrName")
    private String attrName;
    @SerializedName("attrVal")
    private String attrVal;

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
    public String getAttrName() {
        return attrName;
    }

    public void setAttrVal(String attrVal) {
        this.attrVal = attrVal;
    }
    public String getAttrVal() {
        return attrVal;
    }

}

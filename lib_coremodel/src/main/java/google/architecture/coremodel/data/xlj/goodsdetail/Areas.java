package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class Areas {
    @SerializedName("areaId")
    private int areaId;
    @SerializedName("areaName2")
    private String areaName2;
    @SerializedName("areaName1")
    private String areaName1;

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
    public int getAreaId() {
        return areaId;
    }

    public void setAreaName2(String areaName2) {
        this.areaName2 = areaName2;
    }
    public String getAreaName2() {
        return areaName2;
    }

    public void setAreaName1(String areaName1) {
        this.areaName1 = areaName1;
    }
    public String getAreaName1() {
        return areaName1;
    }
}

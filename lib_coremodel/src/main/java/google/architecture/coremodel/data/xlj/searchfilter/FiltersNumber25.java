package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

public class FiltersNumber25 {
    @SerializedName("p_name")
    private String p_name;
    @SerializedName("p_id")
    private int p_id;
    @SerializedName("c")
    private FiltersNumber25Child c;

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }
    public String getP_name() {
        return p_name;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }
    public int getP_id() {
        return p_id;
    }

    public void setC(FiltersNumber25Child c) {
        this.c = c;
    }
    public FiltersNumber25Child getC() {
        return c;
    }
}

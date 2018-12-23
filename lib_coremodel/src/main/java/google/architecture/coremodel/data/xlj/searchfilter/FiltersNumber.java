package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

public class FiltersNumber {
    @SerializedName("c_id")
    private int c_id;
    @SerializedName("c_name")
    private String c_name;
    @SerializedName("c_char")
    private int c_char;
    @SerializedName("c_urlId")
    private String c_urlId;
    @SerializedName("select")
    private String select;

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
    public int getC_id() {
        return c_id;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
    public String getC_name() {
        return c_name;
    }

    public void setC_char(int c_char) {
        this.c_char = c_char;
    }
    public int getC_char() {
        return c_char;
    }

    public void setC_urlId(String c_urlId) {
        this.c_urlId = c_urlId;
    }
    public String getC_urlId() {
        return c_urlId;
    }

    public void setSelect(String select) {
        this.select = select;
    }
    public String getSelect() {
        return select;
    }
}

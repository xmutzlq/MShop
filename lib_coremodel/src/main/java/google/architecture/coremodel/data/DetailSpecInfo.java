package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/8/28
 */

public class DetailSpecInfo {

    public int type;

    @SerializedName("spec_name")
    private String spec_name;
    @SerializedName("spec_list")
    private List<DetailSpecListInfo> spec_list;

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public List<DetailSpecListInfo> getSpec_list() {
        return spec_list;
    }

    public void setSpec_list(List<DetailSpecListInfo> spec_list) {
        this.spec_list = spec_list;
    }
}

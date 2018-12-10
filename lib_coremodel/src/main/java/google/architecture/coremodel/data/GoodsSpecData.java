package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/19
 */

public class GoodsSpecData {
    @SerializedName("spec_goods_list")
    private List<DetailSpecInfo> spec_goods_list;

    @SerializedName("spec_select_info")
    private DetailSpecSelectedInfo spec_select_info;

    public List<DetailSpecInfo> getSpec_goods_list() {
        return spec_goods_list;
    }

    public void setSpec_goods_list(List<DetailSpecInfo> spec_goods_list) {
        this.spec_goods_list = spec_goods_list;
    }

    public DetailSpecSelectedInfo getSpec_select_info() {
        return spec_select_info;
    }

    public void setSpec_select_info(DetailSpecSelectedInfo spec_select_info) {
        this.spec_select_info = spec_select_info;
    }
}

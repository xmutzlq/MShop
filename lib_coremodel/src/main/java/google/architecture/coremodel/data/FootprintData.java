package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/10/18
 */

public class FootprintData {
    @SerializedName("list")
    private List<FootprintInfo> list;
    @SerializedName("totalpage")
    private int totalpage;

    public List<FootprintInfo> getList() {
        return list;
    }

    public void setList(List<FootprintInfo> list) {
        this.list = list;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }
}

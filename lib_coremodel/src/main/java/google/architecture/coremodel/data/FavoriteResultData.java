package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/10/11
 */

public class FavoriteResultData {
    @SerializedName("collect_flag")
    private int collect_flag;

    public int getCollect_flag() {
        return collect_flag;
    }

    public void setCollect_flag(int collect_flag) {
        this.collect_flag = collect_flag;
    }
}

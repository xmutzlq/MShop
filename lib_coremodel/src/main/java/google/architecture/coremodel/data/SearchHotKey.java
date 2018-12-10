package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/8/13
 */

public class SearchHotKey {

    @SerializedName("hot_keyword")
    private List<String> hotKeyword;

    public List<String> getHotKeyword() {
        return hotKeyword;
    }

    public void setHotKeyword(List<String> hotKeyword) {
        this.hotKeyword = hotKeyword;
    }
}

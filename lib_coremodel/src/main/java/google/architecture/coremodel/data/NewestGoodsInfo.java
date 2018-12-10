package google.architecture.coremodel.data;

import org.json.JSONObject;

/**
 * @author lq.zeng
 * @date 2018/5/8
 */

public class NewestGoodsInfo {
    public String title;
    public String url;

    public NewestGoodsInfo(JSONObject jsonObject) {
        this.title = jsonObject.optString("title");
        this.url = jsonObject.optString("imageUrl");
    }
}

package google.architecture.coremodel.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.json.JSONObject;

import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/5/7
 */

public class RecommendInfo implements MultiItemEntity {
    public String url;
    public String title;

    public RecommendInfo(JSONObject jsonObject) {
        this.title = jsonObject.optString("country");
        this.url = jsonObject.optString("coverImageUrl");
    }

    @Override
    public int getItemType() {
        return MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE;
    }
}

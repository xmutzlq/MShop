package google.architecture.home.cache;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import google.architecture.coremodel.data.GoodsData;

/**
 * @author lq.zeng
 * @date 2018/6/4
 */

public class GoodsDataCache {
    //商品
    public static ArrayList<GoodsData> allGoodsData;

    public static void cacheGoodsData(Context context) {
        ArrayList<GoodsData> dataList = new ArrayList<>();
        try {
            InputStream in = context.getAssets().open("goods.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject jsonResult = jsonObject.optJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("goodlist");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                    GoodsData itemEntity = new GoodsData(itemJsonObject);
                    dataList.add(itemEntity);
                }
                if(GoodsDataCache.allGoodsData == null) {
                    GoodsDataCache.allGoodsData = new ArrayList<>(dataList);
                } else {
                    GoodsDataCache.allGoodsData.clear();
                    GoodsDataCache.allGoodsData.addAll(dataList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

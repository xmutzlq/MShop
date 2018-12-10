package google.architecture.home;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import google.architecture.coremodel.data.NewestGoodsInfo;
import google.architecture.coremodel.data.RecommendInfo;

/**
 * @author lq.zeng
 * @date 2018/5/7
 */

public class DataCache {
    //推荐数据
    public static ArrayList<RecommendInfo> allRecommendData;
    public static ArrayList<RecommendInfo> tempRecommendData;

    //最新商品
    public static ArrayList<NewestGoodsInfo> allNewestGoodsData;

    public static void cacheHomeRecommendData(Context context) {
        ArrayList<RecommendInfo> dataList = new ArrayList<>();
        try {
            InputStream in = context.getAssets().open("preset.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                    RecommendInfo itemEntity = new RecommendInfo(itemJsonObject);
                    dataList.add(itemEntity);
                }
                if(DataCache.allRecommendData == null) {
                    DataCache.allRecommendData = new ArrayList<>(dataList);
                } else {
                    DataCache.allRecommendData.clear();
                    DataCache.allRecommendData.addAll(dataList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cacheHomeNewestGoodsData(Context context) {
        ArrayList<NewestGoodsInfo> dataList = new ArrayList<>();
        try {
            InputStream in = context.getAssets().open("findBottomNews.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.optJSONObject(i);
                    NewestGoodsInfo itemEntity = new NewestGoodsInfo(itemJsonObject);
                    dataList.add(itemEntity);
                }
                if(DataCache.allNewestGoodsData == null) {
                    DataCache.allNewestGoodsData = new ArrayList<>(dataList);
                } else {
                    DataCache.allNewestGoodsData.clear();
                    DataCache.allNewestGoodsData.addAll(dataList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

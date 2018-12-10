package google.architecture.category;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import google.architecture.coremodel.data.CatesData;
import google.architecture.coremodel.data.OpDiscoverIndexResult;

/**
 * @author lq.zeng
 * @date 2018/6/5
 */

public class CatesCache {
    public static List<CatesData> catesData;

    public static OpDiscoverIndexResult getCatesData(Context context) {
        String json = getAssetsData(context, "discover.config");
        Gson gson = new Gson();
        OpDiscoverIndexResult result = gson.fromJson(json, OpDiscoverIndexResult.class);
        return result;
    }

    /**
     * 加载资源文件
     *
     * @param path
     * @return
     */
    private static String getAssetsData(Context context, String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = context.getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }

    public static void cacheCatesData(Context context) {
        ArrayList<CatesData> dataList = new ArrayList<>();
        try {
            InputStream in = context.getAssets().open("cates.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                    CatesData itemEntity = new CatesData(itemJsonObject);
                    dataList.add(itemEntity);
                }
                if(CatesCache.catesData == null) {
                    CatesCache.catesData = new ArrayList<>(dataList);
                } else {
                    CatesCache.catesData.clear();
                    CatesCache.catesData.addAll(dataList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

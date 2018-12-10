package google.architecture.home.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import google.architecture.coremodel.data.MoreFilterCatesData;
import google.architecture.coremodel.data.SearchResult;

/**
 * @author lq.zeng
 * @date 2018/6/1
 */

public class AllCatesData {
    public static List<MoreFilterCatesData> mDatas;

    public static List<MoreFilterCatesData> getAllFilterCates(ArrayList<SearchResult.FilterCat> catsData) {
        List<MoreFilterCatesData> cates = new ArrayList<>();
        if(catsData == null || catsData.size() == 0) return cates;
        for (int i = 0; i < catsData.size(); i ++) {
            SearchResult.FilterCat cat = catsData.get(i);
            MoreFilterCatesData data = makCateData("1", "header", cat.getName());
            cates.add(data);
        }
        return cates;
    }

    public static void makeCatesData() {
        List<MoreFilterCatesData> cates = new ArrayList<>();
        addData(cates, "1", "运动鞋包", "跑步鞋");
        addData(cates, "2", "流行男鞋", "休闲系");
        addData(cates, "3", "时尚女鞋", "高跟鞋");
        addData(cates, "4", "户外鞋服", "越野跑鞋");
        addData(cates, "5", "童鞋", "学步鞋");
        addData(cates, "6", "体育用品", "羽毛球鞋");
        addData(cates, "7", "健身训练", "舞蹈用品");
        addData(cates, "8", "功能箱包", "旅行配件");
        addData(cates, "9", "运动护具", "运动护膝");
        addData(cates, "10", "奢侈品", "饰品");
        addData(cates, "11", "生活用品", "牙膏");
        addData(cates, "12", "五金工具", "锯子");
        addData(cates, "13", "个人防护", "静电无尘");
        addData(cates, "14", "餐具", "碗筷");
        mDatas = cates;
    }

    private static void addData(List<MoreFilterCatesData> cates, String id, String parentValue, String childValue) {
        int count = new Random().nextInt(12) + 1;
        for (int i = 0; i < count; i ++) {
            MoreFilterCatesData data = makCateData(id, parentValue, childValue + "[" + i + "]");
            cates.add(data);
        }
    }

    private static MoreFilterCatesData makCateData(String id, String parentValue, String childValue) {
        MoreFilterCatesData data = new MoreFilterCatesData();
        data.id = id;
        data.cateName = parentValue;
        data.cateChildName = childValue;
        data.isChecked = false;
        return data;
    }
}

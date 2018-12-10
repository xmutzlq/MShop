package com.king.android;

import android.content.Context;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.CartBusinessesData;
import google.architecture.coremodel.data.CartGoodsData;
import google.architecture.coremodel.data.RecommendInfo;

/**
 * @author lq.zeng
 * @date 2018/5/10
 */

public class CartDataCache {
    //推荐数据
    public static ArrayList<RecommendInfo> allRecommendData;
    public static List<MultiItemEntity> allCartData;
    public static List<MultiItemEntity> cartData;

    public static String[] imgs = {"http://or4ceksby.bkt.clouddn.com/b_1.jpg", "http://or4ceksby.bkt.clouddn.com/b_5.jpg", "http://or4ceksby.bkt.clouddn.com/b_2.jpg"};

    public static List<MultiItemEntity> generateData() {
        List<MultiItemEntity> res = new ArrayList<>();
        res.addAll(CartDataCache.cartData);
        res.add(new EmptyStateEntity());
        res.add(new GuessTitleEntity("为·你·推·荐"));
        res.addAll(CartDataCache.allRecommendData);
        return res;
    }

    public static void getCartData() {
        List<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            CartBusinessesData lv0 = new CartBusinessesData();
            lv0.businessesId = i + "";
            lv0.businessesName = "商家[" + i + "]";
            for (int j = 0; j < 3; j++) {
                CartGoodsData lv1 = new CartGoodsData();
                lv1.goodsId = i + "_" + j;
                lv1.goodsPic = imgs[j];
                lv1.goodsPrice = 10 * (j + 1) + "";
                lv1.goodsName = "商家[" + i + "]" + "商品[" + j + "]";
                lv1.goodsDes = "商家[" + i + "]" + "商品价格[" + (10 * (j + 1)) + ".00元]";
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
            if(CartDataCache.cartData == null) {
                CartDataCache.cartData = new ArrayList<>(res);
            } else {
                CartDataCache.cartData.clear();
                CartDataCache.cartData.addAll(res);
            }
        }
    }

    public static class GuessTitleEntity implements MultiItemEntity {

        public String title;

        public GuessTitleEntity(String title) {
            this.title = title;
        }

        @Override
        public int getItemType() {
            return MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE_TITLE;
        }
    }

    public static class EmptyStateEntity implements MultiItemEntity {

        @Override
        public int getItemType() {
            return MultiItemTypeHelper.TYPE_EMPTY;
        }
    }

    public static void cacheRecommendData(Context context) {
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
                if(CartDataCache.allRecommendData == null) {
                    CartDataCache.allRecommendData = new ArrayList<>(dataList);
                } else {
                    CartDataCache.allRecommendData.clear();
                    CartDataCache.allRecommendData.addAll(dataList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

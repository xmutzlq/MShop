package com.king.android.details.util;

import google.architecture.coremodel.data.xlj.goodsdetail.List;

/**
 * @author lq.zeng
 * @date 2018/12/25
 */

public class DetailUtil {
    public static String getDefaultColor(java.util.List<List> colors, String colorId) {
        if(colors == null || colors.size() == 0) return "白色";
        for (List color : colors) {
            if(colorId.equals(color.getItemId())) {
                return color.getItemName();
            }
        }
        return "白色";
    }

    public static GoodsSizeParam getDefaultSizeAndSaveCount(java.util.List<List> sizes, String sizeId) {
        GoodsSizeParam params = new GoodsSizeParam();
        params.pSize = "0";
        params.pAllSave = 0;
        params.pShopSave = 0;
        if(sizes == null || sizes.size() == 0) {
            return params;
        }
        for (List size : sizes) {
            if(sizeId.equals(size.getItemId())) {
                params.pSize = size.getItemName();
                params.pAllSave = size.getSpecStock();
                params.pShopSave = size.getShopStock();
                return params;
            }
        }
        return params;
    }

    public static class GoodsSizeParam {
        //号码大小
        public String pSize;
        //号码总库存
        public int pAllSave;
        //号码商店库存
        public int pShopSave;
    }
}
